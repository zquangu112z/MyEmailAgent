/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmain;

import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.bean.MailContent;

/**
 *
 * @author nguqt
 */
public class CustomCellListMail extends ListCell<MailContent> {

    private Text name;
    private Text subject;
    private VBox vbox;

    //font
    Font font20 = new Font(20);
    Font font17 = new Font(17);
    Font font15 = new Font(15);

    public CustomCellListMail() {
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
