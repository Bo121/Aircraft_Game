import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet {

    //the coordinate
    int x, y;
    //attributes of the bullets
    int width = 30, height = 30;

    //the speed of the bullets
    int speed = 9;

    Image image = new ImageIcon("img/Bullet.png").getImage();
    Image image1 = new ImageIcon("img/Bullet1.png").getImage();
    Image image2 = new ImageIcon("img/Bullet2.png").getImage();
    Image image3 = new ImageIcon("img/EnemyBullet.png").getImage();

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Bullet(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}