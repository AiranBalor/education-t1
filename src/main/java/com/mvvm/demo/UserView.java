package com.mvvm.demo;

import com.mvvm.swing.View;
import com.mvvm.swing.BindableTextField;
import com.mvvm.swing.BindableButton;
import javax.swing.*;
import java.awt.*;

public class UserView extends View {
    private BindableTextField firstNameField;
    private BindableTextField lastNameField;
    private BindableTextField fullNameField;

    public UserView(UserViewModel viewModel) {
        super("MVVM Demo - User Management", viewModel);
    }

    @Override
    protected void initializeUI() {
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("First Name:"));
        firstNameField = new BindableTextField("firstName");
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new BindableTextField("lastName");
        add(lastNameField);

        add(new JLabel("Full Name:"));
        fullNameField = new BindableTextField("fullName");
        fullNameField.setEditable(false);
        add(fullNameField);

        BindableButton clearButton = new BindableButton("Clear", "CLEAR");
        BindableButton swapButton = new BindableButton("Swap Names", "SWAP_NAMES");

        add(clearButton);
        add(swapButton);

        bindTextField("firstName", firstNameField);
        bindTextField("lastName", lastNameField);
        bindTextField("fullName", fullNameField);
        bindButton(clearButton);
        bindButton(swapButton);

        showView();
    }
}