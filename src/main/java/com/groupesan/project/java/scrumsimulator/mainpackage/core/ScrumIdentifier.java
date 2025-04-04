package com.groupesan.project.java.scrumsimulator.mainpackage.core;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ScrumIdentifierStoreSingleton;

public abstract class ScrumIdentifier {
    protected int id;

    private ScrumObject thisObject;

    public ScrumIdentifier(int value) {
        id = value;
        ScrumIdentifierStoreSingleton.get().registerIdentifier(this);
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Identifier: " + id;
    }

    public int getValue() {
        return id;
    }

    public void setThisObject(ScrumObject object) {
        thisObject = object;
    }

    public ScrumObject getThisObject() {
        return thisObject;
    }

}
