package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Controller.Controller;
import model.Student;

public class TableElement {
    private Label paginationLabel;
    private Label itemsCountLabel;
    private Button resetCountPages;
    private TextField rowsOnPageField;
    private TableView<Student> table;
    private ToolBar navigator;
    private ToolBar pagination;
    private Pane tableElement;
    private List<Student> defaultStudentList;
    private ObservableList<Student> StudentObsList;
    private ObservableList<Student> curStudentObsList;
    private Controller controller;
    private final int TABLE_HEIGHT = 450;
    private final int TABLE_WIDTH = 1000;
    private final int DEFAULT_ROWS_ON_PAGE_NUMBER = 20;
    private Button toBeginButton = new Button();
    private Button toLeftButton = new Button();
    private Button toRightButton = new Button();
    private Button toEndButton = new Button();
    private TableColumn<Student, String> snpCol = new TableColumn<>("ФИО");
    private TableColumn<Student, String> groupCol = new TableColumn<>("Группа");
    private TableColumn<Student, Integer> firstSkippingCol = new TableColumn("Пропусков по болезне");
    private TableColumn<Student, Integer> secondSkippingCol = new TableColumn<>("Пропусков по другой причине");
    private TableColumn<Student, Integer> thirdSkippingCol = new TableColumn<>("Пропусков без причины");
    private TableColumn<Student, Integer> fourthSkippingCol = new TableColumn("Итого");
    private TableColumn<Student, String> allSkippingCol = new TableColumn("Пропусков за год");


    public TableElement(List<Student> StudentList, Controller controller){
        toBeginButton.setText("<<");
        toEndButton.setText(">>");
        toLeftButton.setText("<");
        toRightButton.setText(">");
        defaultStudentList = StudentList;
        StudentObsList = FXCollections.observableArrayList(defaultStudentList);
        curStudentObsList = FXCollections.observableArrayList();
        this.controller = controller;
        snpCol.setCellValueFactory(new PropertyValueFactory<>("snp"));
        snpCol.setMinWidth(300);
        groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
        groupCol.setMinWidth(75);
        firstSkippingCol.setCellValueFactory(new PropertyValueFactory<>("skippingCauseOfSick"));
        firstSkippingCol.setMinWidth(175);
        secondSkippingCol.setCellValueFactory(new PropertyValueFactory<>("skippingCauseOfOther"));
        secondSkippingCol.setMinWidth(175);
        thirdSkippingCol.setCellValueFactory(new PropertyValueFactory<>("skippingWithNoReason"));
        thirdSkippingCol.setMinWidth(175);
        fourthSkippingCol.setCellValueFactory(new PropertyValueFactory<>("skippingAtAll"));
        fourthSkippingCol.setMinWidth(50);


        paginationLabel = new Label();
        navigator = new ToolBar(
                toBeginButton,
                toLeftButton,
                paginationLabel,
                toRightButton,
                toEndButton
        );



        itemsCountLabel = new Label(" " + StudentObsList.size() + " ");
        rowsOnPageField = new TextField();
        rowsOnPageField.setText(String.valueOf(DEFAULT_ROWS_ON_PAGE_NUMBER));
        resetCountPages = new Button("Обновить количество строк на странице");
        pagination = new ToolBar(
                itemsCountLabel,
                new Separator(),
                new Label("Строчек на странице "),
                rowsOnPageField,
                resetCountPages,
                new Separator(),
                navigator
        );

        table = new TableView<>();
        table.setMinHeight(TABLE_HEIGHT);
        table.setMaxWidth(TABLE_WIDTH);
        //порядок колонок

        allSkippingCol.getColumns().addAll(firstSkippingCol, secondSkippingCol, thirdSkippingCol, fourthSkippingCol);
        table.getColumns().addAll(
                snpCol,
                groupCol,
                allSkippingCol
//                firstSkippingCol,
//                secondSkippingCol,
//                thirdSkippingCol,
//                fourthSkippingCol
        );
        table.setItems(curStudentObsList);
        controller.setRowsOnPage(rowsOnPageField.getText(), StudentObsList, curStudentObsList);
        tableElement = new VBox();
        tableElement.getChildren().addAll(table, pagination);

        resetCountPages.setOnAction(ae -> {
            controller.setRowsOnPage(rowsOnPageField.getText(), StudentObsList, curStudentObsList);
            paginationLabel.setText(controller.getPagination());
        });
        toBeginButton.setOnAction(ae -> {
            controller.goBegin(StudentObsList, curStudentObsList);
            paginationLabel.setText(controller.getPagination());
        });
        toLeftButton.setOnAction(ae -> {
            controller.goLeft(StudentObsList, curStudentObsList);
            paginationLabel.setText(controller.getPagination());
        });
        toRightButton.setOnAction(ae -> {
            controller.goRight(StudentObsList, curStudentObsList);
            paginationLabel.setText(controller.getPagination());
        });
        toEndButton.setOnAction(ae -> {
            controller.goEnd(StudentObsList, curStudentObsList);
            paginationLabel.setText(controller.getPagination());
        });
    }

    public Pane get(){
        return tableElement;
    }

    public void rewriteDefaultList(List<Student> list){
        defaultStudentList = list;
    }

    public void resetToDefaultItems(){
        setObservableList(defaultStudentList);
        itemsCountLabel.setText(" " + StudentObsList.size() + " ");
        paginationLabel.setText(controller.getPagination());
    }

    public void setObservableList(List<Student> list){
        StudentObsList = FXCollections.observableArrayList(list);
        controller.setRowsOnPage(rowsOnPageField.getText(), StudentObsList, curStudentObsList);
        itemsCountLabel.setText(" " + StudentObsList.size() + " ");
        paginationLabel.setText(controller.getPagination());
    }
}
