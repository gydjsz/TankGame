package MyTankGame;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class TankFight extends JPanel implements Runnable{
    static Image sky = GameUtil.getImage("TankImage/sky.jpg");
    static Image newSky = GameUtil.getImage("TankImage/newSky.jpeg");
    static Image explode[] = {
            GameUtil.getImage("TankImage/explode1.gif"),
            GameUtil.getImage("TankImage/explode2.gif"),
            GameUtil.getImage("TankImage/explode3.gif"),
    };
    static Image bome[];
    static {
        bome = new Image[16];
        for(int i = 1; i <= 16; i++)
            GameUtil.getImage("explodeImages/p" + i + ".png");
    }
    static Image fruit[] = {
            GameUtil.getImage("TankImage/heart.png"),
            GameUtil.getImage("TankImage/mango.png"),
            GameUtil.getImage("TankImage/pear.png"),
    };
    static Image improve[];
    static {
        improve = new Image[8];
        for(int i = 0; i < 8; i++)
            improve[i] = GameUtil.getImage("improveImage/improve" + (i + 1) + ".png");
    };
    BufferedImage imag = new BufferedImage(Constent.windowWidth, Constent.height, BufferedImage.TYPE_INT_RGB);
    Graphics g1 = imag.getGraphics();
    int countTank;
    ArrayList<Promotion> promotion;
    static ArrayList<EnemyTank> enemyTank = new ArrayList<EnemyTank>();
    static HeroTank heroTank;
    MyMap myMap;
    MyBase myBase;
    Thread thread;
    int countDead;
    int countHeart;
    int countMango;
    int countPear;
    int sumTank;
    static boolean isEnd;
    SetEnd setEnd;

    int i = 0;
    public TankFight(){
        myMap = new MyMap();
        heroTank = new HeroTank();
        enemyTank.clear();
        Tank.bullet.clear();
        myBase = new MyBase();
        countTank = 0;
        countDead = 0;
        sumTank = 1;
        promotion = new ArrayList<Promotion>();
        produceEnemyTank();
        thread = new Thread(this);
        thread.start();
        countHeart = 0;
        countMango = 0;
        countPear = 0;
        isEnd = false;
        setEnd = new SetEnd();
    }
    @Override
    public void paint(Graphics g) {
        g1.drawImage(sky, 0, 0, Constent.width, Constent.height, null);
        g1.setFont(new Font("楷体", Font.BOLD, 40));
        if(countDead == Constent.EnemyTankNumber)
            heroTank.success = true;
        if(heroTank.success) {
            isEnd = true;
            Font f = g1.getFont();
            g1.setFont(new Font("宋体", Font.BOLD, 100));
            g1.setColor(Color.RED);
            g1.drawString("胜利!", 400, 500);
            g1.setColor(Color.GREEN);
            g1.setFont(f);
            if(!setEnd.isPlay) {
                setEnd.endTime = System.currentTimeMillis();
                setEnd.time = setEnd.endTime - setEnd.startTime;
                setEnd.isPlay = true;
                setEnd.grade = setEnd.deadTank * setEnd.tankGrade + countHeart * setEnd.heartGrade + countMango * setEnd.mangoGrade + countPear * setEnd.pearGrade;
                setEnd.x = 0;
            }
            if (setEnd.x < 100) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                g1.drawString("消灭的坦克:", setEnd.x += 3, setEnd.y);
                g1.drawString("得分:", setEnd.x += 3, setEnd.y + 100);
                g1.drawString("所用时间：", setEnd.x += 3, setEnd.y + 200);

            } else {
                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
                String s = time.format(setEnd.time);
                g1.drawString("消灭的坦克:", setEnd.x, setEnd.y);
                g1.drawString("得分:", setEnd.x, setEnd.y + 100);
                g1.drawString("所用时间：", setEnd.x, setEnd.y + 200);
                g1.drawString(String.valueOf(setEnd.deadTank), setEnd.x + 250, setEnd.y);
                g1.drawString(String.valueOf(setEnd.grade), setEnd.x + 250, setEnd.y + 100);
                g1.drawString(String.valueOf(s), setEnd.x + 250, setEnd.y + 200);
            }
            g1.setColor(Color.CYAN);
            g1.drawString("按下Enter键回到主界面", 600, 600);
        }
        else if(myBase.isLive() && heroTank.Live != 0) {
            for (int i = 0; i < myMap.wood.size(); i++) {
                g1.drawImage(myMap.mapImage[1], myMap.wood.get(i).getX(), myMap.wood.get(i).getY(), 50, 50, null);
            }
            for (int i = 0; i < myMap.grass.size(); i++) {
                g1.drawImage(myMap.mapImage[2], myMap.grass.get(i).getX(), myMap.grass.get(i).getY(), 50, 50, null);
            }
            for (int i = 0; i < myMap.stone.size(); i++) {
                g1.drawImage(myMap.mapImage[3], myMap.stone.get(i).getX(), myMap.stone.get(i).getY(), 50, 50, null);
            }
            for (int i = 0; i < myMap.water.size(); i++) {
                g1.drawImage(myMap.mapImage[4], myMap.water.get(i).getX(), myMap.water.get(i).getY(), 50, 50, null);
            }
            //画出所有的子弹
            drawBullet();
            //画出升级水果
            for(int i = 0; i < promotion.size(); i++){
                g1.drawImage(fruit[promotion.get(i).type], promotion.get(i).x, promotion.get(i).y, 30, 30, null);
                if(isInterse(promotion.get(i).x, promotion.get(i).y, heroTank.x, heroTank.y, 30, heroTank.size)) {
                    if(promotion.get(i).type == 0) {
                        countHeart++;
                        heroTank.blood += promotion.get(i).blood;
                    }
                    else if(promotion.get(i).type == 1) {
                        countMango++;
                        heroTank.power += promotion.get(i).power;
                    }
                    else {
                        if(Constent.Speed >= 20)
                            Constent.Speed += promotion.get(i).speed;
                        countPear++;
                    }
                    promotion.get(i).isLive = false;
                    promotion.remove(i);
                    continue;
                }
            }
            //画出基地
            if (myBase.isLive()) {
                g1.drawImage(myBase.trophy, myBase.getX(), myBase.getY(), myBase.getSize(), myBase.getSize(), null);
                drawHaemalstrand(myBase.getX(), myBase.getY(), myBase.getBlood(), myBase.getTotalBlood(), 110);
            }

            for (int i = 0; i < enemyTank.size(); i++) {
                g1.drawImage(EnemyTank.EnemyImage[enemyTank.get(i).direction], enemyTank.get(i).x, enemyTank.get(i).y, 40, 40, null);
                drawHaemalstrand(enemyTank.get(i).x, enemyTank.get(i).y, enemyTank.get(i).blood, enemyTank.get(i).totalBlood, 50);
            }
            if (heroTank.isLive == true) {
                g1.drawImage(heroTank.tank[heroTank.direction], heroTank.x, heroTank.y, heroTank.size, heroTank.size, null);
                drawHaemalstrand(heroTank.x, heroTank.y, heroTank.blood, heroTank.totalBlood, 50);
            }
        }
        else {
            isEnd = true;
            Font f = g1.getFont();
            g1.setColor(Color.GRAY);
            g1.setFont(new Font("宋体", Font.BOLD, 100));
            g1.drawString("Game Over !", 300, 500);
            g1.setColor(Color.CYAN);
            g1.setFont(f);
            if(!setEnd.isPlay) {
                setEnd.endTime = System.currentTimeMillis();
                setEnd.time = setEnd.endTime - setEnd.startTime;
                setEnd.isPlay = true;
                setEnd.grade = setEnd.deadTank * setEnd.tankGrade + countHeart * setEnd.heartGrade + countMango * setEnd.mangoGrade + countPear * setEnd.pearGrade;
                setEnd.x = 0;
            }
            if (setEnd.x < 100) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                g1.drawString("消灭的坦克:", setEnd.x += 3, setEnd.y);
                g1.drawString("得分:", setEnd.x += 3, setEnd.y + 100);
                g1.drawString("所用时间：", setEnd.x += 3, setEnd.y + 200);

            } else {
                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
                String s = time.format(setEnd.time);
                g1.drawString("消灭的坦克:", setEnd.x, setEnd.y);
                g1.drawString("得分:", setEnd.x, setEnd.y + 100);
                g1.drawString("所用时间：", setEnd.x, setEnd.y + 200);
                g1.drawString(String.valueOf(setEnd.deadTank), setEnd.x + 250, setEnd.y);
                g1.drawString(String.valueOf(setEnd.grade), setEnd.x + 250, setEnd.y + 100);
                g1.drawString(String.valueOf(s), setEnd.x + 250, setEnd.y + 200);
            }
            g1.drawString("按下Enter键回到主界面", 600, 600);
        }
        drawGameInformation();
        g.drawImage(imag, 0, 0, Constent.windowWidth, Constent.height, null);
        repaint();
    }
    public void drawGameInformation(){
        g1.drawImage(newSky, 1210, 0, Constent.windowWidth - Constent.width, Constent.height, null);
        g1.setColor(Color.WHITE);
        g1.setFont(new Font("楷体", Font.BOLD, 40));
        g1.drawString("游戏信息", 1220, 50);
        g1.drawImage(Tank.Tank4[0], 1210, 130, 50, 50, null);
        g1.drawImage(fruit[0], 1220, 458, 40, 40, null);
        g1.drawImage(fruit[1], 1220, 510, 40, 40, null);
        g1.drawImage(fruit[2], 1220, 560, 40, 40, null);
        g1.drawString(String.valueOf(countHeart), 1280, 493);
        g1.drawString("血量", 1315, 493);
        g1.drawString(String.valueOf(countMango), 1280, 546);
        g1.drawString("攻击", 1315, 546);
        g1.drawString(String.valueOf(countPear), 1280, 595);
        g1.drawString("速度", 1315, 595);
        int x1 = 1300, y1 = 140, n = sumTank;
        if(n / 10 == 0) {
            g1.drawImage(Tank.number[n % 10], x1, y1, 30, 30, null);
        }
        else {
            g1.drawImage(Tank.number[n / 10], x1 - 5, y1, 30, 30, null);
            g1.drawImage(Tank.number[n % 10], x1 + 20, y1, 30, 30, null);
        }
        g1.drawString("坦克生命", 1210, 300);
        g1.drawString(String.valueOf(heroTank.Live), 1280, 350);
//        g1.drawString(String.valueOf(enemyTank.size()), 1300, 120);
    }
    public void drawHaemalstrand(int x, int y, int blood, int totalBlood, int width){
        Color c = g1.getColor();
        g1.setColor(Color.RED);
        g1.drawRect(x - 5 - blood / totalBlood, y - 10, width * blood / totalBlood, 6);
        g1.fillRect(x - 5 - blood / totalBlood, y - 10, width * blood / totalBlood, 6);
        g1.setColor(c);
    }
    public void getFruit(int x, int y){
        Promotion p = new Promotion();
        p.x = x;
        p.y = y;
        Random random = new Random();
        int n = random.nextInt(12);
        if(n == 0){
            p.type = 0;
            promotion.add(p);
        }
        if(n == 1){
            p.type = 1;
            promotion.add(p);
        }
        if(n == 2){
            p.type = 2;
            promotion.add(p);
        }
    }
    public void drawBullet(){
        g1.setColor(Color.black);
        for(int i = 0; i < Tank.bullet.size(); i++) {
            //判断基地是否死亡
            if(myBase.isLive() && isInterse(Tank.bullet.get(i).x, Tank.bullet.get(i).y, myBase.getX(), myBase.getY(), Tank.bullet.size(), myBase.getSize())){
                myBase.setBlood(myBase.getBlood() - Tank.bullet.get(i).power);
                drawExplode(Tank.bullet.get(i).x, Tank.bullet.get(i).y);
                if(myBase.getBlood() <= 0)
                    myBase.setLive(false);
                Tank.bullet.remove(i);
                continue;
            }

            //hero的子弹击中敌方
            if(Tank.bullet.get(i).hero){
                for(int j = 0; j < enemyTank.size(); j++){
                    if(isInterse(enemyTank.get(j).x, enemyTank.get(j).y, Tank.bullet.get(i).x, Tank.bullet.get(i).y, enemyTank.get(j).size, Tank.bullet.get(i).size)) {
                        enemyTank.get(j).blood -= heroTank.power;
                        enemyTank.get(j).isOk = false;
                        Tank.bullet.get(i).isLive = false;
                    }
                    if(enemyTank.get(j).blood <= 0) {
                        setEnd.deadTank++;
                        drawExplode(Tank.bullet.get(i).x, Tank.bullet.get(i).y);
                        countDead++;
                        getFruit(enemyTank.get(j).x, enemyTank.get(j).y);
                        enemyTank.remove(j);
                    }
                }
                if(!Tank.bullet.get(i).isLive){
                    Tank.bullet.remove(i);
                    continue;
                }
            }
            if(!Tank.bullet.get(i).hero && heroTank.isLive){
                if(isInterse(Tank.bullet.get(i).x, Tank.bullet.get(i).y, heroTank.x, heroTank.y, Tank.bullet.get(i).size, heroTank.size)){
                    drawExplode(Tank.bullet.get(i).x, Tank.bullet.get(i).y);
                    heroTank.blood -= Tank.bullet.get(i).power;
                    if(heroTank.blood <= 0) {
                        heroTank.isLive = false;
                        heroTank.Live--;
                    }
                    Tank.bullet.remove(i);
                    continue;
                }
            }
            for(int j = 0; j < myMap.wood.size(); j++){
                if(isInterse(myMap.wood.get(j).getX(), myMap.wood.get(j).getY(), Tank.bullet.get(i).x, Tank.bullet.get(i).y, MyMap.size, Tank.bullet.get(i).size)) {
                    drawExplode(Tank.bullet.get(i).x, Tank.bullet.get(i).y);
                    myMap.wood.get(j).blood -= Tank.bullet.get(i).power;
                    if(myMap.wood.get(j).blood <= 0)
                        myMap.wood.remove(j);
                    Tank.bullet.get(i).isLive = false;
                }
            }
            if(!Tank.bullet.get(i).isLive) {
                Tank.bullet.remove(i);
                continue;
            }
            for(int j = 0; j < myMap.grass.size(); j++){
                if(isInterse(myMap.grass.get(j).getX(), myMap.grass.get(j).getY(), Tank.bullet.get(i).x, Tank.bullet.get(i).y, MyMap.size, Tank.bullet.get(i).size)) {
                    drawExplode(Tank.bullet.get(i).x, Tank.bullet.get(i).y);
                    myMap.grass.get(j).blood -= Tank.bullet.get(i).power;
                    if(myMap.grass.get(j).blood <= 0) {
                        if(Tank.bullet.get(i).hero)
                            getFruit(MyMap.grass.get(j).getX(), MyMap.grass.get(j).getY());
                        myMap.grass.remove(j);
                    }
                    Tank.bullet.get(i).isLive = false;
                }
            }
            if(!Tank.bullet.get(i).isLive) {
                Tank.bullet.remove(i);
                continue;
            }
            for(int j = 0; j < myMap.stone.size(); j++){
                if(isInterse(myMap.stone.get(j).getX(), myMap.stone.get(j).getY(), Tank.bullet.get(i).x, Tank.bullet.get(i).y, MyMap.size, Tank.bullet.get(i).size)) {
                    drawExplode(Tank.bullet.get(i).x, Tank.bullet.get(i).y);
                    Tank.bullet.get(i).isLive = false;
                }
            }
            if(!Tank.bullet.get(i).isLive) {
                Tank.bullet.remove(i);
                continue;
            }
            g1.fillOval(Tank.bullet.get(i).x, Tank.bullet.get(i).y, Tank.bullet.get(i).size, Tank.bullet.get(i).size);
        }
    }
    public void drawExplode(int x, int y){
        for(int i = 0; i < 3; i++)
            g1.drawImage(explode[i], x, y, 30, 30, null);
        for(int i = 0; i < 16; i++)
            g1.drawImage(bome[i], x, y, 30, 30, null);
    }
    public boolean isInterse(int x1, int y1, int x2, int y2, int size1, int size2) {
            Rectangle r1 = new Rectangle(x1, y1, size1, size1);
            Rectangle r2 = new Rectangle(x2, y2, size2, size2);
            return r1.intersects(r2);
        }
    public void produceEnemyTank(){
        enemyTank.add(new EnemyTank());
    }
    @Override
    public synchronized void run() {
        while(true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(myBase.isLive() && heroTank.isLive && EnemyTank.changeDirection && countTank == 300 && sumTank < Constent.EnemyTankNumber) {
                sumTank++;
                produceEnemyTank();
                if(countTank  < Constent.EnemyTankNumber / 3)
                    countTank = 0;
                else if(countTank < Constent.EnemyTankNumber / 2)
                    countTank = 50;
                else
                    countTank = 100;
            }
            if(isEnd)
                break;
            countTank++;
        }
    }
    class Promotion{
        int x;
        int y;
        int type;
        int blood;
        int speed;
        int power;
        boolean isLive;
        public Promotion(){
            blood = 10;
            speed = -2;
            power = 4;
            isLive = true;
            Random random = new Random();
            type = random.nextInt(3);
        }
    }
    class SetEnd {
        int x;
        int y;
        boolean isPlay;
        long grade;
        int heartGrade;
        int mangoGrade;
        int pearGrade;
        int tankGrade;
        int deadTank;
        long startTime;
        long endTime;
        long time;
        public SetEnd(){
            x = 100;
            y = 100;
            deadTank = 0;
            isPlay = false;
            heartGrade = 6;
            mangoGrade = 7;
            pearGrade = 8;
            tankGrade = 10;
            startTime = System.currentTimeMillis();
        }

    }
}
