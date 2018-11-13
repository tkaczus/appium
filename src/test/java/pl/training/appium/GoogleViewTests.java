package pl.training.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static pl.training.appium.DriverProvider.googleNewsCapabilities;

public class GoogleViewTests {

    private AndroidDriver<AndroidElement> driver = new DriverProvider().get(googleNewsCapabilities());

    @Before
    public void beforeTest() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void afterTest() {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("Screenshot.jpg"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        driver.closeApp();
    }

    @Test
    public void test() {
        String subtitle = driver.findElementById("com.google.android.apps.magazines:id/contextual_header_subtitle").getText();
        Assert.assertEquals(subtitle, "Top 5 stories right now");
        String resourceId = driver.findElementById("com.google.android.apps.magazines:id/product_name").getText();
        Assert.assertEquals(resourceId, "News");

    }

}
