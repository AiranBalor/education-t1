package com.mvvm.framework;

public interface Observer {
    void update(String propertyName, Object newValue);
}
