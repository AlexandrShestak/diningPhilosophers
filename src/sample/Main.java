package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final int PHILOSOPHERS_NUMBER = 5;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        List<Boolean> forks = new ArrayList<Boolean>(PHILOSOPHERS_NUMBER){
            @Override
            public Boolean get(int index) {
                if (this.size() > 0) {
                    return super.get(index % this.size());
                }
                return super.get(index);
            }
        };
        for (int i = 0 ; i < PHILOSOPHERS_NUMBER ; i++) {
            forks.add(true);
        }

        List<Philosopher> philosophers = new ArrayList<>();
        for (int i = 0 ; i < PHILOSOPHERS_NUMBER ; i++) {
            Philosopher thread = new Philosopher();
            thread.setName("Philosopher number " + i);
            thread.setPhilosopherNumber(i);
            thread.setForks(forks);
            philosophers.add(thread);
        }

        philosophers.forEach(Philosopher::start);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
