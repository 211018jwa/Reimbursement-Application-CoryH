package com.revature.page;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Page object model design pattern
// Represent a webpage as an object
// This class is a blueprint for that object
public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wdw; // Explicit waits..webdriver wait obj
	
	@FindBy(xpath="//input[@id='username']") // PageFactory annotation
	private WebElement usernameInput;
	
	@FindBy(id="myInput") // PageFactory annotation
	private WebElement passwordInput;
	
	@FindBy(id="login-btn") // PageFactory annotation
	private WebElement loginButton;
	
	@FindBy(xpath="//body/form[@id='login-info']/p[1]")
	private WebElement errorMessage;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;	
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(15)); // wait for a maximum of 15 seconds before throwing an exception
		
		// PageFactory initialization
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getUsernameInput() {
		return this.usernameInput;
	}
	
	public WebElement getPasswordInput() {
		return this.passwordInput;
	}
	

	public WebElement getLoginButton() {
		return this.loginButton;
	}
	
	public WebElement getErrorMessage() {
		return this.wdw.until(ExpectedConditions.visibilityOf(this.errorMessage));
	}
	
}