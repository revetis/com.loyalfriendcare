package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class UserHomePage {

    public UserHomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // =======================
    // US_002 - HEADER SECTION
    // =======================

    // Header container
    @FindBy(css = "header.header")
    public WebElement header;

    // Logo link
    @FindBy(css = "#logo a")
    public WebElement headerLogo;

    // Logo image
    @FindBy(css = "#logo img.logo_normal")
    public WebElement headerLogoImage;

    // Sign In button
    @FindBy(xpath = "//a[contains(@href,'/login')]")
    public WebElement signInButton;

    // Sign Up button
    @FindBy(xpath = "//a[contains(@href,'/en/register')]")
    public WebElement signUpButton;
}
