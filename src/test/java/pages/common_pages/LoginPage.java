package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class LoginPage {

    //=============================================
    //=============================================
    //=======     Login Page Locators   ===========
    //=============================================
    //=============================================

    public LoginPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id = "email")
    public WebElement emailAddressInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    public WebElement signInButton;

    @FindBy(id = "checkbox1")
    public WebElement rememberMeCheckbox;

    @FindBy(css = "label[for='checkbox1']")
    public WebElement rememberMeCheckboxText;

    @FindBy(xpath = "//a[contains(@href, 'password/reset')]")
    public WebElement forgotPasswordLink;

}