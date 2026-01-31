package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminDoctorsPage;
import pages.admin_pages.AdminLoyalFriendCare;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;

public class US32 extends TestBaseRapor {

    Layout layout = new Layout();
    LoginPage loginPage = new LoginPage();
    AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
    AdminDoctorsPage adminDoctorsPage = new AdminDoctorsPage();

    @Test
    public void US24_doktorOlusturma(){

        extentTest = extentReports.createTest("Doktor olusturma testi");

        // https://qa.loyalfriendcare.com/en anasayfaya gidin
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Assert.assertTrue(layout.signInLink.isDisplayed());

        // Sing In butonuna tıklanıp login sayfası açılmalı
        layout.signInLink.click();

        // Admin E-mail adres kutusuna mail adresini girebilmeli
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));

        // Admin password kutusuna passwor'dü girebilmeli

        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));

        // Sing In butonuna tıklayıp login olmalı

        loginPage.signInButton.click();

        // Ana sayfada sag üst kösedeki isminin yazili oldugu butona tiklayın
        adminLoyalFriendCare.adminPanelineGirisButonu.click();

        // Sol acilir pencereye gidin
        layout.adminSidebar.click();

        // Acilan sekmede Create Doktors tiklayin
        adminDoctorsPage.sidebarDoctorsMainLink.click();
        adminDoctorsPage.sidebarCreateDoctorsSublink.click();

        // Gerekli bilgileri girin
        adminDoctorsPage.doctorsTitleInput.sendKeys("Dr.Hasan");
        adminDoctorsPage.doctorsContent.sendKeys("Hasans");

        // Doktors save butonuna tıklayın
        adminDoctorsPage.saveButton.click();

        // Doktorlar listesi ve yeni eklenen doktor görütülendigini dogrulayın

        adminDoctorsPage.successMessage.isDisplayed();



    }
}
