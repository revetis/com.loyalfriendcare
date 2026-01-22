package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class HomepageBodyPage {
    public HomepageBodyPage (){PageFactory.initElements(Driver.getDriver(),this);}

    //=============================================
    //=============================================
    //==========     Body Locators   ============
    //=============================================
    //=============================================

    // Karşılama metni başlığı
    @FindBy(xpath = "//div[@class='container text-center']/h3")
    public WebElement welcomeTextTitle;

    // Karşılama metni
    @FindBy(xpath = "//div[@class='container text-center']/p")
    public WebElement welcomeText;

    // Search bar ve button
    @FindBy(xpath = "//input[@name='search']")
    public WebElement searchBar;

    @FindBy(xpath = "//input[@value='Search']")
    public WebElement searchButton;

    // Body bölümleri
    @FindBy(xpath = "//div[@class='main_title_3'][1]")
    public WebElement departmentsSection;

    @FindBy(xpath = "//main//div[@class='row add_bottom_30'][1]")
    public List<WebElement> departmentsItems;

    @FindBy(xpath = "//div[@class='main_title_3'][2]")
    public WebElement popularDoctorsSection;

    @FindBy(xpath = "//main//div[@class='row add_bottom_30'][2]")
    public List<WebElement> popularDoctorsItems;

    @FindBy(xpath = "//div[@class='main_title_3'][3]")
    public WebElement vaccinationsSection;

    @FindBy(xpath = "//main//div[@class='row']")
    public List<WebElement> vaccinationsItems;

    // Scroll to top button
    @FindBy(xpath = "//div[@id='toTop']")
    public WebElement scrollToTopButton;
}
