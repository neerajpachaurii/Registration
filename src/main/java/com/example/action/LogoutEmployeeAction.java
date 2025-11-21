package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

public class LogoutEmployeeAction extends ActionSupport {

    @Override
    public String execute() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        session.remove("loggedEmployee");
        return SUCCESS; // map success to index.jsp or login
    }
}
