package com.example.sparbuch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TemplateEditorView
{
    private int selectedTemplate = -1;
    private List<TransactionTemplate> resultValue;
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

    public void AddToResultValue(TransactionTemplate newElement)
    {
        resultValue.add(newElement);
    }

    public void UpdateElement(int index, TransactionTemplate newElement)
    {
        resultValue.set(index, newElement);
    }

    public void DeleteFromResultValue(int index)
    {
        resultValue.remove(index);
    }

    public List<TransactionTemplate> GetResultValue()
    {
        return resultValue;
    }

    public void SetSelectedTemplate(int selectedTemplate)
    {
        this.selectedTemplate = selectedTemplate;
    }

    public int GetSelectedTemplate()
    {
        return selectedTemplate;
    }

    public TransactionTemplate GetTemplateFromIndex(int index)
    {
        return resultValue.get(index);
    }

    public void Close()
    {
        stage.close();
    }

    public void Show(List<TransactionTemplate> templateList)
    {
        resultValue = templateList;
        teController.SetTemplateList(templateList);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
