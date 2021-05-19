
import java.net.MalformedURLException;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.WebElement;

public class AndroidTests extends BaseTest {
	AndroidDriver<MobileElement> driver;

	@Before
	public void setupDriver() throws MalformedURLException {
		driver = AndroidDriverInstantiate();
	}

	@Test
	public void testSearchYoutube() throws InterruptedException {
		driver.findElementByAccessibilityId("Search").click();
		driver.findElementByXPath("//*[contains(@text, 'Search YouTube')]").sendKeys("Appium installation with Node"+"\n");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		WebElement viewGroup = driver.findElementByXPath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[2]");
		viewGroup.click();
		Thread.sleep(3000);
	}

	@After
	public void cleanup(){
		driver.quit();
	}

}
