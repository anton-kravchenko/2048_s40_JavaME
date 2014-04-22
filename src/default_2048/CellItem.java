package default_2048;

import javax.microedition.lcdui.Graphics;

public class CellItem{
CellItem(int position, int number, int size){
    this.x = (position % 4) * size + 1;// +1 just to see greed under tile
    this.y = (position / 4) * size + 1;
    this.positionInMatrix = position;
    this.size = size - 1; // -2 just for same reason
    this.number = number;
}

public int number, x, y, size, positionInMatrix;

void drawMe(Graphics graphics){
    graphics.setColor(Colors.chooseColor(number));
    graphics.fillRect(x, y, size, size);

    graphics.setColor(Colors.BLACK);
    if(number != 0 )
    graphics.drawString((new Integer(number)).toString(), x, y, 0);
}
void clearMe(Graphics graphics){
    graphics.setColor(Colors.BLACK);
    graphics.fillRect(x, y, size, size);
    System.out.println("clear me ");
}
int getNumber(){
    return number;
}
void setNumber(int n){
    number = n;
}



}