package pages.common_pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class ZiyaretciPage {

    public ZiyaretciPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//*[@*='form-control']")
    public WebElement ziyaretciAramaCubugu;

    @FindBy(xpath = "//input[@type='submit']")
    public WebElement searchButonu;

    @FindBy(xpath = "(//*[@class='btn_add'])[1]")
    public WebElement userPanelineGirisButonu;

    @FindBy(xpath = "//*[@class='sidebar-header']")
    public WebElement userYatakListesi;

    @FindBy(xpath = "(//*[@*='container'])[1]")
    public WebElement aramaSonucElementi;

    @FindBy(xpath = "(//*[@*='img-fluid'])[1]")
    public WebElement sonucElementlerindenBiri;

    @FindBy(xpath = "(//div[@class='container margin_60_35'])[1]/div/div/div")
    public List<WebElement> searchResults;

    public String getResultTitle(int row) {
        return searchResults.get(row).findElement(By.xpath("div/div/div/h3/a")).getText();
    }
}






