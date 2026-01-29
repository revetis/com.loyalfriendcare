package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import pages.common_pages.ResetPassword;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;

public class US09 extends TestBaseRapor {

    Layout layout = new Layout();
    LoginPage loginPage = new LoginPage();
    ResetPassword resetPassword = new ResetPassword();

    @Test
    public void US09_sifreSifirlama(){

        extentTest = extentReports.createTest("Sifre sifirlama testi");

        // https://qa.loyalfriendcare.com/en anasayfaya gidin
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Assert.assertTrue(layout.signInLink.isDisplayed());

        // Sing In butonuna tıklanıp login sayfası açılmalı
        layout.signInLink.click();

        //Login formunun sağ altında bulunan Forgot Password butonu tıklayın
        loginPage.forgotPasswordLink.click();

        // Reset Password sayfasının açıldığı görünmeli

        String expectedIsimIcerik = "Reset Password";
        String actualIcerik = resetPassword.resetPassword.getText();

        Assert.assertTrue(actualIcerik.contains(expectedIsimIcerik));

        // Kullanıcı E-mail adres kutusuna mail adresini girebilmeli

        resetPassword.resetPasswordEmailKutusu.sendKeys(ConfigReader.getProperty("email"));

        // Send Password Reset Ling butonu tıklanır durumda olmalı ve sifre sıfırlanmalı

        resetPassword.sendPasswordResetLink.click();



    }

}
