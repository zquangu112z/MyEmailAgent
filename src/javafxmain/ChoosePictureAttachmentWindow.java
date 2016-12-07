/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmain;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author nguqt
 */
public class ChoosePictureAttachmentWindow {

    FileChooser fileChooser = new FileChooser();
    String picPath;
    Stage stage;

    public ChoosePictureAttachmentWindow(Stage stage) {
        this.stage = stage;
        configureFileChooser(fileChooser);

    }

    public String showDialog() {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return getPathFile(file);
        }
        return null;
    }

    private static void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Choose Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private String getPathFile(File file) {
        try {
            picPath = file.getAbsolutePath();
        } catch (Exception ex) {
            //
            System.out.println("----" + ex);
        }

        return picPath;
    }
}
