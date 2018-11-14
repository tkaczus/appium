package pl.training.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainView {

    public MainView(AndroidDriver<AndroidElement> driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Views']")
    AndroidElement views;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='WebView']")
    AndroidElement webView;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Drag and Drop']")
    AndroidElement dragAndDrop;

    @AndroidFindBy(className = "//android.widget.TextView")
    List<AndroidElement> rows;

}
