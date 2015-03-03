package com.se.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RemoteAppTest {

	private WebDriver driver;

	@Before
	public void setUpSauceLabs() throws Exception {
		// Choose the browser, version, and platform to test
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        capabilities.setCapability(CapabilityType.VERSION, "6");
        capabilities.setCapability(CapabilityType.PLATFORM, "OSX 10.8");
        capabilities.setCapability("name", "Sam's Test");
        // See http://docs.travis-ci.com/user/sauce-connect/
        capabilities.setCapability("tunnel-identifier", System.getenv("TRAVIS_JOB_NUMBER"));
		// Create the connection to Sauce Labs to run the tests
		this.driver = new RemoteWebDriver(
				new URL("http://samroyale:35b75a65-ef15-44cf-b1ae-6ddec40521e6@ondemand.saucelabs.com:80/wd/hub"),
				capabilities);
	}

	/*@Before
	public void setUpFirefox() throws Exception {
		// local firefox - can instantiate directly
		this.driver = new FirefoxDriver();
	}*/

	/*@Before
	public void setUpChrome() throws Exception {
		// local chrome - uses chromedriver
		System.setProperty("webdriver.chrome.driver", "/Users/seldred/Java/chromedriver");
		this.driver = new ChromeDriver();
	}*/

	@Test
	public void webDriver() throws Exception {
		// Make the browser get the page and check its title
		driver.get("http://localhost:8888/");
		//driver.get("http://requestcapture.appspot.com/");
		assertThat(driver.getTitle()).isEqualTo("Request Capture");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}