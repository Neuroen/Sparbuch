package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MainController
{
    @FXML
    private ListView<String> accountsList;

    @FXML
    private ListView<String> transactionsList;

    @FXML
    private void OpenTransactionEditor()
    {
        TransactionEditorView te = new TransactionEditorView();
        te.Show();
    }
}