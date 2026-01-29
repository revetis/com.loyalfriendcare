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

    @FindBy(xpath = "(//*[@id=\"menu\"]/ul/li[3]/ul/li)")
    public List<WebElement> userDepartmentsInHeaderDropdownMenuList;

    @FindBy(xpath = "(//*[@id=\"page\"]/main/div/div[2]/div)")
    public List<WebElement> userHomePageBodyDepartmentsList;

    @FindBy(xpath = "//*[@id=\"page\"]/main/div[2]/div/div/div/div")
    public List<WebElement> departmentsSayfasiList;

    @FindBy(xpath = "//*[@id=\"page\"]/main/div[2]/div/div/div")
    public WebElement departmentsTable;

    @FindBy(xpath = "//*[@id=\"description\"]/h1")
    public WebElement departmanTitle;

    @FindBy(xpath = "(//*[@id=\"sidebar\"]/div/div[1]/form/div[3]/div/div/ul/li)")
    public List<WebElement> appointmentFormDepartmentsList;

    @FindBy(xpath = "(//*[@id=\"sidebar\"]/div/div[1]/form/div[4]/div/div/ul/li)")
    public List<WebElement> appointmentFormDoctorsList;

    @FindBy(xpath = "//*[@id=\"sidebar\"]/div/div[1]/form/div[3]/div/div")
    public  WebElement appointmentFormDepartmentsLink;

    @FindBy(xpath = "//*[@id=\"sidebar\"]/div/div[1]/form/div[4]/div/div")
    public  WebElement appointmentFormDoctorsLink;

    @FindBy(xpath = "//*[@id=\"page\"]/main/div/div[2]")
    public WebElement departmentsList;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul/li[3]/ul")
    public WebElement userDepartmentsinHeaderDropdownMenü;

}