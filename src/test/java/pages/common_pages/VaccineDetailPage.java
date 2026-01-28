package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class VaccineDetailPage {

    public VaccineDetailPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//*[@id='page']//h1")
    public WebElement vaccineDetailTitle;


    @FindBy(xpath = "//*[@id='Date']")
    public WebElement dateInput;

    @FindBy(id="serial")
    public WebElement phoneNumberInput;

    @FindBy(xpath = "//select[@name='category_id']/following-sibling::div[contains(@class,'nice-select')]")
    public WebElement wellnessDropdown;

    @FindBy(xpath = "//select[@name='category_id']/following-sibling::div[contains(@class,'nice-select')]//ul/li")
    public List<WebElement> wellnessOptions;

    @FindBy(xpath = "//select[@name='doctor_id']/following-sibling::div[contains(@class,'nice-select')]")
    public WebElement doctorDropdown;

    @FindBy(xpath = "//select[@name='doctor_id']/following-sibling::div[contains(@class,'nice-select')]//ul/li")
    public List<WebElement> doctorOptions;

    @FindBy(xpath = "//textarea[@name='problem' and @placeholder='Create Message']")
    public WebElement messageTextArea;

    @FindBy(id = "submit-contact-detail")
    public WebElement appointmentBookingButton;

    @FindBy(xpath = "//*[@id='sidebar']//div[@role='alert' and contains(@class,'alert-success')]")
    public WebElement successAlert;

    @FindBy(xpath = "//*[@id='sidebar']//div[@role='alert' and contains(@class,'alert-success')]")
    public List<WebElement> successAlerts;

    @FindBy(xpath = "//*[@id='sidebar']/div/div[1]/form")
    public WebElement appointmentForm;

}

