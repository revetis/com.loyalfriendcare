package pages.user_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;
import java.util.List;

public class UserVaccinationsPage {
    public UserVaccinationsPage(){PageFactory.initElements(Driver.getDriver(), this);}


    @FindBy(xpath = "//div[@class='sub_header_in sticky_header']//h1")
    public WebElement vaccinationsHeader;

    // Tüm aşı kartlarını liste olarak alıyoruz (TC_02 doğrulaması için)
    @FindBy(xpath = "(//div[@class='col-lg-9']//div)[1]")
    public List<WebElement> allVaccinationList;

    // Tüm aşı kartlarında Herhangi bir aşının ismi
    @FindBy(xpath = "//h3")
    public List<WebElement> vaccinationNames;

    // Tüm aşı fotoğrafları-alttaki xpath locator'ına [index] sayı vererek fotolar gezilir
    @FindBy(xpath = "//div[@class='strip grid']//figure//img")
    public List<WebElement> vaccinationsImages;

    // Tüm Aşıların altındaki tarihler - "06-08-2022"
    @FindBy(xpath = "//div[@class='wrapper']//small")
    public List<WebElement> VaccinationsDates;

    // Solda yan menü(sidebar) - Vaccinations listesi
    @FindBy(xpath = "//div[@class='filter_type']//ul")
    public List<WebElement> sidebarVaccinationsList;

    // Solda yan menü(sidebar) - Vaccinations başlığı (tıklanabilir)
    @FindBy(id = "filters_col_bt")
    public List<WebElement> sidebarVaccinationsTitle;

    // Sol yan menüdeki Vaccinations isimleri
    @FindBy(xpath = "//label[@class='container_check']")
    public List<WebElement> sidebarVaccinationsNames;

}
