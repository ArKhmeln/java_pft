package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

//класс, который ведет себя как Set, но со своими методами
public class Groups extends ForwardingSet<GroupData> {

    private Set<GroupData> deligate;

    public Groups(Groups groups) {
        this.deligate = new HashSet<>(groups.deligate);
    }

    public Groups() {
        this.deligate = new HashSet<>();
    }

    @Override
    protected Set<GroupData> delegate() {
        return deligate;
    }

    public Groups withAdded(GroupData group) {
        Groups groups = new Groups(this);
        groups.add(group);  //add работает т.к. реализован в ForwardingSet
        return groups;
    }

    public Groups without(GroupData group) {  //делает копию, из которой удалена группа
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }
}
