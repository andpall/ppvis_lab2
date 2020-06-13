package lab2;

import javafx.application.Application;
import javafx.stage.Stage;
import Controller.Controller;
import model.Student;
import view.View;

public class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage mainStage) {
		Student model = new Student();
		Controller controller = new Controller(model);
		View view = new View(controller);
		
		mainStage = view.getStage();
		mainStage.show();
	}
}
