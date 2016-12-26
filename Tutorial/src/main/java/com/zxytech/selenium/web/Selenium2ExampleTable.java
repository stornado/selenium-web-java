package com.zxytech.selenium.web;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.ErrorHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ryan on 2016/12/26.
 */
public class Selenium2ExampleTable {
    public static void main(String[] args) {
        WebDriver driver = loginMiguManage("http://localhost:8080/manage/login", "user", "passwd");
        driver = navigateToCoverList(driver);
//        traverseTheTable(driver);
        selectCoverType(driver);
    }

    /**
     * 登录咪咕爱唱管理系统
     */
    private static WebDriver loginMiguManage(String url, String user, String passwd) {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.findElement(By.id("loginname")).sendKeys(user);
        driver.findElement(By.name("j_password")).sendKeys(passwd);
        driver.findElement(By.cssSelector("body > dl > dd > form > p.submit > button")).submit();
        return driver;
    }

    /**
     * 跳转到作品列表
     */
    private static WebDriver navigateToCoverList(WebDriver driver) {
        driver.findElement(By.linkText("作品管理")).click();
//        driver.findElement(By.xpath("/html/body/div[2]/ul/li[1]/ul/li[7]/a"));
        driver.findElement(By.cssSelector("body > div.left-menu > ul > li:nth-child(1) > ul > li:nth-child(7) > a"));
        return driver;
    }

    /**
     * 遍历作品列表
     */
    private static void traverseTheTable(WebDriver driver) {
        List<WebElement> covers = driver.findElements(By.cssSelector("#tableList > tbody > tr"));
        // 遍历作品列表元素
        for (WebElement cover : covers) {
            String label = cover.findElement(By.xpath("./td[2]/label")).getText();
            String poster = cover.findElement(By.xpath("./td[3]/img")).getAttribute("src");
            String opusType = cover.findElement(By.xpath("./td[5]")).getText();
            String resourceType = cover.findElement(By.xpath("./td[6]")).getText();
            String coverType = cover.findElement(By.xpath("./td[7]")).getText();
            String nickname = cover.findElement(By.xpath("./td[10]")).getText();
            String hot = cover.findElement(By.xpath("./td[11]")).getText();
            String playCount = cover.findElement(By.xpath("./td[12]")).getText();
            String flowerCount = cover.findElement(By.xpath("./td[13]")).getText();
            String commentCount = cover.findElement(By.xpath("./td[14]")).getText();
            String tags = cover.findElement(By.xpath("./td[15]")).getText();
            String createTime = cover.findElement(By.xpath("./td[16]")).getText();
            String approvalStatus = cover.findElement(By.xpath("./td[17]")).getText();
            System.out.println("label: " + label + ", poster: " + poster + ", opusType: " + opusType + ", resourceType: " + resourceType + ", coverType: " + coverType + ", nickname: " + nickname + ", hot: " + hot + ", playCount: " + playCount + ", flowerCount: " + flowerCount + ", commentCount: " + commentCount + ", tags: " + tags + ", createTime: " + createTime + ", approvalStatus: " + approvalStatus);
        }
    }

    /**
     * 选择作品类型
     *
     * @link http://stackoverflow.com/questions/16166261/selenium-webdriver-how-to-resolve-stale-element-reference-exception
     */
    private static void selectCoverType(WebDriver driver) {
        WebElement select = driver.findElement(By.cssSelector("#action_form > select:nth-child(7)"));
        List<WebElement> allOptions = select.findElements(By.tagName("option"));
//        for (WebElement option : allOptions) {
//            try {
//                System.out.println("Option Text: " + option.getText() + ", Value: " + option.getAttribute("value"));
//                option.click();
//                driver.findElement(By.cssSelector("#action_form > button.btn")).click();
//            } catch (StaleElementReferenceException e) {
//                e.printStackTrace();
//                continue;
//            }
//            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//            // 遍历列表
////            traverseTheTable(driver);
//        }


        for (int i = 0; i < allOptions.size(); i++) {
            try {
                WebElement option = driver.findElement(By.cssSelector("#action_form > select:nth-child(7)")).findElement(By.xpath(String.format("./option[%s]", i + 1)));
                System.out.println("Option Text: " + option.getText() + ", Value: " + option.getAttribute("value"));
                option.click();
                driver.findElement(By.cssSelector("#action_form > button.btn")).submit();
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                continue;
            }
            // 等待页面刷新
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            traverseTheTable(driver);
        }
        driver.close();
    }
}
