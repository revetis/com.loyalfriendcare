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
        extentTest = extentReports.createTest("US_05_TC01_BodySectionTitlesVisible","Anasayfa body bölümündeki başlıkların görünürlüğünü doğrular");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        HomePageBodySection homesection = new HomePageBodySection();

        ReusableMethods.waitForVisibility(homesection.departmentsTitle, 5);
        ReusableMethods.scrollToElement(homesection.departmentsTitle);
        Assert.assertTrue(homesection.departmentsTitle.isDisplayed());
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

            Assert.assertTrue(Driver.getDriver().getCurrentUrl().toLowerCase().contains("dermatology"));

            ReusableMethods.waitForVisibility(
                    departmentsPageServices.dermatologyHeaderTitle, 5
            );

            Assert.assertTrue(
                    departmentsPageServices.dermatologyHeaderTitle.isDisplayed()
            );

            extentTest.pass("Dermatology detay sayfası başarıyla doğrulandı.");
    }
}
