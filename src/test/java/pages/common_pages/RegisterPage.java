package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class RegisterPage {



    //=============================================
    //=============================================
    //=======    Register Page Locators   =========
    //=============================================
    //=============================================

    public RegisterPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id = "name")
    public WebElement usernameInput;

    @FindBy(id = "email")
    public WebElement emailAddressInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(id = "password-confirm")
    public WebElement confirmPasswordInput;

    @FindBy(css = "button[type='submit']")
    public WebElement signUpButton;
}