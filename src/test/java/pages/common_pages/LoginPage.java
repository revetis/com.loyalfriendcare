package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class LoginPage {

    //====================================================================
    //====================================================================
    //=========BU DOSYAYA BIR SEY YAZMAYIN VEYA DEGISTIRMEYIN=============
    //====================================================================
    //====================================================================

    //=============================================
    //=============================================
    //=======     Login Page Locators   ===========
    //=============================================
    //=============================================

    public LoginPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//*[@id=\"email\"]")
    public WebElement emailAddressInput;

    @FindBy(xpath = "//*[@id=\"password\"]")
    public WebElement passwordInput;

    @FindBy(xpath = "/html/body/div/div[2]/div/form/button")
    public WebElement signInButton;

    @FindBy(xpath = "//*[@id=\"checkbox1\"]")
    public WebElement rememberMeCheckbox;

    @FindBy(xpath = "/html/body/div/div[2]/div/form/div[3]/div[1]/div/label")
    public WebElement rememberMeCheckboxText;

    @FindBy(xpath = "/html/body/div/div[2]/div/form/div[3]/div[2]/a")
    public WebElement forgotPasswordLink;










}
