package tests;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminAddUserPage;
import pages.admin_pages.AdminLoyalFriendCare;
import pages.admin_pages.AdminRolesPages;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.SignIn;
import utilities.TestBaseRapor;

public class US24 extends TestBaseRapor {

    Layout layout = new Layout();
    LoginPage loginPage = new LoginPage();
    AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
    AdminAddUserPage adminAddUserPage = new AdminAddUserPage();
    AdminRolesPages adminRolesPages = new AdminRolesPages();

    @Test
    public void US24_rolOlusturma() {

        extentTest = extentReports.createTest("Rol olusturma testi");

        // https://qa.loyalfriendcare.com/en anasayfaya gidin
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Assert.assertTrue(layout.signInLink.isDisplayed());

        // Sing In butonuna tıklanıp login sayfası açılmalı
        layout.signInLink.click();

        // Admin E-mail adres kutusuna mail adresini girebilmeli
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));

        // Admin password kutusuna passwor'dü girebilmeli

        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));

        // Sing In butonuna tıklayıp login olmalı

        loginPage.signInButton.click();

        // Ana sayfada sag üst kösedeki isminin yazili oldugu butona tiklayın
        adminLoyalFriendCare.adminPanelineGirisButonu.click();

        // Sol acilir pencereye gidin
        layout.adminSidebar.click();

        // Admin sayfasinda sola acilir menüde Roles tiklar.

        adminRolesPages.rolesButon.click();

        // Acilan sekmede create role tıklayın
        adminRolesPages.createRole.click();

        // add saysası görüntülendigini dogrulayın

        adminRolesPages.addRoleSayfasi.isDisplayed();

        String expextedIsimIcerik = "Create your role";
        String actualIsim = adminRolesPages.addRoleSayfasi.getText();

        Assert.assertTrue(actualIsim.contains(expextedIsimIcerik));

        // Zorunlu bilgiler yazin

        Faker faker = new Faker();

        adminRolesPages.rolesName.sendKeys(faker.name().name());

        adminRolesPages.rolesDisplayName.sendKeys(faker.name().username());

        // save butonuna basin

        adminAddUserPage.saveButton.click();

        // Roller listesinin sol üst kosesinde "Role store successfully." yazısı görüntülenir
        adminRolesPages.rollerListesiDogrulama.isDisplayed();


    }


    @Test
    public void rolOlusturma() {

        Layout layout1 = new Layout();
        LoginPage loginPage1 = new LoginPage();
        AdminLoyalFriendCare adminLoyalFriendCare1 = new AdminLoyalFriendCare();
        AdminAddUserPage adminAddUserPage1 = new AdminAddUserPage();
        AdminRolesPages adminRolesPages1 = new AdminRolesPages();

        extentTest = extentReports.createTest("Kayitli olan rolu tekrar olusturmama testi");

        // https://qa.loyalfriendcare.com/en anasayfaya gidin
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        layout1 = new Layout();
        loginPage1 = new LoginPage();
        adminLoyalFriendCare1 = new AdminLoyalFriendCare();
        adminAddUserPage1 = new AdminAddUserPage();
        adminRolesPages1 = new AdminRolesPages();


        // Ana sayfada sag üst kösedeki isminin yazili oldugu butona tiklayın
        adminLoyalFriendCare1.adminPanelineGirisButonu.click();

        // Sol acilir pencereye gidin
        layout1.adminSidebar.click();

        // Admin sayfasinda sola acilir menüde Roles tiklar.

        adminRolesPages1.rolesButon.click();

        // Acilan sekmede create role tıklayın
        adminRolesPages1.createRole.click();

        // add saysası görüntülendigini dogrulayın

        adminRolesPages1.addRoleSayfasi.isDisplayed();

        String expextedIsimIcerik = "Create your role";
        String actualIsim = adminRolesPages1.addRoleSayfasi.getText();

        Assert.assertTrue(actualIsim.contains(expextedIsimIcerik));

        // Zorunlu bilgiler yazin

        adminRolesPages1.rolesName.sendKeys("Ahmet");

        adminRolesPages1.rolesDisplayName.sendKeys("Temizlikçi");

        // save butonuna basin

        adminAddUserPage1.saveButton.click();

        // "The name has already been taken" yazısı görüntülenmeli

        adminRolesPages1.kayitliRolOlusturmaUyarisi.isDisplayed();

    }

}
