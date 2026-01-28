package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class CreateBedPage {
    public CreateBedPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id = "Title_en")
    public WebElement formInputEn;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement submitButton;
}
