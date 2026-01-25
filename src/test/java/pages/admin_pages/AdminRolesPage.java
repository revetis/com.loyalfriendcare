package pages.admin_pages;

import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class AdminRolesPage {

    public AdminRolesPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//tr/td[1]")
    public List<WebElement> rolesList;

    @FindBy(xpath = "//tr/td[1]")
    public List<WebElement> rolesActionsSutunuList;

    @FindBy(xpath = "//*[.='Delete']")
    public WebElement rolesDeleteButton;

    @FindBy(xpath = "//div[@class='pull-right']")
    public WebElement rolesSearchLink;






}
