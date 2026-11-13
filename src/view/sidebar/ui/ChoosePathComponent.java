package view.sidebar.ui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ChoosePathComponent extends SidebarComponent {
    public ChoosePathComponent(String name, PathSelectedHandler pathSelectedHandler){
        this(name, Path.of(System.getProperty("user.home")), pathSelectedHandler);
    }

    public ChoosePathComponent(String name, Path defaultPath, PathSelectedHandler pathSelectedHandler) {
        super(name);
        JFileChooser fileChooser = getFileChooser(defaultPath);

        String text = Files.isDirectory(defaultPath) ? "Choose file" : defaultPath.getFileName().toString();
        JButton chooserButton = new JButton(text);

        chooserButton.addActionListener((_) -> {
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                var selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.isDirectory()) {
                    System.out.println("File chosen: " + fileChooser.getSelectedFile().toString());
                    pathSelectedHandler.select(selectedFile.toPath());
                }
            }
        });

        init(chooserButton);
    }

    private JFileChooser getFileChooser(Path defaultPath) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(defaultPath.toFile());
        fileChooser.setFileFilter(new FileFilter() {
            private final String[] fileEndings = {
                    ".jpg",
                    ".jpeg",
                    ".bmp",
                    ".png"
            };

            @Override
            public boolean accept(File f) {
                if(f==null)
                    return false;

                String name = f.getName();
                return f.isDirectory() || Arrays.stream(fileEndings).anyMatch(name::endsWith);
            }

            @Override
            public String getDescription() {
                return "Image formats";
            }
        });
        return fileChooser;
    }

    public interface PathSelectedHandler{
        void select(Path path);
    }
}
