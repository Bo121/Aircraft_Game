import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

//the plane keeps receiving instructions, so make the class a thread
public class HeroPlane extends Thread{

    //the coordinate of the plane
    int x = 220, y = 600;
    //the attributes of the plane
    int width = 50, height = 50;
    //the speed of the plane
    int speed = 10;

    Image img = new ImageIcon("img/10013.png").getImage();
    Image img2 = new ImageIcon("img/10012.png").getImage();
    Image img3 = new ImageIcon("img/10011.png").getImage();

    //Define the movements
    boolean up, down, left, right;

    public HeroPlane() {
    }

    public HeroPlane(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void run(){
        // In computer graphics，（0,0) is located
        // at the top-left corner of the screen or component
        while (true) {
            if (up) {
                if (y > 37)
                    y -= speed; //y coordinate keeps decreasing
                else
                    y = y; // make the plane stay inside the window
            }
            if (down) {
                if (y < 710) // 760-50
                    y += speed; //y coordinate keeps increasing
                else
                    y = y; // make the plane stay inside the window
            }
            if (left) {
                if (x > 0)
                    x -= speed; //x coordinate keeps decreasing
                else
                    x = x; // make the plane stay inside the window
            }
            if (right) {
                if (x < 430) // 480-50
                    x += speed; //x coordinate keeps increasing
                else
                    x = x; // make the plane stay within the window
            }

            // Used to pause the execution of the current
            // thread for a specified time period, in this case,
            // 10 milliseconds, to control the speed of movement.
            // It helps to regulate the speed of the plane's
            // movement by ensuring that the plane's position isn't
            // updated too quickly.
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
