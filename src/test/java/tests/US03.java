package tests;

import org.openqa.selenium.Dimension;
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


public class US03 extends TestBaseRapor {
    Layout layout;
    List<String> functionalErrors = new ArrayList<>();

    @Test(priority = 0)
    public void TC01_HeaderGorunurlukKontrolu(){
        layout = new Layout();
        extentTest = extentReports.createTest("Header Görünürlük Testi");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Assert.assertTrue(layout.header.isDisplayed());

    }

    @Test(dependsOnMethods = "TC01_HeaderGorunurlukKontrolu", priority = 1)
    public void TC02_HeaderLogoGorunurlukVeIslevsellikKontrolu(){
        layout = new Layout();
        extentTest = extentReports.createTest("Header logo Görünürlük ve İşlevsellik Testi");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Assert.assertTrue(ReusableMethods.isClickable(layout.headerLogo),"Header Logosu Tıklanabilir Değil");
    }

    @Test(priority = 3)
    public void TC04_HeaderAnaMenuBaglantilariGorunurlukVeIslevsellikKontrolu(){
        extentTest = extentReports.createTest("Header AnaMenü Bağlantıları Görünürlük Ve İşlevsellik Kontrolü");
        layout = new Layout();
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        Assert.assertTrue(layout.headerHomeLink.isDisplayed());
        Assert.assertTrue(layout.headerAboutUsLink.isDisplayed());
        layout.headerHomeLink.click();
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(),ConfigReader.getProperty("url"));
        layout.headerAboutUsLink.click();
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(),ConfigReader.getProperty("aboutUsUrl"));

    }

    @Test(priority = 2)
    public void TC03_HeaderFonksiyonelBaglantilarGorunurlukVeIslevsellikKontrolu(){
        layout = new Layout();
        SoftAssert softAssert = new SoftAssert();
        extentTest = extentReports.createTest("Header Fonksiyonel Bağlantılar Görünürlük Ve İşlevsellik Kontrolu");
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerDepartmentsLink,3));
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerDoctorsLink,3));
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerMedicinesLink,3));
        softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(layout.headerVaccinationsLink,3));

        softAssert.assertEquals(duplicateVarMi(layout.headerDepartmentsSubLinks,layout.headerDepartmentsLink),0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerDepartmentsSubLinks,layout.headerDepartmentsLink));
        softAssert.assertEquals(duplicateVarMi(layout.headerDoctorSubLinks,layout.headerDoctorsLink),0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerDoctorSubLinks,layout.headerDoctorsLink));
        softAssert.assertEquals(duplicateVarMi(layout.headerMedicinesSubLinks,layout.headerMedicinesLink),0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerMedicinesSubLinks,layout.headerMedicinesLink));
        softAssert.assertEquals(duplicateVarMi(layout.headerVaccinationsSubLinks,layout.headerVaccinationsLink),0);
        softAssert.assertTrue(fonksiyonelBaglantilaraTikla(layout.headerVaccinationsSubLinks,layout.headerVaccinationsLink));

        if (!functionalErrors.isEmpty()) {
            Assert.fail("Test Sırasında Oluşan Hatalar:\n" + String.join("\n", functionalErrors));
        }

        softAssert.assertAll();


    }

    @Test(priority = 4)
    public void TC05_TabTusuIleErisimTesti() {
        extentTest = extentReports.createTest("TC05 - TAB Tuşu ile Gezinme Testi");
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        Actions actions = new Actions(Driver.getDriver());

        actions.sendKeys(Keys.TAB).perform();

        WebElement focusedElement = Driver.getDriver().switchTo().activeElement();
        boolean logoOdaklandiMi = focusedElement.getAttribute("alt") != null &&
                focusedElement.getAttribute("alt").equals("LoyalFriendCare");

        Assert.assertTrue(logoOdaklandiMi, "TAB tuşu ile site logosuna erişilemedi!");
        extentTest.pass("TAB tuşu ile logoya odaklanıldı.");
    }

    //Hamburger menu butonu olmadigi icin bu test disabled
//    @Test(priority = 5,enabled = false)
//    public void TC06_MobilHamburgerMenuTesti() {
//        extentTest = extentReports.createTest("TC06 - Mobil Hamburger Menü Testi");
//
//        Driver.getDriver().manage().window().setSize(new Dimension(430, 932));
//        Driver.getDriver().get(ConfigReader.getProperty("url"));
//        ReusableMethods.bekle(2);
//
//        try {
//            Assert.assertTrue(layout.hamburgerMenu.isDisplayed(), "Mobil çözünürlükte Hamburger Menü butonu görünmüyor!");
//            extentTest.pass("Hamburger menü butonu görünür durumda.");
//        } catch (Exception e) {
//            extentTest.fail("BUG: Mobil çözünürlükte Hamburger butonu bulunamadı!");
//            Assert.fail("Hamburger butonu bulunamadığı için testin geri kalanı (Sidebar vb.) yapılamıyor.");
//        }
//    }


    public int duplicateVarMi(List<WebElement> elementList,WebElement hoverTarget) {
        Set<String> set = new HashSet<>();
        int duplicateSayisi = 0;
        ReusableMethods.hover(hoverTarget);
        ReusableMethods.bekle(1);

        for (WebElement el : elementList) {
            String text = el.getText().trim();
            if (text.isEmpty()) continue;

            if (!set.add(text)) {
                duplicateSayisi++;
                functionalErrors.add("Duplicate Hatası: Menüde '" + text + "' elemanı birden fazla var!");
                extentTest.warning("Tekrar eden (duplicate) element bulundu: " + text);
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
            String expectedUrlPart = text.toLowerCase()
                    .replace(" ", "-")
                    .replace("'", "")
                    .replace("ş", "s")
                    .replace("ı", "i")
                    .replace("ç", "c")
                    .replace("ö", "o")
                    .replace("ü", "u")
                    .replace("ğ", "g");

            element.click();

            String currentUrl = Driver.getDriver().getCurrentUrl();
            if (!currentUrl.contains(expectedUrlPart)) {
                functionalErrors.add("Hata: " + expectedUrlPart + " bekleniyordu. Mevcut URL: " + currentUrl);
            }

            Driver.getDriver().navigate().back();
            layout = new Layout();
        }
        if (!functionalErrors.isEmpty()){
            return false;
        }
        return true;
    }


}
