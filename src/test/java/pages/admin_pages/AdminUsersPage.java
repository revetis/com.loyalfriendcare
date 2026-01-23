package pages.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AdminUsersPage {

    public AdminUsersPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // =======================
    // US_025 - USERS LIST PAGE
    // =======================

    // Users table
    @FindBy(xpath = "//table[contains(@class,'table')]")
    public WebElement usersTable;

    // Search box
    @FindBy(xpath = "//input[@type='search' or @placeholder='Search' or contains(@class,'search')]")
    public WebElement usersSearchBox;

    // Add User button
    @FindBy(xpath = "//a[contains(@href,'Users/create') and contains(@class,'btn-success')]")
    public WebElement addUserButton;

    // =======================
    // DYNAMIC LOCATORS - USER ACTIONS
    // =======================

    // Get user row by name/email/identifier
    public WebElement getUserRow(String userIdentifier) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[contains(.,'" + userIdentifier + "')]")
        );
    }

    // Edit button for specific user
    public WebElement getUserEditButton(String userIdentifier) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[contains(.,'" + userIdentifier + "')]//a[contains(@href,'edit') or contains(@title,'Edit') or contains(@title,'Düzenle')]")
        );
    }

    // Delete button for specific user
    public WebElement getUserDeleteButton(String userIdentifier) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[contains(.,'" + userIdentifier + "')]//button[contains(@class,'delete') or contains(@title,'Delete') or contains(@title,'Sil')]")
        );
    }
}