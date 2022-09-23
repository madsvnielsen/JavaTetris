import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TetrisGame  extends JPanel implements ActionListener {

        private final int B_WIDTH = 500;
        private final int B_HEIGHT = 600;

    private final int START_DELAY = 1000;
        private int DELAY = START_DELAY;
        private Timer timer;

        private Color BackgroundGridColor = Color.getHSBColor(0.78f, 1f, 0.03f);
        private Color BackroundSquareColor = Color.getHSBColor(0.78f, 1f, 0.06f);

        private Color GhostColor = Color.getHSBColor(0.78f, 0f, 0.24f);

        private int BlockSize = 25;
        private int spawnPosition = 25;

        private int GridLineSize = 1;

        private BlockCollection activeBlock;

        private BlockCollection ghostBlock;

        private BlockCollection[] staticBlocks;

        private int GameScore = 0;
        private int LinesSent = 0;

        private int TimeInterval = 0;
        private final int SpeedUpInterval = 10000;
    private final int SpeedUpIncrement = 100;

        private boolean GamePlaying = false;
        private boolean GameOver = false;



        private int bottomPosition = B_HEIGHT/BlockSize;

    public TetrisGame() {
            initBoard();
        }

        private void initBoard() {

            addKeyListener(new TAdapter());
            setBackground(BackgroundGridColor);
            setFocusable(true);

            setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
            initGame();
        }
        private void initGame() {

            timer = new Timer(DELAY, this);
            timer.start();




        }

        private void SpawnBlock()
        {
            if(staticBlocks != null)
            {
                for(int i = 0; i < staticBlocks.length; i++)
                {
                    for(int n = 0; n < staticBlocks[i].blocks.length; n++)
                    {
                        int staticX = staticBlocks[i].blocks[n].x * BlockSize + staticBlocks[i].x;
                        int staticY = staticBlocks[i].blocks[n].y * BlockSize + staticBlocks[i].y;
                        for(int x = 0; x < B_WIDTH/BlockSize; x++ )
                        {
                            for(int y = -2; y < 3; y++ )
                            {
                                int globalX = x * BlockSize;
                                int globalY = spawnPosition + y * BlockSize;
                                if(staticX == globalX && staticY == globalY)
                                {
                                    GameOver = true;
                                    GamePlaying = false;
                                }

                            }

                        }

                    }
                }
            }

            int lastId = -1;
            if(staticBlocks != null)
            {
                lastId = staticBlocks[staticBlocks.length-1].id;
            }
            do
            {
                activeBlock = new BlockPrefab().BlockCollections[(int)Math.round((Math.random() * 6f))];


            } while (activeBlock.id == lastId);

            activeBlock.x = B_WIDTH/2;
            activeBlock.y = spawnPosition;

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            doDrawing(g);
        }

        private void doDrawing(Graphics g) {

            DrawBackGrid(g);
            if(!GameOver)
            {
                DrawGhostBlock(g);
            }
            DrawActiveBlock(g);
            DrawStaticBlocks(g);
            if(GamePlaying)
            {
                g.setColor(Color.white);
                g.setFont(g.getFont().deriveFont(15f));
                g.drawString("Score:" + GameScore, 10,20);
                g.drawString("Lines Sent:" + LinesSent, 10,40);

            }
            DrawPauseScreens(g);


            Toolkit.getDefaultToolkit().sync();


        }

    private void DrawPauseScreens(Graphics g) {
        if(!GamePlaying)
        {
            if(!GameOver)
            {
                g.setColor(Color.white);
                g.setFont(g.getFont().deriveFont(35f));
                g.drawString("Welcome to Tetris!", B_WIDTH/2 - (150),100);
                g.setFont(g.getFont().deriveFont(20f));
                g.drawString("Press 'enter' to start playing", B_WIDTH/2 - (125),200);
            } else
            {
                g.setColor(Color.red);
                g.setFont(g.getFont().deriveFont(35f));
                g.drawString("Game over!", B_WIDTH/2 - (100),100);
                g.setColor(Color.white);
                g.setFont(g.getFont().deriveFont(25f));
                g.drawString("Score: " + GameScore, B_WIDTH/2 - (50),150);
                g.setFont(g.getFont().deriveFont(20f));
                g.setColor(Color.white);
                g.drawString("Press 'enter' to restart!", B_WIDTH/2 - (110),200);

            }
        }
    }

    private void DrawGhostBlock(Graphics g) {
        if(ghostBlock != null)
        {
            g.setColor(ghostBlock.color);
            for(int i = 0; i < ghostBlock.blocks.length; i++)
            {
                g.fillRect(ghostBlock.x + ghostBlock.blocks[i].x * BlockSize + GridLineSize,
                        ghostBlock.y + ghostBlock.blocks[i].y * BlockSize + GridLineSize, BlockSize-2*GridLineSize, BlockSize-2*GridLineSize);
            }
        }
    }

    private void DrawStaticBlocks(Graphics g) {
        if(staticBlocks != null)
        {
            for(int i = 0; i < staticBlocks.length; i++)
            {
                Color co = staticBlocks[i].color;
                g.setColor(co);
                for(int n = 0; n < staticBlocks[i].blocks.length; n++)
                {
                    g.fillRect(staticBlocks[i].x + staticBlocks[i].blocks[n].x * BlockSize + GridLineSize,
                            staticBlocks[i].y + staticBlocks[i].blocks[n].y * BlockSize + GridLineSize, BlockSize - 2*GridLineSize, BlockSize - 2*GridLineSize);
                }
            }

        }
    }

    private void DrawActiveBlock(Graphics g) {
        if(activeBlock != null)
        {
            Color c = activeBlock.color;
            g.setColor(c);
            for(int i = 0; i < activeBlock.blocks.length; i++)
            {
                g.fillRect(activeBlock.x + activeBlock.blocks[i].x * BlockSize + GridLineSize,
                        activeBlock.y + activeBlock.blocks[i].y * BlockSize + GridLineSize, BlockSize-2*GridLineSize, BlockSize-2*GridLineSize);
            }

        }

    }

    private void DrawBackGrid(Graphics g) {
        g.setColor(BackroundSquareColor);
        for(int x = 0; x < B_WIDTH/BlockSize; x++)
        {
            for(int y = 0; y < B_HEIGHT/BlockSize; y++)
            {
                g.fillRect(x*BlockSize + GridLineSize, y*BlockSize + GridLineSize, BlockSize-2*GridLineSize, BlockSize-2*GridLineSize);

            }
        }
    }

    private void SendLines(int[] yLines)
    {
        LinesSent += yLines.length;
        int ScoreAdd = 0;

        for(int i = 0; i < yLines.length; i++)
        {
            ScoreAdd += 100 * (i+1);
        }
        GameScore += ScoreAdd;



        if(staticBlocks != null)
        {
            for(int y = 0; y < yLines.length; y++)
            {
                int globalY = yLines[y]* BlockSize;
                for(int i = 0; i < staticBlocks.length; i++)
                {
                    int[] BlocksToRemove = new int[0];

                    for(int n = 0; n < staticBlocks[i].blocks.length; n++)
                    {

                        int staticY = staticBlocks[i].blocks[n].y * BlockSize + staticBlocks[i].y;
                        if(staticY == globalY)
                        {
                            if(BlocksToRemove == null)
                            {
                                BlocksToRemove = new int[]{n};
                            }
                            else
                            {
                                int[] temp = new int[BlocksToRemove.length +1];
                                for(int a = 0; a < BlocksToRemove.length; a++)
                                {
                                    temp[a] = BlocksToRemove[a];
                                }
                                temp[temp.length -1] = n;
                                BlocksToRemove = temp;

                            }

                        }

                    }

                    if(BlocksToRemove != null)
                    {
                        Block[] newBlocks = new Block[0];


                        for(int a = 0; a < staticBlocks[i].blocks.length; a++)
                        {
                            boolean remainingBlock = true;
                            for (int b = 0; b < BlocksToRemove.length; b++)
                            {
                                if(a==BlocksToRemove[b])
                                {
                                    remainingBlock = false;
                                }
                            }
                            if(remainingBlock)
                            {
                                if(newBlocks == null)
                                {
                                    newBlocks = new Block[]{staticBlocks[i].blocks[a]};
                                }
                                else
                                {
                                    Block[] tempBlocks = new Block[newBlocks.length +1];
                                    for(int c = 0; c < newBlocks.length; c++)
                                    {
                                        tempBlocks[c] = newBlocks[c];
                                    }
                                    tempBlocks[tempBlocks.length-1] = staticBlocks[i].blocks[a];
                                    newBlocks = tempBlocks;
                                }
                            }

                        }
                        staticBlocks[i].blocks = newBlocks;
                    }
                    if(staticBlocks[i].y/BlockSize == yLines[y]-1)
                    {
                        for(int a = 0; a < staticBlocks[i].blocks.length; a++)
                        {
                            if(staticBlocks[i].blocks[a].y == 2)
                            {
                                staticBlocks[i].blocks[a].y = 1;

                            }

                        }
                        staticBlocks[i].y += BlockSize;

                    }
                    else if(staticBlocks[i].y/BlockSize == yLines[y]+1)
                    {
                        for(int a = 0; a < staticBlocks[i].blocks.length; a++)
                        {
                            if(staticBlocks[i].blocks[a].y == -2)
                            {
                                staticBlocks[i].blocks[a].y = -1;

                            }

                        }

                    }
                    else if(staticBlocks[i].y/BlockSize <= yLines[y])
                    {
                        if(staticBlocks[i].y/BlockSize == yLines[y])
                        {
                            for(int a = 0; a < staticBlocks[i].blocks.length; a++)
                            {
                                if(staticBlocks[i].blocks[a].y == 1)
                                {
                                    staticBlocks[i].blocks[a].y = 0;

                                }

                            }
                        }

                        staticBlocks[i].y += BlockSize;
                    }

                }

            }

        }

    }
    private void CheckLines()
    {


        if(staticBlocks != null)
        {
            int[] CompletedLines = new int[0];

            for(int y = 0; y < B_HEIGHT/BlockSize; y++)
            {
                boolean LineComplete = true;

                for(int x = 0; x < B_WIDTH/BlockSize; x++)
                {
                    int globalY = y* BlockSize;
                    int globalX = x * BlockSize;
                    boolean positionValid = false;
                    for(int i = 0; i < staticBlocks.length; i++)
                    {
                        for(int n = 0; n < staticBlocks[i].blocks.length; n++)
                        {
                            int staticY = staticBlocks[i].blocks[n].y * BlockSize + staticBlocks[i].y;
                            int staticX = staticBlocks[i].blocks[n].x * BlockSize + staticBlocks[i].x;
                            if(staticX == globalX && staticY == globalY)
                            {
                                positionValid = true;
                            }
                        }

                    }
                    if(!positionValid)
                    {
                        LineComplete = false;
                    }
                }
                if(LineComplete)
                {
                    if(CompletedLines == null)
                    {
                        CompletedLines = new int[]{y};

                    }
                    else
                    {
                        int[] oldLines = CompletedLines;
                        CompletedLines = new int[CompletedLines.length +1];
                        for(int i = 0; i < oldLines.length; i++)
                        {
                            CompletedLines[i] = oldLines[i];
                        }
                        CompletedLines[CompletedLines.length-1] = y;
                    }
                }

            }
            if(CompletedLines != null)
            {
                SendLines(CompletedLines);
            }


        }

    }

    private void MoveBlocks()
        {
            activeBlock.y += BlockSize;
        }

        private void RotateBlocks()
        {
            for(int i = 0; i < activeBlock.blocks.length; i++)
            {
                int x = activeBlock.blocks[i].x;
                int y = activeBlock.blocks[i].y;
                int newX = y * -1;
                int newY = x;
                activeBlock.blocks[i].x = newX;
                activeBlock.blocks[i].y = newY;
            }
        }

        private void CheckStacking()
        {
            int highestY = 0;
            for(int i = 0; i < activeBlock.blocks.length; i++)
            {
                if(activeBlock.blocks[i].y > highestY)
                {
                    highestY = activeBlock.blocks[i].y;
                }

            }
            boolean collision = false;
            if(staticBlocks != null)
            {
                for(int i = 0; i < activeBlock.blocks.length; i++)
                {
                    int x = activeBlock.blocks[i].x * BlockSize + activeBlock.x;
                    int y = activeBlock.blocks[i].y *BlockSize + BlockSize + activeBlock.y;
                    for(int n = 0; n < staticBlocks.length; n++)
                    {
                        for(int g = 0; g < staticBlocks[n].blocks.length; g++)
                        {
                            int xs = staticBlocks[n].blocks[g].x * BlockSize + staticBlocks[n].x;
                            int ys = staticBlocks[n].blocks[g].y * BlockSize + staticBlocks[n].y;


                            if(xs == x && ys == y)
                            {
                                collision = true;
                                break;
                            }

                        }
                    }
                }

            }


            if((activeBlock.y + highestY * BlockSize)/BlockSize >= bottomPosition -1 || collision)
            {
                if(staticBlocks != null)
                {
                    BlockCollection[] newCollection = new BlockCollection[staticBlocks.length +1];
                    for(int i = 0; i < staticBlocks.length; i++)
                    {
                        newCollection[i] = staticBlocks[i];
                    }
                    newCollection[staticBlocks.length] = activeBlock;
                    staticBlocks = newCollection;
                }
                else
                {
                    staticBlocks = new BlockCollection[1];
                    staticBlocks[0] = activeBlock;
                }



                SpawnBlock();
                CheckLines();


            }
        }

        private void CalculateGhost()
        {
            if(activeBlock != null)
            {
                if(ghostBlock == null)
                {
                    ghostBlock = new BlockCollection(
                            activeBlock.blocks,
                            activeBlock.x,
                            activeBlock.y,
                            GhostColor,
                            activeBlock.id
                    );
                }

                ghostBlock.x = activeBlock.x;
                ghostBlock.blocks = activeBlock.blocks;
                ghostBlock.y = B_HEIGHT - BlockSize;
                boolean clipping = false;

                do
                {
                    clipping = false;
                    for(int i = 0; i < ghostBlock.blocks.length; i++)
                    {
                        int globalY = ghostBlock.blocks[i].y * BlockSize + ghostBlock.y;
                        int globalX = ghostBlock.blocks[i].x * BlockSize + ghostBlock.x;
                        if(globalY > B_HEIGHT-BlockSize)
                        {
                            clipping = true;
                        }
                        if(staticBlocks != null)
                        {
                            for(int n = 0; n < staticBlocks.length; n++)
                            {
                                for(int g = 0; g < staticBlocks[n].blocks.length; g++)
                                {
                                    int staticY = staticBlocks[n].blocks[g].y * BlockSize + staticBlocks[n].y;
                                    int staticX = staticBlocks[n].blocks[g].x * BlockSize + staticBlocks[n].x;

                                    if(staticY == globalY && staticX == globalX)
                                    {
                                        clipping = true;
                                    }
                                }
                            }

                        }

                    }
                    if(clipping)
                    {
                        ghostBlock.y -=BlockSize;
                    }

                }while (clipping);

            }

        }

        @Override
        public void actionPerformed(ActionEvent e) {
        if(GamePlaying)
        {
            TimeInterval += DELAY;
            if(TimeInterval > SpeedUpInterval)
            {
                TimeInterval = 0;
                DELAY -= SpeedUpIncrement;
                timer.setDelay(DELAY);
            }
            CheckStacking();
            MoveBlocks();
            CalculateGhost();
            repaint();
        }

        }

        private class TAdapter extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();
                if(GamePlaying)
                {
                    if ((key == KeyEvent.VK_LEFT)) {
                        int mostLeft = 0;
                        for(int i = 0; i < activeBlock.blocks.length; i++)
                        {
                            if(activeBlock.blocks[i].x < mostLeft)
                            {
                                mostLeft = activeBlock.blocks[i].x;
                            }
                        }
                        if((mostLeft * BlockSize + activeBlock.x)/BlockSize > 0)
                        {
                            activeBlock.x -= BlockSize;

                        }


                    }
                    if ((key == KeyEvent.VK_RIGHT)) {
                        int mostRight = 0;
                        for(int i = 0; i < activeBlock.blocks.length; i++)
                        {
                            if(activeBlock.blocks[i].x > mostRight)
                            {
                                mostRight = activeBlock.blocks[i].x;
                            }
                        }
                        if((mostRight * BlockSize + activeBlock.x)/BlockSize < (B_WIDTH/BlockSize)-1)
                        {
                            activeBlock.x += BlockSize;

                        }

                    }
                    if ((key == KeyEvent.VK_UP)) {
                        RotateBlocks();

                    }
                    if(key == KeyEvent.VK_SPACE)
                    {
                        BlockCollection currentBlocks = activeBlock;
                        while(currentBlocks == activeBlock)
                        {

                            CheckStacking();
                            MoveBlocks();
                        }



                    }
                    if(key == KeyEvent.VK_DOWN)
                    {
                        activeBlock.y += BlockSize;
                        CheckStacking();
                        repaint();



                    }
                    CalculateGhost();
                    repaint();

                }
                if(key == KeyEvent.VK_ENTER && !GamePlaying)
                {
                    if(GameOver)
                    {
                        activeBlock = null;
                        staticBlocks = null;
                        ghostBlock = null;

                    }
                    GameOver = false;
                    GamePlaying = true;
                    GameScore = 0;
                    LinesSent = 0;
                    DELAY = START_DELAY;
                    if(activeBlock == null)
                    {
                        SpawnBlock();

                    }




                }
                if(key == KeyEvent.VK_P && GamePlaying)
                {
                    GamePlaying = false;



                }



            }
        }
}
