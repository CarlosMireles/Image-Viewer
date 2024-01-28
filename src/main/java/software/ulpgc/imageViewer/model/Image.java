package software.ulpgc.imageViewer.model;

public interface Image {
    String id();
    byte[] bytes();
    Image next();
    Image prev();

}
