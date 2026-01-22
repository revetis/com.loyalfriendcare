package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US02TC03LogoHomePageRedirectTest extends TestBaseRapor {

    @Test
    public void logoHomePageRedirectTest() {

        Layout layout = new Layout();

        extentTest = extentReports.createTest("US_02_TC_03 - Logo Home Page Redirect Testi",
                "Logo butonuna tıklandığında ana sayfaya yönlendirme işlevselliğini doğrulama");

        // 1. Farklı bir sayfaya git (örn: About Us)
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("1. Kullanıcı ana sayfaya gitti: " + ConfigReader.getProperty("url"));

        ReusableMethods.waitForClickablility(layout.headerAboutUsLink, 10).click();
        extentTest.info("About Us linkine tıklandı");

        ReusableMethods.bekle(2);
        String aboutUsUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(aboutUsUrl.contains("/about"));
        extentTest.pass("About Us sayfası başarıyla açıldı");

        // 2. Header'daki Logo butonuna tıkla
        ReusableMethods.waitForClickablility(layout.headerLogo, 10).click();
        extentTest.info("2. Header'daki Logo butonuna tıklandı");

        // 3. Sayfanın Home Page'e yönlendiğini doğrula
        ReusableMethods.bekle(2);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        extentTest.info("3. Sayfa yönlendirmesi kontrol ediliyor");

        Assert.assertTrue(
                currentUrl.contains("/en") || currentUrl.endsWith("/en/"),
                "Sayfa Home Page'e yönlenmedi! Mevcut URL: " + currentUrl
        );
        extentTest.pass("Sayfa Home Page'e başarıyla yönlendirildi");

        // 4. URL'nin https://qa.loyalfriendcare.com/en olduğunu kontrol et
        String expectedUrl = "https://qa.loyalfriendcare.com/en";
        Assert.assertTrue(
                currentUrl.equals(expectedUrl) || currentUrl.equals(expectedUrl + "/"),
                "URL beklenen değerde değil! Beklenen: " + expectedUrl + ", Gerçek: " + currentUrl
        );
        extentTest.pass("4. URL doğrulandı: " + currentUrl);
    }
}