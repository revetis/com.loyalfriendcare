package tests;

import com.github.javafaker.Faker;
import org.apache.xmlbeans.impl.xb.xsdschema.Facet;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.common_pages.HomePageDepartmentSection;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import pages.common_pages.RegisterPage;
import pages.user_pages.DepartmentPage;
import pages.user_pages.UserAppointmentFormPage;
import pages.user_pages.UserPage;
import utilities.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class US15 extends TestBaseRapor {

    Layout layout = new Layout();
    RegisterPage registerPage = new RegisterPage();
    LoginPage loginPage = new LoginPage();
    Actions actions = new Actions(Driver.getDriver());
    UserPage userPage = new UserPage();
    SoftAssert softAssert = new SoftAssert();
    HomePageDepartmentSection homePageDepartmentSection = new HomePageDepartmentSection();
    DepartmentPage departmentPage = new DepartmentPage();
    Faker faker =new Faker();
    Random random = new Random();
    UserAppointmentFormPage userAppointmentFormPage = new UserAppointmentFormPage();


    @Test
    public void tc01_step01() {

        extentTest = extentReports.createTest("Header departments linki kontrol",
                "Mouse ile Header'da bulunan departments başlığı üzerine gidilir ve departmanların görüntülendiği doğrulanır");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        layout.signInLink.click();
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("user_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("user_password"));
        loginPage.signInButton.click();

        actions.moveToElement(layout.headerDepartmentsLink).perform();
        ReusableMethods.bekle(1);

        Assert.assertTrue(userPage.userDepartmentsinHeaderDropdownMenü.isDisplayed());
        extentTest.info("Header departments dropdown menü kontrol edildi");


        actions.moveToElement(layout.headerDepartmentsLink).perform();

        int headerDropdownSize = userPage.userDepartmentsInHeaderDropdownMenuList.size();

        for (int i = 0; i < headerDropdownSize; i++) {

            actions.moveToElement(layout.headerDepartmentsLink).perform();
            userPage.userDepartmentsInHeaderDropdownMenuList.get(i).click();
            ReusableMethods.bekle(1);

            String departmanName = userPage.departmanTitle.getText().toLowerCase().replaceAll("[\\s\\p{P}]", "");

            softAssert.assertTrue((Driver.getDriver().getCurrentUrl().replaceAll("[^a-zA-Z0-9çÇğĞıİöÖşŞüÜ]", "").toLowerCase().contains(departmanName)));
            extentTest.info("Header departments dropdown menüden " + departmanName + " kontrol edildi");

            Driver.getDriver().navigate().back();
            ReusableMethods.bekle(1);

        }


        int bodyDepartmentsListSize = userPage.userHomePageBodyDepartmentsList.size();
        int headerDepartmentsListSize = userPage.userDepartmentsInHeaderDropdownMenuList.size();

        softAssert.assertEquals(bodyDepartmentsListSize, headerDepartmentsListSize,
                "Body bölümünde bulunan departman listesi ile header'da bulunan departman listesi eşleşmiyor");


        int bodyDepartmentsList = userPage.userHomePageBodyDepartmentsList.size();

        for (int i = 1; i < bodyDepartmentsList; i++) {

            userPage.userHomePageBodyDepartmentsList.get(i).click();

            //boşluklar ve noktalama işratlerini kaldır
            String departmanName = userPage.departmanTitle.getText().toLowerCase().replaceAll("[\\s\\p{P}]", "");

            // Sayfanın açıldığını doğrula
            softAssert.assertTrue((Driver.getDriver().getCurrentUrl().replaceAll("[^a-zA-Z0-9çÇğĞıİöÖşŞüÜ]", "").toLowerCase().contains(departmanName)));
            extentTest.info("Homepage body departments menüden " + departmanName + " kontrol edildi");
            // Geri dön
            Driver.getDriver().navigate().back();
        }

        Driver.getDriver().navigate().back();


        layout.headerDepartmentsLink.click();

        String expectedUrlIcerik = "Departments";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expectedUrlIcerik));

        Driver.getDriver().navigate().back();


        homePageDepartmentSection.departmentsLink.click();
        String expectedUrlIcerik1 = "Departments";
        String actualUrl1 = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl1.contains(expectedUrlIcerik1));


        Assert.assertTrue(userPage.departmentsTable.isDisplayed());
        extentTest.info("Departments sayfasında departman listesinin görünür olduğu kontrol edildi.");


        int departmentsSize = userPage.departmentsSayfasiList.size();

        for (int i = 1; i < departmentsSize; i++) {

            userPage.departmentsSayfasiList.get(i).click();

            //boşluklar ve noktalama işratlerini kaldır
            String departmanName = userPage.departmanTitle.getText().toLowerCase().replaceAll("[\\s\\p{P}]", "");

            // Sayfanın açıldığını doğrula
            softAssert.assertTrue((Driver.getDriver().getCurrentUrl().replaceAll("[^a-zA-Z0-9çÇğĞıİöÖşŞüÜ]", "").toLowerCase().contains(departmanName)));
            extentTest.info("Departments sayfasında " + departmanName + " kontrol edildi");

            //Randevu formunun görüntülendiğini doğrula
            Assert.assertTrue(departmentPage.departmentFormContainer.isDisplayed());
            extentTest.info(departmanName + " departmanında Appointment Booking kontrol edildi");

            // Geri dön
            Driver.getDriver().navigate().back();
        }

        softAssert.assertAll();
    }

    @Test
    public void tc02(){
        extentTest = extentReports.createTest("Pozitif Randevu Kaydı Oluşturma Testi");

        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Departments/wellness");

        String tarih = LocalDateTime.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        departmentPage.departmentFormDateInput.sendKeys(tarih);

        ReusableMethods.bekle(1);

        String trPhone = faker.numerify("+90 5## ### ## ##");
        departmentPage.departmentFormPhoneNumberInput.sendKeys(trPhone);

        userPage.appointmentFormDepartmentsLink.click();
        ReusableMethods.bekle(1);
        int randomIndexDepartments = random.nextInt(userPage.appointmentFormDepartmentsList.size());
        ReusableMethods.bekle(1);
        userPage.appointmentFormDepartmentsList.get(randomIndexDepartments).click();

        ReusableMethods.bekle(1);

        userPage.appointmentFormDoctorsLink.click();
        ReusableMethods.bekle(1);
        int randomIndexDoctors = random.nextInt(userPage.appointmentFormDoctorsList.size());
        ReusableMethods.bekle(1);
        userPage.appointmentFormDoctorsList.get(randomIndexDoctors).click();

        String[] messages = {
                "Randevu almak istiyorum",
                "Bilgi almak için yazıyorum",
                "Doktor hakkında soru soracağım",
                "Uygun saatleri öğrenmek istiyorum",
                "İlk defa randevu alacağım"
        };

        String mesaj = messages[random.nextInt(messages.length)];
        departmentPage.departmentFormTextArea.sendKeys(mesaj);

        ReusableMethods.bekle(1);

        departmentPage.departmentFormSubmitButton.click();

        ReusableMethods.bekle(1);

        Assert.assertTrue(userAppointmentFormPage.successMessage.isDisplayed());
        extentTest.pass("Randevu kaydı başarılı bir şekilde oluşturuldu.");

    }

    @Test
    public void tc03(){
        extentTest = extentReports.createTest("Negatif Randevu Oluşturma Testi");

        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/Departments/wellness");

        departmentPage.departmentFormSubmitButton.click();
        softAssert.assertFalse(userAppointmentFormPage.successMessage.isDisplayed(),"Randevu formu boş olarak gönderilse de kayıt başarılı oldu.");

        softAssert.assertAll();




    }
}
