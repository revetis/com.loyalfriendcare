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

        // 1. Sidebar'a Hover yap
        ReusableMethods.hover(layout.adminSidebar);

        // 2. Ana menüye tıkla
        ReusableMethods.waitForClickability(layout.adminSidebarBedmanagersButton, 5);
        layout.adminSidebarBedmanagersButton.click();

        // 3. Alt menünün açılmasını bekle
        ReusableMethods.waitForVisibility(layout.adminSidebarBedmanagersSubLinksMenu, 5);

        // 4. "Create Bed managers" linkini listeden bul
        WebElement createBedLink = layout.adminSidebarBedmanagersSubLinks.stream()
                .filter(el -> {
                    // Metni al, görünmüyorsa textContent'ten al, sonra kenar boşluklarını sil
                    String text = el.getText().trim();
                    if (text.isEmpty()) {
                        text = el.getAttribute("textContent").trim();
                    }
                    return text.equalsIgnoreCase("Create Bed managers");
                })
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Alt menüde 'Create Bed managers' linki bulunamadı! Mevcut linkler yüklenmemiş veya isim yanlış."));

        ReusableMethods.waitForClickability(createBedLink, 5);
        createBedLink.click();

        ReusableMethods.waitForPageToLoad(Long.parseLong(ConfigReader.getProperty("timeout")));

        CreateBedPage createBedPage = new CreateBedPage();
        createBedPage.formInputEn.sendKeys("Test Yatak Otomasyon");
        createBedPage.submitButton.click();

        ReusableMethods.waitForPageToLoad(2);
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
                    ReusableMethods.waitForVisibility(subItem, 2);
                    softAssert.assertTrue(subItem.isDisplayed(), errorMsg);
                    ReusableMethods.waitForClickability(subItem, 2);
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
            if(!bedmanagersPage.tableBodyRows.isEmpty()){
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
        extentTest = extentReports.createTest("TC05", "Bed Manager Sayfasinda Edit Butonunun Erisilebilirligi Ve Edit Ozelliginin Testi");

        extentTest.info("Pre-condition: Test icin yeni bir yatak olusturuluyor.");
        yatakOlustur();

        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        SoftAssert softAssert = new SoftAssert();

        extentTest.info("Tablodaki ilk satirin verileri (Title, Dept, Status, Img) aliniyor.");
        Map<String, WebElement> firstRow = bedmanagersPage.getTableRowMap(0);

        if (!firstRow.containsKey("title") || firstRow.get("title") == null) {
            extentTest.fail("Tabloda veri bulunamadi, test durduruluyor.");
            softAssert.fail("İlk satırda 'title' bulunamadı!");
            return;
        }

        String title = bedmanagersPage.getTableRowMap(0).get("title").getText();
        String departments = bedmanagersPage.getTableRowMap(0).get("departments").getText();
        WebElement availabilityElement = bedmanagersPage.getTableRowMap(0).get("availability");
        String availability = availabilityElement != null ? availabilityElement.getText() : "";
        String imageSrc = bedmanagersPage.getTableRowMap(0).get("img").getAttribute("src");

        extentTest.info("Referans Veriler -> Title: " + title + " | Dept: " + departments + " | Status: " + availability);

        extentTest.info("Tablodaki tum Edit butonlarinin gorunurlugu ve aktifligi kontrol ediliyor.");
        for (int row = 0; row < bedmanagersPage.tableBodyRows.size(); row++) {
            Map<String, WebElement> rowMap = bedmanagersPage.getTableRowMap(row);

            if (rowMap.containsKey("editButton") && rowMap.get("editButton") != null) {
                WebElement editButton = rowMap.get("editButton");
                softAssert.assertTrue(editButton.isDisplayed(), row + ". sirada ki edit butonu gorunur degil.");
                softAssert.assertTrue(editButton.isEnabled(), row + ". sirada ki edit butonu aktif degil.");
            }
        }

        extentTest.info("Ilk satirdaki Edit butonuna tiklaniyor.");
        try {
            bedmanagersPage.waitForRowsToLoad();
            bedmanagersPage.getTableRowMap(0).get("editButton").click();
        } catch (Exception e) {
            e.printStackTrace();
            extentTest.fail("Edit butonuna tiklanamadi.");
            softAssert.fail("Ilk sirada ki yatagin edit butonuna tiklayamadim.");
        }

        extentTest.info("Edit sayfasina yonlendirme ve form verilerinin dogrulugu kontrol ediliyor.");
        softAssert.assertTrue(Driver.getDriver().getCurrentUrl().contains("edit"));

        // Form verileri kontrolü
        softAssert.assertEquals(bedmanagersPage.formManagersTitleEn.getAttribute("value"), title, "Formdaki Title verisi yanlış!");
        String selectedDepartment = bedmanagersPage.formDepartmentSelectRendered.getText().trim();
        softAssert.assertEquals(selectedDepartment, departments, "BUG: Departman verisi yanlış yansıtılıyor!");

        boolean isAvailable = availability.equalsIgnoreCase("Active") || availability.equalsIgnoreCase("on");
        softAssert.assertEquals(bedmanagersPage.formAvailabilityCheckBox.isSelected(), isAvailable, "Availability durumu eşleşmiyor!");
        softAssert.assertEquals(bedmanagersPage.formImageThumbnail.getAttribute("src"), imageSrc, "Resim kaynagi eslesmiyor!");

        // --- SCENARIO 1: VALID UPDATE ---
        extentTest.info("--- SENARYO 1: Gecerli veri ile guncelleme (Happy Path) ---");
        extentTest.info("Title alanina 'ehehe' yaziliyor ve kaydediliyor.");
        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formManagersTitleEn.sendKeys("ehehe");

        bedmanagersPage.saveButton.click();
        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();
        try {
            ReusableMethods.waitForVisibility(alertMessageLocators.successMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
            extentTest.info("Basarili guncelleme mesaji goruldu.");
        } catch (Exception e) {
            extentTest.fail("Gecerli veri girilmesine ragmen basarili mesaji cikmadi.");
            softAssert.fail("Gecerli deger girince basarili mesaji gosterilmedi.");
        }


        // --- SCENARIO 2: BOUNDARY TESTING (LONG STRING) ---
        extentTest.info("--- SENARYO 2: Sinir Deger Testi (1000 Karakter) ---");
        extentTest.info("Edit sayfasina tekrar gidiliyor.");
        try {
            bedmanagersPage.waitForRowsToLoad();
            bedmanagersPage.getTableRowMap(0).get("editButton").click();
        } catch (Exception e) {
            e.printStackTrace();
            softAssert.fail("Ilk sirada ki yatagin edit butonuna tiklayamadim.");
        }

        Faker faker = new Faker();
        extentTest.info("Title alanina 1000 karakterlik veri giriliyor.");
        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formManagersTitleEn.sendKeys(faker.lorem().characters(1000));
        bedmanagersPage.saveButton.click();

        alertMessageLocators = new AlertMessageLocators();
        try {
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
            extentTest.info("Beklenen hata mesaji (Error Message) goruldu.");
        } catch (Exception e) {
            extentTest.fail("1000 karakter girilmesine ragmen hata mesaji verilmedi! Sistem bunu kabul etmis olabilir.");
            softAssert.fail("Gecersiz deger girince basarisiz mesaji gosterilmedi.");
        }


        // --- SCENARIO 3: EMPTY FIELD CHECK ---
        extentTest.info("--- SENARYO 3: Bos Alan Kontrolu (Empty Title) ---");
        extentTest.info("Edit sayfasina tekrar gidiliyor.");
        try {
            bedmanagersPage.waitForRowsToLoad();
            bedmanagersPage.getTableRowMap(0).get("editButton").click();
        } catch (Exception e) {
            e.printStackTrace();
            softAssert.fail("Ilk sirada ki yatagin edit butonuna tiklayamadim.");
        }

        extentTest.info("Title alani temizlenip bos gonderiliyor.");
        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.saveButton.click();

        alertMessageLocators = new AlertMessageLocators();
        try {
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, Integer.parseInt(ConfigReader.getProperty("timeout")));
            extentTest.info("Bos title icin hata mesaji goruldu.");
        } catch (Exception e) {
            extentTest.fail("Bos title ile gonderilmesine ragmen hata mesaji cikmadi.");
            softAssert.fail("Bos mesaj girince basarisiz mesaji gosterilmedi.");
        }


        // --- SCENARIO 4: HTML5 VALIDATION & FULL CLEAR ---
        extentTest.info("--- SENARYO 4: Tum Alanlari Temizleme ve HTML5 Validasyon Kontrolu ---");
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

        try {
            extentTest.info("Fransizca accordion menusu varsa temizleniyor.");
            bedmanagersPage.formFrenchLangAccordionButton.click();
            ReusableMethods.waitForVisibility(bedmanagersPage.formFrenchLangTitle, 2);
            bedmanagersPage.formFrenchLangTitle.clear();
            bedmanagersPage.formFrenchLangContentTextArea.clear();
        } catch (Exception ignored) {
            extentTest.info("Fransizca menusu bulunamadi veya acilmadi, geciliyor.");
        }

        try {
            extentTest.info("Arapca accordion menusu varsa temizleniyor.");
            bedmanagersPage.formArabicLangAccordionButton.click();
            ReusableMethods.waitForVisibility(bedmanagersPage.formArabicLangTitle, 2);
            bedmanagersPage.formArabicLangTitle.clear();
            bedmanagersPage.formArabicLangContentTextArea.clear();
        } catch (Exception ignored) {
            extentTest.info("Arapca menusu bulunamadi veya acilmadi, geciliyor.");
        }

        extentTest.info("Ana alanlar (Title, Price, Checkbox, Content) temizleniyor.");
        bedmanagersPage.formManagersTitleEn.clear();
        bedmanagersPage.formBedPriceInput.clear();

        if (bedmanagersPage.formAvailabilityCheckBox.isSelected()) {
            try {
                js.executeScript("arguments[0].click();", bedmanagersPage.formAvailabilityCheckBox);
            } catch (Exception e) {
                System.out.println("Checkbox tıklanamadı, JS denendi.");
            }
        }

        // TextArea bazen clear() ile temizlenmez, JS ile value'su siliniyor
        js.executeScript("arguments[0].value = '';", bedmanagersPage.formContentTextArea);


        extentTest.info("Save butonunun aktiflik durumu ve 'required' attribute kontrolu yapiliyor.");
        softAssert.assertFalse(bedmanagersPage.saveButton.isEnabled(), "Tüm alanlar boşken save butonu hala aktif!");

        String isRequired = bedmanagersPage.formManagersTitleEn.getAttribute("required");
        boolean hasHtmlRequired = (isRequired != null);

        if(hasHtmlRequired) {
            extentTest.info("Title alaninda HTML5 'required' etiketi mevcut.");
        } else {
            extentTest.warning("UYARI: Title alaninda HTML5 'required' etiketi YOK!");
        }
        softAssert.assertTrue(hasHtmlRequired, "Title_en alanı HTML5 'required' özniteliğine sahip değil!");

        if (bedmanagersPage.saveButton.isEnabled()) {
            extentTest.info("Save butonu aktif oldugu icin tiklaniyor ve tarayici uyarisina bakiliyor.");
            bedmanagersPage.saveButton.click();

            if (hasHtmlRequired) {
                String browserMessage = (String) js.executeScript("return arguments[0].validationMessage;", bedmanagersPage.formManagersTitleEn);
                extentTest.info("Tarayici validasyon mesaji: " + browserMessage);

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

        yatakSil();
        extentTest.info("Tum senaryolar tamamlandi, SoftAssert hatalari raporlaniyor.");
        softAssert.assertAll();
    }

    @Test(priority = 7)
    public void TC_06_HerYatakKartindaDeleteButonununErisilebilirligiVeDeleteOzelligiTesti() {
        extentTest = extentReports.createTest("TC06", "Her yatak kartinda delete butonunun erisilebilirligi ve delete ozelliginin testi");

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
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
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
        ReusableMethods.waitForPageToLoad(2);

        // Sayfa yenilendiği için elementleri tazelemek gerekebilir (Stale Element riskine karşı)
        bedmanagersPage = new BedmanagersPage();
        alertMessageLocators = new AlertMessageLocators();

        try {
            // Burada mesajın GÖRÜNMEMESİNİ bekliyoruz, o yüzden try-catch mantığı ters çalışıyor
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, 3); // Kısa bir süre bekle

            // Eğer buraya düşerse mesaj görünmüş demektir -> HATA
            extentTest.fail("HATA: Geri tusuna basinca silindi bildirimi tekrar ekranda belirdi! (Cache/Session hatasi)");
        } catch (Exception e) {
            // Element bulunamazsa veya timeout yerse -> BAŞARILI (Çünkü mesaj çıkmadı)
            extentTest.pass("Geri gidince bildirim mesaji tekrar cikmadi, sayfa durumu stabil.");
        }
        yatakSil();
        extentTest.pass("Delete fonksiyonu ve Alert yonetimi basariyla test edildi.");
    }

    @Test(priority = 6)
    public void TC_07_MobilCozunurlukteUIUXKontrolu() {
        extentTest = extentReports.createTest("TC07", "Mobil Cozunurlukte UI/UX Kontrolu (430x932)");

        // Test verileri ve sayfa objeleri
        bedManagersSayfasinaGit();
        BedmanagersPage bedmanagersPage = new BedmanagersPage();
        Layout layout = new Layout();
        SoftAssert softAssert = new SoftAssert();
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

        // 1. Mobil Çözünürlük Ayarı
        extentTest.info("Test ortami iPhone 14 Pro Max (430x932) cozunurlugune ayarlaniyor.");
        Dimension mobileSize = new Dimension(430, 932);
        Driver.getDriver().manage().window().setSize(mobileSize);

        // Sayfa yapısının yeni çözünürlüğe oturması için refresh atıyoruz
        extentTest.info("Sayfa yenileniyor ve Hamburger menunun gelmesi bekleniyor.");
        Driver.getDriver().navigate().refresh();

        // Hamburger Menü Kontrolü
        try {
            wait.until(ExpectedConditions.visibilityOf(layout.hamburgerMenuButton));
            softAssert.assertTrue(layout.hamburgerMenuButton.isDisplayed(), "Hamburger menu butonu görünür değil!");
            extentTest.info("Hamburger menu butonu gorunur durumda.");

            ReusableMethods.waitForClickability(layout.hamburgerMenuButton, 3);
            layout.hamburgerMenuButton.click();
            extentTest.pass("Hamburger menüye başarıyla tıklandı, sidebar açılabilir durumda.");
        } catch (Exception e) {
            extentTest.fail("Hamburger menü butonu ya yok ya da tıklanamıyor!");
            softAssert.fail("BUG: Hamburger menü görünüyor ama TIKLANABİLİR DEĞİL! (Sidebar açılamıyor)");
        }

        // 2. Bed Managers Sayfası UI Kontrolü
        extentTest.info("Bed Managers sayfasina dogrudan gecis yapiliyor.");
        Driver.getDriver().get(ConfigReader.getProperty("actualBedManagersUrl"));

        extentTest.info("Tablo verilerinin mobil gorunumde yuklenmesi bekleniyor.");
        wait.until(ExpectedConditions.visibilityOfAllElements(bedmanagersPage.tableBodyRows));

        // Görsel Boyut Kontrolü (UX)
        try {
            int imgWidth = bedmanagersPage.getTableRowMap(0).get("img").getSize().getWidth();
            extentTest.info("Mobil gorunumde yatak gorselinin genisligi olculuyor: " + imgWidth + "px");
            softAssert.assertTrue(imgWidth > 30, "UI BUG: Mobilde yatak fotoğrafı çok küçük veya görünmüyor (Genişlik: " + imgWidth + "px)");
        } catch (Exception e) {
            extentTest.warning("Gorsel genisligi alinamadi, element bulunamamis olabilir.");
        }

        // 3. Edit Butonu Fonksiyonelliği (JS Click ile)
        extentTest.info("Edit butonuna mobil uyumluluk icin JS Executor ile tiklaniyor.");
        WebElement firstEditButton = bedmanagersPage.getTableRowMap(0).get("editButton");
        js.executeScript("arguments[0].click();", firstEditButton);

        try {
            wait.until(ExpectedConditions.urlContains("edit"));
            extentTest.info("Edit sayfasina yonlendirme basarili.");
        } catch (Exception e) {
            softAssert.fail("Edit butonuna tiklanmasina ragmen sayfa URL'i degismedi.");
        }

        extentTest.info("Geri tusuna basilarak listeye donuluyor.");
        Driver.getDriver().navigate().back();
        wait.until(ExpectedConditions.visibilityOfAllElements(bedmanagersPage.tableBodyRows));

        // 4. Delete Butonu ve Alert Kontrolü
        extentTest.info("Delete butonuna JS Executor ile tiklaniyor.");
        WebElement firstDeleteButton = bedmanagersPage.getTableRowMap(0).get("deleteButton");
        js.executeScript("arguments[0].click();", firstDeleteButton);

        try {
            extentTest.info("Tarayici uzerinde Alert (Pop-up) bekleniyor.");
            wait.until(ExpectedConditions.alertIsPresent());
            Driver.getDriver().switchTo().alert().accept();
            extentTest.pass("Delete pop-up'ı mobilde görüntülendi ve onaylandı.");
        } catch (Exception e) {
            extentTest.fail("HATA: Delete butonuna basinca Alert cikmadi!");
            softAssert.fail("BUG: Mobilde Delete butonuna basınca 'Emin misiniz?' pop-up'ı ÇIKMADI, direkt sildi veya işlem yapmadı!");
        }


        extentTest.info("Mobil UI/UX testleri tamamlandi, hatalar raporlaniyor.");
        softAssert.assertAll();
    }

    // Yatak silme
    public void yatakSil() {
        try {
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            Layout layout = new Layout();

            ReusableMethods.waitForClickability(layout.headerAuthAdminDashboardButton, 5).click();

            bedManagersSayfasinaGit();

            BedmanagersPage bedmanagersPage = new BedmanagersPage();

            ReusableMethods.waitForPageToLoad(2);

            if (bedmanagersPage.tableBodyRows.isEmpty()) {
                System.out.println("INFO: Silinecek yatak bulunamadı, cleanup işlemi pas geçiliyor.");
                return;
            }

            WebElement deleteButton = bedmanagersPage.getTableRowMap(0).get("deleteButton");

            ReusableMethods.waitForClickability(deleteButton, 3);
            deleteButton.click();

            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));

            try {
                wait.until(ExpectedConditions.alertIsPresent());
                Driver.getDriver().switchTo().alert().accept();
                System.out.println("INFO: Yatak silme onayı (Alert) kabul edildi.");
            } catch (TimeoutException e) {
                System.out.println("UYARI: Delete butonuna basıldı ama Alert çıkmadı veya otomatik kapandı.");
            }

            ReusableMethods.waitForPageToLoad(2);

        } catch (Exception e) {
            System.out.println("HATA: yatakSil() metodu çalışırken bir sorun oluştu: " + e.getMessage());
        }
    }
}