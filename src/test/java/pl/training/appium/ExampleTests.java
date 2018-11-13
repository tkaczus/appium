package pl.training.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ExampleTests {

    private final static File APP_ARCHIVE = new File("ApiDemos-debug.apk");
    private final static String TEST_DEVICE_NAME = "TEST_DEVICE";
    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";


    private AndroidDriver<AndroidElement> getAndroidDriver() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.APP, APP_ARCHIVE.getAbsolutePath());
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, TEST_DEVICE_NAME);
        return new AndroidDriver<AndroidElement>(new URL(APPIUM_SERVER_URL), desiredCapabilities);
    }

    @Test
    public void runApplication() {

    }
}
