package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Controller.Controller;

public class View {
    private Scene scene;
	private TableElement tableElement;
    private Controller controller;
    private Stage stage;
    private VBox root;
    private RequestElement requestElement;
    public enum WindowType {
        DELETE, SEARCH
    }
    public enum INIT_WINDOW_LABEL {
        FILE_MENU_LABEL_TEXT("Файл"),
        EDIT_MENU_LABEL_TEXT("Редактировать"),
        OPEN_DOC_MENU_ITEM_LABEL_TEXT("Открыть документ"),
        SAVE_DOC_MENU_ITEM_LABEL_TEXT("Сохранить документ"),
        ADD_ITEM_MENU_ITEM_LABEL_TEXT("Добавить строчки"),
        SEARCH_ITEMS_MENU_ITEM_LABEL_TEXT("Искать строчки"),
        DELETE_ITEMS_MENU_ITEM_LABEL_TEXT("Удалить строчки"),
        CLOSE_APP_MENU_ITEM_LABEL_TEXT("Выход"),
        OPEN_DOC_BUTTON_LABEL_TEXT("Открыть документ"),
        SAVE_DOC_BUTTON_LABEL_TEXT("Сохранить документ"),
        ADD_ITEMS_BUTTON_LABEL_TEXT("Добавить строчки"),
        SEARCH_ITEMS_BUTTON_LABEL_TEXT("Искать строчки"),
        DELETE_ITEMS_BUTTON_LABEL_TEXT("Удалить строчки");
        private final String value;
        INIT_WINDOW_LABEL(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
	public View(Controller controller) {
        final int STAGE_WIDTH  = 1000;
        final int STAGE_HEIGHT = 650;
        final String STAGE_TITLE_TEXT = "Lab2";

        this.controller = controller;
        initWindow();
        stage = new Stage();
        stage.setWidth (STAGE_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
        stage.setTitle (STAGE_TITLE_TEXT);
        stage.setScene(scene);
	}

	private void initWindow(){
        MenuItem openDocMenuItem = new MenuItem(INIT_WINDOW_LABEL.OPEN_DOC_MENU_ITEM_LABEL_TEXT.getValue());
        MenuItem saveMenuItem = new MenuItem(INIT_WINDOW_LABEL.SAVE_DOC_MENU_ITEM_LABEL_TEXT.getValue());
        MenuItem addItemsMenuItem = new MenuItem(INIT_WINDOW_LABEL.ADD_ITEM_MENU_ITEM_LABEL_TEXT.getValue());
        MenuItem searchItemsMenuItem = new MenuItem(INIT_WINDOW_LABEL.SEARCH_ITEMS_MENU_ITEM_LABEL_TEXT.getValue());
        MenuItem deleteItemsMenuItem = new MenuItem(INIT_WINDOW_LABEL.DELETE_ITEMS_MENU_ITEM_LABEL_TEXT.getValue());
        MenuItem closeAppMenuItem  = new MenuItem(INIT_WINDOW_LABEL.CLOSE_APP_MENU_ITEM_LABEL_TEXT.getValue());
        Menu fileMenu = new Menu(INIT_WINDOW_LABEL.FILE_MENU_LABEL_TEXT.getValue());
        Menu editMenu = new Menu(INIT_WINDOW_LABEL.EDIT_MENU_LABEL_TEXT.getValue());
        MenuBar menuBar = new MenuBar();
        Button openDocButton = new Button(INIT_WINDOW_LABEL.OPEN_DOC_BUTTON_LABEL_TEXT.getValue());
        Button saveDocButton = new Button(INIT_WINDOW_LABEL.SAVE_DOC_BUTTON_LABEL_TEXT.getValue());
        Button addItemsButton = new Button(INIT_WINDOW_LABEL.ADD_ITEMS_BUTTON_LABEL_TEXT.getValue());
        Button searchItemsButton = new Button(INIT_WINDOW_LABEL.SEARCH_ITEMS_BUTTON_LABEL_TEXT.getValue());
        Button deleteItemsButton = new Button(INIT_WINDOW_LABEL.DELETE_ITEMS_BUTTON_LABEL_TEXT.getValue());
        ToolBar instruments;

        fileMenu.getItems().addAll(
                openDocMenuItem,
                saveMenuItem,
                new SeparatorMenuItem(),
                closeAppMenuItem);
        editMenu.getItems().addAll(
                addItemsMenuItem,
                new SeparatorMenuItem(),
                searchItemsMenuItem,
                deleteItemsMenuItem);
        menuBar.getMenus().addAll(
                fileMenu,
                editMenu);

        instruments = new ToolBar(
                openDocButton,
                saveDocButton,
                new Separator(),
                addItemsButton,
                searchItemsButton,
                deleteItemsButton);

        tableElement = new TableElement(controller.getStudentList() , controller);

        root = new VBox();
        root.getChildren().addAll(
                menuBar,
                instruments,
                tableElement.get());
        scene = new Scene(root);

        openDocButton.setOnAction(ae -> openDoc());
            openDocMenuItem.setOnAction(ae -> openDoc());
        saveDocButton.setOnAction(ae -> saveDoc());
            saveMenuItem.setOnAction(ae -> saveDoc());
        addItemsButton.setOnAction(ae -> addItems());
            addItemsMenuItem.setOnAction(ae -> addItems());
        searchItemsButton.setOnAction(ae -> searchItems());
            searchItemsMenuItem.setOnAction(ae -> searchItems());
        deleteItemsButton.setOnAction(ae -> deleteItems());
            deleteItemsMenuItem.setOnAction(ae -> deleteItems());
        closeAppMenuItem.setOnAction(ae -> Platform.exit());
    }

	public Stage getStage(){
	    return stage;
    }

    private void openDoc(){
        FileChooser openDocChooser = new FileChooser();

        openDocChooser.setTitle("Открыть документ");
        openDocChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                new FileChooser.ExtensionFilter("XML-документ", "*.xml")
        );

        try {
            controller.openDoc(openDocChooser.showOpenDialog(stage));
        } catch (Exception exception){
            exception.printStackTrace();
        }

        tableElement.rewriteDefaultList(controller.getStudentList());
        tableElement.resetToDefaultItems();
    }

    private void saveDoc(){
        FileChooser saveDocChooser = new FileChooser();

        saveDocChooser.setTitle("Сохранить документ");
        saveDocChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                                                    new FileChooser.ExtensionFilter("XML-документ", "*.xml"));

        controller.saveDoc(saveDocChooser.showSaveDialog(stage));
    }

	private void addItems(){

        TextField studentNameField = new TextField();
        TextField surnameField = new TextField();
        TextField patronymField = new TextField();
        TextField groupField = new TextField();
        TextField skippingCauseOfSickField = new TextField();
        TextField skippingCauseOfOtherField = new TextField();
        TextField skippingWithNoReasonField = new TextField();
        TextField skippingAtAllField = new TextField();

	    GridPane root = new GridPane();
        Alert addItemWindow;

        root.addRow(0,
                new Label("Имя"),
                studentNameField
        );
        root.addRow(1,
                new Label("Фамилия"),
                surnameField
        );
        root.addRow(2,
                new Label("Отчество"),
                patronymField
        );
        root.addRow(3,
                new Label("Группа"),
                groupField
        );
        root.addRow(4,
                new Label("Пропусков по болезни"),
                skippingCauseOfSickField
        );
        root.addRow(5,
                new Label("Пропусков по другим причинам"),
                skippingCauseOfOtherField
        );
        root.addRow(6,
                new Label("Пропусков без причины"),
                skippingWithNoReasonField
        );
//        root.addRow(7,
//                new Label("Пропусков всего"),
//                skippingAtAllField
//        );

        addItemWindow = createEmptyCloseableDialog();
        addItemWindow.setTitle("Добавить");
        addItemWindow.getDialogPane().setContent(root);
        addItemWindow.show();

        ((Button)addItemWindow.getDialogPane().lookupButton(addItemWindow.getButtonTypes().get(0))).setOnAction(ae->{
            controller.addStudent(
                    studentNameField.getText(),
                    surnameField.getText(),
                    patronymField.getText(),
                    groupField.getText(),
                    Integer.parseInt(skippingCauseOfSickField.getText()),
                    Integer.parseInt(skippingCauseOfOtherField.getText()),
                    Integer.parseInt(skippingWithNoReasonField.getText())
            );
            tableElement.resetToDefaultItems();
            addItemWindow.close();
        });
    }

    private void searchItems(){
        final String WINDOW_TITLE_TEXT = "Искать строчки";
        Alert searchItemsWindow;
        requestElement = new RequestElement(WindowType.SEARCH, controller);

        searchItemsWindow = createEmptyCloseableDialog();
        searchItemsWindow.setTitle(WINDOW_TITLE_TEXT);
        searchItemsWindow.getDialogPane().setContent(requestElement.get());
        searchItemsWindow.show();

        ((Button)searchItemsWindow.getDialogPane().lookupButton(searchItemsWindow.getButtonTypes().get(0))).setOnAction(
                ae-> searchItemsWindow.close()
        );
    }

    private void deleteItems(){
        final String WINDOW_TITLE_TEXT = "Удалить строчки";
        Alert        deleteItemsWindow;
        RequestElement requestElement = new RequestElement(WindowType.DELETE, controller);

        deleteItemsWindow = createEmptyCloseableDialog();
        deleteItemsWindow.setTitle(WINDOW_TITLE_TEXT);
        deleteItemsWindow.getDialogPane().setContent(requestElement.get());
        deleteItemsWindow.show();

        ((Button)deleteItemsWindow.getDialogPane().lookupButton(deleteItemsWindow.getButtonTypes().get(0))).setOnAction(ae->{
            createDeleteInfoWindow(String.valueOf(requestElement.search(controller).size()));
            controller.delete(requestElement.search(controller));
            tableElement.resetToDefaultItems();
            deleteItemsWindow.close();
        });
    }

    private void createDeleteInfoWindow(String deleteInfo){
        final String CLOSE_BUTTON_LABEL_TEXT = "ОК";
        ButtonType closeButton = new ButtonType(CLOSE_BUTTON_LABEL_TEXT);
	    Alert window = new Alert(Alert.AlertType.NONE);
	    VBox vertice = new VBox();

	    vertice.getChildren().add(new Label("Удалено " + deleteInfo + " строчек."));
	    window.getDialogPane().setContent(vertice);
        window.getButtonTypes().addAll(closeButton);
        window.show();
    }

    private Alert createEmptyCloseableDialog(){
        final String CLOSE_BUTTON_LABEL_TEXT = "Далее";
        ButtonType closeButton = new ButtonType(CLOSE_BUTTON_LABEL_TEXT);
        Alert window = new Alert(Alert.AlertType.NONE);

        window.getButtonTypes().addAll(closeButton);
        return window;
    }
}
