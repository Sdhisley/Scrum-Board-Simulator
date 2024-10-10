package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

public class ListofBlocker {

    public enum BlockerType {
        TIME_BLOCKER,
        TECHNICAL_DEBT,
        PEOPLE_BLOCKER,
        NEEDS_MORE_INFO,
        REQUIREMENTS_ISSUE
    }

    private BlockerType blockerType;

    public ListofBlocker(BlockerType blockerType) {
        this.blockerType = blockerType;
    }

    public BlockerType getBlockerType() {
        return blockerType;
    }
}