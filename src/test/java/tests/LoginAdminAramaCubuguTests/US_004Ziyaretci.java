package tests.LoginAdminAramaCubuguTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminLoyalFriendCare;
import pages.common_pages.ZiyaretciPage;
import pages.user_pages.UserLoyalFriendCare;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;
import java.time.Duration;
import java.util.Properties;


public class US_004Ziyaretci extends TestBaseRapor {


    //==========================================================
    //==========================================================
    //========== Ziyaretci AramaCubugu PozitivTest  ============
    //==========================================================
    //==========================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı gir
    // Arama tusuna click et
    // Sistem ilgili sonuçları icerdigini test edin
    Properties properties;
    ZiyaretciPage ziyaretciPage =new ZiyaretciPage();


    @Test
    public void US_004_ZiyaretciAramaCubugu_PozitivTest() {

        ZiyaretciPage ziyaretciPage =new ZiyaretciPage();
        extentTest = extentReports.createTest("US_004 Ziyaretci AramaCubugu PozitivTest");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));


        // Ziyaretci olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        ziyaretciPage.ziyaretciAramaCubugu.sendKeys("Distemper");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        ziyaretciPage.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());
    }


    //================================================================
    //================================================================
    //==========     Ziyaretci AramaCubugu KelimeninBirKismi  ========
    //================================================================
    //================================================================

    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama alanına kelimenin bir kısmını gir
    // Arama tusuna click et
    // Sistem ilgili sonuçları icerdigini test edin


    @Test
    public void US_004_Ziyaretci_AramaCubugu_KelimeninBirKismi() {

        ZiyaretciPage ziyaretciPage =new ZiyaretciPage();
        extentTest = extentReports.createTest("US_004 Ziyaretci_AramaCubugu_KelimeninBirKismi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));


        // ziyaretci olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        ziyaretciPage.ziyaretciAramaCubugu.sendKeys("Dis");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        ziyaretciPage.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());
    }



    //================================================================
    //================================================================
    //==========  Ziyaretci_AramaCubugu_Yalnis Kelime  ===============
    //================================================================
    //================================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama alanına sistemde olmayan bir kelime gir
    // Arama tusuna click et
    // Sistem kısmi eşleşen sonuçları listelemelidir


    @Test
    public void US_004_Ziyaretci_AramaCubugu_Yalnis_Kelime() {

        ZiyaretciPage ziyaretciPage =new ZiyaretciPage();
        extentTest = extentReports.createTest("US_004 Ziyaretci_AramaCubugu_Yalnis_Kelime");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));


        // Ziyaretci olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna yalnis  bir hizmet / ilaç / aşı adı girin
        ziyaretciPage.ziyaretciAramaCubugu.sendKeys("Nutella");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        ziyaretciPage .searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icermedigini test edin
        Assert.assertTrue(ziyaretciPage .aramaSonucElementi.isDisplayed());


    }


    //======================================================================
    //======================================================================
    //==========  Ziyaretci AramaCubugu_Arama Alanini Bos Birak  ===========
    //======================================================================
    //======================================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama cubugunu bos birak
    // Arama tusuna click et
    // Sistem ilgili sonuçları icermemelidir


    @Test
    public void US_004_Ziyaretci_AramaCubugu_Arama_Alanini_Bos_Birak() {

        ZiyaretciPage ziyaretciPage =new ZiyaretciPage();
        extentTest = extentReports.createTest("US_004 Ziyaretci_AramaCubugu_Arama Alanini Bos Birak");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));


        // Ziyaretci olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        ziyaretciPage.ziyaretciAramaCubugu.sendKeys("");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        ziyaretciPage.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icermedigini test edin
        Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());


    }

    //======================================================================
    //======================================================================
    //==========   Ziyaretci_AramaCubugu_İlgili hizmet =====================
    //==========   "ilaç veya aşı detay sayfasınin gorunur olmasi  =========
    //======================================================================
    //======================================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı gir
    // Arama tusuna click et
    // Sistem ilgili sonuçları icermelidir
    // Arama sonuçlarından birine tıkla
    // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır


    @Test
    public void US_004_Ziyaretci_AramaCubugu_Ilgili_Hizmetler() {

        ZiyaretciPage ziyaretciPage =new ZiyaretciPage();
        extentTest = extentReports.createTest("US_004 Ziyaretci_AramaCubugu_İlgili hizmet, " +
                "ilaç veya aşı detay sayfasınin gorunur olmasi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Ziyaretci olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        ziyaretciPage.ziyaretciAramaCubugu.sendKeys("Vaccinations");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        ziyaretciPage.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());

        // Arama sonuçlarından birine tıkla
        ziyaretciPage.sonucElementlerindenBiri.click();
        ReusableMethods.bekle(2);

        // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır
        Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());


        long startTime = System.currentTimeMillis();

        By resultsLocator = By.xpath("(//*[@*='container'])[1]");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsLocator));

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Results loaded in: " + duration + " ms");


        Assert.assertTrue(duration <= 2000, "Results took longer than 2 seconds!");


    }


    //======================================================================
    //======================================================================
    //==========   Ziyaretci_AramaCubugu_SonuclarinGelmeSuresi   ===========
    //======================================================================
    //======================================================================
    //======================================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı gir
    // Arama tusuna click et
    // Sistem ilgili sonuçları icermelidir
    // Arama sonuçlarından birine tıkla
    // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır
    // Arama sonuçları en geç 2 saniye içinde görüntülenmelidir

    @Test
    public void US_004_Ziyaretci_AramaCubugu_SonuclarinGelmeSuresi() {

        ZiyaretciPage ziyaretciPage =new ZiyaretciPage();
        extentTest = extentReports.createTest("US_004 Ziyaretci_SonuclarinGelmeSuresi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Ziyaretci olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        ziyaretciPage.ziyaretciAramaCubugu.sendKeys("Vaccinations");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        ziyaretciPage.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());

        // Arama sonuçlarından birine tıkla
        ziyaretciPage.sonucElementlerindenBiri.click();
        ReusableMethods.bekle(2);

        // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır
        Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());

        // Arama sonuçları en geç 2 saniye içinde görüntülenmelidi
        long startTime = System.currentTimeMillis();

        By resultsLocator = By.xpath("(//*[@*='container'])[1]");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsLocator));

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Results loaded in: " + duration + " ms");


        Assert.assertTrue(duration <= 2000, "Results took longer than 2 seconds!");


    }


}


