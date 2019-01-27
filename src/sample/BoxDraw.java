package sample;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class BoxDraw extends Application{

    public Parent createContent() throws Exception {

        // Box
        Box testBox = new Box(2, 2, 2);
        testBox.setMaterial(new PhongMaterial(Color.WHITE));
        testBox.setDrawMode(DrawMode.FILL);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                new Rotate(-10, Rotate.Y_AXIS),
                new Rotate(-30, Rotate.X_AXIS),
                new Translate(0, 0, -15));

        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(testBox);

        SubScene subScene = new SubScene(root, 900,600);
        subScene.setFill(Color.WHITE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        return group;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}