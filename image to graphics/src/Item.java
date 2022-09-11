import java.awt.image.BufferedImage;

public class Item {
    public static final int iron = 0;
    public int[] cord;
    public Sprite belt;
    public int positionOnBelt;
    public boolean moving;

    static BufferedImage ironImage;
    public Item(int name){
        cord = new int[2];
    }
    public Item(int name, int x, int y){
        cord = new int[2];
    }

    public static void loadOres(){
        BufferedImage temp = Sprite.downloadImage("png_images/iron.png");
        ironImage = Sprite.scaleImageBy2(temp);
    }
}
