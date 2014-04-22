package default_2048;

import java.util.Random;

public class RandomTile{
    Random r;
    public RandomTile() {
        r = new Random();
    }

    public int randomInt(int n){
        return r.nextInt(n);
    }
}