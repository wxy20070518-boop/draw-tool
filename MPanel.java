package wxy251224;

import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;

public class MPanel extends JPanel {
    private FilterImage filter;
    public void setFilterImage(FilterImage filter){
        this.filter  = filter;
    }


    //重写paint方法
    public void paint(Graphics g){
        super.paint(g);
        System.out.println("paint... bufferedImage="+filter.bufferedImage);
        //增加绘制图像功能
        g.drawImage(filter.bufferedImage,0,0,null);

    }
}
