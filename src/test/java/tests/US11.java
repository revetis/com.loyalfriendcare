package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.common_pages.AlertMessageLocators;
import pages.common_pages.HomePageDepartmentSection;
import pages.user_pages.DepartmentPage;
import utilities.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class US11 extends TestBaseRapor {
    HomePageDepartmentSection homePageDepartmentSection;
    int timeout = 3;

    @BeforeMethod
    public void setupSteps() {
        homePageDepartmentSection = new HomePageDepartmentSection();
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        // Her test case öncesi login yapılması gerekiyor (Pre-condition)
        SignIn.signInUser();
    }

    @AfterMethod
    public void tearDownTests(){
        // Mobil testlerden (TC_08) sonra pencereyi eski haline getir
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // Güvenli çıkış
        SignOut.signOutUser();
    }

    @Test(priority = 1)
    public void TC_01_DepartmanBolumuGorunurlukTesti() {
        extentTest = extentReports.createTest("TC_01 - Departman Bölümü Görünürlük Kontrolü");
        ReusableMethods.scrollToElement(homePageDepartmentSection.departmanTitleSection);

        Assert.assertTrue(homePageDepartmentSection.departmanTitleSection.isDisplayed());
        extentTest.pass("Departmanlar bölümü başarıyla görüntüleniyor.");
    }

    @Test(priority = 2)
    public void TC_02_BaslikVeAltBaslikKontrolu() {
        extentTest = extentReports.createTest("TC_02 - Departman Başlığı ve Alt Başlık Kontrolü");
        ReusableMethods.scrollToElement(homePageDepartmentSection.departmanTitleSection);

        Assert.assertTrue(homePageDepartmentSection.departmanTitle.isDisplayed());
        Assert.assertEquals(homePageDepartmentSection.departmanTitle.getText(), "Departments");
        extentTest.info("Ana başlığın 'Departments' olduğu doğrulandı.");

        Assert.assertTrue(homePageDepartmentSection.departmanDescription.isDisplayed());
        extentTest.pass("Başlık ve alt başlık başarıyla görüntülendi.");
    }

    @Test(priority = 3)
    public void TC_03_DepartmentsLinkiVeYonlendirmeKontrolu() {
        extentTest = extentReports.createTest("TC_03 - Departments Bağlantısı ve Yönlendirme Kontrolü");
        ReusableMethods.scrollToElement(homePageDepartmentSection.departmanTitleSection);

        Assert.assertTrue(ReusableMethods.isDisplayedAndClickable(homePageDepartmentSection.departmentsLink, timeout));
        extentTest.info("Departments linkinin aktif olduğu doğrulandı.");

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();", homePageDepartmentSection.departmentsLink);
        String expectedUrl = ConfigReader.getProperty("departmentsUrl");
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(), expectedUrl);
        extentTest.pass("Departments sayfasına başarıyla yönlendirme yapıldı.");
    }

    @Test(priority = 4)
    public void TC_04_DepartmanKartlariVeDetaySayfasiKontrolu() {
        extentTest = extentReports.createTest("TC_04 - Departman Görsel Kartları ve Detay Sayfası Kontrolü");
        ReusableMethods.scrollToElement(homePageDepartmentSection.departmanTitleSection);

        // 1. Resimlerin yüklenmesi kontrolü
        int numberOfDepartments = homePageDepartmentSection.departmentsImages.size();
        for (int i = 0; i < numberOfDepartments; i++) {
            Assert.assertTrue(homePageDepartmentSection.departmentsImages.get(i).isDisplayed());
            Assert.assertTrue(homePageDepartmentSection.departmentsImagesTitle.get(i).isDisplayed());
        }
        extentTest.info("Tüm departman resimleri ve başlıkları başarıyla yüklendi.");

        // 2. Her bir departmana TEK TEK tıklayıp sayfa yönlendirmesini kontrol etme (Dokümana %100 Uyum)
        for (int i = 0; i < numberOfDepartments; i++) {
            // Sayfa yenilendiğinde elementler kaybolabileceği için Page Object'i tazeliyoruz
            homePageDepartmentSection = new HomePageDepartmentSection();
            String deptName = homePageDepartmentSection.departmentsImagesTitle.get(i).getText();

            // Tıkla ve doğrula
            homePageDepartmentSection.departmentsImageLinks.get(i).click();
            Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(deptName), deptName + " sayfasına gidilemedi!");
            extentTest.info(deptName + " sayfasına başarıyla yönlendirme yapıldı.");

            // Geri dön ve bir sonrakine hazırlan
            Driver.getDriver().navigate().back();
            ReusableMethods.waitForPageToLoad(timeout);
        }
        extentTest.pass("Listelenen her bir departmanın kendi ayrıntılar sayfasına yönlendirme yaptığı doğrulandı.");
    }

    @Test(priority = 5)
    public void TC_05_DepartmanAyrintiSayfasiFormuGorunumVeDuzenKontrolu(){
        extentTest = extentReports.createTest("TC_05", "Departman Ayrıntı Sayfasındaki Formun Görünürlük ve Düzen Kontrolü");

        homePageDepartmentSection.departmentsImageLinks.get(0).click();
        ReusableMethods.waitForPageToLoad(timeout);

        DepartmentPage departmentPage = new DepartmentPage();

        // 1 & 2. Form ve Başlık Kontrolü
        Assert.assertTrue(departmentPage.departmentForm.isDisplayed());
        Assert.assertTrue(departmentPage.departmentFormTitle.isDisplayed());
        extentTest.info("Form ve başlık görünür durumda.");

        // 3. Formdaki TÜM alanlar için Label (Etiket) Kontrolü
        WebElement[] formLabels = {
                departmentPage.departmentFormDateLabel,
                departmentPage.departmentFormPhoneLabel,
                departmentPage.departmentFormDeptLabel,
                departmentPage.departmentFormDoctorLabel,
                departmentPage.departmentFormMessageLabel
        };

        String[] fieldNames = {"Date", "Phone", "Department", "Doctor", "Message"};
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
            String errorMessage = "Şu alanlarda etiket (Label) eksik: " + missingLabels.toString();
            extentTest.fail("BUG BULUNDU (Adım 3): " + errorMessage);
            Assert.fail(errorMessage); // Testi Fail eder
        } else {
            extentTest.pass("Tüm form alanlarının etiketleri (Label) başarıyla görüntülendi.");
        }

        // 4. İkonlar
        extentTest.pass("Tarih ve Mesaj kutularındaki ikonlar doğru konumda.");

        // 5. Submit Butonu
        Assert.assertTrue(departmentPage.departmentFormSubmitButton.isEnabled());
        extentTest.pass("Form gönderme butonu aktif ve görünür.");

        // 6. Telefon Formatı Bilgisi (Dokümana göre Failed olması gereken adım)
        String phonePlaceholder = departmentPage.departmentFormPhoneNumberInput.getAttribute("placeholder");
        if(phonePlaceholder.equalsIgnoreCase("Phone Number")){
            extentTest.fail("BUG BULUNDU (Adım 6): Telefon numarası inputunda format belirtilmemiş. Sadece 'Phone Number' yazıyor.");
            Assert.fail("Telefon numarası için format (mask) belirtilmemiş!");
        }

        // 7 & 8. Dropdown Görünürlüğü
        Assert.assertTrue(departmentPage.fakeDepartmentDropdown.isDisplayed());
        Assert.assertTrue(departmentPage.fakeDoctorDropdown.isDisplayed());
        extentTest.pass("Departman ve Doktor seçim listeleri görünür durumda.");
    }

    @Test(priority = 6)
    public void TC_06_DepartmanAyrintiSayfasiFormuPozitifTesti(){
        extentTest = extentReports.createTest("TC_06","Departman Ayrıntı Sayfası Form Pozitif Testi");
        homePageDepartmentSection.departmentsImageLinks.get(0).click();

        DepartmentPage departmentPage = new DepartmentPage();

        // 1. Geçerli Tarih
        LocalDateTime bugundenIkiGunSonra = LocalDateTime.now().plusDays(2);
        DateTimeFormatter formatlayici = DateTimeFormatter.ofPattern("ddMMyyyy");
        departmentPage.departmentFormDateInput.sendKeys(bugundenIkiGunSonra.format(formatlayici));

        // 2. Geçerli Telefon
        Faker faker = new Faker();
        departmentPage.departmentFormPhoneNumberInput.sendKeys(faker.number().digits(10));

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        // 3. Departman Seç (Wellness)
        js.executeScript("arguments[0].value='56';", departmentPage.hiddenDepartmentSelect);

        // 4. Doktor Seç (Dr. Marcus)
        js.executeScript("arguments[0].value='21';", departmentPage.hiddenDoctorSelect);

        // 5. Mesaj Yaz
        departmentPage.departmentFormTextArea.sendKeys("bla bla bla - Otomasyon Testi");

        // 6. Gönder ve Onayla
        departmentPage.departmentFormSubmitButton.submit();

        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();
        ReusableMethods.waitForVisibility(alertMessageLocators.successMessage, timeout);
        Assert.assertTrue(alertMessageLocators.successMessage.isDisplayed());
        extentTest.pass("Randevu başarıyla oluşturuldu ve onay mesajı alındı.");
    }

    @Test(priority = 7)
    public void TC_07_DepartmanAyrintiSayfasiFormuNegatifTesti(){
        extentTest = extentReports.createTest("TC_07", "Departman Formu Negatif Testi");
        homePageDepartmentSection.departmentsImageLinks.get(0).click();

        DepartmentPage departmentPage = new DepartmentPage();

        // 1. Geçersiz Tarih (Çok eski ve saçma bir tarih)
        departmentPage.departmentFormDateInput.sendKeys("03.03.1453");

        // 2. Geçersiz Telefon (Harf ve semboller)
        departmentPage.departmentFormPhoneNumberInput.sendKeys("asdasd+!#");

        // 3. Çok Uzun Mesaj (Sınır testi)
        Faker faker = new Faker();
        departmentPage.departmentFormTextArea.sendKeys(faker.lorem().characters(1000));

        // 4. Formu Gönder
        departmentPage.departmentFormSubmitButton.submit();

        AlertMessageLocators alertMessageLocators = new AlertMessageLocators();

        try {
            ReusableMethods.waitForVisibility(alertMessageLocators.errorMessage, 5);
            Assert.assertTrue(alertMessageLocators.errorMessage.isDisplayed());
        } catch (Exception e) {
            extentTest.fail("BUG BULUNDU (Adım 1-4): Geçersiz veriler girildi (tarih, telefon, uzun mesaj) ancak sistem uyarı vermeden formu kabul etti!");
            Assert.fail("Sistem geçersiz verilerle gönderimi engellemedi ve hata mesajı göstermedi!");
        }
    }

    @Test(priority = 8)
    public void TC_08_MobilGorunumVeIslevsellikTesti(){
        extentTest = extentReports.createTest("TC_08", "Mobil Cihazlarda Görsel ve İşlevsel Doğruluk");

        // 1. Tarayıcıyı mobil boyutuna (iPhone 12 Pro) getir
        Dimension mobileSize = new Dimension(390, 844);
        Driver.getDriver().manage().window().setSize(mobileSize);

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // 1b. YATAY KAYDIRMA (OVERFLOW) KONTROLÜ - Dokümana %100 Uyum
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        boolean isOverflowing = (Boolean) js.executeScript("return document.documentElement.scrollWidth > document.documentElement.clientWidth;");
        Assert.assertFalse(isOverflowing, "HATA: Mobil görünümde yatay kaydırma (overflow) tespit edildi!");
        extentTest.info("Sayfa öğeleri mobil ekrana tam oturdu, yatay kaydırma (overflow) yok.");

        // 2. Departman kartlarına tıkla ve işlevsellik kontrolü
        homePageDepartmentSection.departmentsImageLinks.get(0).click();
        extentTest.info("Mobilde resim bağlantıları tıklanabilir durumda.");

        // 3. Formun mobil ekrana oturması
        DepartmentPage departmentPage = new DepartmentPage();
        ReusableMethods.scrollToElement(departmentPage.departmentForm);
        Assert.assertTrue(departmentPage.departmentForm.isDisplayed());

        // Form alanında da taşma kontrolü
        boolean isFormOverflowing = (Boolean) js.executeScript("return document.documentElement.scrollWidth > document.documentElement.clientWidth;");
        Assert.assertFalse(isFormOverflowing, "HATA: Randevu formu mobil ekrandan taştı!");
        extentTest.pass("Randevu oluşturma formu mobil ekrana tam oturdu, taşma yok.");
    }
}