package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminProfileMenuPage;
import pages.common_pages.Layout;

import utilities.*;

public class US42 extends TestBaseRapor {

    AdminProfileMenuPage adminProfileMenuPage = new AdminProfileMenuPage();

@Test
    public void TC01_AdminLogin(){
        extentTest = extentReports.createTest(("US42-TC01\", \"Geçerli bilgilerle Admin girişi ve Dashboard erişimi"));

    Layout layout = new Layout();
    Driver.getDriver().get(ConfigReader.getProperty("url"));
    SignIn.signInAdmin();

    ReusableMethods.waitForVisibility(layout.headerAuthAdminDashboardButton,10);
    Assert.assertTrue(layout.headerAuthAdminDashboardButton.isDisplayed(),"Admin ismi header'da görüntülenemedi.");

    ReusableMethods.waitForClickability(layout.headerAuthAdminDashboardButton,
                    Integer.parseInt(ConfigReader.getProperty("timeout"))).click();

    extentTest.pass("✅ STEP : Admin Login giriş işlemi ve Dashboard erişimi başarıyla gerçekleşti");

}

@Test
    public void TC02_AdminProfileMenuOptions(){
    extentTest = extentReports.createTest("US42-TC02", "Admin profil menü seçeneklerinin görünürlük kontrolü.");

    // 1. Sağ üst köşedeki Ad-Soyad'ın sağ yanındaki profil butonunun görünür ve aktif olduğunu doğrula

    AdminProfileMenuPage adminProfileMenuPage = new AdminProfileMenuPage();
    boolean isMenuReady = ReusableMethods.isDisplayedAndClickable(adminProfileMenuPage.adminProfileMenuDropDown,15);

    Assert.assertTrue(isMenuReady,"HATA: Profil butonu 15 saniye içinde görünür veya aktif hale gelmedi!");
    extentTest.info("Profil butonunun görünür ve aktif olduğu doğrulandı.");

    adminProfileMenuPage.adminProfileMenuDropDown.click();
    extentTest.info("\"Yönetici dashboard ekranında profil butonu tıklandı.");

    // Seçeneklerin (Settings, Profile, Logout) görünürlük kontrolü
    ReusableMethods.waitForVisibility(adminProfileMenuPage.settingsLink, 5);

    Assert.assertTrue(adminProfileMenuPage.settingsLink.isDisplayed(), "Settings seçeneği görüntülenemedi.");
    Assert.assertTrue(adminProfileMenuPage.profileLink.isDisplayed(), "Edit Profile seçeneği görüntülenemedi.");
    Assert.assertTrue(adminProfileMenuPage.logoutLink.isDisplayed(), "Logout seçeneği görüntülenemedi.");

    extentTest.pass("Profil menüsündeki tüm seçenekler beklendiği gibi görüntülendi.");

}

}
