package view;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Student;

import Controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class RequestElement {
    private String selectedItem;
    private ComboBox criteriaComBox;
    private Button searchButton;
    private TableElement tableElement;
    private GridPane grid;
    private Pane criteriaChooser;
    private Pane root;
    private List<Label> criteria1LabelList;
    private List<Label> criteria2LabelList;
    private List<Label> criteria3LabelList;
    private List<TextField> criteria1FieldList;
    private List<TextField> criteria2FieldList;
    private List<TextField> criteria3FieldList;


    public enum criteriaForRequesting {
        CRITERIA_1("По номеру группы или фамилии студента"),
        CRITERIA_2("По фамилии студента или виду пропуска"),
        CRITERIA_3("По фамилии студента или количеству пропусков по видам");
        private final String value;

        criteriaForRequesting(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static criteriaForRequesting getCriteriaByName(String value) {
            criteriaForRequesting res = null;
            for (criteriaForRequesting x : values()) {
                if (x.getValue().equals(value)) {
                    res = x;
                }
            }
            return res;
        }
    }

    public RequestElement(View.WindowType windowType, Controller controller){
        criteriaForRequesting criteria1 = criteriaForRequesting.CRITERIA_1;
        criteriaForRequesting criteria2 = criteriaForRequesting.CRITERIA_2;
        criteriaForRequesting criteria3 = criteriaForRequesting.CRITERIA_3;
        criteriaComBox = new ComboBox();
        criteriaComBox.getItems().addAll(
                criteria1.getValue(),
                criteria2.getValue(),
                criteria3.getValue()
        );
        criteriaComBox.setValue(criteria1.getValue());
        searchButton = new Button("Найти");
        criteriaChooser = new HBox();

        criteria1LabelList = new ArrayList<>();
        criteria1FieldList = new ArrayList<>();
        criteria2LabelList = new ArrayList<>();
        criteria2FieldList = new ArrayList<>();
        criteria3LabelList = new ArrayList<>();
        criteria3FieldList = new ArrayList<>();
        criteria1FieldList.add(new TextField());
        criteria1FieldList.add(new TextField());
        criteria2FieldList.add(new TextField());
        criteria2FieldList.add(new TextField());
        criteria3FieldList.add(new TextField());
        criteria3FieldList.add(new TextField());
        initCriteriaLists();
        grid = new GridPane();
        switchPreset();
        tableElement = new TableElement(new ArrayList<>(), controller);
        this.root = new VBox();
        if(windowType == View.WindowType.SEARCH){
            criteriaChooser.getChildren().addAll(
                    new Label("Критерий поиска:"),
                    criteriaComBox,
                    searchButton
            );

            this.root.getChildren().addAll(
                    new Separator(),
                    new Separator(),
                    criteriaChooser,
                    grid,
                    new Separator(),
                    new Separator(),
                    tableElement.get(),
                    new Separator(),
                    new Separator(),
                    new Separator()
            );
        }

        if(windowType == View.WindowType.DELETE){
            criteriaChooser.getChildren().addAll(
                    new Label("Критерий поиска: "),
                    criteriaComBox
            );

            this.root.getChildren().addAll(
                    new Separator(),
                    new Separator(),
                    criteriaChooser,
                    grid
            );
        }


        criteriaComBox.setOnAction(ae -> switchPreset());
        searchButton.setOnAction(ae->{
            List<Student> StudentList = search(controller);

            tableElement.setObservableList(StudentList);
        });
    }

    private void switchPreset(){
        grid.getChildren().clear();
        selectedItem = criteriaComBox.getSelectionModel().getSelectedItem().toString();
        criteriaForRequesting criteria = criteriaForRequesting.getCriteriaByName(selectedItem);
        switch (criteria){
            case CRITERIA_1:
                grid.addRow(0,criteria1LabelList.get(0), criteria1FieldList.get(0));
                grid.addRow(1,criteria1LabelList.get(1), criteria1FieldList.get(1));
                break;
            case CRITERIA_2:
                grid.addRow(0,criteria2LabelList.get(0), criteria2FieldList.get(0));
                grid.addRow(1,criteria2LabelList.get(1), criteria2FieldList.get(1));
                break;
            case CRITERIA_3:
                grid.addRow(0,criteria3LabelList.get(0), criteria3FieldList.get(0));
                grid.addRow(1,criteria3LabelList.get(1), criteria3FieldList.get(1));
        }
    }

    private void initCriteriaLists(){
        final String GROUP = "Группа: ";
        final String SURNAME = "Фамилия студента:";
        final String SKIP_TYPE = "Тип пропусков:";
        final String SKIP_AMOUNT = "Кол-во пропусков:";

        criteria1LabelList.add(new Label(GROUP));
        criteria1LabelList.add(new Label(SURNAME));
        criteria2LabelList.add(new Label(SURNAME));
        criteria2LabelList.add(new Label(SKIP_TYPE));
        criteria3LabelList.add(new Label(SURNAME));
        criteria3LabelList.add(new Label(SKIP_AMOUNT));

    }

    public Pane get(){
        return this.root;
    }

    public List<Student> search(Controller controller){
        List<String> criteriaListText;
        criteriaListText = new ArrayList<>();
        criteriaListText.add(criteria1FieldList.get(0).getText());
        criteriaListText.add(criteria1FieldList.get(1).getText());
        criteriaListText.add(criteria2FieldList.get(0).getText());
        criteriaListText.add(criteria2FieldList.get(1).getText());
        criteriaListText.add(criteria3FieldList.get(0).getText());
        criteriaListText.add(criteria3FieldList.get(1).getText());
        return controller.search(selectedItem, criteriaListText);
    }
}
