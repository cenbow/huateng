package cn.com.huateng.web.controller.api;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import com.google.common.io.Closeables;
import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.color.RandomColorFactory;
import org.patchca.filter.ConfigurableFilterFactory;
import org.patchca.filter.library.AbstractImageOp;
import org.patchca.filter.library.WobbleImageOp;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
* Author: jlchen
* Date: 2012-11-23
*/
@Controller
@RequestMapping("/api/captcha")
public class CaptchaGenerator {
    private final static Logger log = LoggerFactory.getLogger(CaptchaGenerator.class);

    public static final String CAPTCHA_TOKEN = "captchaToken";
    public static final String CAPTCHA_TOKEN_SMS = "captchaTokenSms";
    private final ConfigurableCaptchaService configurableCaptchaService;

    public CaptchaGenerator() {
        configurableCaptchaService = new ConfigurableCaptchaService();

        // 颜色创建工厂,使用一定范围内的随机色
        ColorFactory colorFactory = new RandomColorFactory();
        configurableCaptchaService.setColorFactory(colorFactory);

        // 随机字体生成器
        RandomFontFactory fontFactory = new RandomFontFactory();
        fontFactory.setMaxSize(24);
        fontFactory.setMinSize(20);
        configurableCaptchaService.setFontFactory(fontFactory);

        // 随机字符生成器,去除掉容易混淆的字母和数字,如o和0等
        RandomWordFactory wordFactory = new RandomWordFactory();
        wordFactory.setCharacters("abcdefghkmnpqstwxyzABCDEFGHKMNPQSTWXYZ23456789");
        wordFactory.setMaxLength(5);
        wordFactory.setMinLength(4);
        configurableCaptchaService.setWordFactory(wordFactory);

        // 自定义验证码图片背景
        MyCustomBackgroundFactory backgroundFactory = new MyCustomBackgroundFactory();
        configurableCaptchaService.setBackgroundFactory(backgroundFactory);

        // 图片滤镜设置
        ConfigurableFilterFactory filterFactory = new ConfigurableFilterFactory();

        List<BufferedImageOp> filters = new ArrayList<BufferedImageOp>();
        WobbleImageOp wobbleImageOp = new WobbleImageOp();
        wobbleImageOp.setEdgeMode(AbstractImageOp.EDGE_MIRROR);
        wobbleImageOp.setxAmplitude(2.0);
        wobbleImageOp.setyAmplitude(1.0);
        filters.add(wobbleImageOp);
        filterFactory.setFilters(filters);

        configurableCaptchaService.setFilterFactory(filterFactory);

        // 文字渲染器设置
        TextRenderer textRenderer = new BestFitTextRenderer();
        textRenderer.setBottomMargin(3);
        textRenderer.setTopMargin(3);
        configurableCaptchaService.setTextRenderer(textRenderer);

        // 验证码图片的大小
        configurableCaptchaService.setWidth(54);
        configurableCaptchaService.setHeight(30);
    }

    /**
     * 生成base64编码的验证码图片,可以直接输出
     * @param request http request
     * @return  base64编码的验证码图片
     */
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String captcha(HttpServletRequest request) {
        // create the text for the image
        // 得到验证码对象,有验证码图片和验证码字符串
        Captcha captcha = configurableCaptchaService.getCaptcha();
        // 取得验证码字符串放入Session
        String token = captcha.getChallenge();

        request.getSession().setAttribute(CAPTCHA_TOKEN, token);
        // 生成取得验证码图片
        BufferedImage bufferedImage = captcha.getImage();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {

            ImageIO.write(bufferedImage, "png", output);
            return "data:image/png;base64,"+BaseEncoding.base64().encode(output.toByteArray());
        } catch (Exception e) {
            log.error("failed to generate captcha",e);
            throw new RuntimeException("generate captcha failed",e);
        }finally {
            try {
                Closeables.close(output,true);
            } catch (IOException e) {
                //ignore
            }
        }
    }

    /**
     * 生成验证码图片
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public void getCaptcha(HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("cache", "no-cache");
        // create the text for the image
        // 得到验证码对象,有验证码图片和验证码字符串
        Captcha captcha = configurableCaptchaService.getCaptcha();
        // 取得验证码字符串放入Session
        String token = captcha.getChallenge();

        request.getSession().setAttribute(CAPTCHA_TOKEN, token);

        // 生成取得验证码图片
        BufferedImage bufferedImage = captcha.getImage();
        try {
            OutputStream output = response.getOutputStream();
            ImageIO.write(bufferedImage, "png", output);
            output.flush();
            output.close();
        } catch (Exception e){
            log.error("failed to generate captcha");
            throw new RuntimeException("generate captcha failed",e);
        }
    }

    /**
     * 生成验证码图片(所有跟短信有关的业务调用此方法)，会保存到session里两个值，两个不同的key,对应一个相同的value,
     * 其中一个用来发送短信验证，发送短信后就会销毁
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public void getCaptchaWithSms(HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("cache", "no-cache");
        // create the text for the image
        // 得到验证码对象,有验证码图片和验证码字符串
        Captcha captcha = configurableCaptchaService.getCaptcha();
        // 取得验证码字符串放入Session
        String token = captcha.getChallenge();
        //这一对key-value用于正常的业务验证
        request.getSession().setAttribute(CAPTCHA_TOKEN, token);
        //这一对key-value专门用于发送短信时的验证，发送短信时就会销毁
        request.getSession().setAttribute(CAPTCHA_TOKEN_SMS, token);
        // 生成取得验证码图片
        BufferedImage bufferedImage = captcha.getImage();
        try {
            OutputStream output = response.getOutputStream();
            ImageIO.write(bufferedImage, "png", output);
            output.flush();
            output.close();
        } catch (Exception e){
            log.error("failed to generate captcha");
            throw new RuntimeException("generate captcha failed",e);
        }
    }

    /**
     * 供页面ajax判断验证码是否匹配,这里不移除session中的token,在controller中还要再次判断,防止被绕过
     * @param request http request
     * @return  是否匹配
     */
    @RequestMapping(value="/match",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean isMatch(HttpServletRequest request,String actualToken){
        if(Strings.isNullOrEmpty(actualToken)){
            return false;
        }
        String tokenKey = CAPTCHA_TOKEN;
        String expectedToken = (String) request.getSession().getAttribute(tokenKey);
        return null != expectedToken && Objects.equal(actualToken.toLowerCase(), expectedToken.toLowerCase());
    }

    /**
     * 判断captcha是否匹配
     * @param request http request
     * @param actualToken  用户输入的token
     * @return  是否匹配
     */
    public static boolean matches(HttpServletRequest request,String actualToken) {
        if(Strings.isNullOrEmpty(actualToken)){
            return false;
        }
        String tokenKey = CAPTCHA_TOKEN;
        String expectedToken = (String) request.getSession().getAttribute(tokenKey);

        if (null == request.getSession().getAttribute(tokenKey)){
            return false;
        }
        //when expectedToken retrieved ,it should be removed
        request.getSession().removeAttribute(tokenKey);
        return Objects.equal(actualToken.toLowerCase(),expectedToken.toLowerCase());
    }

    /**
     * 判断captcha是否匹配(短信用)
     * @param request http request
     * @param actualToken  用户输入的token
     * @return  是否匹配
     */
    public static boolean matchesSms(HttpServletRequest request,String actualToken) {
        if(Strings.isNullOrEmpty(actualToken)){
            return false;
        }
        String tokenKey = CAPTCHA_TOKEN_SMS;
        String expectedToken = (String) request.getSession().getAttribute(tokenKey);
        if (null == request.getSession().getAttribute(tokenKey)){
            return false;
        }
        //when expectedToken retrieved ,it should be removed
        request.getSession().removeAttribute(tokenKey);
        return Objects.equal(actualToken.toLowerCase(),expectedToken.toLowerCase());
    }

    /**
     * 自定义验证码图片背景,主要画一些噪点和干扰线
     */
    private class MyCustomBackgroundFactory implements BackgroundFactory {
        private Random random = new Random();

        public void fillBackground(BufferedImage image) {
            Graphics graphics = image.getGraphics();

            // 验证码图片的宽高
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            // 填充为白色背景
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, imgWidth, imgHeight);

            // 画100个噪点(颜色及位置随机)
            for(int i = 0; i < 100; i++) {
                // 随机颜色
                int rInt = random.nextInt(255);
                int gInt = random.nextInt(255);
                int bInt = random.nextInt(255);

                graphics.setColor(new Color(rInt, gInt, bInt));

                // 随机位置
                int xInt = random.nextInt(imgWidth - 3);
                int yInt = random.nextInt(imgHeight - 2);

                // 随机旋转角度
                int sAngleInt = random.nextInt(360);
                int eAngleInt = random.nextInt(360);

                // 随机大小
                int wInt = random.nextInt(6);
                int hInt = random.nextInt(6);

                graphics.fillArc(xInt, yInt, wInt, hInt, sAngleInt, eAngleInt);

                if (i % 20 == 0) {
                    int xInt2 = random.nextInt(imgWidth);
                    int yInt2 = random.nextInt(imgHeight);
                    graphics.drawLine(xInt, yInt, xInt2, yInt2);
                }
            }
        }
    }
    
    //生成的图片证验证码返回，不能用于页面显示
    public String registcaptcha(HttpServletRequest request) {
        // create the text for the image
        // 得到验证码对象,有验证码图片和验证码字符串
        Captcha captcha = configurableCaptchaService.getCaptcha();
        // 取得验证码字符串放入Session
        String token = captcha.getChallenge();

        request.getSession().setAttribute(CAPTCHA_TOKEN, token);
        return token;
     
    }
    

}
