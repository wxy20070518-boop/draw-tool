package wxy251224;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import static java.awt.SystemColor.text;

/**
 * 实现接口:public class 子类 implements 接口,接口,,{}
 */
public class PixelListener implements ActionListener, MouseWheelListener {
    private Graphics g;
    FilterImage filter;
    private int[][] pixelArr;
    private int[][] nArr;

    int pixelSize = 100;
    private JPanel centerPanel;
    public void setG(Graphics g) {
        this.g = g;
        filter = new FilterImage(g);
    }
    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }


    //重写事件处理方法
    public void actionPerformed(ActionEvent e) {

        System.out.println("点击按钮！");
        //获取按钮的文本
        String text = e.getActionCommand();
        String path = "C:\\Users\\Amy\\Desktop\\img.jpg";
        //加载图片
        pixelArr = getPixel(new File( path));
        if (text.equals("原图")) {
            filter.drawPixel(pixelArr, pixelSize);
            filter.setFilterName("原图");
        } else if (text.equals("灰度")) {
            filter.gray(pixelArr, pixelSize);
            filter.setFilterName("灰度");
        } else if (text.equals("马赛克")) {
            filter.mosaic(pixelArr, pixelSize);
            filter.setFilterName("马赛克");
        } else if (text.equals("浮雕")) {
            filter.emboss(pixelArr, pixelSize);
            filter.setFilterName("浮雕");
        } else if (text.equals("二值化")) {
            filter.binary(pixelArr, pixelSize);
            filter.setFilterName("二值化");
        } else if (text.equals("加亮度")) {
            filter.addLight(pixelArr, pixelSize);
            filter.setFilterName("加亮度");
        } else if (text.equals("减亮度")) {
            filter.subLight(pixelArr, pixelSize);
            filter.setFilterName("减亮度");
        } else if (text.equals("旋转")) {
            if (nArr != null) {
                nArr = filter.rotate(nArr, pixelSize);
                filter.FilterImage(nArr, pixelSize);
            } else {
                nArr = filter.rotate(pixelArr, pixelSize);
                filter.FilterImage(nArr, pixelSize);
            }
        } else if (text.equals("油画")) {
            filter.oilPainting(pixelArr, pixelSize);
            filter.setFilterName("油画");
        } else if (text.equals("底片")) {
            filter.negative(pixelArr, pixelSize);
            filter.setFilterName("底片");
        } else if (text.equals("怀旧")) {
            filter.old(pixelArr, pixelSize);
            filter.setFilterName("怀旧");
        } else if (text.equals("对比度")) {
            filter.contrast(pixelArr, 1.5, pixelSize);
            filter.setFilterName("对比度");
        }else if (text.equals("打开")) {
            JFileChooser jfc = new JFileChooser();
            FileNameExtensionFilter filefilter = new FileNameExtensionFilter("*.jpg & png Image", "jpg", "png");
            jfc.setFileFilter(filefilter);
            jfc.showOpenDialog(null);
            File  file = jfc.getSelectedFile();
            pixelArr = getPixel(file);
            filter.drawPixel(pixelArr, pixelSize);
        }else if (text.equals("卷积")) {
            filter.Convolution(pixelArr, pixelSize);
            filter.setFilterName("卷积");
        } else if (text.equals("保存")) {
            filter.Save(pixelArr, pixelSize);
            filter.setFilterName("保存");
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotate = e.getWheelRotation();
        centerPanel.paint(g);
        if (rotate > 0) {
            //向下
            pixelSize += 20;
        } else {
            //向上
            pixelSize -= 20;
        }
        // filter.drawPixel(pixelArr,pixelSize);
        int newWidth = (filter.bufferedImage.getWidth() + pixelSize);
        int newHeight = (filter.bufferedImage.getHeight() + pixelSize);
        if (newHeight<0){
            newHeight=0;
        }if(newWidth<0){
            newWidth=0;
        }
        filter.g.drawImage(filter.bufferedImage, 50, 50, newWidth, newHeight, null);
        System.out.println("pixelSize=" + pixelSize);
    }

    //读取指定路径上的图片数据
    //I/O流：读写磁盘上的数据
    public int[][] getPixel(File  file) {

        //读取图片数据，并返回缓冲图片
        BufferedImage bufferedImage ;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[][] pixelArr = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //获取像素点
                int pixel = bufferedImage.getRGB(i, j);
                pixelArr[i][j] = pixel;
            }
        }
        return pixelArr;
    }
}
