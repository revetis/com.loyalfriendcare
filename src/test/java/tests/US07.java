package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import pages.common_pages.RegisterPage;
import pages.user_pages.UserPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US07 extends TestBaseRapor {

    Layout layout = new Layout();
    RegisterPage registerPage = new RegisterPage();
    Faker faker =new Faker();
    UserPage userPage = new UserPage();

    @Test
    public void tc01() {
        extentTest = extentReports.createTest("Register sayfasina erisim",
                                            "Ziyaretçi kayit sayfasina ve formuna erisim saglayabilmeli");


        Driver.getDriver().get(ConfigReader.getProperty("url"));

        //HomePage SignUp linki goruntulenir ve erisilebilir olmali
        Assert.assertTrue(layout.signUpLink.isDisplayed());
        extentTest.pass("Anasayfa'da SignUp butonu görünür durumda");

        Assert.assertTrue(layout.signUpLink.isEnabled());
        extentTest.pass("SignUp butonu erişilebilir durumda");

        //SignUp tiklanir
        layout.signUpLink.click();

        //Register sayfasina yonlendirildigi dogrulanir
        String expectedUrlIcerik = "register";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expectedUrlIcerik));
        extentTest.info("Ziyaretçinin register sayfasına yönlendirildiğinin kontrolü");

        //Kayit formunun goruntulendigi dogrulanir
        Assert.assertTrue(registerPage.usernameInput.isDisplayed());
        extentTest.info("Username alanı görünürlük kontrolü");
        Assert.assertTrue(registerPage.usernameInput.isEnabled());
        extentTest.info("Username alanı erişilebilirlik kontrolü");

        Assert.assertTrue(registerPage.emailAddressInput.isDisplayed());
        extentTest.info("Email Address alanı görünürlük kontrolü");
        Assert.assertTrue(registerPage.emailAddressInput.isEnabled());
        extentTest.info("Email Address alanı erişilebilirlik kontrolü");

        Assert.assertTrue(registerPage.passwordInput.isDisplayed());
        extentTest.info("Password alanı görünürlük kontrolü");
        Assert.assertTrue(registerPage.passwordInput.isEnabled());
        extentTest.info("Password alanı erişilebilirlik kontrolü");

        Assert.assertTrue(registerPage.confirmPasswordInput.isDisplayed());
        extentTest.info("Confirm Password alanı görünürlük kontrolü");
        Assert.assertTrue(registerPage.confirmPasswordInput.isEnabled());
        extentTest.info("Confirm Password alanı erişilebilirlik kontrolü");

        Assert.assertTrue(registerPage.signUpButton.isDisplayed());
        extentTest.info("Register sayfası SignUp butonu görünürlük kontrolü");
        Assert.assertTrue(registerPage.signUpButton.isEnabled());
        extentTest.info("Register sayfası SignUp butonu erişilebilirlik kontrolü");

    }

    @Test
    public void tc02() {
        extentTest = extentReports.createTest("Positive SignUp Testi",
                                            "Ziyaretci basarili bir sekilde kayit olabilmeli");

        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/register");

        //Register formunda istenen bilgiler girilir
        registerPage.usernameInput.sendKeys(faker.gameOfThrones().character());
        registerPage.emailAddressInput.sendKeys(faker.internet().emailAddress());
        String password = faker.internet().password(8, 16, true, true, true);
        registerPage.passwordInput.sendKeys(password);
        registerPage.confirmPasswordInput.sendKeys(password);

        registerPage.signUpButton.click();

        ReusableMethods.bekle(1);

        //Anasayfa'ya yönlendirildigi dogrulanir
        String expectedUrl = "https://qa.loyalfriendcare.com/en";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertEquals(actualUrl, expectedUrl);
        extentTest.info("Kullanıcın kayıt sonrası anasayfaya yönlendirildiğinin kontrolü");


        //HomePage header bölümünde username ve Sign Out butonlari görünür oldugu dogrulanir
        Assert.assertTrue(userPage.userPageUserNameLink.isDisplayed());
        extentTest.info("Kullanıcının kayıt sonrası anasayfa'da Kullanıcı Adı'nın göründüğünün kontrolü");
        Assert.assertTrue(userPage.userPageSignOutLink.isDisplayed());
        extentTest.info("Kullanıcının kayıt sonrası anasayfa'da Sign Out butonunu görüntülediğinin kontrolü");
        Assert.assertTrue(userPage.userPageSignOutLink.isEnabled());
        extentTest.info("Kullanıcının kayıt sonrası anasayfa'da Sign Out butonuna erişebildiğinin kontrolü");

    }

    @Test
    public void tc03(){
        extentTest = extentReports.createTest("Negative SignUp Testi",
                                            "Bilgiler eksik veya hatali girildiğinde uyari mesaji verildiginin kontrolu");

        Driver.getDriver().get("https://qa.loyalfriendcare.com/en/register");

        //Step-1 : Username bos birakilir ve singUp tiklanir ===========================================================
        registerPage.emailAddressInput.sendKeys(faker.internet().emailAddress());
        String password = faker.internet().password(8, 16, true, true, true);
        registerPage.passwordInput.sendKeys(password);
        registerPage.confirmPasswordInput.sendKeys(password);

        registerPage.signUpButton.click();

        //Username uyarı mesaj dogrulanir
        String expectedUsernameUyariMesaji = "Lütfen bu alanı doldurun.";
        String actualUsernameUyariMesaji = registerPage.usernameInput.getAttribute("validationMessage");

        Assert.assertEquals(actualUsernameUyariMesaji,expectedUsernameUyariMesaji);

        ReusableMethods.bekle(2);

        Driver.getDriver().navigate().refresh();



        //Step-2 : Email bos birakilir =================================================================================
        registerPage.usernameInput.sendKeys(faker.gameOfThrones().character());
        registerPage.passwordInput.sendKeys(password);
        registerPage.confirmPasswordInput.sendKeys(password);

        registerPage.signUpButton.click();

        //E-Mail Address uyarı mesaj dogrulanir
        String expectedEmailUyariMesaji = "Lütfen bu alanı doldurun.";
        String actualEmailUyariMesaji = registerPage.emailAddressInput.getAttribute("validationMessage");

        Assert.assertEquals(actualEmailUyariMesaji,expectedEmailUyariMesaji);

        ReusableMethods.bekle(2);

        Driver.getDriver().navigate().refresh();



        //Step-3 : E-Mail Address hatalı/eksik girilir =================================================================
        registerPage.usernameInput.sendKeys(faker.gameOfThrones().character());
        registerPage.emailAddressInput.sendKeys("test");
        registerPage.passwordInput.sendKeys(password);
        registerPage.confirmPasswordInput.sendKeys(password);

        registerPage.signUpButton.click();

        //E-Mail Address uyarı mesaj dogrulanir
        Assert.assertEquals(actualEmailUyariMesaji,expectedEmailUyariMesaji);

        ReusableMethods.bekle(2);

        Driver.getDriver().navigate().refresh();



        //Step-4 : Password bos birakilir ==============================================================================
        registerPage.usernameInput.sendKeys(faker.gameOfThrones().character());
        registerPage.emailAddressInput.sendKeys(faker.internet().emailAddress());

        registerPage.signUpButton.click();

        //Bos Password uyarı mesaj dogrulanir
        String expectedBosPasswordUyariMesaji = "Lütfen bu alanı doldurun.";
        String actualBosPassworUyariMesaji = registerPage.passwordInput.getAttribute("validationMessage");

        Assert.assertEquals(actualBosPassworUyariMesaji,expectedBosPasswordUyariMesaji);

        ReusableMethods.bekle(2);

        Driver.getDriver().navigate().refresh();



        //Step-5 : Password hatalı girilir =============================================================================
        registerPage.usernameInput.sendKeys(faker.gameOfThrones().character());
        registerPage.emailAddressInput.sendKeys(faker.internet().emailAddress());
        registerPage.passwordInput.sendKeys("123");
        registerPage.confirmPasswordInput.sendKeys("abc");

        //Hatalı Password uyari mesaj dogrulanir
        String expectedPasswordUyariMessage = "Lütfen istenen biçimi eşleştirin.";
        String actualPasswordUyariMessage = registerPage.passwordInput.getAttribute("validationMessage");

        Assert.assertEquals(actualPasswordUyariMessage,expectedPasswordUyariMessage);

        ReusableMethods.bekle(2);

        Driver.getDriver().navigate().refresh();



        //Step-6 : Comfirm Password bos birakilir ======================================================================
        registerPage.usernameInput.sendKeys(faker.gameOfThrones().character());
        registerPage.emailAddressInput.sendKeys(faker.internet().emailAddress());
        registerPage.passwordInput.sendKeys(password);

        registerPage.signUpButton.click();

        String expectedComfirmPasswordUyariMesaji = "Lütfen bu alanı doldurun.";
        String actualComfirmPasswordUyariMesaji = registerPage.confirmPasswordInput.getAttribute("validationMessage");

        Assert.assertEquals(actualComfirmPasswordUyariMesaji,expectedComfirmPasswordUyariMesaji);

    }
}
