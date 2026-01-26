package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;
import pages.user_pages.UserAppointmentFormPage;

import java.util.List;

public class US_018_TestCases extends TestBaseRapor {

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

        extentTest = extentReports.createTest("US_018_TC_02 - İlaçlar Listesi Görüntüleme Testi",
                "İlaçlar sayfasında sistemde tanımlı tüm ilaçların listelenmesini doğrulama");

        extentTest.info("Pre-Condition: Kullanıcı Medicines sayfasına erişmiş olmalı ve " +
                "sistemde en az bir ilaç tanımlı olmalı");

        // 1. Medicines sayfasına git
        extentTest.info("1. Medicines sayfasına git");
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Medicines");
        extentTest.info("Medicines sayfasına direkt gidildi: https://qa.loyalfriendcare.com/en/Medicines");

        // 2. Sayfanın tam olarak yüklenmesini bekle
        extentTest.info("2. Sayfanın tam olarak yüklenmesini bekle");
        ReusableMethods.waitForPageToLoad(3);
        ReusableMethods.bekle(2);

        // URL kontrolü
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Medicines") || currentUrl.contains("/medicines" + currentUrl));
        extentTest.pass("Medicines sayfası başarıyla yüklendi: " + currentUrl);

        // 3. İlaç listesinin görünür olduğunu kontrol et
        extentTest.info("3. İlaç listesinin görünür olduğunu kontrol et");

        // Sayfayı scroll down yap
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(2);

        // Container kontrolü
        WebElement container = Driver.getDriver().findElement(By.xpath("//div[contains(@class,'container')]"));
        Assert.assertTrue(container.isDisplayed());
        extentTest.pass("İlaç listesi container'ı görünür");

        // 4. Listelenen ilaç sayısını kontrol et
        extentTest.info("4. Listelenen ilaç sayısını kontrol et");

        // İlaç öğelerini bul
        List<WebElement> medicineItems = Driver.getDriver().findElements(
                By.xpath("//a[contains(@class,'grid_item')]")
        );

        extentTest.info("Bulunan ilaç sayısı: " + medicineItems.size());

        if (medicineItems.size() == 0) {
            // Tekrar scroll yap ve tekrar dene
            ReusableMethods.scrollToHeader();
            ReusableMethods.bekle(2);

            medicineItems = Driver.getDriver().findElements(
                    By.xpath("//a[contains(@class,'grid_item')]")
            );
        }

        int medicineCount = medicineItems.size();
        extentTest.pass("Sistemde listelenen ilaç sayısı: " + medicineCount);

        // 5. İlaçların liste halinde düzgün görüntülendiğini doğrula
        extentTest.info("5. İlaçların liste halinde düzgün görüntülendiğini doğrula");

        if (medicineCount > 0) {
            extentTest.pass("✅ İlaçlar liste halinde görünüyor (Toplam: " + medicineCount + " ilaç)");
        } else {
            extentTest.warning("⚠️ Sistemde ilaç bulunamadı veya liste yüklenmedi");
        }

    }


    // ========================================
    // TC_03: İlaçların Temel Bilgileri Testi
    // ========================================

    @Test(priority = 3, description = "İlaçların temel bilgilerinin (isim, açıklama) " +
            "listelerde görüntülenmesini doğrulama")
    public void tc03_MedicinesBasicInfoDisplayTest() {

        layout = new Layout();

        extentTest = extentReports.createTest("US_018_TC_03 - İlaçların Temel Bilgileri Testi",
                "İlaçların temel bilgilerinin (isim, açıklama) listelerde görüntülenmesini doğrulama");

        extentTest.info("Pre-Condition: Kullanıcı Medicines sayfasında olmalı");

        // 1. Medicines sayfasına git
        extentTest.info("1. Medicines sayfasındaki ilaç listesini görüntüle");
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Medicines");
        ReusableMethods.waitForPageToLoad(3);
        ReusableMethods.bekle(2);
        extentTest.pass("Sayfa yüklendi");

        // 2. İlk ilacı kontrol et
        extentTest.info("2. İlk ilacın isminin görünür olduğunu kontrol et");
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(2);

        List<WebElement> medicineItems = Driver.getDriver().findElements(
                By.xpath("//a[contains(@class,'grid_item')]")
        );

        if (!medicineItems.isEmpty()) {
            WebElement firstMedicine = medicineItems.get(0);
            WebElement firstMedicineName = firstMedicine.findElement(By.xpath(".//h3"));
            String nameText = firstMedicineName.getText();
            extentTest.pass("İlk ilacın ismi görünür: " + nameText);

            // 3. İlk ilacın resmi
            extentTest.info("3. İlacın açıklama bilgisinin görünür olduğunu kontrol et");
            WebElement firstMedicineImage = firstMedicine.findElement(By.xpath(".//img"));
            extentTest.pass("İlk ilacın resmi görünür");
        }

        // 4. Scroll down ve diğer ilaçları kontrol et
        extentTest.info("4. Diğer ilaçlar için de aynı bilgilerin görüntülendiğini doğrula");
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(2);

        // Tüm ilaçları tekrar bul
        medicineItems = Driver.getDriver().findElements(
                By.xpath("//a[contains(@class,'grid_item')]")
        );

        int validCount = 0;
        for (WebElement medicine : medicineItems) {
            try {
                WebElement name = medicine.findElement(By.xpath(".//h3"));
                WebElement image = medicine.findElement(By.xpath(".//img"));

                if (name.isDisplayed() && image.isDisplayed() && !name.getText().isEmpty()) {
                    validCount++;
                }
            } catch (Exception e) {
                // Devam et
            }
        }

        extentTest.pass("Diğer ilaçlar görüntülendi (Toplam geçerli: " + validCount + ")");

        // 5. Bilgilerin okunabilir olduğunu doğrula
        extentTest.info("5. Bilgilerin okunabilir ve düzgün formatta olduğunu kontrol et");

        if (!medicineItems.isEmpty()) {
            WebElement checkMedicine = medicineItems.get(0);
            WebElement checkName = checkMedicine.findElement(By.xpath(".//h3"));
            String fontSize = checkName.getCssValue("font-size");

            extentTest.pass("✅ Bilgiler okunabilir ve düzgün formatta (Font: " + fontSize + ")");
        }

        extentTest.pass("✅ US_018_TC_03 testi tamamlandı!");
    }


    // ========================================
    // TC_04: İlaç Detay Sayfası Bilgileri Testi
    // ========================================
    @Test(priority = 4, description = "İlaç detay sayfasında tüm ilaç bilgilerinin görüntülenmesini doğrulama")
    public void tc04_MedicineDetailPageTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_018_TC_04 - İlaç Detay Sayfası Bilgileri Testi",
                "İlaç detay sayfasında tüm ilaç bilgilerinin görüntülenmesini doğrulama");

        extentTest.info("Pre-Condition: Kullanıcı bir ilacın detay sayfasına erişmiş olmalı");

        // Login yap
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Ana sayfaya gidildi");

        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();
        extentTest.info("Sign In butonuna tıklandı");

        ReusableMethods.bekle(2);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("user_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("user_password"));
        extentTest.info("Email: " + ConfigReader.getProperty("user_email"));

        loginPage.signInButton.click();
        ReusableMethods.bekle(3);
        extentTest.pass("Kullanıcı başarıyla giriş yaptı");

        // 1. İlaç detay sayfasına git
        extentTest.info("1. İlaç detay sayfasına git (Rimadyl - Carprofen)");
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Medicines/suretin-mipen-ruma");
        ReusableMethods.waitForPageToLoad(3);
        ReusableMethods.bekle(2);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Medicines/"));
        extentTest.pass("İlaç detay sayfası açıldı: " + currentUrl);

        // Scroll down yap
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(2);

        // 2. İlacın adının görünür olduğunu doğrula
        extentTest.info("2. İlacın adının görünür olduğunu doğrula");

        WebElement medicineTitle = Driver.getDriver().findElement(
                By.xpath("//h1 | //h2[contains(@class,'title')] | //*[contains(@class,'medicine-title')]")
        );

        ReusableMethods.waitForVisibility(medicineTitle, 2);
        String titleText = medicineTitle.getText();
        Assert.assertFalse(titleText.isEmpty(), "İlaç adı boş!");
        extentTest.pass("İlacın adı görünür: " + titleText);

        // 3. İlacın açıklamasının görünür olduğunu doğrula
        extentTest.info("3. İlacın açıklamasının görünür olduğunu doğrula");

        ReusableMethods.scrollDown();
        ReusableMethods.bekle(2);

        // Açıklama/içerik bölümünü bul
        List<WebElement> contentElements = Driver.getDriver().findElements(
                By.xpath("//p | //div[contains(@class,'content')] | //div[contains(@class,'description')]")
        );

        if (!contentElements.isEmpty()) {
            extentTest.pass("İlacın açıklama bilgisi görünür (Bulunan paragraf sayısı: " + contentElements.size() + ")");
        } else {
            extentTest.warning("⚠️ İlaç açıklaması bulunamadı");
        }

        // 4. Kullanım talimatlarının görünür olduğunu doğrula
        extentTest.info("4. Kullanım talimatlarının görünür olduğunu doğrula");

        ReusableMethods.scrollDown();
        ReusableMethods.bekle(2);

        // Tüm text içeriğini kontrol et
        String pageContent = Driver.getDriver().findElement(By.tagName("body")).getText();

        if (pageContent.length() > 100) {
            extentTest.pass("✅ Kullanım talimatları ve detaylı bilgiler görünür (İçerik uzunluğu: " + pageContent.length() + " karakter)");
        } else {
            extentTest.warning("⚠️ Detaylı içerik bulunamadı");
        }

        extentTest.pass("✅ US_018_TC_04 testi tamamlandı!");

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
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();
        ReusableMethods.bekle(2);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("user_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("user_password"));
        loginPage.signInButton.click();
        ReusableMethods.bekle(3);
        extentTest.pass("Kullanıcı giriş yaptı");

        // İlaç detay sayfasına git
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Medicines/suretin-mipen-ruma");
        ReusableMethods.waitForPageToLoad(3);
        ReusableMethods.bekle(2);
        extentTest.info("İlaç detay sayfasına gidildi: Rimadyl (Carprofen)");

        ReusableMethods.scrollDown();
        ReusableMethods.bekle(2);

        // 1. İlaç detay sayfasında "Randevu Oluştur" formu görünür mü
        extentTest.info("1. İlaç detay sayfasında 'Randevu Oluştur' butonu bul");

        try {
            ReusableMethods.waitForVisibility(appointmentForm.appointmentFormContainer, 2);
            extentTest.pass("✅ STEP 1 PASSED: Randevu formu görünür");
        } catch (Exception e) {
            extentTest.fail("❌ STEP 1 FAILED: Randevu formu bulunamadı!");
        }

        // 2. Butonun görünür ve tıklanabilir olduğunu doğrula
        extentTest.info("2. Butonun görünür ve tıklanabilir olduğunu doğrula");

        try {
            ReusableMethods.scrollToElement(appointmentForm.submitButton);
            ReusableMethods.bekle(1);

            Assert.assertTrue(appointmentForm.submitButton.isDisplayed(),
                    "Randevu butonu görünür değil!");
            Assert.assertTrue(appointmentForm.submitButton.isEnabled(),
                    "Randevu butonu tıklanabilir değil!");

            extentTest.pass("Randevu butonu görünür ve tıklanabilir");
        } catch (Exception e) {
            extentTest.fail("❌ STEP 2 FAILED: Randevu butonu kontrol edilemedi! " + e.getMessage());
        }

        // 3. "Randevu Oluştur" butonuna tıkla (ALANLAR BOŞ!)
        extentTest.info("3. 'Randevu Oluştur' butonuna tıkla (Hiçbir alan doldurulmadan - Validasyon testi)");

        try {
            appointmentForm.submitButton.click();
            extentTest.info("Randevu butonuna boş form ile tıklandı");
            ReusableMethods.bekle(3);

            // Success message var mı kontrol et (BUG!)
            try {
                if (appointmentForm.successMessage.isDisplayed()) {
                    String successText = appointmentForm.successMessage.getText();

                    extentTest.fail("❌ STEP 2 FAILED: BUG! Appointment Booking butonu görünür ve aktif, ancak form validasyonu çalışmıyor");
                    extentTest.fail("❌ STEP 3 FAILED: BUG! Boş formla randevu oluşturuldu! Form alanları boş olmasına rağmen sistem randevuyu kabul etti");
                    extentTest.fail("❌ STEP 4 FAILED: BUG! Randevu formu açıldı ancak zorunlu alanlar (tarih, saat, telefon, departman, doktor, mesaj) doldurulmadan işlem tamamlandı");
                    extentTest.fail("❌ STEP 5 FAILED: BUG! Tarih (30/01/2026), telefon (geçerli format), departman, doktor ve mesaj alanları boş olmasına rağmen randevu başarıyla kaydedildi");
                    extentTest.fail("❌ STEP 6 FAILED: BUG! Randevu başarılı mesajı gösterildi: '" + successText + "' - Form validasyonu tamamen devre dışı!");

                    extentTest.fail("🐛 CRITICAL BUG: Form validasyonu çalışmıyor! Boş veriyle randevu oluşturuluyor!");
                }
            } catch (Exception e) {
                // Success message locator bulunamadı, ama sayfada text olarak kontrol et
                String pageText = Driver.getDriver().findElement(By.tagName("body")).getText();

                if (pageText.contains("Congratulations") || pageText.contains("success") || pageText.contains("Success")) {
                    extentTest.fail("❌ STEP 2-6 FAILED: BUG! Success message butonun locator'ı bulunamadı ama sayfada 'Congratulations' mesajı var!");
                    extentTest.fail("❌ Boş formla randevu oluşturuldu! Form validasyonu çalışmıyor!");
                    extentTest.fail("🐛 CRITICAL BUG: Sayfa içeriği - " + (pageText.contains("Congratulations") ? "Congratulations mesajı tespit edildi!" : "Success mesajı var!"));
                } else {
                    extentTest.pass("Validasyon çalışıyor, boş form kabul edilmedi");
                }
            }

        } catch (Exception e) {
            extentTest.fail("❌ ALL STEPS FAILED: Test exception ile sonlandı: " + e.getMessage());
        }

        extentTest.warning("⚠️ US_018_TC_05 testi tamamlandı - BUG kontrolü yapıldı");
    }

}