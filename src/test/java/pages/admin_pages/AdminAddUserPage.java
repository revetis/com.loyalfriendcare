package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AdminAddUserPage {

    public AdminAddUserPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // =======================
    // US_028 - CREATE USER FORM
    // =======================

    // Name input
    @FindBy(id = "name")
    public WebElement nameInput;

    // Phone input
    @FindBy(id = "Phone")
    public WebElement phoneInput;

    // Email input
    @FindBy(id = "email")
    public WebElement emailInput;

    // Password input
    @FindBy(id = "password")
    public WebElement passwordInput;

    // Confirm password input
    @FindBy(id = "password_confirmation")
    public WebElement confirmPasswordInput;

    // User role checkbox
    @FindBy(xpath = "//input[@type='checkbox' and @name='roles[]']/following-sibling::label[normalize-space()='User']")
    public WebElement userRoleCheckbox;

    // Save button
    @FindBy(xpath = "//button[@type='submit']//span[normalize-space()='save']/ancestor::button")
    public WebElement saveButton;
}