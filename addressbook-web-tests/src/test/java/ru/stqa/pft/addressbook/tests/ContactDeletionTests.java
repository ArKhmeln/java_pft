package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().gotoHomePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Tester")
                    .withLastname("Auto").withPhone("1234").withEmail("mail@test.com").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactDeletion() {
        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next();  //выбрать контакт (все равно какой)
        app.contact().delete(deletedContact);
        Set<ContactData> after = app.contact().all();
        app.goTo().gotoHomePage();
        before.remove(deletedContact);
        Assert.assertEquals(before, after);
    }
}
