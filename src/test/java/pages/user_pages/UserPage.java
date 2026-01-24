package pages.user_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class UserPage {

    public UserPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy (xpath = "//*[.=\" Sign Out\"]")
    public WebElement UserPageSignOutLink;

    @FindBy(xpath = "(//a[@class='btn_add'])[1]")
    public WebElement userPageUserNameLink;

}
