package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;
import pages.user_pages.UserAppointmentFormPage;

import java.time.Duration;
import java.util.List;

public class US18 extends TestBaseRapor {

    Layout layout;
    LoginPage loginPage;

    // ========================================
    // TC_01: Giriş Yapmış Kullanıcının Medicines Sayfasına Erişimi
    // ========================================
    @Test(priority = 1, description = "Giriş yapmış kullanıcının Home Page'den Medicines " +
            "sayfasına erişimini doğrulama")
    public void tc01_LoggedInUserMedicinesAccessTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_018_TC_01 - Medicines Sayfası Erişim Testi",
                "Giriş yapmış kullanıcının Home Page'den Medicines sayfasına erişimini doğrulama");

        extentTest.info("Pre-Condition: Kullanıcı sisteme başarıyla giriş yapmış olmalı " +
                "ve Home Page'de olmalı");

        // 1. Sisteme geçerli kullanıcı bilgileriyle giriş yap
        extentTest.info("1. Sisteme geçerli kullanıcı bilgileriyle giriş yap");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Ana sayfaya gidildi: " + ConfigReader.getProperty("url"));

        // Sign In butonuna tıkla
        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();
        extentTest.info("Sign In butonuna tıklandı");

        ReusableMethods.bekle(2);

        // Login bilgilerini gir
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys("tural.seyidov.user@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        extentTest.info("Email: tural.seyidov.user@loyalfriendcare.com | Password: Loyal.123123");

        loginPage.signInButton.click();
        extentTest.info("Login butonuna tıklandı");

        ReusableMethods.bekle(3);

        // Login başarılı mı kontrol et
        String loggedInUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertFalse(loggedInUrl.contains("/login"));
        extentTest.pass("Kullanıcı başarıyla giriş yaptı");

        // 2. Home Page'in yüklendiğini doğrula
        extentTest.info("2. Home Page'in yüklendiğini doğrula");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForPageToLoad(3);
        ReusableMethods.bekle(2);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/en"));
        extentTest.pass("Home Page başarıyla yüklendi: " + currentUrl);

        // 3. Navbar'da Medicines menüsünü bul
        extentTest.info("3. Navbar'da Medicines menüsünü bul");
        ReusableMethods.waitForVisibility(layout.headerMedicinesLink, 2);
        Assert.assertTrue(layout.headerMedicinesLink.isDisplayed());
        extentTest.pass("Medicines menüsü navbar'da bulundu");

        // 4. Medicines menüsüne tıkla
        extentTest.info("4. Medicines menüsüne tıkla");
        ReusableMethods.waitForClickability(layout.headerMedicinesLink, 2);
        layout.headerMedicinesLink.click();
        extentTest.info("Medicines menüsüne tıklandı");

        // 5. Sayfanın Medicines listesi sayfasına yönlendiğini kontrol et
        extentTest.info("5. Sayfanın Medicines listesi sayfasına yönlendiğini kontrol et");
        ReusableMethods.bekle(2);
        ReusableMethods.waitForPageToLoad(3);

        String medicinesUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(medicinesUrl.contains("/Medicines") || medicinesUrl.contains("/medicines" + medicinesUrl));
        extentTest.pass("Sayfa Medicines listesi sayfasına başarıyla yönlendirildi: " + medicinesUrl);

        extentTest.pass("✅ US_018_TC_01 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_02: İlaç Listesi Görüntüleme Testi
    // ========================================
    @Test(priority = 2, description = "İlaçlar sayfasında sistemde tanımlı tüm " +
            "ilaçların listelenmesini doğrulama")
    public void tc02_MedicinesListDisplayTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_018_TC_02 - İlaçlar Listesi Görüntüleme Testi",
                "İlaçlar sayfasında sistemde tanımlı tüm ilaçların listelenmesini doğrulama");

        extentTest.info("Pre-Condition: Kullanıcı Medicines sayfasına erişmiş olmalı ve " +
                "sistemde en az bir ilaç tanımlı olmalı");

        // 1. Sisteme geçerli kullanıcı bilgileriyle giriş yap
        extentTest.info("1. Sisteme geçerli kullanıcı bilgileriyle giriş yap");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Ana sayfaya gidildi: " + ConfigReader.getProperty("url"));

        // Sign In butonuna tıkla
        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();
        extentTest.info("Sign In butonuna tıklandı");

        ReusableMethods.bekle(2);

        // Login bilgilerini gir
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys("tural.seyidov.user@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        extentTest.info("Email: tural.seyidov.user@loyalfriendcare.com | Password: Loyal.123123");

        loginPage.signInButton.click();
        extentTest.info("Login butonuna tıklandı");

        ReusableMethods.bekle(2);

        // Login başarılı mı kontrol et
        String loggedInUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertFalse(loggedInUrl.contains("/login"));
        extentTest.pass("Kullanıcı başarıyla giriş yaptı");

        // 2. Medicines sayfasına git
        extentTest.info("2. Medicines sayfasına git");
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Medicines");
        extentTest.info("Medicines sayfasına direkt gidildi: https://qa.loyalfriendcare.com/en/Medicines");

        // 3. Sayfanın tam olarak yüklenmesini bekle
        extentTest.info("3. Sayfanın tam olarak yüklenmesini bekle");
        ReusableMethods.waitForPageToLoad(1);
        ReusableMethods.bekle(1);

        // URL kontrolü
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Medicines") || currentUrl.contains("/medicines"),
                "URL Medicines içermiyor: " + currentUrl);
        extentTest.pass("Medicines sayfası başarıyla yüklendi: " + currentUrl);

        // 4. İlaç listesinin görünür olduğunu kontrol et
        extentTest.info("4. İlaç listesinin görünür olduğunu kontrol et");

        // Sayfayı scroll down yap
        ReusableMethods.scrollToBottom();
        ReusableMethods.bekle(1);

        // Container kontrolü
        WebElement container = Driver.getDriver().findElement(By.xpath("//div[contains(@class,'container')]"));
        Assert.assertTrue(container.isDisplayed());
        extentTest.pass("İlaç listesi container'ı görünür");

        // 5. Listelenen ilaç sayısını kontrol et
        extentTest.info("5. Listelenen ilaç sayısını kontrol et");

        // İlaç öğelerini bul
        List<WebElement> medicineItems = Driver.getDriver().findElements(
                By.xpath("//a[contains(@class,'grid_item')]")
        );

        extentTest.info("Bulunan ilaç sayısı: " + medicineItems.size());

        if (medicineItems.size() == 0) {
            // Tekrar scroll yap ve tekrar dene
            ReusableMethods.scrollToHeader();
            ReusableMethods.bekle(1);

            medicineItems = Driver.getDriver().findElements(
                    By.xpath("//a[contains(@class,'grid_item')]")
            );
        }

        int medicineCount = medicineItems.size();
        extentTest.pass("Sistemde listelenen ilaç sayısı: " + medicineCount);

        // 6. İlaçların liste halinde düzgün görüntülendiğini doğrula
        extentTest.info("6. İlaçların liste halinde düzgün görüntülendiğini doğrula");

        if (medicineCount > 0) {
            extentTest.pass("✅ İlaçlar liste halinde görünüyor (Toplam: " + medicineCount + " ilaç)");
        } else {
            extentTest.warning("⚠️ Sistemde ilaç bulunamadı veya liste yüklenmedi");
        }

        extentTest.pass("✅ US_018_TC_02 testi başarıyla tamamlandı!");

    }


    // ========================================
    // TC_03: İlaçların Temel Bilgileri Testi
    // ========================================
    @Test(priority = 3, description = "İlaçların temel bilgilerinin (isim, açıklama) " +
            "listelerde görüntülenmesini doğrulama")
    public void tc03_MedicinesBasicInfoDisplayTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_018_TC_03 - İlaçların Temel Bilgileri Testi",
                "İlaçların temel bilgilerinin (isim, açıklama) listelerde görüntülenmesini doğrulama");

        extentTest.info("Pre-Condition: Kullanıcı Medicines sayfasında olmalı");

        // 1. Sisteme geçerli kullanıcı bilgileriyle giriş yap
        extentTest.info("1. Sisteme geçerli kullanıcı bilgileriyle giriş yap");

        // Direkt login sayfasına git
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/login");
        ReusableMethods.bekle(2);
        extentTest.info("Login sayfasına gidildi");

        // Login bilgilerini gir
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);
        loginPage.emailAddressInput.sendKeys("tural.seyidov.user@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        extentTest.info("Email: tural.seyidov.user@loyalfriendcare.com | Password: Loyal.123123");

        loginPage.signInButton.click();
        extentTest.info("Login butonuna tıklandı");

        ReusableMethods.bekle(3);

        // Login başarılı mı kontrol et
        String loggedInUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertFalse(loggedInUrl.contains("/login"));
        extentTest.pass("Kullanıcı başarıyla giriş yaptı");

        // 2. Medicines sayfasına git
        extentTest.info("2. Medicines sayfasına git");
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Medicines");
        ReusableMethods.waitForPageToLoad(10);
        ReusableMethods.bekle(3);
        extentTest.pass("Medicines sayfası yüklendi");

        // 3. İlaç listesini bul
        extentTest.info("3. İlaç listesini bul");

        // İlaçları bul - "Rimadyl (Carprofen)" gibi ilaç linklerini ara
        List<WebElement> medicineItems = Driver.getDriver().findElements(
                By.xpath("//a[contains(@href,'/Medicines/')]")
        );

        Assert.assertFalse(medicineItems.isEmpty(), "Hiç ilaç bulunamadı!");
        extentTest.pass("İlaç listesi bulundu (Toplam: " + medicineItems.size() + " ilaç)");

        // 4. İlk ilaca tıkla - "Rimadyl (Carprofen)"
        extentTest.info("4. İlk ilaca tıkla ve detay sayfasına git");

        WebElement firstMedicine = medicineItems.get(0);
        String firstMedicineName = firstMedicine.getText();
        extentTest.info("İlk ilaç: " + firstMedicineName);

        // JavaScript ile tıkla
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", firstMedicine);
        ReusableMethods.bekle(1);
        js.executeScript("arguments[0].click();", firstMedicine);

        ReusableMethods.bekle(2);
        ReusableMethods.waitForPageToLoad(10);
        extentTest.pass("İlaç detay sayfasına gidildi");

        // 5. İlaç detay sayfasında scroll down yap
        extentTest.info("5. İlaç detay sayfasında scroll down yap");
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);

        // 6. İlaç bilgilerinin görünür olduğunu kontrol et
        extentTest.info("6. İlaç bilgilerinin görünür olduğunu kontrol et");

        String pageSource = Driver.getDriver().getPageSource();
        Assert.assertFalse(pageSource.isEmpty(), "Sayfa kaynağı boş!");

        // URL kontrolü
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Medicines/"),
                "İlaç detay sayfasında değil! URL: " + currentUrl);
        extentTest.pass("İlaç bilgileri görünür durumda");

        // 7. Navigate back ile Medicines listesine geri dön
        extentTest.info("7. Navigate back ile Medicines listesine geri dön");
        Driver.getDriver().navigate().back();
        ReusableMethods.bekle(2);
        ReusableMethods.waitForPageToLoad(10);
        extentTest.pass("Medicines listesine geri dönüldü");

        // 8. Diğer ilaçları kontrol et - Scroll to bottom
        extentTest.info("8. Diğer ilaçları kontrol et - Scroll to bottom");
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);

        // Tüm ilaçları tekrar bul
        medicineItems = Driver.getDriver().findElements(
                By.xpath("//a[contains(@href,'/Medicines/')]")
        );

        int validCount = 0;
        for (WebElement medicine : medicineItems) {
            try {
                if (medicine.isDisplayed() && !medicine.getText().isEmpty()) {
                    validCount++;
                }
            } catch (Exception e) {
                // Devam et
            }
        }

        Assert.assertTrue(validCount > 0, "Hiç geçerli ilaç bulunamadı!");
        extentTest.pass("Diğer ilaçlar kontrol edildi (Toplam geçerli: " + validCount + ")");

        // 9. Scroll to header - Sayfanın başına dön
        extentTest.info("9. Scroll to header - Sayfanın başına dön");
        ReusableMethods.scrollToHeader();
        ReusableMethods.bekle(2);
        extentTest.pass("Sayfanın başına dönüldü");

        extentTest.pass("✅ US_018_TC_03 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_04: İlaç Detay Sayfası Bilgileri Testi
    // ========================================
    @Test(priority = 4, description = "İlaç detay sayfasında tüm ilaç bilgilerinin " +
            "görüntülenmesini doğrulama")
    public void tc04_MedicineDetailPageTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_018_TC_04 - İlaç Detay Sayfası Bilgileri Testi",
                "İlaç detay sayfasında tüm ilaç bilgilerinin görüntülenmesini doğrulama");

        extentTest.info("Pre-Condition: Kullanıcı bir ilacın detay sayfasına erişmiş olmalı");

        // 1. Login yap - Direkt login sayfasına git
        extentTest.info("1. Sisteme giriş yap");
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/login");
        ReusableMethods.bekle(1);
        extentTest.info("Login sayfasına gidildi");

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("user_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("user_password"));
        extentTest.info("Email: " + ConfigReader.getProperty("user_email"));

        loginPage.signInButton.click();
        ReusableMethods.bekle(1);

        // Login kontrolü
        String loggedInUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertFalse(loggedInUrl.contains("/login"));
        extentTest.pass("Kullanıcı başarıyla giriş yaptı");

        // 2. Navbar'da Medicines menüsüne hover yap
        extentTest.info("2. Navbar'da Medicines menüsüne hover yap");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(1);

        // Medicines menüsüne hover
        ReusableMethods.waitForVisibility(layout.headerMedicinesLink, 10);
        ReusableMethods.hover(layout.headerMedicinesLink);
        ReusableMethods.bekle(1);
        extentTest.pass("Medicines menüsüne hover yapıldı");

        // 3. Açılan listeden "Metacam (Meloxicam)" seç
        extentTest.info("3. Açılan listeden 'Metacam (Meloxicam)' ilaçını seç");

        // Submenu açılmasını bekle
        ReusableMethods.bekle(1);

        // TÜM ilaç linklerini bul
        List<WebElement> medicineLinks = Driver.getDriver().findElements(
                By.xpath("//nav[@id='menu']//a[contains(@href,'/Medicines/')]")
        );

        extentTest.info("Bulunan ilaç linki sayısı: " + medicineLinks.size());

        // Metacam içeren linki bul
        WebElement metacamLink = null;
        for (WebElement link : medicineLinks) {
            if (link.getAttribute("href").contains("metacam-meloxicam")) {
                metacamLink = link;
                break;
            }
        }

        Assert.assertNotNull(metacamLink, "Metacam linki bulunamadı!");
        extentTest.info("Metacam linki bulundu: " + metacamLink.getAttribute("href"));

        // Önce Metacam linkine hover yap
        ReusableMethods.hover(metacamLink);
        ReusableMethods.bekle(1);
        extentTest.info("Metacam linkine hover yapıldı");

        // Sonra tıkla
        ReusableMethods.waitForClickability(metacamLink, 2);
        metacamLink.click();

        ReusableMethods.bekle(1);
        ReusableMethods.waitForPageToLoad(2);
        extentTest.pass("Metacam (Meloxicam) ilaç detay sayfasına gidildi");

        // 4. URL kontrolü
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Medicines/metacam-meloxicam"),
                "İlaç detay sayfası URL'i yanlış! URL: " + currentUrl);
        extentTest.pass("İlaç detay sayfası açıldı: " + currentUrl);

        // Scroll down yap
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);

        // 5. İlacın adının görünür olduğunu doğrula
        extentTest.info("5. İlacın adının görünür olduğunu doğrula (Metacam - Meloxicam)");

        // Farklı olası locator'ları dene
        WebElement medicineTitle = null;
        try {
            medicineTitle = Driver.getDriver().findElement(By.xpath("//h1"));
        } catch (Exception e1) {
            try {
                medicineTitle = Driver.getDriver().findElement(By.xpath("//h2[contains(@class,'title')]"));
            } catch (Exception e2) {
                try {
                    medicineTitle = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'Metacam')]"));
                } catch (Exception e3) {
                    extentTest.fail("İlaç başlığı bulunamadı!");
                }
            }
        }

        if (medicineTitle != null) {
            ReusableMethods.waitForVisibility(medicineTitle, 2);
            String titleText = medicineTitle.getText();
            Assert.assertFalse(titleText.isEmpty(), "İlaç adı boş!");
            Assert.assertTrue(titleText.contains("Metacam") || titleText.contains("Meloxicam"),
                    "İlaç adı 'Metacam' veya 'Meloxicam' içermiyor! Bulunan: " + titleText);
            extentTest.pass("İlacın adı görünür: " + titleText);
        }

        // 6. İlacın açıklamasının görünür olduğunu doğrula
        extentTest.info("6. İlacın açıklamasının görünür olduğunu doğrula");
        ReusableMethods.bekle(1);

        // Açıklama/içerik bölümünü bul
        List<WebElement> contentElements = Driver.getDriver().findElements(
                By.xpath("//p | //div[contains(@class,'content')] | //div[contains(@class,'description')]")
        );

        if (!contentElements.isEmpty()) {
            extentTest.pass("İlacın açıklama bilgisi görünür (Bulunan paragraf sayısı: " + contentElements.size() + ")");
        } else {
            extentTest.warning("⚠️ İlaç açıklaması bulunamadı");
        }

        // 7. Kullanım talimatlarının görünür olduğunu doğrula
        extentTest.info("7. Kullanım talimatlarının görünür olduğunu doğrula");
        ReusableMethods.scrollDown();

        // Tüm text içeriğini kontrol et
        String pageContent = Driver.getDriver().findElement(By.tagName("body")).getText();

        if (pageContent.length() > 100) {
            extentTest.pass("✅ Kullanım talimatları ve detaylı bilgiler görünür (İçerik uzunluğu: " + pageContent.length() + " karakter)");
        } else {
            extentTest.warning("⚠️ Detaylı içerik bulunamadı");
        }

        // 8. Sayfa kaynağında "Metacam" veya "Meloxicam" kelimesinin geçtiğini doğrula
        extentTest.info("8. İlaç bilgilerinin sayfada mevcut olduğunu doğrula");
        Assert.assertTrue(pageContent.contains("Metacam") || pageContent.contains("Meloxicam"),
                "Sayfa içeriğinde 'Metacam' veya 'Meloxicam' bulunamadı!");
        extentTest.pass("✅ İlaç bilgileri sayfada mevcut");

        extentTest.pass("✅ US_018_TC_04 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_05: Randevu Butonu İşlevselliği (BUG Test)
    // ========================================
    @Test(priority = 5, description = "İlaç detay sayfasında Randevu Oluştur butonu işlevselliği")
    public void tc05_AppointmentButtonFunctionalityTest() {

        layout = new Layout();
        loginPage = new LoginPage();
        UserAppointmentFormPage appointmentForm = new UserAppointmentFormPage();

        extentTest = extentReports.createTest("US_018_TC_05 - Randevu Oluştur Butonu Testi",
                "İlaç detay sayfasında Randevu Oluştur butonu işlevselliği ve randevu talebi oluşturma");

        extentTest.info("Pre-Condition: Kullanıcı ilaç detay sayfasında olmalı");

        // Login yap
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/login");
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 1);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("user_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("user_password"));
        loginPage.signInButton.click();
        ReusableMethods.bekle(1);
        extentTest.pass("Kullanıcı giriş yaptı");

        // İlaç detay sayfasına git
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Medicines/metacam-meloxicam");
        ReusableMethods.waitForPageToLoad(1);
        ReusableMethods.bekle(1);
        extentTest.info("İlaç detay sayfasına gidildi: Metacam (Meloxicam)");

        // 1. İlaç detay sayfasında "Randevu Oluştur" formu görünür mü
        extentTest.info("1. İlaç detay sayfasında 'Randevu Oluştur' butonu bul");

        try {
            ReusableMethods.waitForVisibility(appointmentForm.appointmentFormContainer, 1);
            extentTest.pass("✅ STEP 1 PASSED: Randevu formu görünür");
        } catch (Exception e) {
            extentTest.fail("❌ STEP 1 FAILED: Randevu formu bulunamadı!");
            Assert.fail("Randevu formu bulunamadı!");
        }

        // 2. Butonun görünür ve tıklanabilir olduğunu doğrula
        extentTest.info("2. Butonun görünür ve tıklanabilir olduğunu doğrula");

        try {
            ReusableMethods.scrollDown();
            ReusableMethods.bekle(2);

            Assert.assertTrue(appointmentForm.submitButton.isDisplayed(),
                    "Randevu butonu görünür değil!");
            Assert.assertTrue(appointmentForm.submitButton.isEnabled(),
                    "Randevu butonu tıklanabilir değil!");

            extentTest.pass("✅ STEP 2 PASSED: Randevu butonu görünür ve tıklanabilir");
        } catch (Exception e) {
            extentTest.fail("❌ STEP 2 FAILED: Randevu butonu kontrol edilemedi! " + e.getMessage());
            Assert.fail("Randevu butonu kontrol edilemedi!");
        }

        // 3. "Randevu Oluştur" butonuna tıkla (ALANLAR BOŞ!)
        extentTest.info("3. 'Randevu Oluştur' butonuna tıkla (Hiçbir alan doldurulmadan" +
                " - Validasyon testi)");

        appointmentForm.submitButton.click();
        extentTest.info("Randevu butonuna boş form ile tıklandı");
        ReusableMethods.bekle(3);

        // 4. Success message kontrolü - BUG VAR MI?
        extentTest.info("4. Form validasyonunun çalıştığını kontrol et " +
                "(Boş form kabul edilmemeli!)");

        boolean bugDetected = false;
        String bugMessage = "";

        // Yöntem 1: Success message elementi var mı?
        try {
            if (appointmentForm.successMessage.isDisplayed()) {
                String successText = appointmentForm.successMessage.getText();
                bugDetected = true;
                bugMessage = "Success message görüldü: '" + successText + "'";

                extentTest.fail("❌ STEP 3 FAILED: BUG DETECTED!");
                extentTest.fail("❌ CRITICAL BUG: Boş formla randevu oluşturuldu!");
                extentTest.fail("❌ Form validasyonu çalışmıyor!");
                extentTest.fail("❌ Success mesajı: " + successText);
            }
        } catch (Exception e) {
            // Element bulunamadı, sayfa içeriğini kontrol et
        }

        // Yöntem 2: Sayfa içeriğinde success mesajı var mı?
        if (!bugDetected) {
            try {
                String pageText = Driver.getDriver().findElement(By.tagName("body")).getText().toLowerCase();

                if (pageText.contains("congratulations") ||
                        pageText.contains("success") ||
                        pageText.contains("başarı") ||
                        pageText.contains("appointment created") ||
                        pageText.contains("randevu oluşturuldu")) {

                    bugDetected = true;
                    bugMessage = "Sayfa içeriğinde success mesajı tespit edildi!";

                    extentTest.fail("❌ STEP 3 FAILED: BUG DETECTED!");
                    extentTest.fail("❌ CRITICAL BUG: Sayfa içeriğinde başarı mesajı var!");
                    extentTest.fail("❌ Boş formla randevu oluşturuldu!");
                    extentTest.fail("❌ Form validasyonu çalışmıyor!");
                }
            } catch (Exception e) {
                extentTest.warning("⚠️ Sayfa içeriği kontrol edilemedi: " + e.getMessage());
            }
        }

        // Yöntem 3: URL değişti mi? (Başarılı olursa redirect olabilir)
        if (!bugDetected) {
            String currentUrl = Driver.getDriver().getCurrentUrl();
            if (!currentUrl.contains("/Medicines/metacam-meloxicam")) {
                bugDetected = true;
                bugMessage = "URL değişti, randevu oluşturulmuş olabilir! Yeni URL: " + currentUrl;

                extentTest.fail("❌ STEP 3 FAILED: BUG DETECTED!");
                extentTest.fail("❌ CRITICAL BUG: URL değişti, randevu oluşturulmuş olabilir!");
                extentTest.fail("❌ Yeni URL: " + currentUrl);
            }
        }

        // 5. Final Assert - BUG VARSA TEST FAIL OLMALI!
        if (bugDetected) {
            extentTest.fail("🐛 CRITICAL BUG DETECTED: " + bugMessage);
            extentTest.fail("⚠️ Form validasyonu çalışmıyor! Boş formla randevu oluşturuluyor!");

            // TEST FAIL OLMALI!
            Assert.fail("🐛 CRITICAL BUG: Form validasyonu çalışmıyor! Boş formla randevu oluşturuldu! " + bugMessage);
        } else {
            extentTest.pass("✅ STEP 3 PASSED: Form validasyonu çalışıyor, boş form kabul edilmedi");
            extentTest.pass("✅ US_018_TC_05 testi başarıyla tamamlandı!");
        }
    }

}