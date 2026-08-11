package wxy251224;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.io.File;

public class FilterImage {
    public Graphics g;
    public BufferedImage bufferedImage;
    public String filterName;

    public FilterImage(Graphics g) {
        this.g = g;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void FilterImage(int[][] pixelArr, int pixelSize) {
        switch (filterName) {
            case "原图":
                drawPixel(pixelArr, pixelSize);
                break;
            case "灰度":
                gray(pixelArr, pixelSize);
                break;
            case "马赛克":
                mosaic(pixelArr, pixelSize);
                break;
            case "浮雕":
                emboss(pixelArr, pixelSize);
                break;
            case "二值化":
                binary(pixelArr, pixelSize);
                break;
            case "加亮度":
                addLight(pixelArr, pixelSize);
                break;
            case "减亮度":
                subLight(pixelArr, pixelSize);
                break;
            case "旋转":
                rotate(pixelArr, pixelSize);
                break;
            case "油画":
                oilPainting(pixelArr, pixelSize);
                break;
            case "底片":
                negative(pixelArr, pixelSize);
                break;
            case "怀旧":
                old(pixelArr, pixelSize);
                break;
            case "对比度":
                contrast(pixelArr, 1.5, pixelSize);
                break;
            case "卷积":
                Convolution(pixelArr, pixelSize);
                break;
            case"保存":
                Save(pixelArr, pixelSize);
                break;
        }
    }


    //绘制像素图像repaint
    public void drawPixel(int[][] pixelArr, int pixelSize) {
        //创建缓冲图片
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                Color color = new Color(red, green, blue);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);

            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //灰度
    public void gray(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                int gray = (red + green + blue) / 3;
                Color color = new Color(gray, gray, gray);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //马赛克
    //ArrayIndexOutOfBoundsException:数组下标越界
    public void mosaic(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i += 20) {
            for (int j = 0; j < pixelArr[i].length; j += 20) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                Color color = new Color(red, green, blue);
                g1.setColor(color);
                g1.fillRect(i, j, 20, 20);

            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //浮雕
    public void emboss(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length - 1; i++) {
            for (int j = 0; j < pixelArr[i].length - 1; j++) {
                int rgb = pixelArr[i][j];
                int rgbNext = pixelArr[i + 1][j + 1];
                int red = Math.abs(((rgb >> 16) & 0xff) - ((rgbNext >> 16) & 0xff) + 128);
                int green = Math.abs(((rgb >> 8) & 0xff) - ((rgbNext >> 8) & 0xff) + 128);
                int blue = Math.abs((rgb & 0xff) - (rgbNext & 0xff) + 128);
                // 限制RGB范围0-255
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));
                Color color = new Color(red, green, blue);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //二值化
    public void binary(int[][] pixelArr, int pixelSize) {
        //缓冲图片
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                int gray = (red + green + blue) / 3;
                if (gray > 128) {
                    Color color = new Color(255, 255, 255);
                    g1.setColor(color);
                    g1.drawLine(i, j, i, j);
                } else if (gray <= 128) {
                    Color color = new Color(0, 0, 0);
                    g1.setColor(color);
                    g1.drawLine(i, j, i, j);
                }
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //加亮度
    public void addLight(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                red = red + 50;
                green = green + 50;
                blue = blue + 50;
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));
                Color color = new Color(red, green, blue);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //减亮度
    public void subLight(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                red = red - 50;
                green = green - 50;
                blue = blue - 50;
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));
                Color color = new Color(red, green, blue);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //油画
    public void oilPainting(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        Random ran = new Random();//创建随机数
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int num = pixelArr[i][j];//获取像素
                Color color = new Color(num);//像素转颜色
                g1.setColor(color);//画笔颜色
                int w = ran.nextInt(10) + 5;//随机生成大小
                int h = ran.nextInt(10) + 5;//随机生成大小
                g1.fillOval(i, j, w, h);//绘制填充圆
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //旋转
    public int[][] rotate(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        // 创建新的像素数组存储旋转后的像素
        int[][] nArr = new int[pixelArr[0].length][pixelArr.length];
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[0].length; j++) {
                // 将原像素赋值到旋转后的新位置
                nArr[j][pixelArr.length - 1 - i] = pixelArr[i][j];
                Color color = new Color(nArr[j][pixelArr.length - 1 - i]);
                g1.setColor(color);
                g1.drawLine(j, pixelArr.length - 1 - i, j, pixelArr.length - 1 - i);

            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
        return nArr;
    }

    //底片
    public void negative(int[][] pixelArr, int pixelSize) {
        //  获取图片宽高
        int width = pixelArr.length;
        int height = pixelArr[0].length;
        // 创建缓冲图片
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        // 逐像素计算反色值
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;
                // 确保颜色值在0~255
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));
                Color color = new Color(red, green, blue);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //怀旧
    public void old(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                red = (int) (red * 0.393 + green * 0.769 + blue * 0.189);
                green = (int) (red * 0.349 + green * 0.686 + blue * 0.168);
                blue = (int) (red * 0.272 + green * 0.534 + blue * 0.131);
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));
                Color color = new Color(red, green, blue);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //对比度
    public void contrast(int[][] pixelArr, double contrast, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int rgb = pixelArr[i][j];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                red = (int) (128 + (red - 128) * contrast);
                green = (int) (128 + (green - 128) * contrast);
                blue = (int) (128 + (blue - 128) * contrast);
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));
                Color color = new Color(red, green, blue);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
    }

    //保存
    public void Save(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                int rgb = pixelArr[i][j];
                Color color = new Color(rgb);
                g1.setColor(color);
                g1.drawLine(i, j, i, j);
            }
        }
        String savePath = "C://Users//Amy//Desktop//保存图片.png";
        try {
            ImageIO.write(bufferedImage, savePath.substring(savePath.lastIndexOf(".")+1), new File(savePath));
            System.out.println("保存成功！");
        } catch (IOException e) {
            System.out.println("保存失败：" + e.getMessage());
        }
    }

    int [][] kernel = {
            {-1, -1, -1, -1, -1},
            {-1, -1,-1, -1, -1},
            {-1, -1, -1, -1, -1},
            {-1, -1, 36, -1, -1},
            {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1},

    };

    //卷积
    public int[][] Convolution(int[][] pixelArr, int pixelSize) {
        bufferedImage = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bufferedImage.getGraphics();
        //保存卷积后的图像数据
        int height = pixelArr.length - kernel.length + 1;
        int width = pixelArr[0].length - kernel[0].length + 1;
        //保存卷积后的图像数据
        int[][] convolutionArr = new int[height][width];
        int[][] typeArr = new int[kernel.length][kernel[0].length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int n = 0; n < kernel.length; n++) {
                    for (int m = 0; m < kernel[0].length; m++) {
                        typeArr[n][m]= kernel[n][m] * pixelArr[n+i][m+j];
                    }
                }
                int val = 0;
                for (int n = 0; n < typeArr.length; n++) {
                    for (int m = 0; m < typeArr[0].length; m++) {
                        val += typeArr[n][m];
                    }
                }
                if(val < 0)val=0;
                if (val > 255)val=255;
                convolutionArr[i][j] = val;
            }


        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = convolutionArr[i][j];
                Color color = new Color(rgb,rgb,rgb);
                g1.setColor(color);
                g1.drawRect(i,j,1,1);
            }
        }
        int newWidth = (bufferedImage.getWidth() + pixelSize);
        int newHeight = (bufferedImage.getHeight() + pixelSize);
        g.drawImage(bufferedImage, 20, 20, newWidth, newHeight, null);
        return convolutionArr;
    }

}
