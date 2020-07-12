
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * This class is the GUI application.
 */
public class Main extends Application {

    /**
     * launches the program
     *
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }


    /**
     * Essentially creates the entire GUI
     * @param primaryStage the window that the GUI will exist in
     * @throws Exception in case it is not possible to create the GUI.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(e -> System.exit (0));
        primaryStage.setTitle("Discord Plays Launcher");

        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(200);
        primaryStage.setMaxWidth(400);
        primaryStage.setMaxHeight(200);

        Scene scene = createScene();

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private Scene createScene() {
        VBox vBox = createLayout();
        return new Scene(vBox, 400, 200);
    }

    private VBox createLayout() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(25, 25, 25, 25));

        Text title = new Text("Welcome!");

        Text description = new Text("Please enter the token that your bot will be using.");

        Label token = new Label("Token: ");

        TextField tokenField = new TextField();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(token, tokenField);

        Button startBot = new Button("Start Bot");

        vBox.getChildren().addAll(title, description, hBox, startBot);

        return vBox;
    }

}
