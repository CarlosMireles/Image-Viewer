package software.ulpgc.imageViewer.model;

public interface ImageDisplay {
    void paint(String id, int offset, byte[] bytes);
    int getWidth();
    void clear();
    void on(Shift shift);
    void on(Released released);

    interface Shift {
        Shift Null = offset -> {};
        void offset(int offset);
    }
    interface Released {
        Released Null = offset -> {};
        void offset(int offset);
    }
}
