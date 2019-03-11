package MyTankGame;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MyOption extends JDialog{
    OptionImage optionImage1, optionImage2, optionImage3;
    JDialog j1;
    JLabel jL, jL1, jL2, jL3, jLe;
    JRadioButton jr1, jr2, jr3;
    //滑块设置坦克数量
    JSlider jsl;
    //显示坦克数量
    JTextField jtf;
    //是否选择
    JButton jb1, jb2;
    //选择地图
    JComboBox selectMap;
    ButtonGroup group;
    BufferedImage image = new BufferedImage(90, 60, BufferedImage.TYPE_INT_RGB);
    Graphics g1 = image.getGraphics();

    public MyOption(){
        j1 = new JDialog();
        j1.setLayout(null);
        j1.setBounds(350, 150, 400, 400);
        //设置该窗口打开后其他窗口锁住
        j1.setModalityType(ModalityType.APPLICATION_MODAL);

        //定义一个容器
        Container con = j1.getContentPane();
        jL = new JLabel("坦克类型");
        jL.setFont(new Font("楷体", Font.BOLD, 20));
        con.add(jL);
        jL.setBounds(20, 90, 100, 30);
        //定义单选框
        jr1 = new JRadioButton("重击型");
        jr2 = new JRadioButton("速度型");
        jr3 = new JRadioButton("综合型");
        con.add(jr1);
        con.add(jr2);
        con.add(jr3);
        jr1.setBounds(140, 30, 20, 20);
        jr2.setBounds(140, 90, 20, 20);
        jr3.setBounds(140, 150, 20, 20);
        //归组才能实现单选
        group = new ButtonGroup();
        group.add(jr1);
        group.add(jr2);
        group.add(jr3);
        //设置综合型坦克默认选中
        jr3.setSelected(true);
        //画出坦克类型图
        optionImage1 = new OptionImage(Tank.Tank1[0]);
        optionImage1.setBounds(200, 0, 90, 60);
        con.add(optionImage1);
        optionImage2 = new OptionImage(Tank.Tank2[0]);
        optionImage2.setBounds(200, 70, 90, 60);
        con.add(optionImage2);
        optionImage3 = new OptionImage(Tank.Tank3[0]);
        optionImage3.setBounds(200, 140, 90, 60);
        con.add(optionImage3);

        jL1 = new JLabel("重击型");
        jL2 = new JLabel("速度型");
        jL3 = new JLabel("综合型");
        con.add(jL1);
        con.add(jL2);
        con.add(jL3);
        jL1.setBounds(160, 25, 60, 20);
        jL2.setBounds(160, 87, 60, 20);
        jL3.setBounds(160, 147, 60, 20);

        jsl = new JSlider(20, 100, Constent.EnemyTankNumber);  //设置滑块设置的区域
        con.add(jsl);
        jsl.setBounds(160, 250, 160, 30);
        jsl.setPaintTicks(true);//设置滑块绘制刻度标记
        jsl.setMajorTickSpacing(10);//设置主刻度标记的间隔
        jsl.setMinorTickSpacing(2);
        jtf = new JTextField(String.valueOf(jsl.getValue()));
        jtf.setEditable(false);   //设置文本不可更改
        jsl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jtf.setText(String.valueOf(jsl.getValue()));
            }
        });
        con.add(jtf);    //数量显示
        jtf.setBounds(120, 245, 30, 30);

        jLe = new JLabel("敌方坦克数量");
        jLe.setFont(new Font("楷体", Font.BOLD, 15));
        con.add(jLe);
        jLe.setBounds(10, 245, 120, 30);

        jb1 = new JButton("确定");
        jb2 = new JButton("取消");
        con.add(jb1);
        con.add(jb2);
        jb1.setBounds(80, 300, 80, 40);
        jb2.setBounds(250, 300, 80, 40);

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jr1.isSelected()){
                    Constent.HeroTanktype = 1;
                }
                else if(jr2.isSelected())
                    Constent.HeroTanktype = 2;
                else
                    Constent.HeroTanktype = 3;
                Constent.EnemyTankNumber = jsl.getValue();
                if(selectMap.getSelectedItem() == "地图一")
                    Constent.whatMap = 1;
                else if(selectMap.getSelectedItem() == "地图二")
                    Constent.whatMap = 2;
                else
                    Constent.whatMap = 3;
                j1.dispose();
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                j1.dispose();
            }
        });
        selectMap = new JComboBox();
        selectMap.addItem("地图一");
        selectMap.addItem("地图二");
        selectMap.addItem("地图三");
        con.add(selectMap);
        selectMap.setBounds(305, 20, 70, 30);
    }

    class OptionImage extends JPanel{
        Image img;
        int x, y, width, height;
        public OptionImage(Image img){
            this.img = img;
            x = 0;
            y = 0;
            width = 90;
            height = 60;
        }
        public OptionImage(Image img, int width, int height){
            this.img = img;
            x = 0;
            y = 0;
            this.width = width;
            this.height = height;
        }
        public void paintComponent(Graphics g) {
            g1.drawImage(img, x, y, width, height, this);
            g.drawImage(image, x, y, width, height, this);
            repaint();
        }
    }

}
