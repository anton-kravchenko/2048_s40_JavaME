package default_2048;
public class Colors {

    static int color2 = 0xeee4da;
    static int color4 = 0xede0c8;
    static int color8 = 0xf2b179;
    static int color16 = 0xf59563;
    static int color32 = 0xf67c5f;
    static int color64 = 0xf65e3b;
    static int color128 = 0xedcf72;
    static int color256 = 0xedcc61; 
    static int color512 = 0xedc850;
    static int color1024 = 0xedc53f;
    static int color2048 = 0xedcfff;
    
    static int BLACK = 0x000000;
    static int WHITE = 0xFFFFFF;
    static int MAGENTA = 0xFF00FF;
    static int GREEN = 0x00FF00;
    static int CYAN = 0x14f7ff;

    static int chooseColor(int number)
    {
        switch (number) {
        case 2: return color2;
        case 4: return color4;
        case 8: return color8;
        case 16: return color16;
        case 32: return color32;
        case 64: return color64;
        case 128: return color128;
        case 256: return color256;
        case 512: return color512;
        case 1024: return color1024;
        case 2048: return color2048;
        }
        return BLACK;
    }
}
