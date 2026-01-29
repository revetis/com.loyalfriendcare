package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminCreateDepartmanPage {

    @FindBy(xpath = "(//*[.='Create Departments'])[1]")
    public WebElement adminCreateDepartman;

    @FindBy(xpath = "(//*[@class='form-control '])[1]")
    public WebElement admincreateDepartmanForm1;

    @FindBy(xpath = "//*[@id='select2-color-q4-container']")
    public WebElement admincreateDepartmanForm4;

    @FindBy(xpath = "//*[@type='submit']")
    public WebElement admincreateDepartmanFormSubmit;




}
