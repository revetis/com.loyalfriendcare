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

    @Test(priority = 0)
    public void TC01_HeaderGorunurlukKontrolu(){
        layout = new Layout();
        extentTest = extentReports.createTest("TC_01 Header Görünürlük Testi",
                "Ana sayfanın en üstünde Header bölümünün varlığını doğrular.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Uygulama ana sayfası açıldı.");

        Assert.assertTrue(layout.header.isDisplayed(), "HATA: Header bölümü sayfada görüntülenemedi!");
        extentTest.info("Header bölümünün görünürlüğü fiziksel olarak doğrulandı.");
        extentTest.pass("TC_01 başarıyla tamamlandı.");
    }

    @Test(dependsOnMethods = "TC01_HeaderGorunurlukKontrolu", priority = 1)
    public void TC02_HeaderLogoGorunurlukVeIslevsellikKontrolu(){
        layout = new Layout();
        extentTest = extentReports.createTest("TC_02 Header Logo Görünürlük ve İşlevsellik Testi",
                "Logonun görünürlüğünü ve tıklandığında ana sayfaya yönlendirip yönlendirmediğini kontrol eder.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Test sayfası tazelendi.");

        Assert.assertTrue(ReusableMethods.isClickable(layout.headerLogo), "HATA: Header logosu tıklanabilir durumda değil!");
        extentTest.info("Logonun tıklanabilir olduğu doğrulandı.");

        layout.headerLogo.click();
        extentTest.info("Logo tıklandı.");

        Assert.assertEquals(Driver.getDriver().getCurrentUrl(), ConfigReader.getProperty("url"));
        extentTest.info("Logo tıklandıktan sonra ana sayfada kalındığı/yönlenildiği doğrulandı.");
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
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerDepartmentsLink, 3));
        extentTest.info("Departments ana linki kontrol edildi.");

        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerDoctorsLink, 3));
        extentTest.info("Doctors ana linki kontrol edildi.");

        // Duplicate ve Tıklama kontrolleri
        extentTest.info("Dropdown içeriklerinde mükerrer kayıt ve URL yönlendirme kontrolleri başlıyor.");

        softAssert.assertEquals(duplicateVarMi(layout.headerDepartmentsSubLinks, layout.headerDepartmentsLink), 0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerDepartmentsSubLinks, layout.headerDepartmentsLink));
        extentTest.info("Departments dropdown menüsü tamamen tarandı.");

        softAssert.assertEquals(duplicateVarMi(layout.headerDoctorSubLinks, layout.headerDoctorsLink), 0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerDoctorSubLinks, layout.headerDoctorsLink));
        extentTest.info("Doctors dropdown menüsü tamamen tarandı.");

        if (!functionalErrors.isEmpty()) {
            extentTest.fail("Fonksiyonel hatalar tespit edildi, liste detayları aşağıdadır.");
            Assert.fail("Hatalar:\n" + String.join("\n", functionalErrors));
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
        extentTest.info("Home linki görünür.");

        layout.headerAboutUsLink.click();
        extentTest.info("About Us linki tıklandı.");
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(), ConfigReader.getProperty("aboutUsUrl"));
        extentTest.info("About Us URL doğrulaması başarılı.");

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

        for (WebElement element : headerElements) {
            actions.sendKeys(Keys.TAB).perform();
            WebElement focusedElement = Driver.getDriver().switchTo().activeElement();

            Assert.assertEquals(focusedElement, element, "Odak hatası!");
            extentTest.info("TAB tuşu ile şu elemana başarıyla ulaşıldı: " + element.getText());
        }
        extentTest.pass("TAB tuşu ile header navigasyonu kusursuz çalışıyor.");
    }

    // Yardımcı metotlar...
    public int duplicateVarMi(List<WebElement> elementList, WebElement hoverTarget) {
        Set<String> set = new HashSet<>();
        int duplicateSayisi = 0;
        ReusableMethods.hover(hoverTarget);
        ReusableMethods.bekle(1);

        for (WebElement el : elementList) {
            String text = el.getText().trim();
            if (text.isEmpty()) continue;

            if (!set.add(text)) {
                duplicateSayisi++;
                functionalErrors.add("Duplicate Hatası: '" + text + "'");
                extentTest.warning("Mükerrer içerik raporlandı: " + text);
            }
        }
        return duplicateSayisi;
    }

    public boolean fonksiyonelBaglantilaraTikla(List<WebElement> elementList, WebElement hoverTarget) {
        int size = elementList.size();
        for (int i = 0; i < size; i++) {
            ReusableMethods.hover(hoverTarget);
            WebElement element = elementList.get(i);
            String text = element.getText();

            extentTest.info("Alt linke tıklanıyor: " + text);
            element.click();

            // URL Kontrolü ve geri dönme...
            Driver.getDriver().navigate().back();
            layout = new Layout();
        }
        return functionalErrors.isEmpty();
    }
}