package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class RegisterPage {

    //====================================================================
    //====================================================================
    //=========BU DOSYAYA BIR SEY YAZMAYIN VEYA DEGISTIRMEYIN=============
    //====================================================================
    //====================================================================

    public RegisterPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//*[@id=\"top_menu\"]/li[2]/a")
    public WebElement signUpLink;

    @FindBy(xpath = "//*[@id=\"name\"]")
    public WebElement usernameInput;

    @FindBy(xpath = "//*[@id=\"email\"]")
    public WebElement emailAddressInput;

    @FindBy(xpath = "//*[@id=\"password\"]")
    public WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"password\"]")
    public WebElement confirmPasswordInput;
}
