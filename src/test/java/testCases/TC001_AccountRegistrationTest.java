package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups={"regression","master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("**** Starting TC001_AccountRegistrationTest ****");
		try {
			HomePage hp = new HomePage(driver);

			hp.clickMyAccount();
			logger.info("**** Clicked on My Account Link ****");

			hp.clickRegister();
			logger.info("**** Clicked on Register Link ****");

			AccountRegistrationPage regPage = new AccountRegistrationPage(driver);

			logger.info("**** Providing customer details ****");
//			regPage.setFirstName("John".toUpperCase());
//			regPage.setLastName("David".toUpperCase());
//			regPage.setEmail("adcjohndavid"+"@gmail.com");
//			regPage.setTelephone("2312312312");

//			String password = randomAlphaNumeric();

//			regPage.setPassword("xyz@12345");
//			regPage.setConfirmPassword("xyz@12345");

			regPage.setFirstName(randomString().toUpperCase());
			regPage.setLastName(randomString().toUpperCase());
			regPage.setEmail(randomString() + "@gmail.com");
			regPage.setTelephone(randomNumber());

			String password = randomPassword();
			regPage.setPassword(password);
			regPage.setConfirmPassword(password);

			regPage.setPrivacyPolicy();
			regPage.clickContinue();

			logger.info("**** Validating expected Message ****");
			String confmsg = regPage.getConfirmationMsg();
			if(confmsg.equals("Your Account Has Been Created!"))
				Assert.assertTrue(true);
			else
			{
				logger.error("Test Failed....");
				logger.debug("Debug logs...");
				Assert.assertTrue(false);
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			
			Assert.fail();
		}
		logger.info("**** Finished TC001_AccountRegistrationTest ****");
	}
}

/*
 * Randomly create a test data, during run time is dynamic data Hardcoding
 * values is called static data. we have to generate everything randomly,
 * specifically the gmail and etc. we have to create our own user defined
 * method. there is no built in method
 * 
 * setup, teardown, randomnumber generation, randomstring generation and
 * randompassword generation is required for otherclasses, if we create there
 * also, it is duplication so creating a base class under the same package, test
 * cases and place it there the reusable methods. a commonly required method can
 * be saved directly in the base class
 */