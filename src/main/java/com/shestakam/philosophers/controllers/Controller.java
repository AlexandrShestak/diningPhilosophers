package com.shestakam.philosophers.controllers;

import com.shestakam.philosophers.Philosopher;
import com.shestakam.philosophers.services.PhilosopherService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@org.springframework.stereotype.Controller
public class Controller {

    private static final double PHILOSOPHER_RADIUS = 30;
    private static final double FORK_RADIUS = 10;

    @Autowired
    private PhilosopherService philosopherService;


    @FXML
    private Canvas philosopherTable;

    @FXML
    private TableView philosopherResultsTable;

    @FXML
    private TableColumn philosopherNumberColumn;

    @FXML
    private TableColumn philosopherEatingCountColumn;


    @FXML
    private void initialize() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()-> repaint());
                // shod be fixed !!! this line must be deleted, observable list should be update by yourself
                philosopherResultsTable.refresh();
            }
        }, 1 , 100);

        philosopherResultsTable.setItems(FXCollections.synchronizedObservableList(FXCollections.observableArrayList(philosopherService.getPhilosophers())));
        philosopherNumberColumn.setCellValueFactory(
                new PropertyValueFactory<Philosopher, Integer>("philosopherNumber"));
        philosopherEatingCountColumn.setCellValueFactory(
                new PropertyValueFactory<Philosopher, Integer>("eatCount"));
    }

    @FXML
    private void start() {
        philosopherService.getPhilosophers().forEach(philosopher -> {
            philosopher.setRunning(true);
            new Thread(philosopher).start();
        });
    }

    @FXML
    private void stop() {
        philosopherService.getPhilosophers().forEach(philosopher -> philosopher.setRunning(false));
    }

    private void repaint() {
        GraphicsContext graphicsContext = philosopherTable.getGraphicsContext2D();
        double width = philosopherTable.getWidth();
        double height = philosopherTable.getHeight();
        repaintPhilosophers(graphicsContext, width, height);
        repaintForks(graphicsContext, width, height);
    }

    private void repaintPhilosophers(GraphicsContext graphicsContext, double width, double height) {
        for (Philosopher philosopher : philosopherService.getPhilosophers()) {
            if (philosopher.isEating()) {
                graphicsContext.setFill(Color.RED);
            } else {
                graphicsContext.setFill(Color.GREEN);
            }

            int philosopherNumber = philosopher.getPhilosopherNumber();
            double xCoordinate = width/2 + width/3*Math.sin(2*Math.PI/PhilosopherService.PHILOSOPHERS_NUMBER*philosopherNumber);
            double yCoordinate = height/2 + height/3*Math.cos(2*Math.PI/PhilosopherService.PHILOSOPHERS_NUMBER*philosopherNumber);
            graphicsContext.fillOval(xCoordinate, yCoordinate, PHILOSOPHER_RADIUS, PHILOSOPHER_RADIUS);
            graphicsContext.fillText("Philosopher №" + philosopher.getPhilosopherNumber(), xCoordinate, yCoordinate);
        }
    }

    private void repaintForks(GraphicsContext graphicsContext, double width, double height) {
        List<Boolean> forks = philosopherService.getForks();
        for (int  i = 0 ; i < philosopherService.PHILOSOPHERS_NUMBER ; i++) {
            if (forks.get(i)) {
                graphicsContext.setFill(Color.GREEN);
            } else {
                graphicsContext.setFill(Color.BLACK);
            }
            double xCoordinate = width/2 + width/3*Math.sin(2*Math.PI/PhilosopherService.PHILOSOPHERS_NUMBER*(i-0.5));
            double yCoordinate = height/2 + height/3*Math.cos(2*Math.PI/PhilosopherService.PHILOSOPHERS_NUMBER*(i-0.5));
            graphicsContext.fillOval(xCoordinate, yCoordinate, FORK_RADIUS, FORK_RADIUS);
        }
    }
}
