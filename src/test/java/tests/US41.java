package tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.admin_pages.AppointmentsPage;
import pages.common_pages.Layout;
import utilities.*;

import java.util.Map;

public class US41 extends TestBaseRapor {
    Layout layout;

    @BeforeClass
    public void setupTests(){
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        SignIn.signInAdmin();
        this.layout = new Layout();
        layout.headerAuthAdminDashboardButton.click();
    }

    @Test
    public void TC01_SidebardaAppointmentsButonuErisilebilirlikKontrolu(){
        extentTest = extentReports.createTest("TC01","Siderbarda Appointments butonu erisilebilirlik kontrolu");

        this.layout = new Layout();

        Point hoverdanOnceSidebarXKonumu = layout.adminSidebar.getLocation();
        extentTest.info("Sidebara hover yapiliyor.");
        ReusableMethods.hover(layout.adminSidebar);
        Point hoverdanSonraSidebarXKonumu = layout.adminSidebar.getLocation();
        extentTest.info("Sidebar genislemis mi kontrol ediliyor.");
        Assert.assertTrue(hoverdanSonraSidebarXKonumu.getX() >= hoverdanOnceSidebarXKonumu.getX(), "Sidebar Genislemedi veya hareket etmedi");

        extentTest.info("Sidebarda appointments butonu gorunurlugunu kontrol ediliyor.");
        Assert.assertTrue(layout.adminSidebarAppointmentsButton.isDisplayed(),"Sidebarda appointments butonu gorunur degil.");

        extentTest.info("Sidebarda appointments butonu tiklanabilirligi kontrol ediliyor.");
        Assert.assertTrue(layout.adminSidebarAppointmentsButton.isEnabled(),"Sidebarda appintments butonu tiklanabilir degil.");

        extentTest.info("Appointments butonuna tiklaniyor.");
        layout.adminSidebarAppointmentsButton.click();

        AppointmentsPage appointmentsPage = new AppointmentsPage();
        var actualUrl = Driver.getDriver().getCurrentUrl();
        var expectedAppointmentsUrl = ConfigReader.getProperty("expectedAppointmentsUrl");
        var actualPageTitle = appointmentsPage.pageTitle.getText();

        extentTest.info("Sayfa basligi \"Appointments\" mi diye kontrol ediliyor.");
        Assert.assertEquals(actualPageTitle,"Appointments","Sayfa basligi \"Appointments\" degil. Guncel Baslik:"+actualPageTitle);

        extentTest.info("Sayfa URL "+expectedAppointmentsUrl+" mi diye kontrol ediliyor.");
        Assert.assertEquals(actualUrl,expectedAppointmentsUrl,"Sayfa URL "+ConfigReader.getProperty("expectedAppointmentsUrl")+" degil. Guncel Baslik:"+actualUrl);

        extentTest.pass("Siderbarda Appointments butonu erisilebilirlik kontrolu duzgun calisiyor.");
    }

    @Test
    public void TC02_RandevularinDogruSekildeListelendigininKontrolu(){
        extentTest = extentReports.createTest("TC02","Randevularin dogru sekilde listelendiginin kontrolu");
        this.layout = new Layout();

        extentTest.info("Sidebara hover yapiliyor.");
        ReusableMethods.hover(layout.adminSidebar);

        extentTest.info("Appointments butonuna tiklaniyor.");
        layout.adminSidebarAppointmentsButton.click();

        AppointmentsPage appointmentsPage = new AppointmentsPage();

        extentTest.info("Randevu listesinin yuklenmesi bekleniyor.");
        ReusableMethods.waitForVisibility(appointmentsPage.appointments.getFirst(), 3);

        extentTest.info("Randevu listesinin bos olup olmadigi kontrol ediliyor.");
        Assert.assertFalse(appointmentsPage.appointments.isEmpty(), "HATA: Randevu sayfasinda randevular listelenmemis.");

        extentTest.info("Ilk randevu kartindaki etiketlerin (Label) varligi kontrol ediliyor.");
        Map<String, Boolean> labels = appointmentsPage.checkLabelsInAppointment(0);

        for (Map.Entry<String, Boolean> entry : labels.entrySet()) {
            extentTest.info(entry.getKey() + " etiketi mevcut mu? : " + entry.getValue());
            Assert.assertTrue(entry.getValue(), entry.getKey() + " etiket eksik! [HATA]");
        }
        extentTest.pass("Randevularin listelenmesi ve etiket kontrolleri basarili.");
    }

    @Test(enabled = false)// !! Mobil sidebar olmadigi icin test engelleniyor bu yuzden bu test methodu devre disi
    public void TC03_MobileUIUXKontrolu() {
        extentTest = extentReports.createTest("TC03", "Mobilde UI/UX, Yatay Tasama ve Uzun Metin Kontrolu");

        extentTest.info("Test ortami mobil cozunurluge (430x932 - iPhone Pro Max) ayarlaniyor.");
        Driver.getDriver().manage().window().setSize(new Dimension(430, 932));
        this.layout = new Layout();

        extentTest.info("Mobil menude geziniliyor ve Appointments sayfasina gidiliyor.");
        Assert.assertTrue(layout.adminSidebarButton.isDisplayed(),"Admin sidebar hamburger butonu görünür değil.");
        Assert.assertTrue(layout.adminSidebarButton.isEnabled(),"Admin sidebar hamburger butonu tıklanabilir değil.");
        layout.adminSidebarButton.click();
        layout.adminSidebarAppointmentsButton.click();

        AppointmentsPage appointmentsPage = new AppointmentsPage();
        ReusableMethods.waitForVisibility(appointmentsPage.appointments.getFirst(), 5);

        extentTest.info("Javascript Executor ile sayfada yatay tasma (Horizontal Scroll) olup olmadigi kontrol ediliyor.");
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        Boolean hasHorizontalScroll = (Boolean) js.executeScript("return document.documentElement.scrollWidth > document.documentElement.clientWidth;");

        if (hasHorizontalScroll) {
            extentTest.fail("Mobil görünümde istenmeyen Yatay Scroll (Horizontal Overflow) tespit edildi!");
            Assert.fail("Sayfa mobile sığmıyor, yatay taşma var!");
        } else {
            extentTest.pass("Sayfa genişliği mobil ekrana tam oturuyor, yatay scroll yok.");
        }

        extentTest.info("Randevu kartlari taranarak uzun metinlerin UI'i bozup bozmadigi (text-overflow) kontrol ediliyor.");
        boolean uzunMetinBulundu = false;
        boolean cozumVarMi = false;

        for (int i = 0; i < appointmentsPage.appointments.size(); i++) {
            String description = appointmentsPage.getAppointmentDescription(i);

            if (description.length() > 100) {
                uzunMetinBulundu = true;
                extentTest.info("Uzun metin tespit edildi (" + description.length() + " karakter): " + description.substring(0, 20) + "...");

                WebElement descElement = appointmentsPage.appointments.get(i).findElement(By.xpath(".//div[contains(@class,'card-description')]/p"));
                String cssValues = descElement.getCssValue("text-overflow");

                if (!cssValues.equals("ellipsis")) {
                    extentTest.fail("HATA: Uzun metinler için 'Devamını Oku' veya kısaltma (ellipsis) uygulanmamış. Metin ekranı kaplıyor.");
                    Assert.fail("Uzun metin UI'ı bozuyor.");
                } else {
                    cozumVarMi = true;
                }
            }
        }

        if (!uzunMetinBulundu) {
            extentTest.info("Test verilerinde UI'ı bozacak kadar uzun metin bulunamadı. Manuel olarak uzun veri eklenip tekrar denenmeli.");
        }

        extentTest.info("Sayfa basliginin (Title) mobil ekrandaki genisligi kontrol ediliyor.");
        int titleWidth = appointmentsPage.pageTitle.getSize().getWidth();
        if (titleWidth > 350) {
            extentTest.warning("Sayfa başlığı mobil için fazla geniş veya sıkışık görünüyor.");
        }

        extentTest.pass("Mobil UI kontrolleri tamamlandı.");
    }
}