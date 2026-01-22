package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class UserLoyalFriendCare {

    public UserLoyalFriendCare(){
        PageFactory.initElements(Driver.getDriver(),this);

    }
    @FindBy(xpath = "(//*[@class='btn_add'])[1]")
    public WebElement signInButonu;

    @FindBy(id = "//input[@id='email']")
    public WebElement userLoginsayfasiEmailKutusu;

    @FindBy(id = "//input[@id='password']")
    public WebElement userLoginSayfasiPasswordKutusu;

    @FindBy(xpath = "//*[@*='submit']")
    public WebElement userLoginSayfasiLoginButonu;

    @FindBy(xpath = "//*[@*='form-control']")
    public WebElement userAramaCubugu;



}
