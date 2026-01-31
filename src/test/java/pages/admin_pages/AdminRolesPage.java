package pages.admin_pages;

import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class AdminRolesPage {

    public AdminRolesPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//tr/td[1]")
    public List<WebElement> rolesList;

    @FindBy(xpath = "//tr/td[1]")
    public List<WebElement> rolesActionsSutunuList;

    @FindBy(xpath = "//*[.='Delete']")
    public WebElement rolesDeleteButton;

    @FindBy(xpath = "//div[@class='pull-right']")
    public WebElement rolesSearchLink;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[2]/a")
    public WebElement dashboardRolesSidebarLink;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[2]/ul/li")
    public List<WebElement> dashboardRolesSidebarList;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]")
    public WebElement adminDashboardSidebarMenü;

    @FindBy(xpath = "//*[@id=\"top_menu\"]/li[1]")
    public WebElement adminUsernameButton;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div[1]/div[1]/div//h3/text()")
    public WebElement rolesPagesTitle;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[2]/ul/li[1]/a")
    public WebElement rolesButton;

    @FindBy(xpath = "//*[@id=\"tableWithSearch\"]")
    public WebElement rolesTable;

    @FindBy(xpath = "/html/body/div[1]/div[1]/div[2]/div[2]/div/a[2]")
    public WebElement editProfileButton;

    @FindBy(xpath = "/html/body/div/div/h1")
    public WebElement text404;

    @FindBy(xpath = "/html/body/div[1]/div[1]/div[2]/div[2]/button/span")
    public WebElement adminProfileIcon;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul")
    public WebElement DashboardMenü;

    @FindBy(xpath = "//*[@id=\"tableWithSearch\"]/tbody/tr[3]/td[4]/form/button")
    public WebElement deleteButton;

    @FindBy(xpath = "//*[@id=\"page\"]/main/div/div[2]/div[1]/a/figure/img")
    public WebElement wellness;
}