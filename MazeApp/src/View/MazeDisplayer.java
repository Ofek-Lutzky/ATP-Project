package View;


import algorithms.mazeGenerators.Maze;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


import javafx.scene.image.Image;



public class MazeDisplayer extends Canvas {

    private Maze maze;
    private int characterRow;
    private int characterCol;
    private static String characterDirection;
    private static int firstDraw = 0;


    private boolean solutionShow = false;
    private Solution sol;

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNameFloor = new SimpleStringProperty();
    StringProperty imageFileNameStart = new SimpleStringProperty();
    StringProperty imageFileNameEnd = new SimpleStringProperty();
    StringProperty imageFileNameSol = new SimpleStringProperty();
    // all the sides of the character
    StringProperty imageFileNameAshUp = new SimpleStringProperty();
    StringProperty imageFileNameAshDown = new SimpleStringProperty();
    StringProperty imageFileNameAshLeft = new SimpleStringProperty();
    StringProperty imageFileNameAshRight = new SimpleStringProperty();


    public MazeDisplayer() { }

    public int getCharacterRow() {
        return characterRow;
    }

    public int getCharacterCol() {
        return characterCol;
    }



    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameEnd() {
        return imageFileNameEnd.get();
    }

    public void setImageFileNameEnd(String imageFileNameEnd) {
        this.imageFileNameEnd.set(imageFileNameEnd);
    }

    public String getImageFileNameStart() {
        return imageFileNameStart.get();
    }

    public void setImageFileNameStart(String imageFileNameStart) {
        this.imageFileNameStart.set(imageFileNameStart);
    }

    public String getImageFileNameSol() {
        return imageFileNameSol.get();
    }

    public void setImageFileNameSol(String imageFileNameSol) {
        this.imageFileNameSol.set(imageFileNameSol);
    }

    public String getImageFileNameAshUp() {
        return imageFileNameAshUp.get();
    }

    public StringProperty imageFileNameAshUpProperty() {
        return imageFileNameAshUp;
    }

    public void setImageFileNameAshUp(String imageFileNameAshUp) {
        this.imageFileNameAshUp.set(imageFileNameAshUp);
    }

    public String getImageFileNameAshDown() {
        return imageFileNameAshDown.get();
    }

    public StringProperty imageFileNameAshDownProperty() {
        return imageFileNameAshDown;
    }

    public void setImageFileNameAshDown(String imageFileNameAshDown) {
        this.imageFileNameAshDown.set(imageFileNameAshDown);
    }

    public String getImageFileNameAshLeft() {
        return imageFileNameAshLeft.get();
    }

    public StringProperty imageFileNameAshLeftProperty() {
        return imageFileNameAshLeft;
    }

    public void setImageFileNameAshLeft(String imageFileNameAshLeft) {
        this.imageFileNameAshLeft.set(imageFileNameAshLeft);
    }

    public String getImageFileNameAshRight() {
        return imageFileNameAshRight.get();
    }

    public StringProperty imageFileNameAshRightProperty() {
        return imageFileNameAshRight;
    }

    public void setImageFileNameAshRight(String imageFileNameAshRight) {
        this.imageFileNameAshRight.set(imageFileNameAshRight);
    }


    public String getImageFileNameFloor() {
        return imageFileNameFloor.get();
    }

    public StringProperty imageFileNameFloorProperty() {
        return imageFileNameFloor;
    }

    public void setImageFileNameFloor(String imageFileNameFloor) {
        this.imageFileNameFloor.set(imageFileNameFloor);
    }





    public void drawMaze(Maze maze)
    {
        this.maze = maze;
        draw();
    }

    public void draw()
    {
        if(firstDraw == 0){
            characterDirection = getImageFileNameAshLeft();
            firstDraw++;
        }
        if( maze!=null)
        {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int row = maze.getMap().length;
            int col = maze.getMap()[0].length;
            double cellHeight = canvasHeight/row;
            double cellWidth = canvasWidth/col;
            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0,0,canvasWidth,canvasHeight);
            graphicsContext.setFill(Color.RED);

            //Draw Walls
            drawWalls(row,col,cellHeight,cellWidth,graphicsContext);
            //Draw Character
            drawCharacter(cellHeight,cellWidth,graphicsContext , characterDirection);
            //DrawStart
//            drawStart(maze,cellHeight,cellWidth,graphicsContext);
            //DrawFinish
            drawEnd(maze,cellHeight,cellWidth,graphicsContext);

            if (solutionShow && this.sol != null){
                drawSolution(cellHeight,cellWidth,graphicsContext);
            }
       }
    }

    public void setPlayerPosition(int row, int col) {
        if(characterRow < row && characterCol == col){
            characterDirection = getImageFileNameAshDown();
        }
        if(characterRow > row && characterCol == col){
            characterDirection = getImageFileNameAshUp();
        }
        if(characterCol < col && characterRow == row){
            characterDirection = getImageFileNameAshRight();
        }
        if(characterCol > col && characterRow == row){
            characterDirection = getImageFileNameAshLeft();
        }

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



    private void drawWalls(int row,int col, double cellHeight, double cellWidth, GraphicsContext graphicsContext){
        double w,h;
        Image wallImage = null;
        Image floorImage = null;
        try {
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            floorImage = new Image(new FileInputStream(getImageFileNameFloor()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no file....");
        }
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                if(maze.getMap()[i][j] == 1) // Wall
                {
                    h = i * cellHeight;
                    w = j * cellWidth;
                    if (wallImage == null){
                        graphicsContext.fillRect(w,h,cellWidth,cellHeight);
                    }else{
                        graphicsContext.drawImage(floorImage,w,h,cellWidth,cellHeight);
                        graphicsContext.drawImage(wallImage,w,h,cellWidth,cellHeight);
                    }
                }
                else{

                    h = i * cellHeight;
                    w = j * cellWidth;
                    if (wallImage == null){
                        graphicsContext.fillRect(w,h,cellWidth,cellHeight);
                    }else{
                        graphicsContext.drawImage(floorImage,w,h,cellWidth,cellHeight);
                    }                }

            }
        }
    }


    private void drawCharacter(double cellHeight, double cellWidth, GraphicsContext graphicsContext, String image){
        System.out.println(getCharacterRow() + " " + getCharacterCol());
        double h_player = getCharacterRow() * cellHeight;
        double w_player = getCharacterCol() * cellWidth;
        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(image));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Image player....");
        }
        graphicsContext.drawImage(playerImage,w_player,h_player,cellWidth,cellHeight);
    }

    private void drawStart(Maze maze ,double cellHeight, double cellWidth, GraphicsContext graphicsContext){
        System.out.println( "Start Point : "+ maze.getStartPosition().getRowIndex()  + " " + maze.getStartPosition().getColumnIndex());
        double h_start = maze.getStartPosition().getRowIndex() * cellHeight;
        double w_start = maze.getStartPosition().getColumnIndex() * cellWidth;

        Image startImage = null;
        try {
            startImage = new Image(new FileInputStream(getImageFileNameStart()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Image end....");
        }
        graphicsContext.drawImage(startImage,w_start,h_start,cellWidth,cellHeight);
    }

    private void drawEnd(Maze maze ,double cellHeight, double cellWidth, GraphicsContext graphicsContext){
       // System.out.println(getCharacterRow() + " " + getCharacterCol());
        double h_end = maze.getGoalPosition().getRowIndex() * cellHeight;
        double w_end = maze.getGoalPosition().getColumnIndex() * cellWidth;

        Image endImage = null;
        try {
            endImage = new Image(new FileInputStream(getImageFileNameEnd()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Image end....");
        }
        graphicsContext.drawImage(endImage,w_end,h_end,cellWidth,cellHeight);
    }


    private void drawSolution(double cellHeight, double cellWidth, GraphicsContext graphicsContext){
        double w,h;
        Image solImage = null;
        try {
            solImage = new Image(new FileInputStream(getImageFileNameSol()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no file....");
        }

        //without the last cell the goal
        for (int i=0;i<this.sol.getSolutionPath().size()-1; i++ ){
            h = ((MazeState)this.sol.getSolutionPath().get(i)).getRow() * cellHeight;
            w = ((MazeState)this.sol.getSolutionPath().get(i)).getColumn() * cellWidth;
//            if (!(h == this.maze.getGoalPosition().getRowIndex() *cellHeight && w == this.maze.getGoalPosition().getColumnIndex() *cellWidth)){

            if (solImage == null){
                graphicsContext.fillRect(w,h,cellWidth,cellHeight);
            }else{
                graphicsContext.drawImage(solImage,w,h,cellWidth,cellHeight);
            }
//            }
        }
    }
}
