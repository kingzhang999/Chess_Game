package ImageDispose;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageResize {
    public static void main(String[] args) {
        File folder = new File("D:\\java_work\\Chess_Game\\resource");
        File[] listOfFiles = folder.listFiles();//listFiles()方法是File类的一个方法，返回一个包含文件夹中所有文件的数组。
        for (File file : listOfFiles) {//isFile()方法是File类的一个方法，判断是否为文件。
            if (file.isFile() && !file.getName().equals("white.jpg") && !file.getName().equals("black.jpg")) {
                try {
                    BufferedImage image = ImageIO.read(file);//BufferedImage是Java AWT包中的一个类，表示一个可修改的图像。
                    Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    /*getScaledInstance()方法是Image接口的一个方法，用于缩放图像。
                      50和50是目标宽度和高度。
                      Image.SCALE_SMOOTH是缩放算法之一，表示平滑缩放。*/
                    BufferedImage resizedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
                    //BufferedImage.TYPE_INT_RGB是图像类型之一，表示使用8位RGB颜色模型。
                    resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
                    //getGraphics()方法是BufferedImage类的一个方法，获取图像的图形上下文。
                    //drawImage()方法是Graphics类的一个方法，用于在图像上绘制另一个图像。
                    File output = new File("D:\\java_work\\Chess_Game\\resource\\" + file.getName());
                    ImageIO.write(resizedImage, "jpg", output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
