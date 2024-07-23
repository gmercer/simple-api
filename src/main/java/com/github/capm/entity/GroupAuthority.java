package com.github.capm.entity;

public class GroupAuthority {
    private int groupId;
    private String authority;

    public GroupAuthority(int groupId, String authority) {
        this.groupId = groupId;
        this.authority = authority;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
