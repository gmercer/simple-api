package com.github.capm.entity;

public class GroupMember {
    private int id;
    private String username;
    private int groupId;

    public GroupMember(int id, String username, int groupId) {
        this.id = id;
        this.username = username;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
