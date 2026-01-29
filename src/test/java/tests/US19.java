package tests;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import utilities.*;

public class US19 extends TestBaseRapor {


    @BeforeMethod
    public void setupTests() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }

    @Test
    public void TC_01_KayitliKullaniciOlarakSignoutOzelligiGorunurlukVeIslevsellikKontrolu() {
        extentTest = extentReports.createTest("TC01", "Kayitli kullanici olarak Signout ozelligi gorunurluk ve islevsellik kontrolu");
        Layout layout = new Layout();

        extentTest.info("Kayitli kullanici olarak giris yapiliyor (Pre-condition).");
        SignIn.signInUser();
        layout = new Layout();

        String kullaniciAdi = layout.headerAuthUserDashboardButton.getText();
        extentTest.info("Giris basarili. Kullanici adi tespit edildi: " + kullaniciAdi);

        extentTest.info("Header bolumunde Signout butonunun gorunurlugu ve aktifligi kontrol ediliyor.");
        Assert.assertTrue(layout.headerAuthUserSignOutButton.isDisplayed(), "Kayitli kullanicilar icin signout butonu gorunur degil.");
        Assert.assertTrue(layout.headerAuthUserSignOutButton.isEnabled(), "Kayitli kullanicilar icin signout butonu tiklanabilir degil.");

        extentTest.info("Signout butonuna tiklaniyor.");
        SignOut.signOutUser();

        extentTest.info("Tarayicidan Geri (Back) tusuna basilarak Cache/Session kontrolu yapiliyor.");
        Driver.getDriver().navigate().back();
        ReusableMethods.waitForPageToLoad(Long.parseLong(ConfigReader.getProperty("timeout")));

        layout = new Layout();
        String cikistanSonraKullaniciAdi = "";
        try {
            cikistanSonraKullaniciAdi = layout.headerAuthUserDashboardButton.getText();
        } catch (NoSuchElementException e) {
            // Element zaten yoksa bu iyiye işarettir, kullanıcı adı görünmüyor demektir
        } catch (Exception e) {
            e.printStackTrace();
        }

        extentTest.info("Geri donuldugunde kullanici verilerinin silindigi dogrulaniyor.");
        Assert.assertNotEquals(cikistanSonraKullaniciAdi, kullaniciAdi, "HATA: Çıkış yapıldıktan sonra geri tuşuyla dönüldüğünde kullanıcı verileri hala cache'de duruyor!");

        extentTest.info("Sayfa yenileniyor (Refresh) ve kesin cikis durumu kontrol ediliyor.");
        Driver.getDriver().navigate().refresh();
        ReusableMethods.waitForPageToLoad(Long.parseLong(ConfigReader.getProperty("timeout")));


        layout = new Layout();

        extentTest.info("Sign In linkinin gorunur oldugu dogrulaniyor.");
        Assert.assertTrue(layout.signInLink.isDisplayed(), "HATA: cikis yapildiktan sonra signout butonu gorunur degil");
        Assert.assertTrue(layout.signInLink.isEnabled(), "HATA: cikis yapildiktan sonra signout butonu tiklanabilir degil");

        extentTest.pass("Kayitli kullanici signout islevi ve cache temizligi basariyla test edildi.");

    }


    @Test
    public void TC_01_YoneticiOlarakSignoutOzelligiGorunurlukVeIslevsellikKontrolu() {
        extentTest = extentReports.createTest("TC01", "Yonetici (Admin) olarak Signout ozelligi gorunurluk ve islevsellik kontrolu");
        Layout layout = new Layout();

        extentTest.info("Yonetici hesabi ile giris yapiliyor (Pre-condition).");
        SignIn.signInAdmin();
        layout = new Layout();

        String kullaniciAdi = layout.headerAuthAdminDashboardButton.getText();
        extentTest.info("Yonetici girisi basarili. Yonetici adi: " + kullaniciAdi);

        extentTest.info("Yonetici paneli header bolumunde Signout butonunun durumu kontrol ediliyor.");
        Assert.assertTrue(layout.headerAuthAdminSignOutButton.isDisplayed(), "Yonetici icin signout butonu gorunur degil.");
        Assert.assertTrue(layout.headerAuthAdminSignOutButton.isEnabled(), "Yonetici icin signout butonu tiklanabilir degil.");

        extentTest.info("Signout butonuna tiklanarak cikis yapiliyor.");
        SignOut.signOutAdmin();

        extentTest.info("Tarayicidan Geri (Back) tusuna basilarak Admin Cache kontrolu yapiliyor.");
        Driver.getDriver().navigate().back();
        ReusableMethods.waitForPageToLoad(Long.parseLong(ConfigReader.getProperty("timeout")));


        layout = new Layout();
        String cikistanSonraKullaniciAdi = "";
        try {
            cikistanSonraKullaniciAdi = layout.headerAuthAdminDashboardButton.getText();
        } catch (NoSuchElementException e) {
            // Element zaten yoksa bu iyiye işarettir, kullanıcı adı görünmüyor demektir
        } catch (Exception e) {
            e.printStackTrace();
        }

        extentTest.info("Geri donuldugunde Yonetici oturumunun kapali oldugu dogrulaniyor.");
        Assert.assertNotEquals(cikistanSonraKullaniciAdi, kullaniciAdi, "HATA: Çıkış yapıldıktan sonra geri tuşuyla dönüldüğünde Yonetici verileri hala cache'de duruyor!");

        extentTest.info("Sayfa yenileniyor ve Sign In linki kontrol ediliyor.");
        Driver.getDriver().navigate().refresh();
        ReusableMethods.waitForPageToLoad(Long.parseLong(ConfigReader.getProperty("timeout")));


        layout = new Layout();

        Assert.assertTrue(layout.signInLink.isDisplayed(), "HATA: cikis yapildiktan sonra signout butonu gorunur degil(Yonetici)");
        Assert.assertTrue(layout.signInLink.isEnabled(), "HATA: cikis yapildiktan sonra signout butonu tiklanabilir degil(Yonetici)");

        extentTest.pass("Yonetici signout islevi ve cache temizligi basariyla test edildi.");
    }

//    =================!!! TC_03 u hamburger butonu olmadigi icin locate alamadim bu yuzden bu testi atladim ve bug raporuna critical olarak raporladim =====================

}