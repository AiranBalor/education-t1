package com.mvvm.swing;

import javax.swing.*;

public class BindableButton extends JButton {
    private String actionCommand;

    public BindableButton(String text, String actionCommand) {
        super(text);
        this.actionCommand = actionCommand;
        setActionCommand(actionCommand);
    }

    public String getActionCommand() {
        return actionCommand;
    }
}