import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    static final int straight = 0;
    static final int curveToLeft = 1;
    static final int curveToRight = 2;
    static final int up = 0;
    static final int right = 1;
    static final int down = 2;
    static final int left = 3;
    static int i = 0;
    static BufferedImage beltUpImage;
    static BufferedImage beltDownImage;
    static BufferedImage beltRightImage;
    static BufferedImage beltLeftImage;

    static BufferedImage beltDownToRightImage;
    static BufferedImage beltLeftToDownImage;
    static BufferedImage beltUpToLeftImage;
    static BufferedImage beltRightToUpImage;

    static BufferedImage beltDownToLeftImage;
    static BufferedImage beltLeftToUpImage;
    static BufferedImage beltUpToRightImage;
    static BufferedImage beltRightToDownImage;
    int shape;
    int orientation;
    int x;
    int y;
    BufferedImage image;
    Item[] items;
    Item[] itemsLeft;
    Item[] itemsRight;
    Sprite[] beltsAround;
    //straight item coords
    static int[] straightItemX = {0, 16, 16, 48, 48};
    static int[][] straightItemsY = { {},
            {48, 44, 40, 36, 32, 28, 24, 20},
            {16, 12, 8, 4, 0, -4, -8, -12},
            {32, 28, 24, 20, 16, 12, 8, 4},
            {0, -4, -8, -12, -16, -20, -24, -28},
};

    static int[][] curveRightItemsY = {{},
            {32, 30, 39, 39, 38, 38, 37, 37},
            {36, 35, 35, 34, 33, 32, 31, 30},
            {28, 26, 24, 22, 19, 17, 15, 12},
            {24, 22, 20, 18, 16, 14, 12, 10}
    };
    static int[][] curveLeftItemsX = {{},
            {8, 8, 7, 7, 7, 7, 6, 6, 6},
            {24, 22, 20, 18, 16, 14, 12, 10},
            {8, 4, 0, -4, -8, -12, -16, -20, -24},
            {24, 22, 20, 18, 16, 14, 12, 10}
    };
    static int[][] curveLeftItemsY = {{},
            {8, 4, 0, -4, -8, -12, -16, -20, -24},
            {24, 22, 20, 18, 16, 14, 12, 10},
            {8, 4, 0, -4, -8, -12, -16, -20, -24},
            {24, 22, 20, 18, 16, 14, 12, 10}
    };

    public static void calcBeltCord(){
        
    }


    public Sprite(int orientation, int[] oAround, int x, int y){
        this.orientation = orientation;
        this.x = x;
        this.y = y;
        beltsAround = new Sprite[4];




        if((oAround[(left + orientation)%4] == (right + orientation)%4 && oAround[(right + orientation)%4] == (left + orientation)%4) ||
                (oAround[(down + orientation)%4] == (up + orientation)%4)
        ){
            shape = straight;
            //System.out.println("In switch 1");
            switch(orientation){
                case 0: image = beltUpImage; break;
                case 1: image = beltRightImage; break;
                case 2: image = beltDownImage; break;
                case 3: image = beltLeftImage; break;
            }
        }



        else if(oAround[(left + orientation)%4] == (right + orientation)%4){
            shape = curveToLeft;
            //System.out.println("In switch 2");
            switch(orientation){
                case 0: image = beltLeftToUpImage; break;
                case 1: image = beltUpToRightImage; break;
                case 2: image = beltRightToDownImage; break;
                case 3: image = beltDownToLeftImage; break;
            }
        }
        else if(oAround[(right + orientation)%4] == (left + orientation)%4){
            shape = curveToRight;
            //System.out.println("In switch 3" + orientation +" "+ oAround[2]);
            switch(orientation){
                case 0: image = beltRightToUpImage; break;
                case 1: image = beltDownToRightImage; break;
                case 2: image = beltLeftToDownImage; break;
                case 3: image = beltUpToLeftImage; break;
            }
        }
        else {
            shape = straight;
            //System.out.println("In switch 4" + orientation +" "+ oAround[2]);
            switch(orientation){
                case up: image = beltUpImage;
                break;
                case right: image = beltRightImage;
                break;
                case down: image = beltDownImage;
                break;
                case left: image = beltLeftImage;
                break;
            }
            //if(image == null)
                //System.out.println("confused o is: " + orientation);

            switch(shape){
                case straight:
                    itemsLeft = new Item[2];
                    itemsRight = new Item[2];
                    break;
                case curveToRight:
                    itemsLeft = new Item[3];
                    itemsRight = new Item[1];
                    break;
                case curveToLeft:
                    itemsLeft = new Item[1];
                    itemsRight = new Item[3];
                    break;
            }
            items = new Item[4];
        }





    }

    public static void loadSprites(){
        BufferedImage temp;
        temp = downloadImage("png_images/belt.png");
        beltUpImage = scaleImageBy2(temp);
        beltRightImage = rotateBy90(beltUpImage);
        beltDownImage = rotateBy90(beltRightImage);
        beltLeftImage = rotateBy90(beltDownImage);


        temp = downloadImage("png_images/beltDownToRight.png");
        beltDownToRightImage = scaleImageBy2(temp);
        beltLeftToDownImage = rotateBy90(beltDownToRightImage);
        beltUpToLeftImage = rotateBy90(beltLeftToDownImage);
        beltRightToUpImage = rotateBy90(beltUpToLeftImage);

        temp = downloadImage("png_images/beltDownToLeft.png");
        beltDownToLeftImage = scaleImageBy2(temp);
        beltLeftToUpImage = rotateBy90(beltDownToLeftImage);
        beltUpToRightImage = rotateBy90(beltLeftToUpImage);
        beltRightToDownImage = rotateBy90(beltUpToRightImage);
    }

    int[][] rotateMatrixX = {
            {1, 0},
            {0, -1},
            {-1, 0},
            {0, 1}
    };
    int[][] rotateMatrixY = {
            {1, 0},
            {0, 1},
            {-1, 0},
            {0, -1}
    };

    int[][] jumpMatrix = {
            {0, 0},
            {1, 0},
            {1, 1},
            {0, 1}
    };

    public void getNewItemLocation(int beltTick, int position, int[]cord){
        double angle;
        int itemSize = 16;
        int size = 64;
        int diffX = 0;
        int diffY = 0;
        //x first y second
        if(shape == straight){
            diffX = straightItemX[position];
            diffY = straightItemsY[position][beltTick];
        }
        if(shape == curveToRight){
            if(position == 3){
                diffY = straightItemsY[2][beltTick];
                diffX = straightItemX[2];
            }
            else if(position == 4){
                diffY = straightItemsY[2][beltTick];
                diffX = straightItemX[4];
            }
            else{
                angle = 3.14 / 32.0 * (double)(beltTick + position * 8 - 8);
                diffY = (int)(32.0 * Math.cos(angle)) + 16;
                diffX = (int)(32.0 - 32.0 * Math.sin(angle)) + 16;
            }

        }
        int finalDiffX = rotateMatrixX[orientation][0] * diffX + rotateMatrixX[orientation][1] * diffY + jumpMatrix[orientation][0] * 64;
        int finalDiffY = rotateMatrixY[orientation][1] * diffX + rotateMatrixY[orientation][0] * diffY + jumpMatrix[orientation][1] * 64;

        cord[0] = x + finalDiffX - itemSize;
        cord[1] = y + finalDiffY - itemSize;
        System.out.println("X: " + cord[0] + ", Y: " + cord[1] + " p: " + position);

        //else System.out.println("This shouldn't happen");
        //System.out.println("Belts around me: " + beltsAround[1]);
    }
    public static BufferedImage downloadImage(String path){

        File ImageFile = new File(path);
        BufferedImage image;
        try {
            image = ImageIO.read(ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return image;
    }
    public static BufferedImage scaleImageBy2(BufferedImage image){
        final int w = image.getWidth();
        final int h = image.getHeight();
        BufferedImage scaledImage = new BufferedImage((w * 2),(h * 2), BufferedImage.TYPE_INT_ARGB);
        final AffineTransform at = AffineTransform.getScaleInstance(2.0, 2.0);
        final AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        scaledImage = ato.filter(image, scaledImage);

        //final Scale scaler = new Scale(2);
        //BufferedImage scaledImage= scaler.apply(image);
        return scaledImage;
    }

    public static BufferedImage rotateBy90(BufferedImage image){
        final double rads = Math.toRadians(90);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image,rotatedImage);
        return rotatedImage;
    }
}
