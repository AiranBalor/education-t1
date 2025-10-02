package com.mvvm.framework;

import java.util.HashMap;
import java.util.Map;

public abstract class ViewModel extends Observable implements Observer {
    private Map<String, Model> models = new HashMap<>();

    public void addModel(String name, Model model) {
        models.put(name, model);
        model.addObserver(this);
    }

    public Model getModel(String name) {
        return models.get(name);
    }

    @Override
    public void update(String propertyName, Object newValue) {
        notifyObservers(propertyName, newValue);
    }

    public abstract void performAction(String action, Object... params);
}