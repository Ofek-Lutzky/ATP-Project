package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Arrays;

public class ServerStrategySolverSearchProblem implements IServerStrategy{
    private String directoryName = "java.io.tmpdir";

    private final Object lockThreads = new Object();

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        Solution solution;
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            // may be not available by directory security preference throw exception
            String tempDirectoryPath = System.getProperty(directoryName);

            Maze maze = (Maze) fromClient.readObject();
            byte[] mazeInBytes = maze.toByteArray();
            String fileNameString = Arrays.toString(mazeInBytes);
            // we will give each file the name by it hashcode that represent the specific maze we have
            // when we will get a requast from client to maze we already have solve
            // we will return the same solve like we retrun to the other client we solve for him in the first time
            // example: maze = {{0,1} {0,0}} ,start -> [0,0], end -> [1,1] ;"
            // filePath = tempDirectoryPath+"\\"+"startPosition,endPosition,0,1,0,0" something like this
            int fileNameInt = fileNameString.hashCode();
            String fileName = Integer.toString(fileNameInt);
            File temp = new File(tempDirectoryPath, fileName);

            if (temp.exists()) // the case we already have the solution
            {
                FileInputStream inStream = new FileInputStream(tempDirectoryPath+"\\"+fileName);
                ObjectInputStream objectIn = new ObjectInputStream(inStream);
                solution = (Solution) objectIn.readObject();
                objectIn.close();
            }

            else{
                SearchableMaze searchableMaze = new SearchableMaze(maze);

                ISearchingAlgorithm searcher = null;

                String algorithm = Configurations.getInstance().getSearcheAlgo();

                if (algorithm.equals("DFS"))
                    searcher = new DepthFirstSearch();
                else if (algorithm.equals("BEST"))
                    searcher = new BestFirstSearch();
                else if (algorithm.equals("BREADTH"))
                    searcher = new BreadthFirstSearch();
                else{
                    searcher = new BestFirstSearch();
                }

                solution = searcher.solve(searchableMaze);

                synchronized (lockThreads)
                {
                    FileOutputStream fileOut = new FileOutputStream(tempDirectoryPath+"\\"+fileName);
                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                    objectOut.writeObject(solution);
                    objectOut.close();
                }
                solution.print(maze);
            }

            toClient.writeObject(solution);
            toClient.flush();

            fromClient.close();
            toClient.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
