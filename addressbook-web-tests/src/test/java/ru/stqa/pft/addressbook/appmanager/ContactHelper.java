package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("mobile"), contactData.getPhone());
        type(By.name("email"), contactData.getEmail());
        if (creation) { //проверка, откуда исходит запрос (Creation, Modification)
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContact() {   // приходится менять, всегда разный id
        click(By.id("11"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Select all'])[1]/following::input[2]"));
    }

    public void confirmDeletionContacts() {
        closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$");
    }

    public void initContactModification() {
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='mail@test.com'])[1]/following::img[2]"));
    }

    public void submitContactModification() {
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Notes:'])[1]/following::input[1]"));
    }

    private void returnToHomePage() {
        click(By.linkText("home"));
    }

    public void createContact(ContactData contact, boolean b) {
        initContactCreation();
        fillContactForm(contact, b);
        submitContactCreation();
        returnToHomePage();
    }


    public boolean isThereContact() {
        return isElementPresent(By.id("11"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }
}
