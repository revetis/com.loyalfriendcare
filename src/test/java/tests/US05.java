package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.user_pages.DepartmentsPageServices;
import pages.user_pages.HomePageBodySection;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US05 extends TestBaseRapor {

    @Test
    public void US_005_TC01_BodySectionTitlesVisible() {
        extentTest = extentReports.createTest("US_05_TC01_BodySectionTitlesVisible", "Anasayfa body bölümündeki başlıkların görünürlüğünü doğrular");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        HomePageBodySection homesection = new HomePageBodySection();

        ReusableMethods.scrollToElement(homesection.departmentsTitle);
        ReusableMethods.waitForVisibility(homesection.departmentsTitle, 5);
        Assert.assertTrue(homesection.departmentsTitle.isDisplayed());

        ReusableMethods.scrollToElement(homesection.popularDoctorsTitle);
        ReusableMethods.waitForVisibility(homesection.popularDoctorsTitle, 5);
        Assert.assertTrue(homesection.popularDoctorsTitle.isDisplayed());

        ReusableMethods.scrollToElement(homesection.vaccinationsTitle);
        ReusableMethods.waitForVisibility(homesection.vaccinationsTitle, 5);
        Assert.assertTrue(homesection.vaccinationsTitle.isDisplayed());


        extentTest.pass("US_05_TC01: Anasayfa body bölümündeki başlıkların görünürlüğü başarıyla doğrulandı.");
    }

    @Test
    public void US_05_TC02_DermatologyServiceClickable() {
        extentTest = extentReports.createTest(
                "US_005_TC02_DermatologyServiceClickable",
                "Departments altındaki Dermatology hizmetine tıklanınca detay sayfası açılmalıdır"
        );

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        HomePageBodySection homeSection = new HomePageBodySection();
        DepartmentsPageServices departmentsPageServices = new DepartmentsPageServices();

        ReusableMethods.waitForVisibility(homeSection.departmentsTitle, 5);
        ReusableMethods.scrollToElement(homeSection.departmentsTitle);
        extentTest.info("Departments bölümüne kaydırıldı.");

        ReusableMethods.waitForVisibility(departmentsPageServices.dermatologyTitle, 5);
        Assert.assertTrue(departmentsPageServices.dermatologyTitle.isDisplayed());
        extentTest.info("Dermatology hizmeti listede görüntülendi.");

        departmentsPageServices.dermatologyTitle.click();
        extentTest.info("Dermatology hizmetine tıklandı.");

        ReusableMethods.waitForPageToLoad(5);

        Assert.assertTrue(departmentsPageServices.dermatologyHeaderTitle.getText().toLowerCase().contains("dermatology"));


        ReusableMethods.waitForVisibility(
                departmentsPageServices.dermatologyHeaderTitle, 5
        );

        Assert.assertTrue(
                departmentsPageServices.dermatologyHeaderTitle.isDisplayed()
        );

        extentTest.pass("Dermatology detay sayfası başarıyla doğrulandı.");
    }


    @Test
    public void US_05_TC03_PopularDoctorsServiceClickable() {
        extentTest = extentReports.createTest(
                "US_05_TC03_PopularDoctorsServiceClickable",
                "Departments altındaki Popular Doctors hizmetine tıklanınca detay sayfası açılmalıdır"
        );

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        HomePageBodySection homeSection = new HomePageBodySection();
        DepartmentsPageServices departmentsPageServices = new DepartmentsPageServices();

        ReusableMethods.waitForVisibility(homeSection.popularDoctorsTitle, 5);
        ReusableMethods.scrollToElement(homeSection.popularDoctorsTitle);
        extentTest.info("Popular Doctors bölümüne kaydırıldı.");

        ReusableMethods.waitForVisibility(departmentsPageServices.drEmilyChangTitle, 5);
        Assert.assertTrue(departmentsPageServices.drEmilyChangTitle.isDisplayed());
        extentTest.info("Dr. Emily Chang hizmeti listede görüntülendi.");

        departmentsPageServices.drEmilyChangTitle.click();
        extentTest.info("Dr. Emily Chang hizmetine tıklandı.");

        ReusableMethods.waitForPageToLoad(5);

        Assert.assertTrue(Driver.getDriver().getCurrentUrl().toLowerCase().contains("shawn-beltran"));

        ReusableMethods.waitForVisibility(
                departmentsPageServices.drEmilyChangHeaderTitle, 5
        );

        Assert.assertTrue(
                departmentsPageServices.drEmilyChangHeaderTitle.isDisplayed()
        );

        extentTest.pass("Dr. Emily Chang detay sayfası başarıyla doğrulandı.");
    }

    @Test
    public void US_05_TC04_VaccinationsServiceClickable() {
        extentTest = extentReports.createTest(
                "US_05_TC04_VaccinationsServiceClickable",
                "Vaccinations altındaki Rabies Vaccine hizmetine tıklanınca detay sayfası açılmalıdır"
        );

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        HomePageBodySection homeSection = new HomePageBodySection();
        DepartmentsPageServices departmentsPageServices = new DepartmentsPageServices();

        ReusableMethods.waitForVisibility(homeSection.vaccinationsTitle, 5);
        ReusableMethods.scrollToElement(homeSection.vaccinationsTitle);
        extentTest.info("Vaccinations bölümüne kaydırıldı.");

        ReusableMethods.waitForVisibility(departmentsPageServices.rabiesVaccineTitle, 5);
        Assert.assertTrue(departmentsPageServices.rabiesVaccineTitle.isDisplayed());
        extentTest.info("Rabies Vaccine hizmeti listede görüntülendi.");

        departmentsPageServices.rabiesVaccineTitle.click();
        extentTest.info("Rabies Vaccine hizmetine tıklandı.");

        ReusableMethods.waitForPageToLoad(5);

        Assert.assertTrue(departmentsPageServices.rabiesVaccineHeaderTitle.getText().toLowerCase().trim().contains("rabies vaccine"));

        ReusableMethods.waitForVisibility(
                departmentsPageServices.rabiesVaccineHeaderTitle, 5
        );

        Assert.assertTrue(
                departmentsPageServices.rabiesVaccineHeaderTitle.isDisplayed()
        );

        extentTest.pass("Rabies Vaccine detay sayfası başarıyla doğrulandı.");

    }
    }