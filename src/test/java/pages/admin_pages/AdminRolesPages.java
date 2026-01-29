package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AdminRolesPages {

    public AdminRolesPages(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "(//*[@class='title'])[2]")
    public WebElement rolesButon;

    @FindBy(xpath = "//*[*='Create Role']")
    public WebElement createRole;

    @FindBy(xpath = "//*[@class='card-header ']")
    public WebElement addRoleSayfasi;

    @FindBy(xpath = "(//*[@class='form-control '])[1]")
    public WebElement rolesName;

    @FindBy(xpath = "(//*[@class='form-control '])[2]")
    public WebElement rolesDisplayName;

    @FindBy(xpath = "//*[@class='alert alert-success']")
    public WebElement rollerListesiDogrulama;

    @FindBy(xpath = "//*[@class='alert alert-danger']")
    public WebElement kayitliRolOlusturmaUyarisi;

    @FindBy(xpath = "(//*[@class='btn_add'])[1]")
    public WebElement singInButton;


}
