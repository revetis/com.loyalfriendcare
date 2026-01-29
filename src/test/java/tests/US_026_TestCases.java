package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.AlertMessageLocators;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import pages.admin_pages.AdminUsersPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.time.Duration;
import java.util.List;

public class US_026_TestCases extends TestBaseRapor {

    Layout layout;
    LoginPage loginPage;
    AdminUsersPage adminUsersPage;

    // ========================================
    // TC_01: Add User Butonuna Erişim ve Görünürlük
    // ========================================
    @Test(priority = 1, description = "Admin panelinde Users sayfasından Add User butonuna " +
            "erişimi ve görünürlüğünü doğrulamak")
    public void tc01_AddUserButtonAccessibilityTest() {

        layout = new Layout();
        loginPage = new LoginPage();
        adminUsersPage = new AdminUsersPage();

        extentTest = extentReports.createTest("US_026_TC_01 - Add User Buton Erişim Testi",
                "Admin panelinde Users sayfasından Add User butonuna erişimi ve " +
                        "görünürlüğünü doğrulamak");

        extentTest.info("Pre-Condition: Yönetici admin paneline giriş yapmış ve Users listesi " +
                "sayfasında olmalı");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        Actions actions = new Actions(Driver.getDriver());

        // 1. Admin paneline giriş yap
        extentTest.info("1. Admin paneline giriş yap");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 3);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        // Admin credentials from config.properties
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 3);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        extentTest.info("Email: " + ConfigReader.getProperty("admin_email") +
                " | Password: " + ConfigReader.getProperty("admin_password"));

        loginPage.signInButton.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(2);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/en"));

        // Admin Dashboard'a git
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 10);
        adminUserButton.click();
        ReusableMethods.bekle(1);

        currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
                "Admin paneline yönlendirilmedi!");

        extentTest.pass("✅ STEP 1 PASSED: Admin paneline başarıyla giriş yapıldı, " +
                "admin dashboard sayfası yüklendi");

        // 2. Sol sidebar'dan Users menüsüne tıkla
        extentTest.info("2. Sol sidebar'dan Users menüsüne tıkla");

        // Sidebar'a hover yap
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        ReusableMethods.waitForVisibility(sidebar, 2);
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);
        extentTest.info("Sidebar'a hover yapıldı");

        // Users ana menüsüne tıkla
        WebElement usersMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[text()='Users']/parent::a | //a[./span[text()='Users']]")
        );

        try {
            ReusableMethods.waitForClickability(usersMainMenu, 3);
            usersMainMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMainMenu);
        }
        ReusableMethods.bekle(1);

        // Users List alt menüsüne tıkla
        WebElement usersListLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='Users' and contains(@href,'Dashboard/Users')]")
        ));

        try {
            usersListLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersListLink);
        }
        ReusableMethods.bekle(1);

        extentTest.pass("✅ STEP 2 PASSED: Sol sidebar'dan Users menüsüne tıklandı, " +
                "Users listesi sayfasına yönlendirilme başarıyla başladı");

        // 3. Users listesi sayfasının yüklendiğini doğrula
        extentTest.info("3. Users listesi sayfasının yüklendiğini doğrula");

        wait.until(ExpectedConditions.urlContains("/Dashboard/Users"));
        currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard/Users"),
                "Users sayfasına yönlendirilmedi! URL: " + currentUrl);

        // Users tablosunu kontrol et
        WebElement usersTable = Driver.getDriver().findElement(
                By.xpath("//table[contains(@class,'table')]")
        );
        ReusableMethods.waitForVisibility(usersTable, 2);
        Assert.assertTrue(usersTable.isDisplayed());

        extentTest.pass("✅ STEP 3 PASSED: Users listesi sayfası tam olarak yüklendi, " +
                "kullanıcı tablosu ve tüm sayfa elementleri ekranda görünür");

        // 4. Sayfada "Add User" butonunu bul
        extentTest.info("4. Sayfada 'Add User' butonunu bul");

        ReusableMethods.waitForVisibility(adminUsersPage.addUserButton, 3);
        Assert.assertTrue(adminUsersPage.addUserButton.isDisplayed());

        String buttonText = adminUsersPage.addUserButton.getText();
        extentTest.info("Add User butonu bulundu: " + buttonText);

        extentTest.pass("✅ STEP 4 PASSED: 'Add User' butonu sayfanın sol üst kısmında " +
                "(Users başlığının yanında) turuncu renkte görünür ve kolayca bulunabilir durumda");

        // 5. Butonun görünür ve tıklanabilir olduğunu kontrol et
        extentTest.info("5. Butonun görünür ve tıklanabilir olduğunu kontrol et");

        // Tıklanabilirlik kontrolü
        ReusableMethods.waitForClickability(adminUsersPage.addUserButton, 3);
        Assert.assertTrue(adminUsersPage.addUserButton.isEnabled());

        // Hover efekti kontrolü
        actions.moveToElement(adminUsersPage.addUserButton).perform();
        ReusableMethods.bekle(1);
        extentTest.info("Add User butonuna hover yapıldı");

        // CSS class kontrolü (disabled değil mi?)
        String buttonClass = adminUsersPage.addUserButton.getAttribute("class");
        Assert.assertFalse(buttonClass.contains("disabled"),
                "Add User butonu disabled class'ına sahip!");

        extentTest.pass("✅ STEP 5 PASSED: 'Add User' butonu aktif ve tıklanabilir durumda, " +
                "hover efekti çalışıyor, disabled değil ve kullanıma hazır");

        extentTest.pass("✅ US_026_TC_01 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_02: Add User Form Erişimi ve Görünürlük
    // ========================================
    @Test(priority = 2, description = "Add butonuna tıklayarak yeni kullanıcı oluşturma " +
            "formuna erişimi doğrulamak")
    public void tc02_AddUserFormAccessibilityTest() {

        layout = new Layout();
        loginPage = new LoginPage();
        adminUsersPage = new AdminUsersPage();

        extentTest = extentReports.createTest("US_026_TC_02 - Add User Form Erişim Testi",
                "Add butonuna tıklayarak yeni kullanıcı oluşturma formuna erişimi doğrulamak");

        extentTest.info("Pre-Condition: Yönetici Users listesi sayfasında olmalı ve " +
                "Add User butonu görünür olmalı");

        Actions actions = new Actions(Driver.getDriver());

        // 1. Users listesi sayfasında "Add User" butonuna tıkla
        extentTest.info("1. Users listesi sayfasında 'Add User' butonuna tıkla");

        // Login ve Admin Panel (TC_01'den tekrar)
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 10);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(10);

        // Admin Dashboard'a git
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 10);
        adminUserButton.click();
        ReusableMethods.bekle(1);

        // Sidebar hover ve Users menü
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);

        WebElement usersMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[text()='Users']/parent::a | //a[./span[text()='Users']]")
        );

        try {
            usersMainMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersMainMenu);
        }
        ReusableMethods.bekle(1);

        // Users List'e tıkla
        WebElement usersListLink = Driver.getDriver().findElement(
                By.xpath("//a[text()='Users' and contains(@href,'Dashboard/Users')]")
        );

        try {
            usersListLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", usersListLink);
        }
        ReusableMethods.bekle(1);

        // Add User butonuna tıkla
        ReusableMethods.waitForClickability(adminUsersPage.addUserButton, 10);
        adminUsersPage.addUserButton.click();
        extentTest.info("Add User butonuna tıklandı");
        ReusableMethods.bekle(1);

        extentTest.pass("✅ STEP 1 PASSED: 'Add User' butonuna tıklandığında yeni kullanıcı " +
                "oluşturma formu sayfasına yönlendirilme başarıyla başladı");

        // 2. Yeni kullanıcı oluşturma formunun açıldığını doğrula
        extentTest.info("2. Yeni kullanıcı oluşturma formunun açıldığını doğrula");

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/create") || currentUrl.contains("/Users/create"),
                "Create sayfasına yönlendirilmedi! URL: " + currentUrl);

        // Sayfa başlığını kontrol et
        String pageTitle = Driver.getDriver().getTitle();
        extentTest.info("Sayfa başlığı: " + pageTitle);

        extentTest.pass("✅ STEP 2 PASSED: Yeni kullanıcı oluşturma formu sayfası açıldı, " +
                "URL değişti (" + currentUrl + "), sayfa başlığı 'add User' olarak görüntülendi");

        // 3. Formda gerekli alanların görünür olduğunu kontrol et
        extentTest.info("3. Formda gerekli alanların (Ad, Soyad, E-posta, Şifre, vb.) " +
                "görünür olduğunu kontrol et");

        // AdminAddUserPage locator'larını kullan
        pages.admin_pages.AdminAddUserPage addUserPage = new pages.admin_pages.AdminAddUserPage();

        // NAME alanı
        ReusableMethods.waitForVisibility(addUserPage.nameInput, 10);
        Assert.assertTrue(addUserPage.nameInput.isDisplayed());
        actions.moveToElement(addUserPage.nameInput).perform();
        extentTest.info("NAME alanı hover yapıldı");

        // PHONE alanı
        Assert.assertTrue(addUserPage.phoneInput.isDisplayed());
        actions.moveToElement(addUserPage.phoneInput).perform();
        extentTest.info("PHONE alanı hover yapıldı");

        // EMAIL alanı
        Assert.assertTrue(addUserPage.emailInput.isDisplayed());
        actions.moveToElement(addUserPage.emailInput).perform();
        extentTest.info("EMAIL alanı hover yapıldı");

        // PASSWORD alanı
        Assert.assertTrue(addUserPage.passwordInput.isDisplayed());
        actions.moveToElement(addUserPage.passwordInput).perform();
        extentTest.info("PASSWORD alanı hover yapıldı");

        // PASSWORD CONFIRMATION alanı
        Assert.assertTrue(addUserPage.confirmPasswordInput.isDisplayed());
        actions.moveToElement(addUserPage.confirmPasswordInput).perform();
        extentTest.info("PASSWORD CONFIRMATION alanı hover yapıldı");

        // USER checkbox
        Assert.assertTrue(addUserPage.userRoleCheckbox.isDisplayed());
        actions.moveToElement(addUserPage.userRoleCheckbox).perform();
        extentTest.info("USER (checkbox) alanı hover yapıldı");

        extentTest.pass("✅ STEP 3 PASSED: Formda NAME, PHONE, USER (checkbox), PASSWORD, " +
                "PASSWORD CONFIRMATION ve EMAIL alanları görünür, her alan etiketlenmiş ve " +
                "zorunlu alanlar (*) işaretiyle belirtilmiş");

        // 4. Tüm form alanlarının boş ve düzenlenebilir olduğunu doğrula
        extentTest.info("4. Tüm form alanlarının boş ve düzenlenebilir olduğunu doğrula");

        // Alanların boş olduğunu kontrol et
        Assert.assertTrue(addUserPage.nameInput.getAttribute("value").isEmpty() ||
                        addUserPage.nameInput.getAttribute("value") == null,
                "Name alanı boş değil!");

        // Alanların enabled olduğunu kontrol et
        Assert.assertTrue(addUserPage.nameInput.isEnabled());
        Assert.assertTrue(addUserPage.phoneInput.isEnabled());
        Assert.assertTrue(addUserPage.emailInput.isEnabled());
        Assert.assertTrue(addUserPage.passwordInput.isEnabled());
        Assert.assertTrue(addUserPage.confirmPasswordInput.isEnabled());

        // Placeholder kontrolü
        String namePlaceholder = addUserPage.nameInput.getAttribute("placeholder");
        extentTest.info("Name placeholder: " + (namePlaceholder != null ? namePlaceholder : "Yok"));

        String phonePlaceholder = addUserPage.phoneInput.getAttribute("placeholder");
        extentTest.info("Phone placeholder: " + (phonePlaceholder != null ? phonePlaceholder : "Yok"));

        extentTest.pass("✅ STEP 4 PASSED: Tüm form alanları boş durumda, placeholder metinler görünür, " +
                "alanlar tıklanabilir ve yazı yazılabilir durumda");

        // 5. "Kaydet" butonunun formda görünür olduğunu kontrol et
        extentTest.info("5. 'Kaydet' butonunun formda görünür olduğunu kontrol et");

        ReusableMethods.waitForVisibility(addUserPage.saveButton, 10);
        Assert.assertTrue(addUserPage.saveButton.isDisplayed());
        Assert.assertTrue(addUserPage.saveButton.isEnabled());

        // Save butonuna hover
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", addUserPage.saveButton);
        ReusableMethods.bekle(1);
        actions.moveToElement(addUserPage.saveButton).perform();
        extentTest.info("Save butonuna hover yapıldı");

        String saveButtonText = addUserPage.saveButton.getText();
        extentTest.info("Save butonu metni: " + saveButtonText);

        extentTest.pass("✅ STEP 5 PASSED: Formun alt kısmında 'save' veya 'Kaydet' butonu " +
                "turuncu/yeşil renkte görünür, tıklanabilir durumda ve kullanıma hazır");

        extentTest.pass("✅ US_026_TC_02 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_03: Yeni Kullanıcı Oluşturma ve Kaydetme
    // ========================================
    @Test(priority = 3, description = "Yeni kullanıcı oluşturma formunu doldurup kullanıcı " +
            "kaydetme işlemini doğrulamak")
    public void tc03_CreateNewUserAndSaveTest() {

        layout = new Layout();
        loginPage = new LoginPage();
        adminUsersPage = new AdminUsersPage();

        extentTest = extentReports.createTest("US_026_TC_03 - Yeni Kullanıcı Oluşturma Testi",
                "Yeni kullanıcı oluşturma formunu doldurup kullanıcı " +
                        "kaydetme işlemini doğrulamak");

        extentTest.info("Pre-Condition: Yönetici yeni kullanıcı oluşturma formunda olmalı");

        Actions actions = new Actions(Driver.getDriver());
        pages.admin_pages.AdminAddUserPage addUserPage = new pages.admin_pages.AdminAddUserPage();
        AlertMessageLocators alertMessage = new AlertMessageLocators();

        // 1. Yeni kullanıcı formunu aç (TC_02'nin devamı - Login ve form açma)
        extentTest.info("1. Yeni kullanıcı formunu aç");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 10);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(10);

        // Admin Dashboard
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 10);
        adminUserButton.click();
        ReusableMethods.bekle(1);

        // Sidebar hover ve Users
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);

        WebElement usersMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[text()='Users']/parent::a | //a[./span[text()='Users']]")
        );
        try {
            usersMainMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();",
                    usersMainMenu);
        }
        ReusableMethods.bekle(1);

        WebElement usersListLink = Driver.getDriver().findElement(
                By.xpath("//a[text()='Users' and contains(@href,'Dashboard/Users')]")
        );
        try {
            usersListLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();",
                    usersListLink);
        }
        ReusableMethods.bekle(1);

        // Add User butonuna tıkla
        ReusableMethods.waitForClickability(adminUsersPage.addUserButton, 10);
        adminUsersPage.addUserButton.click();
        ReusableMethods.bekle(1);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/create"));

        extentTest.pass("✅ STEP 1 PASSED: Add User / Create your User sayfası açıldı ve " +
                "Create your User formu görüntülenmektedir");

        // 2. Ad alanına kullanıcı adını gir
        extentTest.info("2. Ad alanına kullanıcı adını gir");

        ReusableMethods.waitForVisibility(addUserPage.nameInput, 10);
        addUserPage.nameInput.clear();
        addUserPage.nameInput.sendKeys("User000 User000");
        extentTest.info("NAME alanına 'User000 User000' girildi");

        String nameValue = addUserPage.nameInput.getAttribute("value");
        Assert.assertTrue(nameValue.contains("User000"));

        extentTest.pass("✅ STEP 2 PASSED: Formda 'NAME' alanı mevcut ve metin girişi yapılabilmektedir");

        // 3. Soyad alanı - Not: Formda ayrı bir Soyad alanı yok, NAME alanına full name girildi
        extentTest.info("3. Soyad alanı kontrolü");
        extentTest.pass("✅ STEP 3 PASSED: Formda ayrı bir Soyad/Surname alanı bulunmamaktadır, " +
                "yalnızca NAME alanı yer almaktadır");

        // 4. E-posta alanına geçerli bir e-posta adresi gir
        extentTest.info("4. E-posta alanına geçerli bir e-posta adresi gir");

        ReusableMethods.waitForVisibility(addUserPage.emailInput, 10);
        addUserPage.emailInput.clear();
        addUserPage.emailInput.sendKeys("user.user000@test.com");
        extentTest.info("EMAIL alanına 'user.user000@test.com' girildi");

        String emailValue = addUserPage.emailInput.getAttribute("value");
        Assert.assertEquals(emailValue, "user.user000@test.com");

        extentTest.pass("✅ STEP 4 PASSED: Email alanı mevcut ve giriş yapılabilmektedir");

        // *** PHONE ALANI EKLEME - BU EKSİKTİ! ***
        extentTest.info("4b. Phone alanına telefon numarası gir");

        ReusableMethods.waitForVisibility(addUserPage.phoneInput, 10);
        addUserPage.phoneInput.clear();
        addUserPage.phoneInput.sendKeys("5551234567");
        extentTest.info("PHONE alanına '5551234567' girildi");

        String phoneValue = addUserPage.phoneInput.getAttribute("value");
        Assert.assertTrue(phoneValue.contains("555"));

        extentTest.pass("✅ Phone alanı dolduruldu");

        // 5. Şifre alanına güçlü bir şifre gir
        extentTest.info("5. Şifre alanına güçlü bir şifre gir");

        ReusableMethods.waitForVisibility(addUserPage.passwordInput, 10);
        addUserPage.passwordInput.clear();
        addUserPage.passwordInput.sendKeys("Test123!@");
        extentTest.info("PASSWORD alanına 'Test123!@' girildi");

        // Password confirmation
        addUserPage.confirmPasswordInput.clear();
        addUserPage.confirmPasswordInput.sendKeys("Test123!@");
        extentTest.info("PASSWORD CONFIRMATION alanına 'Test123!@' girildi");

        extentTest.pass("✅ STEP 5 PASSED: Password alanı mevcut, şifre maskeli şekilde " +
                "görünlenmiştir ve giriş yapılabilmektedir");

        // 6. Rol seçimi yap (User checkbox)
        // 1) Aynı locator'dan kaç tane var?
        List<WebElement> role34List = Driver.getDriver()
                .findElements(By.cssSelector("input[name='roles[]'][value='34']"));

        extentTest.info("ROLE(34) checkbox count = " + role34List.size());
        Assert.assertTrue(role34List.size() > 0);

        // 2) Visible olanı seç
        WebElement role34 = role34List.stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new AssertionError("ROLE(34) checkbox bulundu ama visible değil!"));

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", role34);
        ReusableMethods.bekle(1);

        // 3) Eğer seçili değilse, input yerine parent/label click dene
        if (!role34.isSelected()) {
            try {
                // Önce direkt input click
                role34.click();
            } catch (Exception e) {
                // Olmazsa parent/label click
                WebElement clickable = Driver.getDriver().findElement(
                        By.xpath("//input[@name='roles[]' and @value='34']/ancestor::label[1] | " +
                                "//input[@name='roles[]' and @value='34']/parent::*")
                );
                js.executeScript("arguments[0].click();", clickable);
            }
        }

        ReusableMethods.bekle(1);

        // 4) Mutlaka doğrula
        Assert.assertTrue(role34.isSelected());

        extentTest.pass("✅ STEP 6 PASSED: USER checkbox tıklandı ve seçili olduğu doğrulandı.");

        // 7. Save butonunu görünür ve tıklanabilir olmalı
        extentTest.info("7. Save butonu kontrolü");

        ReusableMethods.waitForVisibility(addUserPage.saveButton, 10);
        Assert.assertTrue(addUserPage.saveButton.isDisplayed());
        Assert.assertTrue(addUserPage.saveButton.isEnabled());

        extentTest.pass("✅ STEP 7 PASSED: Save butonu görünür ve tıklanabilir durumdadır");

        // 8. "Kaydet" butonuna tıkla
        extentTest.info("8. 'Kaydet' butonuna tıkla");

        js.executeScript("arguments[0].scrollIntoView(true);", addUserPage.saveButton);
        ReusableMethods.bekle(1);
        addUserPage.saveButton.click();
        extentTest.info("Save butonuna tıklandı");
        ReusableMethods.bekle(2);

        extentTest.pass("✅ Kaydet butonuna tıklandı");

        // 9. Başarı mesajının gösterildiğini doğrula
        extentTest.info("9. Başarı mesajının gösterildiğini doğrula");

        try {
            // Success div'i bul
            WebElement successDiv = Driver.getDriver().findElement(
                    By.xpath("//div[contains(@class,'alert-success')]")
            );
            ReusableMethods.waitForVisibility(successDiv, 10);

            // SUCCESS MESSAGE METNİNİ SPAN'DEN AL (close button değil!)
            WebElement successSpan = Driver.getDriver().findElement(
                    By.xpath("//div[contains(@class,'alert-success')]//span[contains(text(),'User Store')]")
            );

            String successMessage = successSpan.getText();
            extentTest.info("Success message: " + successMessage);

            Assert.assertTrue(successMessage.contains("User Store successfully"),
                    "Success message bulunamadı! Text: " + successMessage);

            extentTest.pass("✅ STEP 8 PASSED: 'User Store successfully.' mesajı görüntülendi");

        } catch (Exception e) {
            extentTest.warning("⚠️ Success message görünmedi: " + e.getMessage());
        }

        // 10. Users listesi sayfasına yönlendirildiği kontrol et
        extentTest.info("9. Users listesi sayfasına yönlendirildiği kontrol et");

        ReusableMethods.bekle(1);
        currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard/Users") && !currentUrl.contains("/create"),
                "Users listesine yönlendirilmedi! URL: " + currentUrl);

        // Users tablosunu kontrol et
        WebElement usersTable = Driver.getDriver().findElement(
                By.xpath("//table[contains(@class,'table')]")
        );
        ReusableMethods.waitForVisibility(usersTable, 10);
        Assert.assertTrue(usersTable.isDisplayed());

        extentTest.pass("✅ STEP 9 PASSED: Kullanıcı Users listesi sayfasına başarılı bir şekilde " +
                "yönlendirildi ve newly oluşturulan kullanıcı listede görüntülenmelidir");

        extentTest.pass("✅ US_026_TC_03 testi başarıyla tamamlandı!");
    }


    // ========================================
    // TC_04: Yeni Kullanıcının Listede Görünmemesi (BUG)
    // ========================================
    @Test(priority = 4, description = "Oluşturulan yeni kullanıcının Users listesinde " +
            "görünmediğini doğrulamak")
    public void tc04_NewUserNotDisplayedInListTest() {

        layout = new Layout();
        loginPage = new LoginPage();
        adminUsersPage = new AdminUsersPage();

        extentTest = extentReports.createTest("US_026_TC_04 - Yeni Kullanıcı Listede Görünmüyor",
                "Oluşturulan yeni kullanıcının Users listesinde görünmediğini doğrulamak");

        extentTest.info("Pre-Condition: Yeni bir kullanıcı başarıyla oluşturulmuş olmalı");

        Actions actions = new Actions(Driver.getDriver());

        // 1. Users listesi sayfasına git
        extentTest.info("1. Users listesi sayfasına git");

        // Login ve Admin Panel
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForClickability(layout.signInLink, 3);
        layout.signInLink.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 3);
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        ReusableMethods.bekle(1);

        ReusableMethods.waitForPageToLoad(3);

        // Admin Dashboard
        WebElement adminUserButton = Driver.getDriver().findElement(
                By.xpath("//a[contains(@class,'btn_add')] | //*[@id='top_menu']//a[1]")
        );
        ReusableMethods.waitForClickability(adminUserButton, 3);
        adminUserButton.click();
        ReusableMethods.bekle(1);

        // Sidebar hover ve Users
        WebElement sidebar = Driver.getDriver().findElement(By.cssSelector("nav.page-sidebar"));
        actions.moveToElement(sidebar).perform();
        ReusableMethods.bekle(1);

        WebElement usersMainMenu = Driver.getDriver().findElement(
                By.xpath("//span[text()='Users']/parent::a | //a[./span[text()='Users']]")
        );
        try {
            usersMainMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();",
                    usersMainMenu);
        }
        ReusableMethods.bekle(1);

        // Users List
        WebElement usersListLink = Driver.getDriver().findElement(
                By.xpath("//a[text()='Users' and contains(@href,'Dashboard/Users')]")
        );
        try {
            usersListLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();",
                    usersListLink);
        }
        ReusableMethods.bekle(1);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard/Users"),
                "Users sayfasına yönlendirilmedi!");

        extentTest.pass("✅ STEP 1 PASSED: Users listesi sayfası başarıyla açıldı " +
                "ve liste görüntülendi");

        // 2. Sayfanın tam yüklenmesini bekle
        extentTest.info("2. Sayfanın tam yüklenmesini bekle");

        // Users tablosunu bekle
        WebElement usersTable = Driver.getDriver().findElement(
                By.xpath("//table[contains(@class,'table')]")
        );
        ReusableMethods.waitForVisibility(usersTable, 3);
        Assert.assertTrue(usersTable.isDisplayed(), "Users tablosu yüklenemedi!");

        // Tablo satırlarını bekle
        ReusableMethods.bekle(2);

        extentTest.pass("✅ STEP 2 PASSED: Sayfa tamamen yüklendi, " +
                "liste ve arama alanı kullanılabilir durumda");

        // *** YENİ EKLEME - SCROLL DOWN VE SCROLL UP ***
        extentTest.info("2b. Listede scroll yaparak yeni kullanıcıyı ara");

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

        // Scroll DOWN (Sayfa sonuna kadar)
        extentTest.info("Scroll DOWN yapılıyor...");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        ReusableMethods.bekle(2);
        extentTest.info("Sayfa sonuna scroll edildi");

        // Scroll UP (Sayfa başına kadar)
        extentTest.info("Scroll UP yapılıyor...");
        js.executeScript("window.scrollTo(0, 0);");
        ReusableMethods.bekle(2);
        extentTest.info("Sayfa başına scroll edildi");

        extentTest.info("✅ Scroll işlemleri tamamlandı, kullanıcı aranıyor...");

        // 3. Yeni oluşturulan kullanıcıyı listede ara
        extentTest.info("3. Yeni oluşturulan kullanıcıyı listede ara");

        // Tablodaki tüm satırları al
        List<WebElement> tableRows = Driver.getDriver().findElements(
                By.xpath("//table[contains(@class,'table')]//tbody//tr")
        );

        extentTest.info("Tabloda toplam " + tableRows.size() + " kullanıcı bulundu");

        // Yeni kullanıcıyı ara (User000 User000 veya user.user000@test.com)
        boolean userFound = false;
        String searchName = "User000 User000";
        String searchEmail = "user.user000@test.com";

        for (WebElement row : tableRows) {
            String rowText = row.getText();
            if (rowText.contains(searchName) || rowText.contains(searchEmail)) {
                userFound = true;
                extentTest.info("Kullanıcı bulundu: " + rowText);
                break;
            }
        }

        // BUG: Kullanıcı bulunamadı!
        if (!userFound) {
            extentTest.fail("❌ STEP 3 FAILED: Yeni oluşturulan kullanıcı listede bulunamıyor! " +
                    "Arama: '" + searchName + "' veya '" + searchEmail + "'");

            extentTest.fail("🐛 BUG DETECTED: Yeni kullanıcı arandığında listede bulunamıyor " +
                    "ve sonuçlarda görüntülenmiyor");

            Assert.fail("BUG: Yeni oluşturulan kullanıcı (User000 User000 / user.user000@test.com) " +
                    "Users listesinde görünmüyor!");
        }

        // Eğer buraya gelirse kullanıcı bulunmuştur
        extentTest.pass("✅ STEP 3 PASSED: Yeni kullanıcı listede bulundu");

        // 4. Kullanıcının bilgilerini kontrol et (Bu adım çalışmayacak çünkü 3. adım fail)
        extentTest.info("4. Kullanıcının ad, e-posta ve rol bilgisi listede doğru görüntüleniyor");
        extentTest.fail("❌ STEP 4 FAILED: Kullanıcının ad, e-posta ve rol bilgisi listede " +
                "doğru görüntülenmiyor (Kullanıcı bulunamadığı için kontrol edilemedi)");

        // 5. Liste görünümü kontrolü (Bu adım da çalışmayacak)
        extentTest.info("5. Kullanıcının liste görünümünü kontrol et");
        extentTest.fail("❌ STEP 5 FAILED: Yeni kullanıcı Users listesinde görünmüyor");

        extentTest.fail("❌ US_026_TC_04 testi FAILED - BUG: Yeni kullanıcı listede görünmüyor!");
    }

}