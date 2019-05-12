package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHomePage();
        if (!app.getContactHelper().isThereContact()) {
            app.getContactHelper().createContact(new ContactData("Tester", "Auto",
                    "1234", "mail@test.com", "test1"), true);
        }
        int before = app.getContactHelper().getContactCount();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().confirmDeletionContacts();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before - 1);
    }
}
