package tests.LoginAdminAramaCubuguTests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin_pages.AdminLoyalFriendCare;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.util.Properties;

public class US_028_Admin extends TestBaseRapor {


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
    public void US_028_AdminPaneli_YeniYatakOlusdurVeKaydiYataklarListesineEkle() {

        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_020 Admin olarak, yeni bir yatak oluşturabilmeli " +
                "ve bu kaydı yataklar listesine ekleyebilmeli. ");
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

        // Ana sayfadan yatak yönetimi bölümüne git
        adminLoyalFriendCare.doctorElementSidebar.click();
        adminLoyalFriendCare.createDoctorButton.click();
        ReusableMethods.bekle(3);

        // Tüm zorunlu alanları geçerli bilgilerle doldur
        Faker faker = new Faker();
        adminLoyalFriendCare.newDoctorCreateGap1.sendKeys("Dr Daniel");
        adminLoyalFriendCare.getNewDoctorCreateGap2.sendKeys("Aut sed iure inventore sequi eius nihil qui. " +
                "Animi non magnam vero. " +
                "Aut dicta repellat vel ut aut ea labore. Maxime ut et occaecati amet aliquid sit ut.");
        adminLoyalFriendCare.doctorSave.click();
        ReusableMethods.bekle(1);


    }


    @Test
    public void US_028_AdminPaneli_AyniYatakiYenidenOlusdur() {

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
        // Ana sayfadan yatak yönetimi bölümüne git
        // Tüm zorunlu alanları geçerli bilgilerle doldur



        AdminLoyalFriendCare adminLoyalFriendCare = new AdminLoyalFriendCare();
        extentTest = extentReports.createTest("US_028 Admin olarak, AyniYatakiYenidenOlusdur ");
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

        // Ana sayfadan yatak yönetimi bölümüne git
        adminLoyalFriendCare.doctorElementSidebar.click();
        adminLoyalFriendCare.createDoctorButton.click();
        ReusableMethods.bekle(3);

        // Tüm zorunlu alanları geçerli bilgilerle doldur
        adminLoyalFriendCare.newDoctorCreateGap1.sendKeys("Dr Daniel");
        adminLoyalFriendCare.getNewDoctorCreateGap2.sendKeys("Aut sed iure inventore sequi eius nihil qui. " +
                "Animi non magnam vero. " +
                "Aut dicta repellat vel ut aut ea labore. Maxime ut et occaecati amet aliquid sit ut.");
        adminLoyalFriendCare.doctorSave.click();
        Assert.assertTrue(adminLoyalFriendCare.doctorStoreSuccess.isDisplayed());
        ReusableMethods.bekle(2);

        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/admin");
        ReusableMethods.bekle(2);

        // Ayni yatgi yeniden  olusdurun.
        actions.clickAndHold(adminLoyalFriendCare.adminSidebarHeader)
                .moveByOffset(-5, 0)
                .release()
                .perform();
        Assert.assertTrue(adminLoyalFriendCare.adminSidebarHeader.isDisplayed());


        adminLoyalFriendCare.doctorElementSidebar.click();
        adminLoyalFriendCare.createDoctorButton.click();
        ReusableMethods.bekle(3);

        adminLoyalFriendCare.newDoctorCreateGap1.sendKeys("Dr Daniel");
        adminLoyalFriendCare.getNewDoctorCreateGap2.sendKeys("Aut sed iure inventore sequi eius nihil qui. " +
                "Animi non magnam vero. " +
                "Aut dicta repellat vel ut aut ea labore. Maxime ut et occaecati amet aliquid sit ut.");
        adminLoyalFriendCare.doctorSave.click();
        String expectedIsim = "Doctors Store unsuccessfully.";
        String actualIsim = adminLoyalFriendCare.doctorStoreSuccess.getText();
        Assert.assertTrue(actualIsim.contains(expectedIsim));
        ReusableMethods.bekle(2);

    }
}