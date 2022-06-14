package Model;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.util.Observer;

public interface IModel {
    void generateMaze(int rows, int cols);
    Maze getMaze();
    void updateCharacterLocation(MovementDirection direction);
    int getCharacterRow();
    int getCharacterCol();
    void assignObserver(Observer o);
    void solveMaze();
    Solution getSolution();


    boolean gameOver();
    void setGameOver(boolean gameOver);
    void stopServers();
    boolean saveFile();
    boolean loadFile(String name);
    void solutionRestart();
    void setShowSolution(boolean b);
//
//    void saveProperties(String selectedSearch, String selectedGenerate);
//
//    Object getSearchAlgorithm();
//
//    Object getGenerateAlgorithm();
}
