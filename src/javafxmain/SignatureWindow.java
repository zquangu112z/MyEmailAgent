/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmain;

import java.util.prefs.Preferences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author nguqt
 */
public class SignatureWindow extends Stage {

    Scene sceneSignatureWindow;
    Label lbSubject, lbTo, lbContent;
    TextField tfSubject, tfTo;
    Text tNotification;
    Button btnSend, btnExit, btnSave;
    GridPane gridAction;

    //font
    Font font20 = new Font(20);
    Font font17 = new Font(17);
    Font font15 = new Font(15);

    Text tSubject, tTo;
    TextArea taSignature;
    private Label lbAttachment;
    private Text t;
    private Button btnRemoveAll;

    Preferences prefSignature = Preferences.userRoot().node(MainWindow.class.getName());

//    Stage stage = this;
    public SignatureWindow() {

        setTitle("Signature");
        Image imgIcon = new Image(this.getClass().getResource("imgIcon.png").toString(), 20, 20, true, false);
        getIcons().add(imgIcon);

        initUI_SignaturePanel();
    }

    void initUI_SignaturePanel() {
        VBox vBoxBodyEmail = new VBox();

        taSignature = new TextArea("Xin chao Quang Ngu 2016: Nhiep anh gia dep trai nhat duyen hai mien Trung");
        taSignature.setPrefSize(1800, 900);
        taSignature.setWrapText(true);
        taSignature.setFont(font17);

        String signature = prefSignature.get("signature", "");
        taSignature.setText(signature);

        vBoxBodyEmail.getChildren().addAll(taSignature);

        HBox hbSave = new HBox();
        btnSave = new Button("Save");
        hbSave.setPadding(new Insets(10, 10, 10, 10));
        btnSave.setAlignment(Pos.CENTER);
        btnSave.setMinWidth(70);
        hbSave.setAlignment(Pos.BOTTOM_CENTER);

        hbSave.getChildren().add(btnSave);
        vBoxBodyEmail.getChildren().addAll(hbSave);

        btnSave.setOnAction((ActionEvent event) -> {
            prefSignature.put("signature", taSignature.getText());
            btnSave.setDisable(true);
        });

        //mo lai button Save
        taSignature.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            btnSave.setDisable(false);
        });

        VBox.setVgrow(taSignature, Priority.ALWAYS);

        sceneSignatureWindow = new Scene(vBoxBodyEmail, 403, 403);
        setScene(sceneSignatureWindow);
        sceneSignatureWindow.getStylesheets().add(AboutWindow.class.getResource("login.css").toExternalForm());

    }

    private SignatureWindow getStage() {
        return this;
    }

    void disableUI() {
        tfSubject.setDisable(true);
        tfTo.setDisable(true);
        taSignature.setDisable(true);
        btnExit.setDisable(true);
        btnSave.setDisable(true);
        btnRemoveAll.setDisable(true);
        tNotification.setText("Sending...");
    }

    void enableUI() {
        tfSubject.setDisable(false);
        tfTo.setDisable(false);
        taSignature.setDisable(false);
        btnExit.setDisable(false);
        btnSave.setDisable(false);
        btnRemoveAll.setDisable(false);
    }

    public static void main(String[] args) {
        SignatureWindow cw = new SignatureWindow();
        cw.show();
    }
}
