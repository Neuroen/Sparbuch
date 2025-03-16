package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AccountEditorController
{
    private AccountEditorView mainView;
    private boolean isNewAccount = true;

    @FXML
    private TextField accountNameField;

    @FXML
    private TextField accountTargetField;

    @FXML
    private void AcceptButtonClicked()
    {
        Account newAccount = new Account();
        if(!accountNameField.getText().isEmpty())
        {
            newAccount.name = accountNameField.getText();
        }

        if(accountTargetField.getText().isEmpty())
        {
            newAccount.saveTarget = 0.0f;
        }
        else
        {
            newAccount.saveTarget = Float.parseFloat(accountTargetField.getText());
        }

        if(isNewAccount)
        {
            newAccount.transactions = new ArrayList<>();
        }

        mainView.SetResultValue(newAccount);
        mainView.Close(true);
    }

    @FXML
    private void CancelButtonClicked()
    {
        mainView.Close(false);
    }

    public void SetAccountName(String accountName)
    {
        accountNameField.setText(accountName);
        isNewAccount = false;
    }

    public void SetAccountTarget(String accountTarget)
    {
        accountTargetField.setText(accountTarget);
        isNewAccount = false;
    }

    public void SetMainView(AccountEditorView mainView)
    {
        this.mainView = mainView;
    }
}
