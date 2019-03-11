package MyTankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MyGame extends JFrame implements MouseListener, ActionListener, KeyListener {
    MyJPanel myJPanel;
    static ArrayList<TankFight> tankFight;
    boolean isStart = false;
    MyOption myOption;
    //菜单
    JMenuBar jmb;
    JMenu jm;
    JMenuItem itemMain, itemStart, itemStop, itemRecovery, itemExit;
    Container con = getContentPane();
    public MyGame(){
        setTitle("My Game");
        setLocation(80, 0);
        setSize(Constent.width, Constent.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        myOption = new MyOption();
        myJPanel = new MyJPanel();
        addMouseListener(this);
        add(myJPanel);
        setVisible(true);
        tankFight = new ArrayList<TankFight>();
        addKeyListener(this);
        addMenu();
        jmb.setVisible(false);
    }
    public void addMenu(){
        jmb = new JMenuBar();
        jm = new JMenu("菜单");
        itemMain = new JMenuItem("主面板");
        itemMain.setActionCommand("主面板");
        itemStart = new JMenuItem("重新开始");
        itemStart.setActionCommand("重新开始");   //设置命令
        itemStop = new JMenuItem("暂停");
        itemStop.setActionCommand("暂停");
        itemRecovery = new JMenuItem("恢复");
        itemRecovery.setActionCommand("恢复");
        itemExit = new JMenuItem("退出");
        itemExit.setActionCommand("退出");
        jm.add(itemMain);
        jm.add(itemStart);
        jm.add(itemStop);
        jm.add(itemRecovery);
        jm.add(itemExit);
        jmb.add(jm);
        setJMenuBar(jmb);
        itemMain.addActionListener(this);
        itemStart.addActionListener(this);
        itemStop.addActionListener(this);
        itemRecovery.addActionListener(this);
        itemExit.addActionListener(this);
    }
    public static void main(String args[]){
        MyGame myGame = new MyGame();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(!isStart && e.getX() > 367 && e.getX() < 777 && e.getY() > 318 && e.getY() < 387){
            jmb.setVisible(true);
            myJPanel.setVisible(false);
            remove(myJPanel);
            TankFight tank = new TankFight();
            add(tank);
            addKeyListener(tank.heroTank.setKeyRun);
            tankFight.add(tank);
            isStart = true;
            try {
                Thread.sleep(60);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while(i <= Constent.windowWidth - Constent.width){
                        setSize(Constent.width + i++, Constent.height);
                    }
                }
            });
            thread.start();
//            myOption.j1.dispose();
        }
        if(!isStart && e.getX() > 367 && e.getX() < 777 && e.getY() > 558 && e.getY() < 625){
            myOption.j1.setVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "主面板": {
                removeKeyListener(TankFight.heroTank.setKeyRun);  //移除旧的键盘监听
                tankFight.get(0).setVisible(false);
                tankFight.remove(0);
                jmb.setVisible(false);
                myJPanel = new MyJPanel();
                add(myJPanel);
                myJPanel.setVisible(true);
                isStart = false;
                TankFight.isEnd = true;
                for (int i = 0; i < TankFight.enemyTank.size(); i++) {
                    TankFight.enemyTank.get(i).canLive = false;
                }

                int i = 0;
                while (i <= Constent.windowWidth - Constent.width) {
                    setSize(Constent.windowWidth - i++, Constent.height);
                }
            }
            break;
            case "重新开始": {
                removeKeyListener(TankFight.heroTank.setKeyRun);  //移除旧的键盘监听
                tankFight.get(0).setVisible(false);
                tankFight.remove(0);
                for (int i = 0; i < TankFight.enemyTank.size(); i++)
                    TankFight.enemyTank.get(i).canLive = false;
                TankFight tank = new TankFight();
                add(tank);
                addKeyListener(tank.heroTank.setKeyRun);
                tankFight.add(tank);
            }
            break;
            case "暂停": {
                tankFight.get(0).heroTank.speed = 0;
                tankFight.get(0).heroTank.changeDirection = false;
                EnemyTank.changeDirection = false;
                for (int i = 0; i < Tank.bullet.size(); i++) {
                    Tank.bullet.get(i).speed = 0;
                }
            }
            break;
            case "恢复": {
                tankFight.get(0).heroTank.changeDirection = true;
                tankFight.get(0).heroTank.speed = 3;
                for (int i = 0; i < TankFight.enemyTank.size(); i++) {
                    TankFight.enemyTank.get(i).changeDirection = true;
                }
                for (int i = 0; i < Tank.bullet.size(); i++) {
                    Tank.bullet.get(i).speed = 5;
                }
            }
            break;
            case "退出": {
                System.exit(0);
            }
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(TankFight.isEnd && e.getKeyCode() == KeyEvent.VK_ENTER){
            removeKeyListener(TankFight.heroTank.setKeyRun);  //移除旧的键盘监听
            tankFight.get(0).setVisible(false);
            tankFight.remove(0);
            jmb.setVisible(false);
            myJPanel = new MyJPanel();
            add(myJPanel);
            myJPanel.setVisible(true);
            isStart = false;
            TankFight.isEnd = false;
            for (int i = 0; i < TankFight.enemyTank.size(); i++)
                TankFight.enemyTank.get(i).canLive = false;
            int i = 0;
            while(i <= Constent.windowWidth - Constent.width){
                setSize(Constent.windowWidth - i++, Constent.height);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
