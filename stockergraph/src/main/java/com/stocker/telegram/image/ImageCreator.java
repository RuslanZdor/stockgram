package com.stocker.telegram.image;

import com.stocker.symbol.Company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class ImageCreator {

    public static File createShowStock(Company company) throws IOException {
        File input = new File("C:\\ruslan_zdor\\stocker\\java-duke.png");
        File output = new File("C:\\ruslan_zdor\\stocker\\duke-text-watermarked.jpg");

        BufferedImage image = ImageIO.read(input);
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        w.setColor(Color.GREEN);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        FontMetrics fontMetrics = w.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(company.getSymbol(), w);

        // calculate center of the image
        int centerX = (image.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = image.getHeight() / 2;

        // add text overlay to the image
        w.drawString(company.getSymbol(), centerX, centerY);
        ImageIO.write(watermarked, "png", output);
        w.dispose();
        return output;
    }
}
