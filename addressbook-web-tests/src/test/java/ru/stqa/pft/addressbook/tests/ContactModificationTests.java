package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHomePage();
        if (!app.getContactHelper().isThereContact()) {
            app.getContactHelper().createContact(new ContactData("Tester", "Auto",
                    "1234", "mail@test.com", "test1"), true);
        }
        int before = app.getContactHelper().getContactCount();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Tester", "Auto",
                "1234", "mail@test.com", null), false);
        app.getContactHelper().submitContactModification();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before);
    }
}
