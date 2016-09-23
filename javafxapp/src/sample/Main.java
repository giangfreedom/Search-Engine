package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application{
    Button search, switchindex, switchmain;
    Stage window;
    Scene mainscene, indexscene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window.setTitle("Search Engine - Milestone 1");

        //Main Scene
        BorderPane mainlayout = new BorderPane();
        mainlayout.getStyleClass().add("background");

        //top of borderpane
        Label label = new Label("Vsion Search");
        label.setMinSize(1010,50);
        label.setId("topbar");
        mainlayout.setTop(label);

        //Middle Area will be a GridPane with 2 columns: 1)Output 2)Document Preview
        GridPane middle = new GridPane();
        middle.setMinSize(1000,600);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        middle.getColumnConstraints().addAll(col1,col2);

        ScrollPane scroll = new ScrollPane();
        TextArea output = new TextArea("Search Results for : WORD HERE\n 1)Document 1: [503] [100]" +
                "\n2)Document 3: [21] [222] ");
        output.setMinSize(500,610);
        output.setEditable(false);
        scroll.getStyleClass().add("scroll");
        output.getStyleClass().add("output");
        scroll.setContent(output);
        scroll.setFitToWidth(true);

        Label preview = new Label("Document Preview:\n\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam varius malesuada arcu," +
                " quis porttitor velit. Pellentesque rutrum justo id urna fringilla, et ullamcorper mauris vulputate. " +
                "Donec eu sapien dolor. Sed venenatis elit varius eros dictum, nec interdum lacus fringilla. " +
                "Fusce lacinia ante in efficitur vehicula. Sed sagittis tortor ut bibendum iaculis. Maecenas consequat" +
                " turpis sem, consectetur viverra est euismod et. Etiam viverra nibh vitae justo consectetur gravida. " +
                "Praesent nec neque non risus placerat convallis sed eu lorem. Cras convallis mattis ipsum, at ultricies nulla.");
        preview.setWrapText(true);
        preview.getStyleClass().add("preview");
        preview.setMinSize(500,610);

        middle.add(scroll,0,0);
        middle.add(preview,1,0);
        mainlayout.setCenter(middle);

        //Bottom Search Bar
        HBox searchbar = new HBox();
        searchbar.setStyle("-fx-padding:10px;");
        TextField userinput = new TextField();
        userinput.setMinWidth(100);
        search = new Button();
        search.setText("Search");
        search.getStyleClass().add("buttons");
        search.setOnAction(e -> {
            System.out.println("Heyyy");
        });

        switchindex = new Button("Index Lookup");
        switchindex.getStyleClass().add("buttons");
        switchindex.setOnAction(e -> window.setScene(indexscene));

        searchbar.getChildren().addAll(userinput,search, switchindex);
        searchbar.setId("bottombar");
        searchbar.setMinHeight(50);
        mainlayout.setBottom(searchbar);


        mainscene = new Scene(mainlayout, 1000,700);
        mainscene.getStylesheets().add("sample/style.css");

        //Index Scene
        StackPane indexlayout = new StackPane();

        switchmain = new Button("Back to Search");
        switchmain.setOnAction(e -> window.setScene(mainscene));

        indexlayout.getChildren().addAll(switchmain);
        indexscene = new Scene(indexlayout, 1000, 700);


        //main
        window.setScene(mainscene);
        window.setResizable(false);
        window.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
