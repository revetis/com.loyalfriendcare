package demo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.admin_pages.AdminRolesPage;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;
import utilities.ReusableMethods;

public class US43_YoneticiProfilDuzenlemeTesti extends TestBaseRapor {

    Layout layout = new Layout();
    LoginPage loginPage = new LoginPage();
    SoftAssert softAssert = new SoftAssert();
    AdminRolesPage adminRolesPage = new AdminRolesPage();

    @BeforeMethod
    public void setup() {

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        layout.signInLink.click();

        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        ReusableMethods.bekle(1);
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        ReusableMethods.bekle(1);
        loginPage.signInButton.click();
        ReusableMethods.bekle(2);
    }

    @Test
    public void tc01() {

        extentTest = extentReports.createTest("Admin Paneli Kontrolü",
                "Admin panelinden profil menüsüne erişilebilirlik kontrolü");

        adminRolesPage.adminUsernameButton.click();
        ReusableMethods.bekle(2);

        softAssert.assertFalse(adminRolesPage.adminProfileIcon.isDisplayed(), "Profil menüsü görüntülenmedi.");
        softAssert.assertTrue(layout.adminHeaderUserInfoButton.isEnabled(), "Profil menüsü erişilebilir durumda.");

        layout.adminHeaderUserInfoButton.click();
        ReusableMethods.bekle(2);

        softAssert.assertAll();

    }

    @Test
    public void tc02() {
        extentTest = extentReports.createTest("Admin Edit Profile Kontrolü",
                "Admin edit profile menüsünden kişisel bilgilerini düzenleyebilmeli");

        layout.adminHeaderUserInfoButton.click();
        ReusableMethods.bekle(2);

        Assert.assertTrue(adminRolesPage.editProfileButton.isDisplayed(), "Edit profil linki görüntülendi");
        adminRolesPage.editProfileButton.click();
        ReusableMethods.bekle(2);

        String actualText = adminRolesPage.text404.getText();
        String expectedText = "404";

        softAssert.assertFalse(actualText.contains(expectedText), "Sayfa yüklenmedi. 404 hatası ile karşılaşıldı");

        softAssert.assertAll();

    }

}