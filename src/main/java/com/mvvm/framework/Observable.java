package com.mvvm.framework;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String propertyName, Object newValue) {
        for (Observer observer : observers) {
            observer.update(propertyName, newValue);
        }
    }
}
