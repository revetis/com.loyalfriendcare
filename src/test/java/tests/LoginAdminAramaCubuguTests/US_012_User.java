package tests.LoginAdminAramaCubuguTests;


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
import pages.common_pages.HomePageBodySection;
import pages.common_pages.HomepageBodyPage;
import pages.user_pages.*;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Random;




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

    @Test
    public void US_004_UserRandevuTalebi01() {

        UserLoyalFriendCare userLoyalFriendCare =new UserLoyalFriendCare();
        extentTest = extentReports.createTest("US_012 User Randevu Talebinin Olusdurulmasi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Sign In butonuna basin
        userLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(1);

        // User olarak siteye girin
        userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
        userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
        userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

        // User olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

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
    public void US_004_UserRandevuTalebi02() {

        UserLoyalFriendCare userLoyalFriendCare =new UserLoyalFriendCare();
        extentTest = extentReports.createTest("US_012 User Randevu Talebi: Zorlu alanlarin bos birakilmasi");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Sign In butonuna basin
        userLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(1);

        // User olarak siteye girin
        userLoyalFriendCare.userLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("user_email"));
        userLoyalFriendCare.userLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("user_password"));
        userLoyalFriendCare.userLoginSayfasiLoginButonu.click();

        // User olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Sayfanin body bolumu icerdigi konturol edilir
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollBy(0,1300)");
        ReusableMethods.bekle(1);
        HomePageBodySection homePageBodySection = new HomePageBodySection();
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


    }



}
