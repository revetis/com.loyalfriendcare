package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ResetPassword {

    public ResetPassword(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//*[@class='card-header']")
    public WebElement resetPassword;

    @FindBy(xpath = "//*[@class='form-control ']")
    public WebElement resetPasswordEmailKutusu;

    @FindBy(xpath = "//*[@class='btn btn-primary']")
    public WebElement sendPasswordResetLink;

}
