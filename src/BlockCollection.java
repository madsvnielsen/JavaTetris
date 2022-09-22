import java.awt.*;

public class BlockCollection {
    Block[] blocks;
    int x;
    int y;

    int id = 0;

    Color color;

    BlockCollection(Block[] smallBlocks, int posX, int posY, Color collectionColor, int n)
    {
        blocks = smallBlocks;
        x = posX;
        y = posY;
        color = collectionColor;
        id = n;
    }

}
