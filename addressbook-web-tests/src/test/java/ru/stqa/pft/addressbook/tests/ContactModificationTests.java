package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod   //так код более понятный
    public void ensurePreconditions() {
        app.goTo().gotoHomePage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Tester")
                    .withLastname("Auto").withPhone("1234").withEmail("mail@test.com").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData().withFirstname("Tester")
                .withLastname("Auto").withPhone("1234").withEmail("mail@test.com");
        app.contact().modify(contact);
        List<ContactData> after = app.contact().list();
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
