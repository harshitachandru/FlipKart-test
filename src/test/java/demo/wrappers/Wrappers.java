package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static void enterText(WebElement locator, String text) {
        try {

            locator.click();
            locator.clear();
            locator.sendKeys(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void movetotheelementtoclick(ChromeDriver driver, WebElement locator) {
        try {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", locator);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressenter(WebElement locator) {
        try {

            locator.sendKeys(Keys.ENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<WebElement> getElements(ChromeDriver driver, By locator) {
        return driver.findElements(locator);
    }

    public static int count(ChromeDriver driver, By locator) {
        List<WebElement> ratingnumbers = getElements(driver, locator);
        int lessrating = 0;

        for (WebElement ratingnumber : ratingnumbers) {
            try {
                double rating = Double.parseDouble(ratingnumber.getText());
                if (rating <= 4) {
                    lessrating++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lessrating;
    }

    public static void printtitleanddiscount(List<WebElement> titles, List<WebElement> discounts) {

        int minSize = Math.min(titles.size(), discounts.size());

        for (int i = 0; i < minSize; i++) {
            // if(i >= discounts.size())
            // {
            // System.out.println("Error: Discount list is smaller than the title list.");
            // break;
            // }

            String titlevalue = titles.get(i).getText();
            String discountnumber = discounts.get(i).getText().replace("% off", "").trim();

            int discountnum = Integer.parseInt(discountnumber);

            System.out.println("Processing title: " + titlevalue + " with discount: " + discountnum);

            if (discountnum > 17) {
                System.out.println("The Title is : " + titlevalue + ", Discount for it is : " + discountnum + "%");
            }
        }
    }

    public static List<Map<String, String>> getTopNReviewedItems(WebDriver driver, By reviewBy, int topN, By titleBy, By imageBy) {
        List<WebElement> reviewElements = driver.findElements(reviewBy);
        List<WebElement> titleElements = driver.findElements(titleBy);
        List<WebElement> imageElements = driver.findElements(imageBy);

        List<Map<String, String>> itemsWithReviews = new ArrayList<>();

        for (int i = 0; i < reviewElements.size(); i++) {
            Map<String, String> itemDetails = new HashMap<>();
            String reviewText = reviewElements.get(i).getText();
            String title = titleElements.get(i).getText();
            String imageUrl = imageElements.get(i).getAttribute("src");

            itemDetails.put("title", title);
            itemDetails.put("imageUrl", imageUrl);
            itemDetails.put("reviewCount", extractNumericValue(reviewText) + "");
            itemsWithReviews.add(itemDetails);
        }

        return itemsWithReviews.stream()
                .sorted((item1, item2) -> Integer.compare(
                        Integer.parseInt(item2.get("reviewCount")),
                        Integer.parseInt(item1.get("reviewCount"))
                ))
                .limit(topN)
                .collect(Collectors.toList());
    }

    public static int extractNumericValue(String text) {
        String numericPart = text.replaceAll("[^\\d]", "");
        return Integer.parseInt(numericPart);
    }

    public static void printTitlesAndImageURLs(List<Map<String, String>> items) {
        for (Map<String, String> item : items) {
            String title = item.get("title");
            String imageUrl = item.get("imageUrl");
            String reviewCount = item.get("reviewCount");
            System.out.println("Title: " + title + ", Image URL: " + imageUrl + ", Review Count: " + reviewCount);
        }
    }
    

    // public static List<WebElement> getTopNReviewedItems(ChromeDriver driver, By reviewBy, int topN) {
    //     List<WebElement> reviewElements = driver.findElements(reviewBy);

    //     return reviewElements.stream().sorted((e1, e2) -> {
    //         try {
    //             int reviews1 = Integer.parseInt(e1.getText().replaceAll("[^0-9]", ""));
    //             int reviews2 = Integer.parseInt(e2.getText().replaceAll("[^0-9]", ""));
    //             return Integer.compare(reviews2, reviews1);

    //         } catch (Exception e) {
    //             return 0;
    //         }
    //     }).limit(topN).collect(Collectors.toList());
    // }

    // public static void printTitlesAndImageURLs(List<WebElement> elements, By titleBy, By imageBy) {
    //     for (WebElement element : elements) {
    //         String title = element.findElement(titleBy).getText();
    //         String imageUrl = element.findElement(imageBy).getAttribute("src");

    //         // String reviewCountText = element.findElement(reviewBy).getText();
    //         // int reviewCount = extractNumericValue(reviewCountText);
    //         System.out.println("Title: " + title + ", Image URL: " + imageUrl);
    //     }
    // }
    // private static int extractNumericValue(String text) {
    //     // Extract numbers from text, handling various formats
    //     String numericPart = text.replaceAll("[^\\d]", ""); // Remove non-numeric characters
    //     return Integer.parseInt(numericPart);
    // }
}
