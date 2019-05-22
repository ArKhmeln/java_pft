package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

    @BeforeMethod   //так код более понятный
    public void ensurePreconditions() {
        app.goTo().gotoHomePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Tester")
                    .withLastname("Auto").withPhone("1234").withEmail("mail@test.com").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactModification() {
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withFirstname("Tester")
                .withLastname("Auto").withPhone("1234").withEmail("mail@test.com");
        app.contact().modify(contact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        //модификация не работает в адресбуке, не проверить код
        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
