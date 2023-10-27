import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Define a player
 */
public class Player extends KeyAdapter {

    GameFrame frame;
    public static int score = 0;

    public Player(GameFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case 38:
                frame.heroPlane.up = true;
                break;
            case 40:
                frame.heroPlane.down = true;
                break;
            case 37:
                frame.heroPlane.left = true;
                break;
            case 39:
                frame.heroPlane.right = true;
                break;

            // press the space bar to shoot
            case 32:
                addBullet();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        //up：38, down：40, left：37, right：39
        switch (keyCode) {
            case 38:
                frame.heroPlane.up = false;
                break;
            case 40:
                frame.heroPlane.down = false;
                break;
            case 37:
                frame.heroPlane.left = false;
                break;
            case 39:
                frame.heroPlane.right = false;
                break;
        }
    }
    //add bullets to the plane
    public void addBullet() {
        frame.bullets.add(new Bullet(frame.heroPlane.x+10, frame.heroPlane.y-20));
    }
}
