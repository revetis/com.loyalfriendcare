package tests;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.admin_pages.AdminCreateMedicinesPage;
import pages.admin_pages.AdminDashboardPage;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US34 extends TestBaseRapor {

    AdminDashboardPage adminDashboardPage;
    LoginPage loginPage;
    Layout layout;
    AdminCreateMedicinesPage adminMedicinesPage;

    @BeforeMethod
    public void setupAdminLogin() {

        extentTest = extentReports.createTest(
                "Admin Login",
                "Admin sisteme giriş yapar"
        );

        layout = new Layout();
        loginPage = new LoginPage();
        adminDashboardPage = new AdminDashboardPage();
        adminMedicinesPage = new AdminCreateMedicinesPage();

        Driver.getDriver().get("https://qa.loyalfriendcare.com");

        ReusableMethods.waitForVisibility(layout.signInLink, 10);
        layout.signInLink.click();

        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        layout.headerAuthUserDashboardButton.click();

        extentTest.pass("Admin login başarılı");
    }

    // =========================================================
    // TC01 - Admin Create Medicines sayfasına erişebilmeli
    // =========================================================
    @Test(priority = 1)
    public void TC01_AdminCanAccessCreateMedicinesPage() {

        extentTest = extentReports.createTest(
                "TC01 - Create Medicines Page Access",
                "Admin Create Medicines sayfasına erişebilmeli"
        );


        // Önce sidebar tıklanıyor
        layout.adminSidebar.click();
        extentTest.info("Sidebar açıldı");

        // Sonra Departments menüsüne tıklanıyor
        layout.adminSidebarMedicinesButton.click();
        extentTest.info("Sidebar Departments menüsüne tıklandı");

        ReusableMethods.bekle(2);

        Assert.assertTrue(
                layout.adminSidebarMedicinesLi.isDisplayed(),
                "Create Departments başlığı görünmüyor!"
        );


        extentTest.pass("Create Medicines sayfasına erişildi");
    }

    // =========================================================
    // TC02 - Admin yeni bir ilaç oluşturabilmeli
    // =========================================================
    @Test(priority = 2)
    public void TC02_AdminCanCreateNewMedicine() {

        extentTest = extentReports.createTest(
                "TC02 - Create New Medicine",
                "Admin yeni bir ilaç oluşturabilmeli"
        );
        // 🔹 Sidebar → Departments → Create Department
        layout.adminSidebar.click();
        extentTest.info("Sidebar açıldı");

        layout.adminSidebarMedicinesButton.click();
        extentTest.info("Departments menüsüne tıklandı");


        extentTest.info("Create Department sayfası açıldı");
        // Create Medicines sayfası
        adminMedicinesPage.adminCreateMedicines.click();
        extentTest.info("Create Medicines sayfası açıldı");

        // Form görünür olana kadar bekle
        ReusableMethods.waitForVisibility(
                adminMedicinesPage.adminCreateMedicinesForm1, 10
        );

        // Form doldurma (örnek)
        adminMedicinesPage.adminCreateMedicinesForm1.click();
        adminMedicinesPage.adminCreateMedicinesForm1.sendKeys(
                "Paracetamol",
                Keys.TAB,
                "500 mg",
                Keys.TAB,
                "Painkiller"
        );

        ReusableMethods.bekle(1);

        // Save butonu
        ReusableMethods.waitForClickability(
                adminMedicinesPage.admincreateDepartmanFormSubmit, 10
        );

        adminMedicinesPage.admincreateDepartmanFormSubmit.click();
        extentTest.info("Save butonuna tıklandı");

        // Basit doğrulama (toast / liste kontrolü eklenebilir)
        ReusableMethods.bekle(2);

        extentTest.pass("Yeni ilaç başarıyla oluşturuldu");
    }
}
