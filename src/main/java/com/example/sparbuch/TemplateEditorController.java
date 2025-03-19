package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TemplateEditorController
{
    @FXML
    public TextField transactionNameField;
    @FXML
    public TextField transactionValueField;
    @FXML
    public TextField templateNameField;
    public TemplateEditorView mainView;

    @FXML
    private void AcceptButtonClicked()
    {
        Transaction newTransaction = new Transaction();
        if(!transactionNameField.getText().isEmpty())
        {
            newTransaction.name = transactionNameField.getText();
        }
        else
        {
            //TODO: Implementiere benachrichtigung das Feld nicht leer sein darf
            return;
        }

        if(!transactionValueField.getText().isEmpty())
        {
            //TODO: Implementiere benachrichtigung das Feld nicht leer sein darf
            newTransaction.value = Float.parseFloat(transactionValueField.getText());
        }
        else
        {
            return;
        }

        TransactionTemplate newTemplate = new TransactionTemplate();
        if(!templateNameField.getText().isEmpty())
        {
            newTemplate.name = templateNameField.getText();
        }
        newTemplate.exampleTransaction = newTransaction;
        mainView.SetResultValue(newTemplate);
        mainView.Close(true);
    }

    @FXML
    private void CancelButtonClicked()
    {
        mainView.Close(false);
    }

    public void SetTemplateName(String name)
    {
        templateNameField.setText(name);
    }

    public void SetTransactionName(String name)
    {
        transactionNameField.setText(name);
    }

    public void SetTransactionValue(String value)
    {
        transactionValueField.setText(value);
    }

    public void SetMainView(TemplateEditorView mainView)
    {
        this.mainView = mainView;
    }
}
