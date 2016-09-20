
package cn.com.huateng.web.controller.api;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.huateng.util.BeanUtils;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 密码输入软件键盘生成类
 *
 * @author QIAOYU
 */
@Controller
@RequestMapping("/api/softKeyBoard")
public class SoftKeyBoard {

    private static final long serialVersionUID = 5513707232426355236L;

    private static Logger logger = Logger.getLogger(SoftKeyBoard.class);

    @Value("#{app.softKeyBoardImgPath}")
    private String softKeyBoardImgPath;

    @Value("#{app.mainSite}")
    private String mainSite;

    @Getter
    @Setter
    private Color backColor;

    @Getter
    @Setter
    private int fontSize;

    @Getter
    @Setter
    private int fontType;

    @Getter
    @Setter
    private Color frontColor;

    @RequestMapping(value = "/getRdmPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getRdmPwd(HttpServletRequest request) {
        Map<String, String> setResult;
        String pwd = (String) request.getSession().getAttribute("pwd");
        if(!Strings.isNullOrEmpty(pwd)){
            setResult = ImmutableMap.of("status", "ok", "pwd",pwd);
        } else
        {
            setResult = ImmutableMap.of("status", "fail", "msg","");
        }

        return setResult;

    }


    @RequestMapping(value = "/pwGeneration", method = RequestMethod.GET)
    public String pwGeneration(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session;


        session = request.getSession();

        response.setContentType("image/jpeg");

        String data = "0123456789";

        String rdm = BeanUtils.data2Random(data);

        session.removeAttribute("pwd");

        session.setAttribute("pwd", rdm);

        BufferedImage img;

        OutputStream out = null;

        try {

            out = response.getOutputStream();
            String localUrl = "http://"+mainSite + softKeyBoardImgPath;
            URL imgUrl = new URL(localUrl);

            img = ImageIO.read(imgUrl);

            if (img != null) {

                Graphics2D g = img.createGraphics();

                Graphics2D g1 = img.createGraphics();

                Graphics2D g2 = img.createGraphics();

                this.setFontSize(20);

                this.setFontType(0);

                this.setFrontColor(new Color(33, 33, 33));

                this.setBackColor(Color.BLACK);

                g1.setColor(frontColor);

                g1.setFont(new Font("宋体", Font.BOLD, 14));

                g2.setColor(frontColor);

                g2.setFont(new Font("宋体", Font.BOLD, 30));

                g.setColor(frontColor);

                g.setFont(new Font("宋体", Font.BOLD, fontSize));

                int i;

                /** 第一行4个数字 */
                for (i = 0; i < 4; i++) {

                    g.drawString(rdm.substring(i, i + 1), 23 + i * 31, 45);

                }

                /** 第二行4个数字 */
                for (i = 0; i < 4; i++) {

                    g.drawString(rdm.substring(i + 4, i + 4 + 1), 23 + i * 31, 76);

                }

                /** 第三行2和数字和清除和关闭按钮 */
                for (i = 0; i < 2; i++) {

                    g.drawString(rdm.substring(i + 8, i + 8 + 1), 23 + i * 31, 107);

                }

                ImageIO.write(img, "JPEG", out);
            }

        } catch (FileNotFoundException e) {

            logger.error(e);

        } catch (IOException e) {

            logger.error(e);

        } finally {

            if (out != null) {

                try {

                    out.close();

                } catch (IOException e) {

                    logger.error(e.getMessage());

                }
            }
        }

        return null;
    }


}