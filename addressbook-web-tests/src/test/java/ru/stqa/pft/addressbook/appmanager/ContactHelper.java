package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

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

    public void selectContact(int index) {   // приходится менять, всегда разный id; upd уже норм
        wd.findElements(By.name("selected[]")).get(index).click();;
    }

    public void deleteSelectedContacts() {
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Select all'])[1]/following::input[2]"));
    }

    public void confirmDeletionContacts() {
        closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$");
    }

    public void initContactModification() {
        click(By.cssSelector("img[alt='Edit']"));
    }

    public void submitContactModification() {
        click(By.name("update"));
        returnToHomePage();
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
        return isElementPresent(By.name("entry"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("entry")).size();
    }

    //доработать!
    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.name("entry"));   //найти эл-ты
        for (WebElement element: elements) {
            String firstname = element.findElement(By.cssSelector("tr[name=entry] td:nth-of-type(3)")).getText();
            String lastname = element.findElement(By.cssSelector("tr[name=entry] td:nth-of-type(2)")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));    //методом тыка сделано
            ContactData contact = new ContactData(id, firstname, lastname, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}
