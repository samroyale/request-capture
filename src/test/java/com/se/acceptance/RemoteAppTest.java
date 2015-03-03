package com.se.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Invokes Selenium on SauceLabs to test an instance of the app running on Travis.
 * 
 * Resources used:
 * * http://docs.travis-ci.com/user/gui-and-headless-browsers/
 * * http://docs.travis-ci.com/user/sauce-connect/
 * * http://sauceio.com/index.php/2013/03/run-your-selenium-tests-completely-in-the-cloud-using-travis-ci-and-sauce-labs/
 * * https://docs.saucelabs.com/ci-integrations/travis-ci/
 * 
 * See also .travis.yml in project root.
 * 
 * @author seldred
 */
public class RemoteAppTest {

	private static final String WEB_APP_ROOT = "http://localhost:8888";
	
	private WebDriver driver;

	@Before
	public void setupWebDriver() throws Exception {
		driver = getWebDriver();
	}
		
	private WebDriver getWebDriver() throws Exception {
		// only use the remote web driver (Sauce Labs) if we're running on Travis
		boolean runningOnTravis = Boolean.parseBoolean(System.getenv("TRAVIS"));
		if (runningOnTravis) {
			return aRemoteWebDriver();
		}
		return aFirefoxDriver();
		// return aChromeDriver();
	}

	public WebDriver aRemoteWebDriver() throws Exception {
		// Choose the browser, version, and platform to test
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        capabilities.setCapability(CapabilityType.VERSION, "6");
        capabilities.setCapability(CapabilityType.PLATFORM, "OSX 10.8");
        capabilities.setCapability("name", "request-capture acceptance test");
        // See http://docs.travis-ci.com/user/sauce-connect/
        capabilities.setCapability("tunnel-identifier", System.getenv("TRAVIS_JOB_NUMBER"));
        // Some more properties to help keep things organised
        capabilities.setCapability("build", System.getenv("TRAVIS_BUILD_NUMBER"));
        capabilities.setCapability("tags", new String[] { System.getenv("TRAVIS_JDK_VERSION"), "CI" });
		// Create the connection to Sauce Labs to run the tests
		return new RemoteWebDriver(
				new URL("http://samroyale:35b75a65-ef15-44cf-b1ae-6ddec40521e6@ondemand.saucelabs.com:80/wd/hub"),
				capabilities);
	}
	
	public WebDriver aFirefoxDriver() throws Exception {
		// Local firefox - can instantiate directly
		return new FirefoxDriver();
	}

	public WebDriver aChromeDriver() throws Exception {
		// Local chrome - uses chromedriver
		System.setProperty("webdriver.chrome.driver", "/Users/seldred/Java/chromedriver");
		return new ChromeDriver();
	}

	@Test
	public void homePageLoadsOkay() throws Exception {
		// Have the browser get the home page and check its title
		driver.get(getFullPathTo("/"));
		assertThat(driver.getTitle()).isEqualTo("Request Capture");
		List<WebElement> anchors = driver.findElements(By.tagName("a"));
		// Check the first 2 links
		assertThat(anchors.get(0).getAttribute("href")).isEqualTo(getFullPathTo("/start"));
		assertThat(anchors.get(1).getAttribute("href")).isEqualTo(getFullPathTo("/list"));
	}
	
	private String getFullPathTo(String path) {
		return WEB_APP_ROOT + path;
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}