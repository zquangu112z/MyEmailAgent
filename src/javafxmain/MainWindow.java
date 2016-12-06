package javafxmain;

import helper.Gmail;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
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
import javafx.util.Callback;
import model.bean.MailContent;
import model.bo.CheckLoginBO;

/**
 *
 * @author nguqt
 */
public class MainWindow extends Application {

    //declare comonents
    GridPane gridLogin, gridGontentPanel;//gridListBox,gridListEmail,gridQuickToolTrip
    Text textLoginTitle, textActiontargetLogin, tBoxSubject, tBoxFrom, tBoxDate;
    Text textBoxName;
    Label lbUserNameLogin, lbPasswordLogin;
    TextField tfUsernameLogin;
    TextArea taContent;
    PasswordField pfPasswordLogin;
    Button btnLogin, btnCompose, btnReply, btnForward;
    HBox hbLogin, hbCompose, hbReply, hbForward;
    CheckBox cbRememberLogin;
    Scene scene;
    Stage primaryStage;

    //font
    Font font20 = new Font(20);
    Font font17 = new Font(17);
    Font font15 = new Font(15);
    String userName, passName;

    //logged in? if yes: switch to inbox interface
    boolean loggedIn;

    ListView<String> listBox;
    ObservableList<String> itemsBox;

    //TODO: xu li neu da o trang thai login roi thi phai connect lai store
    //Du lieu
    Gmail gmailHelper;
    //ArrayList<MailContent> mailContents = new ArrayList<>();
    ObservableList<MailContent> itemsEmail = FXCollections.observableArrayList();

    //Lay thong tin tu Preferences, neu ton tai thi hien thi
    Preferences pref = Preferences.userRoot().node(this.getClass().getName());

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        //Login
        loggedIn = checkLoggedIn();
        if (loggedIn == false) {//vao giao dien loggin
            GUILogin();
        } else {//vao thang giao dien inbox
            GUIInbox();
        }
    }

    /**
     * Giao dien man hinh Inbox
     */
    void GUIInbox() {

        scene = new Scene(new VBox(), 1000, 700);
        primaryStage.setTitle("Inbox");
        Image imgIcon = new Image(this.getClass().getResource("imgIcon.png").toString(), 20, 20, true, false);
        primaryStage.getIcons().add(imgIcon);

        initUI_Menubar();
        initUI_ContentPanel();
        initUI_BodyMail();
        primaryStage.setScene(scene);
        scene.getStylesheets().add(MainWindow.class.getResource("login.css").toExternalForm());
        primaryStage.show();
    }

    void initUI_ContentPanel() {
        gridGontentPanel = new GridPane();
        gridGontentPanel.setHgap(2);
        gridGontentPanel.setVgap(2);
        gridGontentPanel.setPadding(new Insets(0, 5, 5, 5));

        gmailHelper = new Gmail();

        itemsEmail = FXCollections.observableArrayList();//reset. if not: login session after a logout session will retain reloaded message
        Thread getInbox = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<MailContent> mailContents = gmailHelper.getInboxMails();
                for (MailContent mc : mailContents) {
                    Platform.runLater(() -> itemsEmail.add(mc));
                }
                Platform.runLater(() -> {
                    MailContent firstMail = mailContents.get(0);
                    tBoxFrom.setText("From: " + firstMail.getFrom());
                    tBoxSubject.setText("Subject: " + firstMail.getSubject());
                    tBoxDate.setText("Date: " + firstMail.getTime());
                    taContent.setText(firstMail.getBody());
                });
            }
        });

        getInbox.start();

        //-------tool trip
        initUI_ToolTrip();

        //-------separate
        gridGontentPanel.add(new Separator(), 0, 1, 3, 1);

        //--------panel list box
        initUI_ListBox();

        //---------panel list email
        initUI_ListEmail();

        ((VBox) scene.getRoot()).getChildren().addAll(gridGontentPanel);
    }

    /**
     * tool trip: new, reply,forward
     */
    void initUI_ToolTrip() {
        HBox hBoxQuickToolTrip = new HBox(10);
        hBoxQuickToolTrip.setPadding(new Insets(5, 5, 5, 5));
        hBoxQuickToolTrip.setPrefWidth(70);

        btnCompose = new Button("New");
        btnCompose.setMinWidth(70);

        btnReply = new Button("Reply");
        btnReply.setMinWidth(70);

        btnForward = new Button("Forward");
        btnForward.setMinWidth(70);

        hBoxQuickToolTrip.getChildren().addAll(btnCompose, btnReply, btnForward);

        gridGontentPanel.add(hBoxQuickToolTrip, 0, 0, 3, 1);
    }

    /**
     * panel list box
     */
    void initUI_ListBox() {
        //--------panel list box
        VBox vBoxListBox = new VBox();

        listBox = new ListView<>();
        itemsBox = FXCollections.observableArrayList(
                "Inbox", "Draft", "Important", "Sent", "Spam", "All");

        listBox.setItems(itemsBox);

        listBox.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                        textBoxName.setText(new_val);
                    }
                }
        );

        //6 images
        int imgScaleListBox = 30;
        Image image = new Image(this.getClass().getResource("imgInbox.png").toString(), imgScaleListBox, imgScaleListBox, true, false);
        Image image2 = new Image(this.getClass().getResource("imgDraft.png").toString(), imgScaleListBox, imgScaleListBox, true, false);
        Image image3 = new Image(this.getClass().getResource("imgImportant.png").toString(), imgScaleListBox, imgScaleListBox, true, false);
        Image image4 = new Image(this.getClass().getResource("imgSent.png").toString(), imgScaleListBox, imgScaleListBox, true, false);
        Image image5 = new Image(this.getClass().getResource("imgSpam.png").toString(), imgScaleListBox, imgScaleListBox, true, false);
        Image image6 = new Image(this.getClass().getResource("imgAll.png").toString(), imgScaleListBox, imgScaleListBox, true, false);

        Image[] images = new Image[]{image, image2, image3, image4, image5, image6};
        listBox.setCellFactory(itemsBox -> new ListCell<String>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {

                    //Image image = new Image(this.getClass().getResource("imgCompose.png").toString(), 20, 20, true, false);
                    switch (name) {
                        case "Inbox":
                            imageView.setImage(images[0]);
                            break;
                        case "Draft":
                            imageView.setImage(images[1]);
                            break;
                        case "Important":
                            imageView.setImage(images[2]);
                            break;
                        case "Sent":
                            imageView.setImage(images[3]);
                            break;
                        case "Spam":
                            imageView.setImage(images[4]);
                            break;
                        case "All":
                            imageView.setImage(images[5]);
                            break;
                    }
                    setFont(new Font(20));
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });

        VBox.setVgrow(listBox, Priority.ALWAYS);

        vBoxListBox.getChildren().addAll(listBox);
        vBoxListBox.setMinWidth(150);
        gridGontentPanel.add(vBoxListBox, 0, 2);
    }

    /**
     * panel list email
     */
    void initUI_ListEmail() {
        VBox vBoxListEmail = new VBox();
        textBoxName = new Text("Inbox");

        textBoxName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        textBoxName.setFill(Color.ORANGERED);

        //init arr list
//        mailContents.add(new MailContent("messageSubject", "messageFrom", "username", "now", "getTextFromMessage", 0));
//        mailContents.add(new MailContent("messageSubject2", "messageFrom2", "username2", "now2", "getTextFromMessage2", 0));
//        mailContents.add(new MailContent("messageSubject3", "messageFrom3", "username3", "now3", "getTextFromMessage3", 0));
        //local 
        ListView<MailContent> listEmail = new ListView<MailContent>();

//        ObservableList<MailContent> itemsEmail = FXCollections.observableArrayList();
//        for (MailContent mc : mailContents) {
//            itemsEmail.add(mc);
//        }
        listEmail.setItems(itemsEmail);

        listEmail.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<MailContent>() {
                    @Override
                    public void changed(ObservableValue<? extends MailContent> observable, MailContent oldValue, MailContent newValue) {
                        tBoxFrom.setText("From: " + newValue.getFrom());
                        tBoxSubject.setText("Subject: " + newValue.getSubject());
                        tBoxDate.setText("Date: " + newValue.getTime());
                        taContent.setText(newValue.getBody());
                    }
                }
        );

        //with CustomCell
        listEmail.setCellFactory(new Callback<ListView<MailContent>, ListCell<MailContent>>() {
            @Override
            public ListCell<MailContent> call(ListView<MailContent> list) {
                return new CustomCell();
            }
        });

        vBoxListEmail.getChildren().addAll(textBoxName, listEmail);
        VBox.setVgrow(listEmail, Priority.ALWAYS);
        vBoxListEmail.setMinWidth(250);

        gridGontentPanel.add(vBoxListEmail, 1, 2);
    }

    class CustomCell extends ListCell<MailContent> {

        private Text name;
        private Text subject;
        private VBox vbox;

        public CustomCell() {
            super();

            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //do something                  
                }
            });

            name = new Text();
            name.setFont(font20);

            subject = new Text();
            subject.setFont(font15);
            subject.setFill(Color.DARKGRAY);
            vbox = new VBox();
            vbox.getChildren().addAll(name, subject);

            setText(null);
        }

        @Override
        public void updateItem(MailContent item, boolean empty) {
            super.updateItem(item, empty);
            setEditable(false);
            if (item != null) {
                name.setText(item.getFrom().split(" ")[0]);
                subject.setText(item.getSubject());
                setGraphic(vbox);
            } else {
                setGraphic(null);
            }
        }
    }

    void initUI_BodyMail() {
        VBox vBoxBodyEmail = new VBox();

        tBoxSubject = new Text("Subject: ");
        tBoxSubject.setFont(font20);
        tBoxFrom = new Text("From: ");
        tBoxFrom.setFont(font20);
        tBoxDate = new Text("Date: ");
        tBoxDate.setFont(font20);
        taContent = new TextArea();
        taContent.setPrefSize(1800, 900);
        taContent.setWrapText(true);
        taContent.setFont(font17);
        vBoxBodyEmail.getChildren().addAll(tBoxSubject, tBoxFrom, tBoxDate, taContent);
        VBox.setVgrow(taContent, Priority.ALWAYS);
        gridGontentPanel.add(vBoxBodyEmail, 2, 2);
    }

    //initialze UI : menubar
    /**
     * menu bar
     */
    void initUI_Menubar() {
        //Menu Bar
        MenuBar menuBar = new MenuBar();
        // --- Menu File
        Menu mFile = new Menu("File");
        MenuItem miCompose = new MenuItem("New Email", new ImageView(new Image(this.getClass().getResource("imgCompose.png").toString(), 20, 20, true, false)));
        miCompose.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        MenuItem miLoggout = new MenuItem("Loggout");
        miLoggout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                doLoggout(t);
            }
        });
        MenuItem miExit = new MenuItem("Exit");
        miExit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
        miExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        mFile.getItems().addAll(miCompose, miLoggout, new SeparatorMenuItem(), miExit);
        // --- Menu About
        Menu menuAbout = new Menu("About");
        MenuItem miSignature = new MenuItem("Signature");
        MenuItem miAbout = new MenuItem("About");
        menuAbout.getItems().addAll(miSignature, miAbout);
        menuBar.getMenus().addAll(mFile, menuAbout);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
    }

    /**
     * Login Screen
     */
    void GUILogin() {
        gridLogin = new GridPane();
        gridLogin.setAlignment(Pos.CENTER);
        gridLogin.setHgap(10);
        gridLogin.setVgap(10);
        gridLogin.setPadding(new Insets(25, 25, 25, 25));

        textLoginTitle = new Text("My Mail Agent");
        textLoginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        textLoginTitle.setTextAlignment(TextAlignment.CENTER);
        textLoginTitle.setFill(Color.ORANGE);
        gridLogin.add(textLoginTitle, 0, 0, 2, 1);

        lbUserNameLogin = new Label("User Name:");
        gridLogin.add(lbUserNameLogin, 0, 1);

        tfUsernameLogin = new TextField();
        gridLogin.add(tfUsernameLogin, 1, 1);

        lbPasswordLogin = new Label("Password:");
        gridLogin.add(lbPasswordLogin, 0, 2);

        pfPasswordLogin = new PasswordField();
        gridLogin.add(pfPasswordLogin, 1, 2);

        btnLogin = new Button("Sign in");
        hbLogin = new HBox(10);
        hbLogin.setAlignment(Pos.BOTTOM_CENTER);
        hbLogin.getChildren().add(btnLogin);
        gridLogin.add(hbLogin, 0, 4, 2, 1);

        cbRememberLogin = new CheckBox();
        cbRememberLogin.setText("Keep me logged in");
        cbRememberLogin.setSelected(true);
        gridLogin.add(cbRememberLogin, 0, 5, 2, 1);

        cbRememberLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (cbRememberLogin.isSelected()) {
                    System.out.println("selected");
                } else {
                    System.out.println("unselected");
                }
            }
        });

        textActiontargetLogin = new Text();
        gridLogin.add(textActiontargetLogin, 1, 6);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                textActiontargetLogin.setFill(Color.FIREBRICK);
                textActiontargetLogin.setText("Checking...");
                btnLogin.setDisable(true);
                doLogin(e);
                //dbtnLogin.setDisable(false);
            }
        });

        //Lay thong tin tu Preferences, neu ton tai thi hien thi
        getlogginInfor();

        scene = new Scene(gridLogin, 300, 275);

        primaryStage.setTitle("LoginPage");
        primaryStage.setScene(scene);
        scene
                .getStylesheets().add(MainWindow.class
                        .getResource("login.css").toExternalForm());

        primaryStage.show();
    }

    /**
     * check if user logged in
     *
     * @return
     */
    boolean checkLoggedIn() {
        return pref.getBoolean("loggedIn", false);
    }

    /**
     * Get previous session infor
     *
     * @return
     */
    String[] getlogginInfor() {
        userName = pref.get("user", "");
        if (!userName.equals("")) {
            tfUsernameLogin.setText(userName);
        }
        passName = pref.get("pass", "");
        if (!passName.equals("")) {
            //TODO: remove below line (seCurity)
            pfPasswordLogin.setText(passName);
        }
        return new String[]{userName, passName};
    }

    /**
     * Do: thay doi trang thai login thanh false, tro ve man hinh Login
     *
     * @param event
     */
    void doLoggout(ActionEvent event) {
        pref.putBoolean("loggedIn", false);
        GUILogin();
        //System.exit(0);
    }

    /**
     * Do: Luu thong tin, Chuyen sang man hinh Inbox
     *
     * @param event
     */
    public void doLogin(ActionEvent event) {
        System.out.println("ashdfashdkf");
        Thread loginThread = new Thread(new Runnable() {

            @Override
            public void run() {

                boolean loginOK = new CheckLoginBO().checkLoginGmail(tfUsernameLogin.getText(), pfPasswordLogin.getText());
                if (loginOK == false) {
                    System.out.println("ashdfashdkf_if");
                    textActiontargetLogin.setText("Khong the dang nhap!");
                    btnLogin.setDisable(false);
                } else {
                    System.out.println("ashdfashdkf-else");
                    //dua thong tin vao Preferences
                    if (cbRememberLogin.isSelected()) {
                        pref.put("user", tfUsernameLogin.getText());
                        pref.put("pass", pfPasswordLogin.getText());
                        pref.putBoolean("loggedIn", true);
                    }

                    //switch window
                    Platform.runLater(() -> GUIInbox());
                }
            }
        });
        loginThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
