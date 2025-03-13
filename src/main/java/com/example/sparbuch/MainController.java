package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class MainController
{
    @FXML
    private ListView<String> accountsList;

    @FXML
    private ListView<String> transactionsList;

    @FXML
    private Label accountBalance;

    @FXML
    private Label targetProgressLabel;

    @FXML
    private ProgressBar saveTargetIndicator;

    @FXML
    private void OpenTransactionEditor()
    {
        TransactionEditorView te = new TransactionEditorView();
        te.Show();
    }
}