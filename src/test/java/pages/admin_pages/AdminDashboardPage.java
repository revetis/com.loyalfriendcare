package pages.admin_pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AdminDashboardPage {

    public AdminDashboardPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "(//*[@class='text-white'])[1]")
    public WebElement adminDashboardBiriciKutuOzet;

    @FindBy(xpath = "(//*[@class='text-white'])[2]")
    public WebElement adminDashboardBiriciKKutuLearnMore;

    @FindBy(xpath = "(//*[@class='text-white m-t-10'])[1]")
    public WebElement adminDashboardIkinciKutuOzet;

    @FindBy(xpath = "(//*[@class='text-white'])[3]")
    public WebElement adminDashboardIkinciKutuLearnMore;

    @FindBy(xpath = "//*[@class='no-margin text-white p-b-10']")
    public WebElement adminDashboardUcuncuKutuOzet;

    @FindBy(xpath = "(//*[@class='text-white'])[4]")
    public WebElement adminDashboardUcuncuKutuLearnMore;

    @FindBy(xpath = "(//*[@class='text-white m-t-10'])[2]")
    public WebElement adminDashboardDördüncüKutuOzet;

    @FindBy(xpath = "(//*[@class='text-white'])[5]")
    public WebElement adminDashboardDördüncüKutuLearnMore;

    @FindBy(xpath = "(//*[@class='text-white m-t-10'])[3]")
    public WebElement adminDashboardBesinciKutuOzet;

    @FindBy(xpath = "(//*[@class='text-white'])[6]")
    public WebElement adminDashboardBesinciKutuLearnMore;

    @FindBy(xpath = "(//*[@class='text-white m-t-10'])[4]")
    public WebElement adminDashboardAltinciKutuOzet;

    @FindBy(xpath = "(//*[@class='text-white'])[7]")
    public WebElement adminDashboardAltinciKutuLearnMore;

    @FindBy(xpath = "(//*[@class='text-white m-t-10'])[5]")
    public WebElement adminDashboardYedinciKutuOzet;

    @FindBy(xpath = "(//*[@class='text-white'])[8]")
    public WebElement adminDashboardYedinciKutuLearnMore;


}
