package MyTankGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MyMap {
    BufferedImage imag = new BufferedImage(Constent.width, Constent.height, BufferedImage.TYPE_INT_RGB);
    Graphics g1 = imag.getGraphics();
    static ArrayList<Wood> wood;
    static ArrayList<Grass> grass;
    static ArrayList<Stone> stone;
    static ArrayList<Water> water;
    Image mapImage[];
    static int size = 50;
    static int GameMap[][];
    static int GameMap1[][] = {
            {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0},
            {0, 2, 1, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 0, 0, 1, 0, 0},
            {0, 2, 1, 0, 1, 1, 3, 0, 0, 2, 2, 0, 0, 1, 3, 0, 0, 3, 1, 0, 0, 1, 0, 0},
            {0, 1, 1, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0},
            {0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 0, 2, 1, 0},
            {0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 0, 0, 1, 0},
            {0, 2, 2, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 2, 2, 3, 3, 3, 3, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 2, 3, 3, 0, 2, 0, 0, 1, 1, 1, 1, 1, 0, 2, 2, 0, 0, 1, 1, 1, 1, 0},
            {0, 2, 2, 3, 3, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 2, 2, 0, 0, 1, 1, 1, 1, 0},
            {0, 0, 2, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    static int GameMap2[][] = {
            {0, 0, 0, 2, 3, 0, 1, 2, 2, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 2, 3, 3, 1, 0, 0, 0, 3, 0, 0, 0, 0, 1, 3, 3, 0, 0, 0, 0, 0, 0, 0},
            {3, 1, 0, 3, 0, 0, 3, 0, 0, 0, 4, 4, 0, 2, 0, 0, 3, 0, 0, 0, 2, 2, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 4, 4, 0, 0, 0, 0, 0, 3, 0, 2, 2, 0, 0},
            {0, 0, 4, 3, 0, 0, 3, 0, 1, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {3, 4, 4, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 4, 4, 0, 2, 0, 3, 3, 0, 0, 1, 0},
            {0, 4, 4, 0, 0, 0, 0, 2, 0, 2, 0, 0, 2, 0, 0, 4, 2, 0, 0, 0, 1, 2, 0, 2},
            {0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 2, 2, 2, 2, 0, 0, 0, 1, 1, 2, 3, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 4, 4, 3, 0, 0, 3, 1, 1, 0, 0, 0, 0, 0, 3, 2, 2, 0, 1, 0, 0, 2},
            {0, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 2, 2, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0},
            {2, 1, 1, 1, 1, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 3, 3, 0, 3, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 3, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 2, 3, 0, 2},
            {0, 2, 0, 0, 3, 3, 0, 0, 0, 1, 0, 0, 0, 1, 0, 2, 1, 0, 2, 1, 1, 0, 0, 0},
            {0, 1, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 2, 1, 0, 0, 0, 2, 0, 0, 0},
    };

    static int GameMap3[][] = {
            {0, 0, 1, 2, 1, 2, 4, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 2, 0, 2, 0, 0, 0},
            {0, 0, 3, 2, 1, 2, 4, 0, 0, 2, 1, 0, 2, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0},
            {1, 1, 3, 1, 0, 0, 4, 4, 0, 1, 0, 0, 0, 1, 1, 0, 0, 3, 2, 0, 2, 0, 0, 0},
            {0, 1, 0, 1, 0, 2, 4, 4, 4, 0, 0, 3, 2, 0, 0, 0, 1, 0, 4, 2, 0, 0, 2, 1},
            {1, 3, 0, 0, 2, 0, 0, 0, 4, 0, 1, 0, 0, 2, 2, 0, 0, 4, 4, 4, 2, 0, 0, 0},
            {1, 0, 0, 0, 2, 3, 0, 2, 4, 4, 0, 2, 0, 0, 0, 0, 0, 4, 4, 4, 0, 1, 1, 0},
            {0, 1, 1, 4, 1, 3, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 4, 4, 4, 4, 0, 0, 0, 1},
            {0, 2, 1, 4, 4, 1, 2, 0, 1, 0, 2, 0, 1, 3, 0, 1, 0, 0, 2, 0, 1, 3, 0, 3},
            {0, 0, 0, 4, 4, 4, 2, 0, 3, 0, 0, 1, 1, 0, 0, 0, 3, 1, 0, 2, 0, 0, 0, 0},
            {1, 0, 1, 1, 1, 0, 2, 3, 0, 1, 1, 1, 0, 2, 0, 2, 0, 0, 2, 0, 4, 2, 2, 0},
            {0, 0, 0, 2, 2, 3, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 3, 2, 0, 0, 4, 4, 4, 3},
            {1, 1, 1, 0, 0, 0, 3, 3, 0, 2, 2, 0, 0, 0, 1, 3, 0, 0, 0, 1, 4, 4, 4, 0},
            {0, 0, 0, 2, 1, 2, 1, 0, 2, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 0, 2, 0, 0, 2},
            {0, 3, 0, 0, 1, 0, 2, 2, 0, 1, 0, 0, 0, 1, 2, 2, 2, 0, 0, 2, 1, 1, 2, 0},
            {3, 2, 1, 2, 1, 0, 1, 2, 0, 1, 0, 0, 0, 1, 2, 0, 0, 1, 0, 2, 1, 0, 0, 0},
    };
    public MyMap() {
        mapImage = new Image[5];
        mapImage[1] = GameUtil.getImage("TankImage/wood.png");
        mapImage[2] = GameUtil.getImage("TankImage/grass.png");
        mapImage[3] = GameUtil.getImage("TankImage/stone.png");
        mapImage[4] = GameUtil.getImage("TankImage/water.png");
        wood = new ArrayList<Wood>();
        grass = new ArrayList<Grass>();
        stone = new ArrayList<Stone>();
        water = new ArrayList<Water>();
        Wood w;
        Grass g;
        Stone s;
        Water wa;
        if(Constent.whatMap == 1)
            GameMap = GameMap1;
        else if(Constent.whatMap == 2)
            GameMap = GameMap2;
        else
            GameMap = GameMap3;
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 24; j++) {
                if(GameMap[i][j] == 1){
                    w = new Wood();
                    w.setX(j * size);
                    w.setY(i * size);
                    w.setBlood(20);
                    w.setType(1);
                    wood.add(w);
                }
                if(GameMap[i][j] == 2){
                    g = new Grass();
                    g.setX(j * size);
                    g.setY(i * size);
                    g.setBlood(10);
                    g.setType(2);
                    grass.add(g);
                }
                if(GameMap[i][j] == 3){
                    s = new Stone();
                    s.setX(j * size);
                    s.setY(i * size);
                    s.setBlood(0);
                    s.setType(2);
                    stone.add(s);
                }
                if(GameMap[i][j] == 4){
                    wa = new Water();
                    wa.setX(j * size);
                    wa.setY(i * size);
                    wa.setBlood(0);
                    wa.setType(2);
                    water.add(wa);
                }
            }
        }
    }

    class Wood{
        private int x;
        private int y;
        private int type;
        int blood;
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getBlood() {
            return blood;
        }

        public void setBlood(int blood) {
            this.blood = blood;
        }

    }
    class Grass{
        private int x;
        private int y;
        private int type;
        int blood;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getBlood() {
            return blood;
        }

        public void setBlood(int blood) {
            this.blood = blood;
        }
    }
    class Stone{
        private int x;
        private int y;
        private int type;
        int blood;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getBlood() {
            return blood;
        }

        public void setBlood(int blood) {
            this.blood = blood;
        }
    }
    class Water{
        private int x;
        private int y;
        private int type;
        int blood;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getBlood() {
            return blood;
        }

        public void setBlood(int blood) {
            this.blood = blood;
        }
    }
}
