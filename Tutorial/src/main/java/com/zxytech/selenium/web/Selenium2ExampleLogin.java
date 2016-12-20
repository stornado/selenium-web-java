package com.zxytech.selenium.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by ryan on 2016/12/20.
 */
public class Selenium2ExampleLogin {
    public static void main(String[] args) {
        // 使用FireFox切换tab不成功
        final WebDriver webDriver = new ChromeDriver();

        webDriver.get("https://www.baidu.com/");
        // 等待新标签页面加载
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // 百度首页输入框
        // <input type="text" class="s_ipt" name="wd" id="kw" maxlength="100" autocomplete="off">
        WebElement searchInput = webDriver.findElement(By.name("wd"));

        searchInput.sendKeys("四川大学本科教务系统");

        // 百度一下按钮
        // <input type="submit" value="百度一下" id="su" class="btn self-btn bg s_btn">
        WebElement submit = webDriver.findElement(By.cssSelector("#su"));
        submit.click();

        System.out.println("当前标签页面标题：" + webDriver.getTitle());

        // 等待搜索页面加载
        (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.getTitle().toLowerCase().startsWith("四川大学本科教务系统");
            }
        });
        System.out.println("当前标签页面标题：" + webDriver.getTitle());

        // 搜索结果第一项
        // #\31 > h3 > a:nth-child(1) > em
        // //*[@id="1"]/h3/a[1]/em
        WebElement firstResult = webDriver.findElement(By.xpath("//*[@id=\"1\"]/h3/a[1]/em"));
        firstResult.click();
        System.out.println("当前标签页面标题：" + webDriver.getTitle());

        // 等待新标签页面加载
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // 切换到标签页面
        Set<String> windowHandles = webDriver.getWindowHandles();
        List<String> windowHandleList = new ArrayList<String>(windowHandles);

        System.out.println("所有标签页标识：");
        for (String tab : windowHandleList) {
            System.out.println("Tab: " + tab);
        }

        String newTab = windowHandleList.get(windowHandleList.size() - 1);
        webDriver.switchTo().window(newTab);
        System.out.println("NewTAB: " + newTab);

        // 等待新标签页面加载
        (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.getTitle().toLowerCase().startsWith("四川大学本科教务系统");
            }
        });

        System.out.println("当前标签页面标题：" + webDriver.getTitle());

        // 输入账号密码
        webDriver.findElement(By.name("zjh")).sendKeys("username");
        webDriver.findElement(By.name("mm")).sendKeys("passwd");
        webDriver.findElement(By.id("btnSure")).submit();

        // 检查登录结果
        System.out.println("当前标签页面标题：" + webDriver.getTitle());

        // 关闭浏览器
//        webDriver.close();
    }
}
