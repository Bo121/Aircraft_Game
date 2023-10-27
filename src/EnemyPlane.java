import javax.swing.*;
import java.awt.*;

public class EnemyPlane extends Thread{ //make Thread the super class of EnemyPlane
    public GameFrame gf;
    //the coordinate of the EnemyPlane
    public int x, y;
    //attributes of the EnemyPlane
    public int width = 50, height =50;
    //the speed of the EnemyPlane
    public int speed = 2;
    //different types of EnemyPlanes being drawn
    public int type;
    public Image img;

    public EnemyPlane(int x, int y, GameFrame gf) {
        super(); //call the Thread constructor
        this.x = x;
        this.y = y;
        this.gf = gf;
    }
    public EnemyPlane(int x, int y, GameFrame gf, int type) {
        super();
        this.x = x;
        this.y = y;
        this.gf = gf;
        this.type = type;
        //which EnemyPlane to be drawn
        if (type == 0)
            this.img = new ImageIcon("img/Enemy.png").getImage();
        else if (type == 1)
            this.img = new ImageIcon("img/Enemy2.png").getImage();
        else if (type == 2)
            this.img = new ImageIcon("img/Enemy3.png").getImage();
        else if (type == 3)
            this.img = new ImageIcon("img/Enemy4.png").getImage();
        else if (type == 4)
            this.img = new ImageIcon("img/Enemy5.png").getImage();
        else if (type == 5)
            this.img = new ImageIcon("img/Enemy6.png").getImage();
        else if (type == 6)
            this.img = new ImageIcon("img/Enemy7.png").getImage();
        else if (type == 7)
            this.img = new ImageIcon("img/Enemy8.png").getImage();
    }

    //moving logic
    public void run() {
        while(true) {
            // addBullet();
            //hit
            if (hit()) {
                // addBullet(); //if this code is added, then another bullet will be fired automatically if an enemy plane was hit
                Player.score++;
                // the speed will be 0 if the EnemyPlanes were hit
                this.speed = 0;
                // update the images to the explosion images
                this.img = new ImageIcon("img/die.png").getImage();

                // it pauses the thread for 100 milliseconds to allow the explosion
                // image to be displayed for a short duration.
                try {
                    this.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // removes this enemy plane from the enemies list of the GameFrame object.
                gf.enemies.remove(this);
                break;
            }

            // if the enemyPlane has not been hit,to see if the plane has gone off
            // the bottom of the window,  If it has, it breaks out of the loop,
            // stopping the enemy plane's thread.
            if(this.y > 760)
                break;

            // if the enemy plane hasn't been hit and is still within the game window.
            // The thread is then put to sleep for 50 milliseconds before the next iteration of the loop.
            // This pause helps control the pace of the game by preventing the game loop
            // from running too quickly.
            try {
                this.sleep(10); // the time to check for hit
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // hitting test
    public boolean hit() {
        Rectangle myrect = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle rect = null;

        for (int i = 0; i < gf.bullets.size(); i++) {
            Bullet bullet = gf.bullets.get(i);
            rect = new Rectangle(bullet.x, bullet.y-1, bullet.width, bullet.height);
            //return true if hit
            if(myrect.intersects(rect)) {
                return true;
            }
        }
        return false;
    }

    public void addBullet() {
        gf.bullets.add(new Bullet(gf.heroPlane.x+10, gf.heroPlane.y-20));
    }
}