import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static io.appium.java_client.touch.offset.ElementOption.element;

public class ChromeTests extends BaseTest {
	AndroidDriver<WebElement> driver;

	@Before
	public void SetUpDriver() throws MalformedURLException {
		//The driver should use the service above and not the localhost:4723 URL.
		driver = ChromeDriverInstantiate();
	}

	@Test
	public void testSearchGoogle() throws InterruptedException {
		driver.get("http://google.com");
		driver.findElementByName("q").sendKeys("Appium installation with Node"+"\n");
		WebElement searchResult = driver.findElementByXPath("//div[contains(text(),'Appium')]");
		Assert.assertNotNull(searchResult);
		searchResult.click();

		//Small wait for just to view the search result that has been opened
		Thread.sleep(5000);
	}

	@After
	public void cleanup(){
		driver.quit();
	}

}
