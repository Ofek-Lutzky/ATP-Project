package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {

    private Maze maze;
    private int characterRow;
    private int characterCol;


    private boolean solutionShow = false;
    private Solution sol;

    public MazeDisplayer()
    {
//        widthProperty().addListener(evt -> draw());
//        heightProperty().addListener(evt -> draw());
    }


    public int getCharacterRow() {
        return characterRow;
    }

    public int getCharacterCol() {
        return characterCol;
    }


//    StringProperty imageFileNameWall = new SimpleStringProperty();
//    StringProperty imageFileNamePlayer = new SimpleStringProperty();
//
//    public String getImageFileNameWall() {
//        return imageFileNameWall.get();
//    }
//
//    public void setImageFileNameWall(String imageFileNameWall) {
//        this.imageFileNameWall.set(imageFileNameWall);
//    }
//
//    public String getImageFileNamePlayer() {
//        return imageFileNamePlayer.get();
//    }
//
//    public void setImageFileNamePlayer(String imageFileNamePlayer) {
//        this.imageFileNamePlayer.set(imageFileNamePlayer);
//    }



    public void drawMaze(Maze maze)
    {
        this.maze = maze;
        draw();
    }

    public void draw()
    {
        if( maze!=null)
        {
            int canvasHeight = getHeight();
            int canvasWidth = getWidth();
            int row = maze.getMap().length;
            int col = maze.getMap()[0].length;
            int cellHeight = canvasHeight/row;
            int cellWidth = canvasWidth/col;
            Graphics graphicsContext = getGraphics();
            graphicsContext.clearRect(0,0,canvasWidth,canvasHeight);
            graphicsContext.setColor(Color.RED);
            int w,h;
            //Draw Maze
            Image wallImage = null;
//            try {
//                //wallImage = new Image(new FileInputStream(getImageFileNameWall()));
//            } catch (FileNotFoundException e) {
//                System.out.println("There is no file....");
//            }
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    if(this.maze.getMap()[i][j] == 1) // Wall
                    {
                        h = i * cellHeight;
                        w = j * cellWidth;
//                        if (wallImage == null){
                            graphicsContext.fillRect((int) w,(int)h,(int)cellWidth,(int)cellHeight);
//                        }else{
//                            graphicsContext.drawImage((int)w,(int)h,(int)cellWidth,(int)cellHeight);
//                        }
                    }

                }
            }

            int h_player = getCharacterRow() * cellHeight;
            int w_player = getCharacterCol() * cellWidth;
            Image playerImage = null;
//            try {
//                playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
//            } catch (FileNotFoundException e) {
//                System.out.println("There is no Image player....");
//            }
//            graphicsContext.drawImage(playerImage,(int)w_player,(int)h_player,cellWidth,cellHeight);

        }
    }

    public void setPlayerPosition(int row, int col) {
        this.characterRow = row;
        this.characterCol = col;
        draw();
    }
    public void setSolution(Solution solution) {
        this.sol = solution;
        solutionShow = true;
        draw();
    }
    public void setShowSolution(boolean b) {
        solutionShow = b;
        draw();
    }
}
