package software.ulpgc.imageViewer.Swing;

import software.ulpgc.imageViewer.model.Image;
import software.ulpgc.imageViewer.model.ImagePresenter;
import software.ulpgc.imageViewer.ImageIO.ImageIOImageLoader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MainFrame frame = new MainFrame();
        ImagePresenter presenter = new ImagePresenter(frame.getImageDisplay());
        presenter.show(image());
        frame.setVisible(true);
    }

    private static Image image() throws IOException {
        return new ImageIOImageLoader("src/imagenes").load();
    }
}
