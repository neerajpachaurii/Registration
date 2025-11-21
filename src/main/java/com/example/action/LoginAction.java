package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.example.model.User;
import com.example.service.UserService;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

public class LoginAction extends ActionSupport implements ModelDriven<User> {

    private User user = new User();
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getModel() {
        return user;
    }

    @Override
    public String execute() throws Exception {

        if (!userService.userExists(user.getUsername())) {
            addActionError("User not found. Please register first.");
            return "register_first";
        }

        if (userService.authenticate(user.getUsername(), user.getPassword())) {

            Map<String, Object> session = ActionContext.getContext().getSession();
            session.put("loggedUser", userService.findByUsername(user.getUsername()));

            return SUCCESS;
        } else {
            addActionError("Invalid username or password");
            return INPUT;
        }
    }

    @Override
    public void validate() {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            addFieldError("username", "Username required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            addFieldError("password", "Password required");
        }
    }
}
