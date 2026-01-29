package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.common_pages.HomepageBodyPage;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import pages.user_pages.UserPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US21 extends TestBaseRapor {


    @Test(priority = 1)
    public void US_021_TC01_AdminLogin_NavigateToLogin() {

        extentTest = extentReports.createTest(
                "US_021_TC01",
                "Admin, ana sayfadan Sign In'e tıklayarak login sayfasına gidebilmeli"
        );

        // 1) URL'e git
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Ana sayfaya gidildi.");

        HomepageBodyPage homepageBodyPage = new HomepageBodyPage();

        // 2) Header Sign In'e tıkla

        ReusableMethods.waitForClickability(homepageBodyPage.headerSignIn, 5).click();
        extentTest.info("Header Sign In butonuna tıklandı.");

        // 3) Login sayfasında email/pass gir
        LoginPage loginPage = new LoginPage();
        Layout layout = new Layout();

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 5)
                .sendKeys(ConfigReader.getProperty("admin_email"));

        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        extentTest.info("Admin email & password girildi, Sign In tıklandı.");

        // 4) Login sonrası header elementleri görünür mü?
        ReusableMethods.waitForVisibility(layout.headerLogo, 5);
        Assert.assertTrue(layout.headerLogo.isDisplayed(),
                "Header Logo görüntülenmedi!");

        Assert.assertTrue(layout.headerAuthUserDashboardButton.isDisplayed(),
                "Dashboard butonu görüntülenmedi!");

        Assert.assertTrue(layout.headerAuthUserSignOutButton.isDisplayed(),
                "Sign Out butonu görüntülenmedi!");

        extentTest.pass("Header Logo, Dashboard ve Sign Out butonları görüntülendi.");

        // 5) Logo'ya tıkla → Ana sayfaya yönlendirme
        layout.headerLogo.click();
        extentTest.info("Header logo tıklandı.");

        ReusableMethods.bekle(1);

        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/en"),
                "Logo tıklanınca ana sayfaya dönülmedi! URL: " + actualUrl);

        extentTest.pass("Logo tıklanınca ana sayfaya yönlendirildi.");

        // 6) Dashboard butonuna tıkla → Admin Panel
        layout.headerAuthUserDashboardButton.click();
        extentTest.info("Dashboard butonuna tıklandı.");

        ReusableMethods.bekle(1);

        Assert.assertTrue(
                Driver.getDriver().getCurrentUrl().contains("admin"),
                "Dashboard sayfası açılmadı! URL: " + Driver.getDriver().getCurrentUrl()
        );

        extentTest.pass("Admin Dashboard sayfası başarıyla açıldı.");


    }

    @Test(priority = 2)
    public void US_021_TC02_AdminLogout_SignOut() {

        extentTest = extentReports.createTest(
                "US_021_TC02",
                "Admin Sign Out'a tıklayınca sistemden çıkış yapmalı ve Homepage sayfasına yönlenmeli"
        );

        // 1) Ana sayfaya git
        Driver.getDriver().manage().deleteAllCookies();

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        Driver.getDriver().navigate().refresh();

        extentTest.info("Ana sayfaya gidildi.");

        // 2) Sign In'e tıkla
        HomepageBodyPage homepageBodyPage = new HomepageBodyPage();
        ReusableMethods.waitForClickability(homepageBodyPage.headerSignIn, 5).click();
        extentTest.info("Header Sign In butonuna tıklandı.");

        // 3) Login yap
        LoginPage loginPage = new LoginPage();
        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10)
                .sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
        extentTest.info("Admin email & password girildi, Sign In tıklandı.");

        // 4) Login sonrası Sign Out görünür mü?
        Layout layout = new Layout();
        ReusableMethods.waitForVisibility(layout.headerAuthUserSignOutButton, 5);
        Assert.assertTrue(layout.headerAuthUserSignOutButton.isDisplayed(),
                "Login sonrası Sign Out butonu görünmüyor!");
        extentTest.pass("Login başarılı, Sign Out butonu görüntülendi.");

        // 5) Sign Out'a tıkla
        layout.headerAuthUserSignOutButton.click();
        extentTest.info("Sign Out butonuna tıklandı.");

        // 6) Çıkış doğrulama – Homepage'e yönlendirildi mi?

        // homepage logo görünene kadar bekle
        ReusableMethods.waitForVisibility(layout.headerLogo, 5);

        // URL kontrolü
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("/en"),
                "Çıkış sonrası homepage'e yönlendirilmedi! URL: "
                        + Driver.getDriver().getCurrentUrl());
        // Logo kontrolü
        Assert.assertTrue(layout.headerLogo.isDisplayed(),
                "Homepage logo görüntülenmedi!");
        // Sign In tekrar görünmeli
        Assert.assertTrue(homepageBodyPage.headerSignIn.isDisplayed(),
                "Sign Out sonrası Sign In butonu görüntülenmedi!");
        extentTest.pass("Sign Out sonrası homepage'e yönlendirildi ve çıkış başarıyla doğrulandı.");
    }

}


