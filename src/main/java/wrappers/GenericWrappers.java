package wrappers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
//import org.eclipse.jetty.io.UncheckedPrintWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import utils.Reporter;

public class GenericWrappers extends Reporter {
	public RemoteWebDriver driver;
	protected static Properties prop;
	public String sUrl,primaryWindowHandle,sHubUrl,sHubPort;String url2;
	public String environment="https://";
	public List<String>Bznme=new ArrayList<String>();

	public GenericWrappers() {
		Properties prop = new Properties();
		//
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			
			sHubUrl = prop.getProperty("HUB");
			sHubPort = prop.getProperty("PORT");
			sUrl = prop.getProperty("URL");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		}
	
	
	public List<String> verifylistTextByXpath(String xpath){
		
		List<String>a1=new ArrayList<String>();
	List<String>a2=new ArrayList<String>();
		
		
		List<WebElement>element=driver.findElementsByXPath(xpath);
		
		for (WebElement webElement : element) {
			String cate=webElement.getText();
			
			a1.add(cate);
		a2.add(cate);		
		}
		  a1.retainAll(a2);

		  
		  return a2;
		
		
		
		
	}
	public void verifytextmatches(String a ,String b){

if(a.equalsIgnoreCase(b)) {
	
	reportStep(a+"Matches with the value "+b, "PASS");
	
}
	
	else {
		reportStep(a+"does not Matches withe value "+b, "FAIL");
	}
	}

	public void StatusUpdate(String msg,String status) {
		try{

			if("PASS".equalsIgnoreCase(status))
			{
				reportStep(msg, "PASS");
			}
			else {
				reportStep(msg, "FAIL");
			}
			
			
		}
		catch (Exception e) {
			reportStep("Element not found", "FAIL");
		}
	}
	
	

	
	
	
	
	public GenericWrappers(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test=test;
	}
	public void navigate(String url) {
		try {
			
			
			 url2=environment+url;
			
			System.err.println(url2);
			driver.navigate().to(url2);
		/*	for (int i = 0; i<5; i++) {
				Thread.sleep(1000);
				System.out.println(i);
			}*/
			reportStep("Now the page has landed with this url"+url2, "PASS");
		
		}
		
		
		
		catch (NoSuchWindowException e) {
			reportStep("Window was unfortunately closed"+url2, "FAIL");
		}
		
		
		
		catch (TimeoutException e) {
			reportStep("Timeout Exception has occured"+url2, "FAIL");
		}
		
		catch (Exception e) {
			reportStep("Now the page has not landed with this url"+url2, "FAIL");
		}
		
		
		
		
		
	}
	
	public void pagetitle(String text,String msgerror) throws InterruptedException{
	
	
	if(!verifyTitlecontains(text)){
		reportStep(msgerror, "FAIL");
	
	}
	
		
	}	
	
	
	public void verifyTextContainsByXpathexplictwaitalert(String xpath, String text) throws InterruptedException{
		try{
			WebDriverWait wait = new WebDriverWait(driver,120);
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			String sText = element.getText();
			
						if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
			}}

		catch (Exception e) {

			System.out.println("msg sent succesfully");
			Thread.sleep(20000);
			reportStep("unknown exception has occured", "FAIL");
		}
	}
	public void enterBycssXpathExplict(String xpathVal, String mobileNo) {
		try{
		
			Thread.sleep(2000);
			
			WebDriverWait wait = new WebDriverWait(driver,120);
			
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(xpathVal)));
			//WebElement element=wait.until(ExpectedConditions.elementToBeClickable((By.xpath(xpathVal))));
			
	//		driver.findElement(By.xpath(xpathVal)).clear();
			//WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathVal)));
					element.sendKeys(mobileNo);
			
			reportStep("The element with xpath: "+xpathVal+" is entered.", "PASS");
		}
		
		
		catch (Exception e) {
		
			
			//	reportStep("The data: "+xpathVal+" could not be entered in the field :"+xpathVal, "FAIL");
System.out.println(e);
				reportStep("The data: "+xpathVal+" could not be entered in the field.Because No suchelement exception has occured :", "FAIL");

		}
	}

public void clicklistByxpathExplicitwaitindex(String xpathValue,int i) {
	try {
		WebDriverWait wait= new WebDriverWait(driver, 120);
	List <WebElement> myIput=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathValue)));
	myIput.get(i).click();		
reportStep("The element with id: "+xpathValue+" is clicked.", "PASS");

} 
		catch (Exception e) {
			System.out.print(e);
			reportStep("The element with xpath: "+xpathValue+" could not be clicked.Because NO Such Element Exception has occured", "FAIL");
		}
}
	
	
	
	public void verifyTextcontainsurls( String url,String text){
		try {
			
			
			
			if (url.contains(text)){
				reportStep("The text: "+url+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+url+" did not match with the value :"+text, "FAIL");
			}
		}
	
		
		
		
		catch (Exception e) {
			reportStep("The :"+text+" could not be Matched.", "FAIL");
		}
	}
	
	


	public Boolean VerifyElementpresentreturn(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver,100);
		
WebElement e=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

	//	WebElement e  =driver.findElement(By.xpath(xpath));
		Boolean B=false;
		if(e.isDisplayed())
		{

			B=true;
			reportStep("The element with xpath: "+xpath+" is present.", "PASS");
return B;
		}
		
		else {
			reportStep("The element with id: "+xpath+" is not present .", "PASS");

		}
		return B;
		
		
	}
	public void pageRefresh( ){
		try {
				driver.navigate().refresh();
				reportStep("Page has refresh sucessfully", "PASS");

		}
		catch (Exception e) {
			reportStep("Page has refresh sucessfully", "FAIL");
		}
	}
	
	
	
	
	public void verifybyAttributescontainsxpathvalidinvalid(String xpath, String attributes ,String compText,String validmsg,String errorvalid) throws InterruptedException {
		try{
			Thread.sleep(12000);
			String sText = driver.findElementByXPath(xpath).getAttribute(attributes);
	System.out.println(sText);
			if (sText.contains(compText)){
				
				reportStep(sText+ "contains the value  "+ validmsg, "PASS");
			}else{
				System.out.println("******************"+sText+"***************");
				System.out.println("+++++++"+compText+"++++++++++++");
				
				
				reportStep(sText+ "does not contains the value  " +errorvalid, "FAIL");
			}
		}
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
		}
	}


	public void verifyTextnotPresentCONTAINSByXpath(String xpath, String text){
		try {
			
			
			
			String sText = driver.findElement(By.xpath(xpath)).getText();

			if (sText.contains(text)){
				System.out.println("*****************"+sText+"***********");				
				reportStep(text,"FAIL");	
			}
	

			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
			
			
			
		
	}


	public void loadObjects() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/object.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void unloadObjects() {
		prop = null;
	}

	

	public void invokeApp(String browser) throws IOException {
		invokeApps(browser);
	}
	 public void invokeApps(String browser) throws IOException {
	        
	        try{
	              
	              if (browser.equalsIgnoreCase("chrome")){
	                    System.setProperty("webdriver.chrome.driver","./drivers/chromedrives64.exe");
	                    driver = new ChromeDriver();
	              } else if (browser.equalsIgnoreCase("firefox")) 
	                    {
	            	  System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver64.exe");
						driver = new FirefoxDriver();
	                    
	              }else
	              {
	                    System.out.println("\n ********* Driver not specified for OS >> << **********");
	              }
	              
		  			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

	  			driver.manage().window().maximize();
	            
	  			url2=environment+sUrl;
	  			driver.get(url2);
	              
				reportStep("The browser:" + browser + " launched successfully", "PASS");

		  			
	        }catch (Exception ie6)
	        {
				reportStep("The browser:" + browser + "has not been launched", "FAIL");

	              System.out.println(" ********* Exception on browser launch : \n"+ie6);
	        }
	              
	         
	        
	        }

public ArrayList<WebElement>arraylisting(String xpath)    {

ArrayList<WebElement>li=(ArrayList<WebElement>) driver.findElements(By.xpath(xpath));
return(li);
}
	
	
public List<WebElement>listele(String xpath)    {
	List<WebElement> li = null;
try {
		
li=driver.findElements(By.xpath(xpath));
reportStep("List of element has located", "PASS");


}
catch (Exception e) {
	reportStep("List of element has not been located", "FAIL");
}

return li;
}
	
	public void pageScrollup() {
		try{
			JavascriptExecutor jse = driver;
			jse.executeScript("window.scrollBy(0,-250)", "");
	reportStep("Page has sucessfully scrolled", "PASS");

		}
		catch (Exception e) {
			reportStep("Page has not sucessfully scrolled", "FAIL");
		}
	}
	
	public void pageScroll() {
		try{
	JavascriptExecutor jse = driver;
	jse.executeScript("window.scrollBy(0,700)", "");
	reportStep("Page has sucessfully scrolled", "PASS");

		}
		catch (Exception e) {
			reportStep("Page has not sucessfully scrolled", "FAIL");
		}
	}
	
	public void pageScroll400() {
		try{
	JavascriptExecutor jse = driver;
	jse.executeScript("window.scrollBy(0,400)", "");
	reportStep("Page has sucessfully scrolled", "PASS");

		}
		catch (Exception e) {
			reportStep("Page has not sucessfully scrolled", "FAIL");
		}
	}
	
	
	

	
	
	
	public void geturl(String url) {
		try{
	
		driver.get(url);
		
	reportStep("Page has landed on " +url, "PASS");

		}
		catch (Exception e) {
			reportStep("Page has not landed on " +url, "FAIL");
		}
	}
	

	
	public void navigateback() {
		
	try {
		driver.navigate().back();
		reportStep("Page has navigated back sucessfully", "PASS");

	}
		catch (Exception e) {
		}
		
	}
	
	
	public void clickByXpathExplict(String xpathVal) {
		try{
			WebDriverWait wait = new WebDriverWait(driver,120);
			WebElement element=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathVal)));
			//WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVal)));
			
		//	guru99seleniumlink= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "/ht
			
			//WebElement element=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathVal)));
			element.click();
			
			reportStep("The element with xpath: "+xpathVal+" is clicked.", "PASS");
		} 
		
	
		//catch (TimeoutException e) {
			//reportStep("The element with xpath: "+xpathVal+" could not be clicked.Because TimeOut Exception has occured", "FAIL");
		//}
		
		
		catch (Exception e) {
		
		
			reportStep("The element with xpath: "+xpathVal+" could not be clicked.Because Element not found Exception has occured", "FAIL");
			//System.out.println(e.getMessage());
		}
	}
	
	
	public void locateFrame(int frame) {
		try{
			driver.switchTo().frame(frame);
			
			reportStep("Frame has located", "PASS");
		} 
		
		catch (NoSuchFrameException e) {
			reportStep("no such frame exception", "FAIL");
		}
		
		catch (Exception e) {
			reportStep(e.getMessage(), "Fail");
		}
		
		
		
	}
	
	
	
	
	
	
	public void defaultcontent() {
		try{
			driver.switchTo().defaultContent();
			
			reportStep("Now switched to default content", "PASS");
		} 
		
		catch (NoSuchFrameException e) {
			reportStep("no such frame exception", "FAIL");
		}
		
		catch (Exception e) {
			reportStep(e.getMessage(), "Fail");
		}
		
		
		
	}
	
	
	
	
	public void verifyElementpresentByXpathExplicitwait(String xpath){
		try {
			
			WebDriverWait wait = new WebDriverWait(driver,40);
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			
			//String sText = element.getText();
			if (element.isDisplayed()){
				reportStep("Element is Displayed with xpath "+xpath, "PASS");
			}else{
				reportStep("Element is not Displayed with xpath " +xpath, "FAIL");
			}
		}
		
		catch (Exception e) {
			reportStep("Unknown Exception has occured", "FAIL");

		}
	}
	
	public void verifyElementpresentByXpathExplicitw(String xpath){
		try {
			
			WebDriverWait wait = new WebDriverWait(driver,40);
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			
			//String sText = element.getText();
			if (element.isDisplayed()){
				//reportStep("Element is Displayed with xpath "+xpath, "PASS");
			}else{
				//reportStep("Element is not Displayed with xpath " +xpath, "FAIL");
			}
		}
		
		catch (Exception e) {
			//reportStep("Unknown Exception has occured", "FAIL");

		}
	}
	
	
	public void enterByXpathExplict(String xpathVal, String mobileNo) {
		try{
		
			Thread.sleep(2000);
			driver.findElement(By.xpath(xpathVal)).clear();
			WebDriverWait wait = new WebDriverWait(driver,120);
			
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVal)));
			//WebElement element=wait.until(ExpectedConditions.elementToBeClickable((By.xpath(xpathVal))));
			
			
			//WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathVal)));
					element.sendKeys(mobileNo);
			
			reportStep("The element with xpath: "+xpathVal+" is entered.", "PASS");
		}
		
		
		catch (Exception e) {
		
			
			//	reportStep("The data: "+xpathVal+" could not be entered in the field :"+xpathVal, "FAIL");
System.out.println(e);
				reportStep("The data: "+xpathVal+" could not be entered in the field.Because No suchelement exception has occured :", "FAIL");

		}
	}




public void clicklistByxpath(String xpathValue) {
	try {
		List<WebElement> list=driver.findElements(By.xpath(xpathValue));
		Thread.sleep(12000);
		System.out.println(list.size());

		list.get(0).click();		
		
reportStep("The element with id: "+xpathValue+" is clicked.", "PASS");

} 
	
	catch (NoSuchElementException e) {
		reportStep("The element with xpath: "+xpathValue+" could not be clicked.Because NO Such Element Exception has occured", "FAIL");
	}

	catch (TimeoutException e) {
		reportStep("The element with id: "+xpathValue+" could not be clicked.Because Timeout Exception has occured", "FAIL");
	}
		catch (Exception e) {
			System.out.print(e);
			
	reportStep("The element with id: "+xpathValue+" could not be clicked.", "FAIL");
}
}

public void comparevalues(int a,int b) {
	
		if (a!=b){
			reportStep("size is not equal pass", "PASS");
		}else{
			reportStep("size is  equal failed", "FAIL");
		}
	}

public void comparevaluesGreater(int a,int b,String validmsg,String errormsg) {
	
	if (a<b){
		reportStep(validmsg, "PASS");
	}else{
		reportStep(errormsg, "FAIL");
	}
}




public void comparevaluesEqual(int a,int b,String validmsg,String errormsg) {
	
	if (a==b){
		reportStep(validmsg, "PASS");
	}else{
		reportStep(errormsg, "FAIL");
	}
}





public void verifybycheckboxpresent(String xpath, String attributes ,String compText,String validmsg,String errormsg) throws InterruptedException {
	try{
		Thread.sleep(12000);
		System.out.println("atttributes*****"   +attributes);
		String sText = driver.findElementByXPath(xpath).getAttribute(attributes);
System.out.println(sText+ "******");
		if (sText.equalsIgnoreCase(compText)){
			
			reportStep(validmsg+":", "PASS");
		}else{
			System.out.println("******************"+sText+"***************");
			
			
			reportStep(errormsg+":", "FAIL");
		}
	}
	catch (NoSuchElementException e) {
		reportStep("The element with xpath: "+xpath+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
	}
	
	catch (TimeoutException e) {
		reportStep("The element with xpath: "+xpath+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
	}

	catch (Exception e) {
		reportStep("unKnown Exception occur while verify", "FAIL");
	}



}







public void clicklistByxpathExplicitwaitfirst(String xpathValue) {
	try {
				WebDriverWait wait= new WebDriverWait(driver, 120);
List <WebElement> myIput=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathValue)));
	
	
	myIput.get(0).click();
		
		
reportStep("The element with id: "+xpathValue+" is clicked.", "PASS");

} 
	catch (Exception e) {
			reportStep("The element with xpath: "+xpathValue+" could not be clicked.Because NO Such Element Exception has occured", "FAIL");

}
}





public void clicklistByxpathExplicitwait(String xpathValue) {
	try {
		WebDriverWait wait= new WebDriverWait(driver, 80);
	List <WebElement> myIput=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathValue)));
	//List <WebElement> myIput=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathValue)));
	myIput.get(0).click();		
reportStep("The element with id: "+xpathValue+" is clicked.", "PASS");

} 
		catch (Exception e) {
			System.out.print(e);
			reportStep("The element with xpath: "+xpathValue+" could not be clicked.Because NO Such Element Exception has occured", "FAIL");
		}
}


public void clicklistByxpathNosnap(String xpathValue) {
	try {
		List<WebElement> list=driver.findElements(By.xpath(xpathValue));
		Thread.sleep(4000);
		System.out.println(list.size());

		list.get(0).click();	

reportStep("The element with id: "+xpathValue+" is clicked.", "PASS",false);

} 
	
	catch (NoSuchElementException e) {
		reportStep("The element with xpath: "+xpathValue+" could not be clicked.Because NO Such Element Exception has occured", "FAIL",false);
	}

	catch (TimeoutException e) {
		reportStep("The element with id: "+xpathValue+" could not be clicked.Because Timeout Exception has occured", "FAIL",false);
	}
		catch (Exception e) {
	reportStep("The element with id: "+xpathValue+" could not be clicked.", "FAIL",false);
}
}


public void VerifyElementPresent(String xpath) {
	
	WebElement e  =driver.findElement(By.xpath(xpath));
	
	
	if(e.isDisplayed())
	{
		//System.out.println("*********Element is present"+e.getText()+"*********");
		reportStep("The element with xpath: "+xpath+" is present.", "PASS");

	}
	
	else {
	//	System.out.println("*********Element is not present"+e.getText()+"*********");
		reportStep("The element with id: "+xpath+" is not present .", "PASS");

	}
	
	
}
	
public void VerifyElementEnable(String xpath) {
	
	WebElement e  =driver.findElement(By.xpath(xpath));
	
	
	if(e.isEnabled())
	{
		System.out.println("*********Element is present"+e.getText()+"*********");

	}
	
	else {
		System.out.println("*********Element is not present"+e.getText()+"*********");

	}
	
	
}
	


public void VerifyElementPresent(String xpath,String validmsg,String ErrorvalidMsg) {
	
	try {
		WebDriverWait wait = new WebDriverWait(driver,40);
		WebElement e=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	
	if(e.isDisplayed())
	{
		System.out.println("*********Element is present"+e.getText()+"*********");
		reportStep( e.getText() +validmsg, "PASS");

	}
	
	else {
		System.out.println("*********Element is not present"+e.getText()+"*********");
		reportStep(  ErrorvalidMsg , "FAIL");

	}
	
	
	}
	
	
	catch (Exception e) {
		reportStep("Unknown exception occured ", "FAIL");
	}
	
	
	
}






public void VerifyElementcheck(String xpath,String validmsg,String ErrorvalidMsg) {
	
	try {
		WebDriverWait wait = new WebDriverWait(driver,40);
		WebElement e=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	
	if(e.isDisplayed())
	{
		System.out.println("*********Element is present"+e.getText()+"*********");
		reportStep( validmsg, "PASS");
	}
	else {
		reportStep(  ErrorvalidMsg , "FAIL");

	}
	}
	catch (Exception e) {
		reportStep("Unknown exception occured ", "FAIL");
	}
	
	
	
}


public void VerifyElementnotPresent(String xpath,String validmsg,String ErrorvalidMsg) {
	
	try {
	WebElement e  =driver.findElement(By.xpath(xpath));
	
	
	if(e.isDisplayed())
	{
		
		reportStep( "Element is present:" +ErrorvalidMsg, "FAIL");

	}
	
	else {
				reportStep("Element is not present" +validmsg , "PASS");

	}
	
	
	}
	catch ( TimeoutException  e) {
		reportStep("Timeout Exception has occured", "FAIL");

		// TODO: handle exception
	}
	catch ( NoSuchElementException  e) {
		reportStep(   "Element not fount" +ErrorvalidMsg , "PASS");

		// TODO: handle exception
	}
	catch ( ElementNotVisibleException  e) {
		reportStep("Element not fount" +ErrorvalidMsg , "PASS");

		// TODO: handle exception
	}
	
	
	catch (Exception e) {
		reportStep("Unknown exception occured ", "FAIL");
	}
	
	
	
}




















	
	 
	public void enterById(String idValue, String data) {
		try {
			driver.findElement(By.id(idValue)).clear();
			driver.findElement(By.id(idValue)).sendKeys(data);	
			reportStep("The data: "+data+" entered successfully in field :"+idValue, "PASS");
		} 
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+idValue+" could not be entered.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+idValue+" could not be entered.Because TimeOut Exception has occured", "FAIL");
		}
		
		catch (Exception e) {
			reportStep("Unknown exception occured while entering "+data+" in the field :"+idValue, "FAIL");
		}
	}

	
	 
	public void enterByName(String nameValue, String data) {
		try {
			driver.findElement(By.name(nameValue)).clear();
			driver.findElement(By.name(nameValue)).sendKeys(data);	
			reportStep("The data: "+data+" entered successfully in field :"+nameValue, "PASS");

		}
		
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+nameValue+" could not be entered.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+nameValue+" could not be entered.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		 catch (Exception e) {
			reportStep("Unknown exception occured while entering "+data+" in the field :"+nameValue, "FAIL");
		}

	}

	
	 
	public void enterByXpath(String xpathValue, String data) {
		try {
			driver.findElement(By.xpath(xpathValue)).clear();
			driver.findElement(By.xpath(xpathValue)).sendKeys(data);	
			reportStep("The data: "+data+" entered successfully in field :"+xpathValue, "PASS");

		} 
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpathValue+" could not be entered.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpathValue+" could not be entered.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		catch (Exception e) {
			reportStep("Unknown exception occured while entering "+data+" in the field :"+xpathValue, "FAIL");
		}

	}

	
	 
	public boolean verifyTitle(String title){
		boolean bReturn = false;
		String currentUrl = null;
		String url= null;
		try{
			Thread.sleep(4000);
			
			if (driver.getTitle().equalsIgnoreCase(title)){
				
				
				currentUrl=driver.getCurrentUrl();
			 url="  "+currentUrl;
				reportStep("The title of the page matches with the value :"+title+url , "PASS");
				bReturn = true;
			}else
				System.out.println();
				reportStep("The title of the page:"+driver.getTitle()+" did not match with the value :"+title+ url, "SUCCESS");

		}
		
		catch (TimeoutException e) {
			reportStep(" while verifying the title Timeout Exception has occured"+title+ url, "FAIL");
		}
		catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title"+title+ url, "FAIL");
		}
		return bReturn;
	
	}
	
	public boolean verifyelementpresent(String title){
		boolean bReturn = false;
		String currentUrl = null;
		String url= null;
		try{
			Thread.sleep(4000);
			
			if (driver.findElement(By.xpath(title)) != null){
				driver.findElement(By.xpath(title)).click();
				reportStep("The title of the page matches with the value :"+title+url , "PASS");
				bReturn = true;
			}else
				System.out.println();
				reportStep("The title of the page:"+driver.getTitle()+" did not match with the value :"+title+ url, "SUCCESS");

		}
		
		catch (TimeoutException e) {
			reportStep(" while verifying the title Timeout Exception has occured"+title+ url, "FAIL");
		}
		catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title"+title+ url, "FAIL");
		}
		return bReturn;
	
	}

	public boolean verifyTitlecontains(String title){
		boolean bReturn = false;
		String currentUrl = null;
		String url= null;
		try{
			Thread.sleep(4000);
			
			if (driver.getTitle().contains(title)){
				
				
				currentUrl=driver.getCurrentUrl();
			 url="  "+currentUrl;
				reportStep("The title of the page matches with the value :"+title+url , "PASS");
				bReturn = true;
			}else
				System.out.println();
				reportStep("The title of the page:"+driver.getTitle()+" did not match with the value :"+title+ url, "SUCCESS");

		}
		
		catch (TimeoutException e) {
			reportStep(" while verifying the title Timeout Exception has occured"+title+ url, "FAIL");
		}
		catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title"+title+ url, "FAIL");
		}
		return bReturn;
	}
	public void verifyTextByXpath(String xpath, String text){
		try {
			
			
			
			String sText = driver.findElement(By.xpath(xpath)).getText();
			if (sText.equalsIgnoreCase(text)){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	

	

	
	
	public void verifyTextByXpathExplicitwait(String xpath, String text){
		try {
			
			WebDriverWait wait = new WebDriverWait(driver,40);
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			
			String sText = element.getText();
			if (sText.equalsIgnoreCase(text)){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}
		catch (Exception e) {
			reportStep("The :"+text+" could not be Matched.", "FAIL");
		}
	}
	
	
	public void verifyTextByXpathOR(String xpath1,String xpath2, String xpath1text,String xpath2text){
		String verifytextone = null;
		String verifytexttwo = null;
		try {
			
			WebDriverWait wait = new WebDriverWait(driver,40);
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath1)));
			
			
			WebElement element1=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath2)));
		
			
			verifytextone=element.getText();
			verifytexttwo=element1.getText();
			
			 if(element.isDisplayed()&&verifytextone.contains(xpath1text)){
				 
				 reportStep("The text: "+element.getText()+" matches with the value :"+xpath1text, "PASS");
			 }
			 else if (element1.isDisplayed()&&verifytexttwo.contains(xpath2text)) {
				 reportStep("The text: "+element1.getText()+" matches with the value :"+xpath2text, "PASS");				
			}
			
		}
			 catch (Exception e) {
			reportStep("The : "+verifytextone+ "or "+verifytexttwo+ "text does not matched"  , "FAIL");
		}
	}
	
	
	
	
	
	
	
	public void verifyTextContainsByXpathexplictwait(String xpath, String text){
		try{
			WebDriverWait wait = new WebDriverWait(driver,120);
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			String sText = element.getText();
			
						if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not contain the value :"+text, "FAIL");
			}
		}
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
		}
		
		catch (Exception e) {
			reportStep("unknown exception has occured", "FAIL");
		}
	}
	
	public void verifyTextnumberContainsByXpathexplictwait(String xpath, String text){
		try{
			WebDriverWait wait = new WebDriverWait(driver,120);
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			
			String sText = element.getText();

			String veritext=sText.replaceAll("[^0-9]", "");
						if (veritext.contains(text)){
				reportStep("The text: "+veritext+" contains the value :"+text, "PASS");
			}else{
				reportStep("The text: "+veritext+" did not contain the value :"+text, "FAIL");
			}
		}
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
		}
		
		catch (Exception e) {
			reportStep("unknown exception has occured", "FAIL");
		}
	}
	
	public void verifyTextContainsByXpathexplictwaitint(String xpath, String text){
		try{
			WebDriverWait wait = new WebDriverWait(driver,120);
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			String sText = element.getText();
			String comptext=sText.replaceAll("[^0-9]", "");

						if (comptext.contains(text)){
							
							
							
				reportStep("The text: "+comptext+" contains the value :"+text, "PASS");
			}else{
				reportStep("The text: "+comptext+" did not contain the value :"+text, "FAIL");
			}
		}
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
		}
		
		catch (Exception e) {
			reportStep("unknown exception has occured", "FAIL");
		}
	}
	
	
	

	public String ReturnXpathtext(String xpath){
		String sText = null;
		try{
		sText = driver.findElementByXPath(xpath).getText();
		
		}
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be found.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be found.Because TimeOut Exception has occured", "FAIL");
		}
		
		catch (Exception e) {
			reportStep("Unknown exception occured while verify", "FAIL");
		}
		return sText;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	public void verifyTextContainsByXpath(String xpath, String text){
		try{
	WebDriverWait	 wait = new WebDriverWait(driver,120);
		//	elementToBeClickable
			
			//WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		
					
				//	visibilityOfElementLocated(By.xpath(xpath)));
			String sText = element.getText();
		if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not contain the value :"+text, "FAIL");
			}
		}	
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
catch (Exception e) {
			reportStep("Unknown exception occured while verify", "FAIL");
		}
	}
	
	
	public void verifyTextContainsByXpavalidMsg(String xpath, String text,String validmsg,String Errorvalidmsg){
		try{
			String sText = driver.findElementByXPath(xpath).getText();
			if (sText.contains(text)){
				reportStep(validmsg, "PASS");
			}else{
				reportStep(Errorvalidmsg, "FAIL");
			}
		}
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
		}
		
		catch (Exception e) {
			reportStep("Unknown exception occured while verify", "FAIL");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    public void ScrollclickByXpath(String xpath) {
		try{
			JavascriptExecutor JE = driver;  
		    WebElement scroll= driver.findElement(By.xpath(xpath));
		    JE.executeScript("arguments[0].scrollIntoView(true);",scroll);    
		    scroll.click();
		    reportStep("The Page has scrolled element with id: "+xpath+" is clicked.", "PASS");

		} 
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be clicked.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be clicked.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		catch (Exception e) {
			reportStep("The element with id: "+xpath+" could not be clicked.", "FAIL");
		}
	}

    
    
    
    
	
	

	 
	public void verifyTextById(String id, String text) {
		try{
			String sText = driver.findElementById(id).getText();
			if (sText.equalsIgnoreCase(text)){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+id+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+id+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	
	public void verifyTextContainsById(String id, String text) {
		try{
			String sText = driver.findElementById(id).getText();
			if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
			}else{
				reportStep("The text: "+sText+" did not contain the value :"+text, "FAIL");
			}
		}
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+id+" could not be Matched.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+id+" could not be Matched.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}

	
	
	
	
	
	
	
	
	public void verifyTextRadioBoxType(String id, String text) {
		try{
			

			Thread.sleep(4000);
			
			if (id.contains(text)){
				reportStep("The text: "+text+" contains the value with this :"+id+ " So it allows only single locality", "PASS");
			}else{
				reportStep("The text: "+text+" did not contain the value with this:"+id+" So it allows Multiple locality", "FAIL");
			}
		}
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+id+" could not  contain " +text+ ".Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+id+" could not   contain "+text+".Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
	}
	
	
	
	

	 
	public void closeAllBrowsers() {
		try {
		driver.quit();
		} catch (Exception e) {
			reportStep("The browser could not be closed.", "FAIL");
		}

	}
	
	 
	public void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
			reportStep("The browser could not be closed.", "FAIL");
		}
	}


	 
	public void clickById(String id) {
		try{
			driver.findElement(By.id(id)).click();
			reportStep("The element with id: "+id+" is clicked.", "PASS");

		} 
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+id+" could not be clicked.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+id+" could not be clicked.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		catch (Exception e) {
			reportStep("The element with id: "+id+" could not be clicked.", "FAIL");
		}
	}

	
	 
	public void clickByClassName(String classVal) {
		try{
			driver.findElement(By.className(classVal)).click();
			reportStep("The element with class Name: "+classVal+" is clicked.", "PASS");
		}
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+classVal+" could not be clicked.Because NO Such Element Exception has occured", "FAIL");
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+classVal+" could not be clicked.Because TimeOut Exception has occured", "FAIL");
		}
		
		catch (Exception e) {
			reportStep("The element with class Name: "+classVal+" could not be clicked.", "FAIL");
		}
	}

	
	

	
	
	

	
	
	
	
	
	
	
	
	

	public void clickByXpath(String xpathVal) {
		try{
			driver.findElement(By.xpath(xpathVal)).click();
			reportStep("The element : "+xpathVal+" is clicked.", "PASS");
		} 
		
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpathVal+" could not be clicked.Because NO Such Element Exception has occured", "FAIL");
			
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpathVal+" could not be clicked.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		catch (Exception e) {
			reportStep("The element with xpath: "+xpathVal+" could not be clicked.", "FAIL");
		}
	}
	
	
	public void validationMessage(Boolean msg,String valmsg) {
		if(true) {
			reportStep(valmsg, "PASS");

		}
		else {
			reportStep(valmsg, "FAIL");
			
		}
		
	}
	
	
	
	public void clickByXpathcontactFind(String xpathVal) {
	
			driver.findElement(By.xpath(xpathVal)).click();
			//reportStep("The element : "+xpathVal+" is clicked.", "PASS");
		
	}
	public void clickXpathFind(String xpathVal) {
		
		driver.findElement(By.xpath(xpathVal)).click();
		
		
		
	
}
	
	
		
	
	
	
	

	

	
	
	
	
	
	
	
	
	

	public void selectVisibileTextById(String id, String value) {
		try{
			new Select(driver.findElement(By.id(id))).selectByVisibleText(value);;
			reportStep("The element with id: "+id+" is selected with value :"+value, "PASS");
		} catch (Exception e) {
			reportStep("The value: "+value+" could not be selected.", "FAIL");
		}
	}


	public void selectVisibileTextByXPath(String xpath, String value) {
		try{
			new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(value);;
			reportStep("The element with xpath: "+xpath+" is selected with value :"+value, "PASS");
		} 
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+xpath+" could not be found.Because NO Such Element Exception has occured", "FAIL");
			
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+xpath+" could not be found.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		catch (Exception e) {
			reportStep("The value: "+value+" could not be selected.", "FAIL");
		}
	}
	public void selectIndexByxpath(String element, int value) {
		try{
			new Select(driver.findElement(By.xpath(element))).selectByIndex(value);
			reportStep("The element with id: "+element+" is selected with index :"+value, "PASS");
		} 
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+element+" could not be found.Because NO Such Element Exception has occured", "FAIL");
			
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+element+" could not be found.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		
		catch (Exception e) {
			reportStep("The index: "+value+" could not be selected.", "FAIL");
		}
	}

	

	public void selectIndexById(String id, int value) {
		try{
			new Select(driver.findElement(By.id(id))).selectByIndex(value);
			reportStep("The element with id: "+id+" is selected with index :"+value, "PASS");
		} 
		
		catch (NoSuchElementException e) {
			reportStep("The element with xpath: "+id+" could not be found.Because NO Such Element Exception has occured", "FAIL");
			
		}
		
		catch (TimeoutException e) {
			reportStep("The element with xpath: "+id+" could not be found.Because TimeOut Exception has occured", "FAIL");
		}
		
		
		
		
		catch (Exception e) {
			reportStep("The index: "+value+" could not be selected.", "FAIL");
		}
	}

	

	

	public void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be accepted.", "FAIL");
		}

	}

	

	public String getAlertText() {		
		String text = null;
		try {
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be accepted.", "FAIL");
		}
		return text;

	}


	public void dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			reportStep("The alert could not be found.", "FAIL");
		} catch (Exception e) {
			reportStep("The alert could not be accepted.", "FAIL");
		}

	}

	
	
	 
	public long takeSnap(){
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		} catch (WebDriverException e) {
			reportStep("The browser has been closed.", "FAIL");
		} catch (IOException e) {
			reportStep("The snapshot could not be taken", "FAIL");
		}
		return number;
	}

	

	

	
	
	


}
