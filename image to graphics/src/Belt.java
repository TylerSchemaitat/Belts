import First.Images;

import java.awt.image.BufferedImage;

import static First.Enum.*;
import static First.Images.*;

public class Belt {

    public Belt(int orientation, int[] oAround, int x, int y) {

    }

    public static int checkBeltType(int orientation, int[] oAround, int[] inputs) {
        int shape = 0;

        boolean backBelt = false;
        boolean leftBelt = false;
        boolean rightBelt = false;
        BufferedImage image;

        if(oAround[(down + orientation)%4] == (up + orientation)%4){
            backBelt = true;
            if(oAround[(left + orientation)%4] == (right + orientation)%4)
                leftBelt = true;
            if(oAround[(right + orientation)%4] == (left + orientation)%4)
                rightBelt = true;
        }
        else if(oAround[(left + orientation)%4] == (right + orientation)%4 && oAround[(right + orientation)%4] == (left + orientation)%4) {
            leftBelt = rightBelt = true;
            shape = straight;
        }
        else if(oAround[(left + orientation)%4] == (right + orientation)%4){
            backBelt = true;
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
            backBelt = true;
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
        }
        if(shape == straight){
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
        }
        return shape;
    }
}
