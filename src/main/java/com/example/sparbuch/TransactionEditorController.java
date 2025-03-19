package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class TransactionEditorController
{
    private TransactionEditorView mainView;

    @FXML
    private TextField transactionNameField;

    @FXML
    private TextField transactionValueField;

    @FXML
    private DatePicker transactionDatePicker;

    public void initialize()
    {
        transactionDatePicker.setValue(LocalDate.now());
    }

    public void AcceptButtonClicked()
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

        if(transactionDatePicker.getValue() != null)
        {
            LocalDate date = transactionDatePicker.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            newTransaction.date = formatter.format(date);
        }
        else
        {
            return;
        }

        mainView.SetResultValue(newTransaction);
        mainView.Close(true);
    }

    public void CancelButtonClicked()
    {
        mainView.Close(false);
    }

    public void CreateTemplateButtonClicked()
    {
        TextInputDialog td = new TextInputDialog();
        td.showAndWait();
        String templateName = td.getEditor().getText();
        TransactionTemplate newTemplate = new TransactionTemplate();
        newTemplate.name = templateName;
        String transactionName = transactionNameField.getText();
        LocalDate date = transactionDatePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = formatter.format(date);
        float value = Float.parseFloat(transactionValueField.getText());
        newTemplate.exampleTransaction = new Transaction(transactionName, dateString, value);
        //SparbuchData.templates.add(newTemplate);
    }

    public void SetTransactionName(String name)
    {
        transactionNameField.setText(name);
    }

    public void SetTransactionValue(String value)
    {
        transactionValueField.setText(value);
    }

    public void SetTransactionDate(String date)
    {
        int day = Integer.parseInt(date.split("\\.")[0]);
        int month = Integer.parseInt(date.split("\\.")[1]);
        int year = Integer.parseInt(date.split("\\.")[2]);
        transactionDatePicker.setValue(LocalDate.of(year, month, day));
    }

    public void SetMainView(TransactionEditorView mainView)
    {
        this.mainView = mainView;
    }
}
