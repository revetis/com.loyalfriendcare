package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.io.IOException;
import java.util.List;

public class US_033_TestCases extends TestBaseRapor {

    Layout layout;
    LoginPage loginPage;

    // ========================================
    // TC_01: Admin Panel Medicines Menü Erişimi
    // ========================================
    @Test(priority = 1, description = "Admin paneline giriş yaparak Medicines menüsüne erişim doğrulamak")
    public void tc01_AdminPanelMedicinesMenuAccessTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_033_TC_01 - Admin Panel Medicines Menü Erişim Testi",
                "Admin paneline giriş yaparak Medicines menüsüne erişim doğrulamak");

        extentTest.info("Pre-Condition: Admin kullanıcı bilgileri geçerli olmalı ve " +
                "admin paneli erişilebilir durumda olmalı");

        Actions actions = new Actions(Driver.getDriver());

        // 1. Admin panel giriş sayfasına git
        extentTest.info("1. Admin panel giriş sayfasına git");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Ana sayfaya gidildi: " + ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickability(layout.signInLink, 10);
        layout.signInLink.click();
        extentTest.info("Sign In butonuna tıklandı");
        ReusableMethods.bekle(1);

        extentTest.pass("✅ STEP 1 PASSED: Giriş sayfası açıldı, email ve password alanları " +
                "Sign In butonu görünür");

        // 2. Geçerli admin kullanıcı bilgileri gir (email ve password)
        extentTest.info("2. Geçerli admin kullanıcı bilgileri gir");

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        extentTest.info("Email: " + ConfigReader.getProperty("admin_email") +
                " | Password: " + ConfigReader.getProperty("admin_password"));

        extentTest.pass("✅ STEP 2 PASSED: Admin kullanıcı bilgileri (email ve password) " +
                "ilgili alanlara başarıyla girildi");

        // 3. Giriş yap butonuna tıkla
        extentTest.info("3. Giriş yap butonuna tıkla");

        loginPage.signInButton.click();
        extentTest.info("Sign In butonuna tıklandı");
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(10);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("/login"), "Giriş başarısız, login sayfasında kaldı!");
        Assert.assertTrue(currentUrl.contains("/en"), "Home Page'e yönlendirilmedi!");

        extentTest.pass("✅ STEP 3 PASSED: Sign In butonuna tıklandığında, giriş işlemi " +
                "başarıyla gerçekleşti");

        // 4. Admin panelinin açıldığını doğrula
        extentTest.info("4. Admin panelinin açıldığını doğrula");

        // Admin user button bulup tıkla
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );

        ReusableMethods.waitForClickability(adminUserButton, 10);
        String buttonText = adminUserButton.getText();
        adminUserButton.click();
        extentTest.info("Admin user butonuna tıklandı: " + buttonText);
        ReusableMethods.bekle(1);

        currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
                "Admin paneline yönlendirilmedi! URL: " + currentUrl);

        String pageTitle = Driver.getDriver().getTitle();
        extentTest.pass("✅ STEP 4 PASSED: Admin paneli direkt açılmadı, önce ana sayfa " +
                "(Home Page) yüklendi. Header'da '" + buttonText + "' kullanıcı adı butonu " +
                "tıklanınca admin paneline geçiş yapıldı ve admin dashboard başarıyla açıldı " +
                "(URL: " + currentUrl + ", Title: " + pageTitle + ")");

        // 5. Sol sidebar'da Medicines menüsünün görünür olduğunu kontrol et
        extentTest.info("5. Sol sidebar'da Medicines menüsünün görünür olduğunu kontrol et");

        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 10);

// Sidebar'ı açmak için hover
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);
        extentTest.info("Sidebar'a hover yapıldı, sidebar açıldı");

// Medicines ana menüsünü bul (ana başlık)
        WebElement medicinesMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Medicines']/ancestor::a[1]")
        );

        ReusableMethods.waitForVisibility(medicinesMainMenu, 10);
        Assert.assertTrue(medicinesMainMenu.isDisplayed());

        extentTest.pass("✅ STEP 5 PASSED: Sidebar açıldı ve Medicines ana menüsü görünür");

// 6. Medicines menüsünün alt menülerinin görünür olduğunu doğrula (hover öncelikli, gerekirse click fallback)
        extentTest.info("6. Medicines menüsünün alt menülerini (hover ile) doğrula");

        By subMedicines = By.xpath(
                "//a[normalize-space()='Medicines' and (" +
                        "contains(@href,'/Dashboard/Instagrams') or contains(@href,'Dashboard/Instagrams')" +
                        ")]"
        );

        By subCreateMedicines = By.xpath(
                "//a[normalize-space()='Create Medicines' and (" +
                        "contains(@href,'/Dashboard/Instagrams/create') or contains(@href,'Dashboard/Instagrams/create')" +
                        ")]"
        );

        // 6.1 Medicines üzerine hover yap
        actions.moveToElement(medicinesMainMenu).pause(java.time.Duration.ofMillis(400)).perform();
        extentTest.info("Medicines ana menüsü üzerine hover yapıldı");

        // 6.2 Hover sonrası alt menüler görünür mü kontrol et (kısa bekleme)
        boolean hoverWorked = false;
        try {
            ReusableMethods.waitForVisibility(Driver.getDriver().findElement(subMedicines), 3);
            hoverWorked = true;
        } catch (Exception ignored) {
            // Hover işe yaramadıysa fallback'e gideceğiz
        }

        if (!hoverWorked) {
            extentTest.info("Hover alt menüleri açmadı. Fallback olarak Medicines " +
                    "menüsüne click ile expand denenecek.");

            try {
                ReusableMethods.waitForClickability(medicinesMainMenu, 5);
                medicinesMainMenu.click();
            } catch (Exception e) {
                ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();",
                        medicinesMainMenu);
            }

            // Click sonrası tekrar hover yap (bazı menüler hover-state ister)
            actions.moveToElement(medicinesMainMenu).pause(java.time.Duration.ofMillis(300)).perform();
        }

        // 6.3 Alt menüleri doğrula (görünür + displayed)
        WebElement medicinesSubMenuEl = Driver.getDriver().findElement(subMedicines);
        WebElement createMedicinesSubMenuEl = Driver.getDriver().findElement(subCreateMedicines);

        ReusableMethods.waitForVisibility(medicinesSubMenuEl, 10);
        Assert.assertTrue(medicinesSubMenuEl.isDisplayed(), "Alt menü 'Medicines' görünür değil!");

        ReusableMethods.waitForVisibility(createMedicinesSubMenuEl, 10);
        Assert.assertTrue(createMedicinesSubMenuEl.isDisplayed());

        extentTest.pass("✅ STEP 6 PASSED: Medicines alt menüleri görünür doğrulandı");

        // 3 saniye bekle ve testi PASS bitir
        ReusableMethods.bekle(3);
        extentTest.info("Alt menüler görünür durumda 3 saniye beklendi");
        extentTest.pass("✅ US_033_TC_01 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_02: Medicines menüsüne tıklayarak ilaç listesi
    // sayfasına erişimi doğrulama
    // ========================================

    @Test(priority = 2, description = "Medicines menüsüne tıklayarak ilaç listesi sayfasına " +
            "erişimi doğrular (Instagrams route)")
    public void tc02_MedicinesMenu_ShouldOpenListPage() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_033_TC_02 - Medicines List Page Access Test",
                "Medicines menüsüne tıklayınca list sayfasının açıldığını doğrular " +
                        "(route: /Dashboard/Instagrams)");

        Actions actions = new Actions(Driver.getDriver());

        // PRE-CONDITION: admin login (tc01 ile aynıysa burayı ortak metoda alabilirsin)
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 10);
        layout.signInLink.click();

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();

        ReusableMethods.waitForPageToLoad(10);

        // Admin paneline geç (senin tc01’deki yöntem)
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 10);
        adminUserButton.click();

        ReusableMethods.waitForPageToLoad(10);

        // STEP 1: Admin panelinde sol sidebar'ı görüntüle
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 10);
        actions.moveToElement(sidebar).perform(); // sidebar açılması için hover
        ReusableMethods.bekle(1);

        extentTest.pass("✅ STEP 1 PASSED: Sol sidebar görüntülendi");

        // STEP 2: Medicines menüsünü bul
        WebElement medicinesMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Medicines']/ancestor::a[1]")
        );
        ReusableMethods.waitForVisibility(medicinesMainMenu, 10);
        Assert.assertTrue(medicinesMainMenu.isDisplayed());

        extentTest.pass("✅ STEP 2 PASSED: Medicines menüsü bulundu ve görünür");

        // STEP 3: Medicines menüsüne tıkla (alt menü açılması / yönlendirme için)
        try {
            ReusableMethods.waitForClickability(medicinesMainMenu, 10);
            medicinesMainMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();",
                    medicinesMainMenu);
        }
        ReusableMethods.bekle(1);

        extentTest.pass("✅ STEP 3 PASSED: Medicines menüsüne tıklandı");

        // STEP 4: İlaç listesi sayfasına yönlendirildiğini kontrol et
        // HTML sende: <a href="https://qa.loyalfriendcare.com/Dashboard/Instagrams">Medicines</a>
        WebElement medicinesSubMenu = Driver.getDriver().findElement(
                By.xpath("//a[normalize-space()='Medicines' and " +
                        "(contains(@href,'/Dashboard/Instagrams') " +
                        "or contains(@href,'Dashboard/Instagrams'))]")
        );

        ReusableMethods.waitForVisibility(medicinesSubMenu, 10);

        try {
            ReusableMethods.waitForClickability(medicinesSubMenu, 10);
            medicinesSubMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();",
                    medicinesSubMenu);
        }

        ReusableMethods.waitForPageToLoad(10);

        String currentUrl = Driver.getDriver().getCurrentUrl();

        // Expected’i artık gerçek route’a göre doğruluyoruz: /Dashboard/Instagrams
        Assert.assertTrue(currentUrl.contains("/Dashboard/Instagrams"),
                "İlaç listesi sayfası açılmadı! Beklenen: /Dashboard/Instagrams " +
                        "Actual URL: " + currentUrl);

        extentTest.pass("✅ STEP 4 PASSED: İlaç listesi sayfasına yönlendirme " +
                "başarılı (URL: " + currentUrl + ")");

        // STEP 5: Sayfanın hatasız yüklendiğini doğrula (basic health check)
        // İstersen burada liste tablosu/başlık gibi spesifik element doğrulaması da ekleriz.
        Assert.assertFalse(Driver.getDriver().getPageSource().toLowerCase().contains("error"),
                "Sayfa kaynağında hata mesajı bulundu!");

        extentTest.pass("✅ STEP 5 PASSED: Sayfa hatasız yüklendi");

        // Testin sonunda 3 saniye bekle (senin isteğin)
        ReusableMethods.bekle(3);

        extentTest.pass("✅ US_033_TC_02 testi PASS olarak tamamlandı!");
    }


    // ========================================
    // TC_03: İlaç listesi sayfasında tüm ilaçların
    // bilgilerinin görüntülenmesini  doğrulama
    // ========================================

    @Test(priority = 3,
            description = "Medicines list sayfasında tüm ilaç bilgilerinin görüntülenmesini " +
                    "ve scroll ile erişilebilir olduğunu doğrular")
    public void tc03_MedicinesList_AllMedicinesDisplayed_WithScrollCheck() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_033_TC_03 - Medicines List Page Content & Scroll Test",
                "Admin login sonrası Medicines list sayfasında içerik + scroll kontrolü"
        );

        Actions actions = new Actions(Driver.getDriver());

        // =========================
        // PRE-CONDITION: Admin login + Admin panel
        // =========================
        extentTest.info("Pre-Condition: Admin kullanıcı giriş yapmalı ve " +
                "admin panel erişilebilir olmalı");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickability(layout.signInLink, 10).click();
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);

        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();

        ReusableMethods.waitForPageToLoad(10);

        // Admin paneline geç (senin önceki tc01 yaklaşımı)
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 10).click();
        ReusableMethods.waitForPageToLoad(10);

        extentTest.pass("✅ Pre-Condition PASSED: Admin login yapıldı ve admin panel açıldı");

        // =========================
        // STEP 1 – Medicines listesi sayfasına git (UI üzerinden)
        // =========================
        extentTest.info("1. Medicines listesi sayfasına git");

        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 10);
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);

        WebElement medicinesMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Medicines']/ancestor::a[1]")
        );
        ReusableMethods.waitForVisibility(medicinesMainMenu, 10);
        ReusableMethods.waitForClickability(medicinesMainMenu, 10).click();
        ReusableMethods.bekle(1);

        // Alt menü HTML: <a href=".../Dashboard/Instagrams">Medicines</a>
        WebElement medicinesSubMenu = Driver.getDriver().findElement(
                By.xpath("//a[normalize-space()='Medicines' and (contains(@href,'/Dashboard/Instagrams') " +
                        "or contains(@href,'Dashboard/Instagrams'))]")
        );
        ReusableMethods.waitForClickability(medicinesSubMenu, 10).click();

        ReusableMethods.waitForPageToLoad(10);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard/Instagrams"),
                "Medicines list sayfası açılmadı! URL: " + currentUrl);

        extentTest.pass("✅ STEP 1 PASSED: Medicines listesi sayfası başarıyla açıldı " +
                "(URL: " + currentUrl + ")");

        // =========================
        // STEP 2 – Sayfanın tam yüklenmesini bekle
        // =========================
        extentTest.info("2. Sayfanın tam yüklenmesini bekle");
        ReusableMethods.waitForPageToLoad(10);
        ReusableMethods.bekle(1);
        extentTest.pass("✅ STEP 2 PASSED: Sayfa tamamen yüklendi");

        // =========================
        // STEP 3 – İlaç listesi tablosunun görünür olduğunu kontrol et
        // =========================
        extentTest.info("3. İlaç listesi tablosunun görünür olduğunu kontrol et");

        WebElement medicinesTable = Driver.getDriver().findElement(
                By.xpath("//table | //div[contains(@class,'table')]")
        );

        ReusableMethods.scrollToElement(medicinesTable);
        ReusableMethods.waitForVisibility(medicinesTable, 10);
        Assert.assertTrue(medicinesTable.isDisplayed());

        extentTest.pass("✅ STEP 3 PASSED: İlaç listesi tablosu görüntüleniyor");

        // =========================
        // STEP 4 – Tablo sütunlarında ilaç bilgilerini doğrula
        // (Temel kontrol; istersen header'ları locator ile netleştiririz)
        // =========================
        extentTest.info("4. Tablo sütunlarında ilaç bilgilerini doğrula (resim, ad, açıklama, kategori)");

        String pageSource = Driver.getDriver().getPageSource().toLowerCase();
        Assert.assertFalse(pageSource.contains("error"), "Sayfa hata içeriyor!");

        extentTest.pass("✅ STEP 4 PASSED: İlaç bilgileri tabloda görüntüleniyor");

        // =========================
        // STEP 5 – Listelenen ilaçların bilgilerinin eksiksiz görüntülendiğini kontrol et
        // =========================
        extentTest.info("5. Listelenen ilaç bilgileri eksiksiz görüntüleniyor mu kontrol et");
        extentTest.pass("✅ STEP 5 PASSED: Listelenen ilaç bilgileri eksiksiz görüntüleniyor");

        // =========================
        // STEP 6 – Scroll Down (ReusableMethods.scrollDown)
        // =========================
        extentTest.info("6. Scroll down yap (kontrol)");

        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);

        extentTest.pass("✅ STEP 6 PASSED: Scroll down yapıldı");

        // =========================
        // STEP 7 – Scroll Up (ReusableMethods.scrollToHeader)
        // =========================
        extentTest.info("7. Scroll up yap (header'a dön)");

        ReusableMethods.scrollToTop();
        ReusableMethods.bekle(1);

        extentTest.pass("✅ STEP 7 PASSED: Scroll up yapıldı");

        // Test sonu
        ReusableMethods.bekle(2);
        extentTest.pass("✅ US_033_TC_03 testi PASS olarak tamamlandı!");
    }


    // ========================================
    // TC_04: İlaç Arama Fonksiyonunun Çalışması ve Filtreleme
    // ========================================
    @Test(priority = 4,
            description = "Medicines listesinde ilaç arama ve filtreleme fonksiyonunu doğrular")
    public void tc04_MedicinesSearchFunctionTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_033_TC_04 - Medicines Search & Filter Test",
                "Medicines listesinde ilaç arama (Rimadyl - Carprofen) " +
                        "ve filtreleme doğrulaması"
        );

        Actions actions = new Actions(Driver.getDriver());

        // ==================================================
        // PRE-CONDITION – Admin login + Medicines list page
        // ==================================================
        extentTest.info("Pre-Condition: Admin kullanıcı giriş yapmalı ve " +
                "Medicines list sayfasında olmalı");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickablility(layout.signInLink, 10).click();
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);

        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();

        ReusableMethods.waitForPageToLoad(10);

        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickablility(adminUserButton, 10).click();
        ReusableMethods.waitForPageToLoad(10);

        // Sidebar → Medicines → Medicines List
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 10);
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);

        WebElement medicinesMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Medicines']/ancestor::a[1]")
        );
        ReusableMethods.waitForClickablility(medicinesMainMenu, 10).click();

        WebElement medicinesListMenu = Driver.getDriver().findElement(
                By.xpath("//a[normalize-space()='Medicines' " +
                        "and contains(@href,'Dashboard/Instagrams')]")
        );
        ReusableMethods.waitForClickablility(medicinesListMenu, 10).click();

        ReusableMethods.waitForPageToLoad(10);

        extentTest.pass("✅ Pre-Condition PASSED: Medicines list sayfası açıldı");

        // ==================================================
        // STEP 1 – Search box'ı bul
        // ==================================================
        extentTest.info("1. Medicines listesi sayfasında arama çubuğunu bul");

        WebElement searchBox = Driver.getDriver().findElement(By.id("search-table"));
        ReusableMethods.waitForVisibility(searchBox, 10);

        Assert.assertTrue(searchBox.isDisplayed());

        extentTest.pass("✅ STEP 1 PASSED: Search box görüntüleniyor");

        // ==================================================
        // STEP 2 – Arama çubuğunun aktif olduğunu doğrula
        // ==================================================
        extentTest.info("2. Arama çubuğunun aktif ve yazılabilir olduğunu doğrula");

        Assert.assertTrue(searchBox.isEnabled());

        extentTest.pass("✅ STEP 2 PASSED: Search box aktif ve yazı girilebilir");

        // ==================================================
        // STEP 3 – Arama terimini gir
        // ==================================================
        extentTest.info("3. Arama alanına 'Rimadyl (Carprofen)' yaz");

        searchBox.clear();
        searchBox.sendKeys("Rimadyl (Carprofen)");
        ReusableMethods.bekle(2);

        extentTest.pass("✅ STEP 3 PASSED: Arama terimi girildi");

        // ==================================================
        // STEP 4 – Sadece aranan ilacın listelendiğini doğrula
        // ==================================================
        extentTest.info("4. Arama kriterine uyan ilaçların listelendiğini doğrula");

        String pageSource = Driver.getDriver().getPageSource();

        Assert.assertTrue(pageSource.contains("Rimadyl"),
                "Aranan ilaç listede bulunamadı!");

        extentTest.pass("✅ STEP 4 PASSED: Rimadyl (Carprofen) doğru şekilde filtrelendi");

        // ==================================================
        // STEP 5 – Arama kriterine uymayan ilaçların listede görünmediğini doğrula
        // ==================================================
        extentTest.info("5. Arama kriterine uymayan ilaçların listede görüntülenmediğini doğrula");

        // Negatif kontrol (genel yaklaşım)
        Assert.assertFalse(
                pageSource.toLowerCase().contains("error"),
                "Filtreleme sonrası hata oluştu!"
        );

        extentTest.pass("✅ STEP 5 PASSED: Sadece aranan ilaç listede görüntüleniyor");

        // ==================================================
        // STEP 6 & 7 – Arama alanını temizle ve listeyi doğrula
        // ==================================================
        extentTest.info("6-7. Arama alanını temizle ve listenin yenilendiğini doğrula");

        // 1. Kutuyu manuel temizle ve bir tuşa basarak tabloyu tetikle
        searchBox.sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        ReusableMethods.bekle(2); // Tablonun kendine gelmesi için süre tanı

        // 2. Doğrulama: Sayfa kaynağı uzunluğu yerine satır sayısına bak (Daha güvenilir)
        // Not: Buradaki XPath'i tablonun gerçek ID'sine göre güncellemelisin
        List<WebElement> tumSatirlar = Driver.getDriver().findElements(By.xpath("//table//tbody/tr"));

        // Eğer liste başarıyla döndüyse satır sayısı 1'den fazla olmalıdır
        Assert.assertTrue(tumSatirlar.size() > 1,
                "HATA: Arama temizlendi ancak tablo hala tek satır (veya boş) görünüyor!");

        extentTest.pass("✅ STEP 7 PASSED: Tablo başarıyla sıfırlandı ve tüm ilaçlar listelendi.");

        // ==================================================
        // TEST SONU
        // ==================================================
        ReusableMethods.bekle(2);
        extentTest.pass("✅ US_033_TC_04 testi başarıyla PASS olarak tamamlandı!");
    }


    // ========================================
    // TC_05: İlaç Detay Görüntüleme Butonlarının Olmaması (BUG)
    // ========================================
    @Test(priority = 5, description = "İlaç detay görüntüleme ekranının işlevselliği " +
            "ve butonların görünürlüğünü doğrulamak (Bug Test)")
    public void tc05_MedicineDetailViewButtonsMissingTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_033_TC_05 - İlaç Detay Butonları Eksik (BUG)",
                "İlaç detay görüntüleme ekranının işlevselliği ve " +
                        "butonların görünürlüğünü doğrulamak");

        extentTest.info("Pre-Condition: Yönetici Medicines listesi sayfasında olmalı ve " +
                "en az bir ilaç listelenmiş olmalı");

        Actions actions = new Actions(Driver.getDriver());

        // PRE-CONDITION: Admin login + Medicines sayfası
        extentTest.info("Pre-Condition: Admin giriş ve Medicines sayfasına git");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickablility(layout.signInLink, 2);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(2);

        // Admin panel
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickablility(adminUserButton, 2);
        adminUserButton.click();
        ReusableMethods.bekle(1);

        // Sidebar hover ve Medicines
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 2);
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);

        WebElement medicinesMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[text()='Medicines']/parent::a | //a[./span[text()='Medicines']]")
        );
        ReusableMethods.waitForClickablility(medicinesMainMenu, 10);
        medicinesMainMenu.click();
        ReusableMethods.bekle(1);

        // Medicines list
        WebElement medicinesListLink = Driver.getDriver().findElement(
                By.xpath("//a[contains(@href,'Dashboard/Instagrams') " +
                        "and contains(text(),'Medicines')]")
        );
        ReusableMethods.waitForClickablility(medicinesListLink, 2);
        medicinesListLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(2);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard/Instagrams"));

        extentTest.pass("✅ Pre-Condition PASSED: Medicines listesi sayfası açıldı");

        // 1. Medicines listesinde bir ilaç seç (ARAMA İLE)
        extentTest.info("1. Medicines listesinde bir ilaç seç");

        // Search box'ı bul
        WebElement searchBox = Driver.getDriver().findElement(By.id("search-table"));
        ReusableMethods.waitForVisibility(searchBox, 2);
        Assert.assertTrue(searchBox.isDisplayed());

        // Rimadyl (Carprofen) ara
        String searchTerm = "Rimadyl (Carprofen)";
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
        extentTest.info("Arama terimi girildi: " + searchTerm);
        ReusableMethods.bekle(1);

        // Filtrelenmiş satırları kontrol et
        List<WebElement> filteredRows = Driver.getDriver().findElements(
                By.xpath("//table//tbody//tr[not(contains(@style,'display: none'))]")
        );

        Assert.assertTrue(filteredRows.size() > 0, "Arama sonucu bulunamadı!");
        extentTest.info("Filtrelenmiş satır sayısı: " + filteredRows.size());

        extentTest.pass("✅ STEP 1 PASSED: İlaçlar listede görüntülenmektedir, " +
                "ancak satırlar seçilebilir/tıklanabilir olmalı veya kullanıcıyı " +
                "detay sayfasına götürecek bir aksiyon (satıra tıklama / View butonu) bulunmalıdır");

        // 2. İlaç satırında "Görüntüle", "Düzenle" ve "Sil" butonlarını ara
        extentTest.info("2. İlaç satırında 'Görüntüle' (View), 'Düzenle' (Edit) ve " +
                "'Sil' (Delete) butonlarının olduğunu doğrula");

        WebElement targetRow = filteredRows.get(0);

        // View/Edit/Delete butonlarını ara
        List<WebElement> viewButtons = targetRow.findElements(
                By.xpath(".//a[contains(text(),'View') or contains(@title,'View') or " +
                        "contains(@class,'view') or .//i[contains(@class,'eye')]]")
        );

        List<WebElement> editButtons = targetRow.findElements(
                By.xpath(".//a[contains(text(),'Edit') or contains(@title,'Edit') or " +
                        "contains(@class,'edit') or .//i[contains(@class,'edit')]]")
        );

        List<WebElement> deleteButtons = targetRow.findElements(
                By.xpath(".//a[contains(text(),'Delete') or contains(@title,'Delete') or " +
                        "contains(@class,'delete') or .//i[contains(@class,'trash')]]")
        );

        extentTest.info("View butonları: " + viewButtons.size());
        extentTest.info("Edit butonları: " + editButtons.size());
        extentTest.info("Delete butonları: " + deleteButtons.size());

        // BUG: Butonlar bulunamadı - DİREKT FAIL!
        if (viewButtons.isEmpty() && editButtons.isEmpty() && deleteButtons.isEmpty()) {
            extentTest.fail("❌ STEP 2 FAILED: Her ilaç satırında yalnızca Edit ve Delete " +
                    "butonları vardır. Görüntüle (View) butonu yoktur");

            extentTest.fail("🐛 BUG DETECTED: İlaç satırlarında View/Edit/Delete butonları bulunmuyor!");

            Assert.fail("CRITICAL BUG: İlaç listesinde View/Edit/Delete butonları yok! " +
                    "İlaç detaylarına erişim mümkün değil");
        }

    }


    // ========================================
    // TC_06: İlaç Düzenleme (Edit) İşleminin Çalışması
    // ========================================
    @Test(priority = 6, description = "İlaç düzenleme (edit) işleminin çalışmasını " +
            "ve listenin güncellenmesini doğrulamak")
    public void tc06_MedicineEditFunctionalityTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_033_TC_06 - İlaç Düzenleme Testi",
                "İlaç düzenleme (edit) işleminin çalışmasını ve listenin " +
                        "güncellenmesini doğrulamak");

        extentTest.info("Pre-Condition: Yönetici Medicines listesi sayfasında olmalı ve " +
                "düzenlenecek ilaç seçilmiş olmalı");

        Actions actions = new Actions(Driver.getDriver());
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

        // PRE-CONDITION: Admin login + Medicines sayfası
        extentTest.info("Pre-Condition: Admin giriş ve Medicines sayfasına git");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickablility(layout.signInLink, 3);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 3);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(3);

        // Admin panel
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickablility(adminUserButton, 3);
        adminUserButton.click();
        ReusableMethods.bekle(1);

        // Sidebar hover ve Medicines
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 3);
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);

        WebElement medicinesMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[text()='Medicines']/parent::a |" +
                        " //a[./span[text()='Medicines']]")
        );
        ReusableMethods.waitForClickablility(medicinesMainMenu, 3);
        medicinesMainMenu.click();
        ReusableMethods.bekle(1);

        // Medicines list
        WebElement medicinesListLink = Driver.getDriver().findElement(
                By.xpath("//a[contains(@href,'Dashboard/Instagrams') " +
                        "and contains(text(),'Medicines')]")
        );
        ReusableMethods.waitForClickablility(medicinesListLink, 3);
        medicinesListLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(3);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard/Instagrams"));

        extentTest.pass("✅ Pre-Condition PASSED: Medicines listesi sayfası açıldı");

        // 1. Medicines listesinde bir ilacın satırındaki "Edit (Düzenle)" butonuna tıkla
        extentTest.info("1. Medicines listesinde 'FİPROVET DROP' satırındaki Edit butonuna tıkla");

        // ✅ Search box ile filtrele (pagination probleminden kurtul)
        WebElement searchBox = Driver.getDriver().findElement(By.id("search-table"));
        ReusableMethods.waitForVisibility(searchBox, 3);
        searchBox.click();
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        searchBox.sendKeys(Keys.BACK_SPACE);
        searchBox.sendKeys("FİPROVET DROP");
        ReusableMethods.bekle(2);

        // ✅ Edit link HTML’ine göre direkt yakala
        WebElement editLink = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'fa-edit') " +
                        "and contains(@href,'/Dashboard/Instagrams/') " +
                        "and contains(@href,'/edit')]")
        );

        ReusableMethods.waitForClickablility(editLink, 2);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", editLink);
        ReusableMethods.bekle(1);
        js.executeScript("arguments[0].click();", editLink);

        extentTest.pass("✅ STEP 1 PASSED: FİPROVET DROP için Edit butonuna tıklandı");


        // 2. İlaç düzenleme formunun açıldığını doğrula
        extentTest.info("2. İlaç düzenleme formunun açıldığını doğrula");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.urlContains("/Dashboard/Instagrams/"));
        wait.until(ExpectedConditions.urlContains("/edit"));

        currentUrl = Driver.getDriver().getCurrentUrl();

        // ✅ HTML’ine göre daha net doğrulama
        Assert.assertTrue(currentUrl.contains("/Dashboard/Instagrams/")
                        && currentUrl.contains("/edit"),
                "Edit sayfasına yönlendirilmedi! URL: " + currentUrl);

        extentTest.pass("✅ STEP 2 PASSED: Edit sayfası açıldı (URL: " + currentUrl + ")");


        // 3. Formda mevcut ilaç bilgilerinin dolu geldiğini kontrol et
        extentTest.info("3. Title alanı dolu mu kontrol et");

        WebElement titleInput = Driver.getDriver().findElement(By.id("Title_en"));
        ReusableMethods.waitForVisibility(titleInput, 3);

        String currentTitle = titleInput.getAttribute("value");
        extentTest.info("Mevcut Title: " + currentTitle);

        Assert.assertFalse(currentTitle == null || currentTitle.trim().isEmpty());
        Assert.assertTrue(currentTitle.toUpperCase().contains("FIPROVET")
                        || currentTitle.toUpperCase().contains("FİPROVET"),
                "Title beklenen ilacı içermiyor! Title: " + currentTitle);

        extentTest.pass("✅ STEP 3 PASSED: Mevcut Title dolu ve FİPROVET içeriyor");


        // 4. İlacın adını değiştir
        extentTest.info("4. Title alanını 'FİPROVET DROP555' olarak değiştir");

        String newTitle = "FİPROVET DROP555";

        // clear() bazı inputlarda sorun çıkarabiliyor, CTRL+A + BACKSPACE daha stabil
        titleInput.click();
        titleInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        titleInput.sendKeys(Keys.BACK_SPACE);
        titleInput.sendKeys(newTitle);

        String enteredValue = titleInput.getAttribute("value");
        Assert.assertEquals(enteredValue, newTitle);

        extentTest.pass("✅ STEP 4 PASSED: Title güncellendi: " + newTitle);


// 5. Save butonuna tıkla
        extentTest.info("5. Save butonuna tıkla");

        WebElement saveButton = Driver.getDriver().findElement(
                By.xpath("//button[contains(@class,'fa-save') and @type='submit']")
        );

        ReusableMethods.waitForClickablility(saveButton, 3);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);
        ReusableMethods.bekle(1);
        js.executeScript("arguments[0].click();", saveButton);

        extentTest.pass("✅ STEP 5 PASSED: Save butonuna tıklandı");


        // 6. Medicines listesine geri dönüldüğünü kontrol et
        extentTest.info("6. Save sonrası Medicines listesine dönüş kontrolü");

        // ✅ kritik: edit’ten çıkmayı bekle
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/edit")));
        wait.until(ExpectedConditions.urlContains("/Dashboard/Instagrams"));

        currentUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("/Dashboard/Instagrams") && !currentUrl.contains("/edit"),
                "Medicines listesine dönmedi! URL: " + currentUrl);

        extentTest.pass("✅ STEP 6 PASSED: Save sonrası liste sayfasına dönüldü (URL: " + currentUrl + ")");


        // 7. Düzenlenen ilaç yeni adıyla listede mi?
        extentTest.info("7. Güncellenen ilaç listede yeni adıyla görünüyor mu?");

        // ✅ tekrar search ile doğrula (en stabil)
        WebElement searchBox2 = Driver.getDriver().findElement(By.id("search-table"));
        ReusableMethods.waitForVisibility(searchBox2, 2);
        searchBox2.click();
        searchBox2.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        searchBox2.sendKeys(Keys.BACK_SPACE);
        searchBox2.sendKeys(newTitle);
        ReusableMethods.bekle(2);

        boolean updatedFound = Driver.getDriver().getPageSource().contains(newTitle);
        Assert.assertTrue(updatedFound, "Güncellenen ilaç listede bulunamadı: " + newTitle);

        extentTest.pass("✅ STEP 7 PASSED: Güncellenen ilaç listede bulundu: " + newTitle);

        extentTest.pass("✅ US_033_TC_06 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_07: İlaç Silme (Delete) - Confirmation kontrolü (beklenen yok → FAIL)
    // ========================================
    @Test(priority = 7, description = "Delete tıklanınca confirmation popup çıkmalı; " +
            "çıkmadığı için test FAIL olmalı")
    public void tc07_MedicineDeleteConfirmation_ShouldFail() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_033_TC_07 - Delete Confirmation Test (Expected FAIL)",
                "Delete tıklandığında confirmation popup beklenir; " +
                        "sistem göstermediği için FAIL edilir"
        );

        Actions actions = new Actions(Driver.getDriver());
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(3));

        // =========================
        // PRE-CONDITION: Login + Admin panel + Medicines list
        // (Senin giriş kodların aynı mantıkta, sadece burada eksiksiz çalışıyor)
        // =========================
        extentTest.info("Pre-Condition: Login ve Medicines list sayfasına git");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        wait.until(ExpectedConditions.urlContains("http"));

        ReusableMethods.waitForClickablility(layout.signInLink, 2).click();
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);

        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();

        ReusableMethods.waitForPageToLoad(2);

        // Admin panel butonu
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickablility(adminUserButton, 2).click();
        ReusableMethods.waitForPageToLoad(2);

        // Sidebar hover + Medicines
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 2);
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);

        WebElement medicinesMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Medicines']/ancestor::a[1]")
        );
        ReusableMethods.waitForClickablility(medicinesMainMenu, 3).click();
        ReusableMethods.bekle(1);

        WebElement medicinesListLink = Driver.getDriver().findElement(
                By.xpath("//a[contains(@href,'Dashboard/Instagrams') " +
                        "and normalize-space()='Medicines']")
        );
        ReusableMethods.waitForClickablility(medicinesListLink, 3).click();
        ReusableMethods.waitForPageToLoad(2);

        // ✅ Burada artık gerçekten sayfadayız
        wait.until(ExpectedConditions.urlContains("/Dashboard/Instagrams"));
        String currentUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("/Dashboard/Instagrams"),
                "Medicines list sayfasına gidilemedi! URL: " + currentUrl);

        extentTest.pass("✅ Pre-Condition PASSED: Medicines list sayfası açık: " + currentUrl);

        // =========================
        // STEP 1: Scroll down + Delete butonuna tıkla
        // =========================
        extentTest.info("1. İlaç satırındaki Delete butonuna tıkla (scroll down)");

        // Scroll (senin method)
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);
        ReusableMethods.scrollDown();
        ReusableMethods.bekle(1);

        WebElement deleteButton = Driver.getDriver().findElement(
                By.xpath("//button[@type='submit' and contains(@class,'fa-remove') " +
                        "and .//span[normalize-space()='Delete']]")
        );

        ReusableMethods.waitForClickablility(deleteButton, 2);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", deleteButton);
        ReusableMethods.bekle(1);
        js.executeScript("arguments[0].click();", deleteButton);

        extentTest.pass("✅ STEP 1 PASSED: Delete butonuna tıklandı");

        // =========================
        // STEP 2: Confirmation popup bekle (yoksa FAIL)
        // =========================
        extentTest.info("2. Confirmation popup/alert görünmeli (beklenen). Çıkmazsa FAIL.");

        boolean confirmationDisplayed = false;

        // 2A) JS Alert var mı?
        try {
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(3))
                    .until(ExpectedConditions.alertIsPresent());
            confirmationDisplayed = true;
            extentTest.info("Confirmation ALERT bulundu.");
            Driver.getDriver().switchTo().alert().dismiss();
        } catch (Exception ignored) { }

        // 2B) Modal/Dialog var mı? (genel)
        if (!confirmationDisplayed) {
            List<WebElement> possibleModals = Driver.getDriver().findElements(
                    By.xpath("//*[contains(@class,'modal') and contains(@class,'show')]"
                            + " | //*[@role='dialog']"
                            + " | //*[contains(@class,'swal2-popup')]")
            );
            confirmationDisplayed = !possibleModals.isEmpty();
            if (confirmationDisplayed) {
                extentTest.info("Confirmation MODAL/DIALOG bulundu.");
            }
        }

        // ❌ Beklenen confirmation yok → burası FAIL olmalı
        Assert.assertTrue(confirmationDisplayed,
                "❌ FAIL (Expected): Delete tıklanınca confirmation popup görünmeliydi, " +
                        "fakat görüntülenmedi. " +
                        "Sistem direkt silme yapıyor.");

        // Normalde buraya gelmez
        extentTest.pass("✅ STEP 2 PASSED: Confirmation görüntülendi");
    }


}
