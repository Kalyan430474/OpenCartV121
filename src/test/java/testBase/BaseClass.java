package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.net.URL;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {
	
	public static WebDriver driver;
	public Properties p;
	public Logger logger; 
	/*
	 * import org.apache.logging.log4j.LogManager;
	 * import org.apache.logging.log4j.Logger;
	 * import these two packages only for log4j
	*/
	@SuppressWarnings("deprecation")
	@BeforeClass(groups= {"sanity","regression","master","datadriven"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException 
	{
		//loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		logger = LogManager.getLogger(this.getClass());
		
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No Matching OS");
				return;
			}
			
			//browser
			switch(br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome");; break;
			case "firefox" : capabilities.setBrowserName("firefox"); break;
			case "edge" : capabilities.setBrowserName("edge");; break;
			default : System.out.println("Invalid Browser Name"); return; //return breaks from execution
			}	
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" : driver = new ChromeDriver(); break;
			case "firefox" : driver = new FirefoxDriver(); break;
			case "edge" : driver = new EdgeDriver(); break;
			default : System.out.println("Invalid Browser Name"); return; //return breaks from execution
			}				
		}
		

		
//		driver = new ChromeDriver(); (inputs given from testng.xml
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
//		driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appURL")); //reading URL from the system.properties file
		driver.manage().window().maximize();
		
		
	}
	
	@AfterClass(groups= {"sanity","regression","master","datadriven"}) // need to clarify the groups. watch this video while practicing
		public void tearDown()
	{
		driver.quit();
	}
	
	
	@SuppressWarnings("deprecation")
	public String randomString()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	@SuppressWarnings("deprecation")
	public String randomNumber()
	{
		String randomNumber = RandomStringUtils.randomNumeric(10);
		return randomNumber;
	}
	
	@SuppressWarnings("deprecation")
	public String randomPassword()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		String randomNumber = RandomStringUtils.randomNumeric(10);
		return(generatedString+"@"+randomNumber);
	}
	
	public String captureScreen(String tname) throws IOException{
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE); 
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
}
