package com.lmk.springsecuritydemo.open.service.impl;

import com.lmk.springsecuritydemo.open.service.IOpenService;
import com.lmk.springsecuritydemo.utils.ImageCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static com.lmk.springsecuritydemo.utils.ImageCodeConstants.*;

@Service
public class OpenServiceImpl implements IOpenService {

    /**
     * 生成图形验证码
     * @param request
     * @return
     */
    @Override
    public ImageCode generate(ServletWebRequest request) {

        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", WIDTH);
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", HEIGHT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));// 设置字符字体、粗细、大小
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        int length = ServletRequestUtils.getIntParameter(request.getRequest(), "length", RANDOM_SIZE);
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 10, 25);// 调整字符出现在图片的位置
        }

        g.dispose();

        return new ImageCode(image, sRand, EXPIRE_SECOND);
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
