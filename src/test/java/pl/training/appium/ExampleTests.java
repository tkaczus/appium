package pl.training.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ExampleTests {

    private final static File APP_ARCHIVE = new File("ApiDemos-debug.apk");
    private final static String TEST_DEVICE_NAME = "Nexus";
    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";

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
        getAndroidDriver();
    }

    @Test
    public void setupWifi() {
        AndroidDriver<AndroidElement> driver = getAndroidDriver();
        driver.findElementByXPath("//android.widget.TextView[@text='Preference']").click();
        driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
        driver.findElementById("android:id/checkbox").click();
        driver.findElementsByClassName("android.widget.RelativeLayout").get(1).click();
        driver.findElementByClassName("android.widget.EditText").sendKeys("Test network");
        driver.findElementsByClassName("android.widget.Button").get(1).click();
        driver.findElementsByClassName("android.widget.RelativeLayout").get(1).click();
        Assert.assertEquals("Test network", driver.findElementByClassName("android.widget.EditText").getText());

    }
}
