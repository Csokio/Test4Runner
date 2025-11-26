package org.example.utils;

import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SectionScreenshot {

    public void takeDivScreenShot(WebDriver driver, By xpathSelector, String methodName){

        try {
            WebElement element = driver.findElement(xpathSelector);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            BufferedImage fullImg = ImageIO.read(screenshot);

            Point location = element.getLocation();
            Dimension size = element.getSize();

            BufferedImage elementImg;

            if(methodName.equals("testLogoutPageShutterbug")){
                elementImg = fullImg;
            }   else {
                elementImg = fullImg.getSubimage(location.getX(), location.getY(), size.getWidth(), size.getHeight());
            }


            ImageIO.write(elementImg, "png", new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_failed_tests\\" + methodName + ".png"));

            System.out.println("Failed Test's Screenshot taken and saved as: " + methodName);

        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            driver.quit();
        }*/

    }

    private BufferedImage safeSubImage(BufferedImage img, int x, int y, int width, int height) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        if (x + width > imgWidth) {
            width = imgWidth - x;
        }
        if (y + height > imgHeight) {
            height = imgHeight - y;
        }

        if (x < 0 || y < 0 || width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid subimage parameters");
        }

        return img.getSubimage(x, y, width, height);
    }

    /*public void takeDivScreenShot(WebDriver driver, By xpathSelector, String methodName) {
        try {
            WebElement element = driver.findElement(xpathSelector);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            BufferedImage fullImg = ImageIO.read(screenshot);

            Point location = element.getLocation();
            Dimension size = element.getSize();

            BufferedImage elementImg;

            if (methodName.equals("testLogoutPageShutterbug")) {
                elementImg = fullImg;
            } else {
                elementImg = safeSubImage(
                        fullImg,
                        location.getX(),
                        location.getY(),
                        size.getWidth(),
                        size.getHeight()
                );
            }

            ImageIO.write(
                    elementImg,
                    "png",
                    new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_failed_tests\\" + methodName + ".png")
            );

            System.out.println("Failed Test's Screenshot taken and saved as: " + methodName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }


    private BufferedImage safeSubImage(BufferedImage img, int x, int y, int width, int height) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        if (x + width > imgWidth) {
            width = imgWidth - x;
        }
        if (y + height > imgHeight) {
            height = imgHeight - y;
        }

        if (x < 0 || y < 0 || width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid subimage parameters");
        }

        return img.getSubimage(x, y, width, height);
    }*/

}
