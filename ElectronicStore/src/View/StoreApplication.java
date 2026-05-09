package View;

import java.io.IOException;

import Controll.StoreControll;
import Model.Store;
import javafx.application.Application;
import javafx.stage.Stage;

public class StoreApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Store store = new Store();

        store.uploadItems();
        store.loadUsers();
        store.loadBills();

//        store.deafultUsers();
        StoreControll controller = new StoreControll(store);

        LogIn view = new LogIn(stage, controller);
//        stage.setMinWidth(1200);
//        stage.setMinHeight(800);
//
//        stage.setMaxWidth(1200);
//        stage.setMaxHeight(800);

        view.start();

        stage.setOnCloseRequest(e -> {
            store.saveEmpoloyeeChanges();
            store.saveItems();
        });
    }
}
