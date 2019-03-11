package MyTankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyJPanel extends JPanel {

    Image startGame = GameUtil.getImage("TankImage/startGame.jpg");
//    Image coursor = GameUtil.getImage("TankImage/logo.jpg");
    //初始化缓冲区
    BufferedImage imag = new BufferedImage(Constent.width, Constent.height, BufferedImage.TYPE_INT_RGB);
    Graphics g1 = imag.getGraphics();


    public MyJPanel(){
        //设置鼠标指针样式
//        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(coursor,new Point(0, 0), null));
    }
    @Override
    public void paint(Graphics g) {
        //游戏背景图
        g1.drawImage(startGame, 0, 0, Constent.width, Constent.height, null);
        //设置字体
        g1.setFont(new Font("楷体", Font.BOLD, 100));
        g1.setColor(Color.BLUE);
        g1.drawString("开始游戏", 350,350);
        g1.drawString("游戏设置", 350, 600);
        //将缓冲区的内容画到窗口上
        g.drawImage(imag, 0, 0, Constent.width, Constent.height, null);
        repaint();
    }
}

