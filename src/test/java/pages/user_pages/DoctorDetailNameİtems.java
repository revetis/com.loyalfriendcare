package pages.user_pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ReusableMethods;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class DoctorDetailNameİtems {

    WebDriver driver;
    WebDriverWait wait;

    public DoctorDetailNameİtems(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    @FindBy(xpath = "(//div[@class='nice-select wide'])[2]")
    public WebElement doctorDetailSelection;

    @FindBy(xpath = "(//ul[@class='list'])[2]")
    public List <WebElement> doctorDetailOptions;

    // Dropdown özü
    @FindBy(xpath = "//div[contains(@class,'nice-select')]")
    WebElement categoryDropdown;

    // Açılan option-lar
    @FindBy(xpath = "//ul[@class='list']/li[@class='option']")
    List<WebElement> categoryOptions;

    // Random option seçib klikləyən metod
    public void selectRandomCategory() {

        // Dropdown açılır
        wait.until(ExpectedConditions.elementToBeClickable(doctorDetailSelection)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(doctorDetailOptions));

        // Dropdown açılır
        wait.until(ExpectedConditions.elementToBeClickable(categoryDropdown)).click();

        // Option-ların görünməsini gözləyir
        wait.until(ExpectedConditions.visibilityOfAllElements(categoryOptions));

        // Random seçim
        Random random = new Random();
        int index1 = random.nextInt(doctorDetailOptions.size());
        int index = random.nextInt(categoryOptions.size());

        WebElement selectedOption1 = doctorDetailOptions.get(index1);
        WebElement selectedOption = categoryOptions.get(index);
        System.out.println("Selected option: " + selectedOption.getText());

        // Klik
        selectedOption1.click();
        selectedOption.click();
    }


}



































































