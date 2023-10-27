import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

public class GameFrame extends JFrame{
    HeroPlane heroPlane;

    Vector<Bullet> bullets = new Vector<>();
    Vector<Bullet> enemyBullets = new Vector<>();

    Vector<EnemyPlane> enemies = new Vector<>();
    GameFrame frame;


    public GameFrame() {
        frame = this;
        //create a default hero plane
        heroPlane = new HeroPlane();
        heroPlane.start();

        //set up the width and the height of the window
        this.setSize(480, 760);
        //set up the title of the window
        this.setTitle("Air War");
        //make the window non-resizable
        this.setResizable(false);
        //set closing method
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //locate the window at the middle of the screen
        this.setLocationRelativeTo(null);
        //make the window visible
        this.setVisible(true);

        /**
         * The three threads shouldn't be merged, otherwise they will be executed sequentially,
         * in this case, we want the threads to execute concurrently to have the background,
         */

        //this thread keeps using the paint() method
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    repaint();
                    try{
                        Thread.sleep(12);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //the thread creates enemy planes
        new Thread(new Runnable() {
            Random r = new Random();
            @Override
            public void run() {
                while (true) {
                    int type = r.nextInt(8); // Generate either 0 or 1
                    EnemyPlane enemyPlane = new EnemyPlane(r.nextInt(430),0,frame, type);
                    enemyPlane.start();
                    enemies.add(enemyPlane);
                    try {
                        Thread.sleep(650); // how much longer for the next enemyPlane to be created
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //the enemyPlane starts shooting
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // For each enemy plane
                    for (EnemyPlane enemy : enemies) {
                        // Create a new bullet
                        Bullet enemyBullet = new Bullet(enemy.x, enemy.y + 50);
                        // Set the bullet speed (negative to make it go downwards)
                        enemyBullet.speed = -2;
                        // Add the bullet to the collection
                        enemyBullets.add(enemyBullet);
                    }
                    // Sleep for some time before the next round of shooting
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //draw in the window
    //draw continually and animate it
    @Override
    public void paint (Graphics g){
        //draw the background
        BufferedImage image = (BufferedImage) this.createImage(this.getSize().width, this.getSize().height);
        //highly effective brush
        Graphics bi = image.getGraphics();
        //keep drawing the background to make it exist throughout the program
        bi.drawImage(new ImageIcon("img/MAP02_01.png").getImage(),0,0, null);

        //draw the hero plane
        if (Player.score < 20) {
            bi.drawImage(heroPlane.img3, heroPlane.x, heroPlane.y, heroPlane.width, heroPlane.height, null);
            //the plane starts shooting
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                //the amount of bullets at a time
                if (bullet.y > 0)
                    bi.drawImage(bullet.image, bullet.x, bullet.y -= bullet.speed, bullet.width, bullet.height, null);
                else
                    bullets.remove(bullet);
            }
        }
        else if (Player.score < 40) {
            bi.drawImage(heroPlane.img, heroPlane.x, heroPlane.y, heroPlane.width, heroPlane.height, null);
            //the plane starts shooting
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                //the amount of bullets at a time
                if (bullet.y > 0) {
                    bi.drawImage(bullet.image1, bullet.x - 25, bullet.y -= bullet.speed, bullet.width, bullet.height, null);
                    bi.drawImage(bullet.image1, bullet.x + 25, bullet.y -= bullet.speed, bullet.width, bullet.height, null);
                } else
                    bullets.remove(bullet);
            }
        }
        else {
            bi.drawImage(heroPlane.img2, heroPlane.x, heroPlane.y, heroPlane.width, heroPlane.height, null);
            //the plane starts shooting
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                //the amount of bullets at a time
                if (bullet.y > 0) {
                    bi.drawImage(bullet.image2, bullet.x - 25, bullet.y -= bullet.speed, bullet.width, bullet.height, null);
                    bi.drawImage(bullet.image2, bullet.x, bullet.y -= bullet.speed, bullet.width, bullet.height, null);
                    bi.drawImage(bullet.image2, bullet.x + 25, bullet.y -= bullet.speed, bullet.width, bullet.height, null);
                } else
                    bullets.remove(bullet);
            }
        }

        //draw enemyPlanes
        for (int i = 0; i < enemies.size(); i++) {
            EnemyPlane ep = enemies.get(i);
            Bullet bt = new Bullet(ep.x,ep.y) ;
            Random enemy = new Random();
            if(ep.y < 760)
                //change the speed of the plane， ep.y += 1 was ep.y += ep.speed
                bi.drawImage(ep.img, ep.x, ep.y += 1, ep.width, ep.height, null);
            else
                bullets.remove(ep);
        }

        //draw enemy bullets
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bullet enemyBullet = enemyBullets.get(i);
            if (enemyBullet.y < 760 && enemyBullet.y > 0) {
                bi.drawImage(enemyBullet.image3, enemyBullet.x + 10, enemyBullet.y -= enemyBullet.speed, 30, 30, null);
            } else {
                enemyBullets.remove(enemyBullet);
            }
        }

        //make the code above work
        g.drawImage(image, 0,0, null);
    }

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        Player player = new Player(frame);
        frame.addKeyListener(player);
    }
}