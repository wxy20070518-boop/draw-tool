package wxy251224;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class PixeUI {
    //属性

    //方法：显示界面程序
    public void initUI(){
        //窗体
        JFrame jf = new JFrame();//创建窗体
        //像素点->分辨率
        jf.setSize(1000,1000);//设置窗体大小
        jf.setTitle("图像处理1.0");
        jf.setLocationRelativeTo(null);//居中显示
        jf.setDefaultCloseOperation(3);
        //功能面板
        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.gray);
        northPanel.setPreferredSize(new Dimension(500,70));
        jf.add(northPanel,BorderLayout.NORTH);
        PixelListener listener;
        listener = new PixelListener();
        //图像面板
        MPanel centerPanel = new MPanel();
        centerPanel.setFocusable(true);
        centerPanel.requestFocus();
        centerPanel.addMouseWheelListener(listener);
        jf.add(centerPanel, BorderLayout.CENTER);
        //给图像面板添加滚轮监听器
        centerPanel.addMouseWheelListener(listener);
        jf.add(centerPanel,BorderLayout.CENTER);
        String[]name ={"打开" ,"保存","原图","马赛克","灰度","浮雕","二值化","加亮度","减亮度","旋转","油画","底片","怀旧","对比度","卷积"};
        for (int i=0;i<name.length;i++) {
            JButton button = new JButton(name[i]);
            northPanel.add(button);
            button.addActionListener(listener);
        }
        jf.setVisible(true);
        Graphics g = centerPanel.getGraphics();
        listener.setG(g);
        listener.setCenterPanel(centerPanel);
        System.out.println();
        centerPanel.setFilterImage(listener.filter);


    }

    public static void main(String[] args) {
        PixeUI ui = new PixeUI();
        ui.initUI();

    }
}
