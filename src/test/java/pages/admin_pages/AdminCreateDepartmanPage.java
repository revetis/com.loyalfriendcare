package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AdminCreateDepartmanPage {

    public AdminCreateDepartmanPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "(//*[.='Create Departments'])[1]")
    public WebElement adminCreateDepartman;

    @FindBy(xpath = "(//*[@class='form-control '])[1]")
    public WebElement admincreateDepartmanForm1;

    @FindBy(xpath = "//*[@id='select2-color-uv-container']")
    public WebElement admincreateDepartmanForm4;

    @FindBy(xpath = "//*[@type='submit']")
    public WebElement admincreateDepartmanFormSubmit;

    @FindBy(xpath = "//span[text()='Departments']/ancestor::a")
    public WebElement adminSidebarDepartmentsButton;

    @FindBy(css = "nav.page-sidebar")
    public WebElement adminSidebar;



}
