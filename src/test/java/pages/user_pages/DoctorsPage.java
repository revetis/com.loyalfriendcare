package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class DoctorsPage {

    public DoctorsPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //=============================================
    //=============================================
    //=======   Doctors Page Locators   ===========
    //=============================================
    //=============================================

    // Doctors sayfa başlığı
    @FindBy(xpath = "(//div[@class='container'])[1]")
    public WebElement doctorsPageTitle;

    // Doctors listesi - tüm doktorların kartları
    @FindBy(xpath = "(//div[@class='row'])[2]")
    public List<WebElement> doctorsList;

    // Doktor isimleri- alttaki xpath locator'ını () içine alıp [index] sayı vererek Dr.ları gezebilirsin
    @FindBy(xpath = "//h3/a[contains(@href, '/Doctors/')]")
    public List<WebElement> doctorNames;

    // Doktor bilgileri (Profession,Years of exp vb. bilgilerinin oldugu kart alanı)
    @FindBy(xpath = "//div[@class='wrapper']//p")
    public List<WebElement> doctorInfos;

    // Tüm doktor fotoğrafları
    @FindBy(xpath = "//div[@class='strip grid']//figure//img")
    public List<WebElement> doctorPhotos;

    // Tüm doktorların altındaki tarihler - "06-08-2022"
    @FindBy(xpath = "//div[@class='wrapper']//small")
    public List<WebElement> doctorDates;

    // Solda yan menü(sidebar) - Doctors listesi
    @FindBy(xpath = "//div[@class='filter_type']//ul")
    public List<WebElement> sidebarDoctorsList;

    // Solda yan menü(sidebar) - Doctors başlığı (tıklanabilir)
    @FindBy(id = "filters_col_bt")
    public List<WebElement> sidebarDoctorsTitle;

    // Sol yan menüdeki doktor isimleri
    @FindBy(xpath = "//label[@class='container_check']")
    public List<WebElement> sidebarDoctorNames;




}
