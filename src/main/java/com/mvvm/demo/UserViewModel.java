package com.mvvm.demo;

import com.mvvm.framework.ViewModel;

public class UserViewModel extends ViewModel {
    private UserModel userModel;

    public UserViewModel() {
        this.userModel = new UserModel();
        addModel("user", userModel);
    }

    @Override
    public void performAction(String action, Object... params) {
        switch (action) {
            case "UPDATE_FIRSTNAME":
                userModel.setFirstName((String) params[0]);
                break;
            case "UPDATE_LASTNAME":
                userModel.setLastName((String) params[0]);
                break;
            case "UPDATE_FULLNAME":
                break;
            case "CLEAR":
                userModel.setFirstName("");
                userModel.setLastName("");
                break;
            case "SWAP_NAMES":
                String temp = userModel.getFirstName();
                userModel.setFirstName(userModel.getLastName());
                userModel.setLastName(temp);
                break;
        }
    }

    public UserModel getUserModel() {
        return userModel;
    }
}