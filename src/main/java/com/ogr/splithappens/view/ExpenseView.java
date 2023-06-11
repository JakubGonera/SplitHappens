package com.ogr.splithappens.view;

import com.ogr.splithappens.model.Category;
import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.Person;
import com.ogr.splithappens.model.Pair;
import com.ogr.splithappens.viewmodel.IViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ExpenseView {
    private final IViewModel viewModel;
    private final Stage window;
    @FXML
    TextField titleField;
    @FXML
    TextField valueField;
    @FXML
    ChoiceBox<Person> payerField;
    @FXML
    TextField descriptionField;
    @FXML
    ChoiceBox<Category> categoryField;
    @FXML
    DatePicker dateField;
    @FXML
    Text errorText;
    @FXML
    Button addButton;
    @FXML
    Label photoPath;
    @FXML
    ChoiceBox<String> splitChoice;
    @FXML
    ScrollPane splitPane;
    @FXML
    GridPane splitGrid;

    final FileChooser fileChooser = new FileChooser();

    public ExpenseView(IViewModel viewModel, Stage window) {
        this.viewModel = viewModel;
        this.window = window;
    }
    
    public void setBindings() {
        ObservableList<Person> personsList = viewModel.getPersonsList().getValue();
        valueField.setTextFormatter(new TextFormatter<>(new Common.SimpleStringConverter()));
        payerField.getItems().addAll(personsList);
        
        // Fill split options
        splitChoice.getItems().add("Even split");
        splitChoice.getItems().add("Detailed split");
        splitChoice.getItems().add("Percentage split");
        splitChoice.getSelectionModel().selectFirst();

        // Fill category options
        categoryField.getItems().addAll(Category.values());
        categoryField.getSelectionModel().select(Category.Other);

        // Bind what fields are enabled depending on the split method
        splitGrid.prefWidthProperty().bind(splitPane.widthProperty().add(-10));
        valueField.disableProperty().bind(splitChoice.getSelectionModel().selectedItemProperty().isEqualTo("Detailed split"));
        splitPane.disableProperty().bind(splitChoice.getSelectionModel().selectedItemProperty().isEqualTo("Even split"));
        splitChoice.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            changeSplitMethod(t1);
        });

        configureFileChooser(fileChooser);

        splitGrid.getColumnConstraints().clear();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(150);
        col1.setPrefWidth(150);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(150);
        col2.setPrefWidth(150);
        splitGrid.getColumnConstraints().addAll(col1, col2);

        splitGrid.setVgap(7);

        int row = 0;

        List<TextField> detailedFields = new ArrayList<>();
        for (Person person : personsList) {
            Label name = new Label(person.getName());
            name.setFont(Font.font("System", 14));
            splitGrid.add(name, 0, row);
            GridPane.setMargin(name, new Insets(0, 0, 0, 10));

            TextField value = new TextField();
            value.setText("0");
            value.setTextFormatter(new TextFormatter<>(new Common.SimpleStringConverter()));
            splitGrid.add(value, 1, row);
            detailedFields.add(value);

            Label currencyText = new Label("zł");
            splitGrid.add(currencyText, 1, row);
            GridPane.setHalignment(currencyText, HPos.RIGHT);
            GridPane.setMargin(currencyText, new Insets(0, 10, 0, 0));
            row++;
        }

        splitPane.setContent(splitGrid);
        splitGrid.setPadding(new Insets(10, 0, 0, 0));
        // ------------------

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // Validation of the data
                if (Objects.equals(titleField.getText(), "")) {
                    errorText.setText("Empty title!");
                    return;
                }
                if (splitChoice.getSelectionModel().selectedItemProperty().isEqualTo("Detailed split").get()) {
                    boolean nonZero = false;
                    for (TextField f : detailedFields) {
                        if (Objects.equals(f.getText(), "")) {
                            errorText.setText("Detailed split not filled!");
                            return;
                        }
                        if (Common.parseAmountToGrosze(f.getText()) < 0) {
                            errorText.setText("Negative value!");
                            return;
                        }
                        if (Common.parseAmountToGrosze(f.getText()) != 0) {
                            nonZero = true;
                        }
                    }
                    if (!nonZero) {
                        errorText.setText("Detailed split filled with zeros!");
                        return;
                    }
                } else if(splitChoice.getSelectionModel().selectedItemProperty().isEqualTo("Even split").get()){

                   if (Objects.equals(valueField.getText(), "")) {
                       errorText.setText("Empty value!");
                       return;
                   }
                   if (Common.parseAmountToGrosze(valueField.getText()) == 0) {
                       errorText.setText("Zero value!");
                       return;
                   }
                   if (Common.parseAmountToGrosze(valueField.getText()) < 0) {
                       errorText.setText("Negative value!");
                       return;
                   }
                }
                else{
                    int sum = 0;
                    for (TextField f : detailedFields) {
                        if (Objects.equals(f.getText(), "")) {
                            errorText.setText("Percentage split not filled!");
                            return;
                        }
                        if (Integer.parseInt(f.getText()) < 0) {
                            errorText.setText("Negative value!");
                            return;
                        }
                        if(Integer.parseInt(f.getText()) > 100){
                            errorText.setText("Over 100 percent!");
                            return;
                        }
                        sum += Integer.parseInt(f.getText());
                    }
                    if(sum != 100){
                        errorText.setText("Percentages don't add up to 100!");
                        return;
                    }
                }


                if (payerField.getSelectionModel().isEmpty()) {
                    errorText.setText("Payer not selected!");
                    return;
                }

                List<Pair<Integer, Integer>> borrowers = new ArrayList<>();

                int sumValue = 0;
                if (splitChoice.getSelectionModel().getSelectedItem().equals("Detailed split")) {
                    for (int i = 0; i < detailedFields.size(); i++) {
                        int value = Common.parseAmountToGrosze(detailedFields.get(i).getText());
                        if (value != 0) {
                            sumValue += value;
                            borrowers.add(new Pair<>(personsList.get(i).getID(), value));
                        }
                    }
                } else if(splitChoice.getSelectionModel().getSelectedItem().equals("Even split")){
                    int value = Common.parseAmountToGrosze(valueField.getText());
                    sumValue += value;
                    for (Person person : personsList) {
                        borrowers.add(new Pair<>(person.getID(), (int) ((float)value / personsList.size())));
                    }
                }
                else{
                    int value = Common.parseAmountToGrosze(valueField.getText());
                    sumValue += value;
                    for (int i = 0; i < detailedFields.size(); i++) {
                        int percentage = Integer.parseInt(detailedFields.get(i).getText());
                        if (percentage != 0) {
                            borrowers.add(new Pair<>(personsList.get(i).getID(), (int) (value * (float)percentage / 100)));
                        }
                    }
                }

                // Get the date from the datepicker
                Date date = new Date();
                if(dateField.getValue() != null){
                    date = Date.from(dateField.getValue().atStartOfDay().toInstant(java.time.ZoneOffset.UTC));
                }

                // Load photo
                Image photo = null;
                if(!Objects.equals(photoPath.getText(), "") && !Objects.equals(photoPath.getText(), "No file selected")){
                    photo = new Image(photoPath.getText());
                }
                
                // Construct payload and send it to the viewmodel and close window
                Expense expensePayload = new Expense(
                    titleField.getText(), 
                    payerField.getSelectionModel().getSelectedItem().getID(), 
                    sumValue, 
                    borrowers, 
                    descriptionField.getText(),
                    date,
                    categoryField.getSelectionModel().getSelectedItem(),
                    photo,
                    false);
                viewModel.addExpense(expensePayload);

                window.close();

            }
        });

    }

    private void changeSplitMethod(String method) {
        if(method.equals("Detailed split")){
            for(int i = 0; i < splitGrid.getChildren().size(); i++){
                if(splitGrid.getChildren().get(i) instanceof TextField TextField){
                    TextField.setTextFormatter(new TextFormatter<>(new Common.SimpleStringConverter()));
                }
                if(splitGrid.getChildren().get(i) instanceof Label label){
                    if(label.getText().equals("%")){
                        label.setText("zł");
                    }
                }
            }
        }
        else if(method.equals("Percentage split")){
            for(int i = 0; i < splitGrid.getChildren().size(); i++){
                if(splitGrid.getChildren().get(i) instanceof TextField TextField){
                    TextField.setTextFormatter(new TextFormatter<>(new Common.IntegerStringConverter()));
                }
                if(splitGrid.getChildren().get(i) instanceof Label label){
                    if(label.getText().equals("zł")){
                        label.setText("%");
                    }
                }
            }
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Select photo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Pictures"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
    }

    @FXML
    protected void onSelectPhoto(){
        File file = fileChooser.showOpenDialog(window);
        if(file != null){
            photoPath.setText(file.getAbsolutePath());
        }
    }
}
