package com.example.company.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证码
 */
public class CaptchaUtil {
	private static final Logger logger = LoggerFactory.getLogger(CaptchaUtil.class);
	
	private ByteArrayInputStream image;
	private String chkcode;
	private static int codeCount = 4;
	private static int lineCount = 200;
	private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
			'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };


	private void init() {
		int width = 75, height = 24;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < lineCount; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		String sRand = "";
		for (int i = 0; i < codeCount; i++) {
			String rand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			rotateString(g, rand, 16 * i + 6, 18);
		}
		this.chkcode = sRand;
		g.dispose();
		ByteArrayInputStream input = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close();
			input = new ByteArrayInputStream(output.toByteArray());
		} catch (Exception e) {
			logger.error("验证码图片产生出现错误：" + e.toString());
		}

		this.image = input;
	}
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		
		return new Color(r, g, b);
	}
	private void rotateString(Graphics g, String s, int x, int y) {
		int degree = new Random().nextInt() % 30;
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.rotate(degree * Math.PI / 180, x, y);
		g2d.drawString(s, x, y);
	}
	private CaptchaUtil() {
		init();
	}
	public static CaptchaUtil Instance() {
		return new CaptchaUtil();
	}
	public ByteArrayInputStream getImage() {
		return this.image;
	}
	public String getChkcode() {
		return this.chkcode;
	}

}
