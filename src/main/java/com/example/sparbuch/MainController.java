package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Locale;
import java.util.Optional;

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
            transactionsList.getItems().add(transaction.name + " " + String.format(Locale.GERMAN, "%.2f", transaction.value) + "€ " + transaction.date);
        }
    }

    public void CalculateBalance()
    {
        float sum = 0.0f;
        for(Transaction t : selectedAccount.transactions)
        {
            sum += t.value;
        }
        accountBalance.setText(String.format(Locale.GERMAN, "%.2f", sum) + "€");
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
            CalculateBalance();
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
            CalculateBalance();
            SaveData();
        }
    }

    @FXML
    private void OpenAccountEditor()
    {
        AccountEditorView ae = new AccountEditorView();
        if(ae.Show())
        {
            mainData.accounts.add(ae.GetResultValue());
            LoadAccountsList();
            SaveData();
        }
    }

    @FXML
    private void EditAccount()
    {
        AccountEditorView ae = new AccountEditorView();
        ae.SetSelectedAccount(selectedAccount);
        if(ae.Show())
        {
            Account editedAccount = ae.GetResultValue();
            selectedAccount.name = editedAccount.name;
            selectedAccount.saveTarget = editedAccount.saveTarget;
            LoadAccountsList();
            SaveData();
        }
    }

    @FXML
    private void DeleteAccount()
    {
        Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
        deleteAlert.setTitle("Account Löschen!");
        deleteAlert.setHeaderText("Account Löschen!");
        deleteAlert.setContentText("Wollen Sie diesen Account Löschen?");

        ButtonType applyButton = new ButtonType("Löschen");
        ButtonType cancelButton = new ButtonType("Abbrechen");
        deleteAlert.getButtonTypes().setAll(applyButton, cancelButton);

        Optional<ButtonType> result = deleteAlert.showAndWait();
        if(result.get() == applyButton)
        {
            mainData.accounts.remove(accountsList.getSelectionModel().getSelectedIndex());
            LoadAccountsList();
            SaveData();
        }
    }
    @FXML
    private void DeleteTransaction()
    {
        Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
        deleteAlert.setTitle("Transaktion Löschen!");
        deleteAlert.setHeaderText("Transaktion Löschen!");
        deleteAlert.setContentText("Wollen Sie diese Transaktion Löschen?");

        ButtonType applyButton = new ButtonType("Löschen");
        ButtonType cancelButton = new ButtonType("Abbrechen");
        deleteAlert.getButtonTypes().setAll(applyButton, cancelButton);

        Optional<ButtonType> result = deleteAlert.showAndWait();
        if(result.get() == applyButton)
        {
            selectedAccount.transactions.remove(transactionsList.getSelectionModel().getSelectedIndex());
            LoadTransactionsList();
            CalculateBalance();
            SaveData();
        }
    }

    @FXML
    private void OnSelectedIndexChangedAccountsList()
    {
        if(accountsList.getSelectionModel().getSelectedIndex() != -1)
        {
            selectedAccount = mainData.accounts.get(accountsList.getSelectionModel().getSelectedIndex());
            LoadTransactionsList();
            CalculateBalance();
        }

    }

    private void SaveData()
    {
        fm.WriteFile(mainData);
    }
}