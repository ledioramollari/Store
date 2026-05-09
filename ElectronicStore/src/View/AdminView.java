package View;



import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import Controll.StoreControll;

import Model.Cashier;
import Model.Employee;
import Model.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminView {
	private final StoreControll controller;
	private final Stage stage;

	public AdminView(Stage stage, StoreControll controller) {
		this.stage = stage;
		this.controller = controller;
	}

	public void show() {
//      stage.setMinWidth(1200);
//      stage.setMinHeight(800);
//
//      stage.setMaxWidth(1200);
//      stage.setMaxHeight(800);
		GridPane p = new GridPane();

		p.setAlignment(Pos.CENTER);
		p.setPadding(new Insets(20, 10, 10, 10));
		p.setHgap(50);
		p.setVgap(10);

		Button b = new Button("Log Out");
		
		b.setOnAction(e -> {
			LogIn view = new LogIn(stage, controller);

			view.start();

		});

		
		Button controll = new Button("Controll");
		controll.setPrefWidth(200);
		controll.setPrefHeight(50);
		controll.setOnAction(e -> {
			showControll(stage);
		});

		VBox vbox1 = new VBox(10);
		vbox1.getChildren().addAll(controll);
		p.add(vbox1, 1, 0);
		p.add(b, 5, 8);
		Scene scene2 = new Scene(p, 900,600);
		stage.sizeToScene();
		stage.setScene(scene2);
	}

	

	@SuppressWarnings("deprecation")
	public void showControll(Stage stage) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setPadding(new Insets(20));
		grid.setHgap(10);
		grid.setVgap(10);

		TextField username = new TextField();
		PasswordField password = new PasswordField();
		TextField name = new TextField();
		TextField salary = new TextField();
		DatePicker birthday = new DatePicker();
		ComboBox<String> sector = new ComboBox<>();
	    sector.getItems().addAll(
	        "HomeApliances",
	        "IT",
	        "Both");
		TextField email = new TextField();
		ToggleGroup statusGroup = new ToggleGroup();

		RadioButton cashierRB = new RadioButton("Cashier");
		cashierRB.setToggleGroup(statusGroup);
		cashierRB.setSelected(true);

		RadioButton managerRB = new RadioButton("Manager");
		managerRB.setToggleGroup(statusGroup);

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);

		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		grid.add(new Label("Name:"), 0, 2);
		grid.add(name, 1, 2);

		HBox statusBox = new HBox(10, cashierRB, managerRB);
		grid.add(new Label("Status:"), 0, 3);
		grid.add(statusBox, 1, 3, 2, 1);

		grid.add(new Label("Salary:"), 0, 4);
		grid.add(salary, 1, 4);

		grid.add(new Label("Birthday:"), 0, 5);
		grid.add(birthday, 1, 5);

		grid.add(new Label("Status:"), 0, 6);
		grid.add(sector, 1, 6);

		grid.add(new Label("Email:"), 0, 7);
		grid.add(email, 1, 7);;
		username.setPrefWidth(180);
		password.setPrefWidth(180);
		name.setPrefWidth(180);
		salary.setPrefWidth(180);
		birthday.setPrefWidth(180);
		sector.setPrefWidth(180);
		email.setPrefWidth(180);
		Button addBtn = new Button("Create Employee");
		TableView<Employee> table = new TableView<>();
		ArrayList<Employee> employees = controller.getListEmployee();
		
		ObservableList<Employee> data =
			    FXCollections.observableArrayList(employees);

			table.setItems(data);
		addBtn.setOnAction(e -> {
		    try {
		        String status = cashierRB.isSelected() ? "CASHIER" : "MANAGER";
		        LocalDate localDate = birthday.getValue();

		        if (localDate == null) {
		            System.out.println("Select a date");
		            return;
		        }

		        Employee emp;
		        
		        String username1=username.getText();
		        String password1=password.getText();
		        String name1=name.getText();
		        
		        double sal=Double.valueOf(salary.getText());
		        Date date =java.sql.Date.valueOf(localDate);
		        String sector1=sector.getValue();
		        String email1=email.getText();
		        

		        if (status.equals("MANAGER")) {
					emp = new Manager(username1,password1,name1,status,sal, date, sector1,email1);
					System.out.println("manager added")
					;
					
				} else {
					emp = new Cashier(username1,password1,name1,status,sal,date,sector1,email1);
					System.out.println("cashier added");
				}

		        controller.addEmployee(emp);
		        
		        System.out.println("cashier added");
		        showControll( stage);
		        System.out.println("table added");
		        System.out.println("employee added");

		    } catch (Exception ex) {
		        System.out.println("invalid ");
		    }
		});
	

		
		
		
			
			TableColumn<Employee, String> nameCol =
				    new TableColumn<>("Name");
			nameCol.setCellValueFactory(
				    new PropertyValueFactory<>("name")
				);
			
			TableColumn<Employee, String> statusCol =
				    new TableColumn<>("Status");

				statusCol.setCellValueFactory(
				    new PropertyValueFactory<>("status")
				);
				
				TableColumn<Employee, String> userCol =
					    new TableColumn<>("Username");

					userCol.setCellValueFactory(
					    new PropertyValueFactory<>("username")
					);
				
				
					
					
					TableColumn<Employee, Void> removeCol =
						    new TableColumn<>("Remove");
					
					removeCol.setCellFactory(col -> new TableCell<>() {

					    private final Button btn = new Button("Remove");

					    {
					        btn.setOnAction(e -> {
					            Employee emp =
					                getTableView().getItems().get(getIndex());

					            controller.removeEmployee(emp);
					            table.getItems().remove(emp);
					            showControll(stage);
					            
					        });
					    }

					    @Override
					    protected void updateItem(Void item, boolean empty) {
					        super.updateItem(item, empty);
					        if (empty) {
					            setGraphic(null);
					        } else {
					            Employee emp = getTableView().getItems().get(getIndex());

					            if ("ADMIN".equals(emp.getStatus())) {
					                setGraphic(null); 
					            } else {
					                setGraphic(btn);
					            }
					        }
					    }
					});

					
					TableColumn<Employee, Void> modifyCol =
						    new TableColumn<>("Modify");

						modifyCol.setCellFactory(col -> new TableCell<>() {

						     Button btn = new Button("Modify");

						    {
						        btn.setOnAction(e -> {
						            Employee emp =
						                getTableView().getItems().get(getIndex());
						            Stage popup = new Stage();
								    popup.initModality(Modality.APPLICATION_MODAL);
					
								    TextField nameField =
								        new TextField(emp.getName());
					
								    TextField salaryField =
								        new TextField(String.valueOf(emp.getSalary()));
					
								   
					
								    TextField emailField =
								        new TextField(emp.getEmail());
					
								    DatePicker birthdayPicker =
								        new DatePicker(emp.getBirthday().toLocalDate());
					
								    TextField usernameField =
								        new TextField(emp.getUsername());
								    usernameField.setDisable(true);
					
								    PasswordField passwordField = new PasswordField();
								    passwordField.setText(emp.getPassword());
								    passwordField.setDisable(true);
					
								    ComboBox<String> statusBox = new ComboBox<>();
								    statusBox.getItems().addAll("CASHIER", "MANAGER","BANNED");
								    statusBox.setValue(emp.getStatus());
								    
								    ComboBox<String> sectorBox = new ComboBox<>();
								    sectorBox.getItems().addAll("HomeApliances",
									        "IT",
									        "Both");
								    sectorBox.setValue(emp.getPhone());
					
								    Button saveBtn = new Button("Save");
					
								    saveBtn.setOnAction(ev -> {
								        controller.editEmployee(emp,
								            nameField.getText(),
								            salaryField.getText(),
								            statusBox.getValue()
								           ,
								            emailField.getText(),
								            birthdayPicker.getValue(),
								            sectorBox.getValue()
								           
								        );
								        table.refresh();
								        popup.close();
								    });
					
								    GridPane grid1 = new GridPane();
								    grid1.setPadding(new Insets(15));
								    grid1.setHgap(10);
								    grid1.setVgap(10);
					
								    grid1.add(new Label("Username"), 0, 0);
								    grid1.add(usernameField, 1, 0);
					
								    grid1.add(new Label("Password"), 0, 1);
								    grid1.add(passwordField, 1, 1);
					
								    grid1.add(new Label("Name"), 0,2 );
								    grid1.add(nameField,1, 2);
					
								    grid1.add(new Label("Status"), 0, 3);
								    grid1.add(statusBox, 1, 3);
								   
								    grid1.add(new Label("Salary"), 0, 4);
								    grid1.add(salaryField, 1, 4);
					
								    grid1.add(new Label("Sector"), 0, 5);
								    grid1.add(sectorBox, 1, 5);
					
								    grid1.add(new Label("Email"), 0, 6);
								    grid1.add(emailField, 1, 6);
					
								    grid1.add(new Label("Birthday"), 0, 7);
								    grid1.add(birthdayPicker, 1, 7);
								    
								    grid1.add(saveBtn, 1, 8);
					                popup.setScene(new Scene(grid1));
								    popup.show();

						            });
						    }

						    @Override
						    protected void updateItem(Void item, boolean empty) {
						        super.updateItem(item, empty);
						        if (empty) {
						            setGraphic(null);
						        } else {
						            Employee emp = getTableView().getItems().get(getIndex());

						            if ("ADMIN".equals(emp.getStatus())) {
						                setGraphic(null); 
						            } else {
						                setGraphic(btn);
						            }
						    }
						    }
						});
						
						table.getColumns().add(nameCol);
						table.getColumns().add(userCol);
						table.getColumns().add(statusCol);
						table.getColumns().add(modifyCol);
						table.getColumns().add(removeCol);
						table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

						GridPane p = new GridPane();
						p.setAlignment(Pos.TOP_LEFT);
						p.setPadding(new Insets(20, 10, 10, 10));
						p.setHgap(10);
						p.setVgap(8);
						
						GridPane t = new GridPane();
						t.setAlignment(Pos.TOP_LEFT);
						t.setPadding(new Insets(20, 10, 10, 10));
						t.setHgap(10);
						t.setVgap(8);

		
		grid.add(addBtn, 1, 8);
		t.add(table, 0, 0);
		Button b1 = new Button("Back");
		b1.setOnAction(e -> {
			show();
		});
		p.add(b1, 3, 0);
		p.add(grid, 0, 0);
		p.add(t, 1, 0);
		

		stage.setScene(new Scene(p, 900, 600));
		stage.setTitle("Controll");
		stage.sizeToScene();
		stage.show();
		

	}

}
