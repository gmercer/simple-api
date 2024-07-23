package com.github.capm.entity;

public class Group {
    private int id;
    private String groupName;

    public Group(int groupId, String groupName) {
        this.id = groupId;
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
