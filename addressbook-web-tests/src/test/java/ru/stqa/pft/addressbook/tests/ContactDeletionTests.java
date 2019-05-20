package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().gotoHomePage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData("Tester", "Auto",
                    "1234", "mail@test.com", "test1"), true);
        }
    }

    @Test(enabled = false)
    public void testContactDeletion() {
        List<ContactData> before = app.contact().list();
        app.contact().delete();
        List<ContactData> after = app.contact().list();
        app.goTo().gotoHomePage();
        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
