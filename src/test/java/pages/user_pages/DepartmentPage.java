package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class DepartmentPage {
    public DepartmentPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    //========================================
    //========= Departman Sayfasi ============
    //========================================

    @FindBy(css = ".box_detail.booking")
    public WebElement departmentFormContainer;

    @FindBy(name = "Contact")
    public WebElement departmentForm;

    @FindBy(css = ".price h5")
    public WebElement departmentFormTitle;

    // --- INPUTS ---
    @FindBy(id = "Date")
    public WebElement departmentFormDateInput;

    @FindBy(id = "serial")
    public WebElement departmentFormPhoneNumberInput;

    @FindBy(xpath = "//select[@name='category_id']/following-sibling::div")
    public WebElement fakeDepartmentDropdown;

    @FindBy(xpath = "//select[@name='doctor_id']/following-sibling::div")
    public WebElement fakeDoctorDropdown;

    @FindBy(name = "category_id")
    public WebElement hiddenDepartmentSelect;

    @FindBy(name = "doctor_id")
    public WebElement hiddenDoctorSelect;

    @FindBy(name = "problem")
    public WebElement departmentFormTextArea;

    @FindBy(id = "submit-contact-detail")
    public WebElement departmentFormSubmitButton;

    // --- LABELS  ---
    // Her bir inputun bulunduğu "form-group" div'inin içindeki label etiketini arar.
    // HTML'de bu etiketler olmadığı için test sırasında NoSuchElementException fırlatacaktır.

    @FindBy(xpath = "//input[@id='Date']/parent::div/label")
    public WebElement departmentFormDateLabel;

    @FindBy(xpath = "//input[@id='serial']/parent::div/label")
    public WebElement departmentFormPhoneLabel;

    @FindBy(xpath = "//select[@name='category_id']/ancestor::div[contains(@class,'form-group')]/label")
    public WebElement departmentFormDeptLabel;

    @FindBy(xpath = "//select[@name='doctor_id']/ancestor::div[contains(@class,'form-group')]/label")
    public WebElement departmentFormDoctorLabel;

    @FindBy(xpath = "//textarea[@name='problem']/parent::div/label")
    public WebElement departmentFormMessageLabel;

}