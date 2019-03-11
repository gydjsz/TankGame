
package MyTankGame;
import java.awt.*;
import java.util.Random;

public class EnemyTank extends Tank implements Runnable{
    static Image EnemyImage[];
    Thread thread;
    int count = 0;
    int size = 40;
    boolean up, down, left, right;
    static boolean changeDirection;
    int bulletCount = 0;
    boolean isOk;
    boolean canLive;
    int countDirection = 0;
    public EnemyTank(){
        x = Constent.EnemyTankPlaceX;
        y = Constent.EnemyTankPlaceY;
        speed = 3;
        direction = 1;
        power = 7;
        totalBlood = blood = 40;
        isOk = true;
        canLive = true;
        changeDirection = true;
        EnemyImage = Tank.Tank4;
        thread = new Thread(this);
        thread.start();
    }

    public void TankRun(){
        Random random = new Random();
        countDirection++;
        if(countDirection < 3)
            direction = 2;
        else if(countDirection < 20)
            direction = random.nextInt(2) + 1;
        else if(countDirection < 30)
            direction = random.nextInt(random.nextInt(3) + 1);
        else
            direction = random.nextInt(4);
        switch (direction){
            case 0: {
                up = true;
                down = false;
                left = false;
                right = false;
            }
            break;
            case 1:{
                up = false;
                down = true;
                left = false;
                right = false;
            }
            break;
            case 2:{
                up = false;
                down = false;
                left = true;
                right = false;
            }
            break;
            case 3:{
                up = false;
                down = false;
                left = false;
                right = true;
            }
            break;
        }
    }
    public boolean isInterse(int x1, int y1, int x2, int y2) {
        Rectangle r1 = new Rectangle(x1, y1, MyMap.size, MyMap.size);
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
        int x2 = TankFight.heroTank.x, y2 = TankFight.heroTank.y;
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
                flag = 0;
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
                flag = 0;
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
                flag = 0;
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
                flag = 0;
            }
            else{
                flag = 1;
            }
        }
        for(int i = 0; i < TankFight.enemyTank.size(); i++) {
            x1 = TankFight.enemyTank.get(i).x;
            y1 = TankFight.enemyTank.get(i).y;
            if (this != TankFight.enemyTank.get(i)) {
                if (up && y > 0) {
//                    if(isInterse(x1, y1, x, y, 0)) {
//                        return false;
//                    }
                    if (isInterse(x1, y1, x, y - speed, 0)) {
                        y = y1 + size - 5;
                        flag = 0;
                    } else if (isInterse(x2, y2, x, y - speed, 0)) {
                        y = y2 + size - 5;
                        flag = 0;
                    } else {
                        flag = 1;
                    }
                }

                if (down && y < Constent.height - deviationDown) {
//                    if(isInterse(x1, y1, x, y, 0)) {
//                        return false;
//                    }
                    if (isInterse(x1, y1, x, y + speed, 0)) {
                        y = y1 - size + 5;
                        flag = 0;
                    } else if (isInterse(x2, y2, x, y + speed, 0)) {
                        y = y2 - size + 5;
                        flag = 0;
                    } else {
                        flag = 1;
                    }
                }
                if (left && x > 0) {
//                    if(isInterse(x1, y1, x, y, 0)) {
//                        return false;
//                    }
                    if (isInterse(x1, y1, x - speed, y, 0)) {
                        x = x1 + size - 5;
                        flag = 0;
                    } else if (isInterse(x2, y2, x - speed, y, 0)) {
                        x = x2 + size - 5;
                        flag = 0;
                    } else {
                        flag = 1;
                    }
                }
                if (right && x < Constent.width - deviationRight) {
//                    if(isInterse(x1, y1, x, y, 0)) {
//                        return false;
//                    }
                    if (isInterse(x1, y1, x + speed, y, 0)) {
                        x = x1 - size + 5;
                        flag = 0;
                    } else if (isInterse(x2, y2, x + speed, y, 0)) {
                        x = x2 - size + 5;
                        flag = 0;
                    } else {
                        flag = 1;
                    }
                }
            }
        }
        for(int i = 0; i < MyMap.wood.size(); i++){
            x1 = MyMap.wood.get(i).getX();
            y1 = MyMap.wood.get(i).getY();
            if(up && y > 0){
                if(isInterse(x1, y1, x, y - speed)){
                    y = y1 + MyMap.size + 4;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }

            if(down && y < Constent.height - deviationDown){
                if(isInterse(x1, y1, x, y + speed)){
                    y = y1 - size + 5;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
            if(left && x > 0){
                if(isInterse(x1, y1, x - speed, y)){
                    x = x1 + MyMap.size + 4;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
            if(right && x < Constent.width - deviationRight){
                if(isInterse(x1, y1, x + speed, y)){
                    x = x1 - size + 5;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
        }
        for(int i = 0; i < MyMap.grass.size(); i++){
            x1 = MyMap.grass.get(i).getX();
            y1 = MyMap.grass.get(i).getY();
            if(up && y > 0){
                if(isInterse(x1, y1, x, y - speed)){
                    y = y1 + MyMap.size + 4;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }

            if(down && y < Constent.height - deviationDown){
                if(isInterse(x1, y1, x, y + speed)){
                    y = y1 - size + 5;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
            if(left && x > 0){
                if(isInterse(x1, y1, x - speed, y)){
                    x = x1 + MyMap.size + 4;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
            if(right && x < Constent.width - deviationRight){
                if(isInterse(x1, y1, x + speed, y)){
                    x = x1 - size + 5;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
        }
        for(int i = 0; i < MyMap.stone.size(); i++){
            x1 = MyMap.stone.get(i).getX();
            y1 = MyMap.stone.get(i).getY();
            if(up && y > 0){
                if(isInterse(x1, y1, x, y - speed)){
                    y = y1 + MyMap.size + 4;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }

            if(down && y < Constent.height - deviationDown){
                if(isInterse(x1, y1, x, y + speed)){
                    y = y1 - size + 5;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
            if(left && x > 0){
                if(isInterse(x1, y1, x - speed, y)){
                    x = x1 + MyMap.size + 4;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
            if(right && x < Constent.width - deviationRight){
                if(isInterse(x1, y1, x + speed, y)){
                    x = x1 - size + 5;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
        }
        for(int i = 0; i < MyMap.water.size(); i++){
            x1 = MyMap.water.get(i).getX();
            y1 = MyMap.water.get(i).getY();
            if(up && y > 0){
                if(isInterse(x1, y1, x, y - speed)){
                    y = y1 + MyMap.size + 4;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }

            if(down && y < Constent.height - deviationDown){
                if(isInterse(x1, y1, x, y + speed)){
                    y = y1 - size + 5;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
            if(left && x > 0){
                if(isInterse(x1, y1, x - speed, y)){
                    x = x1 + MyMap.size + 4;
                    flag = 0;
                }
                else{
                    flag = 1;
                }
            }
            if(right && x < Constent.width - deviationRight){
                if(isInterse(x1, y1, x + speed, y)){
                    x = x1 - size + 5;
                    flag = 0;
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
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(changeDirection && count == 30) {
                TankRun();
                count = 0;
            }
            if(changeDirection) {
                WhereToMove();
                count++;
                bulletCount++;
            }
            if(changeDirection && bulletCount == 50){
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
                b.power = 4;
                b.speed = 5;
                b.dirction =  direction;
                b.hero = false;
                b.thread.start();
                bullet.add(b);
                bulletCount = 0;
            }
            if(blood <= 0 || canLive == false)
                break;
        }
    }
}
