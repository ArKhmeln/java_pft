package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Tester", "Auto", "1234", "mail@test.com"));
    submitContactCreation();
  }
}
