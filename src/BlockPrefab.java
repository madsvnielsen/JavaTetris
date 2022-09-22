import java.awt.*;

public class BlockPrefab {

    BlockCollection[] BlockCollections = new BlockCollection[]
    {
            new BlockCollection(new Block[]{ //Block 1
                    new Block(0,0),
                    new Block(0,-1),
                    new Block(-1,0),
                    new Block(-1, 1),
            }, 0, 0,
                    Color.getHSBColor(
                            0.45f,
                            0.76f,
                            1f
                    ), 1
            ),
            new BlockCollection(new Block[]{ //Block 2
                    new Block(0,0),
                    new Block(-1,0),
                    new Block(-1,-1),
                    new Block(0, 1),
            }, 0, 0,
                    Color.getHSBColor(
                            0.05f,
                            0.76f,
                            1f
                    ), 2
            ),
            new BlockCollection(new Block[]{ //Block 3
                    new Block(0,0),
                    new Block(-1,0),
                    new Block(1,0),
                    new Block(-1, -1),
            }, 0, 0,
                    Color.getHSBColor(
                            0.15f,
                            0.76f,
                            1f
                    ), 3
            ),
            new BlockCollection(new Block[]{ //Block 4
                    new Block(0,0),
                    new Block(-1,0),
                    new Block(1,0),
                    new Block(1, -1),
            }, 0, 0,
                    Color.getHSBColor(
                            0.3f,
                            0.57f,
                            0.9f
                    ), 4
            ),
            new BlockCollection(new Block[]{ //Block 5
                    new Block(0,0),
                    new Block(-1,0),
                    new Block(-1,-1),
                    new Block(0, -1),
            }, 0, 0,
                    Color.getHSBColor(
                            0.75f,
                            0.57f,
                            0.89f
                    ), 5
            ),
            new BlockCollection(new Block[]{ //Block 6
                    new Block(0,0),
                    new Block(-1,0),
                    new Block(1,0),
                    new Block(0, -1),
            }, 0, 0,
                    Color.getHSBColor(
                            0.94f,
                            1f,
                            1f
                    ), 6
            ),
            new BlockCollection(new Block[]{ //Block 7
                    new Block(0,0),
                    new Block(-1,0),
                    new Block(1,0),
                    new Block(-2, 0),
            }, 0, 0,
                    Color.getHSBColor(
                            0.44f,
                            1f,
                            1f
                    ), 7
            )

    };

}
