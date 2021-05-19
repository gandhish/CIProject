import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class IOSTests extends BaseTest {

	IOSDriver driver;

	@Before
	public void setupDriver() throws MalformedURLException {
		driver = IOSDriver(iOSAppName.UIKitCatalog);
	}

	@Test
	public void testSearchYoutube() throws InterruptedException {
		driver.findElementByAccessibilityId("Alert Views").click();
		driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Text Entry']").sendKeys("TextEntry");
		driver.findElementByAccessibilityId("OK").click();
		driver.findElementByAccessibilityId("Confirm / Cancel").click();
		System.out.println(driver.findElementByXPath("//*[contains(@name, 'message')]").getText());
		driver.findElementByAccessibilityId("Confirm").click();
		//Go back to the main menu
		((WebElement)driver.findElementsByName("UIKitCatalog").get(1)).click();
		//Scroll down to the element: Web View
		Map<String, String> scrollParams = new HashMap<String, String>();
		scrollParams.put("direction", "down");
		scrollParams.put("name", "Web View");
		driver.executeScript("mobile:scroll", scrollParams);
		driver.findElementByAccessibilityId("Web View").click();
		Thread.sleep(2000);
		driver.findElementByXPath("//XCUIElementTypeButton[@name='UIKitCatalog']").click();

		//Pickers
		driver.findElementByAccessibilityId("Picker View").click();
		driver.findElementByAccessibilityId("Red color component value").sendKeys("80");
		driver.findElementByAccessibilityId("Green color component value").sendKeys("215");
		driver.findElementByAccessibilityId("Blue color component value").sendKeys("110");

		((WebElement)driver.findElementsByName("UIKitCatalog").get(1)).click();

		//Sliders
		driver.findElementByAccessibilityId("Sliders").click();
		//The following is specific to an IOSElement and wont work with an Android element. Hence, the cast
		((IOSElement)driver.findElementByXPath("//XCUIElementTypeSlider")).setValue("0%"); //SetValue takes 0 to 1; so 100% is 1, 42% is 0.42
		((IOSElement)driver.findElementByXPath("//XCUIElementTypeSlider")).setValue("1%");

		org.junit.Assert.assertEquals("100%", ((IOSElement)driver.findElementByXPath("//XCUIElementTypeSlider")).getAttribute("value"));
		((WebElement)driver.findElementsByName("UIKitCatalog").get(1)).click();
	}

	@After
	public void cleanup(){
		driver.quit();
	}

}
