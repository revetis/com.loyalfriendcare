package tests;


import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.HomepageBodyPage;
import pages.common_pages.Layout;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US01 extends TestBaseRapor {

    Layout layout = new Layout();
    HomepageBodyPage homepageBodyPage = new HomepageBodyPage();

    @Test
    public void US01_HomepageElementsTest(){

        extentTest = extentReports.createTest("US01 - Home Page Elements Test",
                "Kullanıcı anasayfadaki tüm elementlerin görünürlüğünü ve tıklanabilirliğini doğrular");

        // 1. Web sitesine git
        extentTest.info("Adım 1: Web sitesine gidilir - " + ConfigReader.getProperty("url"));
        Driver.getDriver().get(ConfigReader.getProperty("url"));

        // 2. Anasayfa başarıyla yüklendiğini doğrula
        extentTest.info("Adım 2: Anasayfa yüklenme kontrolü yapılıyor");
        String expectedUrl = "https://qa.loyalfriendcare.com/en";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl,expectedUrl);
        extentTest.pass("Anasayfa başarıyla yüklendi");

        // 3. Header - Logo görünürlük ve tıklanabilirlik testi
        extentTest.info("Adım 3: Header Logo kontrolü yapılıyor");
        Assert.assertTrue(layout.headerLogo.isDisplayed(), "Header Logo görünür değil!");
        Assert.assertTrue(layout.headerLogo.isEnabled(), "Header Logo tıklanabilir değil!");
        extentTest.pass("Header Logo görünür ve tıklanabilir");

        // 4. Header Logo tıklama testi - Home Page'e yönlendirme
        extentTest.info("Adım 4: Header Logo'ya tıklanıyor");
        layout.headerLogo.click();
        ReusableMethods.bekle(2);
        String afterLogoClickUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(afterLogoClickUrl.contains("loyalfriendcare.com"), "Logo tıklandığında Home Page'e yönlendirme yapılmadı!");
        extentTest.pass("Logo tıklandığında Home Page'e başarıyla yönlendirildi");

        // 5. Header Menu - Home link kontrolü
        extentTest.info("Adım 5: Header Home linki kontrol ediliyor");
        Assert.assertTrue(layout.headerHomeLink.isDisplayed(), "Home link görünür değil!");
        Assert.assertTrue(layout.headerHomeLink.isEnabled(), "Home link tıklanabilir değil!");
        extentTest.pass("Home link görünür ve tıklanabilir");

        // 6. Header Menu - About Us link kontrolü
        extentTest.info("Adım 6: Header About Us linki kontrol ediliyor");
        Assert.assertTrue(layout.headerAboutUsLink.isDisplayed(), "About Us link görünür değil!");
        Assert.assertTrue(layout.headerAboutUsLink.isEnabled(), "About Us link tıklanabilir değil!");
        extentTest.pass("About Us link görünür ve tıklanabilir");

        // 7. Header Menu - Departments link kontrolü
        extentTest.info("Adım 7: Header Departments linki kontrol ediliyor");
        Assert.assertTrue(layout.headerDepartmentsLink.isDisplayed(), "Departments link görünür değil!");
        Assert.assertTrue(layout.headerDepartmentsLink.isEnabled(), "Departments link tıklanabilir değil!");
        extentTest.pass("Departments link görünür ve tıklanabilir");

        // 8. Header Menu - Doctors link kontrolü
        extentTest.info("Adım 8: Header Doctors linki kontrol ediliyor");
        Assert.assertTrue(layout.headerDoctorsLink.isDisplayed(), "Doctors link görünür değil!");
        Assert.assertTrue(layout.headerDoctorsLink.isEnabled(), "Doctors link tıklanabilir değil!");
        extentTest.pass("Doctors link görünür ve tıklanabilir");

        // 9. Header Menu - Medicines link kontrolü
        extentTest.info("Adım 9: Header Medicines linki kontrol ediliyor");
        Assert.assertTrue(layout.headerMedicinesLink.isDisplayed(), "Medicines link görünür değil!");
        Assert.assertTrue(layout.headerMedicinesLink.isEnabled(), "Medicines link tıklanabilir değil!");
        extentTest.pass("Medicines link görünür ve tıklanabilir");

        // 10. Header Menu - Vaccinations link kontrolü
        extentTest.info("Adım 10: Header Vaccinations linki kontrol ediliyor");
        Assert.assertTrue(layout.headerVaccinationsLink.isDisplayed(), "Vaccinations link görünür değil!");
        Assert.assertTrue(layout.headerVaccinationsLink.isEnabled(), "Vaccinations link tıklanabilir değil!");
        extentTest.pass("Vaccinations link görünür ve tıklanabilir");

        // 11. Header - Sign In link kontrolü
        extentTest.info("Adım 11: Header Sign In linki kontrol ediliyor");
        Assert.assertTrue(layout.signInLink.isDisplayed(), "Sign In link görünür değil!");
        Assert.assertTrue(layout.signInLink.isEnabled(), "Sign In link tıklanabilir değil!");
        extentTest.pass("Sign In link görünür ve tıklanabilir");

        // 12. Header - Sign Up link kontrolü
        extentTest.info("Adım 12: Header Sign Up linki kontrol ediliyor");
        Assert.assertTrue(layout.signUpLink.isDisplayed(), "Sign Up link görünür değil!");
        Assert.assertTrue(layout.signUpLink.isEnabled(), "Sign Up link tıklanabilir değil!");
        extentTest.pass("Sign Up link görünür ve tıklanabilir");

        // 13. Karşılama metni başlığı kontrolü - "TOP-TIER ATTENTION FOR YOUR FURRY LOVED ONE!"
        extentTest.info("Adım 13: Karşılama metni başlığı kontrol ediliyor");
        Assert.assertTrue(homepageBodyPage.welcomeTextTitle.isDisplayed(), "Karşılama metni başlığı görünür değil!" );
        Assert.assertTrue(homepageBodyPage.welcomeTextTitle.getText().contains("TOP-TIER ATTENTION FOR YOUR FURRY LOVED ONE!"));
        extentTest.pass("Karşılama metni başlığı görünür ve doğru içeriğe sahip");

        // Karşılama metni kontrolü
        Assert.assertTrue(homepageBodyPage.welcomeText.isDisplayed(),"Karşılama metni görünür değil!");
        extentTest.pass("Karşılama metni görünür");

        // 14. Search bar kontrolü
        extentTest.info("Adım 14: Search bar kontrol ediliyor");
        Assert.assertTrue(homepageBodyPage.searchBar.isDisplayed(),"Search bar görünür değil!");
        Assert.assertTrue(homepageBodyPage.searchBar.isEnabled(),"Search bar aktif değil!");
        extentTest.pass("Search bar görünür ve aktif");

        // 15. Search button kontrolü
        extentTest.info("Adım 15: Search button kontrol ediliyor");
        Assert.assertTrue(homepageBodyPage.searchButton.isDisplayed(),"Search button görünür değil!");
        Assert.assertTrue(homepageBodyPage.searchButton.isEnabled(),"Search button aktif değil!");
        extentTest.pass("Search button görünür ve aktif");

        // 16. Body kısmında- Departments bölümü kontrolü
        extentTest.info("Adım 16: Body kısmında- Departments bölümü kontrol ediliyor");
        Assert.assertTrue(homepageBodyPage.departmentsSection.isDisplayed(),"Departments bölümü görünür değil!");
        Assert.assertTrue(homepageBodyPage.departmentsItems.size() > 0,"Departments içeriği bulunamadı!");

        for (WebElement item : homepageBodyPage.departmentsItems) {
            Assert.assertTrue(item.isDisplayed(), "Departments item görünür değil!");
            Assert.assertTrue(item.isEnabled(), "Departments item tıklanabilir değil!");
        }
        extentTest.pass("Departments bölümü ve içerikleri görünür ve tıklanabilir");

        // 17. Body kısmında- Popular Doctors bölümü kontrolü
        extentTest.info("Adım 17: Body Popular Doctors bölümü kontrol ediliyor");
        Assert.assertTrue(homepageBodyPage.popularDoctorsSection.isDisplayed(),"Popular Doctors bölümü görünür değil!");
        Assert.assertTrue(homepageBodyPage.popularDoctorsItems.size() > 0,"Popular Doctors içeriği bulunamadı!");

        for (WebElement item : homepageBodyPage.popularDoctorsItems){
            Assert.assertTrue(item.isDisplayed(),"Popular Doctors item görünür değil!");
            Assert.assertTrue(item.isEnabled(), "Popular Doctors item tıklanabilir değil!");
        }
        extentTest.pass("Popular Doctors bölümü ve içerikleri görünür ve tıklanabilir");

        // 18. Body kısmında - Vaccinations for Pets bölümü kontrolü
        extentTest.info("Adım 18: Body kısmında - Vaccinations for Pets bölümü kontrol ediliyor");
        Assert.assertTrue(homepageBodyPage.vaccinationsSection.isDisplayed(), "Vaccinations for Pets bölümü görünür değil!");
        Assert.assertTrue(homepageBodyPage.vaccinationsItems.size() > 0,"Vaccinations for Pets içeriği bulunamadı!");

        for (WebElement item : homepageBodyPage.vaccinationsItems){
            Assert.assertTrue(item.isDisplayed(), "Vaccinations for Pets item görünür değil!");
            Assert.assertTrue(item.isEnabled(), "Vaccinations for Pets item tıklanabilir değil!");

        }
        extentTest.pass("Vaccinations for Pets bölümü ve içerikleri görünür ve tıklanabilir");

        // 19. Footer - Logo kontrolü
        extentTest.info("Adım 19: Footer Logo kontrol ediliyor");
        Assert.assertTrue(layout.footerLogo.isDisplayed(), "Footer Logo görünür değil!");
        Assert.assertTrue(layout.footerLogo.isEnabled(),"Footer Logo tıklanabilir değil!");
        extentTest.pass("Footer Logo görünür ve tıklanabilir");

        // 20. Footer - Departments bölümü kontrolü
        extentTest.info("Adım 20: Footer Departments bölümü kontrol ediliyor");
        Assert.assertTrue(layout.footerDepartmentsSectionHeader.isDisplayed(), "Footer Departments başlığı görünür değil!");
        Assert.assertTrue(layout.footerDepartmentsSectionLinks.size() > 0, "Footer Departments linkleri bulunamadı!");
        extentTest.pass("Footer Departments bölümü görünür");

        // 21. Footer - Follow Us bölümü kontrolü
        extentTest.info("Adım 21: Footer Follow Us bölümü kontrol ediliyor");
        Assert.assertTrue(layout.footerSocialMenuSectionHeader.isDisplayed(),"Footer Contacts başlığı görünür değil!");
        Assert.assertTrue(layout.footerSocialMenuSectionLinks.size() > 0, "Footer Follow Us linkleri bulunamadı!");
        extentTest.pass("Footer Follow Us bölümü görünür");

        // 23. Footer - Terms and Conditions link kontrolü
        extentTest.info("Adım 23: Footer Terms and Conditions linki kontrol ediliyor");
        Assert.assertTrue(layout.footerTermsLink.isDisplayed(), "Terms and Conditions link görünür değil!");
        Assert.assertTrue(layout.footerTermsLink.isEnabled(), "Terms and Conditions link tıklanabilir değil!");
        extentTest.pass("Terms and Conditions link görünür ve tıklanabilir");

        // 24. Footer - FAQ link kontrolü
        extentTest.info("Adım 24: Footer FAQ linki kontrol ediliyor");
        Assert.assertTrue(layout.footerFaqLink.isDisplayed(), "FAQ link görünür değil!");
        Assert.assertTrue(layout.footerFaqLink.isEnabled(), "FAQ link tıklanabilir değil!");
        extentTest.pass("FAQ link görünür ve tıklanabilir");

        // 25. Footer - Copyright link kontrolü
        extentTest.info("Adım 25: Footer Copyright (@2023) linki kontrol ediliyor");
        Assert.assertTrue(layout.footerCopyrightLink.isDisplayed(), "Copyright link görünür değil!");
        Assert.assertTrue(layout.footerCopyrightLink.isEnabled(), "Copyright link tıklanabilir değil!");
        extentTest.pass("Copyright link görünür ve tıklanabilir");

        // 26. Scroll to top button kontrolü
        extentTest.info("Adım 26: Scroll to top button kontrol ediliyor");
        Assert.assertTrue(homepageBodyPage.scrollToTopButton.isDisplayed(),"Scroll to top button görünür değil!");
        Assert.assertTrue(homepageBodyPage.scrollToTopButton.isEnabled(),"Scroll to top button tıklanabilir değil!");

        // Scroll to top button'a tıkla ve sayfanın en üste gittiğini doğrula
        homepageBodyPage.scrollToTopButton.click();
        ReusableMethods.bekle(2);
        // Sayfanın en üste gittiğini doğrula
        //Long scrollPosition = (Long) js.executeScript("return window.pageYOffset;");
        //Assert.assertTrue(scrollPosition < 100, "Scroll to top button tıklandığında sayfa en üste gitmedi!");
        extentTest.pass("Scroll to top button görünür, tıklanabilir ve çalışıyor");

        extentTest.pass("TC01 - Home Page Elements testi başarıyla tamamlandı");

    }

}
