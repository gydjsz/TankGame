package MyTankGame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HeroTank extends Tank {
    Image tank[];
    SetKeyRun setKeyRun;
    int size = 40;
    int shotSpeed, shotCount = 0;
    boolean canShot;
    int Live;
    boolean isLive = true;
    boolean changeDirection;
    boolean success;
    public HeroTank()
    {
        init();
    }
    public void init(){
        x = 0;
        y = 0;
        success = false;
        type = Constent.HeroTanktype;
        speed = 3;
        direction = 0;
        isLive = true;
        canShot = true;
        Live = 3;
        if(type == 1) {
            tank = Tank.Tank1;
            totalBlood = blood = 30;
            power = 10;
            Constent.Speed = 40;
            shotSpeed = 4;
        }
        if(type == 2) {
            tank = Tank.Tank2;
            totalBlood = blood = 40;
            power = 7;
            Constent.Speed = 25;
            shotSpeed = 3;
        }
        if(type == 3) {
            tank = Tank.Tank3;
            totalBlood = blood = 35;
            power = 8;
            Constent.Speed = 30;
            shotSpeed = 2;
        }
        setKeyRun = new SetKeyRun();
        changeDirection = true;

    }

    class SetKeyRun implements KeyListener, Runnable {
        MyMap myMap;
        Thread thread;
        boolean up, down, left, right;

        public SetKeyRun() {
            myMap = new MyMap();
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(changeDirection) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: {
                        direction = 0;
                        up = true;
                        down = false;
                        left = false;
                        right = false;
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        direction = 1;
                        up = false;
                        down = true;
                        left = false;
                        right = false;
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        direction = 2;
                        up = false;
                        down = false;
                        left = true;
                        right = false;
                    }
                    break;
                    case KeyEvent.VK_RIGHT: {
                        direction = 3;
                        up = false;
                        down = false;
                        left = false;
                        right = true;
                    }
                    break;
                }
                shotCount++;
                if (canShot && shotCount == shotSpeed && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Bullet b = new Bullet();
                    if(direction == 0) {
                        b.x = x + 10;
                        b.y = y;
                    }
                    if(direction == 1){
                        b.x = x + 10;
                        b.y = y + size;
                    }
                    if(direction == 2){
                        b.x = x;
                        b.y = y + 10;
                    }
                    if(direction == 3){
                        b.x = x + size;
                        b.y = y + 10;
                    }
                    b.isLive = true;
                    b.power = 5;
                    b.speed = 5;
                    b.dirction = direction;
                    b.hero = true;
                    b.thread.start();
                    bullet.add(b);
                    canShot = false;
                }
                if (shotCount == shotSpeed)
                    shotCount = 0;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: {
                    up = false;
                }
                break;
                case KeyEvent.VK_DOWN: {
                    down = false;
                }
                break;
                case KeyEvent.VK_LEFT: {
                    left = false;
                }
                break;
                case KeyEvent.VK_RIGHT: {
                    right = false;
                }
                break;
                case KeyEvent.VK_SPACE:{
                    canShot = true;
                }
                break;
            }
        }

        public boolean isInterse(int x1, int y1, int x2, int y2) {
            Rectangle r1 = new Rectangle(x1, y1, myMap.size, myMap.size);
            Rectangle r2 = new Rectangle(x2, y2, size, size);
            return r1.intersects(r2);
        }

        public boolean isInterse(int x1, int y1, int x2, int y2, int n){
            if(n == 0)
                n = size;
            Rectangle r1 = new Rectangle(x1, y1, n, n);
            Rectangle r2 = new Rectangle(x2, y2, size, size);
            return r1.intersects(r2);
        }
        public boolean WhereToMove() {
            int x1, y1;
            int x2 = 0, y2 = 0;
            int flag = 0;
            int deviationRight = 52;
            int deviationDown = 100;
            int size = MyMap.size;
            x1 = MyBase.getX();
            y1 = MyBase.getY();
            if(up && y > 0){
                if(isInterse(x1, y1, x, y, MyBase.getSize())) {
                    return false;
                }
                if(isInterse(x1, y1, x, y - speed, 0)){
                    y = y1 + MyBase.getSize();
                }
                else{
                    flag = 1;
                }
            }

            if(down && y < Constent.height - deviationDown){
                if(isInterse(x1, y1, x, y, MyBase.getSize())) {
                    return false;
                }
                if(isInterse(x1, y1, x, y + speed, MyBase.getSize())){
                    y = y1 - size+ 5;
                }
                else{
                    flag = 1;
                }
            }
            if(left && x > 0){
                if(isInterse(x1, y1, x, y, MyBase.getSize())) {
                    return false;
                }
                if(isInterse(x1, y1, x - speed, y, MyBase.getSize())){
                    x = x1 + MyBase.getSize() + 5;
                }
                else{
                    flag = 1;
                }
            }
            if(right && x < Constent.width - deviationRight){
                if(isInterse(x1, y1, x, y, MyBase.getSize())) {
                    return false;
                }
                if(isInterse(x1, y1, x + speed, y, MyBase.getSize())){
                    x = x1 - size + 5;
                }
                else{
                    flag = 1;
                }
            }
            for(int i = 0; i < TankFight.enemyTank.size(); i++){
                x1 = TankFight.enemyTank.get(i).x;
                y1 = TankFight.enemyTank.get(i).y;
                if(up && y > 0){
                    if(isInterse(x1, y1, x, y, 0)) {
                        return false;
                    }
                    if(isInterse(x1, y1, x, y - speed, 0)){
                        y = y1 + size - 5;
                    }
                    else{
                        flag = 1;
                    }
                }

                if(down && y < Constent.height - deviationDown){
                    if(isInterse(x1, y1, x, y, 0)) {
                        return false;
                    }
                    if(isInterse(x1, y1, x, y + speed, 0)){
                        y = y1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(left && x > 0){
                    if(isInterse(x1, y1, x, y, 0)) {
                        return false;
                    }
                    if(isInterse(x1, y1, x - speed, y, 0)){
                        x = x1 + size - 5;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(right && x < Constent.width - deviationRight){
                    if(isInterse(x1, y1, x, y, 0)) {
                        return false;
                    }
                    if(isInterse(x1, y1, x + speed, y, 0)){
                        x = x1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
            }
            for(int i = 0; i < myMap.wood.size(); i++){
                x1 = myMap.wood.get(i).getX();
                y1 = myMap.wood.get(i).getY();
                if(up && y > 0){
                    if(isInterse(x1, y1, x, y - speed)){
                        y = y1 + myMap.size + 4;
                    }
                    else{
                        flag = 1;
                    }
                }

                if(down && y < Constent.height - deviationDown){
                    if(isInterse(x1, y1, x, y + speed)){
                        y = y1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(left && x > 0){
                    if(isInterse(x1, y1, x - speed, y)){
                        x = x1 + myMap.size + 4;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(right && x < Constent.width - deviationRight){
                    if(isInterse(x1, y1, x + speed, y)){
                        x = x1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
            }
            for(int i = 0; i < myMap.grass.size(); i++){
                x1 = myMap.grass.get(i).getX();
                y1 = myMap.grass.get(i).getY();
                if(up && y > 0){
                    if(isInterse(x1, y1, x, y - speed)){
                        y = y1 + myMap.size + 4;
                    }
                    else{
                        flag = 1;
                    }
                }

                if(down && y < Constent.height - deviationDown){
                    if(isInterse(x1, y1, x, y + speed)){
                        y = y1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(left && x > 0){
                    if(isInterse(x1, y1, x - speed, y)){
                        x = x1 + myMap.size + 4;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(right && x < Constent.width - deviationRight){
                    if(isInterse(x1, y1, x + speed, y)){
                        x = x1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
            }
            for(int i = 0; i < myMap.stone.size(); i++){
                x1 = myMap.stone.get(i).getX();
                y1 = myMap.stone.get(i).getY();
                if(up && y > 0){
                    if(isInterse(x1, y1, x, y - speed)){
                        y = y1 + myMap.size + 4;
                    }
                    else{
                        flag = 1;
                    }
                }

                if(down && y < Constent.height - deviationDown){
                    if(isInterse(x1, y1, x, y + speed)){
                        y = y1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(left && x > 0){
                    if(isInterse(x1, y1, x - speed, y)){
                        x = x1 + myMap.size + 4;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(right && x < Constent.width - deviationRight){
                    if(isInterse(x1, y1, x + speed, y)){
                        x = x1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
            }
            for(int i = 0; i < myMap.water.size(); i++){
                x1 = myMap.water.get(i).getX();
                y1 = myMap.water.get(i).getY();
                if(up && y > 0){
                    if(isInterse(x1, y1, x, y - speed)){
                        y = y1 + myMap.size + 4;
                    }
                    else{
                        flag = 1;
                    }
                }

                if(down && y < Constent.height - deviationDown){
                    if(isInterse(x1, y1, x, y + speed)){
                        y = y1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(left && x > 0){
                    if(isInterse(x1, y1, x - speed, y)){
                        x = x1 + myMap.size + 4;
                    }
                    else{
                        flag = 1;
                    }
                }
                if(right && x < Constent.width - deviationRight){
                    if(isInterse(x1, y1, x + speed, y)){
                        x = x1 - size + 5;
                    }
                    else{
                        flag = 1;
                    }
                }
            }
            if(up && flag == 1)
                y -= speed;
            if(down && flag == 1)
                y += speed;
            if(left && flag == 1)
                x -= speed;
            if(right && flag == 1)
                x += speed;
            return true;
        }

        @Override
        public synchronized void run() {
            while (true) {
                try {
                    WhereToMove();
                    Thread.sleep(Constent.Speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isLive == false){
                    if(Live > 0) {
                        x = 0;
                        y = 0;
                        type = Constent.HeroTanktype;
                        blood = totalBlood;
                        power = 4;
                        speed = 3;
                        direction = 0;
                        isLive = true;
                    }
                    else
                        break;
                }
            }
        }
    }
}
