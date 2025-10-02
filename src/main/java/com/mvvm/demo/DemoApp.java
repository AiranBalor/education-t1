package com.mvvm.demo;

import javax.swing.*;

public class DemoApp {
    public static void main(String[] args) {
        // Запуск в EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            UserViewModel viewModel = new UserViewModel();
            new UserView(viewModel);
        });
    }
}