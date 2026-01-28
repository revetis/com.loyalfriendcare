package tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.HomepageBodyPage;
import pages.common_pages.Layout;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;
import java.util.List;
import java.util.ArrayList;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

public class US06 extends TestBaseRapor {

    @Test(priority = 0)
    public void TC01_FooterGorunurlukKontrolu() {
        Layout layout = new Layout();
        extentTest = extentReports.createTest("TC_01 Footer Görünürlük Testi",
                "Ana sayfanın en altında Footer bölümünün varlığını doğrular.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Uygulama ana sayfası açıldı.");

        // Footer ana bölüm görünürlüğü
        Assert.assertTrue(layout.footerLogo.isDisplayed() || layout.footerDescription.isDisplayed(),
                "HATA: Footer bölümü sayfada görüntülenemedi!");
        extentTest.info("Footer bölümünün görünürlüğü fiziksel olarak doğrulandı.");

        // Footer bölümleri (Departments, Social, Contacts) görünürlük kontrolleri
        Assert.assertTrue(layout.footerDepartmentsSectionHeader.isDisplayed(),
                "HATA: Footer Departments başlığı görünür değil!");
        extentTest.info("Footer Departments başlığı görünür.");

        Assert.assertTrue(layout.footerSocialMenuSectionHeader.isDisplayed(),
                "HATA: Footer Follow Us başlığı görünür değil!");
        extentTest.info("Footer Follow Us başlığı görünür.");

        Assert.assertTrue(layout.footerContactsSectionHeader.isDisplayed(),
                "HATA: Footer Contacts başlığı görünür değil!");
        extentTest.info("Footer Contacts başlığı görünür.");

        extentTest.pass("TC_01 Footer görünürlük testi başarıyla tamamlandı.");
    }

    @Test(priority = 1)
    public void TC02_FooterLinkFonksiyonellikKontrolu() {

        extentTest = extentReports.createTest(
                "TC_02 Footer Link Fonksiyonellik Testi",
                "Footer bölümündeki tüm linklerin tıklanabilir ve doğru yönlendirdiğini kontrol eder."
        );

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        extentTest.info("Uygulama ana sayfası açıldı.");

        List<String> functionalErrors = new ArrayList<>();

        Layout layout = new Layout();

        // Footer linkleri index bazlı gezeceğiz (stale riskini önlemek için)
        List<WebElement> footerLinks = new ArrayList<>();
        footerLinks.addAll(layout.footerDepartmentsSectionLinks);
        footerLinks.addAll(layout.footerContactsSectionLinks);
        footerLinks.addAll(layout.footerSocialMenuSectionLinks);
        footerLinks.add(layout.footerTermsLink);
        footerLinks.add(layout.footerFaqLink);
        footerLinks.add(layout.footerCopyrightLink);

        int totalLinks = footerLinks.size();

        for (int i = 0; i < totalLinks; i++) {

            // Sayfaya her dönüşte elementleri yeniden al
            layout = new Layout();

            footerLinks = new ArrayList<>();
            footerLinks.addAll(layout.footerDepartmentsSectionLinks);
            footerLinks.addAll(layout.footerContactsSectionLinks);
            footerLinks.addAll(layout.footerSocialMenuSectionLinks);
            footerLinks.add(layout.footerTermsLink);
            footerLinks.add(layout.footerFaqLink);
            footerLinks.add(layout.footerCopyrightLink);

            WebElement link = footerLinks.get(i);
            String linkText = link.getText();

            // 1 Tıklanabilirlik kontrolü
            Assert.assertTrue(
                    ReusableMethods.isClickable(link),
                    "HATA: Footer link tıklanabilir değil -> " + linkText
            );
            extentTest.info("Footer link tıklanabilir: " + linkText);

            // 2- href kontrolü
            String expectedUrl = link.getAttribute("href");

            if (expectedUrl == null || expectedUrl.trim().isEmpty()) {
                functionalErrors.add("Href boş veya null: " + linkText);
                extentTest.warning("Href boş/null olduğu için kontrol atlandı: " + linkText);
                continue;
            }

            // JavaScript linkleri atla
            if (expectedUrl.startsWith("javascript")) {
                extentTest.info("JavaScript link – URL kontrolü atlandı: " + linkText);
                continue;
            }

            //
            link.click();
            ReusableMethods.bekle(1);

            //
            String actualUrl = Driver.getDriver().getCurrentUrl();

            if (actualUrl == null || !actualUrl.contains(expectedUrl)) {
                functionalErrors.add(
                        "Link yönlendirme hatası: " + linkText +
                                " | Beklenen URL: " + expectedUrl +
                                " | Gerçek URL: " + actualUrl
                );
            }


            Driver.getDriver().navigate().back();
            ReusableMethods.bekle(1);
        }
    }










}
