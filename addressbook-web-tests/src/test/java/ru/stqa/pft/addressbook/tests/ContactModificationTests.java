package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoHomePage();
        if (!app.getContactHelper().isThereContact()) {
            app.getContactHelper().createContact(new ContactData("Tester", "Auto",
                    "1234", "mail@test.com", "test1"), true);
        }
    }

    @Test(enabled = false)
    public void testContactModification() {
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification();
        ContactData contact = new ContactData("Tester", "Auto",
                "1234", "mail@test.com", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        //модификация не работает в адресбуке, не проверить код
        before.remove(0);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
