/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmain;

import helper.Gmail;
import helper.SendEmail;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author nguqt
 */
public class ComposeWindow extends Stage {

    Scene sceneComposeWindow;

    //font
    Font font20 = new Font(20);
    Font font17 = new Font(17);
    Font font15 = new Font(15);

    Text tSubject, tTo;
    TextArea taContent;

    public ComposeWindow() {
        //super();//TODO remove
        sceneComposeWindow = new Scene(new VBox(), 1000, 700);

        setTitle("Compose");
        Image imgIcon = new Image(this.getClass().getResource("imgIcon.png").toString(), 20, 20, true, false);
        getIcons().add(imgIcon);

        initUI_ComposePanel();
    }

    void initUI_ComposePanel() {
        VBox vBoxBodyEmail = new VBox();
        
        GridPane gridMailInfor = new GridPane();
        gridMailInfor.setAlignment(Pos.BASELINE_LEFT);
        gridMailInfor.setHgap(10);
        gridMailInfor.setVgap(10);
        gridMailInfor.setPadding(new Insets(10, 10, 10, 10));

        Label lbSubject = new Label("Subject: ");
        gridMailInfor.add(lbSubject, 0, 0);

        TextField tfSubject = new TextField("Testing");
        tfSubject.setPrefWidth(2000);
        gridMailInfor.add(tfSubject, 1, 0);

        Label lbTo = new Label("To: ");
        lbTo.setMinWidth(100);
        gridMailInfor.add(lbTo, 0, 1);

        TextField tfTo = new TextField("zquangu112z@gmail.com");
        tfTo.setPrefWidth(2000);
        gridMailInfor.add(tfTo, 1, 1);

        Label lbContent = new Label("Body: ");
        gridMailInfor.add(lbContent, 0, 2);

        taContent = new TextArea("xin chao Quang Ngu 2016");
        taContent.setPrefSize(1800, 900);
        taContent.setWrapText(true);
        taContent.setFont(font17);

        Text notification = new Text("Press Send to delivery your email");
        notification.setFill(Color.DARKORANGE);

        Button btnSend = new Button("Send");
        btnSend.setMinWidth(70);
        btnSend.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String subject = tfSubject.getText();
                String to = tfTo.getText();
                String content = taContent.getText();

                if (subject.equals("") == false && to.equals("") == false && content.equals("") == false) {
                    Gmail gmailSend = new Gmail();
                    gmailSend.sendMail("timexdanang@gmail.com", "quangu112", to, subject, content);//TODO: remove permanent from
                } else {
                    notification.setText("Khong the gui email trong");
                }
            }
        });

        Button btnCancel = new Button("Cancel");
        btnCancel.setMinWidth(70);

        GridPane gridAction = new GridPane();
        gridAction.setPadding(new Insets(10, 0, 0, 0));
        gridAction.setVgap(10);
        gridAction.setHgap(10);
        gridAction.setAlignment(Pos.CENTER);
        gridAction.add(btnSend, 0, 0);
        gridAction.add(btnCancel, 1, 0);
        gridAction.add(notification, 0, 1, 2, 1);

        vBoxBodyEmail.getChildren().addAll(gridMailInfor, taContent, gridAction);
        VBox.setVgrow(taContent, Priority.ALWAYS);

        ((VBox) sceneComposeWindow.getRoot()).getChildren().addAll(vBoxBodyEmail);
        ((VBox) sceneComposeWindow.getRoot()).setPadding(new Insets(5, 5, 5, 5));
        setScene(sceneComposeWindow);
    }

    public static void main(String[] args) {
        ComposeWindow cw = new ComposeWindow();
        cw.show();
    }
}
