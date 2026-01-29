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

/**
 * US_003: Ana Sayfa Header (Üst Bilgi) Fonksiyonellik ve Erişilebilirlik Testleri.
 */
public class US03 extends TestBaseRapor {
    Layout layout;
    List<String> functionalErrors = new ArrayList<>();
    List<String> duplicateErrors = new ArrayList<>();

    @Test(priority = 0)
    public void TC01_HeaderGorunurlukKontrolu(){
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
    public void TC02_HeaderLogoGorunurlukVeIslevsellikKontrolu(){
        layout = new Layout();
        extentTest = extentReports.createTest("TC_02 Header Logo Görünürlük ve İşlevsellik Testi",
                "Logonun görünürlüğünü ve tıklandığında ana sayfaya yönlendirip yönlendirmediğini kontrol eder.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Test sayfası tazelendi.");

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
    public void TC03_HeaderFonksiyonelBaglantilarGorunurlukVeIslevsellikKontrolu(){
        layout = new Layout();
        SoftAssert softAssert = new SoftAssert();
        extentTest = extentReports.createTest("TC03 - Fonksiyonel Bağlantılar (Dropdown) Testi",
                "Departments, Doctors vb. menülerin alt başlıklarını ve URL uyumlarını kontrol eder.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Navigasyon menüleri kontrol ediliyor.");

        // Ana link kontrolleri
        extentTest.info("--- Ana Menü Linklerinin Kontrolü ---");

        extentTest.info("Departments ana linki kontrol ediliyor.");
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerDepartmentsLink, 3));

        extentTest.info("Doctors ana linki kontrol ediliyor.");
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerDoctorsLink, 3));

        extentTest.info("Medicines ana linki kontrol ediliyor.");
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerMedicinesLink,3));

        extentTest.info("Vaccinations ana linki kontrol ediliyor.");
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerVaccinationsLink,3));

        // Duplicate ve Tıklama kontrolleri
        extentTest.info("--- Dropdown İçerik ve URL Kontrolleri Başlıyor ---");

        extentTest.info(">>> 1. Departments Menüsü Test Ediliyor <<<");
        softAssert.assertEquals(duplicateVarMi(layout.headerDepartmentsSubLinks, layout.headerDepartmentsLink), 0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerDepartmentsSubLinks, layout.headerDepartmentsLink));

        extentTest.info(">>> 2. Doctors Menüsü Test Ediliyor <<<");
        softAssert.assertEquals(duplicateVarMi(layout.headerDoctorSubLinks, layout.headerDoctorsLink), 0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerDoctorSubLinks, layout.headerDoctorsLink));

        extentTest.info(">>> 3. Medicines Menüsü Test Ediliyor <<<");
        softAssert.assertEquals(duplicateVarMi(layout.headerMedicinesSubLinks, layout.headerMedicinesLink), 0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerMedicinesSubLinks, layout.headerMedicinesLink));

        extentTest.info(">>> 4. Vaccinations Menüsü Test Ediliyor <<<");
        softAssert.assertEquals(duplicateVarMi(layout.headerVaccinationsSubLinks, layout.headerVaccinationsLink), 0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerVaccinationsSubLinks, layout.headerVaccinationsLink));

        if (!functionalErrors.isEmpty() || !duplicateErrors.isEmpty()) {
            extentTest.fail("Fonksiyonel veya duplicate hatalar tespit edildi.");
            List<List<String>> errors = new ArrayList<>();
            errors.add(functionalErrors);
            errors.add(duplicateErrors);
            softAssert.fail("Fonksiyonel Hatalar:\n" + String.join("\n",errors.getFirst()));
            softAssert.fail("Duplicate Hatalar:\n" + String.join("\n",errors.getLast()));
            softAssert.assertAll();
        }

        softAssert.assertAll();
        extentTest.pass("Tüm dropdown menüler ve alt linkler başarıyla doğrulandı.");
    }

    @Test(priority = 3)
    public void TC04_HeaderAnaMenuBaglantilariGorunurlukVeIslevsellikKontrolu(){
        extentTest = extentReports.createTest("TC04 - Ana Menü Bağlantıları Testi",
                "Home ve About Us linklerinin temel işlevselliğini doğrular.");
        layout = new Layout();
        Driver.getDriver().get(ConfigReader.getProperty("url"));
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
        layout = new Layout();
        extentTest = extentReports.createTest("TC05 - Klavye Erişilebilirlik (TAB) Testi",
                "Header elemanlarına klavye kullanarak sırasıyla odaklanılıp odaklanılmadığını kontrol eder.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Klavye gezinme testi için sayfa yüklendi.");

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
    public int duplicateVarMi(List<WebElement> elementList, WebElement hoverTarget) {
        Set<String> set = new HashSet<>();
        int duplicateSayisi = 0;

        extentTest.info("Menü açılıyor (Hover) ve içerik taranıyor...");
        ReusableMethods.hover(hoverTarget);
        ReusableMethods.bekle(1);

        extentTest.info("Toplam " + elementList.size() + " adet alt link bulundu. Duplicate kontrolü yapılıyor.");
        for (WebElement el : elementList) {
            String text = el.getText().trim();
            if (text.isEmpty()) continue;

            if (!set.add(text)) {
                duplicateSayisi++;
                duplicateErrors.add("Duplicate Hatası: '" + text + "'");
                extentTest.warning("UYARI: Mükerrer içerik tespit edildi: " + text);
            }
        }
        return duplicateSayisi;
    }

    public boolean fonksiyonelBaglantilaraTikla(List<WebElement> elementList, WebElement hoverTarget) {
        int size = elementList.size();
        for (int i = 0; i < size; i++) {
            ReusableMethods.hover(hoverTarget);
            WebElement element = elementList.get(i);
            String text = element.getText().toLowerCase().replace("ç", "c")
                    .replaceAll("dr\\.", "")
                    .replace("ç", "c").replace("ğ", "g").replace("ı", "i")
                    .replace("ö", "o").replace("ş", "s").replace("ü", "u")
                    .trim()
                    .replace(" ", "-")
                    .replaceAll("[^a-z0-9-]", "");

            extentTest.info("[" + (i+1) + "/" + size + "] Alt linke tıklanıyor: " + element.getText());
            element.click();

            // URL Kontrolü ve geri dönme...
            String currentUrl = Driver.getDriver().getCurrentUrl();
            String normalizedUrl = currentUrl.replace("ç", "c").replace("ğ", "g").replace("ı", "i")
                    .replace("ö", "o").replace("ş", "s").replace("ü", "u")
                    .replace("dr.", "");

            if (!normalizedUrl.contains(text)){
                extentTest.fail("URL Hatası! Beklenen parça: " + text + " | Gelen URL: " + currentUrl);
                functionalErrors.add("Link Hatasi: Beklenen parça [" + text + "] URL'de yok. URL: " + currentUrl);
            } else {
                extentTest.info("URL doğrulaması başarılı: " + currentUrl);
            }

            extentTest.info("Geri dönülüyor (Navigate Back).");
            Driver.getDriver().navigate().back();
            layout = new Layout();
        }
        return functionalErrors.isEmpty();
    }
}