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

    public LoginPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }


    @FindBy(xpath = "//*[@id=\"top_menu\"]/li[1]/a")
    public WebElement signInLink;

    @FindBy(xpath = "//*[@id=\"email\"]")
    public WebElement emailAddressInput;

    @FindBy(xpath = "//*[@id=\"password\"]")
    public WebElement passwordInput;

    @FindBy(xpath = "/html/body/div/div[2]/div/form/button")
    public WebElement signInButton;
}
