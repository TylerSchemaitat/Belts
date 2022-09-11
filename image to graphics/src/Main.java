import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.util.*;
import java.util.List;

//Made by Tyler Schemaitat 9/2/22
//Constructs fractal snowflake by creating an array of angles
//limited by resolution, if I could shade pixels instead of drawing lines it might look more realistic
public class Main extends JPanel {
    static final int up = 0;
    static final int right = 1;
    static final int down = 2;
    static final int left = 3;
    int x = 0;
    static Sprite[][] beltMapArray;
    static Sprite[] belts;
    static Item[] items;
    public static boolean w = false;
    public static boolean a = false;
    public static boolean s = false;
    public static boolean d = false;
    static int cameraX = 0;
    static int cameraY = 0;
    public static void main(String[] args) throws InterruptedException {
        setupGameObjects();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Main());
        frame.setSize(800, 800);
        frame.setLocation(200, 10);
        frame.setVisible(true);

        setupListeners(frame);

        gameLoop(frame);
    }

    private static void gameLoop(JFrame frame) throws InterruptedException {
        int ticksPerSecond = 20;
        //int microTicksPerTick = 2;
        int timePerTickInMs = 1000/ticksPerSecond;
        int tick = 0;
        int beltTickCounter = 0;
        //int microTick = 0;
        int beltTick = 0;
        while(1==1) {
            if(a) cameraX--;
            if(d) cameraX++;
            if(w) cameraY--;
            if(s) cameraY++;

            Thread.sleep(timePerTickInMs);
            if(true){
                moveItems(tick);


            }
            frame.repaint();
            tick++;
        }
    }


    public static void moveItems(int beltTick){
        //System.out.println(items[0].positionOnBelt + " shape: " + items[0].belt.shape + "orient: " + items[0].belt.orientation);
        //System.out.println("called move items");
        //length is 1 for now
        int position = 0;
        int[] cords;
        int shape;
        if(beltTick % 8 == 0)
            for(int i = 0; i < 1; i++){
                if(items[0].moving == true){
                    shape = items[0].belt.shape;
                    position = items[0].positionOnBelt;
                    if(shape == Sprite.straight){
                        if(position == 1 || position == 3){
                            items[0].positionOnBelt++;
                        }
                        else{
                            items[0].belt = items[0].belt.beltsAround[0 + items[0].belt.orientation];
                            items[0].positionOnBelt--;
                        }
                    }else if(shape == Sprite.curveToRight){
                        if(position == 1 || position == 2){
                            items[0].positionOnBelt++;
                        }
                        else{
                            items[0].belt = items[0].belt.beltsAround[0 + items[0].belt.orientation];
                            if(items[0].belt.shape == Sprite.straight){
                                if(items[0].positionOnBelt == 3)
                                    items[0].positionOnBelt = 1;
                                else
                                    items[0].positionOnBelt = 3;
                            }else
                            if(items[0].belt.shape == Sprite.curveToRight){
                                if(items[0].positionOnBelt == 3)
                                    items[0].positionOnBelt = 1;
                                else
                                    items[0].positionOnBelt = 4;
                            }
                            else{
                                if(items[0].positionOnBelt == 3)
                                    items[0].positionOnBelt = 1;
                                else
                                    items[0].positionOnBelt = 2;
                            }
                        }
                    }else {
                        if(position == 2 || position == 3){
                            items[0].positionOnBelt++;
                        }
                        else{
                            items[0].belt = items[0].belt.beltsAround[0 + items[0].belt.orientation];
                            if(items[0].belt.shape == Sprite.straight){
                                if(items[0].positionOnBelt == 1)
                                    items[0].positionOnBelt = 1;
                                else
                                    items[0].positionOnBelt = 3;
                            }else
                            if(items[0].belt.shape == Sprite.curveToRight){
                                if(items[0].positionOnBelt == 1)
                                    items[0].positionOnBelt = 1;
                                else
                                    items[0].positionOnBelt = 4;
                            }
                            else{
                                if(items[0].positionOnBelt == 1)
                                    items[0].positionOnBelt = 1;
                                else
                                    items[0].positionOnBelt = 2;
                            }


                        }
                    }
                }
            }
        for(int i = 0; i < 1; i++){
            items[0].belt.getNewItemLocation(beltTick % 8, items[0].positionOnBelt, items[0].cord);
            items[0].moving = true;
        }
    }

    private static void setupGameObjects(){
        //setup game lines
        Sprite.loadSprites();
        Item.loadOres();
        createBelts();



        //adds item onto belt
        items = new Item[10];
        items[0] = new Item(Item.iron, 0, 0);
        items[0].belt = belts[9];
        items[0].positionOnBelt = 1;
        items[0].moving = false;
        //System.out.println("shape lol " + items[0].belt.shape);

        //end setup
    }

    private static void setupListeners(JFrame frame){
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //System.out.println("Key typed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
                switch(e.getKeyChar()){
                    case 'w': w = true;
                        break;
                    case 'a': a = true;
                        break;
                    case 's': s = true;
                        break;
                    case 'd': d = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Key released code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
                switch(e.getKeyChar()){
                    case 'w': w = false;
                        break;
                    case 'a': a = false;
                        break;
                    case 's': s = false;
                        break;
                    case 'd': d = false;
                        break;
                }
            }
        });
    }


    public static void draw(Graphics grf){

        for(int i = 0; i < belts.length; i++){
            //System.out.println("Drawing image " + i + " at " + belts.get(i).x + ", " + belts.get(i).y);
            //if(belts.get(i).image == null)
            //System.out.println("Image " + i + " is null");
            grf.drawImage(belts[i].image, belts[i].x - cameraX,belts[i].y - cameraY,null);
        }

        grf.drawImage(items[0].ironImage, items[0].cord[0] - cameraX,items[0].cord[1] - cameraY,null);
        //grf.drawImage(items[0].ironImage, 0,0,null);
    }
    public static void createBelts(){
        int width = 9;
        int height = 7;
        int[][] beltMap = {
                {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1, 1, 1, 2,-1,-1,-1,-1},
                {-1,-1, 0, 1, 1, 2,-1,-1,-1},
                {-1, 1, 0, 0,-1, 2,-1,-1,-1},
                {-1, 0, 1, 2, 3, 3,-1,-1,-1},
                {-1, 0, 3, 3,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1}
        };
        belts = new Sprite[1000];
        List<Sprite> beltList = new ArrayList<Sprite>();
        beltMapArray = new Sprite[height][width];
        int index = 0;
        for(int i = 1; i < height - 1; i++){
            for(int j = 1; j < width - 1; j++){
                if(beltMap[i][j] != -1){
                    beltMapArray[i][j] = new Sprite(beltMap[i][j], new int[]{beltMap[i - 1][j], beltMap[i][j + 1], beltMap[i + 1][j], beltMap[i][j - 1]}, 64 * j + 200, 64 * i + 200);
                    beltList.add(beltMapArray[i][j]);
                    index++;
                }
            }
        }
        belts = beltList.toArray(new Sprite[beltList.size()]);

        for(int i = 1; i < height - 1; i++){
            for(int j = 1; j < width - 1; j++){
                if(beltMapArray[i][j] != null){
                    if(beltMapArray[i+1][j] != null){
                        beltMapArray[i][j].beltsAround[2] = beltMapArray[i+1][j];
                    }
                    if(beltMapArray[i][j+1] != null){
                        beltMapArray[i][j].beltsAround[1] = beltMapArray[i][j+1];
                    }
                    if(beltMapArray[i-1][j] != null){
                        beltMapArray[i][j].beltsAround[0] = beltMapArray[i-1][j];
                    }
                    if(beltMapArray[i][j-1] != null){
                        beltMapArray[i][j].beltsAround[3] = beltMapArray[i][j-1];
                    }
                }
            }
        }




    }

    protected void paintComponent(Graphics grf) {
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D) grf;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        draw(grf);
        //System.out.println("Done");
    }



    public BufferedImage makeImage() {
        int size = 32;
        int[][] color = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                color[i][j] = Color.red.getRGB();
            }
        }

        BufferedImage image = new BufferedImage(color.length, color[0].length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                image.setRGB(x, y, color[x][y]);
            }
        }
        return image;
    }





}