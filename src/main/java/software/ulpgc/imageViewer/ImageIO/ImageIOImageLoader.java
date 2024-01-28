package software.ulpgc.imageViewer.ImageIO;

import software.ulpgc.imageViewer.model.Image;
import software.ulpgc.imageViewer.model.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageIOImageLoader implements ImageLoader {
    private final Map<String,BufferedImage> imagesLoaded;

    public ImageIOImageLoader(String path) throws IOException {
        this.imagesLoaded = loadImagesFromPath(path);
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    private Image imageAt(int i) {
        return new Image() {
            @Override
            public String id() {
                return getIdFromImage(getImageEntry(i));
            }

            public byte[] bytes() {
                return bufferedImageToByteArray(getImage(getImageEntry(i)));
            }

            @Override
            public Image next() {
                return imageAt((i + 1) % imagesLoaded.size());
            }

            @Override
            public Image prev() {
                return imageAt(i > 0 ? i -1 : imagesLoaded.size()-1);
            }
        };
    }

    private Map<String, BufferedImage> loadImagesFromPath(String path) throws IOException {
        Map<String, BufferedImage> images = new HashMap<>();
        File[] files = getFilesFromPath(path);
        for (File file : files) {
            BufferedImage image = loadImage(file);
            images.put(file.getName(), image);
        }
        return images;
    }

    private File[] getFilesFromPath(String path){
        return new File(path).listFiles();
    }
    private BufferedImage loadImage(File file) throws IOException {
        return ImageIO.read(file);
    }

    public Map.Entry<String, BufferedImage> getImageEntry(int i) {
        int count = 0;
        for (Map.Entry<String, BufferedImage> entry : imagesLoaded.entrySet()) {
            if (count == i) {
                return entry;
            }
            count++;
        }
        return nullMapEntry();
    }

    public String getIdFromImage(Map.Entry<String, BufferedImage> entry){
        return entry.getKey();
    }

    public BufferedImage getImage(Map.Entry<String, BufferedImage> entry){
        return entry.getValue();
    }

    private Map.Entry<String, BufferedImage> nullMapEntry() {
        return new Map.Entry<>() {
            @Override
            public String getKey() {
                return "null";
            }

            @Override
            public BufferedImage getValue() {
                return new BufferedImage(0,0, BufferedImage.TYPE_INT_RGB);
            }

            @Override
            public BufferedImage setValue(BufferedImage value) {
                return new BufferedImage(0,0, BufferedImage.TYPE_INT_RGB);
            }
        };
    }

    public byte[] bufferedImageToByteArray(BufferedImage image) {
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e ){
            return new byte[0];
        }
    }
}
