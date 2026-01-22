package pages.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AdminMedicinesPage {

    public AdminMedicinesPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // =======================
    // US_033 - MEDICINES LIST PAGE
    // =======================

    // Medicines table
    @FindBy(xpath = "//table[contains(@class,'table')]")
    public WebElement medicinesTable;

    // Search box
    @FindBy(xpath = "//input[@type='search' or @placeholder='Search']")
    public WebElement medicinesSearchBox;

    // =======================
    // DYNAMIC LOCATORS - MEDICINE ACTIONS
    // =======================

    // Medicine row by name
    public WebElement getMedicineRow(String medicineName) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[.//p[normalize-space()='" + medicineName + "']]")
        );
    }

    // Medicine title/name
    public WebElement getMedicineTitle(String medicineName) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[.//p[normalize-space()='" + medicineName + "']]//p[normalize-space()='" + medicineName + "']")
        );
    }

    // Medicine content/description
    public WebElement getMedicineContent(String medicineName) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[.//p[normalize-space()='" + medicineName + "']]/td[3]")
        );
    }

    // Medicine image
    public WebElement getMedicineImage(String medicineName) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[.//p[normalize-space()='" + medicineName + "']]/td[1]//img")
        );
    }

    // Edit button for specific medicine
    public WebElement getEditMedicineButton(String medicineName) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[.//p[normalize-space()='" + medicineName + "']]/td[4]//*[self::a or self::button]")
        );
    }

    // Delete button for specific medicine
    public WebElement getDeleteMedicineButton(String medicineName) {
        return Driver.getDriver().findElement(
                By.xpath("//tr[.//p[normalize-space()='" + medicineName + "']]/td[5]//*[self::a or self::button]")
        );
    }
}