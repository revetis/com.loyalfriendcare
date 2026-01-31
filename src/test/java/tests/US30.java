package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.admin_pages.AdminCreateDepartmanPage;
import pages.admin_pages.AdminDashboardPage;
import pages.common_pages.HomePageDepartmentSection;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US30 extends TestBaseRapor {

    AdminDashboardPage adminDashboardPage;
    LoginPage loginPage;
    Layout layout;
    HomePageDepartmentSection homePageDepartmentSection;
    AdminCreateDepartmanPage adminPage; //

    @BeforeMethod
    public void setupAdminLogin() {

        extentTest = extentReports.createTest(
                "Admin Login Setup",
                "Her testten önce Admin olarak sisteme giriş yapılır"
        );

        layout = new Layout();
        loginPage = new LoginPage();
        adminDashboardPage = new AdminDashboardPage();
        homePageDepartmentSection = new HomePageDepartmentSection();
        adminPage = new AdminCreateDepartmanPage();

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        ReusableMethods.waitForVisibility(layout.signInLink, 10);
        layout.signInLink.click();

        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();

     //   ReusableMethods.waitForVisibility(adminDashboardPage.adminDasboardNesrinBora, 10);
     //   adminDashboardPage.adminDasboardNesrinBora.click();

        ReusableMethods.waitForVisibility(layout.headerAuthUserDashboardButton, 10);
        layout.headerAuthUserDashboardButton.click();

        extentTest.pass("Admin login başarılı");
    }


    @Test(priority = 1)
    public void TC01_AdminCreateDepartmentPageAccess() {

        extentTest = extentReports.createTest(
                "TC01 - Create Department Page Access",
                "Admin Create Department sayfasına erişebilmeli"
        );

        // Önce sidebar tıklanıyor
        adminPage.adminSidebar.click();
        extentTest.info("Sidebar açıldı");

        // Sonra Departments menüsüne tıklanıyor
        adminPage.adminSidebarDepartmentsButton.click();
        extentTest.info("Sidebar Departments menüsüne tıklandı");

        ReusableMethods.bekle(2);

        Assert.assertTrue(
                adminPage.adminCreateDepartman.isDisplayed(),
                "Create Departments başlığı görünmüyor!"
        );

        extentTest.pass("Create Department sayfasına erişildi");
    }

    @Test(priority = 2)
    public void TC02_AdminCanCreateNewDepartment() {

        extentTest = extentReports.createTest(
                "TC02 - Create New Department",
                "Tüm alanlar doldurulmalı ve departman başarıyla kaydedilmeli"
        );

        // 🔹 Sidebar → Departments → Create Department
        adminPage.adminSidebar.click();
        extentTest.info("Sidebar açıldı");

        adminPage.adminSidebarDepartmentsButton.click();
        extentTest.info("Departments menüsüne tıklandı");

        adminPage.adminCreateDepartman.click();
        extentTest.info("Create Department sayfası açıldı");

        // 🔹 Form 1 görünür olana kadar bekle
        ReusableMethods.waitForVisibility(adminPage.admincreateDepartmanForm1, 10);

        // 🔹 Form doldurma
        adminPage.admincreateDepartmanForm1.click();
        adminPage.admincreateDepartmanForm1.sendKeys(
                "1",
                Keys.TAB,
                "2",
                Keys.TAB,
                "Test Department",
                Keys.TAB,
                "Red"
        );

// 🔹 Select2 dropdown’u kaput
        ReusableMethods.bekle(1);
        adminPage.admincreateDepartmanForm1.sendKeys(Keys.ENTER);

// 🔹 Odak kır (çok önemli)
        ReusableMethods.bekle(1);
        Driver.getDriver().findElement(By.tagName("body")).click();

// 🔹 Save tıklanabilir olana kadar bekle
        ReusableMethods.waitForClickability(
                adminPage.admincreateDepartmanFormSubmit, 10
        );

// 🔹 Save
        adminPage.admincreateDepartmanFormSubmit.click();
    }






}

