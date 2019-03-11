package MyTankGame;
import java.awt.*;
import java.util.ArrayList;

public class Tank {
    int x;
    int y;
    int speed;
    int direction;
    int power;
    int blood;
    int type;
    int totalBlood;
    static ArrayList<Bullet> bullet = new ArrayList<Bullet>();
    //坦克类型一的图片
    static Image Tank1[]= {
            GameUtil.getImage("TankImage/Tank1_Up.png"),
            GameUtil.getImage("TankImage/Tank1_Down.png"),
            GameUtil.getImage("TankImage/Tank1_Left.png"),
            GameUtil.getImage("TankImage/Tank1_Right.png"),
    };
    //坦克类型二图片
    static Image Tank2[]= {
            GameUtil.getImage("TankImage/Tank2_Up.png"),
            GameUtil.getImage("TankImage/Tank2_Down.png"),
            GameUtil.getImage("TankImage/Tank2_Left.png"),
            GameUtil.getImage("TankImage/Tank2_Right.png"),
    };
    //坦克类型三图片
    static Image Tank3[]= {
            GameUtil.getImage("TankImage/Tank3_Up.png"),
            GameUtil.getImage("TankImage/Tank3_Down.png"),
            GameUtil.getImage("TankImage/Tank3_Left.png"),
            GameUtil.getImage("TankImage/Tank3_Right.png"),
    };

    //敌方坦克
    static Image Tank4[] = {
            GameUtil.getImage("TankImage/TankUp.png"),
            GameUtil.getImage("TankImage/TankDown.png"),
            GameUtil.getImage("TankImage/TankLeft.png"),
            GameUtil.getImage("TankImage/TankRight.png"),
    };

    //数字图片
    static Image number[] = {
            GameUtil.getImage("NumImage/num0.png"),
            GameUtil.getImage("NumImage/num1.png"),
            GameUtil.getImage("NumImage/num2.png"),
            GameUtil.getImage("NumImage/num3.png"),
            GameUtil.getImage("NumImage/num4.png"),
            GameUtil.getImage("NumImage/num5.png"),
            GameUtil.getImage("NumImage/num6.png"),
            GameUtil.getImage("NumImage/num7.png"),
            GameUtil.getImage("NumImage/num8.png"),
            GameUtil.getImage("NumImage/num9.png"),
    };
    class Bullet implements Runnable{
        int x;
        int y;
        int power;
        int speed;
        int dirction;
        boolean isLive;
        int size = 20;
        boolean hero;
        Thread thread;

        public Bullet(){
            thread = new Thread(this);
        }

        @Override
        public synchronized void run() {
            while(true) {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(dirction == 0)
                    y -= speed;
                if(dirction == 1)
                    y += speed;
                if(dirction == 2)
                    x -= speed;
                if(dirction == 3)
                    x += speed;
                if(y < 0 || y > Constent.height || x < 0 || x > Constent.width)
                    isLive = false;
                if(!isLive)
                    break;
            }
        }
    }
}
