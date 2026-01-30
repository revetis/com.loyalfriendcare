package tests;

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

public class US43 extends TestBaseRapor {

    Layout layout = new Layout();
    LoginPage loginPage = new LoginPage();
    SoftAssert softAssert = new SoftAssert();
    AdminRolesPage adminRolesPage = new AdminRolesPage();

    @BeforeMethod
    public void setup(){

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        layout.signInLink.click();

        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();
    }


    @Test
    public void tc01() {

        extentTest = extentReports.createTest("Admin Paneli Kontrolü",
                "Admin panelinden profil menüsüne erişilebilirlik kontrolü");

        adminRolesPage.adminUsernameButton.click();

        softAssert.assertFalse(adminRolesPage.adminProfileIcon.isDisplayed(), "Profil menüsü görüntülenmedi.");
        softAssert.assertTrue(layout.adminHeaderUserInfoButton.isEnabled(), "Profil menüsü erişilebilir durumda.");

        layout.adminHeaderUserInfoButton.click();

        softAssert.assertAll();

    }

    @Test
    public void tc02(){
        extentTest = extentReports.createTest("Admin Edit Profile Kontrolü",
                "Admin edit profile menüsünden kişisel bilgilerini düzenleyebilmeli");

        layout.adminHeaderUserInfoButton.click();

        Assert.assertTrue(adminRolesPage.editProfileButton.isDisplayed(),"Edit profil linki görüntülendi");
        adminRolesPage.editProfileButton.click();

        String actualText = adminRolesPage.text404.getText();
        String expectedText = "404";

        softAssert.assertFalse(actualText.contains(expectedText),"Sayfa yüklenmedi. 404 hatası ile karşılaşıldı");

        softAssert.assertAll();


    }

}