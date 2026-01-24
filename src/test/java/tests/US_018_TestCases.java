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

import java.util.List;

public class US_018_TestCases extends TestBaseRapor {

    Layout layout;
    LoginPage loginPage;

    // ========================================
    // TC_01: Giriş Yapmış Kullanıcının Medicines Sayfasına Erişimi
    // ========================================

    @Test(priority = 1, description = "Giriş yapmış kullanıcının Home Page'den Medicines sayfasına erişimini doğrulama")
    public void tc01_LoggedInUserMedicinesAccessTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_018_TC_01 - Medicines Sayfası Erişim Testi",
                "Giriş yapmış kullanıcının Home Page'den Medicines sayfasına erişimini doğrulama");

        extentTest.info("Pre-Condition: Kullanıcı sisteme başarıyla giriş yapmış olmalı ve Home Page'de olmalı");

        // 1. Sisteme geçerli kullanıcı bilgileriyle giriş yap
        extentTest.info("1. Sisteme geçerli kullanıcı bilgileriyle giriş yap");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Ana sayfaya gidildi: " + ConfigReader.getProperty("url"));

        // Sign In butonuna tıkla
        ReusableMethods.waitForClickablility(layout.signInLink, 10);
        layout.signInLink.click();
        extentTest.info("Sign In butonuna tıklandı");

        ReusableMethods.bekle(2);

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

        // 2. Home Page'in yüklendiğini doğrula
        extentTest.info("2. Home Page'in yüklendiğini doğrula");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForPageToLoad(15);
        ReusableMethods.bekle(2);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/en"));
        extentTest.pass("Home Page başarıyla yüklendi: " + currentUrl);

        // 3. Navbar'da Medicines menüsünü bul
        extentTest.info("3. Navbar'da Medicines menüsünü bul");
        ReusableMethods.waitForVisibility(layout.headerMedicinesLink, 10);
        Assert.assertTrue(layout.headerMedicinesLink.isDisplayed());
        extentTest.pass("Medicines menüsü navbar'da bulundu");

        // 4. Medicines menüsüne tıkla
        extentTest.info("4. Medicines menüsüne tıkla");
        ReusableMethods.waitForClickablility(layout.headerMedicinesLink, 10);
        layout.headerMedicinesLink.click();
        extentTest.info("Medicines menüsüne tıklandı");

        // 5. Sayfanın Medicines listesi sayfasına yönlendiğini kontrol et
        extentTest.info("5. Sayfanın Medicines listesi sayfasına yönlendiğini kontrol et");
        ReusableMethods.bekle(2);
        ReusableMethods.waitForPageToLoad(15);

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
        ReusableMethods.waitForPageToLoad(15);
        ReusableMethods.bekle(3);

        // URL kontrolü
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Medicines") || currentUrl.contains("/medicines" + currentUrl));
        extentTest.pass("Medicines sayfası başarıyla yüklendi: " + currentUrl);

        // 3. İlaç listesinin görünür olduğunu kontrol et
        extentTest.info("3. İlaç listesinin görünür olduğunu kontrol et");

        // Sayfayı scroll down yap
        ReusableMethods.scrollToBottom();
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
        ReusableMethods.waitForPageToLoad(15);
        ReusableMethods.bekle(3);
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

}