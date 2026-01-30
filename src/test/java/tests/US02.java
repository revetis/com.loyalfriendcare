package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import pages.common_pages.RegisterPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US02 extends TestBaseRapor {

    Layout layout;
    LoginPage loginPage;
    RegisterPage registerPage;

    // ========================================
    // TC_01: Logo Görünürlük ve Konum Testi
    // ========================================
    @Test(priority = 1, description = "Home Page Logo Görünürlük ve Konum Testi")
    public void tc01_HomePageLogoVisibilityAndPosition() {

        extentTest = extentReports.createTest("US_02_TC_01 - Home Page Logo Görünürlük ve Konum Testi",
                "Header bölümündeki Logo butonunun görünürlüğünü ve konumunu doğrulama");

        // 1. https://qa.loyalfriendcare.com/en adresine git
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Kullanıcı ana sayfaya gitti: " + ConfigReader.getProperty("url"));

        // 2. Home Page'in yüklenmesini bekle
        ReusableMethods.waitForPageToLoad(3);
        extentTest.info("Home Page yüklendi");

        // 3. Header bölümünü kontrol et
        layout = new Layout();
        Assert.assertTrue(layout.header.isDisplayed());
        extentTest.pass("Header bölümü başarıyla görüntülendi");

        // 4. Loyalfriendcare logosunun sol tarafta konumlandığını doğrula
        ReusableMethods.waitForVisibility(layout.headerLogoImage, 2);
        Assert.assertTrue(layout.headerLogoImage.isDisplayed());

        int logoXPosition = layout.headerLogoImage.getLocation().getX();
        Assert.assertTrue(logoXPosition < 300,
                "Logo sol tarafta konumlanmamış! X pozisyonu: " + logoXPosition);
        extentTest.pass("Loyalfriendcare logosu sol tarafta konumlandı (X: " + logoXPosition + ")");

        // 5. Loyalfriendcare logosunun tıklanabilir olduğunu doğrula
        ReusableMethods.waitForClickability(layout.headerLogo, 2);
        Assert.assertTrue(layout.headerLogo.isEnabled());
        extentTest.pass("Loyalfriendcare logosu tıklanabilir durumda");

        extentTest.pass("US_02_TC_01 Test başarıyla tamamlandı");
    }


    // ========================================
    // TC_02: Sign In ve Sign Up Button Testi
    // ========================================
    @Test(priority = 2, description = "Sign In ve Sign Up Buton Görünürlük Testi")
    public void tc02_SignInSignUpButtonTest() {

        extentTest = extentReports.createTest("US_02_TC_02 - Sign In Sign Up Button Test",
                "Sign In ve Sign Up butonlarının görünürlüğünü, konumunu ve rengini doğrulama");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForPageToLoad(3);
        layout = new Layout();

        // 1. Header bölümünün sağ tarafında Sign in ve Sign Up butonlarının olduğunu kontrol et
        Assert.assertTrue(layout.signInLink.isDisplayed());
        Assert.assertTrue(layout.signUpLink.isDisplayed());
        extentTest.pass("Sign In ve Sign Up butonları header'da bulundu");

        // 2. Sign In butonunun yeşil renkte ve görünür olduğunu doğrula
        ReusableMethods.waitForVisibility(layout.signInLink, 2);
        Assert.assertTrue(layout.signInLink.isDisplayed());

        String signInBackgroundColor = layout.signInLink.getCssValue("background-color");
        Assert.assertTrue(signInBackgroundColor.contains("0, 148, 49"),
                "Sign In butonu yeşil değil! Renk: " + signInBackgroundColor);
        extentTest.pass("Sign In butonu yeşil renkte ve görünür");

        // 3. Sign Up butonunun yeşil renkte ve görünür olduğunu doğrula
        ReusableMethods.waitForVisibility(layout.signUpLink, 2);
        Assert.assertTrue(layout.signUpLink.isDisplayed());

        String signUpBackgroundColor = layout.signUpLink.getCssValue("background-color");
        Assert.assertTrue(signUpBackgroundColor.contains("0, 148, 49"),
                "Sign Up butonu yeşil değil! Renk: " + signUpBackgroundColor);
        extentTest.pass("Sign Up butonu yeşil renkte ve görünür");

        // 4. Her iki butonun sağ tarafta yan yana konumlandığını doğrula
        int signInX = layout.signInLink.getLocation().getX();
        int signUpX = layout.signUpLink.getLocation().getX();
        int signInY = layout.signInLink.getLocation().getY();
        int signUpY = layout.signUpLink.getLocation().getY();

        extentTest.pass("US_02_TC_02 Test başarıyla tamamlandı");
    }


    // ========================================
    // TC_03: Logo Home Page Redirect Testi
    // ========================================
    @Test(priority = 3, description = "Logo Home Page Redirect Testi")
    public void tc03_LogoHomePageRedirectTest() {

        layout = new Layout();

        extentTest = extentReports.createTest("US_02_TC_03 - Logo Home Page Redirect Testi",
                "Logo butonuna tıklandığında ana sayfaya yönlendirme işlevselliğini doğrulama");

        // 1. Farklı bir sayfaya git (örn: About Us)
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("1. Kullanıcı ana sayfaya gitti: " + ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickability(layout.headerAboutUsLink, 2).click();
        extentTest.info("About Us linkine tıklandı");

        ReusableMethods.bekle(2);
        String aboutUsUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(aboutUsUrl.contains("/about"));
        extentTest.pass("About Us sayfası başarıyla açıldı");

        // 2. Header'daki Logo butonuna tıkla
        ReusableMethods.waitForClickability(layout.headerLogo, 2).click();
        extentTest.info("2. Header'daki Logo butonuna tıklandı");

        // 3. Sayfanın Home Page'e yönlendiğini doğrula
        ReusableMethods.bekle(2);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        extentTest.info("3. Sayfa yönlendirmesi kontrol ediliyor");

        Assert.assertTrue(
                currentUrl.contains("/en") || currentUrl.endsWith("/en/"),
                "Sayfa Home Page'e yönlenmedi! Mevcut URL: " + currentUrl
        );
        extentTest.pass("Sayfa Home Page'e başarıyla yönlendirildi");

        // 4. URL'nin https://qa.loyalfriendcare.com/en olduğunu kontrol et
        String expectedUrl = "https://qa.loyalfriendcare.com/en";
        Assert.assertTrue(
                currentUrl.equals(expectedUrl) || currentUrl.equals(expectedUrl + "/"),
                "URL beklenen değerde değil! Beklenen: " + expectedUrl + ", Gerçek: " + currentUrl
        );
        extentTest.pass("4. URL doğrulandı: " + currentUrl);
    }

    // ========================================
    // TC_04: Sign In Button Functionality Testi
    // ========================================
    @Test(priority = 4, description = "Sign In Button Functionality Testi")
    public void tc04_SignInButtonFunctionalityTest() {

        Layout layout = new Layout();
        LoginPage loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_02_TC_04 - Sign In Butonu İşlevsellik Testi",
                "Sign In butonuna tıklandığında giriş sayfasına yönlendirme ve login testleri");

        extentTest.info("Pre-Condition: Kullanıcı Home Page'de olmalı");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("1. Ana sayfaya gidildi: " + ConfigReader.getProperty("url"));

        // 1. Sign Up butonunun yeşil renkte görünür olduğunu doğrula
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("1. Header'daki Sign Up butonunun yeşil renkte görünür olduğunu doğrula");

        // 2. Sign In butonuna tıkla
        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();
        extentTest.info("Sign In butonuna tıklandı");

        // 3. Giriş sayfasına yönlendirildiğini doğrula
        ReusableMethods.bekle(2);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"));
        extentTest.pass("Giriş sayfasına başarıyla yönlendirildi: " + currentUrl);

        // 4. Giriş formunun görünür olduğunu kontrol et
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 1);
        ReusableMethods.waitForVisibility(loginPage.passwordInput, 1);

        Assert.assertTrue(loginPage.emailAddressInput.isDisplayed());
        Assert.assertTrue(loginPage.passwordInput.isDisplayed());
        extentTest.pass("Giriş formu (email ve password) görünür olduğu doğrulandı");

        // 5: Doğru email + Doğru şifre
        extentTest.info("5. Doğru email ve doğru şifre ile giriş yap");
        loginPage.emailAddressInput.clear();
        loginPage.emailAddressInput.sendKeys("tural.seyidov.user@loyalfriendcare.com");
        loginPage.passwordInput.clear();
        loginPage.passwordInput.sendKeys("Loyal.123123");
        extentTest.info("Email: tural.seyidov.user@loyalfriendcare.com | Password: Loyal.123123");

        loginPage.signInButton.click();
        extentTest.info("Sign In butonuna tıklandı");

        ReusableMethods.bekle(2);
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

        // 6: Doğru email + Yanlış şifre
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

        // 7: Yanlış email + Doğru şifre
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

        // 8: Yanlış email + Yanlış şifre
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

        // 9: Boş email + Boş şifre
        extentTest.info("9. Boş email ve boş şifre ile giriş yap");

        loginPage.emailAddressInput.clear();
        loginPage.passwordInput.clear();
        extentTest.info("Email: (boş) | Password: (boş)");

        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        String emptyUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(emptyUrl.contains("/login"), "Boş bilgilerle giriş yapılmamalıydı!");
        extentTest.pass("✅ Boş bilgilerle giriş engellendi");

        // 10: Geçersiz email formatı
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


    // ========================================
    // TC_05: Sign Up Button Functionality Testi
    // ========================================
    @Test(priority = 5, description = "Sign Up Button Functionality Testi")
    public void tc05_SignUpButtonFunctionalityTest() {

        Layout layout = new Layout();
        RegisterPage registerPage = new RegisterPage();

        extentTest = extentReports.createTest("US_02_TC_05 - Sign Up Butonu İşlevsellik Testi",
                "Sign Up butonuna tıklandığında kayıt sayfasına yönlendirme ve kayıt testleri");

        extentTest.info("Pre-Condition: Kullanıcı Home Page'de olmalı ve kayıt olmamış olmalı");

        // 1. Sign Up butonunun yeşil renkte görünür olduğunu doğrula
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("1. Header'daki Sign Up butonunun yeşil renkte görünür olduğunu doğrula");

        ReusableMethods.waitForVisibility(layout.signUpLink, 1);
        Assert.assertTrue(layout.signUpLink.isDisplayed());
        extentTest.pass("Sign Up butonu görünür olduğu doğrulandı");

        // 2. Sign Up butonuna tıkla
        extentTest.info("2. Sign Up butonuna tıkla");
        ReusableMethods.waitForClickability(layout.signUpLink, 1);
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
        ReusableMethods.waitForVisibility(registerPage.usernameInput, 2);
        ReusableMethods.waitForVisibility(registerPage.emailAddressInput, 2);
        ReusableMethods.waitForVisibility(registerPage.passwordInput, 2);
        ReusableMethods.waitForVisibility(registerPage.confirmPasswordInput, 2);

        Assert.assertTrue(registerPage.usernameInput.isDisplayed());
        Assert.assertTrue(registerPage.emailAddressInput.isDisplayed());
        Assert.assertTrue(registerPage.passwordInput.isDisplayed());
        Assert.assertTrue(registerPage.confirmPasswordInput.isDisplayed());
        extentTest.pass("Kayıt formu tüm alanlarıyla görünür olduğu doğrulandı");

        // 5: Tüm alanları geçerli verilerle doldur
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

        // 6: Boş user name, dolu diğer alanlarla kayıt ol
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

        // Test 7: Boş email, dolu diğer alanlarla kayıt ol
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

        // 8: Boş password, dolu diğer alanlarla kayıt ol
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

        // 9: Boş confirm password, dolu diğer alanlarla kayıt ol
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

        // 10: Tüm alanları boş bırak ve kayıt ol
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

        // 11: Geçersiz email formatı ile kayıt ol
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

        // 12: Zayıf şifre ile kayit ol
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

        // 13: Password ve Confirm Password eşleşmiyor
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

        // 14: Daha önce kayıtlı olan email
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

        // 15: Çok kısa kullanıcı adı - FAIL OLMALI
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