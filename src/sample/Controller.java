package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private static final double FILOSOPHER_RADIUS = 30;
    private static final double FORK_RADIUS = 10;
    private PhilosopherService philosopherService;


    @FXML
    private Canvas philosopherTable;

    @FXML
    private void initialize() {
        philosopherService = new PhilosopherService();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()-> repaint());
            }
        }, 1 , 10000);
    }

    @FXML
    private void start() {
        philosopherService.getPhilosophers().forEach(Philosopher::start);
    }

    @FXML
    private void stop() {
        // TODO: 07.03.2016 know why stop is deprecated
        philosopherService.getPhilosophers().forEach(Philosopher::stop);

    }

    private void repaint() {
        GraphicsContext graphicsContext = philosopherTable.getGraphicsContext2D();
        double width = philosopherTable.getWidth();
        double height = philosopherTable.getHeight();
        for (Philosopher philosopher : philosopherService.getPhilosophers()) {
            if (philosopher.isEating()) {
                graphicsContext.setFill(Color.RED);
            } else {
                graphicsContext.setFill(Color.GREEN);
            }

            int philosopherNumber = philosopher.getPhilosopherNumber();
            double xCoordinate = width/2 + width/3*Math.sin(2*Math.PI/PhilosopherService.PHILOSOPHERS_NUMBER*philosopherNumber);
            double yCoordinate = height/2 + height/3*Math.cos(2*Math.PI/PhilosopherService.PHILOSOPHERS_NUMBER*philosopherNumber);
            graphicsContext.fillOval(xCoordinate, yCoordinate, FILOSOPHER_RADIUS, FILOSOPHER_RADIUS);
            graphicsContext.fillText("Philosopher №" + philosopher.getPhilosopherNumber(), xCoordinate, yCoordinate);
        }
        List<Boolean> forks = philosopherService.getForks();
        for (int  i = 0 ; i < philosopherService.PHILOSOPHERS_NUMBER ; i++) {
            if (forks.get(i)) {
                graphicsContext.setFill(Color.GREEN);
            } else {
                graphicsContext.setFill(Color.BLACK);
            }
            double xCoordinate = width/2 + width/3*Math.sin(2*Math.PI/PhilosopherService.PHILOSOPHERS_NUMBER*(i+0.5));
            double yCoordinate = height/2 + height/3*Math.cos(2*Math.PI/PhilosopherService.PHILOSOPHERS_NUMBER*(i+0.5));
            graphicsContext.fillOval(xCoordinate, yCoordinate, FORK_RADIUS, FORK_RADIUS);

        }
    }
}
