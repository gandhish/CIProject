import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


public class BaseTest {

	enum iOSAppName {UIKitCatalog, longtap, ContactsSimulator};
	static AppiumDriverLocalService service;
	static boolean isServiceStarted = false;

	public static void startService(){
		//Setting up the environment variables
		Map<String, String> envVariablesMap = new HashMap();
		envVariablesMap.put("JAVA_HOME", "/Library/Java/JavaVirtualMachines/jdk-16.jdk/Contents/Home");
		envVariablesMap.put("ANDROID_HOME", "/Users/gandhish/Library/Android/sdk");

		//Start the Appium Server
		service = AppiumDriverLocalService.buildService(
				new AppiumServiceBuilder().usingDriverExecutable(new File ("/usr/local/bin/node")) //Node.js path
						.withAppiumJS(new File("/Applications/Appium.app/Contents/Resources/app/node_modules/appium/build/lib/main.js")) //Appium -> main.js path
						.withLogFile(new File(System.getProperty("user.dir")+"/src/test/resources/logs/Appium.log")) //Generate logs at this location
						.withArgument(GeneralServerFlag.LOCAL_TIMEZONE) //Set the timezone to local when writing to log file
						.withEnvironment(envVariablesMap) //Environment variables -- specify a Map containing the env variables to be set - include JAVA_HOME, ANDROID_HOME
		);

		service.start();
		isServiceStarted = true;
	}


	public static IOSDriver IOSDriver(iOSAppName appName) throws java.net.MalformedURLException{
		DesiredCapabilities objCapabilities = new DesiredCapabilities();
		objCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.4");
		objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 12 Pro Max");
		objCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
		objCapabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 50000);
		objCapabilities.setCapability("commandTimeouts", "12000");

		objCapabilities.setCapability(MobileCapabilityType.APP, "Users/gandhish/desktop/"+appName+".app");
		//Assumes that the Appium Server is already running on this location
		IOSDriver driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), objCapabilities);
		
		return driver;
	}

	public static IOSDriver IOSDriver(iOSAppName appName, boolean useTestService) throws java.net.MalformedURLException{
		DesiredCapabilities objCapabilities = new DesiredCapabilities();
		objCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.4");
		objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 12 Pro Max");
		objCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
		objCapabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 50000);
		objCapabilities.setCapability("commandTimeouts", "12000");

		objCapabilities.setCapability(MobileCapabilityType.APP, "Users/gandhish/desktop/"+appName+".app");
		//Uses the service started on the test run
		IOSDriver driver;
		if(useTestService){
			if(isServiceStarted)
			driver = new IOSDriver(service, objCapabilities);
			else {
				startService();
				isServiceStarted = true;
				driver = new IOSDriver(service, objCapabilities);
			}
		} else {
			driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), objCapabilities);
		}

		return driver;
	}
	
	public static AndroidDriver<WebElement> ChromeDriverInstantiate() throws java.net.MalformedURLException{
		DesiredCapabilities objCapabilities = new DesiredCapabilities();
		objCapabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
		objCapabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
		objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
		
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://localhost:4723/wd/hub"), objCapabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public static AndroidDriver<WebElement> ChromeDriverInstantiate(AppiumDriverLocalService service){
		DesiredCapabilities objCapabilities = new DesiredCapabilities();
		objCapabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
		objCapabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
		objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");

		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(service, objCapabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}


	public static AndroidDriver<WebElement> ChromeDriverInstantiate(boolean useTestService) throws MalformedURLException {
		DesiredCapabilities objCapabilities = new DesiredCapabilities();
		objCapabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
		objCapabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
		objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
		AndroidDriver<WebElement> driver;
		if(useTestService) {
			if(isServiceStarted)
			driver = new AndroidDriver<WebElement>(service, objCapabilities);
			else {
				startService();
				isServiceStarted = true;
				driver = new AndroidDriver<WebElement>(service, objCapabilities);
			}
		}
		else
			driver = new AndroidDriver<WebElement>(new URL("http://localhost:4723/wd/hub"), objCapabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	public static AndroidDriver<MobileElement> AndroidDriverInstantiate() throws java.net.MalformedURLException{
		DesiredCapabilities objCapabilities = new DesiredCapabilities();
		objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
		objCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
	//	objCapabilities.setCapability("app", app.getAbsolutePath()); //If this is specified, it means that the app needs to be installed on the device and not already there
		objCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.youtube");
		objCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.youtube.app.WatchWhileActivity");

		AndroidDriver<MobileElement> driver = new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), objCapabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public static AndroidDriver<MobileElement> AndroidDriverInstantiate(boolean useTestService) throws java.net.MalformedURLException{
		DesiredCapabilities objCapabilities = new DesiredCapabilities();
		objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
		objCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		//	objCapabilities.setCapability("app", app.getAbsolutePath()); //If this is specified, it means that the app needs to be installed on the device and not already there
		objCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.youtube");
		objCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.youtube.app.WatchWhileActivity");
		AndroidDriver<MobileElement> driver;
        if(useTestService) {
        	if (isServiceStarted)
				driver = new AndroidDriver<MobileElement>(service, objCapabilities);
        	else {
        		startService();
        		isServiceStarted = true;
				driver = new AndroidDriver<MobileElement>(service, objCapabilities);
			}
		}
        else
			driver = new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), objCapabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	
}
