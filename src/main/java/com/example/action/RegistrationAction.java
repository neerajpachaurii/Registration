package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.example.model.User;
import com.example.service.UserService;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

public class RegistrationAction extends ActionSupport implements ModelDriven<User> {

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

        // Save to DB
        userService.saveUser(user);

        // Store in session
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("loggedUser", user);
        
        addActionMessage("User registered successfully!"); // for flashing message


        return SUCCESS;
    }

    @Override
    public void validate() {

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            addFieldError("username", "Username required");
        }

        if (user.getPassword() == null || user.getPassword().length() < 4) {
            addFieldError("password", "Password must be at least 4 chars");
        }

        if (user.getEmail() == null || !user.getEmail().matches("^.+@.+\\..+$")) {
            addFieldError("email", "Valid email required");
        }

        // check if username already exists
        if (userService != null && userService.userExists(user.getUsername())) {
            addFieldError("username", "Username already taken");
        }
    }
}
