package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.admin_pages.BedmanagersPage;
import pages.admin_pages.CreateBedPage;
import pages.common_pages.AlertMessageLocators;
import pages.common_pages.Layout;
import utilities.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class US27 extends TestBaseRapor {

    @BeforeMethod
    public void setupSteps() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Layout layout = new Layout();
        SignIn.signInAdmin();
        ReusableMethods.waitForClickability(layout.headerAuthAdminDashboardButton, Integer.parseInt(ConfigReader.getProperty("timeout"))).click();
    }

    public void yatakOlustur() {
        Layout layout = new Layout();
        ReusableMethods.hover(layout.adminSidebar);
        layout.adminSidebarBedmanagersButton.click();
        ReusableMethods.waitForVisibility(layout.adminSidebarBedmanagersSubLinksMenu, 5);
        layout.getBedManagersSubLink("Create Bed managers").click();

        ReusableMethods.waitForPageToLoad(Long.parseLong(ConfigReader.getProperty("timeout")));

        CreateBedPage createBedPage = new CreateBedPage();

        createBedPage.formInputEn.sendKeys("Test");
        createBedPage.submitButton.click();
    }

    @AfterMethod
    public void teardownTests(ITestResult result) {
        if (result.isSuccess()) {
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            SignOut.signOutAdmin();
        }
    }

    @Test(priority = 1)
    public void TC_01_YonetimPanelindeSidebarMenuKontrolu() {
        extentTest = extentReports.createTest("TC_01", "TC01 - Yonetim Panelinde Sidebar Menu Kontrolu");
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        SoftAssert softAssert = new SoftAssert();
        Integer timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));

        Layout layout = new Layout();

        Point hoverdanOnceSidebarXKonumu = layout.adminSidebar.getLocation();
        ReusableMethods.hover(layout.adminSidebar);
        Point hoverdanSonraSidebarXKonumu = layout.adminSidebar.getLocation();
        softAssert.assertTrue(hoverdanSonraSidebarXKonumu.getX() >= hoverdanOnceSidebarXKonumu.getX(), "Sidebar Genislemedi veya hareket etmedi");

        softAssert.assertFalse(layout.adminSidebarAdminToolList.isEmpty(), "Sidebarda Yonetim Araclari Yok!");

        for (int i = 0; i < layout.adminSidebarAdminToolList.size(); i++) {
            WebElement toolWrapper = layout.adminSidebarAdminToolList.get(i);

            if (!toolWrapper.isDisplayed() || toolWrapper.getAttribute("class").contains("d-none")) {
                continue;
            }

            WebElement tool = layout.getTool(i);
            try {
                ReusableMethods.waitForVisibility(tool, timeout);
                softAssert.assertTrue(tool.isDisplayed(), i + ". sıradaki ana menü görünür değil.");
                ReusableMethods.waitForClickability(tool, timeout);
            } catch (TimeoutException e) {
                softAssert.fail(i + ". siradaki eleman (" + tool.getText() + ") etkileşime hazır değil.");
            }
        }

        for (int i = 0; i < layout.adminSidebarAdminToolList.size(); i++) {
            WebElement toolWrapper = layout.adminSidebarAdminToolList.get(i);

            if (!toolWrapper.isDisplayed() || toolWrapper.getAttribute("class").contains("d-none")) continue;

            List<WebElement> subList = layout.getToolSubList(i);
            if (subList.isEmpty()) continue;

            WebElement tool = layout.getTool(i);

            ReusableMethods.hover(layout.adminSidebar);
            ReusableMethods.waitForClickability(tool, timeout);

            try {
                tool.click();
            } catch (Exception e) {
                JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
                js.executeScript("arguments[0].click();", tool);
            }

            try {
                ReusableMethods.waitForVisibility(subList.get(0), timeout);
            } catch (Exception ignored) {
            }

            for (int j = 0; j < subList.size(); j++) {
                WebElement subItem = subList.get(j);

                String subItemText = subItem.getAttribute("innerText").trim();
                if (subItemText.isEmpty()) {
                    subItemText = subItem.getAttribute("textContent").trim();
                }

                String errorMsg = "Sidebar menü: " + tool.getText() + " -> Alt link " + j + " (" + subItemText + ") görünür/tıklanabilir değil.";

                try {
                    ReusableMethods.waitForVisibility(subItem, 2);
                    softAssert.assertTrue(subItem.isDisplayed(), errorMsg);
                    ReusableMethods.waitForClickability(subItem, 2);
                } catch (Exception e) {
                    softAssert.fail(errorMsg + " [Zaman Aşımı/Etkileşim Hatası]");
                }
            }
        }

        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout"))));
        softAssert.assertAll();
        extentTest.pass("Yonetim paneli sidebar menu duzgun calisiyor");
    }

    @Test(priority = 2)
    public void TC_02_SidebarMenudeBedManagerBasligiKontroluVeAccordionOzelligiKontrolu() {
        extentTest = extentReports.createTest("TC02", "Sidebar Menude Bed Manager Basligi Kontrolu Ve Accordion Ozelligi Kontrolu");
        Layout layout = new Layout();

        ReusableMethods.hover(layout.adminSidebar);
        Assert.assertTrue(layout.adminSidebarBedmanagersButton.isDisplayed(), "Sidebarda Bed Managers butonu görünmüyor.");

        layout.adminSidebarBedmanagersButton.click();

        Assert.assertTrue(layout.adminSidebarBedmanagersSubLinksMenu.getAttribute("style").contains("display: block;"),
                "Accordion menu açılmadı.");

        bedManagersSayfasinaGit();

        String expectedUrl = ConfigReader.getProperty("bedManagersUrl");
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "URL uyuşmazlığı! Beklenen: " + expectedUrl + " Gelen: " + actualUrl);

        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        Assert.assertFalse(bedmanagersPage.tableBodyRows.isEmpty(), "Yataklar listesi boş geldi.");

        extentTest.pass("Test başarıyla tamamlandı.");
    }

    @Test(priority = 3)
    public void TC_03_BedManagersSayfasindaAramaKutusuGorunurlukVeIslevsellikKontrolu() {
        extentTest = extentReports.createTest("TC_03", "Bed managers Sayfasinda Arama Kutusu Gorunurluk Ve Islevsellik Kontrolu");
        bedManagersSayfasinaGit();

        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));

        int tableSize = bedmanagersPage.tableBodyRows.size();

        Assert.assertTrue(bedmanagersPage.searchInput.isDisplayed(), "Arama kutusu gorunmuyor.");
        Assert.assertTrue(bedmanagersPage.searchInput.isEnabled(), "Arama kutusu aktif degil.");
        String arananDeger = "test".toLowerCase();
        bedmanagersPage.searchInput.sendKeys(arananDeger);

        wait.until(ExpectedConditions.visibilityOfAllElements(bedmanagersPage.tableBodyRows));

        boolean yanlisVarMi = false;
        List<Integer> yanlisOlanSatirlar = new ArrayList<>();
        for (int row = 0; row < bedmanagersPage.tableBodyRows.size(); row++) {
            Map<String, WebElement> rowMap = bedmanagersPage.getTableRowMap(row);

            if (rowMap.containsKey("title") && rowMap.get("title") != null) {
                String titleText = rowMap.get("title").getText().toLowerCase();
                if (!titleText.contains(arananDeger)) {
                    yanlisVarMi = true;
                    yanlisOlanSatirlar.add(row);
                }
            }
        }
        Assert.assertFalse(yanlisVarMi, "Aranan deger ile sonuclar tam uyusmuyor. Yanlis satirlar: " + yanlisOlanSatirlar);

        bedmanagersPage.searchInput.sendKeys(Keys.CONTROL + "a");
        bedmanagersPage.searchInput.sendKeys(Keys.BACK_SPACE);

        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[@id='tableWithSearch']/tbody/tr"), tableSize));

        Assert.assertEquals(bedmanagersPage.tableBodyRows.size(), tableSize, "Arama kutusu temizlenince butun yataklar listelenmedi.");
    }

    private static void bedManagersSayfasinaGit() {
        Layout layout = new Layout();

        ReusableMethods.hover(layout.adminSidebar);
        ReusableMethods.waitForClickability(layout.adminSidebarBedmanagersButton, 5);
        layout.adminSidebarBedmanagersButton.click();

        WebElement subBedManager = layout.adminSidebarBedmanagersSubLinks.stream()
                .filter(el -> el.getText().equalsIgnoreCase("Bed managers"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Alt menüde 'Bed managers' bulunamadı!"));

        ReusableMethods.waitForClickability(subBedManager, 5);
        subBedManager.click();
        ReusableMethods.waitForPageToLoad(Long.parseLong(ConfigReader.getProperty("timeout")));
    }

    @Test(priority = 4)
    public void TC_04_BedManagerDetaySayfasiYonlendirmeKontrolu() {
        extentTest = extentReports.createTest("TC_04", "Bed Manager Detay Sayfası Yönlendirme Kontrolü");
        bedManagersSayfasinaGit();
        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        SoftAssert softAssert = new SoftAssert();

        Map<String, WebElement> firstRow = bedmanagersPage.getTableRowMap(0);

        Assert.assertFalse(bedmanagersPage.tableBodyRows.isEmpty(), "Tabloda listelenen yatak bulunamadı.");

        if (!firstRow.containsKey("title") || firstRow.get("title") == null) {
            softAssert.fail("İlk satırda 'title' elementi bulunamadı!");
            softAssert.assertAll();
            return;
        }

        WebElement ilkYatak = firstRow.get("title");
        String yatakIsmi = ilkYatak.getText();

        extentTest.info("Tıklanacak yatak: " + yatakIsmi);

        try {
            try {
                ReusableMethods.waitForClickability(ilkYatak, 5);
                ilkYatak.click();
            } catch (Exception e) {
                JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
                js.executeScript("arguments[0].click();", ilkYatak);
            }
        } catch (Exception e) {
            softAssert.fail("Yatak tiklanabilir degil.Tiklaninca detay sayfasina yonlendirmiyor");
        }

        String currentUrl = Driver.getDriver().getCurrentUrl();

        softAssert.assertTrue(currentUrl.contains("Details") || currentUrl.contains("show"),
                "Yatak detay sayfasına yönlendirilmedi. Mevcut URL: " + currentUrl);

        softAssert.assertAll();
        extentTest.pass("Detay sayfası yönlendirmesi başarıyla kontrol edildi.");
    }

    @Test(priority = 5)
    public void TC_05_BedManagerSayfasindaEditButonununErisilebilirligiVeEditOzelligininTesti() {
        extentTest = extentReports.createTest("TC05", "Bed Manager Sayfasinda Edit Butonunun Erisilebilirligi Ve Edit Ozelliginin Testi");
        yatakOlustur();
        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        SoftAssert softAssert = new SoftAssert();

        Map<String, WebElement> firstRow = bedmanagersPage.getTableRowMap(0);

        if (!firstRow.containsKey("title") || firstRow.get("title") == null) {
            softAssert.fail("İlk satırda 'title' bulunamadı!");
            return;
        }

        String title = bedmanagersPage.getTableRowMap(0).get("title").getText();
        String departments = bedmanagersPage.getTableRowMap(0).get("departments").getText();
        WebElement availabilityElement = bedmanagersPage.getTableRowMap(0).get("availability");
        String availability = availabilityElement != null ? availabilityElement.getText() : "";
        String imageSrc = bedmanagersPage.getTableRowMap(0).get("img").getAttribute("src");

        for (int row = 0; row < bedmanagersPage.tableBodyRows.size(); row++) {
            Map<String, WebElement> rowMap = bedmanagersPage.getTableRowMap(row);

            if (rowMap.containsKey("editButton") && rowMap.get("editButton") != null) {
                WebElement editButton = rowMap.get("editButton");
                softAssert.assertTrue(editButton.isDisplayed(),
                        row + ". sirada ki edit butonu gorunur degil.");
                softAssert.assertTrue(editButton.isEnabled(),
                        row + ". sirada ki edit butonu aktif degil.");
            }
        }
        try {
            bedmanagersPage.waitForRowsToLoad();
            bedmanagersPage.getTableRowMap(0).get("editButton").click();
        } catch (Exception e) {
            e.printStackTrace();
            softAssert.fail("Ilk sirada ki yatagin edit butonuna tiklayamadim.");
        }

        softAssert.assertTrue(Driver.getDriver().getCurrentUrl().contains("edit"));
        softAssert.assertEquals(bedmanagersPage.formManagersTitleEn.getAttribute("value"), title);
        String selectedDepartment = bedmanagersPage.formDepartmentSelectRendered.getText().trim();
        softAssert.assertEquals(selectedDepartment, departments, "BUG: Departman verisi yanlış yansıtılıyor!");
        boolean isAvailable = availability.equalsIgnoreCase("Active") ||
                availability.equalsIgnoreCase("on");
        softAssert.assertEquals(bedmanagersPage.formAvailabilityCheckBox.isSelected(), isAvailable, "Availability durumu eşleşmiyor!");
        softAssert.assertEquals(bedmanagersPage.formImageThumbnail.getAttribute("src"), imageSrc);

        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formManagersTitleEn.sendKeys("ehehe");

        bedmanagersPage.saveButton.click();
        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();
        try {
            ReusableMethods.waitForVisibility(alertMessageLocators.successMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
        } catch (Exception e) {
            softAssert.fail("Gecerli deger girince basarili mesaji gosterilmedi.");

        }



        try {
            bedmanagersPage.waitForRowsToLoad();
            bedmanagersPage.getTableRowMap(0).get("editButton").click();
        } catch (Exception e) {
            e.printStackTrace();
            softAssert.fail("Ilk sirada ki yatagin edit butonuna tiklayamadim.");
        }

        Faker faker = new Faker();
        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formManagersTitleEn.sendKeys(faker.lorem().characters(1000));
        bedmanagersPage.saveButton.click();
        alertMessageLocators = new AlertMessageLocators();
        try {
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
        } catch (Exception e) {
            softAssert.fail("Gecersiz deger girince basarisiz mesaji gosterilmedi.");
        }

        try {
            bedmanagersPage.waitForRowsToLoad();
            bedmanagersPage.getTableRowMap(0).get("editButton").click();
        } catch (Exception e) {
            e.printStackTrace();
            softAssert.fail("Ilk sirada ki yatagin edit butonuna tiklayamadim.");
        }

        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.saveButton.click();
        alertMessageLocators = new AlertMessageLocators();
        try {
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
        } catch (Exception e) {
            softAssert.fail("Bos mesaj girince basarisiz mesaji gosterilmedi.");
        }

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

        try {
            bedmanagersPage.formFrenchLangAccordionButton.click();
            ReusableMethods.waitForVisibility(bedmanagersPage.formFrenchLangTitle, 2);
            bedmanagersPage.formFrenchLangTitle.clear();
            bedmanagersPage.formFrenchLangContentTextArea.clear();
        } catch (Exception ignored) {}

        try {
            bedmanagersPage.formArabicLangAccordionButton.click();
            ReusableMethods.waitForVisibility(bedmanagersPage.formArabicLangTitle, 2);
            bedmanagersPage.formArabicLangTitle.clear();
            bedmanagersPage.formArabicLangContentTextArea.clear();
        } catch (Exception ignored) {}

        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formBedPriceInput.clear();
        if (bedmanagersPage.formAvailabilityCheckBox.isSelected()) {
            try {
                js.executeScript("arguments[0].click();", bedmanagersPage.formAvailabilityCheckBox);
            } catch (Exception e) {
                System.out.println("Checkbox tıklanamadı, JS denendi.");
            }
        }

        js.executeScript("arguments[0].value = '';", bedmanagersPage.formContentTextArea);


        softAssert.assertFalse(bedmanagersPage.saveButton.isEnabled(), "Tüm alanlar boşken save butonu hala aktif!");

        String isRequired = bedmanagersPage.formManagersTitleEn.getAttribute("required");
        boolean hasHtmlRequired = (isRequired != null);
        softAssert.assertTrue(hasHtmlRequired, "Title_en alanı HTML5 'required' özniteliğine sahip değil!");

        if (bedmanagersPage.saveButton.isEnabled()) {
            bedmanagersPage.saveButton.click();

            if (hasHtmlRequired) {
                String browserMessage = (String) js.executeScript("return arguments[0].validationMessage;", bedmanagersPage.formManagersTitleEn);

                softAssert.assertTrue(browserMessage != null && !browserMessage.isEmpty(),
                        "Input 'required' olmasına rağmen tarayıcı uyarı mesajı üretmedi!");
            } else {
                try {
                    ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, 5);
                    softAssert.assertTrue(alertMessageLocators.errorMessage.isDisplayed(), "Backend hata mesajı göstermedi!");
                } catch (Exception e) {
                    softAssert.fail("Form gönderildi ama ne bir hata mesajı geldi ne de bir uyarı!");
                }
            }
        }

        softAssert.assertAll();


    }

    @Test(priority = 7)
    public void TC_06_HerYatakKartindaDeleteButonununErisilebilirligiVeDeleteOzelligiTesti() {
        extentTest = extentReports.createTest("TC06", "Her yatak kartinda delete butonunun erisilebilirligi ve delete ozelliginin testi");
        yatakOlustur();
        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();

        WebElement deleteButton = bedmanagersPage.getTableRowMap(0).get("deleteButton");
        Assert.assertTrue(deleteButton.isDisplayed(), "Delete butonu gorunur degil");
        Assert.assertTrue(deleteButton.isEnabled(), "Delete butonu erisilebilir degil");

        deleteButton.click();

        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Driver.getDriver().switchTo().alert().accept();
            extentTest.pass("Onay kutusu görüntülendi ve onaylandı.");
        } catch (Exception e) {
            extentTest.fail("HATA: Onay uyarısı çıkmadan direkt silindi veya alert bulunamadı!");
        }

        ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
        Assert.assertTrue(alertMessageLocators.errorMessage.isDisplayed(), "(Uyari) Basari mesaji gorulmedi.");

        Driver.getDriver().navigate().back();
        ReusableMethods.waitForPageToLoad(2);
        bedmanagersPage = new BedmanagersPage();
        alertMessageLocators = new AlertMessageLocators();

        try {
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
            extentTest.fail("HATA: Geri gidince silindi bildirimi tekrar cikti!");
        } catch (Exception e) {
            extentTest.pass("Geri gidince tekrar bildirim cikmadi.");
        }
    }

    @Test(priority = 6)
    public void TC_07_MobilCozunurlukteUIUXKontrolu() {
        extentTest = extentReports.createTest("TC07", "Mobil Cozunurlukte UI/UX Kontrolu (430x932)");
        bedManagersSayfasinaGit();
        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        Layout layout = new Layout();
        SoftAssert softAssert = new SoftAssert();
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

        Dimension mobileSize = new Dimension(430, 932);
        Driver.getDriver().manage().window().setSize(mobileSize);
        extentTest.info("Tarayıcı mobil çözünürlüğe (430x932) ayarlandı.");

        Driver.getDriver().navigate().refresh();
        wait.until(ExpectedConditions.visibilityOf(layout.hamburgerMenuButton));

        softAssert.assertTrue(layout.hamburgerMenuButton.isDisplayed(), "Hamburger menu butonu görünür değil!");

        try {
            ReusableMethods.waitForClickability(layout.hamburgerMenuButton, 3);
            layout.hamburgerMenuButton.click();
            extentTest.pass("Hamburger menüye tıklandı.");
        } catch (Exception e) {
            softAssert.fail("BUG: Hamburger menü görünüyor ama TIKLANABİLİR DEĞİL! (Sidebar açılamıyor)");
        }

        Driver.getDriver().get(ConfigReader.getProperty("actualBedManagersUrl"));
        wait.until(ExpectedConditions.visibilityOfAllElements(bedmanagersPage.tableBodyRows));

        int imgWidth = bedmanagersPage.getTableRowMap(0).get("img").getSize().getWidth();
        softAssert.assertTrue(imgWidth > 30, "UI BUG: Mobilde yatak fotoğrafı çok küçük (Genişlik: " + imgWidth + "px)");

        WebElement firstEditButton = bedmanagersPage.getTableRowMap(0).get("editButton");
        js.executeScript("arguments[0].click();", firstEditButton);
        wait.until(ExpectedConditions.urlContains("edit"));

        Driver.getDriver().navigate().back();
        wait.until(ExpectedConditions.visibilityOfAllElements(bedmanagersPage.tableBodyRows));

        WebElement firstDeleteButton = bedmanagersPage.getTableRowMap(0).get("deleteButton");
        js.executeScript("arguments[0].click();", firstDeleteButton);

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Driver.getDriver().switchTo().alert().accept();
            extentTest.pass("Delete pop-up'ı mobilde görüntülendi.");
        } catch (Exception e) {
            softAssert.fail("BUG: Mobilde Delete butonuna basınca 'Emin misiniz?' pop-up'ı ÇIKMADI, direkt sildi!");
        }

        Driver.getDriver().manage().window().maximize();

        softAssert.assertAll();
    }
}