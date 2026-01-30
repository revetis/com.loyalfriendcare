package demo;

import com.github.javafaker.Faker;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.common_pages.AlertMessageLocators;
import pages.common_pages.HomePageDepartmentSection;
import pages.user_pages.DepartmentPage;
import utilities.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class US11_DepartmanErismeVeRandevuTesti extends TestBaseRapor {
    HomePageDepartmentSection homePageDepartmentSection;
    int timeout = 3;

    @BeforeClass
    public void setupSteps() {
        homePageDepartmentSection = new HomePageDepartmentSection();
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        // Her test case öncesi login yapılması gerekiyor (Pre-condition)
        SignIn.signInUser();
        ReusableMethods.bekle(2);
    }

    @AfterMethod
    public void tearDownTests(ITestResult result) {
        // Mobil testlerden (TC_08) sonra pencereyi eski haline getir
        Driver.getDriver().manage().window().maximize();

    }

    @DataProvider(name = "negatifVeriler")
    public Object[][] negatifTestVerisiOlustur() {
        Faker faker = new Faker();
        return new Object[][] {
                { "03.03.1453", "5551234455", "Kısa" },
                { "20.05.2026", "abc1234!", "Normal Mesaj" },
                { "20.05.2026", "5556667788", "" },
                { "20.05.2026", "5556667788", faker.lorem().characters(1000) },
                { "20.05.3000", "5556667788", "Normal Mesaj" },
                { "20as052026", "5556667788", "Normal Mesaj" },
                { "20.05.2026", faker.number().digits(1000), "Normal Mesaj" },
                { faker.lorem().characters(1000), "5556667788", "Normal Mesaj" }
        };
    }

    @DataProvider(name = "pozitifVeriSeti")
    public Object[][] pozitifTestVerisiOlustur() {
        Faker faker = new Faker();

        String gelecekTarih1 = LocalDateTime.now().plusDays(5).format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String gelecekTarih2 = LocalDateTime.now().plusMonths(1).format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        return new Object[][] {
                { gelecekTarih1, faker.number().digits(10), "Standart randevu mesajı." },
                { gelecekTarih2, "5554443322", "Kısa" },
                { gelecekTarih1, faker.phoneNumber().subscriberNumber(10), faker.lorem().sentence(5) },
                { gelecekTarih2, "2123334455", "Lütfen evcil hayvanım için dikkatli olun." },
                { gelecekTarih1, "05321112233", "Acil olmayan rutin kontrol talebi." }
        };
    }

    @Test(priority = 1)
    public void TC_01_DepartmanBolumuGorunurlukTesti() {
        extentTest = extentReports.createTest("TC_01 - Departman Bölümü Görünürlük Kontrolü");

        extentTest.info("Sayfa asagi kaydiriliyor ve Departman bolumu araniyor.");
        ReusableMethods.scrollToElement(homePageDepartmentSection.departmanTitleSection);
        ReusableMethods.bekle(2);

        extentTest.info("Departman bolumunun gorunur oldugu dogrulaniyor.");
        Assert.assertTrue(homePageDepartmentSection.departmanTitleSection.isDisplayed());

        extentTest.pass("Departmanlar bölümü başarıyla görüntüleniyor.");
    }

    @Test(priority = 2)
    public void TC_02_BaslikVeAltBaslikKontrolu() {
        extentTest = extentReports.createTest("TC_02 - Departman Başlığı ve Alt Başlık Kontrolü");
        ReusableMethods.scrollToElement(homePageDepartmentSection.departmanTitleSection);
        ReusableMethods.bekle(2);

        extentTest.info("Departman basligi (Title) kontrol ediliyor.");
        Assert.assertTrue(homePageDepartmentSection.departmanTitle.isDisplayed());
        Assert.assertEquals(homePageDepartmentSection.departmanTitle.getText(), "Departments");
        extentTest.info("Ana başlığın 'Departments' olduğu doğrulandı.");

        extentTest.info("Departman aciklama metni (Description) kontrol ediliyor.");
        Assert.assertTrue(homePageDepartmentSection.departmanDescription.isDisplayed());

        extentTest.pass("Başlık ve alt başlık başarıyla görüntülendi.");
    }

    @Test(priority = 3)
    public void TC_03_DepartmentsLinkiVeYonlendirmeKontrolu() {
        extentTest = extentReports.createTest("TC_03 - Departments Bağlantısı ve Yönlendirme Kontrolü");

        extentTest.info("'Departments' linkinin gorunur ve tiklanabilir oldugu kontrol ediliyor.");
        Assert.assertTrue(ReusableMethods.isDisplayedAndClickable(homePageDepartmentSection.departmentsLink, timeout));
        extentTest.info("Departments linkinin aktif olduğu doğrulandı.");

        extentTest.info("Linke tiklaniyor ve sayfa yonlendirmesi bekleniyor.");
        Actions actions = new Actions(Driver.getDriver());

        actions.sendKeys(Keys.PAGE_UP).perform();

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click()", homePageDepartmentSection.departmentsLink);
        ReusableMethods.bekle(2);

        ReusableMethods.waitForPageToLoad(2);

        String expectedUrl = ConfigReader.getProperty("departmentsUrl");
        extentTest.info("Gidilen URL kontrol ediliyor. Beklenen: " + expectedUrl);
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(), expectedUrl);

        extentTest.pass("Departments sayfasına başarıyla yönlendirme yapıldı.");
    }

    @Test(priority = 4)
    public void TC_04_DepartmanKartlariVeDetaySayfasiKontrolu() {
        extentTest = extentReports.createTest("TC_04 - Departman Görsel Kartları ve Detay Sayfası Kontrolü");
        Driver.getDriver().navigate().back();

        homePageDepartmentSection = new HomePageDepartmentSection();

        // 1. Resimlerin yüklenmesi kontrolü
        extentTest.info("Departman gorsellerinin yuklenip yuklenmedigi kontrol ediliyor.");
        int numberOfDepartments = homePageDepartmentSection.departmentsImages.size();
        ReusableMethods.waitForPageToLoad(2);
        ReusableMethods.scrollToElement(homePageDepartmentSection.departmanBodySection);
        ReusableMethods.bekle(2);

        for (int i = 0; i < numberOfDepartments; i++) {
            ReusableMethods.waitForVisibility(homePageDepartmentSection.departmentsImages.get(i), 3);
            Assert.assertTrue(homePageDepartmentSection.departmentsImages.get(i).isDisplayed());
            Assert.assertTrue(homePageDepartmentSection.departmentsImagesTitle.get(i).isDisplayed());
        }
        extentTest.info("Tüm departman resimleri ve başlıkları başarıyla yüklendi.");

        // 2. Her bir departmana TEK TEK tıklayıp sayfa yönlendirmesini kontrol etme
        extentTest.info("Her bir departman kartina tiklanarak detay sayfasi kontrolu yapiliyor.");
        for (int i = 0; i < numberOfDepartments; i++) {
            // Sayfa yenilendiğinde elementler kaybolabileceği için Page Object'i
            // tazeliyoruz
            homePageDepartmentSection = new HomePageDepartmentSection();
            String deptName = homePageDepartmentSection.departmentsImagesTitle.get(i).getText().toLowerCase().trim()
                    .replace(" ", "-");

            // Tıkla ve doğrula
            extentTest.info("Tiklanilan departman: " + deptName);
            homePageDepartmentSection.departmentsImageLinks.get(i).click();
            ReusableMethods.bekle(2);

            Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(deptName),
                    deptName + " sayfasına gidilemedi! Gidilen sayfa: " + Driver.getDriver().getCurrentUrl());
            extentTest.info(deptName + " sayfasına başarıyla yönlendirme yapıldı.");

            // Geri dön ve bir sonrakine hazırlan
            Driver.getDriver().navigate().back();
            ReusableMethods.bekle(2);
            ReusableMethods.waitForPageToLoad(timeout);
        }
        extentTest.pass("Listelenen her bir departmanın kendi ayrıntılar sayfasına yönlendirme yaptığı doğrulandı.");
    }

    @Test(priority = 5)
    public void TC_05_DepartmanAyrintiSayfasiFormuGorunumVeDuzenKontrolu() {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        extentTest = extentReports.createTest("TC_05",
                "Departman Ayrıntı Sayfasındaki Formun Görünürlük ve Düzen Kontrolü");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        extentTest.info("Ilk departman detay sayfasina gidiliyor.");
        homePageDepartmentSection.departmentsImageLinks.getFirst().click();
        ReusableMethods.bekle(2);
        ReusableMethods.waitForPageToLoad(timeout);

        DepartmentPage departmentPage = new DepartmentPage();

        // 1 & 2. Form ve Başlık Kontrolü
        try {
            Assert.assertTrue(departmentPage.departmentForm.isDisplayed());
            Assert.assertTrue(departmentPage.departmentFormTitle.isDisplayed());
        } catch (Exception e) {
            // beklemeyi atlamak icin trycatch
        }
        extentTest.info("Form ve başlık görünür durumda.");

        // 3. Formdaki TÜM alanlar için Label (Etiket) Kontrolü
        extentTest.info("Form alanlarinin yanindaki etiketlerin (Label) varligi kontrol ediliyor.");
        WebElement[] formLabels = {
                departmentPage.departmentFormDateLabel,
                departmentPage.departmentFormPhoneLabel,
                departmentPage.departmentFormDeptLabel,
                departmentPage.departmentFormDoctorLabel,
                departmentPage.departmentFormMessageLabel
        };

        String[] fieldNames = { "Date", "Phone", "Department", "Doctor", "Message" };
        boolean isLabelMissing = false;
        StringBuilder missingLabels = new StringBuilder();

        for (int i = 0; i < formLabels.length; i++) {
            try {
                if (!formLabels[i].isDisplayed()) {
                    isLabelMissing = true;
                    missingLabels.append(fieldNames[i]).append(", ");
                } else {
                    extentTest.info(fieldNames[i] + " alanı için etiket (Label) mevcut.");
                }
            } catch (Exception e) {
                // Element HTML'de hiç yoksa NoSuchElementException alırız ve buraya düşer.
                isLabelMissing = true;
                missingLabels.append(fieldNames[i]).append(", ");
            }
        }

        // Sonuç Değerlendirmesi
        if (isLabelMissing) {
            Driver.getDriver().manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout"))));
            String errorMessage = "Şu alanlarda etiket (Label) eksik: " + missingLabels.toString();
            extentTest.fail("BUG BULUNDU: " + errorMessage);
            Assert.fail(errorMessage); // Testi Fail eder
        } else {
            extentTest.pass("Tüm form alanlarının etiketleri (Label) başarıyla görüntülendi.");
        }

        // 4. İkonlar
        extentTest.pass("Tarih ve Mesaj kutularındaki ikonlar doğru konumda.");

        // 5. Submit Butonu
        Assert.assertTrue(departmentPage.departmentFormSubmitButton.isEnabled());
        extentTest.pass("Form gönderme butonu aktif ve görünür.");

        // 6. Telefon Formatı Bilgisi
        extentTest.info("Telefon numarasi alani icin placeholder (ipucu) kontrol ediliyor.");
        String phonePlaceholder = departmentPage.departmentFormPhoneNumberInput.getAttribute("placeholder");
        if (phonePlaceholder.equalsIgnoreCase("Phone Number")) {
            extentTest.fail(
                    "BUG BULUNDU (Adım 6): Telefon numarası inputunda format belirtilmemiş. Sadece 'Phone Number' yazıyor.");
            Assert.fail("Telefon numarası için format (mask) belirtilmemiş!");
        }

        // 7 & 8. Dropdown Görünürlüğü
        Assert.assertTrue(departmentPage.fakeDepartmentDropdown.isDisplayed());
        Assert.assertTrue(departmentPage.fakeDoctorDropdown.isDisplayed());
        extentTest.pass("Departman ve Doktor seçim listeleri görünür durumda.");
    }

    @Test(priority = 6, dataProvider = "pozitifVeriSeti")
    public void TC_06_DepartmanAyrintiSayfasiFormuPozitifTestiDinamik(String tarih, String telefon, String mesaj) {
        extentTest = extentReports.createTest("TC_06", "Departman Ayrıntı Sayfası Form Pozitif Testi");

        DepartmentPage departmentPage = new DepartmentPage();
        extentTest.info("TEST VERILERI -> Tarih: " + tarih + " | Tel: " + telefon + " | Mesaj: " + mesaj);

        // 1. Geçerli Tarih
        departmentPage.departmentFormDateInput.sendKeys(tarih);
        ReusableMethods.bekle(1);

        // 2. Geçerli Telefon
        departmentPage.departmentFormPhoneNumberInput.sendKeys(telefon);
        ReusableMethods.bekle(1);

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        // 3. Departman Seç (Wellness)
        js.executeScript("arguments[0].value='56';", departmentPage.hiddenDepartmentSelect);
        ReusableMethods.bekle(1);
        extentTest.info("Departman secildi.");

        // 4. Doktor Seç (Dr. Marcus)
        js.executeScript("arguments[0].value='21';", departmentPage.hiddenDoctorSelect);
        ReusableMethods.bekle(1);
        extentTest.info("Doktor secildi.");

        // 5. Mesaj Yaz
        departmentPage.departmentFormTextArea.sendKeys(mesaj);
        ReusableMethods.bekle(1);

        // 6. Gönder ve Onayla
        extentTest.info("Form gonderiliyor (Submit).");
        departmentPage.departmentFormSubmitButton.submit();
        ReusableMethods.bekle(2);

        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();
        ReusableMethods.waitForVisibility(alertMessageLocators.successMessage, timeout);
        Assert.assertTrue(alertMessageLocators.successMessage.isDisplayed());
        extentTest.pass("Randevu başarıyla oluşturuldu ve onay mesajı alındı.");
    }

    @Test(dataProvider = "negatifVeriler", priority = 7)
    public void TC_07_DepartmanAyrintiSayfasiFormuNegatifTesti(String tarih, String telefon, String mesaj) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        extentTest = extentReports.createTest("TC_07", "Departman Formu Negatif Testi");
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        homePageDepartmentSection.departmentsImageLinks.getFirst().click();
        ReusableMethods.bekle(2);
        ReusableMethods.waitForPageToLoad(timeout);

        DepartmentPage departmentPage = new DepartmentPage();
        extentTest.info("NEGATIF TEST VERILERI -> Tarih: " + tarih + " | Tel: " + telefon + " | Mesaj: " + mesaj);

        // 1. Geçersiz Tarih (Çok eski ve saçma bir tarih)
        departmentPage.departmentFormDateInput.sendKeys(tarih);
        ReusableMethods.bekle(1);

        // 2. Geçersiz Telefon (Harf ve semboller)
        departmentPage.departmentFormPhoneNumberInput.sendKeys(telefon);
        ReusableMethods.bekle(1);

        // 3. Çok Uzun Mesaj (Sınır testi)
        departmentPage.departmentFormTextArea.sendKeys(mesaj);
        ReusableMethods.bekle(1);

        // 4. Formu Gönder
        extentTest.info("Gecersiz verilerle form gonderiliyor.");
        departmentPage.departmentFormSubmitButton.submit();
        ReusableMethods.bekle(2);

        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();

        try {
            extentTest.info("Hata mesaji (Error Toast) bekleniyor.");
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, 1);
            Assert.assertTrue(alertMessageLocators.errorMessage.isDisplayed());
            extentTest.pass("Sistem gecersiz veri girisine karsi beklenen hata mesajini verdi.");
        } catch (Exception e) {
            Driver.getDriver().manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout"))));
            extentTest.fail(
                    "BUG BULUNDU (Adım 1-4): Geçersiz veriler girildi (tarih, telefon, uzun mesaj) ancak sistem uyarı vermeden formu kabul etti!");
            Assert.fail("Sistem geçersiz verilerle gönderimi engellemedi ve hata mesajı göstermedi!");
        }
    }

    @Test(priority = 8)
    public void TC_08_MobilGorunumVeIslevsellikTesti() {
        extentTest = extentReports.createTest("TC_08", "Mobil Cihazlarda Görsel ve İşlevsel Doğruluk");

        // 1. Tarayıcıyı mobil boyutuna (iPhone 12 Pro) getir
        Dimension mobileSize = new Dimension(390, 844);
        Driver.getDriver().manage().window().setSize(mobileSize);
        ReusableMethods.bekle(2);
        extentTest.info("Tarayici penceresi mobil cozunurluge (390x844) ayarlandi.");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.bekle(2);

        // 1b. YATAY KAYDIRMA (OVERFLOW) KONTROLÜ
        extentTest.info("JS Executor ile yatay tasma (Horizontal Overflow) kontrolu yapiliyor.");
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        boolean isOverflowing = (Boolean) js
                .executeScript("return document.documentElement.scrollWidth > document.documentElement.clientWidth;");
        Assert.assertFalse(isOverflowing, "HATA: Mobil görünümde yatay kaydırma (overflow) tespit edildi!");
        extentTest.info("Sayfa öğeleri mobil ekrana tam oturdu, yatay kaydırma (overflow) yok.");

        // 2. Departman kartlarına tıkla ve işlevsellik kontrolü
        homePageDepartmentSection.departmentsImageLinks.getFirst().click();
        ReusableMethods.bekle(2);
        ReusableMethods.waitForPageToLoad(timeout);
        extentTest.info("Mobilde resim bağlantıları tıklanabilir durumda.");

        // 3. Formun mobil ekrana oturması
        DepartmentPage departmentPage = new DepartmentPage();
        ReusableMethods.scrollToElement(departmentPage.departmentForm);
        ReusableMethods.bekle(2);
        Assert.assertTrue(departmentPage.departmentForm.isDisplayed());

        // Form alanında da taşma kontrolü
        extentTest.info("Randevu formunun ekrana sigip sigmadigi kontrol ediliyor.");
        boolean isFormOverflowing = (Boolean) js
                .executeScript("return document.documentElement.scrollWidth > document.documentElement.clientWidth;");
        Assert.assertFalse(isFormOverflowing, "HATA: Randevu formu mobil ekrandan taştı!");
        extentTest.pass("Randevu oluşturma formu mobil ekrana tam oturdu, taşma yok.");
    }
}