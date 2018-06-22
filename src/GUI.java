import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
            stage.setScene(new Scene(root, 559, 600));
            stage.setResizable(false);
            stage.setTitle("Solar Panel Control Center");
            stage.show();
            do{
                Runnable databaseUpload = new DBConnect();
                new Thread(databaseUpload).start();
            }while(stage.isShowing());
        }catch (Exception e){
            System.out.println("Test");
        }
    }
}
