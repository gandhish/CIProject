import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSTouchAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;
import static io.appium.java_client.touch.TapOptions.tapOptions;


public class GestureTests extends BaseTest {

	IOSDriver driver;

	@Before
	public void setupDriver() throws MalformedURLException {
		driver = IOSDriver(iOSAppName.longtap);
	}

	@Test
	public void testLongTap(){
		//Long Tap element
		MobileElement objLongTap = ((MobileElement)driver.findElementByName("Long tap"));
		//Action: long press
		//Pointer Input types: touch, mouse, pen?
		IOSTouchAction objTouch = new IOSTouchAction(driver);
		//Create Action Sequence and then perform
		//Perform is needed so that all these chained actions are performed in the sequence
		objTouch.longPress(longPressOptions().withElement(element(objLongTap)).withDuration(ofSeconds(2))).release().perform();

		//Tap
		MobileElement objTap = (MobileElement)driver.findElementByXPath("//XCUIElementTypeSwitch[1]");
		objTouch.tap(tapOptions().withElement(element(objTap))).perform();
		objTap = (MobileElement)driver.findElementByXPath("//XCUIElementTypeSwitch[2]");
		objTouch.tap(tapOptions().withElement(element(objTap))).perform();
		objTap = (MobileElement)driver.findElementByXPath("//XCUIElementTypeSwitch[1]");
		objTouch.tap(tapOptions().withElement(element(objTap))).perform();
	}

	@After
	public void cleanup(){
		driver.quit();
	}

}
