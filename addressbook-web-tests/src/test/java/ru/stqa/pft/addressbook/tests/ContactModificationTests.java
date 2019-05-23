package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

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
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withFirstname("Tester")
                .withLastname("Auto").withPhone("1234").withEmail("mail@test.com");
        app.contact().modify(contact);
        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size());
        //модификация не работает в адресбуке, не проверить код
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
