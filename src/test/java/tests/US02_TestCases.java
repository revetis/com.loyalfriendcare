package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US02_TestCases extends TestBaseRapor {

    Layout layout;

    @Test
    public void testHomePageLogoVisibilityAndPosition() {

        extentTest = extentReports.createTest("US_02_TC_01 - Home Page Logo Görünürlük ve Konum Testi",
                "Header bölümündeki Logo butonunun görünürlüğünü ve konumunu doğrulama");

        // 1. https://qa.loyalfriendcare.com/en adresine git
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Kullanıcı ana sayfaya gitti: " + ConfigReader.getProperty("url"));

        // 2. Home Page'in yüklenmesini bekle
        ReusableMethods.waitForPageToLoad(15);
        extentTest.info("Home Page yüklendi");

        // 3. Header bölümünü kontrol et
        layout = new Layout();
        Assert.assertTrue(layout.header.isDisplayed());
        extentTest.pass("Header bölümü başarıyla görüntülendi");

        // 4. Loyalfriendcare logosunun sol tarafta konumlandığını doğrula
        ReusableMethods.waitForVisibility(layout.headerLogoImage, 10);
        Assert.assertTrue(layout.headerLogoImage.isDisplayed());

        int logoXPosition = layout.headerLogoImage.getLocation().getX();
        Assert.assertTrue(logoXPosition < 300,
                "Logo sol tarafta konumlanmamış! X pozisyonu: " + logoXPosition);
        extentTest.pass("Loyalfriendcare logosu sol tarafta konumlandı (X: " + logoXPosition + ")");

        // 5. Loyalfriendcare logosunun tıklanabilir olduğunu doğrula
        ReusableMethods.waitForClickablility(layout.headerLogo, 10);
        Assert.assertTrue(layout.headerLogo.isEnabled());
        extentTest.pass("Loyalfriendcare logosu tıklanabilir durumda");

        extentTest.pass("US_02_TC_01 Test başarıyla tamamlandı");

        System.out.println("Hi There");

    } // main method sonu
}


