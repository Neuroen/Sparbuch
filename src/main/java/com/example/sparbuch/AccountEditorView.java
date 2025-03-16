package com.example.sparbuch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountEditorView
{
    private Stage stage;
    private boolean result = false;
    private AccountEditorController aeController;
    private Account resultValue;

    public AccountEditorView()
    {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AccountEditorView.fxml"));
        try
        {
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            aeController = fxmlLoader.getController();
            stage.setTitle("Transaktion");
            stage.setScene(scene);
            aeController.SetMainView(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void SetResultValue(Account resultValue)
    {
        this.resultValue = resultValue;
    }

    public Account GetResultValue()
    {
        return this.resultValue;
    }

    public void SetSelectedAccount(Account selectedAccount)
    {
        aeController.SetAccountName(selectedAccount.name);
        aeController.SetAccountTarget(String.valueOf(selectedAccount.saveTarget));
    }

    public void Close(boolean state)
    {
        result = state;
        stage.close();
    }

    public boolean Show()
    {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return result;
    }
}
