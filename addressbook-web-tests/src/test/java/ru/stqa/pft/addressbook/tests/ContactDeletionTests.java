package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoHomePage();
        if (!app.getContactHelper().isThereContact()) {
            app.getContactHelper().createContact(new ContactData("Tester", "Auto",
                    "1234", "mail@test.com", "test1"), true);
        }
    }

    @Test(enabled = false)
    public void testContactDeletion() {
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(0);    //м.б. ошибка - тогда поставить 0
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().confirmDeletionContacts();
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
