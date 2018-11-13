package pl.training.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverProvider {

    private static final String TEST_DEVICE_NAME = "TEST_DEVICE";
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";

    static DesiredCapabilities defaultCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, TEST_DEVICE_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "100");
        return desiredCapabilities;
    }

    static DesiredCapabilities apiDemosCapabilities() {
        File appArchive = new File("ApiDemos-debug.apk");
        DesiredCapabilities desiredCapabilities = defaultCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.APP, appArchive.getAbsolutePath());
        return desiredCapabilities;
    }

    static DesiredCapabilities googleNewsCapabilities() {
        DesiredCapabilities desiredCapabilities = defaultCapabilities();
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.apps.magazines");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.apps.dots.android.app.activity.CurrentsStartActivity");
        return desiredCapabilities;
    }

    public AndroidDriver<AndroidElement> get(DesiredCapabilities desiredCapabilities) {
        try {
            return new AndroidDriver<>(new URL(APPIUM_SERVER_URL), desiredCapabilities);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(APPIUM_SERVER_URL);
        }
    }

}

