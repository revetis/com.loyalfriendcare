package pages.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

import java.time.Duration;
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

    public void waitForRowsToLoad() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(3));

        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table[@id='tableWithSearch']/tbody/tr"), 0));
        } catch (Exception e) {
            System.out.println("Tablo 3 saniye bekledi ama veri gelmedi. JavaScript yüklenmemiş olabilir.");
        }
    }

    public Map<String, WebElement> getTableRowMap(int row) {
        WebElement rowElement = tableBodyRows.get(row);
        Map<String, WebElement> rowElements = new HashMap<>();

        addIfFound(rowElements, "img", rowElement, ".//td[1]//img");

        addIfFound(rowElements, "title", rowElement,
                ".//td[2]/p | .//td[2][not(.//p)]");

        addIfFound(rowElements, "departments", rowElement,
                ".//td[3]//a | .//td[3]//p | .//td[3][not(.//a or .//p)]");

        addIfFound(rowElements, "availability", rowElement,
                ".//td[4]//*[string-length(normalize-space(text())) > 0]");

        addIfFound(rowElements, "editButton", rowElement,
                ".//td[5]//a");

        addIfFound(rowElements, "deleteButton", rowElement,
                ".//td[6]//button");

        return rowElements;
    }

    private void addIfFound(Map<String, WebElement> map, String key, WebElement parent, String xpath) {
        List<WebElement> elements = parent.findElements(By.xpath(xpath));
        if (!elements.isEmpty()) {
            map.put(key, elements.get(0));
        }
    }

    @FindBy(css = "div.alert-success")
    public WebElement alertSuccess;

    @FindBy(css = "div.alert-danger")
    public WebElement alertDanger;

    @FindBy(xpath = "//tr[@class='odd']/td")
    public WebElement noMatchingFoundWarning;

    @FindBy(xpath = "(//span[contains(@class, 'select2-selection__rendered')])[1]")
    public WebElement formDepartmentSelectRendered;

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
    public WebElement formAvailabilityCheckBox;

    @FindBy(xpath = "(//input[@type='checkbox'])[3]")
    public WebElement formDonyChangeImageCheckbox;

    @FindBy(css = ".thumbnail-wrapper.d48>img")
    public WebElement formImageThumbnail;

    @FindBy(xpath = "(//input[@name='_token'])[3]")
    public WebElement formUploadBedImageInput;

    @FindBy(xpath = "(//input[@name='_token'])[4]")
    public WebElement formUploadBedVideoInput;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveButton;
}