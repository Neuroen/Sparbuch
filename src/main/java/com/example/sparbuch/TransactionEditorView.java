package com.example.sparbuch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionEditorView
{
    private boolean result = false;
    private Transaction resultValue;
    private Transaction selectedTransaction;

    Stage stage;
    private TransactionEditorController teController;

    public TransactionEditorView()
    {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("TransactionEditorView.fxml"));
        try
        {
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            teController = fxmlLoader.getController();
            stage.setTitle("Transaktion");
            stage.setScene(scene);
            teController.SetMainView(this);
            if(selectedTransaction != null)
            {
                teController.SetTransactionName(selectedTransaction.name);
                teController.SetTransactionValue(String.valueOf(selectedTransaction.value));
                teController.SetTransactionDate(selectedTransaction.date);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*public TransactionEditorController( TODO add TransactionObject)
    {

    }
     */

    public void SetResultValue(Transaction resultValue)
    {
        this.resultValue = resultValue;
    }

    public Transaction GetResultValue()
    {
        return resultValue;
    }

    public void SetSelectedTransaction(Transaction selectedTransaction)
    {
        this.selectedTransaction = selectedTransaction;
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
        System.out.println(result);
        return result;
    }
}
