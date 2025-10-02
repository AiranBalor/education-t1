package com.mvvm.swing;

import com.mvvm.framework.Observer;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BindableTextField extends JTextField implements Observer {
    private String boundProperty;
    private boolean updating = false;

    public BindableTextField(String boundProperty) {
        this.boundProperty = boundProperty;
        
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!updating) {
                    notifyViewModel();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!updating) {
                    notifyViewModel();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!updating) {
                    notifyViewModel();
                }
            }
        });
    }

    private void notifyViewModel() {
        // Здесь будет логика уведомления ViewModel
        // В реальной реализации нужен доступ к ViewModel
        firePropertyChange(boundProperty, null, getText());
    }

    @Override
    public void update(String propertyName, Object newValue) {
        if (boundProperty.equals(propertyName) && newValue != null) {
            updating = true;
            setText(newValue.toString());
            updating = false;
        }
    }

    public String getBoundProperty() {
        return boundProperty;
    }
}