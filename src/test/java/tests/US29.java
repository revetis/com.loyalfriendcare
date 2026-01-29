package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminDashboardPage;
import pages.admin_pages.AdminDashboardPages;
import pages.admin_pages.DepartmentsPage;
import pages.common_pages.HomepageBodyPage;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.time.Duration;
import java.util.List;

public class US29 extends TestBaseRapor {

    @Test
    public void US29_TC01_departmentsListIsDisplayed() {

        extentTest = extentReports.createTest(
                "US_029_TC01",
                "Admin, dashboard üzerinden Departments sayfasına erişebilmeli ve departman listesini görüntüleyebilmelidir."
        );

        // 1) URL'e git
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Ana sayfaya gidildi.");

        // Pages
        LoginPage loginPage = new LoginPage();
        AdminDashboardPage dashboardPage = new AdminDashboardPage();
        AdminDashboardPages adminDashboardPages = new AdminDashboardPages();
        DepartmentsPage departmentsPage = new DepartmentsPage();
        Layout layout = new Layout();
        HomepageBodyPage homepageBodyPage = new HomepageBodyPage();

        // 1) Admin login

        ReusableMethods.waitForClickability(homepageBodyPage.headerSignIn, 5).click();
        extentTest.info("Header Sign In butonuna tıklandı.");


        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 5)
                .sendKeys(ConfigReader.getProperty("admin_email"));

        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        extentTest.info("Admin email & password girildi, Sign In tıklandı.");

        // 2) Dashboard doğrula

        layout.headerAuthUserDashboardButton.click();
        extentTest.info("Dashboard butonuna tıklandı.");

        ReusableMethods.bekle(1);

        Assert.assertTrue(
                Driver.getDriver().getCurrentUrl().contains("admin"),
                "Dashboard sayfası açılmadı! URL: " + Driver.getDriver().getCurrentUrl()
        );

        extentTest.pass("Admin Dashboard sayfası başarıyla açıldı.");

        Assert.assertTrue(dashboardPage.adminDashboardUsersInDatabaseText.isDisplayed(),
                "Dashboard açılmadı / admin giriş başarısız olabilir.");
        extentTest.pass("Dashboard açıldı (Users in Database text göründü).");


        // 3) Departments sayfasına git (üst menü + alt menü)

        Actions actions = new Actions(Driver.getDriver());

        actions
                .moveToElement(adminDashboardPages.sidebarDepartmentsIcon)
                .pause(Duration.ofMillis(600))
                .perform();

        extentTest.info("Sidebar Departments ikonu üzerine hover yapıldı.");


        ReusableMethods.waitForClickability(adminDashboardPages.adminSidebarDepartmentsButton, 5).click();
        extentTest.info("Sol menüden Departments üst menüsü tıklandı.");

        ReusableMethods.waitForClickability(adminDashboardPages.departmentsListSubMenu, 5).click();
        extentTest.info("Alt menüden Departments (liste) tıklandı.");


        // 4) Departments sayfası açıldı mı?
        Assert.assertTrue(departmentsPage.isDepartmentsPageOpened(),
                "Departments sayfası açılmadı.");
        extentTest.pass("Departments sayfası açıldı.");

        // 5) Departman listesi dolu mu?
        int rowCount = departmentsPage.getDepartmentRowCount();
        Assert.assertTrue(rowCount > 0,
                "Departman listesi boş görünüyor. Row count: " + rowCount);
    }

    @Test
    public void US29_TC02_searchDepartmentByName() {

        extentTest = extentReports.createTest(
                "US_029_TC02",
                "Admin, Departments sayfasında listeden seçilen bir departman adını aratabilmelidir."
        );

        // 1) URL'e git
        Driver.getDriver().manage().deleteAllCookies();

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        Driver.getDriver().navigate().refresh();
        extentTest.info("Ana sayfaya gidildi.");

        // Pages
        LoginPage loginPage = new LoginPage();
        AdminDashboardPage dashboardPage = new AdminDashboardPage();
        AdminDashboardPages adminDashboardPages = new AdminDashboardPages();
        DepartmentsPage departmentsPage = new DepartmentsPage();
        Layout layout = new Layout();
        HomepageBodyPage homepageBodyPage = new HomepageBodyPage();

        // 2) Admin login
        ReusableMethods.waitForClickability(homepageBodyPage.headerSignIn, 5).click();
        extentTest.info("Header Sign In butonuna tıklandı.");

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 5)
                .sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        extentTest.info("Admin email & password girildi, Sign In tıklandı.");

        // 3) Dashboard doğrula
        ReusableMethods.waitForClickability(layout.headerAuthUserDashboardButton, 5).click();
        extentTest.info("Dashboard butonuna tıklandı.");

        Assert.assertTrue(
                Driver.getDriver().getCurrentUrl().contains("admin"),
                "Dashboard sayfası açılmadı! URL: " + Driver.getDriver().getCurrentUrl()
        );

        Assert.assertTrue(
                ReusableMethods.waitForVisibility(dashboardPage.adminDashboardUsersInDatabaseText, 5).isDisplayed(),
                "Dashboard açılmadı / admin giriş başarısız olabilir."
        );
        extentTest.pass("Admin Dashboard açıldı.");

        // 4) Sidebar aç (ikon hover) + Departments sayfasına git
        Actions actions = new Actions(Driver.getDriver());

        actions
                .moveToElement(adminDashboardPages.sidebarDepartmentsIcon)
                .pause(Duration.ofMillis(600))
                .perform();

        extentTest.info("Sidebar Departments ikonu üzerine hover yapıldı.");


        ReusableMethods.waitForClickability(adminDashboardPages.adminSidebarDepartmentsButton, 5).click();
        extentTest.info("Sol menüden Departments üst menüsü tıklandı.");

        ReusableMethods.waitForClickability(adminDashboardPages.departmentsListSubMenu, 5).click();
        extentTest.info("Alt menüden Departments (liste) tıklandı.");
        // 5) Departments sayfası açıldı mı?
        Assert.assertTrue(
                ReusableMethods.waitForVisibility(departmentsPage.departmentsHeader, 5).isDisplayed(),
                "Departments sayfası açılmadı."
        );
        extentTest.pass("Departments sayfası açıldı.");

        // 6) Sabit departman adı ile arama yap (random yok)
        String selectedName = "Wellness";
        extentTest.info("Aratılacak departman adı (sabit): " + selectedName);

        String expectedName = selectedName;
        Assert.assertFalse(expectedName.isEmpty(), "Seçilen departman adı boş geldi.");
        extentTest.info("Aratılacak departman adı: " + expectedName);

        // 7) SearchBox'a tıkla ve arama yap
        ReusableMethods.waitForClickability(departmentsPage.searchBox, 5).click();
        departmentsPage.searchBox.clear();
        departmentsPage.searchBox.sendKeys(expectedName);
        extentTest.info("SearchBox'a departman adı yazıldı.");

        ReusableMethods.bekle(1); // tablo filtreleme için küçük bekleme

        // 8) Sonuç doğrulama: Görünen tüm isimler expectedName içermeli
        Assert.assertTrue(departmentsPage.departmentNames.size() > 0,
                "Arama sonrası tabloda hiç sonuç görünmüyor.");

        for (WebElement nameEl : departmentsPage.departmentNames) {
            String actual = nameEl.getText().trim().toLowerCase();
            Assert.assertTrue(actual.contains(expectedName.toLowerCase()),
                    "Arama sonucu beklenmeyen kayıt bulundu. Beklenen: " + expectedName + " | Actual: " + actual);
        }

        extentTest.pass("Arama sonucu listelenen departman isimleri aranan kelimeyi içeriyor.");
    }

    @Test
    public void US29_TC03_searchRandomDepartmentAndVerifyEditDelete() {

        extentTest = extentReports.createTest(
                "US_029_TC03",
                "Admin, Departments sayfasında rastgele departmanı aratıp Edit/Delete ile güncelleme+silme doğrulamalıdır."
        );

        // 1) URL'e git
        Driver.getDriver().manage().deleteAllCookies();
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Driver.getDriver().navigate().refresh();
        extentTest.info("Ana sayfaya gidildi.");
        // Pages
        LoginPage loginPage = new LoginPage();
        AdminDashboardPage dashboardPage = new AdminDashboardPage();
        AdminDashboardPages adminDashboardPages = new AdminDashboardPages();
        DepartmentsPage departmentsPage = new DepartmentsPage();
        Layout layout = new Layout();
        HomepageBodyPage homepageBodyPage = new HomepageBodyPage();

        // 2) Admin login
        ReusableMethods.waitForClickability(homepageBodyPage.headerSignIn, 5).click();
        extentTest.info("Header Sign In butonuna tıklandı.");
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 5) .sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click(); extentTest.info("Admin email & password girildi, Sign In tıklandı.");

        // 3) Dashboard doğrula
        ReusableMethods.waitForClickability(layout.headerAuthUserDashboardButton, 5).click();
        Assert.assertTrue(
                ReusableMethods.waitForVisibility(dashboardPage.adminDashboardUsersInDatabaseText, 5).isDisplayed(),
                "Dashboard açılmadı / admin giriş başarısız olabilir."
        );
        extentTest.pass("Admin Dashboard açıldı.");

        // 4) Sidebar hover + Departments sayfasına git
        Actions actions = new Actions
                (Driver.getDriver()); actions.moveToElement(adminDashboardPages.sidebarDepartmentsIcon) .pause(Duration.ofMillis(600)) .perform();
                extentTest.info("Sidebar Departments ikonu üzerine hover yapıldı.");
                ReusableMethods.waitForClickability(adminDashboardPages.adminSidebarDepartmentsButton, 5).click(); extentTest.info("Sol menüden Departments üst menüsü tıklandı.");
                ReusableMethods.waitForClickability(adminDashboardPages.departmentsListSubMenu, 5).click(); extentTest.info("Alt menüden Departments (liste) tıklandı.");

        // 5) Departments sayfası açıldı mı?
        Assert.assertTrue( ReusableMethods.waitForVisibility(departmentsPage.departmentsHeader, 5).isDisplayed(), "Departments sayfası açılmadı." );
        extentTest.pass("Departments sayfası açıldı.");

        // 6) Sabit departman adı ile arama yap (random yok)
        String selectedName = "Wellness";
        extentTest.info("Aratılacak departman adı (sabit): " + selectedName);

        // 7) SearchBox'a yaz
        ReusableMethods.waitForClickability(departmentsPage.searchBox, 5).click();
        departmentsPage.searchBox.clear();
        departmentsPage.searchBox.sendKeys(selectedName);
        extentTest.info("SearchBox'a random departman adı yazıldı.");
        ReusableMethods.bekle(1);

        // 8) Sonuç var mı?
        Assert.assertTrue(departmentsPage.filteredRows.size() > 0, "Arama sonrası tabloda sonuç yok. Aranan: " + selectedName);
        extentTest.pass("Arama sonucu en az 1 satır döndü.");

        // 9) İlk satır adı aranan ismi içeriyor mu?
        String resultName = departmentsPage.firstRowDepartmentName.getText().trim(); Assert.assertTrue(resultName.toLowerCase().contains(selectedName.toLowerCase()), "Arama sonucu departman adı eşleşmedi. Aranan: " + selectedName + " | Gelen: " + resultName); extentTest.pass("Arama sonucu departman adı doğrulandı: " + resultName);

        // 10) Çıkan sonucun yanında Edit/Delete var mı?
        WebElement firstRow = departmentsPage.allRows.get(0);
        // Aynı satırın içindeki Edit link ve Delete buttonu yakala
        List<WebElement> editLinks = firstRow.findElements(By.xpath(".//a[contains(@class,'fa-edit') or contains(.,'Edit')]"));
        List<WebElement> deleteButtons = firstRow.findElements(By.xpath(".//button[contains(.,'Delete') or contains(@class,'btn-danger')]"));

        Assert.assertTrue(editLinks.size() > 0, "Arama sonucu satırında Edit butonu bulunamadı!");
        Assert.assertTrue(deleteButtons.size() > 0, "Arama sonucu satırında Delete butonu bulunamadı!");
        extentTest.pass("Arama sonucu satırında Edit ve Delete butonları mevcut.");

        // 11) Edit'e tıkla ve Edit sayfasına git
        WebElement editBtn = editLinks.get(0);
        ReusableMethods.waitForClickability(editBtn, 5).click();
        extentTest.info("Edit butonuna tıklandı, edit sayfasına gidildi.");

        // 12) Edit sayfasında Title alanını değiştir ve kaydet
        ReusableMethods.waitForVisibility(departmentsPage.titleDepartmentInput, 5);
        String oldTitle = departmentsPage.titleDepartmentInput.getAttribute("value").trim();
        Assert.assertFalse(oldTitle.isEmpty(), "Edit sayfasında Title değeri boş geldi!");
        extentTest.info("Eski Title: " + oldTitle); String newTitle = oldTitle + "_upd";
        departmentsPage.titleDepartmentInput.clear();
        departmentsPage.titleDepartmentInput.sendKeys(newTitle);
        extentTest.info("Yeni Title yazıldı: " + newTitle); ReusableMethods.waitForClickability(departmentsPage.saveButton, 5).click();
        extentTest.info("Save butonuna tıklandı.");

        // 13) Save sonrası tekrar listeye dön (en stabil yöntem: back + refresh)
        Driver.getDriver().navigate().refresh(); extentTest.info("Departments list sayfasına geri dönüldü ve refresh yapıldı.");

        // 14) Yeni Title ile search yap
        ReusableMethods.waitForVisibility(departmentsPage.searchBox, 5);
        departmentsPage.searchBox.clear();
        departmentsPage.searchBox.sendKeys(newTitle);
        extentTest.info("SearchBox'a yeni Title yazıldı: " + newTitle);
        ReusableMethods.bekle(1);
        Assert.assertTrue(departmentsPage.filteredRows.size() > 0, "Güncellenen kayıt listede bulunamadı! Aranan: " + newTitle);
        extentTest.pass("Güncellenen departman listede bulundu.");

        // 15) Gelen ilk satır gerçekten newTitle içeriyor mu?
        String updatedRowName = departmentsPage.firstRowDepartmentName.getText().trim();
        Assert.assertTrue(updatedRowName.toLowerCase().contains(newTitle.toLowerCase()), "Güncellenen satır adı eşleşmedi. Beklenen: " + newTitle + " | Gelen: " + updatedRowName);
        extentTest.pass("Güncellenen satır adı doğrulandı: " + updatedRowName);

        // 16) Delete'e tıkla (ilk satırdan yakala)
        WebElement firstRowAfterUpdate = departmentsPage.allRows.get(0);
        WebElement deleteBtn = firstRowAfterUpdate.findElement(By.xpath(".//button[contains(.,'Delete') or contains(@class,'btn-danger')]"));
        ReusableMethods.waitForClickability(deleteBtn, 5).click(); extentTest.info("Delete butonuna tıklandı.");

        // 17) Silindikten sonra tekrar aynı Title ile ara -> sonuç 0 olmalı

        Driver.getDriver().navigate().refresh();
        ReusableMethods.waitForVisibility(departmentsPage.searchBox, 5);
        departmentsPage.searchBox.clear();
        departmentsPage.searchBox.sendKeys(newTitle);
        extentTest.info("Silme sonrası tekrar arama yapıldı: " + newTitle);
        ReusableMethods.bekle(1); Assert.assertTrue(departmentsPage.filteredRows.size() == 0, "Silinen kayıt hala listede görünüyor! Aranan: " + newTitle);
        extentTest.pass("Silme işlemi doğrulandı. Kayıt listede görünmüyor.");
    }



}
