package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminCreateMedicinesPage {
    @FindBy(xpath = "(//*[.='Create Medicines'])[1]")
    public WebElement adminCreateMedicines;

    @FindBy(xpath = "//*[@id='Title_en']")
    public WebElement adminCreateMedicinesForm1;

    @FindBy(xpath = "//*[@type='submit']")
    public WebElement admincreateDepartmanFormSubmit;


}
