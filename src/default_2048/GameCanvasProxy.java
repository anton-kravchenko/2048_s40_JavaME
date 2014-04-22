package default_2048;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class GameCanvasProxy extends GameCanvas {
    int canvasHeight = getHeight();
    int score = 0;
    Vector emptyTiles;
    CellItem[] itemGrid;
    Graphics graphics = getGraphics();

    final static int MV_RIGHT = 5;
    final static int MV_DOWN = 6;
    final static int MV_LEFT = 2;
    final static int MV_UP = 1;

    final static int PASTE_TO_START = 0;
    final static int PASTE_TO_END = 3;

    public void setCommandListener(CommandListener c) {
        super.setCommandListener(c);
    }
    KeyCodeAdapter keyCode = KeyCodeAdapter.getInstance();
    

    protected void keyReleased(int keyCode) {
        super.keyReleased(keyCode); //To change body of generated methods, choose Tools | Templates.
    }
    
    RandomTile randomTile;
    boolean moveSuccess = false;
    int[] initNumbers = {2, 4};

    protected GameCanvasProxy() {
        super(true);
        keyCode.setCanvas(this);
        this.emptyTiles = new Vector();
        this.randomTile = new RandomTile();
        itemGrid = new CellItem[16];
        for (int i = 0; i < 16; i++) {
            CellItem item = new CellItem(i, 0, 60);
            itemGrid[i] = item;
            emptyTiles.addElement(item);
        }

        int[] inserted = {0,0};
        
        for (int i = 0; i < 2; i++) {
            int num = randomTile.randomInt(emptyTiles.size() - 1);
            itemGrid[num].number = initNumbers[randomTile.randomInt(2)];
            inserted[i] = num;
            if(i ==1){
                if(inserted[1] > inserted[0])
                    num--;
                emptyTiles.removeElementAt(num);
            }
            if(i == 0)
            emptyTiles.removeElementAt(num);
            
        }
    }

    void drawGrid() {
        graphics.setColor(Colors.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        int width = getWidth();
        int itemSize = width / 4;

        graphics.setColor(Colors.MAGENTA);

        for (int i = 0; i <= width; i += itemSize) {
            graphics.drawLine(i, 0, i, itemSize * 4);
        }
        graphics.drawLine(width - 1, 0, width - 1, itemSize * 4);

        for (int i = 0; i < itemSize * 5; i += itemSize) {
            graphics.drawLine(0, i, width, i);
        }
        System.out.println("drawing grid");
        flushGraphics();
    }

    void drawItems() {
        for (int i = 0; i < 16; i++) {
            itemGrid[i].drawMe(getGraphics());
        }
        graphics.setColor(0x00FF00);
//        graphics.drawString("SCORE : " + Integer.toString(100500), getWidth() / 2 - 40, canvasHeight - 65, 0);
        flushGraphics();
    }

    void move(int MV) {

        Vector line = new Vector();
        for (int i = 0; i < 4; i++) {
            if (MV == MV_DOWN || MV == MV_UP) {
                line = getVertLine(i);
            } else {
                line = getHorLine(i);
            }

            cutZeros(MV, line);
            makeSum(MV, line);
        }
    }

    void makeSum(int MV, Vector line) {
        boolean makeChanges = false;
        int offset = 0;
        int step = 0;
        switch (MV) {
            case MV_RIGHT:
            case MV_DOWN:
                offset = 3;
                step = -1;
                break;
            case MV_LEFT:
            case MV_UP:
                offset = 0;
                step = 1;
                break;
        }

        for (int i = offset, k = 0; k < 3; i += step, k++) {
//    for(int i = offset, k =0; k < 3; i+=step, k++){
            CellItem current = (CellItem) line.elementAt(i);
            CellItem next = (CellItem) line.elementAt(i + step);
            if ((next.number == current.number) && (current.number != 0)) {
                next.number *= 2;
                score += next.number;
                current.number = 0;////increment i
//            emptyTiles.addElement(current);
//            System.err.println("add " + current.positionInMatrix);
                makeChanges = true;
                i += step;
                k++;
            }
        }
        if (makeChanges) {
            cutZeros(MV, line);
        }
    }

    Vector getHorLine(int n) {
        Vector line = new Vector();
        for (int i = n * 4; i < n * 4 + 4; i++) {
            line.addElement(itemGrid[i]);
        }
        return line;
    }

    Vector getVertLine(int n) {
        Vector line = new Vector();
        for (int i = n; i <= n + 12; i += 4) {
            line.addElement(itemGrid[i]);
        }
        return line;
    }

    void cutZeros(int MV, Vector line) {
        Vector noZeros = new Vector();
        int step = 0;
        int offset = 0;
        switch (MV) {
            case MV_RIGHT:
            case MV_DOWN:
                offset = 3;
                step = -1;
                break;
            case MV_LEFT:
            case MV_UP:
                offset = 0;
                step = 1;
                break;
        }

        for (int i = 0; i < 4; i++) {
            CellItem item = (CellItem) line.elementAt(i);
            if (item.number != 0) {
                CellItem noZ = new CellItem(item.positionInMatrix, item.number, 0); // just for save our number
                noZeros.addElement(noZ);
            }
        }
        for (int i = 0; i < 4; i++) {
            CellItem item = (CellItem) line.elementAt(i);
            item.number = 0;
        }
        int size = noZeros.size();

        int start = 0;
        if (MV == MV_DOWN || MV == MV_RIGHT) {
            start = 4 - noZeros.size();
        }
        for (int i = start, k = 0; k < size; i++, k++) {
            CellItem oldItem = (CellItem) line.elementAt(i);
            CellItem newItem = (CellItem) noZeros.elementAt(k);
            oldItem.number = newItem.number;
            if (oldItem.positionInMatrix != newItem.positionInMatrix) {
                moveSuccess = true;
            }
        }

    }

    protected void keyPressed(int key) {
//        graphics.drawString("SCORE : " + Integer.toString(100500), 10, canvasHeight - 65, 0);
        System.out.println("key listener ");
        moveSuccess = false;
        int keyStates = keyCode.adoptKeyCode(key);
        
        if (keyStates == 201) {
            move(MV_UP);
        } else if (keyStates == 203) {
            move(MV_DOWN);
        }

//        move(c.getCommandType());
        emptyTiles.removeAllElements();
        for (int i = 0; i < 16; i++) {
            if (itemGrid[i].number == 0) {
                emptyTiles.addElement(itemGrid[i]);
            }
        }
        if (!emptyTiles.isEmpty() && moveSuccess) {
            int rand = randomTile.randomInt(emptyTiles.size());
            itemGrid[((CellItem) emptyTiles.elementAt(rand)).positionInMatrix].number = initNumbers[randomTile.randomInt(2)];
        }

        drawItems();
    }

    public Graphics getGraphics() {
        return super.getGraphics();
    }

    public void makeMagic(Command c) {
        System.out.println("inside command listener");
        moveSuccess = false;
        int keyStates = getKeyStates();
        if ((keyStates & LEFT_PRESSED) != 0) {
            move(MV_LEFT);
        } else if ((keyStates & RIGHT_PRESSED) != 0) {
            move(MV_RIGHT);
        } else if ((keyStates & UP_PRESSED) != 0) {
            move(MV_UP);
        } else if ((keyStates & DOWN_PRESSED) != 0) {
            move(MV_DOWN);
        }

        move(c.getCommandType());

        emptyTiles.removeAllElements();
        for (int i = 0; i < 16; i++) {
            if (itemGrid[i].number == 0) {
                emptyTiles.addElement(itemGrid[i]);
            }
        }
        if (!emptyTiles.isEmpty() && moveSuccess) {
            int rand = randomTile.randomInt(emptyTiles.size());
            itemGrid[((CellItem) emptyTiles.elementAt(rand)).positionInMatrix].number = initNumbers[randomTile.randomInt(2)];
        }

        drawItems();
        //need to paste new elements here
    }
}
