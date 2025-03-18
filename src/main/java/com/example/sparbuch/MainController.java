package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Locale;
import java.util.Optional;

public class MainController
{
    FileManager fm = new FileManager();
    @FXML
    private ListView<String> accountsList;

    @FXML
    private TableView transactionsList;

    @FXML
    private Label accountBalance;

    @FXML
    private Label targetHeaderLabel;

    @FXML
    private Label targetProgressLabel;

    @FXML
    private Button editAccountButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Button createTransactionButton;

    @FXML
    private Button editTransactionButton;

    @FXML
    private Button deleteTransactionButton;

    @FXML
    private ProgressBar saveTargetIndicator;

    private SparbuchData mainData;

    private Account selectedAccount;

    public void Init()
    {
        transactionsList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<Transaction, String> nameColumn = new TableColumn<>("Bezeichnung");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Transaction, Float> valueColumn = new TableColumn<>("Wert");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Datum");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        transactionsList.getColumns().addAll(nameColumn, valueColumn, dateColumn);
        accountsList.getSelectionModel().selectFirst();
        OnSelectedIndexChangedAccountsList();
    }

    public void UpdateUI(boolean updateAccountsList)
    {
        if(updateAccountsList)
        {
            LoadAccountsList();
        }

        if(selectedAccount != null)
        {
            editAccountButton.setDisable(false);
            deleteAccountButton.setDisable(false);

            LoadTransactionsList();
            SetBalanceText(CalculateBalance());
            if(selectedAccount.saveTarget != 0.0f)
            {
                saveTargetIndicator.setVisible(true);
                targetProgressLabel.setVisible(true);
                targetHeaderLabel.setVisible(true);
                SetSaveTarget(CalculateSaveTarget());
                String balanceFormatted = String.format(Locale.GERMAN, "%.2f", CalculateBalance());
                String targetFormatted = String.format(Locale.GERMAN, "%.2f", selectedAccount.saveTarget);
                targetProgressLabel.setText(balanceFormatted + "€ von " + targetFormatted + "€");
            }
            else
            {
                saveTargetIndicator.setVisible(false);
                targetProgressLabel.setVisible(false);
                targetHeaderLabel.setVisible(false);
            }
        }
        else
        {
            transactionsList.getItems().clear();
            accountBalance.setText("");
            saveTargetIndicator.setVisible(false);
            targetProgressLabel.setVisible(false);
            targetHeaderLabel.setVisible(false);
        }
        UpdateButtons();
    }

    private void UpdateButtons()
    {
        if(selectedAccount != null)
        {
            editAccountButton.setDisable(false);
            deleteAccountButton.setDisable(false);
            createTransactionButton.setDisable(false);
        }
        else
        {
            editAccountButton.setDisable(true);
            deleteAccountButton.setDisable(true);
            createTransactionButton.setDisable(true);
        }

        if(transactionsList.getSelectionModel().getSelectedIndex() != -1)
        {
            createTransactionButton.setDisable(false);
            editTransactionButton.setDisable(false);
            deleteTransactionButton.setDisable(false);
        }
        else
        {
            editTransactionButton.setDisable(true);
            deleteTransactionButton.setDisable(true);
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
            transactionsList.getItems().add(transaction);
        }
    }

    public void SetBalanceText(float sum)
    {
        accountBalance.setText(String.format(Locale.GERMAN, "%.2f", sum) + "€");
    }

    public void SetSaveTarget(float percent)
    {
        saveTargetIndicator.setProgress(percent);
    }

    public float CalculateBalance()
    {
        float sum = 0.0f;
        for(Transaction t : selectedAccount.transactions)
        {
            sum += t.value;
        }
        return sum;
    }

    private float CalculateSaveTarget()
    {
        float balance = CalculateBalance();
        float targetPercent = (balance / (selectedAccount.saveTarget / 100)) / 100;
        if(targetPercent > 1.0f)
        {
            targetPercent = 1.0f;
        }
        return targetPercent;
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
            UpdateUI(false);
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
            UpdateUI(false);
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
            UpdateUI(true);
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
            UpdateUI(true);
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
            selectedAccount = null;
            mainData.accounts.remove(accountsList.getSelectionModel().getSelectedIndex());
            UpdateUI(true);
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
            UpdateUI(false);
            SaveData();
        }
    }

    @FXML
    private void OnSelectedIndexChangedAccountsList()
    {
        if(accountsList.getSelectionModel().getSelectedIndex() != -1)
        {
            selectedAccount = mainData.accounts.get(accountsList.getSelectionModel().getSelectedIndex());
            UpdateUI(false);
        }
        UpdateButtons();
    }

    @FXML
    private void OnSelectedIndexChangedTransactionsList()
    {
        UpdateButtons();
    }

    private void SaveData()
    {
        fm.WriteFile(mainData);
    }
}