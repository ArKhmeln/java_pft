package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (creation) {     //проверка, откуда исходит запрос (Creation, Modification)
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContactById(int id) {   // приходится менять, всегда разный id; upd уже норм
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();;
    }

    public void deleteSelectedContacts() {
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Select all'])[1]/following::input[2]"));
    }

    public void confirmDeletionContacts() {
        closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$");
    }

    public void initModification() {
        click(By.cssSelector("img[alt='Edit']"));
    }

    public void submitModification() {
        click(By.name("update"));
        returnToHomePage();
    }

    private void returnToHomePage() {
        click(By.linkText("home"));
    }

    public void create(ContactData contact, boolean b) {
        initContactCreation();
        fillContactForm(contact, b);
        submitContactCreation();
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        initModification();
        fillContactForm(contact, false);
        submitModification();
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
        confirmDeletionContacts();
    }

    public boolean isThereContact() {
        return isElementPresent(By.name("entry"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("entry")).size();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));   //найти эл-ты
        for (WebElement element: elements) {
            String firstname = element.findElement(By.cssSelector("tr[name=entry] td:nth-of-type(3)")).getText();
            String lastname = element.findElement(By.cssSelector("tr[name=entry] td:nth-of-type(2)")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));    //методом тыка сделано
            contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
        }
        return contacts;
    }
}
