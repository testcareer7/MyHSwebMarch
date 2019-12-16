package pages;

import java.io.IOException;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import wrappers.ProjectWrapp;

public class MyH_Dashboardpage extends ProjectWrapp{
	public  MyH_Dashboardpage(RemoteWebDriver driver, ExtentTest test){
		this.driver = driver;
		this.test = test;
	
}
	
	
	public MyH_Dashboardpage verifyPageTitle(String pagetitle,String error) throws InterruptedException {
		pagetitle(pagetitle,error);
	
//	if(!verifyTitlecontains("Dashboard | MyHyundai")){
//		reportStep("This is not Dashboard | MyHyundai", "FAIL");
//	
//	}
	return this;
	}


public MyH_Dashboardpage clickhamburgermenu() throws InterruptedException {
	Thread.sleep(6000);
	pageRefresh();
	Thread.sleep(10000);

	clickByXpathExplict(prop.getProperty("click.hamburgermenu.xpath"));
return this;
}





		public ManagesubscriptionPage clicksubscriptionLink() throws InterruptedException {
	
			Thread.sleep(10000);
			pageRefresh();
	pageScroll400();
	Thread.sleep(2000);
	
	clickByXpathExplict(prop.getProperty("Managesubscription.xpath"));
	Thread.sleep(20000);
	pageScroll400();
return new ManagesubscriptionPage(driver, test);

}	
		

public MyH_Dashboardpage blueLinkEnrolStatusVerify(String status) throws InterruptedException {
	Thread.sleep(20000);
	//pageRefresh();
	verifyTextContainsByXpath(prop.getProperty("BlueLink.Active.Status.xpath"),status);
//	verifyTextContainsByXpath(prop.getProperty("status.dashboard.xpath"),packageActiveStatus);
	//Thread.sleep(20000);

return this;

}




	

public Hyundairesourcespage clickhyundairesourcesSelectCategory() throws InterruptedException {
	Thread.sleep(4000);
	clickByXpathExplict(prop.getProperty("clickHyundaiResourcesMnenu.xpath"));
	Thread.sleep(4000);

	clickByXpathExplict(prop.getProperty("clickgetstartedcategory.xpath"));

return new Hyundairesourcespage(driver, test);
}
	


public VehicleHealthPage clickVehicleHealthMenu() {
	clickByXpathExplict(prop.getProperty("vehiclehealth.xpath"));
return new VehicleHealthPage(driver, test);


}
	



	
	
	

}
