package pl.training.appium;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import javafx.scene.shape.MoveTo;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class ExampleTests {

    private final static File APP_ARCHIVE = new File("ApiDemos-debug.apk");
    private final static String TEST_DEVICE_NAME = "Nexus";
    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    private String testNetwork = "Test network";

    private AndroidDriver<AndroidElement> driver = getAndroidDriver();

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


    /**
     * ustawiamy połaczenie do anroida (symulatora) - ta jest instalowana
     * mozna uruchomic juz zainstalowana
     *
     * @return
     * @throws MalformedURLException
     */
    private AndroidDriver<AndroidElement> getAndroidDriver() {
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.APP, APP_ARCHIVE.getAbsolutePath());
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, TEST_DEVICE_NAME);
            desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "10");
            //desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE);
            //desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY);
            return new AndroidDriver<AndroidElement>(new URL(APPIUM_SERVER_URL), desiredCapabilities);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException();
        }
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
