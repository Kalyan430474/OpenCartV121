package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"sanity","master"})
	public void verify_login()
	{
		logger.info("**** Starting TC002_LoginTest ****");
		try {
			HomePage hp = new HomePage(driver);

			hp.clickMyAccount();
			logger.info("**** Clicked on My Account Link ****");

			hp.clickLogin();
			logger.info("**** Clicked on Login Link ****");

			LoginPage lp = new LoginPage(driver);
			
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();

			logger.info("**** Validating expected Message ****");
			//My Account
			
			MyAccountPage macc= new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountExists();// - login page
			
		
			
//			Asserts.assertEquals(targetPage, true, "Login failed"); - login page
			Assert.assertTrue(targetPage);// - login page
			
			Thread.sleep(2000);
		} catch (Exception e) {
			
			Assert.fail();
		}
		logger.info("**** Finished TC002_LoginTest ****");
	}
}