package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;
import java.util.List;

public class AdminProfileMenuPage {

    public AdminProfileMenuPage() { PageFactory.initElements(Driver.getDriver(),this);}

    // Admin Dashboard - Sağ üstteki profil menüsü/ikonu
    @FindBy(xpath = "//div[@class='dropdown pull-right d-lg-block d-none']")
    public WebElement adminProfileMenuDropDown;

    // Menü açıldığında görünen seçenekler
    @FindBy(xpath = "(//div[@role='menu']//a)[1]")
    public WebElement settingsLink;

    @FindBy(xpath = "(//div[@role='menu']//a)[2]") // Dosyadaki Edit Profil yerine sitedeki 'Profile'
    public WebElement profileLink;

    @FindBy(xpath = "(//div[@role='menu']//a)[3]")
    public WebElement logoutLink;
}
