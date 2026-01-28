package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AdminDoctorsPage {

    public AdminDoctorsPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    // --- Sidebar (Sol Menü) ---
    @FindBy(xpath = "//span[normalize-space()='Doctors']") // Menüdeki ana başlık
    public WebElement sidebarDoctorsMainLink;

    @FindBy(xpath = "//a[.='Doctors']") // Alt menü (Listeleme)
    public WebElement sidebarDoctorsSubLink;




}
