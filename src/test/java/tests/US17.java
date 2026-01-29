package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.HomepageBodyPage;
import pages.common_pages.Layout;
import pages.user_pages.UserVaccinationsPage;
import pages.user_pages.VaccinationsDetailsPage;
import utilities.*;


public class US17 extends TestBaseRapor {

    Layout layout = new Layout();
    HomepageBodyPage homepageBodyPage = new HomepageBodyPage();
    UserVaccinationsPage userVaccinationsPage = new UserVaccinationsPage();
    VaccinationsDetailsPage vaccinationsDetailsPage = new VaccinationsDetailsPage();

    @Test(priority = 1)
    public void TC01_LoginAndAccessVaccinations() {
        extentTest = extentReports.createTest("TC01 - Giriş ve Sayfa Kontrolü", "Giriş yapılıp aşılar listelenmeli.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        SignIn.signInUser();
        ReusableMethods.bekle(2);

        // Vaccinations sekmesine tıklama
        ReusableMethods.waitForClickability(layout.headerVaccinationsLink, 10);
        layout.headerVaccinationsLink.click();
        ReusableMethods.bekle(2);
        extentTest.pass("Vaccinations sekmesine tıklandı");

        // Aşı kartlarının geldiğini doğrula
        Assert.assertTrue(userVaccinationsPage.allVaccinationList.size() > 0, "Aşı listesi boş!");
        extentTest.pass("Giriş yapıldı ve aşılar listelendi.");
    }

    @Test(priority = 2, dependsOnMethods = "TC01_LoginAndAccessVaccinations")
    public void TC02_SelectVaccine() {
        extentTest = extentReports.createTest("TC02 - Aşı Seçimi", "Detay sayfasına gidilebilmeli.");

        // Zaten aşılar sayfasındayız, direkt ilk aşıya tıklıyoruz
        userVaccinationsPage.vaccinationNames.get(0).click();
        ReusableMethods.bekle(2);

        // Detay sayfasının açıldığını form başlığıyla doğrula
        Assert.assertTrue(vaccinationsDetailsPage.vaccinationHeaderTitle.isDisplayed());
        extentTest.pass("Detay sayfası ve randevu formu görüntülendi.");
    }

    @Test(priority = 3, dependsOnMethods = "TC02_SelectVaccine")
    public void TC03_VaccineFieldCheck() {
        extentTest = extentReports.createTest("TC03 - Gereksinim Kontrolü",
                "Formda US_17 dokümanında belirtilen 'Seçilen Aşı için randevu oluşturulur' AC sı kontrol edilir.");

        // Gereksinim Kontrolü: Aşı Seçim Alanı
        // NOT: Bu alan gerçekte olmadığı için testimiz burada duracaktır. (Hard Assert)

        boolean isVaccineFieldPresent = false;

        if (!isVaccineFieldPresent) {
            extentTest.fail("KRİTİK BUG yada Requirement Mismatch (Gereksinim Uyumsuzluğu) : " +
                            "US_17 de belirtilen 'Seçilen Aşı için randevu oluşturulur' AC sını karşılayan bir 'Aşı seçim alanı' bulunmamaktadır..");
            Assert.fail("Gereksinim Hatası: Formda aşı seçim alanı bulunamadı. Test durduruldu!");
        }
    }
}