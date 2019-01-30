package com.cinatic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class ImageHelper {
	public static String getTextFromImage(BufferedImage img, boolean sharpen, boolean blur) {
		ITesseract instance = new Tesseract();
		instance.setDatapath(LoadLibs.extractTessResources("tessdata").getAbsolutePath());

		if (sharpen)
			img = setSharpen(img);

		if (blur)
			img = setBlur(img);

		String text = "";
		try {
			text = instance.doOCR(img);
		} catch (TesseractException e) {			
			e.printStackTrace();
		}
		return text;
	}

	public static BufferedImage setSharpen(BufferedImage raw) {
		BufferedImage img = new BufferedImage(raw.getWidth(), raw.getHeight(), raw.getType());
		float data[] = { -1.0f, -1.0f, -1.0f, -1.0f, 9.0f, -1.0f, -1.0f, -1.0f, -1.0f };
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		convolve.filter(raw, img);
		return img;
	}

	public static BufferedImage setBlur(BufferedImage raw) {
		BufferedImage img = new BufferedImage(raw.getWidth(), raw.getHeight(), raw.getType());
		float data[] = { 0.1625f, 0.125f, 0.1625f, 0.125f, 0.25f, 0.125f, 0.1625f, 0.125f, 0.1625f };
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		convolve.filter(raw, img);
		return img;
	}

	public static BufferedImage setEdgeDetect(BufferedImage raw) {
		BufferedImage img = new BufferedImage(raw.getWidth(), raw.getHeight(), raw.getType());
		float data[] = { 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f };
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		convolve.filter(raw, img);
		return img;
	}

	public static BufferedImage setBlackAndWhite(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();

		int precision = 50;
		precision = (0 <= precision && precision <= 100) ? precision : 50;

		int limit = 255 * precision / 100;

		for (int i = 0, j; i < w; ++i) {
			for (j = 0; j < h; ++j) {
				Color color = new Color(img.getRGB(i, j));
				if (limit <= color.getRed() || limit <= color.getGreen() || limit <= color.getBlue()) {
					img.setRGB(i, j, Color.WHITE.getRGB());
				} else {
					img.setRGB(i, j, Color.BLACK.getRGB());
				}
			}
		}
		return img;
	}

	public static BufferedImage setGrayscale(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;
				int avg = (r + g + b) / 3;
				p = (a << 24) | (avg << 16) | (avg << 8) | avg;
				img.setRGB(x, y, p);
			}
		}
		return img;
	}

	public static BufferedImage setAlpha(BufferedImage raw, Color colorExpected, int diffstart, int diffend) {
		int WIDTH = raw.getWidth();
		int HEIGHT = raw.getHeight();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		int pixels[] = new int[WIDTH * HEIGHT];
		raw.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		for (int i = 0; i < pixels.length; i++) {
			if (!(pixels[i] >= colorExpected.getRGB() + diffstart && pixels[i] <= colorExpected.getRGB() + diffend)) {
				pixels[i] = 0x00ffffff;
			}
		}
		image.setRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		return image;
	}

	public static BufferedImage correctImage(BufferedImage raw) {
		int startX = -1;
		int startY = -1;
		int endX = -1;
		int endY = -1;

		int WIDTH = raw.getWidth();
		int HEIGHT = raw.getHeight();

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (raw.getRGB(x, y) == Color.black.getRGB()) {
					if (startX == -1 || x < startX)
						startX = x;
					if (startY == -1 || y < startY)
						startY = y;
					if (endX == -1 || x > endX)
						endX = x;
					if (endY == -1 || y > endY)
						endY = y;
				}
			}
		}

		BufferedImage image = new BufferedImage(endX - startX + 1, endY - startY + 1, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (raw.getRGB(x, y) == Color.black.getRGB()) {
					image.setRGB(x - startX, y - startY, Color.black.getRGB());
				}
			}
		}

		return image;
	}
}
