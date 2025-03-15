package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class MainController
{
    FileManager fm = new FileManager();
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

    private SparbuchData mainData;

    private Account selectedAccount;

    public void UpdateUI()
    {
        LoadAccountsList();
        if(selectedAccount != null)
        {
            LoadTransactionsList();
        }
    }

    public void LoadAccountsList()
    {
        accountsList.getItems().clear();
        for(int i = 0; i < mainData.accounts.size(); i++)
        {
            accountsList.getItems().add(mainData.accounts.get(i).name);
        }
    }

    public void LoadTransactionsList()
    {
        transactionsList.getItems().clear();
        for(int i = 0; i < selectedAccount.transactions.size(); i++)
        {
            Transaction transaction = selectedAccount.transactions.get(i);
            transactionsList.getItems().add(transaction.name + " " + transaction.value + " " + transaction.date);
        }
    }

    public void SetMainData(SparbuchData mainData)
    {
        this.mainData = mainData;
    }

    @FXML
    private void OpenTransactionEditor()
    {
        TransactionEditorView te = new TransactionEditorView();
        if(te.Show())
        {
            selectedAccount.transactions.add(te.GetResultValue());
            LoadTransactionsList();
            SaveData();
        }
    }

    @FXML
    private void EditTransaction()
    {
        int transactionSelect = transactionsList.getSelectionModel().getSelectedIndex();
        Transaction transaction = selectedAccount.transactions.get(transactionSelect);
        TransactionEditorView te = new TransactionEditorView();
        te.SetSelectedTransaction(transaction);
        if(te.Show())
        {
            selectedAccount.transactions.remove(transactionSelect);
            selectedAccount.transactions.add(transactionSelect, te.GetResultValue());
            LoadTransactionsList();
            SaveData();
        }
    }

    @FXML
    private void OpenAccountEditor()
    {
        //TODO: Implementieren
    }

    @FXML
    private void OnSelectedIndexChangedAccountsList()
    {
        selectedAccount = mainData.accounts.get(accountsList.getSelectionModel().getSelectedIndex());
        LoadTransactionsList();
    }

    private void SaveData()
    {
        fm.WriteFile(mainData);
    }
}