package pl.training.appium;

import io.appium.java_client.TouchAction;
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
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static pl.training.appium.DriverProvider.apiDemosCapabilities;

public class ExampleTests {

    private String testNetwork = "Test network";

    private AndroidDriver<AndroidElement> driver = new DriverProvider().get(apiDemosCapabilities());

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
    public void runApplication() {
        driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
        System.out.println("elements = " + driver.findElementsByAndroidUIAutomator("new UiSelector().clickable(true)").size());
    }

    @Test
    public void setupWifi() {
        driver.findElementByXPath("//android.widget.TextView[@text='Preference']").click();
        driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
        driver.findElementById("android:id/checkbox").click();
        Assert.assertEquals(driver.findElementById("android:id/checkbox").getAttribute("checked"), "true");
        driver.findElementsByClassName("android.widget.RelativeLayout").get(1).click();
        driver.findElementByClassName("android.widget.EditText").sendKeys(testNetwork);
        driver.findElementsByClassName("android.widget.Button").get(1).click();
        driver.findElementsByClassName("android.widget.RelativeLayout").get(1).click();
        Assert.assertEquals(testNetwork, driver.findElementByClassName("android.widget.EditText").getText());
    }

    @Test
    public void shouldShowContextMenuOnLongTap() {
        driver.findElementByXPath("//android.widget.TextView[@text='Views']").click();
        driver.findElementByXPath("//android.widget.TextView[@text='Expandable Lists']").click();

        AndroidElement customAdapter = driver.findElementByXPath("//android.widget.TextView[@text='1. Custom Adapter']");
        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(tapOptions().withElement(element(customAdapter))).perform();

        AndroidElement dogNames = driver.findElementByXPath("//android.widget.TextView[@text='Dog Names']");
        touchAction.longPress(longPressOptions().withElement(element(dogNames))
                .withDuration(Duration.ofSeconds(2)))
                .release()
                .perform();

        Assert.assertTrue(driver.findElementById("android:id/title").isEnabled());
    }


    @Test
    public void shouldChangeTime() {
        driver.findElementByXPath("//android.widget.TextView[@text='Views']").click();
        driver.findElementByXPath("//android.widget.TextView[@text='Date Widgets']").click();
        driver.findElementByXPath("//android.widget.TextView[@text='1. Dialog']").click();
        driver.findElementById("io.appium.android.apis:id/pickTime").click();

        List<AndroidElement> elementyZegara = driver.findElementsByClassName("android.widget.RadialTimePickerView$RadialPickerTouchHelper");
        elementyZegara.get(0).click();

        TouchAction touchAction = new TouchAction(driver);

        touchAction.longPress(longPressOptions().withElement(element(elementyZegara.get(5)))
                .withDuration(Duration.ofSeconds(2)))
                .moveTo(element(elementyZegara.get(0)))
                .release()
                .perform();

        String h = driver.findElementById("android:id/hours").getText();
        String m = driver.findElementById("android:id/minutes").getText();
        Assert.assertEquals(h + ":" + m, "1:00");
    }

    @Test
    public void shouldScrool() {
        driver.findElementByXPath("//android.widget.TextView[@text='Views']").click();
        driver.findElementsByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Switches\"))");
        driver.findElementByXPath("//android.widget.TextView[@text='Switches']").click();
        driver.findElementByXPath("//android.widget.Switch[@text='Default is on ON']").click();

        Assert.assertEquals(driver.findElementByXPath("//android.widget.Switch[@text='Default is on OFF']").getAttribute("checked"), "false");
    }


}
