package pages.common_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.ArrayList;
import java.util.List;

public class Layout {

    public Layout(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    //=============================================
    //=============================================
    //==========     Header Locators   ============
    //=============================================
    //=============================================

    // Navigation bar
    @FindBy(css = "nav#menu")
    public WebElement headerNavigationBar;

    @FindBy(css = "header.header")
    public WebElement header;

    @FindBy(xpath = "//*[@id=\"logo\"]/a")
    public WebElement headerLogo;

    @FindBy(css = "#logo img.logo_normal")
    public WebElement headerLogoImage;

    @FindBy(xpath = "//nav[@id='menu']//a[text()='Home']")
    public WebElement headerHomeLink;

    @FindBy(xpath = "//nav[@id='menu']//a[text()='About Us']")
    public WebElement headerAboutUsLink;

    @FindBy(xpath = "//nav[@id='menu']//a[contains(text(),'Departments')]")
    public WebElement headerDepartmentsLink;

    @FindBy(css = "#menu > ul > li:nth-child(3) li")
    public List<WebElement> headerDepartmentsSubLinks;

    @FindBy(xpath = "//nav[@id='menu']//a[contains(text(),'Doctors')]")
    public WebElement headerDoctorsLink;

    @FindBy(css = "#menu > ul > li:nth-child(4) li")
    public List<WebElement> headerDoctorSubLinks;

    @FindBy(xpath = "//nav[@id='menu']//a[contains(text(),'Medicines')]")
    public WebElement headerMedicinesLink;

    @FindBy(css = "#menu > ul > li:nth-child(5) li")
    public List<WebElement> headerMedicinesSubLinks;

    @FindBy(xpath = "//nav[@id='menu']//a[contains(text(),'Vaccinations')]")
    public WebElement headerVaccinationsLink;

    @FindBy(css = "#menu > ul > li:nth-child(6) li")
    public List<WebElement> headerVaccinationsSubLinks;

    @FindBy(css = "#top_menu li:nth-child(2) a")
    public WebElement signUpLink;

    @FindBy(css = "#top_menu li:nth-child(1) a")
    public WebElement signInLink;

    //For Auth Admin
    @FindBy(css = "#top_menu li:nth-child(1) a")
    public WebElement headerAuthAdminDashboardButton;

    @FindBy(css = "#top_menu li:nth-child(2) a")
    public WebElement headerAuthAdminSignOutButton;

    //For Auth User
    @FindBy(css = "#top_menu li:nth-child(1) a")
    public WebElement headerAuthUserDashboardButton;

    @FindBy(css = "#top_menu li:nth-child(2) a")
    public WebElement headerAuthUserSignOutButton;


    //=============================================
    //=============================================
    //==========     Footer Locators   ============
    //=============================================
    //=============================================

    @FindBy(css = "footer h3 a")
    public WebElement footerLogo;

    @FindBy(css = "footer h3 img")
    public WebElement footerLogoImg;

    @FindBy(css = "#collapse_ft_1 p")
    public WebElement footerDescription;

    @FindBy(css = "h3[data-target='#collapse_ft_2']")
    public WebElement footerDepartmentsSectionHeader;

    @FindBy(css = "#collapse_ft_2 ul li")
    public List<WebElement> footerDepartmentsSectionLinks;

    @FindBy(css = "h3[data-target='#collapse_ft_4']")
    public WebElement footerSocialMenuSectionHeader;

    @FindBy(css = "#collapse_ft_4 ul li")
    public List<WebElement> footerSocialMenuSectionLinks;

    @FindBy(css = "h3[data-target='#collapse_ft_3']")
    public WebElement footerContactsSectionHeader;

    @FindBy(css = "#collapse_ft_3 ul li")
    public List<WebElement> footerContactsSectionLinks;

    @FindBy(css = "#additional_links li:nth-child(1) a")
    public WebElement footerTermsLink;

    @FindBy(css = "#additional_links li:nth-child(2) a")
    public WebElement footerFaqLink;

    @FindBy(css = "#additional_links li:nth-child(3) a")
    public WebElement footerCopyrightLink;

    //=============================================
    //=============================================
    //=====    AdminPanel Header Locators   =======
    //=============================================
    //=============================================


    @FindBy(css = ".header .semi-bold")
    public WebElement adminHeaderUsername;

    @FindBy(css = "button.profile-dropdown-toggle")
    public WebElement adminHeaderUserInfoButton;

    @FindBy(css = ".profile-dropdown a.dropdown-item")

    public List<WebElement> adminHeaderUserInfoDropdownMenuLinks;

    //=============================================
    //=============================================
    //=====    AdminPanel Sidebar Locators   ======
    //=============================================
    //=============================================

    @FindBy(css = "nav.page-sidebar")
    public WebElement adminSidebar;

    @FindBy(css = ".toggle-sidebar")
    public WebElement adminSidebarButton;

    //Yonetim araclari
    @FindBy(xpath = "//ul[@class='menu-items scroll-content']/li")
    public List<WebElement> adminSidebarAdminToolList;

    public WebElement getTool(int row){
        return adminSidebarAdminToolList.get(row).findElement(By.xpath("./a"));
    }
    public WebElement getToolWithName(String name){
        for (WebElement tool : adminSidebarAdminToolList){
            if (tool.findElement(By.xpath("./a")).getText().equals(name)){
                return tool.findElement(By.xpath("./a"));
            }
        }
        return null;
    }

    public List<WebElement> getToolSubList(int row){
        try {
            return adminSidebarAdminToolList.get(row).findElements(By.xpath(".//ul[contains(@class,'sub-menu')]//li/a"));
        } catch (Exception e) {
            return new ArrayList<>();//eger sublist yoksa bos liste donuyoruz ve listenin bos olup olmmadigini kontrol ediyoruz
        }
    }

    @FindBy(css = ".toggle-sidebar")
    public WebElement hamburgerMenuButton;

    //Logo
    @FindBy(css = ".sidebar-header img.brand")
    public WebElement adminSidebarLogo;
    //DashboardLink
    @FindBy(xpath = "//span[text()='Dashboard']/ancestor::a")
    public WebElement adminSidebarDashboardLink;
    //Roles
    @FindBy(xpath = "//span[text()='Roles']/ancestor::a")
    public WebElement adminSidebarRolesButton;

    @FindBy(xpath = "//span[text()='Roles']/ancestor::li")
    public WebElement adminSidebarRolesLi;

    @FindBy(css = "li.open ul.sub-menu li")
    public List<WebElement> adminSidebarRolesSubLinks;
    //Users
    @FindBy(xpath = "//span[text()='Users']/ancestor::li")
    public WebElement adminSidebarUsersLi;

    @FindBy(xpath = "//span[text()='Users']/ancestor::a")
    public WebElement adminSidebarUsersButton;

    @FindBy(xpath = "//span[text()='Users']/ancestor::li//ul/li")
    public List<WebElement> adminSidebarUsersSubLinks;
    //Bed managers
    @FindBy(xpath = "//span[text()='Bed managers']/ancestor::li")
    public WebElement adminSidebarBedmanagersLi;

    @FindBy(xpath = "//span[text()='Bed managers']/ancestor::a")
    public WebElement adminSidebarBedmanagersButton;

    @FindBy(xpath = "//span[text()='Bed managers']/ancestor::li//ul")
    public WebElement adminSidebarBedmanagersSubLinksMenu;

    @FindBy(xpath = "//span[text()='Bed managers']/ancestor::li//ul/li")
    public List<WebElement> adminSidebarBedmanagersSubLinks;

    public WebElement getBedManagersSubLink(String name){
        for (WebElement el :adminSidebarBedmanagersSubLinks){
            if (el.findElement(By.xpath("./a")).getText().equalsIgnoreCase(name))return el.findElement(By.xpath("./a"));
        }
        return null;
    }


    //Departments
    @FindBy(xpath = "//span[text()='Departments']/ancestor::li")
    public WebElement adminSidebarDepartmentsLi;

    @FindBy(xpath = "//span[text()='Departments']/ancestor::a")
    public WebElement adminSidebarDepartmentsButton;

    @FindBy(xpath = "//span[text()='Departments']/ancestor::li//ul/li")
    public List<WebElement> adminSidebarDepartmentsSubLinks;
    //Doctors
    @FindBy(xpath = "//span[text()='Doctors']/ancestor::li")
    public WebElement adminSidebarDoctorsLi;

    @FindBy(xpath = "//span[text()='Doctors']/ancestor::a")
    public WebElement adminSidebarDoctorsButton;

    @FindBy(xpath = "//span[text()='Doctors']/ancestor::li//ul/li")
    public List<WebElement> adminSidebarDoctorsSubLinks;
    //Medicines
    @FindBy(xpath = "//span[text()='Medicines']/ancestor::li")
    public WebElement adminSidebarMedicinesLi;

    @FindBy(xpath = "//span[text()='Medicines']/ancestor::a")
    public WebElement adminSidebarMedicinesButton;

    @FindBy(xpath = "//span[text()='Medicines']/ancestor::li//ul/li")
    public List<WebElement> adminSidebarMedicinesSubLinks;
    //Pets adsense
    @FindBy(xpath = "//span[text()='Pets adsense']/ancestor::li")
    public WebElement adminSidebarPetsadsenseLi;

    @FindBy(xpath = "//span[text()='Pets adsense']/ancestor::a")
    public WebElement adminSidebarPetsadsenseButton;

    @FindBy(xpath = "//span[text()='Pets adsense']/ancestor::li//ul/li")
    public List<WebElement> adminSidebarPetsadsenseSubLinks;

    //Appointments
    @FindBy(xpath = "//span[text()='Tickets']/ancestor::a")
    public WebElement adminSidebarAppointmentsButton;

    @FindBy(xpath = "//span[text()='Tickets']")
    public WebElement adminSidebarAppointmentsButtonText;

    //Vaccinations
    @FindBy(xpath = "//span[text()='Vaccinations']/ancestor::a")
    public WebElement adminSidebarVaccinationsButton;

    @FindBy(xpath = "//span[text()='Vaccinations']")
    public WebElement adminSidebarVaccinationsButtonText;


    //=============================================
    //=============================================
    //=====    AdminPanel Footer Locators   =======
    //=============================================
    //=============================================


    @FindBy(css = ".container-fluid.footer")
    public WebElement adminFooter;

    @FindBy(css = ".footer p.pull-left")
    public WebElement adminFooterCopyrightSection;

    @FindBy(css = ".footer p.pull-right")
    public WebElement adminFooterSignature;

}

