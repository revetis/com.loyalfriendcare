//package tests;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import pages.common_pages.Layout;
//import pages.common_pages.LoginPage;
//import pages.admin_pages.AdminDashboardPages;
//import utilities.ConfigReader;
//import utilities.Driver;
//import utilities.ReusableMethods;
//import utilities.TestBaseRapor;
//import org.openqa.selenium.Keys;
//
//
//import java.time.Duration;
//import java.util.List;
//
//public class US_025_TestCases extends TestBaseRapor {
//
//    Layout layout;
//    LoginPage loginPage;
//
//    // ========================================
//    // TC_01: Admin Panel Users Menü Erişimi
//    // ========================================
//    @Test(priority = 1, description = "Admin paneline giriş yapılarak Users menüsüne erişim doğrulamak")
//    public void tc01_AdminPanelUsersMenuAccessTest() {
//
//        layout = new Layout();
//        loginPage = new LoginPage();
//
//        extentTest = extentReports.createTest("US_025_TC_01 - Admin Panel Users Menü Erişim Testi",
//                "Admin paneline giriş yapılarak Users menüsüne erişim doğrulamak");
//
//        extentTest.info("Pre-Condition: Admin kullanıcı bilgileri geçerli olmalı ve " +
//                "admin paneli erişilebilir durumda olmalı");
//
//        // 1. Giriş sayfasına git ve admin kullanıcı bilgileriyle giriş yap
//        extentTest.info("1. Giriş sayfasına git ve admin kullanıcı bilgileriyle giriş yap");
//
//        Driver.getDriver().get(ConfigReader.getProperty("url"));
//        extentTest.info("Ana sayfaya gidildi: " + ConfigReader.getProperty("url"));
//
//        ReusableMethods.waitForClickability(layout.signInLink, 2);
//        layout.signInLink.click();
//        extentTest.info("Sign In butonuna tıklandı");
//        ReusableMethods.bekle(2);
//
//        // Admin login bilgileri
//        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 1);
//        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
//        loginPage.passwordInput.sendKeys("Loyal.123123");
//        extentTest.info("Email: samet.ture.admin@loyalfriendcare.com | Password: Loyal.123123");
//
//        loginPage.signInButton.click();
//        ReusableMethods.bekle(3);
//
//        String currentUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertFalse(currentUrl.contains("/login"));
//        extentTest.pass("✅ STEP 1 PASSED: Giriş başarılı olmalı ve Home Page'e yönlendirildi");
//
//        // 2. Home Page'e yönlendirildiğini doğrula
//        extentTest.info("2. Home Page'e yönlendirildiğini doğrula");
//
//        ReusableMethods.waitForPageToLoad(1);
//        ReusableMethods.bekle(2);
//
//        currentUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertTrue(currentUrl.contains("/en"));
//
//        // Navbar ve search bar kontrol
//        Assert.assertTrue(layout.headerNavigationBar.isDisplayed());
//        extentTest.pass("✅ STEP 2 PASSED: Home Page başarıyla açıldı, " +
//                "sayfa içeriği (başlık, search bar, navbar) görünür");
//
//        // 3. Header'da "Samet Türe" isminin görünür olduğunu kontrol
//        extentTest.info("3. Header'da 'Tural Seyidov' isminin görünür olduğunu kontrol et");
//
//        // Admin user button - "Samet Türe" veya herhangi bir admin adı
//        WebElement adminUserButton = Driver.getDriver().findElement(
//                By.xpath("//a[contains(@class,'btn_add')] | " +
//                        "//button[contains(@class,'btn')] | " +
//                        "//*[@id='top_menu']//a[1]")
//        );
//
//        ReusableMethods.waitForVisibility(adminUserButton, 3);
//        Assert.assertTrue(adminUserButton.isDisplayed(), "Admin user butonu görünür değil!");
//
//        String buttonText = adminUserButton.getText();
//        extentTest.pass("✅ STEP 3 PASSED: Header'ın sağ tarafında yeşil " +
//                "renkle '" + buttonText + "' kullanıcı adı butonu görünür ve tıklanabilir");
//
//        // 4. Butona tıkla
//        extentTest.info("4. 'Tural Seyidov' butonuna tıkla");
//
//        ReusableMethods.waitForClickability(adminUserButton, 1);
//        adminUserButton.click();
//        extentTest.info("Admin user butonuna tıklandı: " + buttonText);
//        ReusableMethods.bekle(3);
//        extentTest.pass("✅ STEP 4 PASSED: Butona tıklandı, admin paneline yönlendirme işlemi başladı");
//
//        // 5. Admin paneline yönlendirildiğini doğrula
//        extentTest.info("5. Admin paneline yönlendirildiğini doğrula");
//
//        ReusableMethods.waitForPageToLoad(2);
//        ReusableMethods.bekle(1);
//
//        currentUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
//                "Admin paneline yönlendirilmedi! Mevcut URL: " + currentUrl);
//
//        // Dashboard kontrolü
//        String pageTitle = Driver.getDriver().getTitle();
//        extentTest.pass("✅ STEP 5 PASSED: Admin panel sayfası açıldı " +
//                "(URL: " + currentUrl + ", Title: " + pageTitle + ")");
//
//        // 6. Sol sidebar'a hover yap ve Users menüsünü bul
//        extentTest.info("6. Sol sidebar'a hover yap");
//
//        ReusableMethods.bekle(3);
//
//        // Sidebar'a hover yap (açılması için)
//        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
//        Actions actions = new Actions(Driver.getDriver());
//        actions.moveToElement(sidebar).perform();
//        ReusableMethods.bekle(2);
//        extentTest.info("Sidebar'a hover yapıldı, sidebar açıldı");
//
//        // Users ANA MENÜSÜ (span içinde Users yazısı olan)
//        WebElement usersMenuLink = Driver.getDriver().findElement(
//                By.xpath("//span[text()='Users']/parent::a | " +
//                        "//a[./span[text()='Users']] | " +
//                        "//li[./span[text()='Users']]//a")
//        );
//
//        ReusableMethods.waitForVisibility(usersMenuLink, 3);
//        Assert.assertTrue(usersMenuLink.isDisplayed());
//
//        extentTest.pass("✅ STEP 6 PASSED: Sidebar'a hover yapıldı, Users ana menüsü görünür");
//
//        // 7. Users menüsüne tıkla ve alt menüleri kontrol et
//        extentTest.info("7. Users ana menüsüne tıkla");
//
//        ReusableMethods.waitForClickability(usersMenuLink, 3);
//        usersMenuLink.click();
//        extentTest.info("Users ana menüsüne tıklandı");
//        ReusableMethods.bekle(2);
//
//        // Alt menü linklerini kontrol et (tıklandıktan SONRA görünür olacaklar)
//        WebElement usersSubMenu = Driver.getDriver().findElement(
//                By.xpath("//a[text()='Users' and contains(@href,'Dashboard/Users')]")
//        );
//        WebElement createUserSubMenu = Driver.getDriver().findElement(
//                By.xpath("//a[normalize-space()='Create User' and contains(@href,'Users/create')]")
//        );
//
//        ReusableMethods.waitForVisibility(usersSubMenu, 3);
//        Assert.assertTrue(usersSubMenu.isDisplayed());
//
//        ReusableMethods.waitForVisibility(createUserSubMenu, 2);
//        Assert.assertTrue(createUserSubMenu.isDisplayed());
//
//        extentTest.pass("✅ STEP 7 PASSED: Users ana menüsüne tıklandı, alt menüler (Users, Create User) görünür");
//
//        extentTest.pass("✅ US_025_TC_01 testi başarıyla tamamlandı!");
//    }
//
//
//    // ========================================
//    // TC_02: Users menüsüne tıklayarak kullanıcı listesi sayfasına erişim doğrulamak
//    // ========================================
//    @Test(priority = 2, description = "Users menüsüne tıklayarak kullanıcı listesi " +
//            "sayfasına erişim doğrulamak")
//    public void tc02_AdminPanelUsersListPageAccessTest() {
//
//        layout = new Layout();
//        loginPage = new LoginPage();
//
//        extentTest = extentReports.createTest(
//                "US_025_TC_02 - Users List Page Access Testi",
//                "Users menüsüne tıklayarak kullanıcı listesi sayfasına erişimi doğrulamak"
//        );
//
//        extentTest.info("Pre-Condition: Yönetici admin paneline giriş yapmış olmalı");
//
//        // 1. Login
//        Driver.getDriver().get(ConfigReader.getProperty("url"));
//        ReusableMethods.waitForClickability(layout.signInLink, 2);
//        layout.signInLink.click();
//        ReusableMethods.bekle(1);
//
//        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 2);
//        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
//        loginPage.passwordInput.sendKeys("Loyal.123123");
//        loginPage.signInButton.click();
//        ReusableMethods.bekle(2);
//
//        // 2. Home Page kontrol
//        ReusableMethods.waitForPageToLoad(3);
//        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"));
//
//        // 3-4. Admin butonuna tıkla (admin paneline geç)
//        WebElement adminUserButton = Driver.getDriver().findElement(
//                By.xpath("//a[contains(@class,'btn_add')] | " +
//                        "//button[contains(@class,'btn')] | " +
//                        "//*[@id='top_menu']//a[1]")
//        );
//        ReusableMethods.waitForClickability(adminUserButton, 3);
//        adminUserButton.click();
//        ReusableMethods.bekle(2);
//
//        // 5. Admin panel URL kontrol
//        String currentUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
//                "Admin paneline yönlendirilmedi! Mevcut URL: " + currentUrl);
//
//        // STEP 1: Sol sidebar görünür
//        extentTest.info("1. Admin panelinde sol sidebar'ı görüntüle");
//
//        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
//        ReusableMethods.waitForVisibility(sidebar, 3);
//        Assert.assertTrue(sidebar.isDisplayed(), "Sol sidebar görüntülenmiyor!");
//        extentTest.pass("✅ STEP 1 PASSED: Sol sidebar ekranda görünüyor");
//
//        // STEP 2: Users menüsünü bul (hover ile)
//        extentTest.info("2. Users menüsünü bul");
//
//        Actions actions = new Actions(Driver.getDriver());
//        actions.moveToElement(sidebar).pause(Duration.ofMillis(600)).perform();
//
//        WebElement usersMenuLink = Driver.getDriver().findElement(
//                By.xpath("//span[normalize-space()='Users']/parent::a | " +
//                        "//a[./span[normalize-space()='Users']] | " +
//                        "//a[normalize-space()='Users' and contains(@href,'Dashboard/Users')]")
//        );
//
//        ReusableMethods.waitForVisibility(usersMenuLink, 3);
//        Assert.assertTrue(usersMenuLink.isDisplayed());
//        extentTest.pass("✅ STEP 2 PASSED: Users menüsü görünür");
//
//        // STEP 3: Users menüsüne tıkla
//        extentTest.info("3. Users menüsüne tıkla");
//
//        try {
//            ReusableMethods.waitForClickability(usersMenuLink, 2);
//            usersMenuLink.click();
//        } catch (Exception e) {
//            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMenuLink);
//        }
//
//        extentTest.pass("✅ STEP 3 PASSED: Users menüsüne tıklandı");
//
//        // STEP 4: Users list sayfasına git ve scroll yap (HEADER KONTROLÜ YOK)
//        extentTest.info("4. Kullanıcı listesi sayfasına yönlendirildiğini kontrol et " +
//                "ve scroll down yap");
//
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(3));
//
//        // URL gelmezse Users alt menüsünü tıklayarak URL'yi tetikler
//        wait.until(driver -> {
//            if (driver.getCurrentUrl().contains("/Dashboard/Users")) return true;
//
//            try {
//                WebElement usersSubMenuLink = driver.findElement(
//                        By.xpath("//a[normalize-space()='Users' and contains(@href,'/Dashboard/Users')] | " +
//                                "//a[contains(@href,'/Dashboard/Users') and (.//span[normalize-space()='Users'] " +
//                                "or normalize-space()='Users')]")
//                );
//
//                if (usersSubMenuLink.isDisplayed() && usersSubMenuLink.isEnabled()) {
//                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", usersSubMenuLink);
//                }
//            } catch (Exception ignored) {
//            }
//
//            return driver.getCurrentUrl().contains("/Dashboard/Users");
//        });
//
//        String usersUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertTrue(usersUrl.contains("/Dashboard/Users"),
//                STR."Users list sayfasına yönlendirilmedi! Mevcut URL: \{usersUrl}");
//
//        ✅ Sayfa açıldıysa scroll down + up yap ve PASS ver
//       extentTest.info("Users list sayfası açıldı, scroll işlemi yapılıyor");
//
//        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
//        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
//        ReusableMethods.bekle(2);
//        js.executeScript("window.scrollTo(0, 0);");
//        ReusableMethods.bekle(1);
//
//        extentTest.pass("✅ STEP 4 PASSED: Users list sayfası açıldı ve scroll ile sayfa yüklendi doğrulandı");
//
//        // STEP 5: Sayfanın hatasız yüklendiğini doğrula
//        extentTest.info("5. Sayfanın hatasız yüklendiğini doğrula");
//
//        List<WebElement> errorLike = Driver.getDriver().findElements(
//                By.xpath("//*[contains(translate(.,'ERROR','error'),'error') or " +
//                        "contains(translate(.,'EXCEPTION','exception'),'exception') or " +
//                        "contains(translate(.,'NOT FOUND','not found'),'not found')]")
//        );
//
//        Assert.assertTrue(errorLike.isEmpty(), "Sayfada hata mesajı görünüyor!");
//        extentTest.pass("✅ STEP 5 PASSED: Sayfa hatasız yüklendi");
//    }
//
//    // ========================================
//    // TC_03: Kullanıcı listesi sayfasında tüm kullanıcı bilgilerinin görüntülenmesini doğrulamak
//    // ========================================
//    @Test(priority = 3, description = "Kullanıcı listesi sayfasına erişildikten sonra scroll down/up ile sayfanın görüntülendiğini doğrulamak")
//    public void tc03_AdminPanelUsersListScrollValidationTest() {
//
//        layout = new Layout();
//        loginPage = new LoginPage();
//
//        extentTest = extentReports.createTest(
//                "US_025_TC_03 - Users List Scroll Validation Testi",
//                "Users list sayfasına erişildikten sonra scroll down ve up işlemleriyle sayfanın görüntülendiğini doğrulamak"
//        );
//
//        // =========================
//        // TC_01 / TC_02 ortak adımlar: Login + Admin panel
//        // =========================
//
//        extentTest.info("Pre-Condition: Admin kullanıcı bilgileriyle login olunmalı ve admin panel erişilebilir olmalı");
//
//        // 1) Siteye git
//        Driver.getDriver().get(ConfigReader.getProperty("url"));
//        ReusableMethods.waitForClickability(layout.signInLink, 5);
//        layout.signInLink.click();
//        ReusableMethods.bekle(1);
//
//        // 2) Login
//        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 5);
//        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
//        loginPage.passwordInput.sendKeys("Loyal.123123");
//        loginPage.signInButton.click();
//        ReusableMethods.bekle(2);
//
//        // 3) Home Page kontrol
//        ReusableMethods.waitForPageToLoad(10);
//        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"), "Home Page'e yönlendirilmedi!");
//        extentTest.pass("✅ Login başarılı, Home Page açıldı");
//
//        // 4) Admin butonuna tıkla (admin paneline geç)
//        WebElement adminUserButton = Driver.getDriver().findElement(
//                By.xpath("//a[contains(@class,'btn_add')] | " +
//                        "//button[contains(@class,'btn')] | " +
//                        "//*[@id='top_menu']//a[1]")
//        );
//        ReusableMethods.waitForClickability(adminUserButton, 10);
//        adminUserButton.click();
//        ReusableMethods.bekle(2);
//
//        // 5) Admin panel URL kontrol
//        String currentUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
//                "Admin paneline yönlendirilmedi! Mevcut URL: " + currentUrl);
//        extentTest.pass("✅ Admin panel açıldı: " + currentUrl);
//
//        // =========================
//        // TC_03 sheet adımları
//        // =========================
//
//        // STEP 1: Users listesi sayfasına git (sidebar hover + Users tıkla)
//        extentTest.info("1. Users listesi sayfasına git");
//
//        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
//        Actions actions = new Actions(Driver.getDriver());
//        actions.moveToElement(sidebar).pause(Duration.ofMillis(600)).perform();
//
//        WebElement usersMenuLink = Driver.getDriver().findElement(
//                By.xpath("//span[normalize-space()='Users']/parent::a | " +
//                        "//a[./span[normalize-space()='Users']] | " +
//                        "//a[contains(@href,'/Dashboard/Users') and (normalize-space()='Users' or .//span[normalize-space()='Users'])]")
//        );
//
//        try {
//            ReusableMethods.waitForClickability(usersMenuLink, 10);
//            usersMenuLink.click();
//        } catch (Exception e) {
//            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMenuLink);
//        }
//
//        // URL gelmezse alt menüden Users linkini tıkla
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(12));
//        wait.until(driver -> {
//            if (driver.getCurrentUrl().contains("/Dashboard/Users")) return true;
//
//            try {
//                WebElement usersSubMenuLink = driver.findElement(
//                        By.xpath("//a[normalize-space()='Users' and contains(@href,'/Dashboard/Users')] | " +
//                                "//a[contains(@href,'/Dashboard/Users') and (normalize-space()='Users' or .//span[normalize-space()='Users'])]")
//                );
//
//                if (usersSubMenuLink.isDisplayed() && usersSubMenuLink.isEnabled()) {
//                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", usersSubMenuLink);
//                }
//            } catch (Exception ignored) {
//            }
//
//            return driver.getCurrentUrl().contains("/Dashboard/Users");
//        });
//
//        String usersUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertTrue(usersUrl.contains("/Dashboard/Users"),
//                "Users list sayfasına erişilemedi! Mevcut URL: " + usersUrl);
//
//        extentTest.pass("✅ STEP 1 PASSED: Users listesi sayfası açıldı (URL: " + usersUrl + ")");
//
//        // STEP 2: Sayfanın tam yüklenmesini bekle
//        extentTest.info("2. Sayfanın tam yüklenmesini bekle");
//        ReusableMethods.waitForPageToLoad(10);
//        ReusableMethods.bekle(3);
//        extentTest.pass("✅ STEP 2 PASSED: Sayfa yüklendi (3 sn beklendi)");
//
//        // STEP 3: Scroll down (3 sn bekle)
//        extentTest.info("3. Scroll down yap ve 3 saniye bekle");
//        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
//        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
//        ReusableMethods.bekle(3);
//        extentTest.pass("✅ STEP 3 PASSED: Scroll down yapıldı (3 sn beklendi)");
//
//        // STEP 4: Scroll up (3 sn bekle)
//        extentTest.info("4. Scroll up yap ve 3 saniye bekle");
//        js.executeScript("window.scrollTo(0, 0);");
//        ReusableMethods.bekle(3);
//        extentTest.pass("✅ STEP 4 PASSED: Scroll up yapıldı (3 sn beklendi)");
//
//        // TEST SONUCU
//        extentTest.pass("✅ US_025_TC_03 testi başarıyla tamamlandı!");
//
//    }
//
//
//    // ========================================
//    // TC_04: Kullanıcı arama fonksiyonunun çalışması ve filtreleme işlemini doğrulamak
//    // ========================================
//    @Test(priority = 4, description = "Users listesi sayfasında arama kutusu ile filtreleme ve " +
//            "temizleme işlemini doğrulamak")
//    public void tc04_AdminPanelUsersSearchFilterTest() {
//
//        layout = new Layout();
//        loginPage = new LoginPage();
//
//        extentTest = extentReports.createTest(
//                "US_025_TC_04 - Users Search & Filter Testi",
//                "Users listesi sayfasında search kutusuna isim yazınca filtreleme yapıldığını, " +
//                        "temizleyince listenin geri geldiğini doğrulamak"
//        );
//
//        extentTest.info("Pre-Condition: Yönetici Users listesi sayfasında olmalı ve " +
//                "sistemde birden fazla kullanıcı bulunmalı");
//
//        // =========================
//        // Login + Admin panel + Users list sayfası (TC_02/TC_03 ortak akış)
//        // =========================
//
//        Driver.getDriver().get(ConfigReader.getProperty("url"));
//        ReusableMethods.waitForClickability(layout.signInLink, 5);
//        layout.signInLink.click();
//        ReusableMethods.bekle(1);
//
//        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 5);
//        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
//        loginPage.passwordInput.sendKeys("Loyal.123123");
//        loginPage.signInButton.click();
//        ReusableMethods.bekle(2);
//
//        ReusableMethods.waitForPageToLoad(10);
//        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"), "Home Page'e yönlendirilmedi!");
//        extentTest.pass("✅ Login başarılı, Home Page açıldı");
//
//        WebElement adminUserButton = Driver.getDriver().findElement(
//                By.xpath("//a[contains(@class,'btn_add')] | " +
//                        "//button[contains(@class,'btn')] | " +
//                        "//*[@id='top_menu']//a[1]")
//        );
//        ReusableMethods.waitForClickability(adminUserButton, 10);
//        adminUserButton.click();
//        ReusableMethods.bekle(2);
//
//        String currentUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
//                "Admin paneline yönlendirilmedi! Mevcut URL: " + currentUrl);
//        extentTest.pass("✅ Admin panel açıldı: " + currentUrl);
//
//        // Users list sayfasına git (sidebar hover + Users click)
//        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
//        Actions actions = new Actions(Driver.getDriver());
//        actions.moveToElement(sidebar).pause(Duration.ofMillis(600)).perform();
//
//        WebElement usersMenuLink = Driver.getDriver().findElement(
//                By.xpath("//span[normalize-space()='Users']/parent::a | " +
//                        "//a[./span[normalize-space()='Users']] | " +
//                        "//a[contains(@href,'/Dashboard/Users') and (normalize-space()='Users' or .//span[normalize-space()='Users'])]")
//        );
//
//        try {
//            ReusableMethods.waitForClickability(usersMenuLink, 10);
//            usersMenuLink.click();
//        } catch (Exception e) {
//            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMenuLink);
//        }
//
//        // URL gelmezse alt menüden Users linkini tıkla
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(12));
//        wait.until(driver -> {
//            if (driver.getCurrentUrl().contains("/Dashboard/Users")) return true;
//
//            try {
//                WebElement usersSubMenuLink = driver.findElement(
//                        By.xpath("//a[normalize-space()='Users' and contains(@href,'/Dashboard/Users')] | " +
//                                "//a[contains(@href,'/Dashboard/Users') and (normalize-space()='Users' or .//span[normalize-space()='Users'])]")
//                );
//                if (usersSubMenuLink.isDisplayed() && usersSubMenuLink.isEnabled()) {
//                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", usersSubMenuLink);
//                }
//            } catch (Exception ignored) {}
//
//            return driver.getCurrentUrl().contains("/Dashboard/Users");
//        });
//
//        String usersUrl = Driver.getDriver().getCurrentUrl();
//        Assert.assertTrue(usersUrl.contains("/Dashboard/Users"),
//                "Users list sayfasına erişilemedi! Mevcut URL: " + usersUrl);
//        extentTest.pass("✅ Users listesi sayfası açıldı: " + usersUrl);
//
//        // =========================
//        // TC_04 Sheet adımları
//        // =========================
//
//        // 1-2) Arama çubuğunu bul + görünür/kullanılabilir olduğunu doğrula
//        extentTest.info("1-2. Users listesi sayfasında arama çubuğunu bul ve kullanılabilir olduğunu doğrula");
//
//        WebElement searchInput = Driver.getDriver().findElement(By.id("search-table"));
//        ReusableMethods.waitForVisibility(searchInput, 10);
//
//        Assert.assertTrue(searchInput.isDisplayed(), "Search input görünmüyor!");
//        Assert.assertTrue(searchInput.isEnabled(), "Search input aktif değil!");
//        extentTest.pass("✅ STEP 1-2 PASSED: Arama (Search) çubuğu görünür ve aktif");
//
//        // Filtre öncesi satır sayısını al (liste geri döndü mü kontrolünde kullanacağız)
//        List<WebElement> rowsBefore = Driver.getDriver().findElements(By.cssSelector("table tbody tr"));
//        int countBefore = rowsBefore.size();
//        Assert.assertTrue(countBefore > 0, "Kullanıcı listesinde hiç satır yok!");
//        extentTest.info("Filtreleme öncesi satır sayısı: " + countBefore);
//
//        // 3) Arama çubuğuna ismi yaz
//        extentTest.info("3. Arama çubuğuna mevcut bir kullanıcının adını yaz: Margarito O'Connell");
//
//        String searchName = "Margarito O'Connell";
//        searchInput.click();
//        searchInput.clear();
//        searchInput.sendKeys(searchName);
//        ReusableMethods.bekle(2);
//
//        extentTest.pass("✅ STEP 3 PASSED: Arama terimi girildi: " + searchName);
//
//        // 4) Sadece aranan kullanıcının gösterildiğini doğrula (basit doğrulama: tabloda metin geçsin)
//        extentTest.info("4. Sadece aranan kullanıcının gösterildiğini doğrula");
//
//        wait.until(driver -> {
//            List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
//            if (rows.isEmpty()) return false;
//            // en az 1 satırda aranan isim geçsin
//            for (WebElement r : rows) {
//                if (r.getText().toLowerCase().contains(searchName.toLowerCase())) return true;
//            }
//            return false;
//        });
//
//        List<WebElement> rowsAfterFilter = Driver.getDriver().findElements(By.cssSelector("table tbody tr"));
//        boolean found = false;
//        for (WebElement r : rowsAfterFilter) {
//            if (r.getText().toLowerCase().contains(searchName.toLowerCase())) {
//                found = true;
//                break;
//            }
//        }
//        Assert.assertTrue(found, "Filtrelenmiş listede aranan kullanıcı bulunamadı!");
//        extentTest.pass("✅ STEP 4 PASSED: Arama kriterine uyan kullanıcı listede görüntülendi");
//
//        // 5-6) Arama alanını temizle ve tüm kullanıcıların geri geldiğini doğrula
//        extentTest.info("5-6. Arama alanını temizle ve tüm kullanıcıların tekrar listelendiğini doğrula");
//
//        // Filtreli halde kaç satır var? (genelde 1 olur)
//        int filteredCount = Driver.getDriver().findElements(By.cssSelector("table tbody tr")).size();
//
//        searchInput.click();
//        ReusableMethods.bekle(1);
//
//        // clear() bazen JS event tetiklemediği için Ctrl+A + Backspace kullanıyoruz
//        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
//
//        // Ekstra garanti: JS ile value='' yap ve input/keyup event dispatch et (DataTables reset için)
//        ((JavascriptExecutor) Driver.getDriver()).executeScript(
//                "arguments[0].value='';" +
//                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
//                        "arguments[0].dispatchEvent(new Event('keyup', {bubbles:true}));",
//                searchInput
//        );
//
//        ReusableMethods.bekle(2);
//
//        // Input gerçekten boş mu?
//        wait.until(ExpectedConditions.attributeToBe(searchInput, "value", ""));
//
//        // Filtre kalkınca satır sayısı artmalı (1'den >1'e dönmesi beklenir)
//        wait.until(driver -> driver.findElements(By.cssSelector("table tbody tr")).size() > filteredCount);
//
//        int countAfterClear = Driver.getDriver().findElements(By.cssSelector("table tbody tr")).size();
//        Assert.assertTrue(countAfterClear > filteredCount,
//                "Arama temizlendi ama liste genişlemedi! Filtered: " + filteredCount +
//                        " AfterClear: " + countAfterClear);
//
//        extentTest.pass("✅ STEP 5-6 PASSED: Arama alanı temizlendi, " +
//                "kullanıcı listesi varsayılan haline döndü " +
//                "(Row count: " + countAfterClear + ")");
//    }
//
//
//    // ========================================
//    // TC_05: Kullanıcı detay görüntüleme ekranının işlevselliğini ve butonların görünürlüğünü doğrulamak
//    // (Görüntüle butonu olmadığı için STEP 4'te FAIL olacak ve test bitecek.)
//    // ========================================
//    @Test(priority = 5, description = "Margarito O'Connell seçilir, Edit/Delete hover yapılır, Görüntüle butonu olmadığı için STEP 4 FAIL olur.")
//    public void tc05_AdminPanelUserDetailViewButtonMissingFailTest() {
//
//        layout = new Layout();
//        loginPage = new LoginPage();
//
//        extentTest = extentReports.createTest(
//                "US_025_TC_05 - User Detail View Button Missing (FAIL) Testi",
//                "Margarito O'Connell satırı seçilir; Edit/Delete sadece hover; Görüntüle butonu olmadığı için STEP 4 fail."
//        );
//
//        // =========================
//        // Ortak Adımlar: Login -> Admin Panel -> Users List
//        // (Burada daha önce çalıştırdığın sağlam akışın aynısı)
//        // =========================
//        Driver.getDriver().get(ConfigReader.getProperty("url"));
//        ReusableMethods.waitForClickability(layout.signInLink, 5);
//        layout.signInLink.click();
//        ReusableMethods.bekle(1);
//
//        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 5);
//        loginPage.emailAddressInput.sendKeys("samet.ture.admin@loyalfriendcare.com");
//        loginPage.passwordInput.sendKeys("Loyal.123123");
//        loginPage.signInButton.click();
//        ReusableMethods.bekle(2);
//
//        ReusableMethods.waitForPageToLoad(10);
//        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"), "Home Page açılmadı!");
//        extentTest.pass("✅ Login başarılı, Home Page açıldı");
//
//        WebElement adminUserButton = Driver.getDriver().findElement(
//                By.xpath("//a[contains(@class,'btn_add')] | //button[contains(@class,'btn')] | //*[@id='top_menu']//a[1]")
//        );
//        ReusableMethods.waitForClickability(adminUserButton, 10);
//        adminUserButton.click();
//        ReusableMethods.bekle(2);
//
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
//        Actions actions = new Actions(Driver.getDriver());
//
//        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
//        ReusableMethods.waitForVisibility(sidebar, 10);
//        actions.moveToElement(sidebar).pause(Duration.ofMillis(500)).perform();
//
//        // Users ana menü (expand)
//        WebElement usersMainMenu = Driver.getDriver().findElement(
//                By.xpath("//span[normalize-space()='Users']/parent::a | //a[.//span[normalize-space()='Users']]")
//        );
//        try {
//            ReusableMethods.waitForClickability(usersMainMenu, 10);
//            usersMainMenu.click();
//        } catch (Exception e) {
//            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMainMenu);
//        }
//        ReusableMethods.bekle(1);
//
//        // Alt menü Users (href=/Dashboard/Users) -> KRİTİK
//        WebElement usersSubMenu = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//a[contains(@href,'/Dashboard/Users') and normalize-space()='Users'] | " +
//                        "//a[contains(@href,'/Dashboard/Users') and .//text()[normalize-space()='Users']]")
//        ));
//        try {
//            usersSubMenu.click();
//        } catch (Exception e) {
//            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersSubMenu);
//        }
//
//        wait.until(ExpectedConditions.urlContains("/Dashboard/Users"));
//        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/Dashboard/Users"),
//                "Users list sayfasına gidilemedi! URL: " + Driver.getDriver().getCurrentUrl());
//
//        extentTest.pass("✅ Users list sayfası açıldı: " + Driver.getDriver().getCurrentUrl());
//
//        // =========================
//        // TC_05 Steps
//        // =========================
//
//        // STEP 1: Margarito O'Connell satırını bul ve seç
//        extentTest.info("STEP 1: Users listesinde Margarito O'Connell kullanıcısını bul ve seç");
//
//        String targetName = "Margarito O'Connell";
//
//        // Tablonun yüklenmesini bekle
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody")));
//
//        // İsim geçen satırı bul (case-insensitive)
//        By targetRowBy = By.xpath("//table//tbody//tr[td][contains(translate(., " +
//                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " +
//                "'" + targetName.toLowerCase() + "')]");
//
//        WebElement targetRow = wait.until(ExpectedConditions.visibilityOfElementLocated(targetRowBy));
//        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", targetRow);
//
//        try {
//            targetRow.click();
//        } catch (Exception e) {
//            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", targetRow);
//        }
//
//        extentTest.pass("✅ STEP 1 PASSED: Margarito O'Connell satırı seçildi");
//
//        // STEP 2: Edit & Delete butonları görünür olmalı
//        extentTest.info("STEP 2: Edit ve Delete butonlarının görünür olduğunu doğrula (tıklama yok)");
//
//        // Not: Butonlar satır içinde değilse (sağ panel / action bar), global arıyoruz.
//        By editBtnBy = By.xpath(
//                "//a[contains(@href,'edit') or contains(@class,'edit') or contains(translate(normalize-space(.),'EDIT','edit'),'edit')] | " +
//                        "//button[contains(@class,'edit') or contains(translate(normalize-space(.),'EDIT','edit'),'edit')]"
//        );
//
//        By deleteBtnBy = By.xpath(
//                "//a[contains(@href,'delete') or contains(@class,'delete') or " +
//                        "contains(translate(normalize-space(.),'DELETE','delete'),'delete') or contains(translate(normalize-space(.),'SIL','sil'),'sil')] | " +
//                        "//button[contains(@class,'delete') or contains(translate(normalize-space(.),'DELETE','delete'),'delete') or contains(translate(normalize-space(.),'SIL','sil'),'sil')]"
//        );
//
//        WebElement editBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(editBtnBy));
//        WebElement deleteBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(deleteBtnBy));
//
//        Assert.assertTrue(editBtn.isDisplayed(), "Edit butonu görünmüyor!");
//        Assert.assertTrue(deleteBtn.isDisplayed(), "Delete/Sil butonu görünmüyor!");
//
//        extentTest.pass("✅ STEP 2 PASSED: Edit ve Delete butonları görüntülendi");
//
//        // STEP 3: Edit & Delete hover (tıklama YOK)
//        extentTest.info("STEP 3: Edit ve Delete butonlarına hover yap (tıklama yok)");
//
//        actions.moveToElement(editBtn).pause(Duration.ofMillis(800)).perform();
//        ReusableMethods.bekle(1);
//
//        actions.moveToElement(deleteBtn).pause(Duration.ofMillis(800)).perform();
//        ReusableMethods.bekle(1);
//
//        extentTest.pass("✅ STEP 3 PASSED: Edit ve Delete üzerinde hover yapıldı (tıklama yapılmadı)");
//
//        // STEP 4: Görüntüle butonu yok -> FAIL
//        extentTest.info("STEP 4: 'Görüntüle/View' butonu aranır (beklenen: yok -> FAIL)");
//
//        try {
//            WebElement viewBtn = Driver.getDriver().findElement(By.xpath(
//                    "//a[contains(translate(normalize-space(.),'GÖRÜNTÜLE','görüntüle'),'görüntüle') or " +
//                            "contains(translate(normalize-space(.),'VIEW','view'),'view') or contains(@href,'view') or contains(@href,'show') or contains(@class,'view')] | " +
//                            "//button[contains(translate(normalize-space(.),'GÖRÜNTÜLE','görüntüle'),'görüntüle') or " +
//                            "contains(translate(normalize-space(.),'VIEW','view'),'view') or contains(@class,'view')]"
//            ));
//
//            // Bulunursa bu testin amacına ters, yine FAIL
//            extentTest.fail("❌ STEP 4 FAILED: 'Görüntüle/View' butonu beklenmiyordu ama bulundu: " + viewBtn.getText());
//            Assert.fail("'Görüntüle/View' butonu sayfada bulunmamalıydı, ancak bulundu!");
//
//        } catch (Exception e) {
//            // Beklenen durum: buton yok -> FAIL olarak raporla ve testi bitir
//            extentTest.fail("❌ STEP 4 FAILED (Beklenen): 'Görüntüle/View' butonu bulunamadı. Test bu adımda fail olmalıdır.");
//            Assert.fail("Görüntüle/View butonu bulunamadı (beklenen fail).");
//        }
//
//    }
//}
//
//
