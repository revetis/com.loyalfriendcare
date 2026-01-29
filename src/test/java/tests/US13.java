package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.HomePageBodySection;
import pages.common_pages.VaccineDetailPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US13 extends TestBaseRapor {


    @Test(priority = 1)
    public void US_013_TC01_VaccinationsSectionVisible() {

        extentTest = extentReports.createTest("US_013_TC01",
                "Vaccinations for Pets bölümü ve aşı kartı görüntülenmeli");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        HomePageBodySection homeSection = new HomePageBodySection();

        ReusableMethods.scrollToElement(homeSection.vaccinationsTitle);
        ReusableMethods.waitForVisibility(homeSection.vaccinationsTitle, 5);

        Assert.assertTrue(homeSection.vaccinationsTitle.isDisplayed());
        extentTest.pass("Vaccinations for Pets başlığı görüntülendi.");

        ReusableMethods.waitForVisibility(homeSection.felineHerpesvirusVaccineImg, 5);
        Assert.assertTrue(homeSection.felineHerpesvirusVaccineImg.isDisplayed());
        extentTest.pass("Feline Herpesvirus Vaccine kartı görüntülendi.");


    }

    @Test(priority = 2)
    public void US_013_TC02_SelectVaccineOpensDetail() {

        extentTest = extentReports.createTest("US_013_TC02",
                "Feline Herpesvirus Vaccine seçilince detail sayfası açılmalı ve randevu alanı görünmeli");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        HomePageBodySection homeSection = new HomePageBodySection();

        ReusableMethods.scrollToElement(homeSection.vaccinationsTitle);
        ReusableMethods.waitForVisibility(homeSection.felineHerpesvirusVaccineLink, 5);

        homeSection.felineHerpesvirusVaccineLink.click();
        extentTest.info("Feline Herpesvirus Vaccine kartına tıklandı.");

        VaccineDetailPage detailPage = new VaccineDetailPage();

        ReusableMethods.waitForVisibility(detailPage.vaccineDetailTitle, 5);
        Assert.assertTrue(detailPage.vaccineDetailTitle.getText().contains("Feline Herpesvirus Vaccine"),
                "Title beklenen aşı adını içermiyor. Actual: " + detailPage.vaccineDetailTitle.getText());
        extentTest.pass("Detail sayfası başlığı görüntülendi: " + detailPage.vaccineDetailTitle.getText());

        ReusableMethods.waitForVisibility(detailPage.appointmentForm, 5);
        Assert.assertTrue(detailPage.appointmentForm.isDisplayed(), "Appointment form görünmüyor");
        extentTest.pass("Appointment form görüntülendi.");
    }


    @Test(priority = 3)
    public void US_013_TC03_AppointmentBooking_ValidDate_Submit() {

        extentTest = extentReports.createTest(
                "US_013_TC03_Appointment Booking Form Submit",
                "Kullanıcı formu doldurup Appointment Booking ile talep oluşturabilmeli"
        );

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        HomePageBodySection homeSection = new HomePageBodySection();


        // 1) Vaccinations'a scroll + kartı seç
        ReusableMethods.scrollToElement(homeSection.vaccinationsTitle);
        ReusableMethods.waitForVisibility(homeSection.felineHerpesvirusVaccineLink, 5);
        homeSection.felineHerpesvirusVaccineLink.click();
        extentTest.info("Feline Herpesvirus Vaccine detay sayfasına gidildi.");

        VaccineDetailPage detailPage = new VaccineDetailPage();

        // 2) Form görünüyor mu? (Sen zaten appointmentForm eklemiştin)
        ReusableMethods.waitForVisibility(detailPage.appointmentForm, 5);
        Assert.assertTrue(detailPage.appointmentForm.isDisplayed(), "Appointment form görünmüyor!");
        extentTest.pass("Appointment form görüntülendi.");

        // 3) Date: bugünkü tarih + 3 gün (gelecek tarih)
        String futureDate = ReusableMethods.getFutureDate(3);
        detailPage.dateInput.clear();          // <-- BURAYA
        detailPage.dateInput.sendKeys(futureDate);
        extentTest.info("Date girildi: " + futureDate);


        // 4) Phone
        String phone = "555" + (int) (Math.random() * 10000000); // 10 hane
        detailPage.phoneNumberInput.clear();
        detailPage.phoneNumberInput.sendKeys(phone);

        // 5) Wellness dropdown: 2. sıradaki seç
        detailPage.wellnessDropdown.click();
        ReusableMethods.bekle(1);
        detailPage.wellnessOptions.get(1).click();
        extentTest.info("Wellness 2. seçenek seçildi.");

        // 6) Doctor dropdown: 2. sıradaki seç
        detailPage.doctorDropdown.click();
        ReusableMethods.bekle(1);
        detailPage.doctorOptions.get(1).click();
        extentTest.info("Doctor 2. seçenek seçildi.");

        // 7) Message
        String msg = "Automation test - appointment request.";
        detailPage.messageTextArea.sendKeys(msg);
        extentTest.info("Message girildi.");

        // 8) Submit
        detailPage.appointmentBookingButton.click();
        extentTest.info("Appointment Booking butonuna tıklandı.");

        ReusableMethods.waitForVisibility(detailPage.successAlert, 5);
        Assert.assertTrue(detailPage.successAlert.isDisplayed(),
                "Success mesajı görüntülenmedi!");
        extentTest.pass("Randevu başarıyla oluşturuldu mesajı görüntülendi.");

    }

    @Test(priority = 4)
    public void US_013_TC03_AppointmentBooking_InvalidDate_Submit() {

        extentTest = extentReports.createTest(
                "US_013_TC03_Negative",
                "Geçmiş tarih ile randevu oluşturulamamalı (negatif senaryo)"
        );

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        HomePageBodySection homeSection = new HomePageBodySection();

        // Vaccine detayına git
        ReusableMethods.scrollToElement(homeSection.vaccinationsTitle);
        ReusableMethods.waitForVisibility(homeSection.felineHerpesvirusVaccineLink, 5);
        homeSection.felineHerpesvirusVaccineLink.click();
        extentTest.info("Feline Herpesvirus Vaccine detay sayfasına gidildi.");

        VaccineDetailPage detailPage = new VaccineDetailPage();
        ReusableMethods.waitForVisibility(detailPage.appointmentForm, 5);
        Assert.assertTrue(detailPage.appointmentForm.isDisplayed(), "Appointment form görünmüyor!");
        extentTest.pass("Appointment form görüntülendi.");

        // Geçmiş tarih (bugün -1)
        String pastDate = ReusableMethods.getFutureDate(-1);
        detailPage.dateInput.clear();
        detailPage.dateInput.sendKeys(pastDate);
        extentTest.info("Geçmiş tarih girildi: " + pastDate);

        // Phone
        String phone = "555" + (int) (Math.random() * 10000000);
        detailPage.phoneNumberInput.clear();
        detailPage.phoneNumberInput.sendKeys(phone);
        extentTest.info("Phone girildi: " + phone);

        // Wellness dropdown: 2. seçenek
        detailPage.wellnessDropdown.click();
        ReusableMethods.bekle(1);
        detailPage.wellnessOptions.get(1).click();
        extentTest.info("Wellness 2. seçenek seçildi.");

        // Doctor dropdown: 2. seçenek
        detailPage.doctorDropdown.click();
        ReusableMethods.bekle(1);
        detailPage.doctorOptions.get(1).click();
        extentTest.info("Doctor 2. seçenek seçildi.");

        // Message
        String msg = "Automation test - appointment request (negative).";
        detailPage.messageTextArea.sendKeys(msg);
        extentTest.info("Message girildi.");

        // Submit
        detailPage.appointmentBookingButton.click();
        extentTest.info("Appointment Booking butonuna tıklandı.");


        boolean successShown = detailPage.successAlerts.size() > 0;

        if (successShown) {
            extentTest.fail("BUG: Geçmiş tarih ile success mesajı görüntülendi!");
            Assert.fail("BUG: Geçmiş tarih ile success mesajı görüntülendi!");
        } else {
            extentTest.pass("Geçmiş tarih ile success mesajı çıkmadı. Negatif senaryo başarılı.");
            Assert.assertEquals(detailPage.successAlerts.size(), 0,
                    "Geçmiş tarih ile success mesajı çıkmamalıydı!");
        }
    }}