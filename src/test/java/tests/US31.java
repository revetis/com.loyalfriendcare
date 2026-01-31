package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.admin_pages.AdminDoctorsPage;
import pages.common_pages.AlertMessageLocators;
import pages.common_pages.Layout;
import utilities.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class US31 extends TestBaseRapor {

    Layout layout = new Layout();
    AdminDoctorsPage adminDoctorsPage = new AdminDoctorsPage();


    @Test
    public void TC01_AdminGiris_YonetimSidebarMenudeDoctorsAccess() {
        extentTest = extentReports.createTest("TC_01,TC_02",
                "Admin Login olup Yönetim Sidebar Menude Doctors ve alt menüleri görünür olmalı");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        SignIn.signInAdmin();
               extentTest.pass("✅ STEP : Admin Login giriş işlemi başarıyla gerçekleşti");

        // Admin user button bulup tıkla
        ReusableMethods.waitForClickability(layout.headerAuthAdminDashboardButton,
                Integer.parseInt(ConfigReader.getProperty("timeout")));
        layout.headerAuthAdminDashboardButton.click();
        extentTest.info("Admin user butonuna tıklandı: ");
        ReusableMethods.bekle(1);

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Dashboard") || currentUrl.contains("/admin"),
                "Admin paneline yönlendirilmedi! URL: " + currentUrl);

        ReusableMethods.hover(layout.adminSidebar);
        ReusableMethods.waitForVisibility(layout.adminSidebar, 10);
        Assert.assertTrue(adminDoctorsPage.sidebarDoctorsMainLink.isDisplayed(), "Sidebarda Menu görünmüyor.");
        extentTest.pass("Yonetim paneli sidebar menu duzgun calisiyor");

        ReusableMethods.isDisplayedAndClickable(adminDoctorsPage.sidebarDoctorsMainLink,5);
        adminDoctorsPage.sidebarDoctorsMainLink.click();

        ReusableMethods.waitForVisibility(adminDoctorsPage.sidebarDoctorsSubLink, 5);
        Assert.assertTrue(adminDoctorsPage.sidebarDoctorsSubLink.isDisplayed());

        ReusableMethods.waitForVisibility(adminDoctorsPage.sidebarCreateDoctorsSublink, 5);
        Assert.assertTrue(adminDoctorsPage.sidebarCreateDoctorsSublink.isDisplayed());

        extentTest.pass("✅ STEP PASSED: Doctors alt menüleri görünürlüğü doğrulandı");

    }

    @Test(priority = 2, dependsOnMethods = "TC01_AdminGiris_YonetimSidebarMenudeDoctorsAccess")
    public void TC02_DoctorsOpenListPageandSearch() {
        extentTest = extentReports.createTest("TC03, TC04",
                "Doktor listesinin olduğu Doctors ekranını görüntüleme + Doktor search işlemi");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));

        ReusableMethods.waitForClickability(adminDoctorsPage.sidebarDoctorsSubLink, 5);
        adminDoctorsPage.sidebarDoctorsSubLink.click();
        ReusableMethods.bekle(1);
        extentTest.info("Doctors menüsüne tıklandı.");

        // 2. ÖNEMLİ: Liste yüklenene kadar bekle ve toplam sayıyı BURADA al
        adminDoctorsPage.waitForRowsToLoad();
        int doctorsListSize = adminDoctorsPage.doctorsListRows.size();
        extentTest.info("Toplam doktor sayısı: " + doctorsListSize);

        // 3. Doctors Listenin açıldığını doğrula
        Assert.assertFalse(adminDoctorsPage.doctorsListRows.isEmpty(), "Doktor listesi boş veya yüklenemedi!");
        extentTest.pass("Doktor listesi başarıyla görüntülendi.");

        //Doctors sayfasinda Search/Arama Kutusu Gorunurluk Ve Islevsellik Kontrolu
        Assert.assertTrue(adminDoctorsPage.searchInput.isDisplayed(), "Doctor Arama kutusu gorunmuyor");
        Assert.assertTrue(adminDoctorsPage.searchInput.isEnabled(), "Arama kutusu aktif degil.");

        // Search/Arama İşlemi
        String arananDeger = "Daniel".toLowerCase();
        adminDoctorsPage.searchInput.sendKeys(arananDeger);
        ReusableMethods.bekle(2);

        // Arama sonrası sadece filtrelenmiş satırları kontrol et
        List<WebElement> filteredRows = adminDoctorsPage.doctorsListRows;

        for (WebElement row : filteredRows) {
            String rowText = row.getText().toLowerCase();
            Assert.assertTrue(rowText.contains(arananDeger),
                    "Arama sonucuyla eşleşmeyen veri bulundu: " + rowText);
        }
        extentTest.pass("Arama sonuçlarının doğruluğu onaylandı.");

        //  Arama Kutusunu Temizle ve Eski Sayıya Dönüşü Bekle
        adminDoctorsPage.searchInput.sendKeys(Keys.CONTROL + "a");
        adminDoctorsPage.searchInput.sendKeys(Keys.BACK_SPACE);
        ReusableMethods.bekle(1);

        // Burada numberOfElementsToBe kullanarak listenin eski (toplam) sayısına dönmesini bekliyoruz
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[@id='tableWithSearch']/tbody/tr"), doctorsListSize));

        Assert.assertEquals(adminDoctorsPage.doctorsListRows.size(), doctorsListSize,
                "Arama kutusu temizlenince butun doktorlar listelenmedi.");
        ReusableMethods.bekle(1);

    }
    //Burada bulunan ilk Edit butonuna başarıyla tıklanması için method tanımladık

    public void editBtnClick() {
        // 1. Önce satırların gelmesini bekleyelim (Senin yazdığın metot)
        adminDoctorsPage.waitForRowsToLoad();

        boolean clickDone = false;
        SoftAssert softAssert = new SoftAssert(); // Döngü dışında tanımladık

        for (int row = 0; row < adminDoctorsPage.doctorsListRows.size(); row++) {
            Map<String, WebElement> rowMap = adminDoctorsPage.getTableRowMap(row);

            if (rowMap.containsKey("editButton") && rowMap.get("editButton") != null) {
                WebElement editButton = rowMap.get("editButton");

                // Görünürlük ve Aktiflik Kontrolü
                softAssert.assertTrue(editButton.isDisplayed(), row + ". sıradaki edit butonu görünür değil.");
                softAssert.assertTrue(editButton.isEnabled(), row + ". sıradaki edit butonu aktif değil.");

                // Tıklanabilir olana kadar bekle ve doğrula
                boolean isClickable = ReusableMethods.isDisplayedAndClickable(editButton, 10);

                if (isClickable) {
                    editButton.click();
                    extentTest.info("Tabloda bulunan ilk aktif Edit butonuna (" + (row+1) + ". satır) başarıyla tıklandı.");
                    clickDone = true;
                    break; // TIKLAMA YAPILDI, DÖNGÜDEN ÇIK (Sayfa değiştiği için devam edemezsin)
                }
            }
        }

        Assert.assertTrue(clickDone, "Tabloda tıklanabilir bir Edit butonu bulunamadı!");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "TC02_DoctorsOpenListPageandSearch")
    public void TC03_DoctorEditValidation() {
        extentTest = extentReports.createTest("TC05 - Doktor Düzenleme(Edit) İşlemi",
                "Dinamik tablo yapısı üzerinden doktor düzenleme ve doğrulama.");

        adminDoctorsPage = new AdminDoctorsPage();

        // 1. Önce satırların yüklendiğinden emin olalım
        adminDoctorsPage.waitForRowsToLoad();

        // 2. Edit butonunu bul ve tıkla
        editBtnClick();

        // 3. Pozitif Test: Bilgi Güncelleme
        ReusableMethods.waitForVisibility(adminDoctorsPage.doctorsTitleInput, 10);
        adminDoctorsPage.doctorsTitleInput.clear();
        String updatedTitle = null;
        try {
            updatedTitle = "Dr. Dynamic " + ReusableMethods.getScreenshot("edit");
        } catch (IOException e) {
            e.printStackTrace();
        }
        adminDoctorsPage.doctorsTitleInput.sendKeys(updatedTitle);

        ReusableMethods.scrollToElement(adminDoctorsPage.saveButton);
        adminDoctorsPage.saveButton.click();
        ReusableMethods.bekle(1);

        // Kaydedildiğini doğrula
        ReusableMethods.waitForVisibility(adminDoctorsPage.successMessage, 10);
        Assert.assertTrue(adminDoctorsPage.successMessage.isDisplayed(), "Başarı mesajı görüntülenmedi.");
        extentTest.pass("Değişiklik başarıyla kaydedildi.");


        // 4. Negatif Test: Boş Bırakma (Title/Name alanı)
        // Driver.getDriver().navigate().refresh();
        editBtnClick();

        boolean doctorsTitleInputGorunurVeTiklanabilirMi = ReusableMethods.isDisplayedAndClickable(adminDoctorsPage.doctorsTitleInput, 10);
        Assert.assertTrue(doctorsTitleInputGorunurVeTiklanabilirMi);

        adminDoctorsPage.doctorsTitleInput.sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        ReusableMethods.scrollToElement(adminDoctorsPage.saveButton);
        adminDoctorsPage.saveButton.click();

        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();
        String isRequired = adminDoctorsPage.doctorsTitleInput.getAttribute("required");
        boolean hasHtmlRequired = (isRequired != null);
        Assert.assertTrue(hasHtmlRequired, "Title_en alanı HTML5 'required' özniteliğine sahip değil!");

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        if (adminDoctorsPage.saveButton.isEnabled()) {
            adminDoctorsPage.saveButton.click();

            if (hasHtmlRequired) {
                String browserMessage = (String) js.executeScript("return arguments[0].validationMessage;", adminDoctorsPage.doctorsTitleInput);

                Assert.assertTrue(browserMessage != null && !browserMessage.isEmpty(),
                        "Input 'required' olmasına rağmen tarayıcı uyarı mesajı üretmedi!");
            } else {
                try {
                    ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, 5);
                    Assert.assertTrue(alertMessageLocators.errorMessage.isDisplayed(), "Backend hata mesajı göstermedi!");
                } catch (Exception e) {
                    Assert.fail("Form gönderildi ama ne bir hata mesajı geldi ne de bir uyarı!");
                }
            }
        }


        // HTML5 validation mesajını doğrula
        String validationMsg = adminDoctorsPage.doctorsTitleInput.getAttribute("validationMessage");
        Assert.assertFalse(validationMsg.isEmpty(), "Zorunlu alan uyarısı alınamadı.");
        extentTest.pass("Zorunlu alan uyarısı doğrulandı: " + validationMsg);

        // Listeye Geri Dön

    }

    @Test(priority = 4, dependsOnMethods = "TC02_DoctorsOpenListPageandSearch")
    public void TC04_DoctorDeleteProcess() {
        extentTest = extentReports.createTest("TC06 - Doktor Silme İşlemi",
                "Dinamik tablo yapısı üzerinden silme işlemi ve listeden kalktığının doğrulanması.");

        Driver.getDriver().navigate().back();
        ReusableMethods.waitForPageToLoad(5);

        AdminDoctorsPage adminDoctorsPage = new AdminDoctorsPage();
        adminDoctorsPage.waitForRowsToLoad();

        // 1. Silinecek satırın haritasını al ve ismi kaydet
        Map<String, WebElement> rowToDelete = adminDoctorsPage.getTableRowMap(0);
        String deletedName = rowToDelete.get("title").getText();
        extentTest.info("Silinecek doktor: " + deletedName);

        // 2. Silme butonuna tıkla
        WebElement deleteBtn = rowToDelete.get("deleteButton");
        deleteBtn.click();

        // 3. Onay (Alert) İşlemi
        try {
            Driver.getDriver().switchTo().alert().accept();
            extentTest.info("Silme onayı verildi.");
        } catch (Exception e) {
            extentTest.info("Alert bulunamadı, işleme devam ediliyor.");
        }

        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();
        // 4. Başarı mesajını ve listeden yok olduğunu doğrula
        ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, 10);
        Assert.assertTrue(alertMessageLocators.errorMessage.isDisplayed());

        // Arama yaparak listede olmadığını teyit et
        adminDoctorsPage.searchInput.sendKeys(deletedName);
        ReusableMethods.bekle(1);

        // Tablodaki tüm satırları kontrol et, silinen isim olmamalı
        boolean isStillPresent = adminDoctorsPage.doctorsListRows.stream()
                .anyMatch(row -> row.getText().contains(deletedName));

        Assert.assertFalse(isStillPresent, "HATA: Silinen doktor hala listede!");
        ReusableMethods.bekle(2);
        extentTest.pass("Doktor başarıyla silindi.");
    }

}