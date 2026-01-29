package pages.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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

public class AdminDoctorsPage {

    public AdminDoctorsPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    // --- Sidebar (Sol Menü) ---
    @FindBy(xpath = "//span[normalize-space()='Doctors']") // Menüdeki ana başlık
    public WebElement sidebarDoctorsMainLink;

    @FindBy(xpath = "//a[.='Doctors']") // Alt menü (Listeleme)
    public WebElement sidebarDoctorsSubLink;

    //Alt Menu Create Doctors Sublink
    @FindBy(xpath = "//a[.='Create Doctors']")
    public WebElement sidebarCreateDoctorsSublink;

    //Doctors List
    @FindBy(xpath = "//table[@id='tableWithSearch']/tbody/tr")
    public List<WebElement> doctorsListRows;

    @FindBy(id = "search-table")
    public WebElement searchInput;

    public void waitForRowsToLoad() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table[@id='tableWithSearch']/tbody/tr"), 0));
        } catch (TimeoutException e) {
            System.out.println("Tablo 10 saniye bekledi ama veri gelmedi.");
        }
    }


    public Map<String, WebElement> getTableRowMap(int row) {
        WebElement rowElement = doctorsListRows.get(row);
        Map<String, WebElement> rowElements = new HashMap<>();

        addIfFound(rowElements, "IMAGE", rowElement, ".//td[1]//img");

        addIfFound(rowElements, "title", rowElement,
                ".//td[2]/p | .//td[2][not(.//p)]");

        addIfFound(rowElements, "content", rowElement,
                ".//td[3]//a | .//td[3]//p | .//td[3][not(.//a or .//p)]");

        addIfFound(rowElements, "editButton", rowElement,
                "//a[.//span[normalize-space()='Edit']] ");
                        // //a[contains(@href,'edit')]z
        // .//td[4]//*[string-length(normalize-space(text())) > 0]

        addIfFound(rowElements, "deleteButton", rowElement,
        "//button[@type='submit']");




        return rowElements;
    }

    private void addIfFound(Map<String, WebElement> rowElements, String IMAGE, WebElement rowElement, String xpath) {
        List<WebElement> elements = rowElement.findElements(By.xpath(xpath));
        if (!elements.isEmpty()) {
            rowElements.put(IMAGE, elements.get(0));
        }
    }

    // Doctor Edit sayfasında Doctors Title alanı
    @FindBy(id = "Title_en")
    public WebElement doctorsTitleInput;

    // Save butonu
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveButton;

    @FindBy(xpath = "//div[@class='alert alert-success']")
    public WebElement successMessage;

}
