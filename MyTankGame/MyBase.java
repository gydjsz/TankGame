package MyTankGame;

import java.awt.*;

public class MyBase {
    static private int x;
    static private int y;
    static private int size;
    private int blood;
    private boolean isLive;
    private int totalBlood;
    Image trophy = GameUtil.getImage("TankImage/trophy.png");

    public MyBase(){
        x = 520;
        y = 650;
        size = 120;
        totalBlood = blood = 50;
        isLive = true;
    }

    public static int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public static int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public static int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getTotalBlood() {
        return totalBlood;
    }

    public void setTotalBlood(int totalBlood) {
        this.totalBlood = totalBlood;
    }
}
