package tests.LoginAdminAramaCubuguTests;

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
    AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
    Properties properties;
    UserLoyalFriendCare userLoyalFriendCare = new UserLoyalFriendCare();

    @Test
    public void US_004_Admin_AramaCubugu_PozitivTest() {

        extentTest = extentReports.createTest("US_004 Admin AramaCubugu PozitivTest");
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

        // Arama çubuğuna geçerli bir hizmet / ilaç / aşı adı girin
        adminLoyalFriendCare.adminAramaCubugu.sendKeys("Distemper");
        ReusableMethods.bekle(2);

        // Arama tusuna click et
        adminLoyalFriendCare.searchButonu.click();
        ReusableMethods.bekle(2);

        // Sistem ilgili sonuçları icerdigini test edin
        Assert.assertTrue(adminLoyalFriendCare.aramaSonucElementi.isDisplayed());
        ReusableMethods.bekle(1);
    }

}
