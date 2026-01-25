package pages.common_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class UserMedicinesPage {

    public UserMedicinesPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // =======================
    // US_018 - MEDICINES PAGE (USER SIDE)
    // =======================

    // Medicines menu link
    @FindBy(xpath = "//a[contains(@href,'/medicines') or contains(text(),'Medicines') or contains(text(),'İlaçlar')]")
    public WebElement userMedicinesMenuLink;

    // Medicine list container
    @FindBy(xpath = "//div[contains(@class,'medicine-list') or contains(@class,'medicines')]")
    public WebElement medicineListContainer;

    // Appointment request button
    @FindBy(xpath = "//button[contains(text(),'Randevu') or contains(text(),'Appointment')] | //a[contains(@href,'appointment')]")
    public WebElement appointmentRequestButton;

    // =======================
    // DYNAMIC LOCATORS
    // =======================

    // Get specific medicine by name
    public WebElement getMedicineByName(String medicineName) {
        return Driver.getDriver().findElement(
                By.xpath("//div[contains(@class,'medicine')]//h3[contains(text(),'" + medicineName + "')]/ancestor::div[contains(@class,'medicine')]")
        );
    }
}
