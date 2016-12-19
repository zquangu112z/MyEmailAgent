/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmain;

import helper.Gmail;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 *
 * @author nguqt
 */
public class ComposeWindow extends Stage {

    Scene sceneComposeWindow;
    Label lbSubject, lbTo, lbContent;
    TextField tfSubject, tfTo;
    Text tNotification;
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

    Preferences prefSignature = Preferences.userRoot().node(MainWindow.class.getName());

//    Stage stage = this;
    public ComposeWindow() {
        sceneComposeWindow = new Scene(new VBox(), 1000, 500);

        sceneComposeWindow.getStylesheets().add(ComposeWindow.class.getResource("login.css").toExternalForm());

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

        lbSubject = new Label("Subject: ");
        gridMailInfor.add(lbSubject, 0, 0);

        tfSubject = new TextField("Testing");
        tfSubject.setPrefWidth(2000);
        gridMailInfor.add(tfSubject, 1, 0);

        lbTo = new Label("To: ");
        lbTo.setMinWidth(100);
        gridMailInfor.add(lbTo, 0, 1);

        tfTo = new TextField("zquangu112z@gmail.com");
        tfTo.setPrefWidth(2000);
        gridMailInfor.add(tfTo, 1, 1);

        lbContent = new Label("Body: ");
        gridMailInfor.add(lbContent, 0, 2);

        taContent = new TextArea("Xin chao Quang Ngu 2016: Nhiep anh gia dep trai nhat duyen hai mien Trung" + "\n" + prefSignature.get("signature", ""));
        taContent.setPrefSize(1800, 900);
        taContent.setWrapText(true);
        taContent.setFont(font17);

        tNotification = new Text("Press Send to delivery your email");
        tNotification.setFill(Color.DARKORANGE);

        //attachment
        GridPane gridMailAttach = new GridPane();
        gridMailAttach.setAlignment(Pos.BASELINE_LEFT);
        gridMailAttach.setHgap(10);
        gridMailAttach.setVgap(10);
        gridMailAttach.setPadding(new Insets(10, 10, 10, 10));

        lbAttachment = new Label("Attachment: ");
        lbAttachment.setMinWidth(100);
        gridMailAttach.add(lbAttachment, 0, 0);

        btnChooseFile = new Button("Choose File");
        btnChooseFile.setMinWidth(70);
        gridMailAttach.add(btnChooseFile, 2, 0);

        t = new Text("");
        t.setId("text-link");
        gridMailAttach.add(t, 1, 0);

        ArrayList<String> paths = new ArrayList<>();
        btnChooseFile.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ChoosePictureAttachmentWindow cpaw = new ChoosePictureAttachmentWindow(getStage());
                String newPath = new String(cpaw.showDialog());

                if (paths.contains(newPath)) {

                } else {
                    paths.add(newPath);
                }

                for (String path : paths) {
                    if (t.getText().contains(path)) {
                        continue;
                    }
                    t.setText(t.getText() + "\n" + path);

                }
            }
        });

        btnRemoveAll = new Button("Remove All");
        btnRemoveAll.setMinWidth(70);
        gridMailAttach.add(btnRemoveAll, 3, 0);

        btnRemoveAll.setOnAction((ActionEvent event) -> {
            paths.clear();
            t.setText("");
        });

        btnSend = new Button("Send");
        btnSend.setMinWidth(70);
        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String subject = tfSubject.getText();

                //xu li multi address: đầu vào phải là String []
                String[] addrs = excuteStringAddress(tfTo.getText());

                String content = taContent.getText();

                if (subject.equals("") == false && (addrs.length > 0) && content.equals("") == false) {
                    Gmail gmailSend = new Gmail();
                    disableUI();
                    Thread sendMailThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                gmailSend.sendMail("timexdanang@gmail.com", "quangu112", addrs, subject, content, paths);//TODO: remove permanent from
                            } catch (MessagingException me) {
                                tNotification.setText("Send failed: MessagingException");
                            } catch (Exception ex) {
                                System.out.println("send failed" + ex);
                            }
                            Platform.runLater(() -> {
                                enableUI();
                                tNotification.setText("Sent successful!");
                            });
                        }
                    });

                    sendMailThread.start();

                } else {
                    tNotification.setText("Khong the gui email trong");
                }
            }
        });

        btnExit = new Button("Exit");
        btnExit.setMinWidth(70);
        btnExit.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                close();
            }
        });
        gridAction = new GridPane();
        gridAction.setPadding(new Insets(10, 0, 0, 0));
        gridAction.setVgap(10);
        gridAction.setHgap(10);
        gridAction.setAlignment(Pos.CENTER);
        gridAction.add(btnSend, 0, 0);
        gridAction.add(btnExit, 1, 0);
        gridAction.add(tNotification, 0, 1, 2, 1);

        vBoxBodyEmail.getChildren().addAll(gridMailInfor, taContent, gridMailAttach, gridAction);
        VBox.setVgrow(taContent, Priority.ALWAYS);

        ((VBox) sceneComposeWindow.getRoot()).getChildren().addAll(vBoxBodyEmail);
        ((VBox) sceneComposeWindow.getRoot()).setPadding(new Insets(5, 5, 5, 5));
        setScene(sceneComposeWindow);
    }

    String[] excuteStringAddress(String text) {
        String[] addrs = text.split(",");
        for (int i = 0; i < addrs.length; i++) {
            addrs[i] = addrs[i].trim();
        }
        return addrs;
    }

    private ComposeWindow getStage() {
        return this;
    }

    void disableUI() {
        tfSubject.setDisable(true);
        tfTo.setDisable(true);
        taContent.setDisable(true);
        btnExit.setDisable(true);
        btnChooseFile.setDisable(true);
        btnRemoveAll.setDisable(true);
        tNotification.setText("Sending...");
    }

    void enableUI() {
        tfSubject.setDisable(false);
        tfTo.setDisable(false);
        taContent.setDisable(false);
        btnExit.setDisable(false);
        btnChooseFile.setDisable(false);
        btnRemoveAll.setDisable(false);
    }

    public static void main(String[] args) {
        ComposeWindow cw = new ComposeWindow();
        cw.show();
    }
}
