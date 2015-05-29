package vuelo.entregable2.app;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/vuelo/entregable2/view/vista.fxml"));
			Scene scene = new Scene(root,1000,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icono.png"))); 
			primaryStage.setTitle("Panel de información sobre vuelos");
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(1000);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	


	public static void main(String[] args) {
		launch(args);
	}
}
