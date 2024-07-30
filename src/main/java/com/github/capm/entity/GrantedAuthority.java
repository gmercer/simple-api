package com.github.capm.entity;

public class GrantedAuthority {

    String authority;

    // We need empty constructor to help deserialisation in JUnit
    // see https://github.com/FasterXML/jackson-databind/issues/2984
    public GrantedAuthority() {
    }

    public GrantedAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
