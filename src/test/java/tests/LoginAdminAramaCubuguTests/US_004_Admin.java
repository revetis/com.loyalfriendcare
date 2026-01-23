package tests.LoginAdminAramaCubuguTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminLoyalFriendCare;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.util.List;
import java.util.Properties;




public class US_004_Admin extends TestBaseRapor {

    //==========================================================
    //==========================================================
    //==========     Admin AramaCubugu PozitivTest  ============
    //==========================================================
    //==========================================================

    Properties properties;
    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Sign In butonuna basin
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı gir
    // Arama tusuna click et
    // Sistem ilgili sonuçları icerdigini test edin


    @Test
    public void aramaCubuguTest() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin AramaCubugu PozitivTest");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Sign In butonuna basin
        adminLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
        adminLoyalFriendCare.adminLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("admin_email"));
        adminLoyalFriendCare.adminLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("admin_password"));
        adminLoyalFriendCare.adminLoginSayfasiLoginButonu.click();

        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Distemper");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
    }


    //================================================================
    //================================================================
    //==========     Admin_AramaCubugu_KelimeninBirKismi  ============
    //================================================================
    //================================================================

    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Sign In butonuna basin
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama alanına kelimenin bir kısmını gir
    // Arama tusuna click et
    // Sistem ilgili sonuçları icerdigini test edin


    @Test
    public void aramaCubuguTest1() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_KelimeninBirKismi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));


        // Sign In butonuna basin
        adminLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
        adminLoyalFriendCare.adminLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("admin_email"));
        adminLoyalFriendCare.adminLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("admin_password"));
        adminLoyalFriendCare.adminLoginSayfasiLoginButonu.click();


        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Dis");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
    }



    //================================================================
    //================================================================
    //==========     Admin_AramaCubugu_KelimeninBirKismi  ============
    //================================================================
    //================================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Sign In butonuna basin
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama alanına sistemde olmayan bir kelime gir
    // Arama tusuna click et
    // Sistem kısmi eşleşen sonuçları listelemelidir


    @Test
    public void aramaCubuguTest2() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_KelimeninBirKismi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Sign In butonuna basin
        adminLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
        adminLoyalFriendCare.adminLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("admin_email"));
        adminLoyalFriendCare.adminLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("admin_password"));
        adminLoyalFriendCare.adminLoginSayfasiLoginButonu.click();

        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Nutella");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icermedigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());


    }


    //======================================================================
    //======================================================================
    //==========     Admin_AramaCubugu_Arama Alanini Bos Birak  ============
    //======================================================================
    //======================================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Sign In butonuna basin
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama cubugunu bos birak
    // Arama tusuna click et
    // Sistem ilgili sonuçları icermemelidir


    @Test
    public void aramaCubuguTest3() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_Arama Alanini Bos Birak");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Sign In butonuna basin
        adminLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
        adminLoyalFriendCare.adminLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("admin_email"));
        adminLoyalFriendCare.adminLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("admin_password"));
        adminLoyalFriendCare.adminLoginSayfasiLoginButonu.click();

        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icermedigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());


    }

    //======================================================================
    //======================================================================
    //==========     Admin_AramaCubugu_İlgili hizmet =======================
    //==========    "ilaç veya aşı detay sayfasınin gorunur olmasi  =======
    //======================================================================
    //======================================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Sign In butonuna basin
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı gir
    // Arama tusuna click et
    // Sistem ilgili sonuçları icermelidir
    // Arama sonuçlarından birine tıkla
    // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır


    @Test
    public void aramaCubuguTest4() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_İlgili hizmet, " +
                                                    "ilaç veya aşı detay sayfasınin gorunur olmasi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Sign In butonuna basin
        adminLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
        adminLoyalFriendCare.adminLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("admin_email"));
        adminLoyalFriendCare.adminLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("admin_password"));
        adminLoyalFriendCare.adminLoginSayfasiLoginButonu.click();

        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Vaccinations");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());


        // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır
        // Arama sonuçlarından birine tıkla
        List<WebElement> services = Driver.getDriver().findElements(
                By.cssSelector("div.strip.grid h3 a")
        );

        if (!services.isEmpty()) {
            services.get(1).click();
        } else {
            System.out.println("Heç bir nəticə tapılmadı");
        }
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());







    }



}
