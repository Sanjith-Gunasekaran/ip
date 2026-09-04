package heisenberg;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/** Represents an avatar and message loaded from {@code DialogBox.fxml}. */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image image) {
        URL dialogBoxResource = Objects.requireNonNull(
                MainWindow.class.getResource("/view/DialogBox.fxml"),
                "Missing FXML resource: /view/DialogBox.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(dialogBoxResource);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load the dialog box view.", e);
        }

        dialog.setText(text);
        displayPicture.setImage(image);
    }

    /** Creates a dialog aligned for a message entered by the user. */
    public static DialogBox getUserDialog(String text, Image image) {
        return new DialogBox(text, image);
    }

    /** Creates a dialog aligned for a response from Heisenberg. */
    public static DialogBox getHeisenbergDialog(String text, Image image) {
        DialogBox dialogBox = new DialogBox(text, image);
        dialogBox.flip();
        dialogBox.dialog.setStyle(
                "-fx-background-color: #eeeeee; -fx-background-radius: 8; -fx-padding: 8;");
        return dialogBox;
    }

    /** Places the avatar on the left to distinguish Heisenberg's responses. */
    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
    }
}
