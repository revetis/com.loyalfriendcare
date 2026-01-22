package pages.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BedmanagersPage {
    public BedmanagersPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    //========================================
    //==== AdminPanel Bedmanager Sayfasi =====
    //========================================

    @FindBy(css = ".inner h3")
    public WebElement pageTitle;

    @FindBy(id = "search-table")
    public WebElement searchInput;

    @FindBy(xpath = "//table[@id='tableWithSearch']/tbody/tr")
    public List<WebElement> tableBodyRows;

    public Map<String, WebElement> getTableRowMap(int row) {
        List<WebElement> cells = tableBodyRows.get(row).findElements(By.tagName("td"));
        Map<String, WebElement> rowElements = new HashMap<>();

        try {
            rowElements.put("img", cells.get(0).findElement(By.tagName("img")));
            rowElements.put("title", cells.get(1).findElement(By.tagName("p")));
            rowElements.put("departments", cells.get(2).findElement(By.tagName("a")));

            if (cells.size() > 3) rowElements.put("availability", cells.get(3));
            if (cells.size() > 4) rowElements.put("editButton", cells.get(4).findElement(By.tagName("a")));
            if (cells.size() > 5) rowElements.put("deleteButton", cells.get(5).findElement(By.tagName("button")));

        } catch (Exception e) {
            System.out.println("Error occured: "+e.getMessage());
        }

        return rowElements;
    }

    @FindBy(css = "div.alert-success")
    public WebElement alertSuccess;

    @FindBy(css = "div.alert-danger")
    public WebElement alertDanger;

    @FindBy(xpath = "//tr[@class='odd']/td")
    public WebElement noMatchingFoundWarning;

    //Edit page
    @FindBy(xpath = "//div[@class='card-body']/h3")
    public WebElement editPageTitle;

    @FindBy(xpath = "//form[@role='form']")
    public WebElement editForm;

    @FindBy(xpath = "//input[@name='Title_en']")
    public WebElement formManagersTitleEn;

    @FindBy(xpath = "//textarea[@class='note-codable']")
    public WebElement formContentTextArea;

    @FindBy(xpath = "//a[@href='#collapseTwo']")
    public WebElement formFrenchLangAccordionButton;

    @FindBy(xpath = "//a[@href='#collapseThree']")
    public WebElement formArabicLangAccordionButton;

    @FindBy(name = "Title_fr")
    public WebElement formFrenchLangTitle;

    @FindBy(name = "body_fr")
    public WebElement formFrenchLangContentTextArea;

    @FindBy(name = "Title_ar")
    public WebElement formArabicLangTitle;

    @FindBy(name = "body_ar")
    public WebElement formArabicLangContentTextArea;

    @FindBy(xpath = "(//span[@class='select2-selection select2-selection--single'])[1]")
    public WebElement formDepartmentSelect;

    @FindBy(xpath = "(//ul[@role='tree'])[1]")
    public List<WebElement> formDepartmentSelectOptions;

    @FindBy(xpath = "(//span[@class='select2-selection select2-selection--single'])[2]")
    public WebElement formDoctorsSelect;

    @FindBy(xpath = "(//ul[@role='tree'])[2]")
    public List<WebElement> formDoctorsSelectOptions;

    @FindBy(xpath = "(//span[@class='select2-selection select2-selection--single'])[3]")
    public WebElement formMedicinesSelect;

    @FindBy(xpath = "(//ul[@role='tree'])[3]")
    public List<WebElement> formMedicinesSelectOptions;

    @FindBy(xpath = "//input[@placeholder='Bed Price']")
    public WebElement formBedPriceInput;

    @FindBy(xpath = "(//input[@type='checkbox'])[2]")
    public WebElement formAvailiblityCheckBox;

    @FindBy(xpath = "(//input[@type='checkbox'])[3]")
    public WebElement formDonyChangeImageCheckbox;

    @FindBy(xpath = "(//input[@name='_token'])[3]")
    public WebElement formUploadBedImageInput;

    @FindBy(xpath = "(//input[@name='_token'])[4]")
    public WebElement formUploadBedVideoInput;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveButton;
}