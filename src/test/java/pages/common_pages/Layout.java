package pages.common_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

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

    @FindBy(xpath = "/html/body/header")
    public WebElement header;

    @FindBy(xpath = "//*[@id=\"logo\"]/a")
    public WebElement headerLogo;

    @FindBy(xpath = "//*[@id=\"logo\"]/a/img[1]")
    public WebElement headerLogoImage;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul/li[1]/span/a")
    public WebElement headerHomeLink;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul/li[2]/span/a")
    public WebElement headerAboutUsLink;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul/li[3]/span/a")
    public WebElement headerDepartmentsLink;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul/li[4]/span/a")
    public WebElement headerDoctorsLink;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul/li[5]/span/a")
    public WebElement headerMedicinesLink;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul/li[6]/span/a")
    public WebElement headerVaccinationsLink;

    @FindBy(xpath = "//*[@id=\"top_menu\"]/li[2]/a")
    public WebElement signUpLink;

    @FindBy(xpath = "//*[@id=\"top_menu\"]/li[1]/a")
    public WebElement signInLink;

    //=============================================
    //=============================================
    //==========     Footer Locators   ============
    //=============================================
    //=============================================

    @FindBy(xpath = "//*[@id=\"page\"]/footer/div/div[1]/div[1]/h3/a")
    public WebElement footerLogo;

    @FindBy(xpath = "//*[@id=\"page\"]/footer/div/div[1]/div[1]/h3/a/img")
    public WebElement footerLogoImg;

    @FindBy(xpath = "//*[@id=\"collapse_ft_1\"]/p")
    public WebElement footerDescription;

    @FindBy(xpath = "//*[@id=\"page\"]/footer/div/div[1]/div[2]/h3")
    public WebElement footerDepartmentsSectionHeader;

    @FindBy(xpath = "//*[@id=\"collapse_ft_2\"]/ul/li")
    public List<WebElement> footerDepartmentsSectionLinks;

    @FindBy(xpath = "//*[@id=\"page\"]/footer/div/div[1]/div[3]/h3")
    public WebElement footerSocialMenuSectionHeader;

    @FindBy(xpath = "//*[@id=\"collapse_ft_4\"]/div/ul/li")
    public List<WebElement> footerSocialMenuSectionLinks;

    @FindBy(xpath = "//*[@id=\"page\"]/footer/div/div[1]/div[4]/h3")
    public WebElement footerContactsSectionHeader;

    @FindBy(xpath = "//*[@id=\"collapse_ft_3\"]/ul/li")
    public List<WebElement> footerContactsSectionLinks;

    @FindBy(xpath = "//*[@id=\"additional_links\"]/li[1]/a")
    public WebElement footerTermsLink;

    @FindBy(xpath = "//*[@id=\"additional_links\"]/li[2]/a")
    public WebElement footerFaqLink;

    @FindBy(xpath = "//*[@id=\"additional_links\"]/li[3]/a")
    public WebElement footerCopyrightLink;

    //=============================================
    //=============================================
    //=====    AdminPanel Header Locators   =======
    //=============================================
    //=============================================

    @FindBy(xpath = "/html/body/div[1]/div[1]/div[2]/div[1]/span")
    public WebElement adminHeaderUsername;

    @FindBy(xpath = "/html/body/div[1]/div[1]/div[2]/div[2]/button")
    public WebElement adminHeaderUserInfoButton;

    @FindBy(xpath = "/html/body/div[1]/div[1]/div[2]/div[2]/div/a")
    public List<WebElement> adminHeaderUserInfoDropdownMenuLinks;

    //=============================================
    //=============================================
    //=====    AdminPanel Sidebar Locators   ======
    //=============================================
    //=============================================

    @FindBy(xpath = "/html/body/nav")
    public WebElement adminSidebar;
    //Logo
    @FindBy(xpath = "/html/body/nav/div[1]/img")
    public WebElement adminSidebarLogo;
    //DashboardLink
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[1]/a")
    public WebElement adminSidebarDashboardLink;
    //Roles
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[2]/a")
    public WebElement adminSidebarRolesButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[2]")
    public WebElement adminSidebarRolesLi;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[2]/ul/li")
    public List<WebElement> adminSidebarRolesSubLinks;
    //Users
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[3]")
    public WebElement adminSidebarUsersLi;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[3]/a")
    public WebElement adminSidebarUsersButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[3]/ul/li")
    public List<WebElement> adminSidebarUsersSubLinks;
    //Bed managers
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[4]")
    public WebElement adminSidebarBedmanagersLi;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[4]/a")
    public WebElement adminSidebarBedmanagersButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[4]/ul/li")
    public List<WebElement> adminSidebarBedmanagersSubLinks;
    //Departments
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[5]")
    public WebElement adminSidebarDepartmentsLi;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[5]/a")
    public WebElement adminSidebarDepartmentsButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[5]/ul/li")
    public List<WebElement> adminSidebarDepartmentsSubLinks;
    //Doctors
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[6]")
    public WebElement adminSidebarDoctorsLi;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[6]/a")
    public WebElement adminSidebarDoctorsButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[6]/ul/li")
    public List<WebElement> adminSidebarDoctorsSubLinks;
    //Medicines
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[7]")
    public WebElement adminSidebarMedicinesLi;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[7]/a")
    public WebElement adminSidebarMedicinesButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[7]/ul/li")
    public List<WebElement> adminSidebarMedicinesSubLinks;
    //Pets adsense
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[8]")
    public WebElement adminSidebarPetsadsenseLi;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[8]/a")
    public WebElement adminSidebarPetsadsenseButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[8]/ul/li")
    public List<WebElement> adminSidebarPetsadsenseSubLinks;

    //Tickets
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[12]/a")
    public WebElement adminSidebarTicketsButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[12]/a/span")
    public WebElement adminSidebarTicketsButtonText;

    //Vaccinations
    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[13]/a")
    public WebElement adminSidebarVaccinationsButton;

    @FindBy(xpath = "/html/body/nav/div[2]/div[1]/ul/li[13]/a/span")
    public WebElement adminSidebarVaccinationsButtonText;


    //=============================================
    //=============================================
    //=====    AdminPanel Footer Locators   =======
    //=============================================
    //=============================================

    @FindBy(xpath = "/html/body/div[1]/div[2]/div[2]")
    public WebElement adminFooter;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div[2]/div/p[1]")
    public WebElement adminFooterCopyrightSection;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div[2]/div/p[2]")
    public WebElement adminFooterSignature;



























































}
