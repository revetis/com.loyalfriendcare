package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class DepartmentsPageServices {
    public DepartmentsPageServices(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//h3[normalize-space()='Dermatology']")
    public WebElement dermatologyTitle;

    @FindBy(xpath = "//div[contains(@class,'sub_header_in')]//h1[contains(.,'Dermatology')]")
    public WebElement dermatologyHeaderTitle;


}
