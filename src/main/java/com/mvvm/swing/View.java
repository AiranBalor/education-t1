package com.mvvm.swing;

import com.mvvm.framework.ViewModel;
import com.mvvm.framework.Observer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public abstract class View extends JFrame implements Observer, ActionListener {
    protected ViewModel viewModel;
    private Map<String, BindableTextField> textFields = new HashMap<>();

    public View(String title, ViewModel viewModel) {
        super(title);
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        initializeUI();
    }

    protected abstract void initializeUI();

    public void bindTextField(String propertyName, BindableTextField textField) {
        textFields.put(propertyName, textField);
        textField.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(propertyName)) {
                viewModel.performAction("UPDATE_" + propertyName.toUpperCase(), evt.getNewValue());
            }
        });
    }

    public void bindButton(BindableButton button) {
        button.addActionListener(this);
    }

    @Override
    public void update(String propertyName, Object newValue) {
        BindableTextField textField = textFields.get(propertyName);
        if (textField != null) {
            textField.update(propertyName, newValue);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        viewModel.performAction(e.getActionCommand());
    }

    protected void showView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}