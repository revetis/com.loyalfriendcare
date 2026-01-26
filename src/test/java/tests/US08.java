package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;

public class US08 extends TestBaseRapor {

    Layout layout = new Layout();
    LoginPage loginPage = new LoginPage();


    @Test
    public void US08_KullaniciGirisTest(){

        extentTest = extentReports.createTest("Kullanici giris testi");

        // https://qa.loyalfriendcare.com/en anasayfaya gidin
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Assert.assertTrue(layout.signInLink.isDisplayed());

        // Sing In butonuna tıklanıp login sayfası açılmalı
        layout.signInLink.click();

        // Kullanıcı E-mail adres kutusuna mail adresini girebilmeli

        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("user_email"));

        // Kullanıcı passwor kutusuna passwor'dü girebilmeli

        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("user_password"));

        // Sing In butonuna tıklayıp login olmalı

        loginPage.signInButton.click();








    }
}
