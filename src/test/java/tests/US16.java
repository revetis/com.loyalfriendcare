package tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import pages.user_pages.DoctorDetailPage;
import pages.user_pages.DoctorsPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.time.Duration;

public class US16 extends TestBaseRapor {

    @Test(priority = 1)
    public void TC_01_UserLoginTest() {
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

        ReusableMethods.waitForVisibility(loginPage.emailAddressInput, 10);
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
    }

    @Test(priority = 2, dependsOnMethods = "TC_01_UserLoginTest")
    public void TC_02_HomePageToDoctorsPageNavigationTest() {
        extentTest = extentReports.createTest("TC_02 - Kayıtlı Kullanıcı, Home Page Sayfasından Doctors Sayfasına Erişebilmeli",
                "Kayıtlı kullanıcı ile login olduktan sonra Home page header bölümünde 'Doctors' tabına üzerine mouse ile gidilerek tıklanır");

        Layout layout = new Layout();


        // 1. Kayıtlı kullanıcı ile login olduktan sonra Home page
      //  extentTest.info("Adım 1: Kullanıcı Home Page'de, header'daki Doctors linkine mouse ile gidiliyor");
       // ReusableMethods.waitForVisibility(layout.headerDoctorsLink, 10);
       // ReusableMethods.hover(layout.headerDoctorsLink);
      //  extentTest.pass("Doctors linkine mouse ile gidildi");


        // 2. Header'dan Doctors linkine tıklanır
        extentTest.info("Adım 2: Header'dan Doctors linkine tıklanıyor");
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));wait.until(ExpectedConditions.elementToBeClickable(layout.headerDoctorsLink));

        layout.headerDoctorsLink.click();
        ReusableMethods.bekle(2);
        extentTest.pass("Doctors linkine tıklandı");

        // 3. Home page header bölümünde "Doctors" tabına tıklanır
        extentTest.info("Adım 3: Doctors sayfasına yönlendirilme kontrolü yapılıyor");
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("doctors") || currentUrl.contains("doctor"),
                "Doctors sayfasına yönlendirilmedi! Mevcut URL: " + currentUrl);
        extentTest.pass("TC_02 - Doctors sayfasına başarıyla erişildi");
    }

    @Test(priority = 3, dependsOnMethods = "TC_02_HomePageToDoctorsPageNavigationTest")
    public void TC_03_DoctorsPageDoctorInformationTest() {
        extentTest = extentReports.createTest("TC_03 - Doctors Sayfasındaki Doktorların Bilgilerini İnceleyebilmeli",
                "Doctors isminde tüm doktorların olduğu yeni bir sayfaya gidilir. Sayfanın body kısmında doktorlar ve bilgileri yer alır");

        DoctorsPage doctorsPage = new DoctorsPage();

        // 1. Doctors isminde tüm doktorların olduğu sayfada doktorlar görünür
        extentTest.info("Adım 1: Doctors sayfasındaki doktorların listesi kontrol ediliyor");
        ReusableMethods.waitForVisibility(doctorsPage.doctorsList.get(0), 10);
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

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Doctors/"), "Doktor detay sayfasına yönlendirilmedi!");
        extentTest.pass("TC_03 - Seçilen doktor '" + selectedDoctorName + "' için detay sayfasına yönlendirildi");


        extentTest.info("NOT: DoctorsPage sınıfı oluşturulmalı ve locator'lar eklenmelidir");
        extentTest.warning("TC_03 - Test için DoctorsPage.java dosyası gereklidir");
    }

    @Test(priority = 4, dependsOnMethods = "TC_03_DoctorsPageDoctorInformationTest")
    public void TC_04_SelectedDoctorPageAccessTest() {
        extentTest = extentReports.createTest("TC_04 - Seçilen Doktorun Sayfasına Erişebilmeli",
                "Doctors sayfasında seçilen bir Doktorun ismine tıklanarak o doktorun sayfasına gidilir kontrol edilir");


        DoctorDetailPage doctorDetailPage = new DoctorDetailPage();

        // 1. Doktor detay sayfasına yönlendirildiğini kontrol et
        extentTest.info("Adım 1: Doktor detay sayfasına yönlendirilme kontrolü yapılıyor");
        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/Doctors/"), "Doktor detay sayfasında değil!");
        extentTest.pass("Doktor detay sayfasına başarıyla yönlendirildi");

        // 2. Doktor header başlığını kontrol et
        extentTest.info("Adım 2: Doktor header başlığı kontrol ediliyor");
        ReusableMethods.waitForVisibility(doctorDetailPage.doctorHeaderTitle, 10);
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

        Assert.assertTrue(doctorDetailPage.doctorDetailInfos.isDisplayed(), "Doktor detay bilgisi görünür değil!");
        extentTest.info(doctorDetailPage.doctorDetailInfos.getText());
        extentTest.pass("Tüm doktor bilgileri başarıyla görüntülendi");

        // 5. Appointment Booking formunu kontrol et
        extentTest.info("Adım 5: Appointment Booking formu kontrol ediliyor");
        Assert.assertTrue(doctorDetailPage.appointmentBookingTitle.isDisplayed(), "Appointment Booking başlığı görünür değil!");
        Assert.assertTrue(doctorDetailPage.DateInput.isDisplayed(), "Tarih input'u görünür değil!");
        Assert.assertTrue(doctorDetailPage.phoneNumberInput.isDisplayed(), "Telefon input'u görünür değil!");
        Assert.assertTrue(doctorDetailPage.createMessageTextarea.isDisplayed(), "Message textarea görünür değil!");
        ReusableMethods.bekle(1);
        extentTest.pass("Appointment Booking formu tüm alanları ile görüntülendi");

        // 6. Randevu olustur butonunu kontrol et
        extentTest.info("Adım 6: Appointment Booking butonu kontrol ediliyor");
        Assert.assertTrue(doctorDetailPage.appointmentBookingButton.isDisplayed(), "Appointment Booking butonu görünür değil!");
        Assert.assertTrue(doctorDetailPage.appointmentBookingButton.isEnabled(), "Appointment Booking butonu aktif değil!");
        extentTest.pass("Appointment Booking butonu görünür ve aktif");

        // 7. "No money charged" mesajını kontrol et
        extentTest.info("Adım 7: 'No money charged in this step' mesajı kontrol ediliyor");
        Assert.assertTrue(doctorDetailPage.noChargeMessage.isDisplayed(), "No charge mesajı görünür değil!");
        extentTest.pass("'No money charged in this step' mesajı görüntülendi");

        // 8. Reviews bölümünü kontrol et
        extentTest.info("Adım 8: Reviews bölümü kontrol ediliyor");
        ReusableMethods.bekle(1);

        Assert.assertTrue(doctorDetailPage.reviewsTitle.isDisplayed(), "Reviews başlığı görünür değil!");
        Assert.assertTrue(doctorDetailPage.leaveReviewTitle.isDisplayed(), "Leave a Review başlığı görünür değil!");
        Assert.assertTrue(doctorDetailPage.submitButton.isDisplayed(), "Submit butonu görünür değil!");
        ReusableMethods.bekle(1);
        extentTest.pass("Reviews bölümü görüntülendi");

        extentTest.pass("TC_04 - Doktor detay sayfası tüm bilgileri ile başarıyla görüntülendi ve kontrol edildi");

    }
}



