package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.goTo().GroupPage();
        Set<GroupData> before = app.group().all();
        GroupData group = new GroupData().withName("test1");
        app.group().create(group);
        Set<GroupData> after = app.group().all();
        Assert.assertEquals(after.size(), before.size() + 1); //кол-во групп до должно быть = кол-ву гр. после

        //как найти идентификатор новой группы? В новой группе id максимальный, сравниваем друг с другом
        //список в поток, ф-я для мах эл-та (сравн. объекты GroupDara с пом. сравнения их Id). на выходе мах объект
        //group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());    //присвоить новой добавленной группе правильный id
        before.add(group);
        Assert.assertEquals(before, after);
    }
}

        /*
        более длинный вариант сравнения
        int max = 0;
        for (GroupData g : after) {
            if (g.getId() > max) {
                max = g.getId();
            }
        }
         */