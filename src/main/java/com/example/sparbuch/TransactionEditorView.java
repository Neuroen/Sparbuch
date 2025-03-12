package com.example.sparbuch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionEditorView
{
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

            teController.SetTransactionValue("2000");
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

    public void Show()
    {
        stage.show();
    }
}
