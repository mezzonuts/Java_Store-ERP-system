package com.sosha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SoshaApp extends Application {
    private ConfigurableApplicationContext ctx;
    @Override public void init() {
        ctx = new SpringApplicationBuilder(SoshaSpringApp.class).run();
    }
    @Override public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        loader.setControllerFactory(ctx::getBean);
        stage.setScene(new Scene(loader.load(), 900, 600));
        stage.setTitle("Sosha POS v2.0");
        stage.show();
    }
    @Override public void stop() { ctx.close(); }
    public static void main(String[] args) { launch(args); }
}
