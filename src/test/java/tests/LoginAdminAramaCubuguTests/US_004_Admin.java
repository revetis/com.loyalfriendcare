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
import java.util.List;
import java.util.Properties;


public class US_004_Admin extends TestBaseRapor {

    //==========================================================
    //==========================================================
    //==========     Admin AramaCubugu PozitivTest  ============
    //==========================================================
    //==========================================================


    // Uc farkli test method'u olusturun
    // https://qa.loyalfriendcare.com/en url sayfasina gidi
    // Sign In butonuna basin
    // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı gir
    // Arama tusuna click et
    // Sistem ilgili sonuçları icerdigini test edin
    AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
    Properties properties;
    UserLoyalFriendCare userLoyalFriendCare = new UserLoyalFriendCare();

    @Test
    public void US_004_Admin_AramaCubugu_PozitivTest() {

        extentTest = extentReports.createTest("US_004 Admin AramaCubugu PozitivTest");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(3);

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
        ReusableMethods.bekle(2);

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Distemper");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
        ReusableMethods.bekle(1);
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
    public void US_004Admin_AramaCubugu_KelimeninBirKismi02() {
        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_KelimeninBirKismi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(2);


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
        ReusableMethods.bekle(3);

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Dis");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
        ReusableMethods.bekle(3);
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
    public void US_004Admin_AramaCubugu_Yalnis_Kelime03() {
        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_Yalnis_Kelime");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(3);

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
        ReusableMethods.bekle(2);

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Nutella");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icermedigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
        ReusableMethods.bekle(3);


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
    public void US_004_Admin_AramaCubugu_Arama_Alanini_Bos_Birak04() {
        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_Arama Alanini Bos Birak");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(3);

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
        ReusableMethods.bekle(2);

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icermedigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
        ReusableMethods.bekle(3);


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
    public void US_004_Admin_AramaCubugu_Ilgili_Hizmetler05() {
        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_İlgili hizmet, " +
                "ilaç veya aşı detay sayfasınin gorunur olmasi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(2);

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
        ReusableMethods.bekle(2);

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Vaccinations");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());


        // Arama sonuçlarından birine tıkla
        adminLoyalFriendCare.sonucElementlerindenBiri.click();
        ReusableMethods.bekle(2);

        // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
        ReusableMethods.bekle(3);


    }


    //======================================================================
    //======================================================================
    //==========   Admin_AramaCubugu_SonuclarinGelmeSuresi   ===============
    //======================================================================
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
    // Arama sonuçları en geç 2 saniye içinde görüntülenmelidir


    @Test
    public void US_004_Admin_AramaCubugu_SonuclarinGelmeSuresi06() {
        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 Admin_AramaCubugu_SonuclarinGelmeSuresi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(2);

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
        ReusableMethods.bekle(2);

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Vaccinations");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());


        // Arama sonuçlarından birine tıkla
        adminLoyalFriendCare.sonucElementlerindenBiri.click();
        ReusableMethods.bekle(2);

        // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());

        // Arama sonuçları en geç 2 saniye içinde görüntülenmelidir
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
        long startTime = System.currentTimeMillis();

        By resultsLocator = By.xpath("(//*[@*='container'])[1]");
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2));

        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsLocator));
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Results loaded in: " + duration + " ms");


        Assert.assertTrue(duration <= 2000, "Results took longer than 2 seconds!");
        ReusableMethods.bekle(3);


    }



        //==========================================================
        //==========================================================
        //==========     User AramaCubugu PozitivTest  ============
        //==========================================================
        //==========================================================


        // Uc farkli test method'u olusturun
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        // Sign In butonuna basin
        // Ayrilikda admin, uye, ziyaterci olaral siteye  gidip
        // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı gir
        // Arama tusuna click et
        // Sistem ilgili sonuçları icerdigini test edin





        @Test
        public void US_004_UserAramaCubugu_PozitivTest01() {

            UserLoyalFriendCare userLoyalFriendCare = new UserLoyalFriendCare();
            extentTest = extentReports.createTest("US_004 User AramaCubugu PozitivTest");
            // https://qa.loyalfriendcare.com/en url sayfasina gidi
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            ReusableMethods.bekle(2);

            // Sign In butonuna basin
            userLoyalFriendCare.signInButonu.click();
            ReusableMethods.bekle(2);

            // Admin olarak siteye girin
            userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
            userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
            userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

            // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
            String expextedUrlİcerik = "qa.loyalfriendcare.com";
            String actualUrl = Driver.getDriver().getCurrentUrl();
            ReusableMethods.bekle(2);

            Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

            // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
            userLoyalFriendCare.userAramaCubugu.sendKeys("Distemper");
            ReusableMethods.bekle(2);

            // Arama tusuna click et
            userLoyalFriendCare.searchButonu.click();
            ReusableMethods.bekle(2);

            // Sistem ilgili sonuçları icerdigini test edin
            Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());
            ReusableMethods.bekle(3);
        }


        //================================================================
        //================================================================
        //==========     User_AramaCubugu_KelimeninBirKismi  ============
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
        public void US_004_User_AramaCubugu_KelimeninBirKismi02() {

            UserLoyalFriendCare userLoyalFriendCare = new UserLoyalFriendCare();
            extentTest = extentReports.createTest("US_004 User_AramaCubugu_KelimeninBirKismi");
            // https://qa.loyalfriendcare.com/en url sayfasina gidi
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            ReusableMethods.bekle(1);


            // Sign In butonuna basin
            userLoyalFriendCare.signInButonu.click();
            ReusableMethods.bekle(1);

            // Admin olarak siteye girin
            userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
            userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
            userLoyalFriendCare.userLoginSayfasiLoginButonu.click();


            // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
            String expextedUrlİcerik = "qa.loyalfriendcare.com";
            String actualUrl = Driver.getDriver().getCurrentUrl();
            ReusableMethods.bekle(1);

            Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

            // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
            userLoyalFriendCare.userAramaCubugu.sendKeys("Dis");
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            userLoyalFriendCare.searchButonu.click();
            ReusableMethods.bekle(1);

            // Sistem ilgili sonuçları icerdigini test edin
            Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());

        }


        //================================================================
        //================================================================
        //==========     User_AramaCubugu_KelimeninBirKismi  ============
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
        public void US_004_User_AramaCubugu_Yalnis_Kelime03() {

            UserLoyalFriendCare userLoyalFriendCare = new UserLoyalFriendCare();
            extentTest = extentReports.createTest("US_004 User_AramaCubugu_Yalnis_Kelime");
            // https://qa.loyalfriendcare.com/en url sayfasina gidi
            Driver.getDriver().get(ConfigReader.getProperty("url"));

            // Sign In butonuna basin
            userLoyalFriendCare.signInButonu.click();
            ReusableMethods.bekle(1);

            // Admin olarak siteye girin
            userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
            userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
            userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

            // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
            String expextedUrlİcerik = "qa.loyalfriendcare.com";
            String actualUrl = Driver.getDriver().getCurrentUrl();
            ReusableMethods.bekle(2);

            Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

            // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
            userLoyalFriendCare.userAramaCubugu.sendKeys("Nutella");
            ReusableMethods.bekle(2);

            // Arama tusuna click et
            userLoyalFriendCare.searchButonu.click();
            ReusableMethods.bekle(2);

            // Sistem ilgili sonuçları icermedigini test edin
            Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());
            ReusableMethods.bekle(3);


        }


        //======================================================================
        //======================================================================
        //==========     User_AramaCubugu_Arama Alanini Bos Birak  ============
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
        public void US_004_User_AramaCubugu_Arama_Alanini_Bos_Birak04() {

            UserLoyalFriendCare userLoyalFriendCare = new UserLoyalFriendCare();
            extentTest = extentReports.createTest("US_004 User_AramaCubugu_Arama Alanini Bos Birak");
            // https://qa.loyalfriendcare.com/en url sayfasina gidi
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            ReusableMethods.bekle(1);

            // Sign In butonuna basin
            userLoyalFriendCare.signInButonu.click();
            ReusableMethods.bekle(1);

            // Admin olarak siteye girin
            userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
            userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
            userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

            // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
            String expextedUrlİcerik = "qa.loyalfriendcare.com";
            String actualUrl = Driver.getDriver().getCurrentUrl();
            ReusableMethods.bekle(1);

            Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

            // Arama çubuğunu bos birak
            userLoyalFriendCare.userAramaCubugu.sendKeys("");
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            userLoyalFriendCare.searchButonu.click();
            ReusableMethods.bekle(1);

            // Sistem ilgili sonuçları icermedigini test edin
            Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());



        }

        //======================================================================
        //======================================================================
        //==========     User_AramaCubugu_İlgili hizmet =======================
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
        public void US_004_User_AramaCubugu_Ilgili_Hizmetler05() {

            UserLoyalFriendCare userLoyalFriendCare = new UserLoyalFriendCare();
            extentTest = extentReports.createTest("US_004 User_AramaCubugu_İlgili hizmet, " +
                    "ilaç veya aşı detay sayfasınin gorunur olmasi");
            // https://qa.loyalfriendcare.com/en url sayfasina gidi
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            ReusableMethods.bekle(1);

            // Sign In butonuna basin
            userLoyalFriendCare.signInButonu.click();
            ReusableMethods.bekle(1);

            // Admin olarak siteye girin
            userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
            userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
            userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

            // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
            String expextedUrlİcerik = "qa.loyalfriendcare.com";
            String actualUrl = Driver.getDriver().getCurrentUrl();
            ReusableMethods.bekle(1);

            Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

            // Arama çubuğunu bos birak
            userLoyalFriendCare.userAramaCubugu.sendKeys("Vaccinations");
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            userLoyalFriendCare.searchButonu.click();
            ReusableMethods.bekle(1);

            // Sistem ilgili sonuçları icerdigini test edin
            Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());

            // Arama sonuçlarından birine tıkla
            userLoyalFriendCare.sonucElementlerindenBiri.click();
            ReusableMethods.bekle(1);

            // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır
            Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());


            long startTime = System.currentTimeMillis();

            By resultsLocator = By.xpath("(//*[@*='container'])[1]");

            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultsLocator));

            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;
            System.out.println("Results loaded in: " + duration + " ms");


            Assert.assertTrue(duration <= 2000, "Results took longer than 2 seconds!");
            ReusableMethods.bekle(1);


        }


        //======================================================================
        //======================================================================
        //==========   User_AramaCubugu_SonuclarinGelmeSuresi   ===============
        //======================================================================
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
        // Arama sonuçları en geç 2 saniye içinde görüntülenmelidir

        @Test
        public void US_004_User_AramaCubugu_SonuclarinGelmeSuresi06() {

            UserLoyalFriendCare userLoyalFriendCare = new UserLoyalFriendCare();
            extentTest = extentReports.createTest("US_004 User_SonuclarinGelmeSuresi");
            // https://qa.loyalfriendcare.com/en url sayfasina gidi
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            ReusableMethods.bekle(1);

            // Sign In butonuna basin
            userLoyalFriendCare.signInButonu.click();
            ReusableMethods.bekle(1);

            // Admin olarak siteye girin
            userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
            userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
            userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

            // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
            String expextedUrlİcerik = "qa.loyalfriendcare.com";
            String actualUrl = Driver.getDriver().getCurrentUrl();
            ReusableMethods.bekle(1);

            Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

            // Arama çubuğunu bos birak
            userLoyalFriendCare.userAramaCubugu.sendKeys("Vaccinations");
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            userLoyalFriendCare.searchButonu.click();
            ReusableMethods.bekle(1);

            // Sistem ilgili sonuçları icerdigini test edin
            Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());

            // Arama sonuçlarından birine tıkla
            userLoyalFriendCare.sonucElementlerindenBiri.click();
            ReusableMethods.bekle(1);

            // İlgili hizmet, ilaç veya aşı detay sayfası açılmalıdır
            Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());

            // Arama sonuçları en geç 2 saniye içinde görüntülenmelidi
            long startTime = System.currentTimeMillis();

            By resultsLocator = By.xpath("(//*[@*='container'])[1]");

            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultsLocator));

            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;
            System.out.println("Results loaded in: " + duration + " ms");


            Assert.assertTrue(duration <= 2000, "Results took longer than 2 seconds!");
            ReusableMethods.bekle(1);

        }



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
        ZiyaretciPage ziyaretciPage =new ZiyaretciPage();


        @Test
        public void US_004_ZiyaretciAramaCubugu_PozitivTest01() {

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
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            ziyaretciPage.searchButonu.click();
            ReusableMethods.bekle(1);

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
        public void US_004_Ziyaretci_AramaCubugu_KelimeninBirKismi02() {

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
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            ziyaretciPage.searchButonu.click();
            ReusableMethods.bekle(1);

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
        public void US_004_Ziyaretci_AramaCubugu_Yalnis_Kelime03() {

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
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            ziyaretciPage .searchButonu.click();
            ReusableMethods.bekle(1);

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
        public void US_004_Ziyaretci_AramaCubugu_Arama_Alanini_Bos_Birak04() {

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
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            ziyaretciPage.searchButonu.click();
            ReusableMethods.bekle(1);

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
        public void US_004_Ziyaretci_AramaCubugu_Ilgili_Hizmetler05() {

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
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            ziyaretciPage.searchButonu.click();
            ReusableMethods.bekle(1);

            // Sistem ilgili sonuçları icerdigini test edin
            Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());

            // Arama sonuçlarından birine tıkla
            ziyaretciPage.sonucElementlerindenBiri.click();
            ReusableMethods.bekle(1);

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
        public void US_004_Ziyaretci_AramaCubugu_SonuclarinGelmeSuresi06() {

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
            ReusableMethods.bekle(1);

            // Arama tusuna click et
            ziyaretciPage.searchButonu.click();
            ReusableMethods.bekle(1);

            // Sistem ilgili sonuçları icerdigini test edin
            Assert.assertTrue(ziyaretciPage.aramaSonucElementi.isDisplayed());

            // Arama sonuçlarından birine tıkla
            ziyaretciPage.sonucElementlerindenBiri.click();
            ReusableMethods.bekle(1);

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




