package cn.com.huateng.web.util;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * Author: 韩纳威
 * Date: 13-8-21
 * Time: 下午5:36
 */
public class ImageUtil {
    public final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    private File file = null; // 文件对象
    private String inputDir; // 输入图路径
    private String outputDir; // 输出图路径
    private String inputFileName; // 输入图文件名
    private String outputFileName; // 输出图文件名
    private int outputWidth = 100; // 默认输出图片宽
    private int outputHeight = 100; // 默认输出图片高
    private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)
    /**
     * 图片格式：JPG
     */
    private static final String PICTRUE_FORMATE_JPG = "jpg";

    /***/
    public ImageUtil() { // 初始化变量
        inputDir = "";
        outputDir = "";
        inputFileName = "";
        outputFileName = "";
        outputWidth = 100;
        outputHeight = 100;
    }


    /**
     * @return the inputDir
     */
    public String getInputDir() {
        return inputDir;
    }


    /**
     * @param inputDir the inputDir to set
     */
    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }


    /**
     * @return the outputDir
     */
    public String getOutputDir() {
        return outputDir;
    }


    /**
     * @param outputDir the outputDir to set
     */
    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }


    /**
     * @return the inputFileName
     */
    public String getInputFileName() {
        return inputFileName;
    }


    /**
     * @param inputFileName the inputFileName to set
     */
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }


    /**
     * @return the outputFileName
     */
    public String getOutputFileName() {
        return outputFileName;
    }


    /**
     * @param outputFileName the outputFileName to set
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }


    /**
     * @return the outputWidth
     */
    public int getOutputWidth() {
        return outputWidth;
    }


    /**
     * @param outputWidth the outputWidth to set
     */
    public void setOutputWidth(int outputWidth) {
        this.outputWidth = outputWidth;
    }


    /**
     * @return the outputHeight
     */
    public int getOutputHeight() {
        return outputHeight;
    }


    /**
     * @param outputHeight the outputHeight to set
     */
    public void setOutputHeight(int outputHeight) {
        this.outputHeight = outputHeight;
    }


    /**
     * 获得图片大小
     *
     * @param path 图片路径
     * @return <br><b>作者： wmchen</b>
     *         <br>创建时间：2012-11-19 上午9:48:36
     * @since 2012-11-19
     */
    public long getPicSize(String path) {
        file = new File(path);
        return file.length();
    }


    /**
     * 图片处理
     *
     * @return <br><b>作者： wmchen</b>
     *         <br>创建时间：2012-11-19 上午9:49:10
     * @since 2012-11-19
     */
    public String compressPic() {
        try {
            logger.info("压缩图片开始。。。。。");
            //获得源文件
            file = new File(inputDir + inputFileName);
            if (!file.exists()) {
                return "";
            }
            Image img = ImageIO.read(file);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                logger.info(" can't read,retry!" + "<BR>");
                return "no";
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (this.proportion) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                    logger.info("计算压缩率。。。。。。");
                } else {
                    newWidth = outputWidth; // 输出的图片宽度
                    newHeight = outputHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
                 * 优先级比速度高 生成的图片质量比较好 但速度慢
                 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST), 0, 0, null);
                logger.info("图片压缩完成");
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);
                // JPEGImageEncoder可适用于其他图片类型的转换
                //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                //encoder.encode(tag);
                out.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "ok";
    }

    public InputStream compressPic1(MultipartFile file,int width,int heigth)throws Exception{
        Image img = ImageIO.read(file.getInputStream());
        // 判断图片格式是否正确
        outputWidth=width;
        outputHeight=heigth;
        if (img.getWidth(null) == -1) {
            logger.info(" can't read,retry!" + "<BR>");
            return null;
        } else {
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (this.proportion) {
                // 为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;
                double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;
                // 根据缩放比率大的进行缩放控制
                double rate = rate1 > rate2 ? rate1 : rate2;
                newWidth = (int) (((double) img.getWidth(null)) / rate);
                newHeight = (int) (((double) img.getHeight(null)) / rate);
                logger.info("计算压缩率。。。。。。");
            } else {
                newWidth = outputWidth; // 输出的图片宽度
                newHeight = outputHeight; // 输出的图片高度
            }
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
                 * 优先级比速度高 生成的图片质量比较好 但速度慢
                 */
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST), 0, 0, null);
            boolean flag = ImageIO.write(tag, "jpg", out);
            logger.info("图片压缩完成");
            // JPEGImageEncoder可适用于其他图片类型的转换
            logger.info("is outstream null? {}" ,out == null? "yes": "no");
            InputStream inputStream=new ByteArrayInputStream(out.toByteArray());
            return inputStream;
        }
      }

    /**
     * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
     *
     * @param inputDir       输入图路径
     * @param outputDir      输出图路径
     * @param inputFileName  输入图文件名
     * @param outputFileName 输出图文件名
     * @param width          图片宽
     * @param height         图片长
     * @param gp             是否等比缩放
     */
    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {
        // 输入图路径
        this.inputDir = inputDir;
        // 输出图路径
        this.outputDir = outputDir;
        // 输入图文件名
        this.inputFileName = inputFileName;
        // 输出图文件名
        this.outputFileName = outputFileName;
        // 设置图片长宽
        this.outputWidth = width;

        this.outputHeight = height;

        // 是否是等比缩放 标记
        this.proportion = gp;
        return compressPic();
    }

    public static InputStream pressText1(InputStream inputStream,String pressText, String fontName, int fontStyle, int fontSize, Color color, float alpha, Integer degree){
        try{
            Image image = ImageIO.read(inputStream);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) bufferedImage.getWidth() / 2, (double) bufferedImage
                        .getHeight() / 2);
            }
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            int width_1 = fontSize * getLength(pressText);
            int widthDiff = width - width_1;
            int heightDiff = height - fontSize;
            g.drawString(pressText, widthDiff / 2, heightDiff / 2);
            g.dispose();
            //ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(bufferedImage, "jpg", out);
            InputStream input=new ByteArrayInputStream(out.toByteArray());
            return input;
        }catch (Exception e){
            logger.error("compress image fail ",e.getMessage());
            return inputStream;
        }

    }

    /**
     * 添加文字水印
     *
     * @param targetImg 目标图片路径
     * @param pressText 水印文字
     * @param fontName  字体名称
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize  字体大小，单位为像素
     * @param color     字体颜色
     * @param alpha     透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, float alpha, Integer degree) {
        try {
            File file = new File(targetImg);
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) bufferedImage.getWidth() / 2, (double) bufferedImage
                        .getHeight() / 2);
            }
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            int width_1 = fontSize * getLength(pressText);
            int widthDiff = width - width_1;
            int heightDiff = height - fontSize;
            g.drawString(pressText, widthDiff / 2, heightDiff / 2);
            g.dispose();
            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     *
     * @param text 字符串
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }
}
