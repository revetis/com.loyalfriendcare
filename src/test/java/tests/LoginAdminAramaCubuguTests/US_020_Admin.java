package tests.LoginAdminAramaCubuguTests;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminLoyalFriendCare;
import pages.user_pages.UserLoyalFriendCare;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.util.Properties;

public class US_020_Admin extends TestBaseRapor {

    //================================================================
    //================================================================
    //==========  Admin Paneli ve Yonetim Araclari  ============
    //================================================================
    //================================================================

    // https://qa.loyalfriendcare.com/en url sayfasina gidin
    // Sign In butonuna basin
    // Admin olarak siteye girin
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Admin giriş ekranına git
    // Yönetim araçları (kullanıcı yönetimi, içerik yönetimi vb.) görüntülenmelidir
    // Yönetim araçlarından birini seç (ör. Kullanıcı Yönetimi)
    // Seçilen yönetim aracına ait ekran açılmalıdır


    AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
    Properties properties;


    @Test
    public void US_020_AdminPaneli_YonetimAraclari01() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_020 Admin Paneli ve Yonetim Araclari ");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(3);

        // Sign In butonuna basin
        adminLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
        adminLoyalFriendCare.adminLoginsayfasiEmailKutusu.sendKeys(ConfigReader.getProperty("admin_email"));
        adminLoyalFriendCare.adminLoginSayfasiPasswordKutusu.sendKeys(ConfigReader.getProperty("admin_password"));
        adminLoyalFriendCare.adminLoginSayfasiLoginButonu.click();

        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        ReusableMethods.bekle(2);

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

        // Admin giriş ekranına git
        adminLoyalFriendCare.adminPanelineGirisButonu.click();

        // Yönetim araçları (kullanıcı yönetimi, içerik yönetimi vb.) görüntülenmelidir
        Actions actions = new Actions(Driver.getDriver());

        actions.clickAndHold(adminLoyalFriendCare.adminSidebarHeader)
                .moveByOffset(-5, 0)
                .release()
                .perform();
        Assert.assertTrue(adminLoyalFriendCare.adminSidebarHeader.isDisplayed());

        // Yönetim araçlarından birini seç (ör. Kullanıcı Yönetimi)
        adminLoyalFriendCare.doctorElementSidebar.click();
        adminLoyalFriendCare.createDoctorButton.click();
        ReusableMethods.bekle(3);

    }


    //================================================================
    //================================================================
    //==========  Admin Paneli ve Yonetim Araclari  ============
    //================================================================
    //================================================================

    // https://qa.loyalfriendcare.com/en url sayfasina gidin
    // Sign In butonuna basin
    // Yetkisiz kullanicinin admin paneline erisimi
    // url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
    // Admin giriş ekranına git
    // Yönetim araçları (kullanıcı yönetimi, içerik yönetimi vb.) görüntülenmelidir
    // Yönetim araçlarından birini seç (ör. Kullanıcı Yönetimi)
    // Seçilen yönetim aracına ait ekran açılmalıdır



    @Test
    public void US_020_AdminPaneli_YonetimAraclari_YetkisizEmailPassword02() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_020 Admin Paneli ve Yonetim Araclari ");
        // https://qa.loyalfriendcare.com/en url sayfasina gidi
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(3);

        // Sign In butonuna basin
        adminLoyalFriendCare.signInButonu.click();
        ReusableMethods.bekle(2);

        // Admin olarak siteye girin
        adminLoyalFriendCare.adminLoginsayfasiEmailKutusu.sendKeys
                (ConfigReader.getProperty("yetkisizAdmin_email"));
        adminLoyalFriendCare.adminLoginSayfasiPasswordKutusu.sendKeys
                (ConfigReader.getProperty("yetkisizAdmin_password"));
        adminLoyalFriendCare.adminLoginSayfasiLoginButonu.click();

        // Admin sayfasinda olarak url'in qa.loyalfriendcare.com kelimeleri icerdigini test edin
        String expextedUrlİcerik = "qa.loyalfriendcare.com/en/admin";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        ReusableMethods.bekle(2);

        Assert.assertTrue(actualUrl.contains(expextedUrlİcerik));

    }
}