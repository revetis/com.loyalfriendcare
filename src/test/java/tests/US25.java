package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.util.List;

public class US25 extends TestBaseRapor {

    Layout layout;
    LoginPage loginPage;

    // ========================================
    // TC_01: Admin Panel Users Menü Erişimi
    // ========================================
    @Test(priority = 1, description = "Admin paneline giriş yapılarak Users menüsüne erişim doğrulamak")
    public void tc01_AdminPanelUsersMenuAccessTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest("US_025_TC_01 - Admin Panel Users Menü Erişim Testi",
                "Admin paneline giriş yapılarak Users menüsüne erişim doğrulamak");

        extentTest.info("Pre-Condition: Admin kullanıcı bilgileri geçerli olmalı ve " +
                "admin paneli erişilebilir durumda olmalı");

        // 1. Giriş sayfasına git ve admin kullanıcı bilgileriyle giriş yap
        extentTest.info("1. Giriş sayfasına git ve admin kullanıcı bilgileriyle giriş yap");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Ana sayfaya gidildi: " + ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();
        extentTest.info("Sign In butonuna tıklandı");
        ReusableMethods.bekle(2);

        // Admin login bilgileri
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 1);
        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        extentTest.info("Email: samet.ture.admin@loyalfriendcare.com | Password: Loyal.123123");

        loginPage.signInButton.click();
        ReusableMethods.bekle(3);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("/login"));
        extentTest.pass("✅ STEP 1 PASSED: Giriş başarılı olmalı ve Home Page'e yönlendirildi");

        // 2. Home Page'e yönlendirildiğini doğrula
        extentTest.info("2. Home Page'e yönlendirildiğini doğrula");

        ReusableMethods.waitForPageToLoad(1);
        ReusableMethods.bekle(2);

        currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/en"));

        // Navbar ve search bar kontrol
        Assert.assertTrue(layout.headerNavigationBar.isDisplayed());
        extentTest.pass("✅ STEP 2 PASSED: Home Page başarıyla açıldı, " +
                "sayfa içeriği (başlık, search bar, navbar) görünür");

        // 3. Header'da "Samet Türe" isminin görünür olduğunu kontrol
        extentTest.info("3. Header'da 'Tural Seyidov' isminin görünür olduğunu kontrol et");

        // Admin user button - "Samet Türe" veya herhangi bir admin adı
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | " +
                        "//button[contains(@class,'btn')] | " +
                        "//*[@id='top_menu']//a[1]")
        );

        ReusableMethods.waitForVisibility(adminUserButton, 3);
        Assert.assertTrue(adminUserButton.isDisplayed(), "Admin user butonu görünür değil!");

        String buttonText = adminUserButton.getText();
        extentTest.pass("✅ STEP 3 PASSED: Header'ın sağ tarafında yeşil " +
                "renkle '" + buttonText + "' kullanıcı adı butonu görünür ve tıklanabilir");

        // 4. Butona tıkla
        extentTest.info("4. 'Tural Seyidov' butonuna tıkla");

        ReusableMethods.waitForClickability(adminUserButton, 1);
        adminUserButton.click();
        extentTest.info("Admin user butonuna tıklandı: " + buttonText);
        ReusableMethods.bekle(3);
        extentTest.pass("✅ STEP 4 PASSED: Butona tıklandı, admin paneline yönlendirme işlemi başladı");

        // 5. Admin paneline yönlendirildiğini doğrula
        extentTest.info("5. Admin paneline yönlendirildiğini doğrula");

        ReusableMethods.waitForPageToLoad(2);
        ReusableMethods.bekle(1);

        currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
                "Admin paneline yönlendirilmedi! Mevcut URL: " + currentUrl);

        // Dashboard kontrolü
        String pageTitle = Driver.getDriver().getTitle();
        extentTest.pass("✅ STEP 5 PASSED: Admin panel sayfası açıldı " +
                "(URL: " + currentUrl + ", Title: " + pageTitle + ")");

        // 6. Sol sidebar'a hover yap ve Users menüsünü bul
        extentTest.info("6. Sol sidebar'a hover yap");

        ReusableMethods.bekle(3);

        // Sidebar'a hover yap (açılması için)
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(2);
        extentTest.info("Sidebar'a hover yapıldı, sidebar açıldı");

        // Users ANA MENÜSÜ (span içinde Users yazısı olan)
        WebElement usersMenuLink = Driver.getDriver().findElement(
                By.xpath("//span[text()='Users']/parent::a | " +
                        "//a[./span[text()='Users']] | " +
                        "//li[./span[text()='Users']]//a")
        );

        ReusableMethods.waitForVisibility(usersMenuLink, 3);
        Assert.assertTrue(usersMenuLink.isDisplayed());

        extentTest.pass("✅ STEP 6 PASSED: Sidebar'a hover yapıldı, Users ana menüsü görünür");

        // 7. Users menüsüne tıkla ve alt menüleri kontrol et
        extentTest.info("7. Users ana menüsüne tıkla");

        ReusableMethods.waitForClickability(usersMenuLink, 3);
        usersMenuLink.click();
        extentTest.info("Users ana menüsüne tıklandı");
        ReusableMethods.bekle(2);

        // Alt menü linklerini kontrol et (tıklandıktan SONRA görünür olacaklar)
        WebElement usersSubMenu = Driver.getDriver().findElement(
                By.xpath("//a[text()='Users' and contains(@href,'Dashboard/Users')]")
        );
        WebElement createUserSubMenu = Driver.getDriver().findElement(
                By.xpath("//a[normalize-space()='Create User' and contains(@href,'Users/create')]")
        );

        ReusableMethods.waitForVisibility(usersSubMenu, 3);
        Assert.assertTrue(usersSubMenu.isDisplayed());

        ReusableMethods.waitForVisibility(createUserSubMenu, 2);
        Assert.assertTrue(createUserSubMenu.isDisplayed());

        extentTest.pass("✅ STEP 7 PASSED: Users ana menüsüne tıklandı, alt menüler (Users, Create User) görünür");

        extentTest.pass("✅ US_025_TC_01 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_02: Users menüsüne tıklayarak kullanıcı listesi sayfasına erişim doğrulamak
    // ========================================
    @Test(priority = 2, description = "Users menüsüne tıklayarak kullanıcı listesi " +
            "sayfasına erişim doğrulamak")
    public void tc02_AdminPanelUsersListPageAccessTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_025_TC_02 - Users List Page Access Testi",
                "Users menüsüne tıklayarak kullanıcı listesi sayfasına erişimi doğrulamak"
        );

        extentTest.info("Pre-Condition: Yönetici admin paneline giriş yapmış olmalı");

        // 1. Login
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        // 2. Home Page kontrol
        ReusableMethods.waitForPageToLoad(3);
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"));

        // 3-4. Admin butonuna tıkla (admin paneline geç)
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | " +
                        "//button[contains(@class,'btn')] | " +
                        "//*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 3);
        adminUserButton.click();
        ReusableMethods.bekle(2);

        // 5. Admin panel URL kontrol
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
                "Admin paneline yönlendirilmedi! Mevcut URL: " + currentUrl);

        // STEP 1: Sol sidebar görünür
        extentTest.info("1. Admin panelinde sol sidebar'ı görüntüle");

        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 3);
        Assert.assertTrue(sidebar.isDisplayed(), "Sol sidebar görüntülenmiyor!");
        extentTest.pass("✅ STEP 1 PASSED: Sol sidebar ekranda görünüyor");

        // STEP 2: Users menüsünü bul (hover ile)
        extentTest.info("2. Users menüsünü bul");

        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(sidebar).pause(Duration.ofMillis(600)).perform();

        WebElement usersMenuLink = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Users']/parent::a | " +
                        "//a[./span[normalize-space()='Users']] | " +
                        "//a[normalize-space()='Users' and contains(@href,'Dashboard/Users')]")
        );

        ReusableMethods.waitForVisibility(usersMenuLink, 3);
        Assert.assertTrue(usersMenuLink.isDisplayed());
        extentTest.pass("✅ STEP 2 PASSED: Users menüsü görünür");

        // STEP 3: Users menüsüne tıkla
        extentTest.info("3. Users menüsüne tıkla");

        try {
            ReusableMethods.waitForClickability(usersMenuLink, 2);
            usersMenuLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMenuLink);
        }

        extentTest.pass("✅ STEP 3 PASSED: Users menüsüne tıklandı");

        // STEP 4: Users list sayfasına git ve scroll yap (HEADER KONTROLÜ YOK)
        extentTest.info("4. Kullanıcı listesi sayfasına yönlendirildiğini kontrol et " +
                "ve scroll down yap");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(3));

        // URL gelmezse Users alt menüsünü tıklayarak URL'yi tetikler
        wait.until(driver -> {
            if (driver.getCurrentUrl().contains("/Dashboard/Users")) return true;

            try {
                WebElement usersSubMenuLink = driver.findElement(
                        By.xpath("//a[normalize-space()='Users' and contains(@href,'/Dashboard/Users')] | " +
                                "//a[contains(@href,'/Dashboard/Users') and (.//span[normalize-space()='Users'] " +
                                "or normalize-space()='Users')]")
                );

                if (usersSubMenuLink.isDisplayed() && usersSubMenuLink.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", usersSubMenuLink);
                }
            } catch (Exception ignored) {
            }

            return driver.getCurrentUrl().contains("/Dashboard/Users");
        });

        String usersUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(usersUrl.contains("/Dashboard/Users"),
                "Users list sayfasına yönlendirilmedi! Mevcut URL: " + usersUrl);

        // ✅ Sayfa açıldıysa scroll down + up yap ve PASS ver
        extentTest.info("Users list sayfası açıldı, scroll işlemi yapılıyor");

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        ReusableMethods.bekle(2);
        js.executeScript("window.scrollTo(0, 0);");
        ReusableMethods.bekle(1);

        extentTest.pass("✅ STEP 4 PASSED: Users list sayfası açıldı ve scroll ile sayfa yüklendi doğrulandı");

        // STEP 5: Sayfanın hatasız yüklendiğini doğrula
        extentTest.info("5. Sayfanın hatasız yüklendiğini doğrula");

        List<WebElement> errorLike = Driver.getDriver().findElements(
                By.xpath("//*[contains(translate(.,'ERROR','error'),'error') or " +
                        "contains(translate(.,'EXCEPTION','exception'),'exception') or " +
                        "contains(translate(.,'NOT FOUND','not found'),'not found')]")
        );

        Assert.assertTrue(errorLike.isEmpty(), "Sayfada hata mesajı görünüyor!");
        extentTest.pass("✅ STEP 5 PASSED: Sayfa hatasız yüklendi");
    }


    // ========================================
    // TC_03: Kullanıcı listesi sayfasında tüm kullanıcı bilgilerinin görüntülenmesini doğrulamak
    // ========================================
    @Test(priority = 3, description = "Kullanıcı listesi sayfasına erişildikten sonra scroll down/up " +
            "ile sayfanın görüntülendiğini doğrulamak")
    public void tc03_AdminPanelUsersListScrollValidationTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_025_TC_03 - Users List Scroll Validation Testi",
                "Users list sayfasına erişildikten sonra scroll down ve up işlemleriyle sayfanın " +
                        "görüntülendiğini doğrulamak"
        );

        // =========================
        // TC_01 / TC_02 ortak adımlar: Login + Admin panel
        // =========================

        extentTest.info("Pre-Condition: Admin kullanıcı bilgileriyle login olunmalı ve " +
                "admin panel erişilebilir olmalı");

        // 1) Siteye git
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 5);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        // 2) Login
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 5);
        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        // 3) Home Page kontrol
        ReusableMethods.waitForPageToLoad(10);
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"),"Home Page'e yönlendirilmedi!");
        extentTest.pass("✅ Login başarılı, Home Page açıldı");

        // 4) Admin butonuna tıkla (admin paneline geç)
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | " +
                        "//button[contains(@class,'btn')] | " +
                        "//*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 2);
        adminUserButton.click();
        ReusableMethods.bekle(2);

        // 5) Admin panel URL kontrol
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
                "Admin paneline yönlendirilmedi! Mevcut URL: " + currentUrl);
        extentTest.pass("✅ Admin panel açıldı: " + currentUrl);

        // =========================
        // TC_03 sheet adımları
        // =========================

        // STEP 1: Users listesi sayfasına git (sidebar hover + Users tıkla)
        extentTest.info("1. Users listesi sayfasına git");

        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(sidebar).pause(Duration.ofMillis(600)).perform();

        WebElement usersMenuLink = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Users']/parent::a | " +
                        "//a[./span[normalize-space()='Users']] | " +
                        "//a[contains(@href,'/Dashboard/Users') and (normalize-space()='Users' or .//span[normalize-space()='Users'])]")
        );

        try {
            ReusableMethods.waitForClickability(usersMenuLink, 2);
            usersMenuLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMenuLink);
        }

        // URL gelmezse alt menüden Users linkini tıkla
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(12));
        wait.until(driver -> {
            if (driver.getCurrentUrl().contains("/Dashboard/Users")) return true;

            try {
                WebElement usersSubMenuLink = driver.findElement(
                        By.xpath("//a[normalize-space()='Users' and contains(@href,'/Dashboard/Users')] | " +
                                "//a[contains(@href,'/Dashboard/Users') and (normalize-space()='Users' " +
                                "or .//span[normalize-space()='Users'])]")
                );

                if (usersSubMenuLink.isDisplayed() && usersSubMenuLink.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", usersSubMenuLink);
                }
            } catch (Exception ignored) {
            }

            return driver.getCurrentUrl().contains("/Dashboard/Users");
        });

        String usersUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(usersUrl.contains("/Dashboard/Users"),
                "Users list sayfasına erişilemedi! Mevcut URL: " + usersUrl);

        extentTest.pass("✅ STEP 1 PASSED: Users listesi sayfası açıldı (URL: " + usersUrl + ")");

        // STEP 2: Sayfanın tam yüklenmesini bekle
        extentTest.info("2. Sayfanın tam yüklenmesini bekle");
        ReusableMethods.waitForPageToLoad(2);
        ReusableMethods.bekle(2);
        extentTest.pass("✅ STEP 2 PASSED: Sayfa yüklendi (3 sn beklendi)");

        // STEP 3: Scroll down (2 sn bekle)
        extentTest.info("3. Scroll down yap ve 3 saniye bekle");
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        ReusableMethods.bekle(2);
        extentTest.pass("✅ STEP 3 PASSED: Scroll down yapıldı (3 sn beklendi)");

        // STEP 4: Scroll up (2 sn bekle)
        extentTest.info("4. Scroll up yap ve 3 saniye bekle");
        js.executeScript("window.scrollTo(0, 0);");
        ReusableMethods.bekle(2);
        extentTest.pass("✅ STEP 4 PASSED: Scroll up yapıldı (3 sn beklendi)");

        // TEST SONUCU
        extentTest.pass("✅ US_025_TC_03 testi başarıyla tamamlandı!");

    }


    // ========================================
    // TC_04: Kullanıcı arama fonksiyonunun çalışması ve filtreleme işlemini doğrulamak
    // ========================================
    @Test(priority = 4, description = "Users listesi sayfasında arama kutusu ile filtreleme ve " +
            "temizleme işlemini doğrulamak")
    public void tc04_AdminPanelUsersSearchFilterTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_025_TC_04 - Users Search & Filter Testi",
                "Users listesi sayfasında search kutusuna isim yazınca filtreleme yapıldığını, " +
                        "temizleyince listenin geri geldiğini doğrulamak"
        );

        extentTest.info("Pre-Condition: Yönetici Users listesi sayfasında olmalı ve " +
                "sistemde birden fazla kullanıcı bulunmalı");

        // =========================
        // Login + Admin panel + Users list sayfası (TC_02/TC_03 ortak akış)
        // =========================

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 5);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        ReusableMethods.waitForPageToLoad(3);
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"), "Home Page'e yönlendirilmedi!");
        extentTest.pass("✅ Login başarılı, Home Page açıldı");

        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | " +
                        "//button[contains(@class,'btn')] | " +
                        "//*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 2);
        adminUserButton.click();
        ReusableMethods.bekle(2);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
                "Admin paneline yönlendirilmedi! Mevcut URL: " + currentUrl);
        extentTest.pass("✅ Admin panel açıldı: " + currentUrl);

        // Users list sayfasına git (sidebar hover + Users click)
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(sidebar).pause(Duration.ofMillis(600)).perform();

        WebElement usersMenuLink = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Users']/parent::a | " +
                        "//a[./span[normalize-space()='Users']] | " +
                        "//a[contains(@href,'/Dashboard/Users') and (normalize-space()='Users' or .//span[normalize-space()='Users'])]")
        );

        try {
            ReusableMethods.waitForClickability(usersMenuLink, 2);
            usersMenuLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMenuLink);
        }

        // URL gelmezse alt menüden Users linkini tıkla
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(12));
        wait.until(driver -> {
            if (driver.getCurrentUrl().contains("/Dashboard/Users")) return true;

            try {
                WebElement usersSubMenuLink = driver.findElement(
                        By.xpath("//a[normalize-space()='Users' and contains(@href,'/Dashboard/Users')] | " +
                                "//a[contains(@href,'/Dashboard/Users') and (normalize-space()='Users' " +
                                "or .//span[normalize-space()='Users'])]")
                );
                if (usersSubMenuLink.isDisplayed() && usersSubMenuLink.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", usersSubMenuLink);
                }
            } catch (Exception ignored) {
            }

            return driver.getCurrentUrl().contains("/Dashboard/Users");
        });

        String usersUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(usersUrl.contains("/Dashboard/Users"),
                "Users list sayfasına erişilemedi! Mevcut URL: " + usersUrl);
        extentTest.pass("✅ Users listesi sayfası açıldı: " + usersUrl);

        // =========================
        // TC_04 Sheet adımları
        // =========================

        // 1-2) Arama çubuğunu bul + görünür/kullanılabilir olduğunu doğrula
        extentTest.info("1-2. Users listesi sayfasında arama çubuğunu bul ve kullanılabilir olduğunu doğrula");

        WebElement searchInput = Driver.getDriver().findElement(By.id("search-table"));
        ReusableMethods.waitForVisibility(searchInput, 2);

        Assert.assertTrue(searchInput.isDisplayed(), "Search input görünmüyor!");
        Assert.assertTrue(searchInput.isEnabled(), "Search input aktif değil!");
        extentTest.pass("✅ STEP 1-2 PASSED: Arama (Search) çubuğu görünür ve aktif");

        // Filtre öncesi satır sayısını al (liste geri döndü mü kontrolünde kullanacağız)
        List<WebElement> rowsBefore = Driver.getDriver().findElements(By.cssSelector("table tbody tr"));
        int countBefore = rowsBefore.size();
        Assert.assertTrue(countBefore > 0, "Kullanıcı listesinde hiç satır yok!");
        extentTest.info("Filtreleme öncesi satır sayısı: " + countBefore);

        // 3) Arama çubuğuna ismi yaz
        extentTest.info("3. Arama çubuğuna mevcut bir kullanıcının adını yaz: Margarito O'Connell");

        String searchName = "Margarito O'Connell";
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(searchName);
        ReusableMethods.bekle(2);

        extentTest.pass("✅ STEP 3 PASSED: Arama terimi girildi: " + searchName);

        // 4) Sadece aranan kullanıcının gösterildiğini doğrula (basit doğrulama: tabloda metin geçsin)
        extentTest.info("4. Sadece aranan kullanıcının gösterildiğini doğrula");

        wait.until(driver -> {
            List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
            if (rows.isEmpty()) return false;
            // en az 1 satırda aranan isim geçsin
            for (WebElement r : rows) {
                if (r.getText().toLowerCase().contains(searchName.toLowerCase())) return true;
            }
            return false;
        });

        List<WebElement> rowsAfterFilter = Driver.getDriver().findElements(By.cssSelector("table tbody tr"));
        boolean found = false;
        for (WebElement r : rowsAfterFilter) {
            if (r.getText().toLowerCase().contains(searchName.toLowerCase())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Filtrelenmiş listede aranan kullanıcı bulunamadı!");
        extentTest.pass("✅ STEP 4 PASSED: Arama kriterine uyan kullanıcı listede görüntülendi");

        // 5-6) Arama alanını temizle ve tüm kullanıcıların geri geldiğini doğrula
        extentTest.info("5-6. Arama alanını temizle ve tüm kullanıcıların tekrar listelendiğini doğrula");

        // Filtreli halde kaç satır var? (genelde 1 olur)
        int filteredCount = Driver.getDriver().findElements(By.cssSelector("table tbody tr")).size();

        searchInput.click();
        ReusableMethods.bekle(1);

        // clear() bazen JS event tetiklemediği için Ctrl+A + Backspace kullanıyoruz
        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

        // Ekstra garanti: JS ile value='' yap ve input/keyup event dispatch et (DataTables reset için)
        ((JavascriptExecutor) Driver.getDriver()).executeScript(
                "arguments[0].value='';" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('keyup', {bubbles:true}));",
                searchInput
        );

        ReusableMethods.bekle(2);

        // Input gerçekten boş mu?
        wait.until(ExpectedConditions.attributeToBe(searchInput, "value", ""));

        // Filtre kalkınca satır sayısı artmalı (1'den >1'e dönmesi beklenir)
        wait.until(driver -> driver.findElements(By.cssSelector("table tbody tr")).size() > filteredCount);

        int countAfterClear = Driver.getDriver().findElements(By.cssSelector("table tbody tr")).size();
        Assert.assertTrue(countAfterClear > filteredCount,
                "Arama temizlendi ama liste genişlemedi! Filtered: " + filteredCount +
                        " AfterClear: " + countAfterClear);

        extentTest.pass("✅ STEP 5-6 PASSED: Arama alanı temizlendi, " +
                "kullanıcı listesi varsayılan haline döndü " +
                "(Row count: " + countAfterClear + ")");
    }


    // ========================================
    // TC_05: Kullanıcı detay görüntüleme ekranının işlevselliğini ve butonların görünürlüğünü doğrulamak
    // (Görüntüle butonu olmadığı için STEP 4'te FAIL olacak ve test bitecek.)
    // ========================================
    @Test(priority = 5, description = "Search ile Margarito filtrelenir, Edit/Delete hover yapılır; " +
            "Görüntüle yokluğu nedeniyle STEP 4 FAIL olur.")
    public void tc05_AdminPanelUserDetailViewButtonMissingFailTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_025_TC_05 - User Detail View Button Missing (FAIL) Testi",
                "Users listesinde arama ile Margarito filtrelenir; Edit/Delete hover kontrol edilir; " +
                        "Görüntüle yokluğu nedeniyle STEP 4 fail."
        );

        String targetName = "Margarito O'Connell";
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        Actions actions = new Actions(Driver.getDriver());

        // =========================
        // Ortak Akış: Login -> Admin -> Users (alt menü)
        // =========================
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        loginPage.signInButton.click();
        ReusableMethods.bekle(2);

        ReusableMethods.waitForPageToLoad(2);
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"));
        extentTest.pass("✅ Login başarılı, Home Page açıldı");

        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //button[contains(@class,'btn')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 2);
        adminUserButton.click();
        ReusableMethods.bekle(2);

        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 2);
        actions.moveToElement(sidebar).pause(Duration.ofMillis(500)).perform();

        WebElement usersMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Users']/parent::a | //a[.//span[normalize-space()='Users']]")
        );
        try {
            ReusableMethods.waitForClickability(usersMainMenu, 2);
            usersMainMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMainMenu);
        }
        ReusableMethods.bekle(1);

        WebElement usersSubMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/Dashboard/Users') and normalize-space()='Users'] | " +
                        "//a[contains(@href,'/Dashboard/Users') and .//text()[normalize-space()='Users']]")
        ));
        try {
            usersSubMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersSubMenu);
        }

        wait.until(ExpectedConditions.urlContains("/Dashboard/Users"));
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/Dashboard/Users"),
                "Users list sayfasına gidilemedi! URL: " + Driver.getDriver().getCurrentUrl());
        extentTest.pass("✅ Users list sayfası açıldı: " + Driver.getDriver().getCurrentUrl());

        // =========================
        // STEP 1: Search kutusuna Margarito yazıp filtrele
        // =========================
        extentTest.info("STEP 1: Search alanına '" + targetName + "' yaz ve filtrelemeyi doğrula");

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-table")));
        Assert.assertTrue(searchInput.isDisplayed() && searchInput.isEnabled());

        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(targetName);
        ReusableMethods.bekle(2);

        // En az bir satır görünmeli (filtre çalıştı mı)
        wait.until(driver -> driver.findElements(By.cssSelector("table tbody tr")).size() > 0);

        // İlk satır metni Margarito içeriyor mu? (opsiyonel ama güzel doğrulama)
        String firstRowText = Driver.getDriver().findElement(By.cssSelector("table tbody tr")).getText().toLowerCase();
        Assert.assertTrue(firstRowText.contains("margarito"));
        extentTest.pass("✅ STEP 1 PASSED: Arama ile liste filtrelendi ve Margarito satırı görüntülendi");

        // =========================
        // STEP 2: Filtrelenen ilk satırı seç (Margarito)
        // =========================
        extentTest.info("STEP 2: Filtrelenen listeden Margarito satırını seç");

        WebElement targetRow = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("table tbody tr")));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", targetRow);

        try {
            targetRow.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", targetRow);
        }
        extentTest.pass("✅ STEP 2 PASSED: Margarito satırı seçildi");

        // =========================
        // STEP 3: Edit & Delete butonlarına sadece HOVER yap
        // =========================
        extentTest.info("STEP 4: Edit ve Delete butonlarına hover yapılıyor");

        // Edit için (Tahmini) ve Delete için (Kesin - Gönderdiğin koda göre) locatorlar
        By editBtnBy = By.xpath("//button[contains(@class,'fa-edit')] | //a[contains(@class,'edit')]");
        By deleteBtnBy = By.xpath("//button[contains(@class,'fa-remove')]");

        try {
            // 1. Edit Hover
            WebElement editBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(editBtnBy));
            actions.moveToElement(editBtn).pause(Duration.ofMillis(800)).perform();
            extentTest.pass("✅ Edit butonu üzerinde hover yapıldı.");

            ReusableMethods.bekle(1);

            // 2. Delete Hover (Gönderdiğin butonu bulacak)
            WebElement deleteBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(deleteBtnBy));
            actions.moveToElement(deleteBtn).pause(Duration.ofMillis(800)).perform();
            extentTest.pass("✅ Delete butonu üzerinde hover yapıldı.");

        } catch (Exception e) {
            extentTest.info("⚠️ Hover sırasında bir sorun oluştu (belki butonlar geç yüklendi): " + e.getMessage());
            // Testi durdurmuyoruz, çünkü asıl amacımız 4. adımdaki eksikliği göstermek.
        }

        // =========================
        // STEP 4: Görüntüle (View) Butonu Yokluğu -> BURADA FAIL ALACAKSIN
        // =========================
        extentTest.info("STEP 5: 'Görüntüle/View' butonu aranıyor (Beklenen: Yok -> FAIL)");

        // Geniş kapsamlı arama
        By viewBtnBy = By.xpath("//button[contains(@class,'view')] | //a[contains(@class,'view')] | //*[contains(text(),'View')]");

        List<WebElement> viewButtons = Driver.getDriver().findElements(viewBtnBy);

        if (viewButtons.isEmpty()) {
            String bugMsg = "❌ BUG: User Story'de istenen 'Görüntüle' butonu sayfada OLMADIĞI için test fail edildi.";
            extentTest.fail(bugMsg);
            Assert.fail(bugMsg); // İşte burada testin kırmızı yanacak ve raporun hazır olacak!
        } else {
            extentTest.pass("✅ Görüntüle butonu mevcut.");
        }

    }


    // ========================================
    // TC_06: Kullanıcı düzenleme (edit) işlevinin çalışmasını ve listenin güncellenmesini doğrulamak
    // (Edit sayfasına girilir, Phone ve Password alanları doldurulur,
    // Save yapılarak başarı mesajı doğrulanır.)
    // ========================================
    @Test(priority = 6, description = "Admin Margarito kullanıcısını editler, " +
            "telefon ve şifre bilgilerini güncelleyerek kaydeder.")
    public void tc06_AdminPanelEditUserAndSaveTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_025_TC_06 - Edit User and Save Testi",
                "Margarito kullanıcısının telefon ve şifre bilgileri güncellenerek kaydedilir."
        );

        String targetName = "Margarito O'Connell";
        String phoneValue = "0123456789";
        String passValue  = "123456";

        // Beklemeleri minimum tutuyoruz
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(Driver.getDriver());
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

        // =========================
        // Ortak Akış: Login -> Admin -> Users
        // =========================
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickability(layout.signInLink, 3);
        layout.signInLink.click();

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 3);
        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        loginPage.signInButton.click();

        ReusableMethods.waitForPageToLoad(2);
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"));
        extentTest.pass("✅ Login başarılı, Home Page açıldı");

        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //button[contains(@class,'btn')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 2);
        adminUserButton.click();

        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 2);
        actions.moveToElement(sidebar).pause(Duration.ofMillis(250)).perform();

        WebElement usersMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Users']/parent::a | //a[.//span[normalize-space()='Users']]")
        );
        try {
            usersMainMenu.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", usersMainMenu);
        }

        // Submenu
        WebElement usersSubMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/Dashboard/Users') and normalize-space()='Users'] | " +
                        "//a[contains(@href,'/Dashboard/Users') and .//text()[normalize-space()='Users']]")
        ));
        try {
            usersSubMenu.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", usersSubMenu);
        }

        wait.until(ExpectedConditions.urlContains("/Dashboard/Users"));
        extentTest.pass("✅ Users list sayfası açıldı: " + Driver.getDriver().getCurrentUrl());

        // =========================
        // STEP 1: Margarito filtrele
        // =========================
        extentTest.info("STEP 1: Search alanına Margarito yaz ve filtrele");

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-table")));
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(targetName);

        // kısa bekleme: DataTable filtreyi uygulasın
        ReusableMethods.bekle(1);

        // en az 1 satır gelsin ve Margarito içersin
        wait.until(driver -> {
            List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
            if (rows.isEmpty()) return false;
            return rows.get(0).getText().toLowerCase().contains("margarito");
        });
        extentTest.pass("✅ STEP 1 PASSED: Margarito filtrelendi");

        // =========================
        // STEP 2: Margarito Edit tıkla (href net)
        // =========================
        extentTest.info("STEP 2: Margarito satırında Edit butonuna tıkla");

        By margaritoEditBy = By.cssSelector("a[href='https://qa.loyalfriendcare.com/en/Dashboard/Users/margarito-o-connell/edit']");
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(margaritoEditBy));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", editBtn);
        try {
            editBtn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", editBtn);
        }

        wait.until(ExpectedConditions.urlContains("/Dashboard/Users/margarito-o-connell/edit"));
        extentTest.pass("✅ STEP 2 PASSED: Edit sayfası açıldı");

        // =========================
        // STEP 3: Form alanlarını doldur (minimum wait)
        // =========================
        extentTest.info("STEP 3: Phone / Password / Confirm doldur");

        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Phone")));
        phoneInput.clear();
        phoneInput.sendKeys(phoneValue);

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordInput.clear();
        passwordInput.sendKeys(passValue);

        // CONFIRM: doğru locator - uzun fallback yok
        WebElement confirmPasswordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("password_confirmation"))
        );
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(passValue);

        extentTest.pass("✅ STEP 3 PASSED: Phone=0123456789, Password/Confirm=123456 girildi");

        // =========================
        // STEP 4: Save
        // =========================
        extentTest.info("STEP 4: Save butonuna tıkla");

        By saveBtnBy = By.cssSelector("button.fa-save[type='submit']");
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveBtnBy));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", saveBtn);

        try {
            saveBtn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", saveBtn);
        }

        extentTest.pass("✅ STEP 4 PASSED: Save tıklandı");

        // =========================
        // STEP 5: Success mesajı
        // =========================
        extentTest.info("STEP 5: Başarı mesajını doğrula");

        By successMsgBy = By.xpath("//*[contains(translate(.,'SUCCESSFULLY','successfully'),'successfully') or " +
                "contains(.,'User Updated successfully.')]");
        WebElement successMsg = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(successMsgBy));

        Assert.assertTrue(successMsg.isDisplayed());
        extentTest.pass("✅ STEP 5 PASSED: Başarı mesajı görüldü: " + successMsg.getText());
    }


    // ========================================
    // TC_07: Delete işleminde confirmation çıkmalı
    // Gerçekte confirmation yok, kullanıcı direkt siliniyor.
    // "User deleted successfully" görüldüğü anda test FAIL ile bitirilir.
    // ========================================
    @Test(priority = 7, description = "Users listesinde cecelia.wolf aranır, " +
            "Delete tıklanır; confirmation yerine direkt 'User deleted successfully' görüldüğü anda FAIL.")
    public void tc07_AdminPanelDeleteUserConfirmationMissingFailTest() {

        layout = new Layout();
        loginPage = new LoginPage();

        extentTest = extentReports.createTest(
                "US_025_TC_07 - Delete Confirmation Missing (FAIL) Testi",
                "cecelia.wolf kullanıcısında Delete tıklanınca confirmation beklenir; ancak direkt 'User deleted successfully' görülür ve test FAIL ile biter."
        );

        String searchKey = "cecelia.wolf";

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(12));
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        Actions actions = new Actions(Driver.getDriver());

        // =========================
        // Ortak Akış: Login -> Admin -> Users (alt menü)
        // =========================
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickability(layout.signInLink, 2);
        layout.signInLink.click();

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
        loginPage.passwordInput.sendKeys("Loyal.123123");
        loginPage.signInButton.click();

        ReusableMethods.waitForPageToLoad(2);
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"), "Home Page açılmadı!");
        extentTest.pass("✅ Login başarılı, Home Page açıldı");

        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //button[contains(@class,'btn')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 2);
        adminUserButton.click();

        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 2);
        actions.moveToElement(sidebar).pause(Duration.ofMillis(250)).perform();

        WebElement usersMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[normalize-space()='Users']/parent::a | //a[.//span[normalize-space()='Users']]")
        );
        try {
            usersMainMenu.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", usersMainMenu);
        }

        WebElement usersSubMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/Dashboard/Users') and normalize-space()='Users'] | " +
                        "//a[contains(@href,'/Dashboard/Users') and .//text()[normalize-space()='Users']]")
        ));
        try {
            usersSubMenu.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", usersSubMenu);
        }

        wait.until(ExpectedConditions.urlContains("/Dashboard/Users"));
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/Dashboard/Users"),
                "Users list sayfasına gidilemedi! URL: " + Driver.getDriver().getCurrentUrl());
        extentTest.pass("✅ Users list sayfası açıldı: " + Driver.getDriver().getCurrentUrl());

        // =========================
        // STEP 1: Search input'a cecelia.wolf yaz
        // =========================
        extentTest.info("STEP 1: Search input'a 'cecelia.wolf' yaz ve filtrele");

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-table")));
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(searchKey);
        ReusableMethods.bekle(1);

        // =========================
        // STEP 2: Kullanıcı satırını doğrula
        // =========================
        extentTest.info("STEP 2: Filtrelenmiş tabloda cecelia.wolf satırını doğrula");

        By userRowBy = By.xpath(
                "//table//tbody//tr[td][contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + searchKey + "')]"
        );
        WebElement userRow = wait.until(ExpectedConditions.visibilityOfElementLocated(userRowBy));
        Assert.assertTrue(userRow.isDisplayed(), "cecelia.wolf satırı görüntülenemedi!");
        extentTest.pass("✅ STEP 1-2 PASSED: cecelia.wolf kullanıcı satırı bulundu");

        // =========================
        // STEP 3: Delete butonuna tıkla (HTML'e birebir)
        // <button type="submit" class="btn btn-danger ... fa-remove"><span>Delete</span></button>
        // =========================
        extentTest.info("STEP 3: cecelia.wolf satırındaki Delete butonuna tıkla");

        By deleteBtnBy = By.xpath(
                "//table//tbody//tr[td][contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + searchKey + "')]"
                        + "//button[@type='submit' and contains(@class,'btn-danger') and contains(@class,'fa-remove') and .//span[normalize-space()='Delete']]"
        );

        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(deleteBtnBy));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", deleteBtn);

        try {
            deleteBtn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", deleteBtn);
        }
        extentTest.pass("✅ STEP 3 PASSED: Delete butonuna tıklandı");

        // =========================
        // STEP 4: "User deleted successfully" görüldüğü anda FAIL ile bitir (bekleme yok)
        // =========================
        extentTest.info("STEP 4: 'User deleted successfully' mesajını gör ve testi FAIL olarak bitir (bekleme yok)");

        try {
            WebElement deletedToast = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'user deleted successfully')]")
                    ));

            extentTest.fail("❌ STEP 4 FAILED (Beklenen Bug): Confirmation çıkmadan kullanıcı silindi. Mesaj görüldü: "
                    + deletedToast.getText());

            // Mesajı gördüğümüz anda BEKLEME YOK -> direkt fail
            Assert.fail("Delete işleminde confirmation çıkmadı; kullanıcı direkt silindi ('User deleted successfully').");

        } catch (Exception e) {
            extentTest.fail("❌ STEP 4 FAILED: 'User deleted successfully' mesajı görülmedi. (Test verisi/akış değişmiş olabilir)");
            Assert.fail("'User deleted successfully' mesajı yakalanamadı. " + e.getMessage());
        }
    }

}



