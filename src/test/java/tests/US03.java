package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.common_pages.Layout;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.util.*;

import static java.awt.SystemColor.text;

/**
 * US_003: Ana Sayfa Header (Üst Bilgi) Fonksiyonellik ve Erişilebilirlik Testleri.
 */
public class US03 extends TestBaseRapor {
    Layout layout;
    List<String> functionalErrors = new ArrayList<>();
    List<String> duplicateErrors = new ArrayList<>();

    @Test(priority = 0)
    public void TC01_HeaderGorunurlukKontrolu() {
        layout = new Layout();
        extentTest = extentReports.createTest("TC_01 Header Görünürlük Testi",
                "Ana sayfanın en üstünde Header bölümünün varlığını doğrular.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Uygulama ana sayfası açıldı: " + ConfigReader.getProperty("url"));

        Assert.assertTrue(layout.header.isDisplayed(), "HATA: Header bölümü sayfada görüntülenemedi!");
        extentTest.info("Header (Üst Menü) bölümünün görünürlüğü fiziksel olarak doğrulandı.");
        extentTest.pass("TC_01 başarıyla tamamlandı.");
    }

    @Test(dependsOnMethods = "TC01_HeaderGorunurlukKontrolu", priority = 1)
    public void TC02_HeaderLogoGorunurlukVeIslevsellikKontrolu() {
        layout = new Layout();
        extentTest = extentReports.createTest("TC_02 Header Logo Görünürlük ve İşlevsellik Testi",
                "Logonun görünürlüğünü ve tıklandığında ana sayfaya yönlendirip yönlendirmediğini kontrol eder.");


        extentTest.info("Logonun tıklanabilirliği kontrol ediliyor.");
        Assert.assertTrue(ReusableMethods.isClickable(layout.headerLogo), "HATA: Header logosu tıklanabilir durumda değil!");

        extentTest.info("Logoya tıklanıyor.");
        layout.headerLogo.click();

        String currentUrl = Driver.getDriver().getCurrentUrl();
        extentTest.info("Yönlendirme kontrolü yapılıyor. Mevcut URL: " + currentUrl);
        Assert.assertEquals(currentUrl, ConfigReader.getProperty("url"));

        extentTest.pass("TC_02 başarıyla tamamlandı.");
    }

    @Test(priority = 2)
    public void TC03_HeaderFonksiyonelBaglantilarGorunurlukVeIslevsellikKontrolu() {
        layout = new Layout();
        SoftAssert softAssert = new SoftAssert();
        extentTest = extentReports.createTest("TC03 - Fonksiyonel Bağlantılar (Dropdown) Testi",
                "Departments, Doctors vb. menülerin alt başlıklarını ve URL uyumlarını kontrol eder.");

        extentTest.info("Navigasyon menüleri kontrol ediliyor.");

        // Ana link kontrolleri
        extentTest.info("--- Ana Menü Linklerinin Kontrolü ---");

        extentTest.info("Departments ana linki kontrol ediliyor.");
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerDepartmentsLink, 3));

        extentTest.info("Doctors ana linki kontrol ediliyor.");
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerDoctorsLink, 3));

        extentTest.info("Medicines ana linki kontrol ediliyor.");
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerMedicinesLink, 3));

        extentTest.info("Vaccinations ana linki kontrol ediliyor.");
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerVaccinationsLink, 3));

        // Duplicate ve Tıklama kontrolleri
        extentTest.info("--- Dropdown İçerik ve URL Kontrolleri Başlıyor ---");

        extentTest.info(">>> 1. Departments Menüsü Test Ediliyor <<<");
        checkDuplicateAndFunctionalError(softAssert,"Departments");

        extentTest.info(">>> 2. Doctors Menüsü Test Ediliyor <<<");
        checkDuplicateAndFunctionalError(softAssert,"Doctors");

        extentTest.info(">>> 3. Medicines Menüsü Test Ediliyor <<<");
        checkDuplicateAndFunctionalError(softAssert,"Medicines");

        extentTest.info(">>> 4. Vaccinations Menüsü Test Ediliyor <<<");
        checkDuplicateAndFunctionalError(softAssert,"Vaccinations");

        if (!functionalErrors.isEmpty() || !duplicateErrors.isEmpty()) {
            extentTest.fail("Fonksiyonel veya duplicate hatalar tespit edildi.");
            List<List<String>> errors = new ArrayList<>();
            errors.add(functionalErrors);
            errors.add(duplicateErrors);
            softAssert.fail("Fonksiyonel Hatalar:\n" + String.join("\n", errors.getFirst()));
            softAssert.fail("Duplicate Hatalar:\n" + String.join("\n", errors.getLast()));
            softAssert.assertAll();
        }

        softAssert.assertAll();
        extentTest.pass("Tüm dropdown menüler ve alt linkler başarıyla doğrulandı.");
    }
    //yukaridaki test icin yardimci method
    public void checkDuplicateAndFunctionalError(SoftAssert softAssert,String menuName){
        softAssert.assertEquals(duplicateVarMi(menuName), 0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(menuName));
    }

    @Test(priority = 3)
    public void TC04_HeaderAnaMenuBaglantilariGorunurlukVeIslevsellikKontrolu() {
        extentTest = extentReports.createTest("TC04 - Ana Menü Bağlantıları Testi",
                "Home ve About Us linklerinin temel işlevselliğini doğrular.");
        extentTest.info("Ana menü bağlantıları (Home/About Us) test ediliyor.");

        Assert.assertTrue(layout.headerHomeLink.isDisplayed());
        extentTest.info("Home linki görünür durumda.");

        extentTest.info("About Us linkine tıklanıyor.");
        layout.headerAboutUsLink.click();

        String currentUrl = Driver.getDriver().getCurrentUrl();
        String expectedUrl = ConfigReader.getProperty("aboutUsUrl");

        extentTest.info("URL doğrulaması yapılıyor. Beklenen: " + expectedUrl + " | Gelen: " + currentUrl);
        Assert.assertEquals(currentUrl, expectedUrl);

        extentTest.pass("Ana menü link geçişleri sorunsuz.");
    }

    @Test(priority = 4)
    public void TC05_HeaderTabTesti() {
        extentTest = extentReports.createTest("TC05 - Klavye Erişilebilirlik (TAB) Testi",
                "Header elemanlarına klavye kullanarak sırasıyla odaklanılıp odaklanılmadığını kontrol eder.");


        List<WebElement> headerElements = new ArrayList<>(Arrays.asList(
                layout.headerLogo, layout.headerHomeLink, layout.headerAboutUsLink,
                layout.headerDepartmentsLink, layout.headerDoctorsLink, layout.headerMedicinesLink,
                layout.headerVaccinationsLink, layout.signInLink, layout.signUpLink
        ));

        Actions actions = new Actions(Driver.getDriver());

        extentTest.info("Header elementleri üzerinde TAB tuşu ile gezilmeye başlanıyor.");
        for (WebElement element : headerElements) {
            actions.sendKeys(Keys.TAB).perform();
            WebElement focusedElement = Driver.getDriver().switchTo().activeElement();

            Assert.assertEquals(focusedElement, element, "Odak hatası! Beklenen elemente odaklanılamadı.");
            extentTest.info("TAB tuşu ile şu elemana başarıyla ulaşıldı: " + element.getText());
        }
        extentTest.pass("TAB tuşu ile header navigasyonu kusursuz çalışıyor.");
    }

    // Yardımcı metotlar...
    public int duplicateVarMi(String menuName) {
        Set<String> set = new HashSet<>();
        int duplicateSayisi = 0;

        layout = new Layout();

        WebElement hoverTarget = getAnaBaslikByIsim(menuName);

        extentTest.info(menuName + " menüsü açılıyor (Hover)...");
        ReusableMethods.hover(hoverTarget);

        List<WebElement> elementList = getListeByIsim(menuName);

        extentTest.info("Toplam " + elementList.size() + " adet alt link bulundu. Duplicate kontrolü yapılıyor.");
        for (WebElement el : elementList) {
            ReusableMethods.waitForVisibility(el,1);
            String text = el.getText().trim();
            if (text.isEmpty()) continue;

            if (!set.add(text)) {
                duplicateSayisi++;
                duplicateErrors.add(menuName + " Menüsünde Duplicate: '" + text + "'");
                extentTest.warning("UYARI: Mükerrer içerik tespit edildi: " + text);
            }
        }
        return duplicateSayisi;
    }

    public boolean fonksiyonelBaglantilaraTikla(String menuAdi) {

        layout = new Layout();
        List<WebElement> liste = getListeByIsim(menuAdi);
        int size = liste.size();

        extentTest.info(menuAdi + " menüsü altında " + size + " link test edilecek.");

        for (int i = 0; i < size; i++) {

            layout = new Layout();

            WebElement anaBaslik = getAnaBaslikByIsim(menuAdi);
            ReusableMethods.hover(anaBaslik);

            List<WebElement> tazeListe = getListeByIsim(menuAdi);
            WebElement element = tazeListe.get(i);

            String text = ReusableMethods.normalizeString(element.getText().toLowerCase());


            extentTest.info("[" + (i + 1) + "/" + size + "] Tıklanıyor: " + element.getText());
            element.click();

            String currentUrl = Driver.getDriver().getCurrentUrl();
            if (!currentUrl.toLowerCase().contains(text)) {
                extentTest.fail("URL Hatası! Beklenen: " + text + " | Gelen: " + currentUrl);
                functionalErrors.add("Hata: " + currentUrl + " -> " + text);
            } else {
                extentTest.info("URL doğrulandı.");
            }

            Driver.getDriver().navigate().back();
        }
        return functionalErrors.isEmpty();
    }

    // String isme göre ilgili WebElement listesini döndüren yardımcı metot
    public List<WebElement> getListeByIsim(String menuAdi) {
        switch (menuAdi) {
            case "Departments": return layout.headerDepartmentsSubLinks;
            case "Doctors":     return layout.headerDoctorSubLinks;
            case "Medicines":   return layout.headerMedicinesSubLinks;
            case "Vaccinations":return layout.headerVaccinationsSubLinks;
            default: return new ArrayList<>();
        }
    }

    // Aynı şekilde Hover yapacağımız Ana Başlığı da getiren metot
    public WebElement getAnaBaslikByIsim(String menuAdi) {
        switch (menuAdi) {
            case "Departments": return layout.headerDepartmentsLink;
            case "Doctors":     return layout.headerDoctorsLink;
            case "Medicines":   return layout.headerMedicinesLink;
            case "Vaccinations":return layout.headerVaccinationsLink;
            default: return null;
        }
    }


}