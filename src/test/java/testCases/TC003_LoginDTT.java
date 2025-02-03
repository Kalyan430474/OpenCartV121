package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
 * data is valid - login success - test pass - logout
 * data is valid - login failure - test fail
 * 
 * data is invalid - login pass - test fail - logout
 * data is invalid - login fail - test pass
 */
public class TC003_LoginDTT extends BaseClass {
	
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "datadriven")
	public void verify_loginDDT(String email, String pwd, String exp)
	{
		
		logger.info("**** Starting of TC003_LoginDTT ****");
		
		try
		{
			//HomePage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//Login
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			
			
			//MyAccount
			
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountExists();
			
			if(exp.equalsIgnoreCase("Valid"))
			{
				if(targetPage==true)
				{
					macc.clickLogout();
					Assert.assertTrue(true); //data valid, login success, test pass
				}
				else
					Assert.assertTrue(false);//data valid, login fail, test fail
			}
			
			if(exp.equalsIgnoreCase("Invalid"))
			{
				macc.clickLogout();
				Assert.assertTrue(false);//data invalid, login success, test fail
			}
			else
				Assert.assertTrue(true);//data invalid, login fail, test pass
			
		}
		catch(Exception e)
		{
			Assert.fail();
			
		}
			
		logger.info("**** Finished TC003_LoginDTT ****");
	}
}
