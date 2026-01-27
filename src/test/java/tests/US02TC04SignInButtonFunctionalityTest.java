package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US02TC04SignInButtonFunctionalityTest extends TestBaseRapor {

    @Test
    public void signInButtonFunctionalityTest() {

        Layout layout = new Layout();
        LoginPage loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_02_TC_04 - Sign In Butonu İşlevsellik Testi",
                "Sign In butonuna tıklandığında giriş sayfasına yönlendirme ve login testleri");

        extentTest.info("Pre-Condition: Kullanıcı Home Page'de olmalı");

        // 1. Ana sayfaya git
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("1. Ana sayfaya gidildi: " + ConfigReader.getProperty("url"));

        // 2. Sign In butonunun görünür olduğunu doğrula
        ReusableMethods.waitForVisibility(layout.signInLink, 10);
        Assert.assertTrue(layout.signInLink.isDisplayed());
        extentTest.pass("Sign In butonu görünür olduğu doğrulandı");

        // 3. Sign In butonuna tıkla
        ReusableMethods.waitForClickability(layout.signInLink, 10);
        layout.signInLink.click();
        extentTest.info("Sign In butonuna tıklandı");

        // 4. Giriş sayfasına yönlendirildiğini doğrula
        ReusableMethods.bekle(2);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"));
        extentTest.pass("Giriş sayfasına başarıyla yönlendirildi: " + currentUrl);

        // 5. Giriş formunun görünür olduğunu kontrol et
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);
        ReusableMethods.waitForVisibility(loginPage.passwordInput, 10);

        Assert.assertTrue(loginPage.emailAddressInput.isDisplayed());
        Assert.assertTrue(loginPage.passwordInput.isDisplayed());
        extentTest.pass("Giriş formu (email ve password) görünür olduğu doğrulandı");

        // ========================================
        // Test Case 5: Doğru email + Doğru şifre
        // ========================================
        // ========================================
        extentTest.info("5. Doğru email ve doğru şifre ile giriş yap");
        loginPage.emailAddressInput.clear();
        loginPage.emailAddressInput.sendKeys("tural.seyidov.user@loyalfriendcare.com");
        loginPage.passwordInput.clear();
        loginPage.passwordInput.sendKeys("Loyal.123123");
        extentTest.info("Email: tural.seyidov.user@loyalfriendcare.com | Password: Loyal.123123");

        loginPage.signInButton.click();
        extentTest.info("Sign In butonuna tıklandı");

        ReusableMethods.bekle(3);
        String loggedInUrl = Driver.getDriver().getCurrentUrl();

        // Başarılı giriş kontrolü
        if (loggedInUrl.contains("/dashboard") || !loggedInUrl.contains("/login")) {
            extentTest.pass("✅ Doğru bilgilerle giriş başarılı!");

            // Logout yap (sonraki testler için)
            extentTest.info("Logout işlemi yapılıyor...");

            // Ana sayfaya git
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            ReusableMethods.bekle(2);

            // Sign Out butonuna tıkla
            ReusableMethods.waitForClickability(layout.headerAuthUserSignOutButton, 10);
            layout.headerAuthUserSignOutButton.click();
            extentTest.info("Sign Out butonuna tıklandı");

            ReusableMethods.bekle(2);
            extentTest.pass("Logout işlemi başarılı");

        } else {
            extentTest.fail("❌ Doğru bilgilerle giriş başarısız!");
        }

        // ========================================
        // Test Case 6: Doğru email + Yanlış şifre
        // ========================================
        extentTest.info("6. Doğru email ve yanlış şifre ile giriş yap");

        // Sign In sayfasına git
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/login");
        ReusableMethods.bekle(2);

        loginPage.emailAddressInput.clear();
        loginPage.emailAddressInput.sendKeys("tural.seyidov.user@loyalfriendcare.com");
        loginPage.passwordInput.clear();
        loginPage.passwordInput.sendKeys("Loyal.123456");
        extentTest.info("Email: tural.seyidov.user@loyalfriendcare.com | Password: Loyal.123456 (Yanlış)");

        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        // Hala login sayfasında mı kontrol et
        String wrongPassUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(wrongPassUrl.contains("/login"), "Yanlış şifre ile giriş yapılmamalıydı!");
        extentTest.pass("✅ Yanlış şifre ile giriş engellendi");

        // ========================================
        // Test Case 7: Yanlış email + Doğru şifre
        // ========================================
        extentTest.info("7. Yanlış email ve doğru şifre ile giriş yap");

        loginPage.emailAddressInput.clear();
        loginPage.emailAddressInput.sendKeys("wrong.user@loyalfriendcare.com");
        loginPage.passwordInput.clear();
        loginPage.passwordInput.sendKeys("Loyal.123123");
        extentTest.info("Email: wrong.user@loyalfriendcare.com (Yanlış) | Password: Loyal.123123");

        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        String wrongEmailUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(wrongEmailUrl.contains("/login"), "Yanlış email ile giriş yapılmamalıydı!");
        extentTest.pass("✅ Yanlış email ile giriş engellendi");

        // ========================================
        // Test Case 8: Yanlış email + Yanlış şifre
        // ========================================
        extentTest.info("8. Yanlış email ve yanlış şifre ile giriş yap");

        loginPage.emailAddressInput.clear();
        loginPage.emailAddressInput.sendKeys("wrong.user@loyalfriendcare.com");
        loginPage.passwordInput.clear();
        loginPage.passwordInput.sendKeys("Loyal.123456");
        extentTest.info("Email: wrong.user@loyalfriendcare.com (Yanlış) | Password: Loyal.123456 (Yanlış)");

        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        String bothWrongUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(bothWrongUrl.contains("/login"), "Yanlış bilgilerle giriş yapılmamalıydı!");
        extentTest.pass("✅ Yanlış email ve şifre ile giriş engellendi");

        // ========================================
        // Test Case 9: Boş email + Boş şifre
        // ========================================
        extentTest.info("9. Boş email ve boş şifre ile giriş yap");

        loginPage.emailAddressInput.clear();
        loginPage.passwordInput.clear();
        extentTest.info("Email: (boş) | Password: (boş)");

        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        String emptyUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(emptyUrl.contains("/login"), "Boş bilgilerle giriş yapılmamalıydı!");
        extentTest.pass("✅ Boş bilgilerle giriş engellendi");

        // ========================================
        // Test Case 10: Geçersiz email formatı
        // ========================================
        extentTest.info("10. Geçersiz email formatı ile giriş yap");

        loginPage.emailAddressInput.clear();
        loginPage.emailAddressInput.sendKeys("invalidemail");
        loginPage.passwordInput.clear();
        loginPage.passwordInput.sendKeys("Test123!");
        extentTest.info("Email: invalidemail (Geçersiz format) | Password: Test123!");

        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        String invalidUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(invalidUrl.contains("/login"), "Geçersiz email formatı ile giriş yapılmamalıydı!");
        extentTest.pass("✅ Geçersiz email formatı ile giriş engellendi");

        extentTest.pass("🎉 TC_04 testi başarıyla tamamlandı!");
    }
}