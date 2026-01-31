package pages.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class AdminLoyalFriendCare {

    public AdminLoyalFriendCare() {
        PageFactory.initElements(Driver.getDriver(), this);

    }

    @FindBy(xpath = "(//*[@class='btn_add'])[1]")
    public WebElement signInButonu;

    @FindBy(xpath = "//input[@id='email']")
    public WebElement adminLoginsayfasiEmailKutusu;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement adminLoginSayfasiPasswordKutusu;

    @FindBy(xpath = "//*[@*='submit']")
    public WebElement adminLoginSayfasiLoginButonu;

    @FindBy(xpath = "//*[@*='form-control']")
    public WebElement adminAramaCubugu;

    @FindBy(xpath = "//input[@type='submit']")
    public WebElement searchButonu;

    @FindBy(xpath = "(//*[@class='btn_add'])[1]")
    public WebElement adminPanelineGirisButonu;

    @FindBy(xpath = "//*[@class='sidebar-header']")
    public WebElement adminYatakListesi;


    @FindBy(xpath = "(//*[@*='container'])[1]")
    public WebElement AramaSonucElementi;

    @FindBy(xpath = "(//*[@*='img-fluid'])[1]")
    public WebElement sonucElementlerindenBiri;

    @FindBy(xpath = "(//div[@class='container margin_60_35'])[1]/div/div/div")
    public List<WebElement> searchResults;

    @FindBy(xpath = "//div[@class='sidebar-header']")
    public WebElement adminSidebarHeader;

    @FindBy(xpath = "//span[normalize-space()='Doctors']")
    public WebElement doctorElementSidebar;

    @FindBy(xpath = "//a[normalize-space()='Create Doctors']")
    public WebElement createDoctorButton;

    @FindBy(xpath = "//input[@id='Title_en']")
    public WebElement newDoctorCreateGap1;

    @FindBy(xpath = "//input[@id='body_en']")
    public WebElement getNewDoctorCreateGap2;

    @FindBy(xpath = "//*[@type='submit']")
    public WebElement doctorSave;

    @FindBy(xpath = "//span[normalize-space()='Doctors Store successfully.']")
    public WebElement doctorStoreSuccess;

    @FindBy(xpath = "(//*[@*='container'])[1]")
    public WebElement aramaSonucElementi;

    public String getResultTitle(int row) {
        return searchResults.get(row).findElement(By.xpath("div/div/div/h3/a")).getText();
    }
}
