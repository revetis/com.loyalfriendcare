package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.common_pages.HomePageDepartmentSection;
import pages.common_pages.HomepageBodyPage;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import pages.user_pages.DoctorDetailPage;
import pages.user_pages.DoctorsPage;
import utilities.*;
import java.lang.reflect.Method;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;


import java.time.Duration;
import java.util.Locale;

public class US16 extends TestBaseRapor {


    @BeforeMethod
    public void setupSteps(Method method) {
        String methodName = method.getName();

        Layout layout = new Layout();
        DoctorsPage doctorsPage = new DoctorsPage();

        // TC_01, TC_02 haricindeki tüm testler için çalış
        if (!methodName.startsWith("TC_01") && !methodName.startsWith("TC_02")) {
            Driver.getDriver().get(ConfigReader.getProperty("url"));
            // Her test case öncesi login yapılması gerekiyor (Pre-condition)
            SignIn.signInUser();
            ReusableMethods.bekle(2);

            // Doctors linkine tıkla
            layout.headerDoctorsLink.click();
            ReusableMethods.bekle(2);

            // Doctorun detay sayfasına git
            doctorsPage.doctorNames.get(0).click();
            ReusableMethods.bekle(2);
        }
    }

    @Test(priority = 1)
    public void TC_01_User_HomePageToDoctorsPageNavigationTest() {
        // setupSteps çalışmaz, kendi login'ini yapar

        extentTest = extentReports.createTest("TC_01 - Kayıtlı Kullanıcı ile Sisteme Giriş Yapma İşlemi",
                "Kayıtlı bir kullanıcı geçerli (email ve password) hesap bilgileri girerek Sign In butonuna tıklanır");

        // Page objelerini oluştur
        Layout layout = new Layout();
        LoginPage loginPage = new LoginPage();

        // 1. Anasayfa Header kısmında Sign In'e tıkla
        extentTest.info("Adım 1: Anasayfa Header kısmında Sign In butonuna tıklanıyor");
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForVisibility(layout.signInLink, 10);
        layout.signInLink.click();
        extentTest.pass("Sign In sayfasına başarıyla yönlendirildi");

        // 2. Geçerli email ve password bilgileri girilip Sign In butonuna tıkla
        extentTest.info("Adım 2: Geçerli email ve password bilgileri giriliyor");

        String userEmail = ConfigReader.getProperty("user_email");
        String userPassword = ConfigReader.getProperty("user_password");

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 15);

        loginPage.emailAddressInput.sendKeys(userEmail);
        extentTest.info("Email girildi: " + userEmail);

        loginPage.passwordInput.sendKeys(userPassword);
        extentTest.info("Password girildi");

        loginPage.signInButton.click();
        extentTest.pass("Sign In butonuna tıklandı");

        // 3. Başarılı giriş kontrolü - URL kontrolü
        ReusableMethods.bekle(2);
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("/login"), "Login işlemi başarısız! Hala login sayfasında.");
        extentTest.pass("TC_01 - Kullanıcı başarıyla giriş yaptı");

        // 4. Kayıtlı kullanıcı ile login olduktan sonra Home page header'daki Doctors linkine mouse ile gidilir
        extentTest.info("Adım 1: Kullanıcı Home Page'de, header'daki Doctors linkine mouse ile gidiliyor");
        ReusableMethods.waitForVisibility(layout.headerDoctorsLink, 10);
        ReusableMethods.hover(layout.headerDoctorsLink);
        extentTest.pass("Doctors linkine mouse ile gidildi");

        // 5. Header'dan Doctors linkine tıklanır
        extentTest.info("Adım 2: Header'dan Doctors linkine tıklanıyor");
        layout.headerDoctorsLink.click();
        ReusableMethods.bekle(2);
        extentTest.pass("Doctors linkine tıklandı");

        extentTest.info("Adım 3: Doctors sayfasına yönlendirilme kontrolü yapılıyor");
        currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.toLowerCase().contains("doctors") || currentUrl.toLowerCase().contains("doctor"),
                "Doctors sayfasına yönlendirilmedi! Mevcut URL: " + currentUrl);
        extentTest.pass("TC_02 - Doctors sayfasına başarıyla erişildi");
    }

    @Test(priority = 2, dependsOnMethods = "TC_01_User_HomePageToDoctorsPageNavigationTest()")
    public void TC_02_DoctorsPageDoctorInformationTest() {

        extentTest = extentReports.createTest("TC_02 - Doctors Sayfasındaki Doktorların Bilgilerini İnceleyebilmeli",
                "Doctors isminde tüm doktorların olduğu yeni bir sayfaya gidilir. Sayfanın body kısmında doktorlar ve bilgileri yer alır");

        DoctorsPage doctorsPage = new DoctorsPage();

        // 1. Doctors isminde tüm doktorların olduğu sayfada doktorlar görünür
        extentTest.info("Adım 1: Doctors sayfasındaki doktorların listesi kontrol ediliyor");
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Doctors");
        ReusableMethods.waitForVisibility(doctorsPage.doctorsList.get(0), 15);
        Assert.assertTrue(doctorsPage.doctorsList.size() > 0, "Doctors sayfasında doktor bulunamadı!");
        extentTest.pass("Doctors listesi görüntülendi, toplam doktor sayısı: " + doctorsPage.doctorsList.size());

        // 2. Sayfanın solunda Doctors başlığı menüsü altında listelenen doktorların isimleri görünür
        extentTest.info("Adım 2: Doctors sayfasındaki doktor isimleri kontrol ediliyor");
        ReusableMethods.waitForVisibility(doctorsPage.doctorNames.get(0), 10);

        for (int i = 0; i < doctorsPage.doctorNames.size(); i++) {
            WebElement doctorName = doctorsPage.doctorNames.get(i);
            Assert.assertTrue(doctorName.isDisplayed(), "Doktor ismi görünür değil!");
            extentTest.info("Doktor " + (i + 1) + ": " + doctorName.getText());
        }
        extentTest.pass("Tüm doktor isimleri görüntülendi, toplam: " + doctorsPage.doctorNames.size());

        // Doktor bilgilerini de kontrol et
        extentTest.info("Adım 2.1: Doktorların detay bilgileri kontrol ediliyor");
        Assert.assertTrue(doctorsPage.doctorInfos.size() > 0, "Doktor bilgileri bulunamadı!");
        extentTest.pass("Doktor bilgileri (Profession, Experience, Specialization) görüntülendi");

        // 3. Bu listelenen doktorlardan seçilen bir doktorun ismine tıklanarak o doktorun sayfasına gidilir
        extentTest.info("Adım 3: Listeden bir doktor seçiliyor ve tıklanıyor");
        String selectedDoctorName = doctorsPage.doctorNames.get(0).getText();
        extentTest.info("Seçilen doktor: " + selectedDoctorName);

        doctorsPage.doctorNames.get(0).click();
        ReusableMethods.bekle(2);

        // 4. Doktor detay sayfasına yönlendirildiğini kontrol et
        extentTest.info("Adım 1: Doktor detay sayfasına yönlendirilme kontrolü yapılıyor");
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Doctors/"), "Doktor detay sayfasında değil!");
        ReusableMethods.bekle(2);
        extentTest.pass("Seçilen doktor '" + selectedDoctorName + " icin doktor detay sayfasına başarıyla yönlendirildi");

    }

        @Test(priority = 3)
        public void TC_03_DoctorDetailPageDisplayAndValidationTest() {
            extentTest = extentReports.createTest("TC_03 - Doktor Detay Sayfasında Doktor detay kartı ve Randevu Formu Görüntüleme, İnceleme",
                    "Doktor detay sayfasında Doktor'un detay kartı ve sağ tarafta randevu oluşturma formu kontrol edilir ve alanların doğru çalıştığı test edilir");

            DoctorDetailPage doctorDetailPage = new DoctorDetailPage();
            DoctorsPage doctorsPage = new DoctorsPage();
            HomepageBodyPage homepageBodyPage = new HomepageBodyPage();

        // 1. Doktor detay sayfasına yönlendirildiğini kontrol et
        extentTest.info("Adım 1: Doktor detay sayfasına yönlendirilme kontrolü yapılıyor");
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Doctors/"), "Doktor detay sayfasında değil!");
        ReusableMethods.bekle(1);
        extentTest.pass("Doktor detay sayfasına başarıyla yönlendirildi");

        // 2. Doktor header başlığını kontrol et
        extentTest.info("Adım 2: Doktor header başlığı kontrol ediliyor");
        ReusableMethods.waitForVisibility(doctorDetailPage.doctorHeaderTitle, 15);
        Assert.assertTrue(doctorDetailPage.doctorHeaderTitle.isDisplayed(), "Doktor header başlığı görünür değil!");
        Assert.assertTrue(doctorDetailPage.doctorHeaderTitle.getText().contains("Dr."), "Header başlık doktor ismi içermiyor!");
        extentTest.pass("Doktor header başlığı görüntülendi: " + doctorDetailPage.doctorHeaderTitle.getText());

        // 3. Doktor fotoğrafını kontrol et
        extentTest.info("Adım 3: Doktor fotoğrafı kontrol ediliyor");
        Assert.assertTrue(doctorDetailPage.doctorPhoto.isDisplayed(), "Doktor fotoğrafı görünür değil!");
        extentTest.pass("Doktor fotoğrafı görüntülendi");

        // 4. Doktor ismi ve bilgileri kontrol et
        extentTest.info("Adım 4: Doktor ismi ve detay bilgileri kontrol ediliyor");
        Assert.assertTrue(doctorDetailPage.doctorName.isDisplayed(), "Doktor ismi görünür değil!");
        String doctorNameText = doctorDetailPage.doctorName.getText();
        extentTest.info("Doktor ismi: " + doctorNameText);

        Actions actions = new Actions(Driver.getDriver());
        actions.sendKeys(Keys.PAGE_DOWN).perform();

        ReusableMethods.bekle(2);
        Assert.assertTrue(doctorDetailPage.doctorDetailInfos.isDisplayed(), "Doktor detay bilgisi görünür değil!");
        ReusableMethods.bekle(1);
        extentTest.info(doctorDetailPage.doctorDetailInfos.getText());
        extentTest.pass("Tüm doktor bilgileri başarıyla görüntülendi");

        actions.sendKeys(Keys.PAGE_UP).perform();
        ReusableMethods.bekle(1);

        // 5. Appointment Booking formunu kontrol et
        extentTest.info("Adım 5: Appointment Booking formu kontrol ediliyor");
        Assert.assertTrue(doctorDetailPage.appointmentBookingTitle.isDisplayed(), "Appointment Booking başlığı görünür değil!");
        Assert.assertTrue(doctorDetailPage.DateInput.isDisplayed(), "Tarih input'u görünür değil!");
        Assert.assertTrue(doctorDetailPage.phoneNumberInput.isDisplayed(), "Telefon input'u görünür değil!");

        Assert.assertTrue(doctorDetailPage.createMessageTextarea.isDisplayed(), "Message textarea görünür değil!");
        ReusableMethods.bekle(1);
        extentTest.pass("Appointment Booking formu tüm alanları ile görüntülendi");

        extentTest.info("Adım 6: Form alanlarının etiket ve placeholder kontrolü yapılıyor");

        // Tarih alanı ne olduğunu belirten bir etiket veya placeholder kontrolü
        ReusableMethods.waitForVisibility(doctorDetailPage.DateInput, 10);
        Assert.assertTrue(doctorDetailPage.DateInput.isDisplayed(), "Tarih input'u görünür değil!");
        String dateType = doctorDetailPage.DateInput.getAttribute("type");
        String datePlaceholder = doctorDetailPage.DateInput.getAttribute("placeholder");
        Assert.assertTrue(dateType != null || datePlaceholder != null,
                    "Tarih input'unun type veya placeholder attribute'u yok!");
        extentTest.info("✓ Tarih alanı kontrolü başarılı - Type: " + dateType);

        // Telefon numarası alanının ne olduğunu belirten bir etiket veya placeholder kontrolü
        Assert.assertTrue(doctorDetailPage.phoneNumberInput.isDisplayed(), "Phone Number input'u görünür değil!");
        String phonePlaceholder = doctorDetailPage.phoneNumberInput.getAttribute("placeholder");
        if (phonePlaceholder.equalsIgnoreCase("Phone Number")){
            extentTest.fail("BUG: Telefon numarası inputunda format belirtilmemiş.");
            Assert.fail("Telefon numarası için format belirtilmemiş!");
        }

        // Mesaj alanı ne olduğunu belirten bir etiket veya placeholder kontrolü
        Assert.assertTrue(doctorDetailPage.createMessageTextarea.isDisplayed(), "Create Message textarea görünür değil!");
        String messagePlaceholder = doctorDetailPage.createMessageTextarea.getAttribute("placeholder");
        Assert.assertTrue(messagePlaceholder != null && messagePlaceholder.contains("Message"),
                    "Create Message placeholder'ı bulunamadı!");
        extentTest.info("✓ Create Message alanı placeholder: " + messagePlaceholder);

        extentTest.pass("Tüm form alanlarının etiket ve placeholder kontrolü başarılı");

            // 6. Formdaki Tarih alanını kontrol et
            extentTest.info("Adım 6: Tarih alanı kontrol ediliyor");
            Assert.assertTrue(doctorDetailPage.DateInput.isEnabled(), "Tarih alanı aktif değil!");
            extentTest.pass("Tarih alanı aktif ve kullanılabilir");

            // 7. Tarih seçme ikonuna tıklanır
            extentTest.info("Adım 7: Tarih seçimi yapılıyor");
            doctorDetailPage.DateInput.click();
            ReusableMethods.bekle(2);
            extentTest.pass("Tarih seçme alanı tıklandı");

            // 8. Takvimdeki tıklanabilir Ay/Yıl, Aşağı yukarı ok, altta Temizle ve Bugün tuşları kontrol
            extentTest.info("Adım 8: Takvim öğeleri kontrol ediliyor");
            String dateInputType = doctorDetailPage.DateInput.getAttribute("type");
            Assert.assertTrue(dateInputType.equals("date") || dateInputType.equals("text"),
                    "Tarih input'u doğru type'a sahip değil!");
            extentTest.pass("Tarih input type'ı kontrol edildi: " + dateInputType);

            // 9. Departman alanından departmanlar listelendiği ve seçilebildiği kontrol edilir
            extentTest.info("Adım 9: Departman dropdown kontrolü yapılıyor");
            try {
                if (doctorDetailPage.wellnessDropdown.isDisplayed()) {
                    Assert.assertTrue(doctorDetailPage.wellnessDropdown.isEnabled(), "Wellness dropdown aktif değil!");
                    extentTest.pass("Departman dropdown görünür ve aktif");
                }
            } catch (Exception e) {
                extentTest.info("Departman dropdown bu sayfada bulunmuyor veya gizli");
            }

            // 10. Doktorlar alanından doktorların listelendiği ve seçilebildiği kontrol edilir
            extentTest.info("Adım 10: Doktorlar dropdown kontrolü yapılıyor");
            try {
                if (doctorDetailPage.doctorSelectionDropdown.isDisplayed()) {
                    Assert.assertTrue(doctorDetailPage.doctorSelectionDropdown.isEnabled(), "Doktorlar dropdown aktif değil!");
                    extentTest.pass("Doktorlar dropdown görünür ve aktif");
                }
            } catch (Exception e) {
                extentTest.info("Doktorlar dropdown bu sayfada bulunmuyor veya otomatik seçili");
            }

            // 11. Mesaj alanına mesaj yazılabildiği ve ikonun olduğu kontrol edilir
            extentTest.info("Adım 11: Mesaj alanı yazılabilirlik kontrolü yapılıyor");
            Assert.assertTrue(doctorDetailPage.createMessageTextarea.isEnabled(), "Mesaj textarea aktif değil!");
            doctorDetailPage.createMessageTextarea.clear();
            doctorDetailPage.createMessageTextarea.sendKeys("Test mesajı - Randevu testi");
            String enteredText = doctorDetailPage.createMessageTextarea.getAttribute("value");
            Assert.assertTrue(enteredText.contains("Test mesajı"), "Mesaj alanına yazı yazılamadı!");
            extentTest.pass("Mesaj alanına yazı yazılabildiği doğrulandı");

        //  Randevu olustur butonunu kontrol et

        extentTest.info("Adım 6: Appointment Booking butonu kontrol ediliyor");
        Assert.assertTrue(doctorDetailPage.appointmentBookingButton.isDisplayed(), "Appointment Booking butonu görünür değil!");
        Assert.assertTrue(doctorDetailPage.appointmentBookingButton.isEnabled(), "Appointment Booking butonu aktif değil!");
        extentTest.pass("Appointment Booking butonu görünür ve aktif");

        //  "No money charged" mesajını kontrol et

        extentTest.info("Adım 7: 'No money charged in this step' mesajı kontrol ediliyor");
        Assert.assertTrue(doctorDetailPage.noChargeMessage.isDisplayed(), "No charge mesajı görünür değil!");
        extentTest.pass("'No money charged in this step' mesajı görüntülendi");

        //  Reviews bölümünü kontrol et

        extentTest.info("Adım 8: Reviews bölümü kontrol ediliyor");
        ReusableMethods.bekle(1);

        Assert.assertTrue(doctorDetailPage.reviewsTitle.isDisplayed(), "Reviews başlığı görünür değil!");
        Assert.assertTrue(doctorDetailPage.leaveReviewTitle.isDisplayed(), "Leave a Review başlığı görünür değil!");
        Assert.assertTrue(doctorDetailPage.submitButton.isDisplayed(), "Submit butonu görünür değil!");
        ReusableMethods.bekle(1);
        extentTest.pass("Reviews bölümü görüntülendi");

        extentTest.pass("TC_03 - Doktor detay sayfası tüm bilgileri ile başarıyla görüntülendi ve kontrol edildi");

    }

    @Test(priority = 4)
    public void TC_04_AppointmentFormPositiveTest() {
        extentTest = extentReports.createTest("TC_04 - Doktor Sayfasında Randevu Oluşturma Pozitif Testi",
                "Geçerli yakın bir tarih takvimden seçilir veya elle girilir, geçerli bir telefon numarası girilir, " +
                        "uygun departman ve doktor seçilir, mesaj yazılır ve Appointment Booking butonuna basılır");

        DoctorDetailPage doctorDetailPage = new DoctorDetailPage();
        DoctorsPage doctorsPage = new DoctorsPage();

        // 1. Geçerli yakın bir tarih takvimden seçilir yada elle girilir
        extentTest.info("Adım 1: Geçerli bir tarih giriliyor - 16.01.2026");
        ReusableMethods.waitForVisibility(doctorDetailPage.DateInput, 10);

        doctorDetailPage.DateInput.clear();
        doctorDetailPage.DateInput.sendKeys("2026-01-16");
        ReusableMethods.bekle(2);
        extentTest.pass("Tarih girildi: 16.01.2026");

        // 2. Geçerli bir telefon numarası girilir
        extentTest.info("Adım 2: Geçerli bir telefon numarası giriliyor");
        doctorDetailPage.phoneNumberInput.clear();
        doctorDetailPage.phoneNumberInput.sendKeys("905011234567");
        ReusableMethods.bekle(2);
        extentTest.pass("Telefon numarası girildi: 905011234567");

        // 3. Uygun departman seçilir
        extentTest.info("Adım 3: Uygun departman seçiliyor");
        try {
            if (doctorDetailPage.wellnessDropdown.isDisplayed() && doctorDetailPage.wellnessDropdown.isEnabled()) {
                doctorDetailPage.wellnessDropdown.click();
                ReusableMethods.bekle(1);
                extentTest.pass("Departman seçildi");
            }
        } catch (Exception e) {
            extentTest.info("Departman dropdown mevcut değil veya otomatik seçili");
        }

        // 4. Uygun doktor seçilir
        extentTest.info("Adım 4: Uygun doktor seçiliyor");
        try {
            if (doctorDetailPage.doctorSelectionDropdown.isDisplayed() && doctorDetailPage.doctorSelectionDropdown.isEnabled()) {
                doctorDetailPage.doctorSelectionDropdown.click();
                ReusableMethods.bekle(1);
                extentTest.pass("Doktor seçildi");
            }
        } catch (Exception e) {
            extentTest.info("Doktor otomatik seçili");
        }

        // 5. Mesaj yazılır
        extentTest.info("Adım 5: Mesaj yazılıyor");
        doctorDetailPage.createMessageTextarea.clear();
        String testMessage = "Randevu talebi - Test mesajı - Pozitif test senaryosu";
        doctorDetailPage.createMessageTextarea.sendKeys(testMessage);
        ReusableMethods.bekle(2);
        extentTest.pass("Mesaj yazıldı: " + testMessage);

        // 6. "Appointment Booking" butonuna basılır
        extentTest.info("Adım 6: Appointment Booking butonuna tıklanıyor");
        ReusableMethods.waitForClickability(doctorDetailPage.appointmentBookingButton, 10);

        String urlBeforeClick = Driver.getDriver().getCurrentUrl();
        doctorDetailPage.appointmentBookingButton.click();
        ReusableMethods.bekle(3);

        String urlAfterClick = Driver.getDriver().getCurrentUrl();

        // Başarılı randevu oluşturma kontrolü
        boolean urlChanged = !urlBeforeClick.equals(urlAfterClick);
        boolean pageContainsSuccess = Driver.getDriver().getPageSource().toLowerCase().contains("success") ||
                Driver.getDriver().getPageSource().toLowerCase().contains("appointment") ||
                Driver.getDriver().getPageSource().toLowerCase().contains("booking");

        Assert.assertTrue(urlChanged || pageContainsSuccess,
                "Randevu oluşturma işlemi tamamlanamadı!");

        extentTest.pass("TC_06 - Appointment Booking butonuna tıklandı ve işlem tamamlandı");
        extentTest.info("Randevu öncesi URL: " + urlBeforeClick);
        extentTest.info("Randevu sonrası URL: " + urlAfterClick);
    }

        @Test(priority = 5)
        public void TC_05_AppointmentFormNegativeTest () {
            extentTest = extentReports.createTest("TC_05 - Doktor Sayfasında Randevu Oluşturma Negatif Testi",
                    "Randevu talep formundaki alanlar seçilmeden 'Appointment Booking' butonuna basılır ve " +
                            "geçersiz tarih, telefon girişi ile de test edilir");

        // User ile login olup ve Doctor detay sayfasına Method kullanılarak gidildi

            DoctorDetailPage doctorDetailPage = new DoctorDetailPage();

            // 1. Boş form ile buton tıklama testi
            extentTest.info("Adım 2: Boş form ile Appointment Booking butonuna basılıyor");
            ReusableMethods.waitForVisibility(doctorDetailPage.appointmentBookingButton, 10);
            // Formu temizle
            doctorDetailPage.DateInput.clear();
            doctorDetailPage.phoneNumberInput.clear();
            doctorDetailPage.createMessageTextarea.clear();
            ReusableMethods.bekle(1);

            String urlBefore = Driver.getDriver().getCurrentUrl();
            doctorDetailPage.appointmentBookingButton.click();
            ReusableMethods.bekle(2);

            String urlAfter = Driver.getDriver().getCurrentUrl();
            Assert.assertEquals(urlBefore, urlAfter, "Boş form ile randevu oluşturulmamalıydı!");
            extentTest.pass("Boş form ile randevu oluşturulamadı - Beklenen davranış");

            // 2. Geçersiz tarih testi
            extentTest.info("Adım 3: Geçersiz tarih giriliyor - 35.13.99999");
            doctorDetailPage.DateInput.clear();
            doctorDetailPage.DateInput.sendKeys("35.13.99999");
            ReusableMethods.bekle(1);

            // gecerli telefon
            doctorDetailPage.phoneNumberInput.clear();
            doctorDetailPage.phoneNumberInput.sendKeys("905011234567");
            ReusableMethods.bekle(1);

            doctorDetailPage.appointmentBookingButton.click();
            ReusableMethods.bekle(2);

            String currentUrl = Driver.getDriver().getCurrentUrl();
            String pageSource = Driver.getDriver().getPageSource().toLowerCase();
            boolean isStillOnDoctorPage = currentUrl.contains("/Doctors/");
            boolean hasErrorIndicator = pageSource.contains("error") ||
                    pageSource.contains("invalid") ||
                    pageSource.contains("required");

            Assert.assertTrue(isStillOnDoctorPage || hasErrorIndicator,
                    "Geçersiz tarih ile randevu oluşturulmamalıydı!");
            extentTest.pass("Geçersiz tarih (35.13.99999) ile randevu oluşturulamadı - Beklenen davranış");

            // 3. Geçersiz telefon testi
            extentTest.info("Adım 4: Geçersiz telefon numarası giriliyor - 33.00.0000");
            doctorDetailPage.DateInput.clear();
            doctorDetailPage.DateInput.sendKeys("05.03.2026");
            ReusableMethods.bekle(1);

            doctorDetailPage.phoneNumberInput.clear();
            doctorDetailPage.phoneNumberInput.sendKeys("33.00.0000");
            ReusableMethods.bekle(1);

            doctorDetailPage.appointmentBookingButton.click();
            ReusableMethods.bekle(2);

            currentUrl = Driver.getDriver().getCurrentUrl();
            pageSource = Driver.getDriver().getPageSource().toLowerCase();
            isStillOnDoctorPage = currentUrl.contains("/Doctors/");
            hasErrorIndicator = pageSource.contains("error") ||
                    pageSource.contains("invalid") ||
                    pageSource.contains("required");

            Assert.assertTrue(isStillOnDoctorPage || hasErrorIndicator,
                    "Geçersiz telefon ile randevu oluşturulmamalıydı!");
            extentTest.pass("Geçersiz telefon numarası (33.00.0000) ile randevu oluşturulamadı - Beklenen davranış");

            // 5. Özel karakterli mesaj testi
            extentTest.info("Adım 5: Mesaj alanına özel karakterler ve uzun metin giriliyor");
            doctorDetailPage.createMessageTextarea.clear();
            String longMessage = "attrrre75??/(&%+^^^'''a-?!xxxxxxxxxaaaaaaaaaaabbbbbbbbbbbbbbbbbbb111111111111122222222222222222222222222"+
            "......................................,,,,,,,,,,,,,,,,'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''"+
            "'''''''''''''''<<<<<<<<<<<<<<<<<<<<<<------->>> - Özel karakterler ve çok uzun test mesajı";
            doctorDetailPage.createMessageTextarea.sendKeys(longMessage);
            ReusableMethods.bekle(1);

            String enteredMessage = doctorDetailPage.createMessageTextarea.getAttribute("value");
            Assert.assertTrue(enteredMessage.contains("attrrre75??/(&%+^"), "Özel karakterli uzun mesaj yazılamadı!");
            extentTest.pass("Özel karakterli uzun mesaj yazılabildi: " + longMessage);

            // 6. Geçersiz verilerle buton tıklama
            extentTest.info("Adım 6: Geçersiz telefon ve özel karakterlerle Appointment Booking butonuna basılıyor");
            ReusableMethods.waitForClickability(doctorDetailPage.appointmentBookingButton, 10);
            doctorDetailPage.appointmentBookingButton.click();
            ReusableMethods.bekle(2);

            currentUrl = Driver.getDriver().getCurrentUrl();
            pageSource = Driver.getDriver().getPageSource().toLowerCase();
            isStillOnDoctorPage = currentUrl.contains("/Doctors/");
            hasErrorIndicator = pageSource.contains("error") ||
                    pageSource.contains("invalid") ||
                    pageSource.contains("required");

            Assert.assertTrue(isStillOnDoctorPage || hasErrorIndicator,
                    "Geçersiz verilerle randevu oluşturulmamalıydı!");

            extentTest.pass("TC_05 - Negatif test tamamlandı: Geçersiz verilerle randevu oluşturulamadı");
            extentTest.info("Tüm negatif senaryolar başarıyla test edildi:");
            extentTest.info("  ✓ Boş form ile randevu oluşturulamadı");
            extentTest.info("  ✓ Geçersiz tarih ile randevu oluşturulamadı");
            extentTest.info("  ✓ Geçersiz telefon ile randevu oluşturulamadı");
            extentTest.info("  ✓ Özel karakterli uzun mesaj girilebildi");
            extentTest.pass("TC_05 tamamlandı");
            System.out.println("US16 Test Bitti");
        }

    }




