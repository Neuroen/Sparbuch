package com.example.sparbuch;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class TemplateEditorController
{
    @FXML
    public TextField transactionNameField;
    @FXML
    public TextField transactionValueField;
    @FXML
    public TextField templateNameField;
    @FXML
    public ComboBox<String> templateListBox;
    @FXML
    public Button deleteTemplateButton;

    public TemplateEditorView mainView;

    @FXML
    private void AcceptButtonClicked()
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
            newTransaction.value = Float.parseFloat(transactionValueField.getText());
        }

        TransactionTemplate newTemplate = new TransactionTemplate();
        if(!templateNameField.getText().isEmpty())
        {
            newTemplate.name = templateNameField.getText();
        }
        else
        {
            return;
        }
        newTemplate.exampleTransaction = newTransaction;
        int selectedTemplate = templateListBox.getSelectionModel().getSelectedIndex() - 1;
        if(selectedTemplate == -1)
        {
            mainView.AddToResultValue(newTemplate);
        }
        else
        {
            mainView.UpdateElement(selectedTemplate, newTemplate);
        }
        mainView.Close();
    }

    @FXML
    private void CancelButtonClicked()
    {
        mainView.Close();
    }

    public void SetTemplateList(List<TransactionTemplate> templateList)
    {
        templateListBox.getItems().add("Neues Template");
        for(TransactionTemplate template : templateList)
        {
            templateListBox.getItems().add(template.name);
        }
        templateListBox.getSelectionModel().select(0);
    }

    public void DeleteTemplate()
    {
        Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
        deleteAlert.setTitle("Template Löschen!");
        deleteAlert.setHeaderText("Template Löschen!");
        deleteAlert.setContentText("Wollen Sie dieses Template Löschen?");

        ButtonType applyButton = new ButtonType("Löschen");
        ButtonType cancelButton = new ButtonType("Abbrechen");
        deleteAlert.getButtonTypes().setAll(applyButton, cancelButton);

        Optional<ButtonType> result = deleteAlert.showAndWait();
        if(result.get() == applyButton)
        {
            int selectedTemplate = templateListBox.getSelectionModel().getSelectedIndex() - 1;
            if (selectedTemplate != -1)
            {
                mainView.DeleteFromResultValue(selectedTemplate);
                templateListBox.getItems().remove(selectedTemplate + 1);
            }
        }
    }

    public void SetMainView(TemplateEditorView mainView)
    {
        this.mainView = mainView;
    }

    public void OnSelectedTemplateChanged()
    {
        int selectedIndex = templateListBox.getSelectionModel().getSelectedIndex() - 1;
        if(selectedIndex == -1)
        {
            templateNameField.clear();
            transactionNameField.clear();
            transactionValueField.clear();
            deleteTemplateButton.setDisable(true);
        }
        else
        {
            TransactionTemplate selectedTemplate = mainView.GetTemplateFromIndex(selectedIndex);
            templateNameField.setText(selectedTemplate.name);
            transactionNameField.setText(selectedTemplate.exampleTransaction.name);
            transactionValueField.setText(String.valueOf(selectedTemplate.exampleTransaction.value));
            deleteTemplateButton.setDisable(false);
        }
    }
}
