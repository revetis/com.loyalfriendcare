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

    @FindBy(id = "Date")
    public WebElement departmentFormDateInput;

    @FindBy(id = "serial")
    public WebElement departmentFormPhoneNumberInput;

    @FindBy(name = "category_id")
    public WebElement departmentFormDepartmentSelect;

    @FindBy(name = "doctor_id")
    public WebElement departmentFormDoctorSelect;

    @FindBy(name = "problem")
    public WebElement departmentFormTextArea;

    @FindBy(id = "submit-contact-detail")
    public WebElement departmentFormSubmitButton;

}