package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
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

    @BeforeClass
    public void setupTests() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Layout layout = new Layout();
        SignIn.signInAdmin();
        ReusableMethods.waitForClickability(layout.headerAuthAdminDashboardButton, Integer.parseInt(ConfigReader.getProperty("timeout"))).click();
    }

    @AfterClass
    public void teardownTests() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        SignOut.signOutAdmin();
    }

    @AfterMethod
    public void resizeScreen() {
        Driver.getDriver().manage().window().maximize();
    }

    //Yardimci methodlar
    public void yatakOlustur() {
        Layout layout = new Layout();
        CreateBedPage createBedPage = new CreateBedPage();
        WebDriverWait shortWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(1));

        ReusableMethods.hover(layout.adminSidebar);
        ReusableMethods.waitForClickability(layout.adminSidebarBedmanagersButton, 1);
        layout.adminSidebarBedmanagersButton.click();

        try {
            shortWait.until(ExpectedConditions.visibilityOf(layout.adminSidebarBedmanagersSubLinksMenu));
        } catch (TimeoutException e) {
            System.out.println("UYARI: Alt menu gecikmeli acildi veya acilamadi.");
        }

        WebElement createBedLink = layout.adminSidebarBedmanagersSubLinks.stream()
                .filter(el -> {
                    String text = el.getText().trim();
                    if (text.isEmpty()) {
                        text = el.getAttribute("textContent").trim();
                    }
                    return text.equalsIgnoreCase("Create Bed managers");
                })
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Alt menüde 'Create Bed managers' linki bulunamadı!"));

        ReusableMethods.waitForClickability(createBedLink, 5);
        createBedLink.click();

        try {
            shortWait.until(ExpectedConditions.visibilityOf(createBedPage.formInputEn));
        } catch (TimeoutException e) {
            System.out.println("HATA: Create Bed form yuklenmedi.");
            return;
        }

        createBedPage.formInputEn.sendKeys("Test Yatak Otomasyon");
        createBedPage.submitButton.click();

        try {
            AlertMessageLocators alertMsg = new AlertMessageLocators();
            shortWait.until(ExpectedConditions.visibilityOf(alertMsg.successMessage));
            System.out.println("INFO: Yatak basariyla olusturuldu.");
        } catch (TimeoutException e) {
            System.out.println("UYARI: Success message gorulmedi ama islem devam ediyor.");
        }
    }

    public void yatakSil() {
        try {
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            Layout layout = new Layout();
            WebDriverWait shortWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(1));

            ReusableMethods.waitForClickability(layout.headerAuthAdminDashboardButton, 1).click();
            bedManagersSayfasinaGit();

            BedmanagersPage bedmanagersPage = new BedmanagersPage();

            try {
                shortWait.until(ExpectedConditions.visibilityOfAllElements(
                        bedmanagersPage.tableBodyRows));
            } catch (TimeoutException e) {
                System.out.println("INFO: Silinecek yatak bulunamadi.");
                return;
            }

            if (bedmanagersPage.tableBodyRows.isEmpty()) {
                System.out.println("INFO: Tablo bos, cleanup pas geciliyor.");
                return;
            }

            WebElement deleteButton = bedmanagersPage.getTableRowMap(0).get("deleteButton");
            ReusableMethods.waitForClickability(deleteButton, 1);
            deleteButton.click();

            try {
                shortWait.until(ExpectedConditions.alertIsPresent());
                Driver.getDriver().switchTo().alert().accept();
                System.out.println("INFO: Yatak silme onayı kabul edildi.");
            } catch (TimeoutException e) {
                System.out.println("UYARI: Alert cikmadi veya otomatik kapandi.");
            }

            try {
                shortWait.until(ExpectedConditions.stalenessOf(deleteButton));
            } catch (Exception ignored) {
                // Stale kontrolü başarısız olsa bile devam et
            }

        } catch (Exception e) {
            System.out.println("HATA: yatakSil() metodu: " + e.getMessage());
        }
    }
    //Yardimci methodlar sonu

    @Test(priority = 1)
    public void TC_01_YonetimPanelindeSidebarMenuKontrolu() {
        extentTest = extentReports.createTest("TC_01", "TC01 - Yonetim Panelinde Sidebar Menu ve Alt Menulerin Kontrolu");

        // Implicit wait'i sıfırlıyoruz ki loop içinde element ararken gereksiz beklemeyelim (Explicit wait kullanacağız)
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        SoftAssert softAssert = new SoftAssert();
        Integer timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));

        Layout layout = new Layout();

        // --- ADIM 1: SIDEBAR GENİŞLEME KONTROLÜ ---
        extentTest.info("Sidebar hover (uzerine gelme) testi baslatiliyor.");
        Point hoverdanOnceSidebarXKonumu = layout.adminSidebar.getLocation();

        ReusableMethods.hover(layout.adminSidebar);
        extentTest.info("Mouse sidebar uzerine getirildi.");

        Point hoverdanSonraSidebarXKonumu = layout.adminSidebar.getLocation();

        if (hoverdanSonraSidebarXKonumu.getX() >= hoverdanOnceSidebarXKonumu.getX()) {
            extentTest.info("Sidebar basariyla genisledi/hareket etti.");
        } else {
            extentTest.warning("UYARI: Sidebar beklenen sekilde hareket etmedi!");
        }
        softAssert.assertTrue(hoverdanSonraSidebarXKonumu.getX() >= hoverdanOnceSidebarXKonumu.getX(), "Sidebar Genislemedi veya hareket etmedi");

        // --- ADIM 2: ANA MENÜLERİN GÖRÜNÜRLÜK KONTROLÜ ---
        extentTest.info("Sidebar ana menu listesinin dolulugu kontrol ediliyor.");
        softAssert.assertFalse(layout.adminSidebarAdminToolList.isEmpty(), "Sidebarda Yonetim Araclari Yok!");

        for (int i = 0; i < layout.adminSidebarAdminToolList.size(); i++) {
            WebElement toolWrapper = layout.adminSidebarAdminToolList.get(i);

            // Gizli (d-none) olan elementleri atla
            if (!toolWrapper.isDisplayed() || toolWrapper.getAttribute("class").contains("d-none")) {
                continue;
            }

            WebElement tool = layout.getTool(i);
            String toolName = "";
            try {
                toolName = tool.getText();
                extentTest.info("Ana menu kontrol ediliyor: " + toolName);

                ReusableMethods.waitForVisibility(tool, timeout);
                softAssert.assertTrue(tool.isDisplayed(), i + ". sıradaki ana menü (" + toolName + ") görünür değil.");
                ReusableMethods.waitForClickability(tool, timeout);
            } catch (TimeoutException e) {
                extentTest.fail("HATA: " + toolName + " menusu etkilesime hazir degil (Timeout).");
                softAssert.fail(i + ". siradaki eleman (" + toolName + ") etkileşime hazır değil.");
            } catch (Exception e) {
                extentTest.warning("HATA: " + toolName + " kontrol edilirken beklenmedik bir hata olustu.");
            }
        }

        // --- ADIM 3: ALT MENÜLERİN (SUB-MENU) FONKSİYONEL KONTROLÜ ---
        extentTest.info("=== Alt Menulerin (Sub-Menus) Acilma ve Tiklanabilirlik Kontrolune Geciliyor ===");

        for (int i = 0; i < layout.adminSidebarAdminToolList.size(); i++) {
            WebElement toolWrapper = layout.adminSidebarAdminToolList.get(i);

            if (!toolWrapper.isDisplayed() || toolWrapper.getAttribute("class").contains("d-none")) continue;

            List<WebElement> subList = layout.getToolSubList(i);
            // Alt menüsü olmayanları pas geç
            if (subList.isEmpty()) continue;

            WebElement tool = layout.getTool(i);
            String mainToolName = tool.getText();

            ReusableMethods.hover(layout.adminSidebar);
            ReusableMethods.waitForClickability(tool, timeout);

            try {
                extentTest.info("Alt menuleri gormek icin ana menuye tiklaniyor: " + mainToolName);
                tool.click();
            } catch (Exception e) {
                extentTest.warning("Normal tiklama basarisiz oldu, JS Click deneneniyor: " + mainToolName);
                JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
                js.executeScript("arguments[0].click();", tool);
            }

            try {
                ReusableMethods.waitForVisibility(subList.get(0), timeout);
            } catch (Exception ignored) {
                extentTest.warning("Alt menuler acilmadi veya cok gec yuklendi: " + mainToolName);
            }

            for (int j = 0; j < subList.size(); j++) {
                WebElement subItem = subList.get(j);

                String subItemText = subItem.getAttribute("innerText").trim();
                if (subItemText.isEmpty()) {
                    subItemText = subItem.getAttribute("textContent").trim();
                }

                String errorMsg = "Sidebar menü: " + mainToolName + " -> Alt link " + j + " (" + subItemText + ") görünür/tıklanabilir değil.";

                try {
                    softAssert.assertTrue(ReusableMethods.isDisplayedAndClickable(subItem, timeout), errorMsg);
                    extentTest.info("   -> Alt menü OK: " + subItemText);
                } catch (Exception e) {
                    extentTest.fail("   -> HATA: " + subItemText + " menüsüne erişilemedi!");
                    softAssert.fail(errorMsg + " [Zaman Aşımı/Etkileşim Hatası]");
                }
            }
        }

        // Test bitince implicit wait'i eski haline getiriyoruz
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout"))));

        extentTest.info("Tum kontroller tamamlandi, soft assert hatalari raporlaniyor.");
        softAssert.assertAll();
        extentTest.pass("Yonetim paneli sidebar menu ve alt menuler duzgun calisiyor.");
    }

    @Test(priority = 2)
    public void TC_02_SidebarMenudeBedManagerBasligiKontroluVeAccordionOzelligiKontrolu() {
        extentTest = extentReports.createTest("TC02", "Sidebar Menude Bed Manager Basligi ve Accordion Kontrolu");
        Layout layout = new Layout();

        extentTest.info("Sidebar uzerine geliniyor (Hover islemi).");
        ReusableMethods.hover(layout.adminSidebar);

        extentTest.info("'Bed Managers' ana basliginin gorunurlugu kontrol ediliyor.");
        Assert.assertTrue(layout.adminSidebarBedmanagersButton.isDisplayed(), "Sidebarda Bed Managers butonu görünmüyor.");

        extentTest.info("'Bed Managers' basligina tiklaniyor.");
        layout.adminSidebarBedmanagersButton.click();

        // Accordion açılırken CSS animasyonu olabilir, elementin görünür olmasını beklemek en sadesi
        extentTest.info("Alt menunun (Accordion) acilmasi bekleniyor.");
        ReusableMethods.waitForVisibility(layout.adminSidebarBedmanagersSubLinksMenu, 2);

        // Style attribute değerini loga basıyoruz ki hata olursa "display: none" mı kaldı görelim
        String menuStyle = layout.adminSidebarBedmanagersSubLinksMenu.getAttribute("style");
        extentTest.info("Menu style attribute degeri okundu: " + menuStyle);

        Assert.assertTrue(menuStyle != null && menuStyle.contains("display: block"),
                "HATA: Accordion menu açılmadı veya 'display: block' değeri almadı. Guncel Style: " + menuStyle);

        extentTest.info("Alt linke tiklanarak 'Bed Managers' listeleme sayfasina gidiliyor.");
        bedManagersSayfasinaGit();

        String expectedUrl = ConfigReader.getProperty("bedManagersUrl");
        String actualUrl = Driver.getDriver().getCurrentUrl();

        extentTest.info("URL dogrulamasi yapiliyor. Beklenen: " + expectedUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "URL uyuşmazlığı! Beklenen: " + expectedUrl + " Gelen: " + actualUrl);

        BedmanagersPage bedmanagersPage = new BedmanagersPage();

        // Tablo verilerinin veritabanından gelmesi biraz sürebilir, ilk satırı bekletiyoruz
        extentTest.info("Tablo verilerinin yuklenmesi bekleniyor.");
        try {
            if (!bedmanagersPage.tableBodyRows.isEmpty()) {
                ReusableMethods.waitForVisibility(bedmanagersPage.tableBodyRows.get(0), 5);
            }
        } catch (Exception e) {
            extentTest.warning("Tablo satirlari 5 saniye icinde gorunur olmadi, liste bos veya gec yukleniyor olabilir.");
        }

        extentTest.info("Tablonun bos olmadigi (verilerin listelendigi) dogrulaniyor.");
        Assert.assertFalse(bedmanagersPage.tableBodyRows.isEmpty(), "HATA: Bed Managers tablosu boş geldi, kayıtlar listelenmedi.");

        extentTest.pass("Bed Manager accordion menusu ve sayfa listeleme testi başarıyla tamamlandı.");
    }

    @Test(priority = 3)
    public void TC_03_BedManagersSayfasindaAramaKutusuGorunurlukVeIslevsellikKontrolu() {
        extentTest = extentReports.createTest("TC_03", "Bed managers Sayfasinda Arama Kutusu Gorunurluk Ve Islevsellik Kontrolu");

        extentTest.info("Bed Managers sayfasina gidiliyor.");
        bedManagersSayfasinaGit();

        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));

        extentTest.info("Baslangic tablo satir sayisi aliniyor.");
        int tableSize = bedmanagersPage.tableBodyRows.size();
        extentTest.info("Baslangic satir sayisi: " + tableSize);

        extentTest.info("Arama kutusunun gorunur ve aktif oldugu kontrol ediliyor.");
        Assert.assertTrue(bedmanagersPage.searchInput.isDisplayed(), "Arama kutusu gorunmuyor.");
        Assert.assertTrue(bedmanagersPage.searchInput.isEnabled(), "Arama kutusu aktif degil.");

        String arananDeger = "test".toLowerCase();
        extentTest.info("Arama kutusuna '" + arananDeger + "' degeri giriliyor.");
        bedmanagersPage.searchInput.sendKeys(arananDeger);

        extentTest.info("Sonuclarin filtrelenmesi bekleniyor.");
        wait.until(ExpectedConditions.visibilityOfAllElements(bedmanagersPage.tableBodyRows));

        extentTest.info("Listelenen sonuclarin '" + arananDeger + "' icerip icermedigi dogrulaniyor.");
        boolean yanlisVarMi = false;
        List<Integer> yanlisOlanSatirlar = new ArrayList<>();
        for (int row = 0; row < bedmanagersPage.tableBodyRows.size(); row++) {
            Map<String, WebElement> rowMap = bedmanagersPage.getTableRowMap(row);

            if (rowMap.containsKey("title") && rowMap.get("title") != null) {
                String titleText = rowMap.get("title").getText().toLowerCase();
                if (!titleText.contains(arananDeger)) {
                    yanlisVarMi = true;
                    yanlisOlanSatirlar.add(row);
                    extentTest.info("HATA: " + row + ". satirda uyumsuz icerik tespit edildi: " + titleText);
                }
            }
        }
        Assert.assertFalse(yanlisVarMi, "Aranan deger ile sonuclar tam uyusmuyor. Yanlis satirlar: " + yanlisOlanSatirlar);

        extentTest.info("Arama kutusu temizleniyor.");
        bedmanagersPage.searchInput.sendKeys(Keys.CONTROL + "a");
        bedmanagersPage.searchInput.sendKeys(Keys.BACK_SPACE);

        extentTest.info("Tablonun eski haline (tum liste) donmesi bekleniyor.");
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[@id='tableWithSearch']/tbody/tr"), tableSize));

        extentTest.info("Tablo satir sayisinin baslangic degeri (" + tableSize + ") ile esit oldugu dogrulaniyor.");
        Assert.assertEquals(bedmanagersPage.tableBodyRows.size(), tableSize, "Arama kutusu temizlenince butun yataklar listelenmedi.");

        extentTest.pass("Bed managers sayfasinda arama islevi duzgun calisiyor.");
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
        extentTest = extentReports.createTest("TC04", "Bed Manager Detay Sayfası Yönlendirme Kontrolü");

        extentTest.info("Bed Managers sayfasina gidiliyor.");
        bedManagersSayfasinaGit();

        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        SoftAssert softAssert = new SoftAssert();

        extentTest.info("Tablodaki ilk satir verileri aliniyor.");
        Map<String, WebElement> firstRow = bedmanagersPage.getTableRowMap(0);

        extentTest.info("Tablonun bos olmadigi kontrol ediliyor.");
        Assert.assertFalse(bedmanagersPage.tableBodyRows.isEmpty(), "Tabloda listelenen yatak bulunamadı.");

        if (!firstRow.containsKey("title") || firstRow.get("title") == null) {
            extentTest.fail("Ilk satirda tiklanacak bir 'title' elementi bulunamadi.");
            softAssert.fail("İlk satırda 'title' elementi bulunamadı!");
            softAssert.assertAll();
            return;
        }

        WebElement ilkYatak = firstRow.get("title");
        String yatakIsmi = ilkYatak.getText();

        extentTest.info("Tıklanacak yatak ismi tespit edildi: " + yatakIsmi);

        try {
            try {
                extentTest.info("Elemente normal tiklama yapiliyor.");
                ReusableMethods.waitForClickability(ilkYatak, 5);
                ilkYatak.click();
            } catch (Exception e) {
                extentTest.info("Normal tiklama basarisiz oldu, JS Executor ile tiklama deneniyor.");
                JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
                js.executeScript("arguments[0].click();", ilkYatak);
            }
        } catch (Exception e) {
            extentTest.fail("Tiklama islemi gerceklestirilemedi.");
            softAssert.fail("Yatak tiklanabilir degil.Tiklaninca detay sayfasina yonlendirmiyor");
        }

        String currentUrl = Driver.getDriver().getCurrentUrl();
        extentTest.info("Yonlendirme sonrasi URL kontrol ediliyor. Guncel URL: " + currentUrl);

        softAssert.assertTrue(currentUrl.contains("Details") || currentUrl.contains("show"),
                "Yatak detay sayfasına yönlendirilmedi. Mevcut URL: " + currentUrl);

        softAssert.assertAll();
        extentTest.pass("Detay sayfası yönlendirmesi başarıyla kontrol edildi.");
    }

    @Test(priority = 5)
    public void TC_05_BedManagerSayfasindaEditButonununErisilebilirligiVeEditOzelligininTesti() {
        extentTest = extentReports.createTest("TC05",
                "Bed Manager Sayfasinda Edit Butonunun Erisilebilirligi Ve Edit Ozelliginin Testi");

        extentTest.info("Pre-condition: Test icin yeni bir yatak olusturuluyor.");
        yatakOlustur();

        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();
        SoftAssert softAssert = new SoftAssert();
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebDriverWait shortWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(1));

        extentTest.info("Tablodaki ilk satirin verileri aliniyor.");
        Map<String, WebElement> firstRow = bedmanagersPage.getTableRowMap(0);

        if (!firstRow.containsKey("title") || firstRow.get("title") == null) {
            extentTest.fail("Tabloda veri bulunamadi, test durduruluyor.");
            softAssert.fail("İlk satırda 'title' bulunamadı!");
            softAssert.assertAll();
            return;
        }

        String title = firstRow.get("title").getText();
        String departments = firstRow.get("departments").getText();
        WebElement availabilityElement = firstRow.get("availability");
        String availability = availabilityElement != null ? availabilityElement.getText() : "";
        String imageSrc = firstRow.get("img").getAttribute("src");

        extentTest.info("Referans Veriler -> Title: " + title + " | Dept: " + departments +
                " | Status: " + availability);

        // === EDIT BUTTON VISIBILITY CHECK ===
        extentTest.info("Tablodaki tum Edit butonlarinin gorunurlugu kontrol ediliyor.");
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

        // === NAVIGATE TO EDIT PAGE ===
        extentTest.info("Ilk satirdaki Edit butonuna tiklaniyor.");
        try {
            bedmanagersPage.waitForRowsToLoad();
            WebElement editBtn = bedmanagersPage.getTableRowMap(0).get("editButton");
            ReusableMethods.scrollToElement(editBtn);
            js.executeScript("arguments[0].click();", editBtn);

            shortWait.until(ExpectedConditions.urlContains("edit"));
        } catch (Exception e) {
            extentTest.fail("Edit butonuna tiklanamadi veya sayfa yuklenmedi.");
            softAssert.fail("Edit sayfasina gidemedi.");
            softAssert.assertAll();
            return;
        }

        // === FORM DATA VERIFICATION ===
        extentTest.info("Edit sayfasindaki form verilerinin dogrulugu kontrol ediliyor.");
        try {
            shortWait.until(ExpectedConditions.visibilityOf(bedmanagersPage.formManagersTitleEn));
        } catch (TimeoutException e) {
            extentTest.fail("Form elementleri yuklenmedi.");
            softAssert.fail("Edit form yuklenmedi.");
            softAssert.assertAll();
            return;
        }

        softAssert.assertEquals(bedmanagersPage.formManagersTitleEn.getAttribute("value"), title,
                "Formdaki Title verisi yanlış!");

        String selectedDepartment = bedmanagersPage.formDepartmentSelectRendered.getText().trim();
        softAssert.assertEquals(selectedDepartment, departments,
                "BUG: Departman verisi yanlış yansıtılıyor!");

        boolean isAvailable = availability.equalsIgnoreCase("Active") ||
                availability.equalsIgnoreCase("on");
        softAssert.assertEquals(bedmanagersPage.formAvailabilityCheckBox.isSelected(), isAvailable,
                "Availability durumu eşleşmiyor!");

        softAssert.assertEquals(bedmanagersPage.formImageThumbnail.getAttribute("src"), imageSrc,
                "Resim kaynagi eslesmiyor!");

        // ═══════════════════════════════════════════════════════════════
        // SCENARIO 1: VALID UPDATE (Happy Path)
        // ═══════════════════════════════════════════════════════════════
        extentTest.info("--- SENARYO 1: Gecerli veri ile guncelleme ---");
        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formManagersTitleEn.sendKeys("ehehe");
        bedmanagersPage.saveButton.click();

        try {
            shortWait.until(ExpectedConditions.visibilityOf(alertMessageLocators.successMessage));
            extentTest.pass("Gecerli guncelleme basarili mesaji gosterdi.");
        } catch (TimeoutException e) {
            extentTest.fail("Gecerli veri ile kayit basarisiz oldu.");
            softAssert.fail("Valid update success message gorunmedi.");
        }

        // ═══════════════════════════════════════════════════════════════
        // SCENARIO 2: BOUNDARY TEST (1000 Characters)
        // ═══════════════════════════════════════════════════════════════
        extentTest.info("--- SENARYO 2: Sinir Deger Testi (1000 Karakter) ---");

        // Edit sayfasına geri dön
        try {
            bedmanagersPage.waitForRowsToLoad();
            js.executeScript("arguments[0].click();",
                    bedmanagersPage.getTableRowMap(0).get("editButton"));
            shortWait.until(ExpectedConditions.urlContains("edit"));
        } catch (Exception e) {
            extentTest.fail("Edit sayfasina geri donulemedi.");
            softAssert.fail("2. senaryo icin edit sayfasi acilamadi.");
        }

        Faker faker = new Faker();
        String longText = faker.lorem().characters(1000);
        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formManagersTitleEn.sendKeys(longText);
        bedmanagersPage.saveButton.click();

        try {
            shortWait.until(ExpectedConditions.visibilityOf(alertMessageLocators.errorMessage));
            extentTest.pass("1000 karakter icin hata mesaji gosterildi.");
        } catch (TimeoutException e) {
            extentTest.fail("BUG: 1000 karakterlik veri kabul edildi, hata mesaji cikmadi!");
            softAssert.fail("Boundary test failed - long text accepted.");
        }

        // ═══════════════════════════════════════════════════════════════
        // SCENARIO 3: EMPTY FIELD CHECK
        // ═══════════════════════════════════════════════════════════════
        extentTest.info("--- SENARYO 3: Bos Alan Kontrolu ---");

        try {
            bedmanagersPage.waitForRowsToLoad();
            js.executeScript("arguments[0].click();",
                    bedmanagersPage.getTableRowMap(0).get("editButton"));
            shortWait.until(ExpectedConditions.urlContains("edit"));
        } catch (Exception e) {
            extentTest.fail("Edit sayfasina geri donulemedi.");
            softAssert.fail("3. senaryo icin edit sayfasi acilamadi.");
        }

        bedmanagersPage.formManagersTitleEn.clear();

        String validationMessage = (String) js.executeScript(
                "return arguments[0].validationMessage;",
                bedmanagersPage.formManagersTitleEn
        );

        boolean hasHtml5Required = validationMessage != null && !validationMessage.isEmpty();

        if (hasHtml5Required) {
            extentTest.info("HTML5 'required' attribute mevcut, tarayici validasyonu aktif.");
            extentTest.info("Browser validation mesaji: " + validationMessage);

            // Save butonuna basıldığında browser validation devreye girer
            // Backend'e istek gitmez, dolayısıyla toaster mesajı çıkmaz
            softAssert.assertTrue(true, "HTML5 validation çalışıyor - Beklenen davranış");

        } else {
            extentTest.info("HTML5 validation YOK, backend validation kontrol ediliyor.");
            bedmanagersPage.saveButton.click();

            try {
                shortWait.until(ExpectedConditions.visibilityOf(alertMessageLocators.errorMessage));
                extentTest.pass("Backend bos alan icin hata mesaji gosterdi.");
            } catch (TimeoutException e) {
                extentTest.fail("KRITIK BUG: Ne HTML5 ne de backend validation calismadi!");
                softAssert.fail("Empty field validation COMPLETELY MISSING!");
            }
        }

        // ═══════════════════════════════════════════════════════════════
        // SCENARIO 4: FULL CLEAR & HTML5 VALIDATION
        // ═══════════════════════════════════════════════════════════════
        extentTest.info("--- SENARYO 4: Tum Alanlari Temizleme ve Detayli Validation ---");

        // Accordion'ları temizle
        try {
            bedmanagersPage.formFrenchLangAccordionButton.click();
            shortWait.until(ExpectedConditions.visibilityOf(bedmanagersPage.formFrenchLangTitle));
            bedmanagersPage.formFrenchLangTitle.clear();
            bedmanagersPage.formFrenchLangContentTextArea.clear();
        } catch (Exception ignored) {
            extentTest.info("Fransizca accordion bulunamadi veya acilamadi.");
        }

        try {
            bedmanagersPage.formArabicLangAccordionButton.click();
            shortWait.until(ExpectedConditions.visibilityOf(bedmanagersPage.formArabicLangTitle));
            bedmanagersPage.formArabicLangTitle.clear();
            bedmanagersPage.formArabicLangContentTextArea.clear();
        } catch (Exception ignored) {
            extentTest.info("Arapca accordion bulunamadi veya acilamadi.");
        }

        // Ana alanları temizle
        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formBedPriceInput.clear();

        if (bedmanagersPage.formAvailabilityCheckBox.isSelected()) {
            js.executeScript("arguments[0].click();", bedmanagersPage.formAvailabilityCheckBox);
        }

        js.executeScript("arguments[0].value = '';", bedmanagersPage.formContentTextArea);

        // Required attribute kontrolü
        String isRequired = bedmanagersPage.formManagersTitleEn.getAttribute("required");
        boolean hasRequiredAttr = (isRequired != null);

        extentTest.info("Title alaninda 'required' attribute: " + hasRequiredAttr);
        softAssert.assertTrue(hasRequiredAttr,
                "KRITIK BUG: Title alaninda HTML5 'required' attribute EKSIK!");

        // Save buton durumu
        boolean saveEnabled = bedmanagersPage.saveButton.isEnabled();
        extentTest.info("Tum alanlar bos - Save buton durumu: " +
                (saveEnabled ? "AKTIF (Beklenmeyen)" : "PASIF (Beklenen)"));

        if (hasRequiredAttr) {
            // HTML5 validation varsa, save butonu disabled olmalı VEYA tıklanınca uyarı vermeli
            if (saveEnabled) {
                bedmanagersPage.saveButton.click();

                String browserMsg = (String) js.executeScript(
                        "return arguments[0].validationMessage;",
                        bedmanagersPage.formManagersTitleEn
                );

                softAssert.assertTrue(browserMsg != null && !browserMsg.isEmpty(),
                        "Save butonu aktif ama tarayici uyari vermiyor!");
                extentTest.info("Browser validation mesaji: " + browserMsg);
            } else {
                extentTest.pass("Save butonu disabled - HTML5 validation calisiyor.");
            }
        } else {
            // HTML5 validation yoksa backend kontrolü yapmalı
            if (saveEnabled) {
                bedmanagersPage.saveButton.click();

                try {
                    shortWait.until(ExpectedConditions.visibilityOf(alertMessageLocators.errorMessage));
                    extentTest.pass("Backend validation hata mesaji gosterdi.");
                } catch (TimeoutException e) {
                    softAssert.fail("KRITIK: Tum alanlar bos ama sistem kabul etti!");
                }
            }
        }

        // Cleanup
        yatakSil();

        extentTest.info("Tum senaryolar tamamlandi, SoftAssert dogrulamasi yapiliyor.");
        softAssert.assertAll();
    }

    @Test(priority = 7)
    public void TC_06_HerYatakKartindaDeleteButonununErisilebilirligiVeDeleteOzelligiTesti() {
        extentTest = extentReports.createTest("TC06", "Her yatak kartinda delete butonunun erisilebilirligi ve delete ozelliginin testi");
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        extentTest.info("Pre-condition: Silme testi icin yeni bir yatak kaydi olusturuluyor.");
        yatakOlustur();

        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();

        // İlk satırdaki delete butonunu al
        WebElement deleteButton = bedmanagersPage.getTableRowMap(0).get("deleteButton");

        extentTest.info("Delete butonunun gorunurlugu ve aktifligi kontrol ediliyor.");
        Assert.assertTrue(deleteButton.isDisplayed(), "Delete butonu gorunur degil");
        Assert.assertTrue(deleteButton.isEnabled(), "Delete butonu erisilebilir degil");

        extentTest.info("Delete butonuna tiklaniyor.");
        deleteButton.click();

        try {
            extentTest.info("Tarayici uzerinde JS Alert (Onay Kutusu) cikmasi bekleniyor.");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(1));
            wait.until(ExpectedConditions.alertIsPresent());

            extentTest.info("Alert penceresi goruldu, 'OK' (Accept) secenegine tiklaniyor.");
            Driver.getDriver().switchTo().alert().accept();
            extentTest.pass("Onay kutusu başarıyla yönetildi.");
        } catch (Exception e) {
            extentTest.fail("HATA: Onay uyarısı çıkmadan direkt silme işlemi yapıldı veya Alert yakalanamadı!");
        }

        extentTest.info("Silme islemi sonrasi sistem bildirim mesaji (Toaster) bekleniyor.");
        ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
        Assert.assertTrue(alertMessageLocators.errorMessage.isDisplayed(), "Silme islemi sonrasi beklenen bildirim mesaji gorulmedi.");

        // --- Regression / Cache Kontrolü ---
        extentTest.info("Tarayicidan Geri (Back) tusuna basilarak sayfa davranisi ve mesaj tekrari kontrol ediliyor.");
        Driver.getDriver().navigate().back();
        ReusableMethods.waitForPageToLoad(1);

        // Sayfa yenilendiği için elementleri tazelemek gerekebilir (Stale Element riskine karşı)
        bedmanagersPage = new BedmanagersPage();
        alertMessageLocators = new AlertMessageLocators();

        try {
            // Burada mesajın GÖRÜNMEMESİNİ bekliyoruz, o yüzden try-catch mantığı ters çalışıyor
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, 1); // Kısa bir süre bekle

            // Eğer buraya düşerse mesaj görünmüş demektir -> HATA
            extentTest.fail("HATA: Geri tusuna basinca silindi bildirimi tekrar ekranda belirdi! (Cache/Session hatasi)");
        } catch (Exception e) {
            // Element bulunamazsa veya timeout yerse -> BAŞARILI (Çünkü mesaj çıkmadı)
            extentTest.pass("Geri gidince bildirim mesaji tekrar cikmadi, sayfa durumu stabil.");
        }
        yatakSil();

        int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

        extentTest.pass("Delete fonksiyonu ve Alert yonetimi basariyla test edildi.");
    }

    @Test(priority = 6)
    public void TC_07_MobilCozunurlukteUIUXKontrolu() {
        extentTest = extentReports.createTest("TC07",
                "Mobil Cozunurlukte UI/UX Kontrolu (430x932)");

        bedManagersSayfasinaGit();

        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        Layout layout = new Layout();
        SoftAssert softAssert = new SoftAssert();
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2));

        //yatak olusturalim once
        yatakOlustur();


        // 1. Mobil Çözünürlük
        extentTest.info("iPhone 14 Pro Max (430x932) cozunurlugune ayarlaniyor.");
        Driver.getDriver().manage().window().setSize(new Dimension(430, 932));

        extentTest.info("Sayfa yenileniyor (responsive layout icin).");
        Driver.getDriver().navigate().refresh();

        try {
            wait.until(ExpectedConditions.visibilityOf(layout.hamburgerMenuButton));
            softAssert.assertTrue(layout.hamburgerMenuButton.isDisplayed(),
                    "Hamburger menu gorunmuyor!");
            extentTest.info("Hamburger menu basariyla goruldu.");

            wait.until(ExpectedConditions.elementToBeClickable(layout.hamburgerMenuButton));
            layout.hamburgerMenuButton.click();
            extentTest.pass("Hamburger menu tiklandi.");

        } catch (TimeoutException e) {
            extentTest.fail("BUG: Hamburger menu YOK veya tiklanamadi!");
            softAssert.fail("Mobilde hamburger menu calısmiyor!");
        }

        // 2. Bed Managers Sayfası
        extentTest.info("Bed Managers sayfasina dogrudan gidiliyor.");
        Driver.getDriver().get(ConfigReader.getProperty("actualBedManagersUrl"));

        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(bedmanagersPage.tableBodyRows));
        } catch (TimeoutException e) {
            extentTest.fail("Mobilde tablo verisi yuklenmedi.");
            softAssert.fail("Table did not load on mobile view.");
        }

        // Görsel Boyut Kontrolü
        try {
            WebElement img = bedmanagersPage.getTableRowMap(0).get("img");
            int imgWidth = img.getSize().getWidth();
            extentTest.info("Mobilde yatak gorseli genisligi: " + imgWidth + "px");

            softAssert.assertTrue(imgWidth > 30,
                    "UI BUG: Mobilde gorsel cok kucuk! Genislik: " + imgWidth + "px");
        } catch (Exception e) {
            extentTest.warning("Gorsel boyutu alinamadi: " + e.getMessage());
        }

        // 3. Edit Butonu
        extentTest.info("Edit butonuna JS ile tiklaniyor.");
        WebElement firstEditBtn = bedmanagersPage.getTableRowMap(0).get("editButton");
        js.executeScript("arguments[0].click();", firstEditBtn);

        try {
            wait.until(ExpectedConditions.urlContains("edit"));
            extentTest.pass("Edit sayfasina yonlendirme basarili.");
        } catch (TimeoutException e) {
            extentTest.fail("Edit butonuna basinca sayfa acilmadi.");
            softAssert.fail("Edit button did not navigate on mobile.");
        }

        extentTest.info("Geri tusuna basiliyor.");
        Driver.getDriver().navigate().back();

        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(bedmanagersPage.tableBodyRows));
        } catch (TimeoutException e) {
            extentTest.warning("Geri gelirken tablo gec yuklendi.");
        }

        // 4. Delete Butonu ve Alert
        extentTest.info("Delete butonuna JS ile tiklaniyor.");
        WebElement firstDelBtn = bedmanagersPage.getTableRowMap(0).get("deleteButton");
        js.executeScript("arguments[0].click();", firstDelBtn);

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Driver.getDriver().switchTo().alert().accept();
            extentTest.pass("Delete alert mobilde basariyla goruldu ve onaylandi.");
        } catch (TimeoutException e) {
            extentTest.fail("KRITIK BUG: Mobilde delete alert CIKMADI!");
            softAssert.fail("Delete confirmation popup did NOT appear on mobile!");
        }

        extentTest.info("Mobil UI/UX testleri tamamlandi.");
        softAssert.assertAll();
    }


}