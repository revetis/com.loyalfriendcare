package tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.HomepageBodyPage;
import pages.common_pages.Layout;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;

public class US06 extends TestBaseRapor {

    Layout layout = new Layout();

    @Test(priority = 0)
    public void TC01_FooterGorunurlukKontrolu() {
        Layout layout = new Layout();
        extentTest = extentReports.createTest("TC_01 Footer Görünürlük Testi",
                "Ana sayfanın en altında Footer bölümünün varlığını doğrular.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Uygulama ana sayfası açıldı.");

        // Footer ana bölüm görünürlüğü
        Assert.assertTrue(layout.footerLogo.isDisplayed() || layout.footerDescription.isDisplayed(),
                "HATA: Footer bölümü sayfada görüntülenemedi!");
        extentTest.info("Footer bölümünün görünürlüğü fiziksel olarak doğrulandı.");

        // Footer bölümleri (Departments, Social, Contacts) görünürlük kontrolleri
        Assert.assertTrue(layout.footerDepartmentsSectionHeader.isDisplayed(),
                "HATA: Footer Departments başlığı görünür değil!");
        extentTest.info("Footer Departments başlığı görünür.");

        Assert.assertTrue(layout.footerSocialMenuSectionHeader.isDisplayed(),
                "HATA: Footer Follow Us başlığı görünür değil!");
        extentTest.info("Footer Follow Us başlığı görünür.");

        Assert.assertTrue(layout.footerContactsSectionHeader.isDisplayed(),
                "HATA: Footer Contacts başlığı görünür değil!");
        extentTest.info("Footer Contacts başlığı görünür.");

        extentTest.pass("TC_01 Footer görünürlük testi başarıyla tamamlandı.");
    }











}
