package com.se.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverTest {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		// Choose the browser, version, and platform to test
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        capabilities.setCapability(CapabilityType.VERSION, "6");
        capabilities.setCapability(CapabilityType.PLATFORM, "OSX 10.8");
        capabilities.setCapability("name", "Sam's Test");
		// Create the connection to Sauce Labs to run the tests
		this.driver = new RemoteWebDriver(
				new URL("http://samroyale:35b75a65-ef15-44cf-b1ae-6ddec40521e6@ondemand.saucelabs.com:80/wd/hub"),
				capabilities);
	}

	@Test
	public void webDriver() throws Exception {
		// Make the browser get the page and check its title
		driver.get("http://localhost:8888/");
		assertThat(driver.getTitle()).isEqualTo("Request Capture");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}