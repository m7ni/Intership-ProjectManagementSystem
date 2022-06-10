package pt.isec.pa.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.pa.model.Facade;

public class BlockUI extends Stage {
    Facade facade;
    private final int HEIGHT = 30;

    public BlockUI(Window parent, Facade facade) {
        this.facade = facade;
        createViews();
        initOwner(parent);

        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    private void createViews() {
        VBox vBox = new VBox();

        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);

        VBox verticalBox = new VBox();
        verticalBox.setSpacing(20);
        verticalBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Do you want to block this Phase?");
        title.setAlignment(Pos.CENTER);

        HBox btns = new HBox();
        btns.setSpacing(15);
        btns.setAlignment(Pos.CENTER);

        Button btnYes = new Button("Yes");
        btnYes.setPrefHeight(HEIGHT);
        btnYes.setBackground(new Background(new BackgroundFill(Color.rgb(204, 153, 255), new CornerRadii(3), Insets.EMPTY)));
        btnYes.setStyle("-fx-text-fill: #ffffff");
        btnYes.setOnAction(e -> {
            facade.next(false);
            this.close();
        });

        Button btnNo = new Button("No");
        btnNo.setPrefHeight(HEIGHT);
        btnNo.setOnAction(e -> {
            facade.next(true);
            this.close();
        });

        btns.getChildren().addAll(btnNo,btnYes);

        verticalBox.getChildren().addAll(title);
        hBox.getChildren().addAll(verticalBox);

        vBox.getChildren().addAll(hBox,btns);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));

        Scene scene = new Scene(vBox, 200,100);
        this.setTitle("Block");
        this.setScene(scene);
        setResizable(false);

    }

}
