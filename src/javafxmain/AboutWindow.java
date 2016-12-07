/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author nguqt
 */
public class AboutWindow extends Stage {

    Scene sceneComposeWindow;
    Label lbSubject, lbAuthor, lbAbout;
    TextField tfSubject, tfTo;
    Text tAbout;
    Button btnSend, btnExit, btnChooseFile;
    GridPane gridAction;

    //font
    Font font20 = new Font(20);
    Font font17 = new Font(17);
    Font font15 = new Font(15);

    Text tSubject, tTo;
    TextArea taContent;
    private Label lbAttachment;
    private Text t;
    private Button btnRemoveAll;
    private Label lbName;

    public AboutWindow() {

        setTitle("About");
        Image imgIcon = new Image(this.getClass().getResource("imgIcon.png").toString(), 20, 20, true, false);
        getIcons().add(imgIcon);

        initUI_AboutPanel();
    }

    void initUI_AboutPanel() {

        GridPane gridAbout = new GridPane();
        gridAbout.setAlignment(Pos.CENTER);
        gridAbout.setHgap(10);
        gridAbout.setVgap(10);
        gridAbout.setPadding(new Insets(10, 10, 10, 10));

        lbName = new Label("Name: Gmail Agent");
        gridAbout.add(lbName, 0, 0);
        lbName.setFont(font20);

        lbAuthor = new Label("Author: @nqtrg on Instagram");
        gridAbout.add(lbAuthor, 0, 1);
        lbAuthor.setFont(font20);
        tAbout = new Text("NOV 11, 2016" + "\n"
                + "\"Do an Lap Trinh Mang thay Nguyen\"" + "\n"
                + "Email: <nguqtruong@gmail.com>");
        tAbout.setFill(Color.DARKORANGE);
        tAbout.setFont(font20);
        gridAbout.add(tAbout, 0, 2);

        //VBox.setVgrow(gridAbout, Priority.ALWAYS);
        sceneComposeWindow = new Scene(gridAbout, 403, 403);
        setScene(sceneComposeWindow);
        sceneComposeWindow.getStylesheets().add(AboutWindow.class.getResource("login.css").toExternalForm());
    }

    public static void main(String[] args) {
        AboutWindow cw = new AboutWindow();
        cw.show();
    }
}
