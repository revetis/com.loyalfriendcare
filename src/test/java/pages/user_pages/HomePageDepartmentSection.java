package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class HomePageDepartmentSection {
    public HomePageDepartmentSection(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    //========================================
    //======Anasayfa Departmanlar bolumu======
    //========================================

    @FindBy(css = ".main_title_3")
    public WebElement departmanTitleSection;

    @FindBy(xpath = "//h2[text()='Departments']/ancestor::div[contains(@class,'container')]/div[contains(@class,'row')]")
    public WebElement departmanBodySection;

    @FindBy(css = ".main_title_3 h2")
    public WebElement departmanTitle;

    @FindBy(css = ".main_title_3 p")
    public WebElement departmanDescription;

    @FindBy(css = ".main_title_3 a")
    public WebElement departmentsLink;

    @FindBy(css = "a.grid_item")
    public List<WebElement> departmentsImageLinks;

    @FindBy(css = "a.grid_item img")
    public List<WebElement> departmentsImages;

    @FindBy(css = "a.grid_item h3")
    public List<WebElement> departmentsImagesTitle;



}