package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Controller {

    List<File> pathList = new ArrayList<>();

    @FXML
    public TextArea txtArea;
    @FXML
    public TextArea txtArchivePath;

    @FXML
    public void buttonChose(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файлы которые хотите скопировать");
        fileChooser.setInitialDirectory(new File("C:/Test/directory"));
        pathList = fileChooser.showOpenMultipleDialog(new Stage());
        for (File path : pathList) {
            txtArea.setText(path.getAbsolutePath() + "\n" + txtArea.getText());
        }
    }

    @FXML
    public void buttonToArchive() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите место для архива");
        directoryChooser.setInitialDirectory(new File("C:/"));
        String pathDir = directoryChooser.showDialog(new Stage()).getAbsolutePath();

        txtArchivePath.setText(pathDir);
        Path path = Paths.get(pathDir);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path.resolve("archive.zip").toString()));
        for (File archiveFile: pathList) {
            zipOutputStream.putNextEntry(new ZipEntry(archiveFile.getName()));
            Files.copy(Path.of(archiveFile.getAbsolutePath()),zipOutputStream);
        }
        zipOutputStream.close();
        txtArea.setText("");
    }
}
