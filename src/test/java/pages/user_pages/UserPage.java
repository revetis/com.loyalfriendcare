package pages.user_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class UserPage {

    public UserPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy (xpath = "//*[.=\" Sign Out\"]")
    public WebElement userPageSignOutLink;

    @FindBy(xpath = "(//a[@class='btn_add'])[1]")
    public WebElement userPageUserNameLink;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul/li[3]/ul")
    public WebElement userDepartmentsInHeaderDropdownMenu;

    @FindBy(xpath = "//*[@id=\"page\"]/main/div/div[2]")
    public WebElement departmentsList;

    @FindBy(xpath = "//*[@id=\"page\"]/main/div[2]/div/div/div")
    public List<WebElement> departmentsSayfasiList;
}
