package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AlertMessageLocators {

    public AlertMessageLocators() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // =======================
    // COMMON ELEMENTS - SUCCESS/ERROR MESSAGES
    // =======================

    // Success message
    @FindBy(xpath = "//div[contains(@class,'alert-success') or contains(@class,'toast-success')]")
    public WebElement successMessage;

    // Error message
    @FindBy(xpath = "//div[contains(@class,'alert-danger') or contains(@class,'toast-error')]")
    public WebElement errorMessage;

    // General alert/message text
    @FindBy(xpath = "//div[contains(@class,'alert')]//span | //div[contains(@class,'toast')]//span")
    public WebElement messageText;
}