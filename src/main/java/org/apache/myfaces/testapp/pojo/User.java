package org.apache.myfaces.testapp.pojo;

import java.io.Serializable;

public class User implements Serializable{
    private String lgn = "admin";
    private String psw = "password";

    public String getLgn() {
        return lgn;
    }

    public void setLgn(String lgn) {
        this.lgn = lgn;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public User() {
    }
}
