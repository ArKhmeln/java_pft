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
        type(By.name("homephone"), contactData.getHomePhone());
        type(By.name("mobilephone"), contactData.getMobilePhone());
        type(By.name("workphone"), contactData.getWorkPhone());
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

    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<>();
        List<WebElement> rows = wd.findElements(By.name("entry"));   //найти эл-ты
        for (WebElement row: rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            //можно было findElement(By.name("firstname")).getAttribute("value");
            String firstname = row.findElement(By.cssSelector("tr[name=entry] td:nth-of-type(3)")).getText();
            String lastname = row.findElement(By.cssSelector("tr[name=entry] td:nth-of-type(2)")).getText();
            String allPhones = cells.get(5).getText();
            String[] phones =  cells.get(5).getText().split("\n");  //порезать, чтобы оказались 3 телефона
            //было int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            int id = Integer.parseInt(rows.get(0).findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
                    .withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]));
        }
        return contacts;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }

    private void initContactModificationById(int id) {
        //String.format умеет подставлять значения внутрь строки (%s, то что дожно подставитсья - id
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../..")); //переход к родительскому элементу(2 прыжка вверх)
        List<WebElement> cells = row.findElements(By.tagName("td"));    //полный список ячеек
        cells.get(7).findElement(By.tagName("a")).click();  //берем нужную ячейку(8-ой столбец), внутри ссылку на ред.
    }
}
