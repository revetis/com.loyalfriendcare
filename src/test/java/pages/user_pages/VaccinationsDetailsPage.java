package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;
import java.util.List;

public class VaccinationsDetailsPage {
    public VaccinationsDetailsPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //=============================================
    //====   Vaccination Detail Page Locators   ========
    //=============================================

    // Aşı ismi başlık (header) - yeşil alan
    @FindBy(xpath = "//div[@class='container']//h1")
    public WebElement vaccinationHeaderTitle;

    // Aşı fotoğrafı
    @FindBy(xpath = "//img[@class='img-fluid']")
    public WebElement vaccinationPhoto;

    //Aşı ismi ( rabies vaccine)
    @FindBy(xpath = "//*[@id='description']//h1" )
    public WebElement vaccinationName;

    // Tarih bilgisi (06-08-2022)
    @FindBy(xpath = "//div[@class='detail_title_1']")
    public WebElement vaccinationCardDate;

    //Aşı detay bilgisi
    @FindBy(xpath = "//section[@id='description']//p")
    public WebElement vaccinationDetailInfo;

    // ========== APPOINTMENT BOOKING FORMU ==========

    // Appointment Booking başlığı
    @FindBy(xpath = "//h5[.='Appointment Booking']")
    public WebElement appointmentBookingTitle;

    // Tarih seçim input'u (gg-aa-yyyy)
    @FindBy(id = "Date")
    public WebElement DateInput;

    // Phone Number input
    @FindBy(id = "serial")
    public WebElement phoneNumberInput;

    // Wellness dropdown
    @FindBy(xpath = "(//div[@class='nice-select wide'])[2]")
    public WebElement wellnessDropdown;

    // Doctor selection dropdown (Dr. Alejandro Martinez)
    @FindBy(xpath = "(//div[@class='nice-select wide'])[3]")
    public WebElement doctorSelectionDropdown;

    // Create Message textarea
    @FindBy(xpath = "//textarea[@placeholder='Create Message']")
    public WebElement createMessageTextarea;

    // Appointment Booking butonu (yeşil buton)
    @FindBy(id = "submit-contact-detail")
    public WebElement appointmentBookingButton;

    // Success message
    @FindBy(xpath = "//*[contains(text(),'Congratulations') or contains(text(),'success') or contains(@class,'alert-success')]")
    public WebElement successMessage;


}
