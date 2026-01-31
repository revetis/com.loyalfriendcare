package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;
import java.util.List;

public class DoctorDetailPage {

    public Actions categoryDropdown;

    public DoctorDetailPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // =============================================
    // =============================================
    // ==== Doctor Detail Page Locators ========
    // =============================================
    // =============================================

    // ========== DOKTOR BİLGİLERİ ==========

    // Doktor ismi başlık (header) - yeşil alan
    @FindBy(xpath = "(//h1[contains(text(), 'Dr.')])[1]")
    public WebElement doctorHeaderTitle;

    // Doktor fotoğrafı
    @FindBy(xpath = "//img[@class='img-fluid']")
    public WebElement doctorPhoto;

    // Doktor ismi (Dr. Nathan Patel)
    @FindBy(xpath = "(//h1[contains(text(), 'Dr.')])[2]")
    public WebElement doctorName;

    // Tarih bilgisi (06-08-2022)
    @FindBy(xpath = "//div[@class='detail_title_1']")
    public WebElement doctorDate;

    // Doktor detay bilgileri (Profession,Years of exp vb.)
    @FindBy(xpath = "//section[@id='description']//p")
    public WebElement doctorDetailInfos;

    @FindBy(xpath = "//*[@*='Appointment Booking']")
    public WebElement doctorDetailBaslik;

    @FindBy(xpath = "//select[@name='doctor_id']")
    public WebElement doctorSelectionDropdown;

    // ========== APPOINTMENT BOOKING FORMU ==========

    // Appointment Booking başlığı
    @FindBy(xpath = "//h5[.='Appointment Booking']")
    public WebElement appointmentBookingTitle;

    // Tarih seçim input'u (gg-aa-yyyy)
    @FindBy(id = "Date")
    public WebElement DateInput;

    // Phone Number input
    @FindBy(id = "Date")
    public WebElement phoneNumberInput;

    // Wellness dropdown
    @FindBy(xpath = "(//div[@class='nice-select wide'])[2]")
    public WebElement wellnessDropdown;

    // Doctor selection dropdown (Dr. Alejandro Martinez)
    @FindBy(xpath = "//li[contains(text(),'Dr. Olivia Bennett')]")
    public WebElement doctorSectionName;

    @FindBy(xpath = "(//div[@class='nice-select wide'])[3]")
    public WebElement doctorDetailSelection;

    @FindBy(xpath = "//ul[@class='list']/li[@class='option']")
    public List<WebElement> categoryOptions;

    @FindBy(xpath = "//li[contains(text(),'Dermatology')]")
    public WebElement anycategory;

    // Create Message textarea
    @FindBy(xpath = "//textarea[@placeholder='Create Message']")
    public WebElement createMessageTextarea;

    // Appointment Booking butonu (yeşil buton)
    @FindBy(id = "submit-contact-detail")
    public WebElement appointmentBookingButton;

    @FindBy(xpath = "//*[@id=\"sidebar\"]/div/div[1]")
    public WebElement sonucAcceptMessage;

    @FindBy(xpath = "//*[@*='Create Message']")
    public WebElement creatMessage;

    // "No money charged in this step" mesajı
    @FindBy(xpath = "//*[contains(text(), 'No money charged in this step')]")
    public WebElement noChargeMessage;

    @FindBy(xpath = "(//input[@class='form-control'])[1]")
    public WebElement gunAyYil;

    // ========== REVIEWS BOLUMU ==========

    // "0 Reviews" başlığı
    @FindBy(xpath = "//*[contains(text(), 'Reviews')]")
    public WebElement reviewsTitle;

    // "Leave a Review" başlığı
    @FindBy(xpath = "//*[contains(text(), 'Leave a Review')]")
    public WebElement leaveReviewTitle;

    // Rating dropdown
    @FindBy(xpath = "(//div[@class='nice-select wide'])[1]")
    public WebElement ratingDropdown;

    // Your Review textarea
    @FindBy(id = "Comment")
    public WebElement reviewTextarea;

    // Submit butonu (yeşil)
    @FindBy(id = "submit-review")
    public WebElement submitButton;

}