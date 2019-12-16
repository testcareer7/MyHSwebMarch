package pages;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import freemarker.template.utility.Execute;

import wrappers.ProjectWrapp;

public class MyH_Homepage extends ProjectWrapp{
	public  MyH_Homepage(RemoteWebDriver driver, ExtentTest test){
		this.driver = driver;
		this.test = test;
		
		//if(!verifyTitlecontains("Dashboard | MyHyundai")){
	//		reportStep("This is not Dashboard | MyHyundai", "FAIL");
			//	}

	}

	public  MyH_Dashboardpage entercredentials(String uname,String pwd) throws IOException, InterruptedException {
		Thread.sleep(6000);
		pageRefresh();
		
		enterByXpathExplict(prop.getProperty("enterusername.xpath"),uname);
enterByXpathExplict(prop.getProperty("enterpwd.xpath"), pwd);
clickByXpathExplict(prop.getProperty("loginbutton.xpath"));
Thread.sleep(6000);
		return new MyH_Dashboardpage(driver, test);
	}
	public  Welcomepage credentialwelcomepage(String uname,String pwd) throws IOException, InterruptedException {
		Thread.sleep(10000);
		pageRefresh();
		
		enterByXpathExplict(prop.getProperty("enterusername.xpath"),uname);
enterByXpathExplict(prop.getProperty("enterpwd.xpath"), pwd);
clickByXpathExplict(prop.getProperty("loginbutton.xpath"));
Thread.sleep(6000);


		return new Welcomepage(driver, test);
	}

	public  MyH_Homepage enterinvalidcredentials(String uname,String pwd,String Valmsg) throws IOException, InterruptedException {
		Thread.sleep(6000);
		pageRefresh();
		enterByXpathExplict(prop.getProperty("enterusername.xpath"),uname);
enterByXpathExplict(prop.getProperty("enterpwd.xpath"), pwd);
clickByXpathExplict(prop.getProperty("loginbutton.xpath"));
Thread.sleep(6000);
verifyTextContainsByXpath(prop.getProperty("invalidlogin.xpath"),"You have entered an incorrect username or password");
		return this;
	}

	
}
