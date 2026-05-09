package View;




import Controll.StoreControll;
import Model.Admin;
import Model.Banned;
import Model.Employee;
import Model.Manager;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LogIn {

    private StoreControll controller;
    private Stage stage;

    public LogIn(Stage stage, StoreControll controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void start() {
    	
     

        GridPane p = new GridPane();
        p.setAlignment(Pos.CENTER);
        p.setPadding(new Insets(20));
        p.setHgap(10);
        p.setVgap(10);

        TextField t = new TextField();
        PasswordField pass = new PasswordField();

        p.add(new Label("Username"), 0, 0);
        p.add(t, 1, 0);
        p.add(new Label("Password"), 0, 1);
        p.add(pass, 1, 1);

        Button b = new Button("Log in");
        p.add(b, 1, 2);

        b.setOnAction(e -> {
            Employee emp = controller.checkInfo(t.getText(), pass.getText());
           
            if (emp == null) {
                
                Label w=new Label("Wrong Credentials");
                p.add(w, 1, 3);
                t.clear();
		        pass.clear();
            } 
            else if(emp instanceof Banned) {
            	Label w=new Label("Acces Denied");
            	p.add(w, 1, 3);
            }
            else if (emp instanceof Admin) {
            	System.out.print("admin");
            	controller.setEmployee(emp);
                System.out.println(controller.getEmplo().getName());
            	AdminView view = new AdminView(stage, controller);
            	view.show();
            } 
            else if (emp instanceof Manager) {
            	controller.setEmployee(emp);
                System.out.println(controller.getEmplo().getName());
            	System.out.print("Manager");
            	ManagerView view=new ManagerView(stage,controller);
            	view.show();
            } 
            else {
            	controller.setEmployee(emp);
                System.out.println(controller.getEmplo().getName());
            	System.out.print("Cashier");
            	CashierView view=new CashierView(stage,controller);
            	view.show();
            }
        });

        stage.setScene(new Scene(p,900,600));
        stage.setTitle("Log in");
        stage.show();
    }
}
	