package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
//    private final Logger LOG = LogManager.getLogger(); //Log4j2
    private ExecutorService threadPool; // Thread pool

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;

        // the number of the threads in the threads pool will determine by the configuration
        Configurations.getInstance().setThreadPoolSize("2");
        this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(Configurations.getInstance().getThreadPoolSize()));
    }

    public void start(){
        // we push it to a new thread so each server will be under thread of its own
        new Thread(this::runServer).start();
    }

    public void runServer(){
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
//            LOG.info("Starting server at port = " + port);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
//                    LOG.info("Client accepted: " + clientSocket.toString());

                    // Insert the new task into the thread pool:
                    threadPool.submit(() ->  handleClient(clientSocket));

                    // This thread will handle the new Client
//                    new Thread(() -> {
//                        handleClient(clientSocket);
//                    }).start();

                } catch (SocketTimeoutException e){
//                    LOG.debug("Socket timeout");
                    System.out.println(e.getMessage());
                }
            }
            serverSocket.close();
            //threadPool.shutdown(); // do not allow any new tasks into the thread pool (not doing anything to the current tasks and running threads)
            threadPool.shutdownNow(); // do not allow any new tasks into the thread pool, and also interrupts all running threads (do not terminate the threads, so if they do not handle interrupts properly, they could never stop...)
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
//        LOG.info("Stopping server...");
        stop = true;
    }

    private void handleClient(Socket clientSocket) {
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
//            LOG.info("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (IOException e){
//            LOG.error("IOException", e);
            System.out.println(e.getMessage());
        }
    }
}
