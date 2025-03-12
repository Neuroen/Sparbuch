package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TransactionEditorController
{
    @FXML
    private TextField transactionValueField;

    @FXML
    private DatePicker transactionDatePicker;


    public void SetTransactionValue(String value)
    {
        transactionValueField.setText(value);
    }
}
