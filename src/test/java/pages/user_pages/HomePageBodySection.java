package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HomePageBodySection {
    public HomePageBodySection(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//h2[normalize-space()='Departments']")
    public WebElement departmentsTitle;

    @FindBy(xpath = "//h2[normalize-space()='Popular Doctors']")
    public WebElement popularDoctorsTitle;

    @FindBy(xpath = "//h2[normalize-space()='Vaccinations for Pets']")
    public WebElement vaccinationsTitle;
}
