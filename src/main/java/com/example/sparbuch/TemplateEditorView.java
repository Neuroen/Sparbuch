package com.example.sparbuch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TemplateEditorView
{
    private boolean result = false;
    private TransactionTemplate resultValue;
    private Stage stage;
    private TemplateEditorController teController;

    public TemplateEditorView()
    {
        stage = new Stage();
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("TemplateEditorView.fxml"));
        try
        {
            Scene scene = new Scene(fxmlLoader.load(), 400, 200);
            teController = fxmlLoader.getController();
            stage.setTitle("Transaktion Template");
            stage.setScene(scene);
            teController.SetMainView(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void SetResultValue(TransactionTemplate resultValue)
    {
        this.resultValue = resultValue;
    }

    public TransactionTemplate GetResultValue()
    {
        return resultValue;
    }

    public void SetSelectedTransaction(TransactionTemplate selectedTemplate)
    {
        teController.SetTransactionName(selectedTemplate.exampleTransaction.name);
        teController.SetTransactionValue(String.valueOf(selectedTemplate.exampleTransaction.value));
        teController.SetTemplateName(selectedTemplate.name);
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
