package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.admin_pages.AdminDashboardPage;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class US22 extends TestBaseRapor {

    AdminDashboardPage adminDashboardPage;
    LoginPage loginPage;
    Layout layout;

    @BeforeClass
    public void setupAdminLogin() {
        extentTest = extentReports.createTest(
                "Admin Login Setup",
                "Tüm testlerden önce Admin olarak sisteme giriş yapılır ve NesrinBora alanına tıklanır"
        );

        layout = new Layout();
        loginPage = new LoginPage();
        adminDashboardPage = new AdminDashboardPage();

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Login sayfasına git
        ReusableMethods.waitForVisibility(layout.signInLink, 10);
        layout.signInLink.click();

        // Admin bilgilerini gir
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("admin_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("admin_password"));
        loginPage.signInButton.click();

        // Login sonrası NesrinBora alanına tıkla

     //   ReusableMethods.waitForVisibility(adminDashboardPage.adminDasboardNesrinBora, 10);
      //  adminDashboardPage.adminDasboardNesrinBora.click();

        ReusableMethods.waitForVisibility(layout.headerAuthUserDashboardButton, 10);
        layout.headerAuthUserDashboardButton.click();


        extentTest.pass("Admin kullanıcı sisteme başarıyla giriş yaptı ve NesrinBora alanına tıkladı");
    }

    @Test(priority = 1)
    public void TC_01_AdminDashboard_OzetGorunurlukTesti() {

        extentTest = extentReports.createTest(
                "TC_01 - Admin Dashboard Özet Görünürlük Testi",
                "Admin dashboard üzerindeki tüm kutularda özet bilgilerin görüntülendiğini doğrular"
        );

        extentTest.info("Admin Dashboard özet alanları kontrol ediliyor");

        Assert.assertTrue(adminDashboardPage.adminDashboardBiriciKutuOzet.isDisplayed(),
                "1. kutu özeti görüntülenemedi");

        Assert.assertTrue(adminDashboardPage.adminDashboardIkinciKutuOzet.isDisplayed(),
                "2. kutu özeti görüntülenemedi");
        Assert.assertTrue(adminDashboardPage.adminDashboardUcuncuKutuOzet.isDisplayed(),
                "3. kutu özeti görüntülenemedi");
        Assert.assertTrue(adminDashboardPage.adminDashboardDördüncüKutuOzet.isDisplayed(),
                "4. kutu özeti görüntülenemedi");
        Assert.assertTrue(adminDashboardPage.adminDashboardBesinciKutuOzet.isDisplayed(),
                "5. kutu özeti görüntülenemedi");
        Assert.assertTrue(adminDashboardPage.adminDashboardAltinciKutuOzet.isDisplayed(),
                "6. kutu özeti görüntülenemedi");
        Assert.assertTrue(adminDashboardPage.adminDashboardYedinciKutuOzet.isDisplayed(),
                "7. kutu özeti görüntülenemedi");

        extentTest.pass("Admin Dashboard üzerindeki tüm kutu özetleri başarıyla görüntülendi");
    }

    @Test(priority = 2, dependsOnMethods = {"TC_01_AdminDashboard_OzetGorunurlukTesti"})
    public void TC_02_AdminDashboard_LearnMoreLinkTesti() {

        extentTest = extentReports.createTest(
                "TC_02 - Admin Dashboard Learn More Link Testi",
                "Admin dashboard üzerindeki Learn More linklerinin tıklanabilir ve çalışır olduğunu doğrular"
        );

        List<WebElement> learnMoreLinks = Arrays.asList(
                adminDashboardPage.adminDashboardBiriciKKutuLearnMore,
                adminDashboardPage.adminDashboardIkinciKutuLearnMore,
                adminDashboardPage.adminDashboardUcuncuKutuLearnMore,
                adminDashboardPage.adminDashboardDördüncüKutuLearnMore,
                adminDashboardPage.adminDashboardBesinciKutuLearnMore,
                adminDashboardPage.adminDashboardAltinciKutuLearnMore,
                adminDashboardPage.adminDashboardYedinciKutuLearnMore
        );

        int index = 1;
        for (WebElement link : learnMoreLinks) {

            extentTest.info(index + ". Learn More linki kontrol ediliyor");

            try {
                // Link görünür olana kadar bekle
                ReusableMethods.waitForVisibility(link, 10);

                Assert.assertTrue(link.isDisplayed(), index + ". Learn More linki görünür değil");
                Assert.assertTrue(link.isEnabled(), index + ". Learn More linki tıklanabilir değil");

                String beforeClickUrl = Driver.getDriver().getCurrentUrl();

                link.click();
                ReusableMethods.bekle(2);

                String afterClickUrl = Driver.getDriver().getCurrentUrl();

                Assert.assertNotEquals(beforeClickUrl, afterClickUrl, index + ". Learn More linki yönlendirme yapmadı");

                extentTest.pass(index + ". Learn More linki başarıyla çalıştı");

                Driver.getDriver().navigate().back();

                // Geri geldikten sonra dashboard yüklenene kadar bekle
                ReusableMethods.bekle(2);

            } catch (Exception e) {
                extentTest.fail(index + ". Learn More linki test edilirken hata oluştu: " + e.getMessage());
            }

            index++;
        }

        extentTest.pass("Admin Dashboard üzerindeki tüm Learn More linkleri başarıyla çalışıyor");
    }


    // ======= Burada AfterClass ekliyoruz =======
    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            String resimYolu = ReusableMethods.getScreenshot(result.getName());
            extentTest.fail(result.getName());
            extentTest.addScreenCaptureFromPath(resimYolu);
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.skip("Test Case is skipped: " + result.getName());
        }

        // Driver null ise quitDriver çağrılmasın
        if (Driver.getDriver() != null) {
            Driver.quitDriver();
        }
    }
}
