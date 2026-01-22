package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.RegisterPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US02TC05SignUpButtonFunctionalityTest extends TestBaseRapor {

    @Test
    public void signUpButtonFunctionalityTest() {

        Layout layout = new Layout();
        RegisterPage registerPage = new RegisterPage();

        extentTest = extentReports.createTest("US_02_TC_05 - Sign Up Butonu İşlevsellik Testi",
                "Sign Up butonuna tıklandığında kayıt sayfasına yönlendirme ve kayıt testleri");

        extentTest.info("Pre-Condition: Kullanıcı Home Page'de olmalı ve kayıt olmamış olmalı");

        // 1. Sign Up butonunun yeşil renkte görünür olduğunu doğrula
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("1. Header'daki Sign Up butonunun yeşil renkte görünür olduğunu doğrula");

        ReusableMethods.waitForVisibility(layout.signUpLink, 10);
        Assert.assertTrue(layout.signUpLink.isDisplayed());
        extentTest.pass("Sign Up butonu görünür olduğu doğrulandı");

        // 2. Sign Up butonuna tıkla
        extentTest.info("2. Sign Up butonuna tıkla");
        ReusableMethods.waitForClickablility(layout.signUpLink, 10);
        layout.signUpLink.click();
        extentTest.info("Sign Up butonuna tıklandı");

        // 3. Kayıt sayfasına yönlendirildiğini doğrula
        extentTest.info("3. Kayıt sayfasına yönlendirildiğini doğrula");
        ReusableMethods.bekle(2);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/register"));
        extentTest.pass("Kayıt sayfasına başarıyla yönlendirildi: " + currentUrl);

        // 4. Kayıt formunun görünür olduğunu kontrol et
        extentTest.info("4. Kayıt formunun (ad, email, password alanları) görünür olduğunu kontrol et");
        ReusableMethods.waitForVisibility(registerPage.usernameInput, 10);
        ReusableMethods.waitForVisibility(registerPage.emailAddressInput, 10);
        ReusableMethods.waitForVisibility(registerPage.passwordInput, 10);
        ReusableMethods.waitForVisibility(registerPage.confirmPasswordInput, 10);

        Assert.assertTrue(registerPage.usernameInput.isDisplayed());
        Assert.assertTrue(registerPage.emailAddressInput.isDisplayed());
        Assert.assertTrue(registerPage.passwordInput.isDisplayed());
        Assert.assertTrue(registerPage.confirmPasswordInput.isDisplayed());
        extentTest.pass("Kayıt formu tüm alanlarıyla görünür olduğu doğrulandı");

        // ========================================
        // Test Case 5: Tüm alanları geçerli verilerle doldur
        // ========================================
        extentTest.info("5. Tüm alanları geçerli verilerle doldur ve kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("TestUser123");
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("testuser@test.com");
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("Test123!@");
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("Test123!@");
        extentTest.info("User: TestUser123 | Email: testuser@test.com | Pass: Test123!@ | Confirm: Test123!@");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(3);

        String registeredUrl = Driver.getDriver().getCurrentUrl();
        if (!registeredUrl.contains("/register")) {
            extentTest.pass("✅ Geçerli bilgilerle kayıt başarılı!");
        } else {
            extentTest.fail("❌ Geçerli bilgilerle kayıt başarısız!");
        }

        // Session temizle
        Driver.getDriver().manage().deleteAllCookies();
        ReusableMethods.bekle(1);

        // ========================================
        // Test Case 6: Boş username
        // ========================================
        extentTest.info("6. Boş user name, dolu diğer alanlarla kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("test@test.com");
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("Test123!@");
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("Test123!@");
        extentTest.info("User: (boş) | Email: test@test.com | Pass: Test123!@");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String emptyUsernameUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(emptyUsernameUrl.contains("/register"), "Boş username ile kayıt yapılmamalıydı!");
        extentTest.pass("✅ Boş username ile kayıt engellendi");

        // ========================================
        // Test Case 7: Boş email
        // ========================================
        extentTest.info("7. Boş email, dolu diğer alanlarla kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("TestUser");
        registerPage.emailAddressInput.clear();
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("Test123!@");
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("Test123!@");
        extentTest.info("User: TestUser | Email: (boş) | Pass: Test123!@");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String emptyEmailUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(emptyEmailUrl.contains("/register"), "Boş email ile kayıt yapılmamalıydı!");
        extentTest.pass("✅ Boş email ile kayıt engellendi");

        // ========================================
        // Test Case 8: Boş password
        // ========================================
        extentTest.info("8. Boş password, dolu diğer alanlarla kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("TestUser");
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("test@test.com");
        registerPage.passwordInput.clear();
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("Test123!@");
        extentTest.info("User: TestUser | Email: test@test.com | Pass: (boş) | Confirm: Test123!@");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String emptyPasswordUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(emptyPasswordUrl.contains("/register"), "Boş password ile kayıt yapılmamalıydı!");
        extentTest.pass("✅ Boş password ile kayıt engellendi");

        // ========================================
        // Test Case 9: Boş confirm password
        // ========================================
        extentTest.info("9. Boş confirm password, dolu diğer alanlarla kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("TestUser");
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("test@test.com");
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("Test123!@");
        registerPage.confirmPasswordInput.clear();
        extentTest.info("User: TestUser | Email: test@test.com | Pass: Test123!@ | Confirm: (boş)");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String emptyConfirmUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(emptyConfirmUrl.contains("/register"), "Boş confirm password ile kayıt yapılmamalıydı!");
        extentTest.pass("✅ Boş confirm password ile kayıt engellendi");

        // ========================================
        // Test Case 10: Tüm alanları boş bırak
        // ========================================
        extentTest.info("10. Tüm alanları boş bırak ve kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.emailAddressInput.clear();
        registerPage.passwordInput.clear();
        registerPage.confirmPasswordInput.clear();
        extentTest.info("Tüm alanlar boş");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String allEmptyUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(allEmptyUrl.contains("/register"), "Tüm alanlar boşken kayıt yapılmamalıydı!");
        extentTest.pass("✅ Tüm alanlar boşken kayıt engellendi");

        // ========================================
        // Test Case 11: Geçersiz email formatı
        // ========================================
        extentTest.info("11. Geçersiz email formatı ile kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("TestUser");
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("invalidemail");
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("Test123!@");
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("Test123!@");
        extentTest.info("User: TestUser | Email: invalidemail (geçersiz) | Pass: Test123!@");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String invalidEmailUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(invalidEmailUrl.contains("/register"), "Geçersiz email ile kayıt yapılmamalıydı!");
        extentTest.pass("✅ Geçersiz email formatı ile kayıt engellendi");

        // ========================================
        // Test Case 12: Zayıf şifre
        // ========================================
        extentTest.info("12. Zayıf şifre ile kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("TestUser");
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("test@test.com");
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("123");
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("123");
        extentTest.info("User: TestUser | Email: test@test.com | Pass: 123 (zayıf)");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String weakPasswordUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(weakPasswordUrl.contains("/register"), "Zayıf şifre ile kayıt yapılmamalıydı!");
        extentTest.pass("✅ Zayıf şifre ile kayıt engellendi");

        // ========================================
        // Test Case 13: Password ve Confirm Password eşleşmiyor
        // ========================================
        extentTest.info("13. Password ve Confirm Password eşleşmiyor");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("TestUser");
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("test@test.com");
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("Test123!@");
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("Different123!@");
        extentTest.info("User: TestUser | Email: test@test.com | Pass: Test123!@ | Confirm: Different123!@");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String mismatchUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(mismatchUrl.contains("/register"), "Password eşleşmediğinde kayıt yapılmamalıydı!");
        extentTest.pass("✅ Password eşleşmezliği ile kayıt engellendi");

        // ========================================
        // Test Case 14: Daha önce kayıtlı olan email
        // ========================================
        extentTest.info("14. Daha önce kayıtlı olan email ile kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("NewUser");
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("tural.seyidov.user@loyalfriendcare.com");
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("Test123!@");
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("Test123!@");
        extentTest.info("User: NewUser | Email: tural.seyidov.user@loyalfriendcare.com (kayıtlı)");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

        String duplicateEmailUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(duplicateEmailUrl.contains("/register"), "Kayıtlı email ile kayıt yapılmamalıydı!");
        extentTest.pass("✅ Daha önce kayıtlı email ile kayıt engellendi");

        // ========================================
        // Test Case 15: Çok kısa kullanıcı adı - FAIL OLMALI
        // ========================================
        extentTest.info("15. Çok kısa kullanıcı adı ile kayıt ol");
        Driver.getDriver().get(ConfigReader.getProperty("url") + "/register");
        ReusableMethods.bekle(2);

        registerPage.usernameInput.clear();
        registerPage.usernameInput.sendKeys("t");
        registerPage.emailAddressInput.clear();
        registerPage.emailAddressInput.sendKeys("test@test.com");
        registerPage.passwordInput.clear();
        registerPage.passwordInput.sendKeys("Test123!@");
        registerPage.confirmPasswordInput.clear();
        registerPage.confirmPasswordInput.sendKeys("Test123!@");
        extentTest.info("User: t (1 karakter) | Email: test@test.com | Pass: Test123!@");

        registerPage.signUpButton.click();
        ReusableMethods.bekle(2);

    }
}