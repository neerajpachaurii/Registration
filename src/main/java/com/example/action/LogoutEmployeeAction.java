package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

public class LogoutEmployeeAction extends ActionSupport {

    public String execute() {

        Map session = ActionContext.getContext().getSession();
        session.remove("loggedEmployee");

        return SUCCESS;
    }
}
