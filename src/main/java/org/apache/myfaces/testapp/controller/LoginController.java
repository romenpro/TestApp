package org.apache.myfaces.testapp.controller;

import org.apache.myfaces.testapp.pojo.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {
    private User user;

    public LoginController() {
        user = new User();
    }

    public String loginAction() throws IOException {
        String action;

        if (user.getLgn().equalsIgnoreCase("admin") && user.getPsw().equalsIgnoreCase("password")) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
            return "loginPass";
        }
        else
            action = "loginFail";

        return action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
