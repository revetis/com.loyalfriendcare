package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

import java.time.Duration;
import java.util.List;
import java.util.Random;

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

    @FindBy(xpath = "//div[@class='main_title_3'][3]")
    public WebElement vaccinationsSection;

    @FindBy(xpath = "//main//div[@class='row']")
    public List<WebElement> vaccinationsItems;

    // Scroll to top button
    @FindBy(xpath = "//div[@id='toTop']")
    public WebElement scrollToTopButton;

    @FindBy(xpath = "//main//div[@class='row add_bottom_30'][2]")
    public List<WebElement> popularDoctorsItems;

    @FindBy(xpath = "//*[@*='Dr. Marcus Rodriguez']")
    public WebElement popularDoctorLocate ;

    public boolean DoktorKartlarininClickable() {
        for (WebElement each : popularDoctorsItems) {
            if (!each.isDisplayed() || !each.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    public boolean popularDoctorlarListesininGorunurlugu() {
        return popularDoctorsItems.size()>0;
    }

    // Listeden random doktora klik edir
    public void clickRandomDoctor() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfAllElements(popularDoctorsItems));

        int randomIndex = new Random().nextInt(popularDoctorsItems.size());
        popularDoctorsItems.get(randomIndex).click();
    }

    public void clickRandomPopularDoctor() {
        if (popularDoctorsItems.isEmpty()) {
            System.out.println("Popular doctors list is empty!");
            return;
        }
        Random random = new Random();
        int index = random.nextInt(popularDoctorsItems.size()); // 0-dan size()-1-ə qədər random index
        WebElement randomDoctor = popularDoctorsItems.get(index);
        randomDoctor.click(); // Random doktoru klikləyir
    }


}
