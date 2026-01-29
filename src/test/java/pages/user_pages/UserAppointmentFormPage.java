package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class UserAppointmentFormPage {

        public UserAppointmentFormPage() {
            PageFactory.initElements(Driver.getDriver(), this);
        }

        // Form container
        @FindBy(css = ".box_detail.booking")
        public WebElement appointmentFormContainer;

        // Date input
        @FindBy(id = "Date")
        public WebElement dateInput;

        // Phone number input
        @FindBy(id = "serial")
        public WebElement phoneNumberInput;

        // Department select
        @FindBy(name = "category_id")
        public WebElement departmentSelect;

        // Doctor select
        @FindBy(name = "doctor_id")
        public WebElement doctorSelect;

        // Message/Problem textarea
        @FindBy(name = "problem")
        public WebElement messageTextArea;

        // Submit button
        @FindBy(id = "submit-contact-detail")
        public WebElement submitButton;

        // Success message
        @FindBy(xpath = "//*[contains(text(),'Congratulations') or contains(text(),'success') or contains(@class,'alert-success')]")
        public WebElement successMessage;
    }
