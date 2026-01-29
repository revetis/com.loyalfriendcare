package tests.LoginAdminAramaCubuguTests;

<<<<<<< HEAD

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.HomepageBodyPage;
import pages.user_pages.*;
=======
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminLoyalFriendCare;
import pages.user_pages.UserLoyalFriendCare;
>>>>>>> main
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;
<<<<<<< HEAD

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Random;


=======
import java.time.Duration;
import java.util.Properties;
>>>>>>> main


public class US_012_User extends TestBaseRapor {


    //==========================================================
    //==========================================================
    //==========  User Randevu Talebinin Olusdurulmasi  ========
    //==========================================================
    //==========================================================



    // https://qa.loyalfriendcare.com/en url sayfasina gidin
    // Sign In butonuna basin
    // User olarak ana sayfaya girili
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Sayfanin body bolumu icerdigi konturol edilir
    // Doktorlarin listelendigini  konturol edin
    // Doktor kartlarinin tiklanabilir olub olmadigi konturol edilir
    // Listeden herhangi bir doktora tiklanir
    // Doktor detay sayfasinin gorunur oldugunu test edin
    //“Appointment Booking” butonunun tiklanabilir oldugunu test edin
    // Randevu formundaki tum zorlu alanlar gecerli olarak doldurulur
    // “Appointment Booking” butonuna click edilir
    // Sistem randevu talebini başarıyla oluşturmalıdır
    // Sistem tarafından gösterilen mesajin gorunur oldugunu konturol edin

    Properties properties;
    UserLoyalFriendCare userLoyalFriendCare =new UserLoyalFriendCare();

<<<<<<< HEAD
    @Test
    public void US_004_UserRandevuTalebi01() {

        UserLoyalFriendCare userLoyalFriendCare =new UserLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 User Randevu Talebinin Olusdurulmasi");
=======

    @Test
    public void US_004_UserAramaCubugu_PozitivTest() {

        UserLoyalFriendCare userLoyalFriendCare =new UserLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 User AramaCubugu PozitivTest");
>>>>>>> main
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Sign In butonuna basin
        userLoyalFriendCare.signInButonu.click();
<<<<<<< HEAD
        ReusableMethods.bekle(1);

        // User olarak siteye girin
=======
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
>>>>>>> main
        userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
        userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
        userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

<<<<<<< HEAD
        // User olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
=======
        // Sayfanin body bolumu icerdigi konturol edilir


        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
>>>>>>> main
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

<<<<<<< HEAD
        // Sayfanin body bolumu icerdigi konturol edilir
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollBy(0,1300)");
        ReusableMethods.bekle(1);
        HomePageBodySection homePageBodySection =new HomePageBodySection();
        Assert.assertTrue(homePageBodySection.popularDoctorsTitle.isDisplayed());

        // Doktorlarin listelendigini  konturol edin
        HomepageBodyPage homepageBodyPage = new HomepageBodyPage();
        Assert.assertTrue(homepageBodyPage.popularDoctorlarListesininGorunurlugu(),
                "Popular doctors listelenmedi!");

        // Doktor kartlarinin tiklanabilir olub olmadigi konturol edilir
        Assert.assertTrue(homepageBodyPage.DoktorKartlarininClickable(),
                "Doktor kartlarindan biri kliklənə bilən deyil!");

        // Listeden herhangi bir doktora tiklanir
        homepageBodyPage.popularDoctorLocate.click();

        // Doktor detay sayfasinin gorunur oldugunu test edin
        DoctorDetailPage doctorDetailPage = new DoctorDetailPage();
        Assert.assertTrue(doctorDetailPage.appointmentBookingButton.isDisplayed());

        //“Appointment Booking” butonunun tiklanabilir oldugunu test edin
        Assert.assertTrue(doctorDetailPage.appointmentBookingButton.isEnabled());

        // Randevu formundaki tum zorlu alanlar gecerli olarak doldurulur
        Actions actions = new Actions(Driver.getDriver());
        WebElement hizmetSecimi = Driver.getDriver().findElement
                (By.xpath("//input[@id='Date']"));
        actions.click(hizmetSecimi)
                .sendKeys("20-2-2026")
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys("+900340340340")
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB).perform();

        WebElement wellDrop = Driver.getDriver().findElement
                  (By.xpath("//div[@class='box_detail booking']//div[3]//div[1]//div[1]"));
        wellDrop.click();
        doctorDetailPage.anycategory.click();
        doctorDetailPage.doctorDetailSelection.click();
        doctorDetailPage.doctorSectionName.click();
        doctorDetailPage.appointmentBookingButton.click();

        // Sistem randevu talebini başarıyla oluşturmalıdır
        doctorDetailPage.appointmentBookingButton.click();

        // Sistem tarafından gösterilen mesajin gorunur oldugunu konturol edin
        Assert.assertTrue(doctorDetailPage.sonucAcceptMessage.isDisplayed());
        ReusableMethods.bekle(5);


=======


        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());
>>>>>>> main
    }


    //================================================================
    //================================================================
    //==========   User Randevu Talebinin Olusdurulmasi   ============
    //================================================================
    //================================================================

    // https://qa.loyalfriendcare.com/en url sayfasina gidin
    // Sign In butonuna basin
    // User olarak ana sayfaya girili
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Sayfanin body bolumu icerdigi konturol edilir
    // Doktorlarin listelenib listelenmedigi konturol edilir
    // Doktor kartlarinin tiklanabilir olub olmadigi konturol edilir
    // Listeden herhangi bir doktora tiklanir
    // Doktor detay sayfasinin gorunur oldugunu test edin
    //“Appointment Booking” butonunun gorunur oldugunu test edin
    // Randevu formundaki  zorunlu alanlari boş bırakılır
    // “Appointment Booking” butonuna click edilir
    // Randevu talebi oluşturulmamalıdır
    // Sistem tarafından gösterilen mesajin gorunur olmadigini konturol edin


    @Test
<<<<<<< HEAD
    public void US_004_UserRandevuTalebi02() {

        UserLoyalFriendCare userLoyalFriendCare =new UserLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 User Randevu Talebi: Zorlu alanlarin bos birakilmasi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Sign In butonuna basin
        userLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(1);

        // User olarak siteye girin
=======
    public void US_004_User_AramaCubugu_KelimeninBirKismi() {


        extentTest = extentReports.createTest("US_004 User_AramaCubugu_KelimeninBirKismi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));


        // Sign In butonuna basin
        userLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
>>>>>>> main
        userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
        userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
        userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

<<<<<<< HEAD
        // User olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
=======

        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
>>>>>>> main
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

<<<<<<< HEAD
        // Sayfanin body bolumu icerdigi konturol edilir
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollBy(0,1300)");
        ReusableMethods.bekle(1);
        HomePageBodySection homePageBodySection =new HomePageBodySection();
        Assert.assertTrue(homePageBodySection.popularDoctorsTitle.isDisplayed());

        // Doktorlarin listelendigini  konturol edin
        HomepageBodyPage homepageBodyPage = new HomepageBodyPage();
        Assert.assertTrue(homepageBodyPage.popularDoctorlarListesininGorunurlugu(),
                "Popular doctors listelenmedi!");

        // Doktor kartlarinin tiklanabilir olub olmadigi konturol edilir
        Assert.assertTrue(homepageBodyPage.DoktorKartlarininClickable(),
                "Doktor kartlarindan biri kliklənə bilən deyil!");

        // Listeden herhangi bir doktora tiklanir
        homepageBodyPage.popularDoctorLocate.click();

        // Doktor detay sayfasinin gorunur oldugunu test edin
        DoctorDetailPage doctorDetailPage = new DoctorDetailPage();
        Assert.assertTrue(doctorDetailPage.appointmentBookingButton.isDisplayed());

        //“Appointment Booking” butonunun tiklanabilir oldugunu test edin
        Assert.assertTrue(doctorDetailPage.appointmentBookingButton.isEnabled());

        // Randevu formundaki tum zorlu alanlar gecerli olarak doldurulur
        Actions actions = new Actions(Driver.getDriver());
        WebElement hizmetSecimi = Driver.getDriver().findElement
                (By.xpath("//input[@id='Date']"));
        actions.click(hizmetSecimi)
                .sendKeys("")
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys("")
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB).perform();

        WebElement wellDrop = Driver.getDriver().findElement
                (By.xpath("//div[@class='box_detail booking']//div[3]//div[1]//div[1]"));
        wellDrop.click();
        doctorDetailPage.anycategory.click();
        doctorDetailPage.doctorDetailSelection.click();
        doctorDetailPage.doctorSectionName.click();
        doctorDetailPage.appointmentBookingButton.click();

        // Sistem randevu talebini başarıyla oluşturmalıdır
        doctorDetailPage.appointmentBookingButton.click();

        // Sistem tarafından gösterilen mesajin gorunur oldugunu konturol edin
        Assert.assertFalse(doctorDetailPage.sonucAcceptMessage.isDisplayed());
        ReusableMethods.bekle(5);
=======
        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        userLoyalFriendCare.userAramaCubugu.sendKeys("Dis");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        userLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

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
    public void US_004_User_AramaCubugu_Yalnis_Kelime() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_004 User_AramaCubugu_Yalnis_Kelime");
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
    public void US_004_User_AramaCubugu_Arama_Alanini_Bos_Birak() {

        extentTest = extentReports.createTest("US_004 User_AramaCubugu_Arama Alanini Bos Birak");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

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

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        userLoyalFriendCare.userAramaCubugu.sendKeys("");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        userLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

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
    public void US_004_User_AramaCubugu_Ilgili_Hizmetler() {

        extentTest = extentReports.createTest("US_004 User_AramaCubugu_İlgili hizmet, " +
                "ilaç veya aşı detay sayfasınin gorunur olmasi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

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

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        userLoyalFriendCare.userAramaCubugu.sendKeys("Vaccinations");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        userLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());

        // Arama sonuçlarından birine tıkla
        userLoyalFriendCare.sonucElementlerindenBiri.click();
        ReusableMethods.bekle(2);

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
    public void US_004_User_AramaCubugu_SonuclarinGelmeSuresi() {

        extentTest = extentReports.createTest("US_004 User_SonuclarinGelmeSuresi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

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

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Arama çubuğunu bos birak
        userLoyalFriendCare.userAramaCubugu.sendKeys("Vaccinations");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        userLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(userLoyalFriendCare.aramaSonucElementi.isDisplayed());

        // Arama sonuçlarından birine tıkla
        userLoyalFriendCare.sonucElementlerindenBiri.click();
        ReusableMethods.bekle(2);

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
>>>>>>> main


    }


}

