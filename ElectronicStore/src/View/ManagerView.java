package View;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;



import Controll.StoreControll;
import Model.Bill;
import Model.BillItems;
import Model.Cashier;
import Model.Employee;
import Model.Items;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerView {

	private final StoreControll controller;
	private final Stage stage;

	public ManagerView(Stage stage, StoreControll controller) {
		this.stage = stage;
		this.controller = controller;
	}

	public void show() {
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

		Button cashier = new Button("Cashiers");
		cashier.setPrefWidth(200);
		cashier.setPrefHeight(50);
		cashier.setOnAction(e -> {
			cashiers(stage);
		});
		Button controll = new Button("Inventory");
		controll.setPrefWidth(200);
		controll.setPrefHeight(50);
		controll.setOnAction(e -> {
			show2(stage);
		});

		VBox vbox1 = new VBox(10);
		vbox1.getChildren().addAll(cashier, controll);
		p.add(vbox1, 3, 0);
		p.add(b, 5, 8);
		Scene scene2 = new Scene(p, 900, 600);
		stage.setScene(scene2);
		stage.sizeToScene();

	}

	@SuppressWarnings("deprecation")
	public void show2(Stage stage) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setPadding(new Insets(20));
		grid.setHgap(10);
		grid.setVgap(10);
		Employee emp = controller.getEmplo();
		TextField name = new TextField();
		TextField brand = new TextField();
		TextField price = new TextField();
		TextField stock = new TextField();
		TextField bprice = new TextField();
		TextField serie = new TextField();
		ComboBox<String> sectorBox = new ComboBox<>();
		if (emp.getPhone().equals("Both")) {
			sectorBox.getItems().addAll("HomeApliances","IT" );
}
		
		else {
			sectorBox.getItems().addAll(emp.getPhone());
		}
		
		 
		grid.add(new Label("Name:"), 0, 0);
		grid.add(name, 1, 0);

		grid.add(new Label("Brand:"), 0, 1);
		grid.add(brand, 1, 1);

		grid.add(new Label("Price:"), 0, 2);
		grid.add(price, 1, 2);

		grid.add(new Label("Stock"), 0, 3);
		grid.add(stock, 1, 3);

		grid.add(new Label("Buy Price:"), 0, 4);
		grid.add(bprice, 1, 4);

		grid.add(new Label("Serie:"), 0, 5);
		grid.add(serie, 1, 5);

		grid.add(new Label("Spesifics:"), 0, 6);
		grid.add(sectorBox, 1, 6);

		Button addBtn = new Button("Create Item");
		addBtn.setOnAction(e -> {
			try {
				String name1 = name.getText();
				String brand1 = brand.getText();
				double price1 = Double.parseDouble(price.getText());
				int stock1 = Integer.parseInt(stock.getText());
				double power1 = Double.parseDouble(bprice.getText());
				String serie1 = serie.getText();
				String spesifics1 = sectorBox.getValue();

				Items item = new Items(name1, brand1, price1, stock1, power1, serie1, spesifics1);

				controller.addItems(item);

				System.out.println("Item added!");

				name.clear();
				brand.clear();
				price.clear();
				stock.clear();
				bprice.clear();
				serie.clear();
				
				show2(stage);

			} catch (NumberFormatException ex) {
				System.out.println("must be numbers");
			}

		});
		
		ArrayList<Items> items =controller.sortSectors(controller.getListItems(),emp.getPhone());

		TableView<Items> table = new TableView<>();

		ObservableList<Items> data = FXCollections.observableArrayList(items);

		table.setItems(data);

		TableColumn<Items, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Items, String> brandCol = new TableColumn<>("Brand");

		brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));

		TableColumn<Items, Double> priceCol = new TableColumn<>("Price");

		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		TableColumn<Items, Double> bpriceCol = new TableColumn<>("B.Price");
		
		bpriceCol.setCellValueFactory(new PropertyValueFactory<>("bprice"));

		TableColumn<Items, Integer> stockCol = new TableColumn<>("Stock");

		stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
		
		stockCol.setCellFactory(col -> new TableCell<Items, Integer>() {
		    
		    protected void updateItem(Integer value, boolean empty) {
		        super.updateItem(value, empty);
		        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
		            setText(null);
		            setStyle("");
		            return;
		        }
		        Items item = getTableRow().getItem();
		        setText(String.valueOf(value));
		        setStyle(item.getStock() < 3 ? "-fx-text-fill: red;" : "");
		    }
		});

		TableColumn<Items, String> serieCol = new TableColumn<>("Serie");

		serieCol.setCellValueFactory(new PropertyValueFactory<>("serie"));

		TableColumn<Items, Void> removeCol = new TableColumn<>("Remove");

		removeCol.setCellFactory(col -> new TableCell<>() {

			private final Button btn = new Button("Remove");

			{
				btn.setOnAction(e -> {
					Items i = getTableView().getItems().get(getIndex());

					controller.removeItem(i);
					table.getItems().remove(i);
					 table.refresh();;

				});
			}

			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {

					setGraphic(btn);

				}
			}
		});

		TableColumn<Items, Void> modifyCol = new TableColumn<>("Modify");

		modifyCol.setCellFactory(col -> new TableCell<>() {

			Button btn = new Button("Modify");

			{
				btn.setOnAction(e -> {
					Items i = getTableView().getItems().get(getIndex());
					Stage popup = new Stage();
					popup.initModality(Modality.APPLICATION_MODAL);

					TextField nameField = new TextField(i.getName());

					TextField brandField = new TextField(i.getBrand());

					TextField priceField = new TextField(String.valueOf(i.getPrice()));
					
					TextField bpriceField = new TextField(String.valueOf(i.getBprice()));

					TextField stockField = new TextField(String.valueOf(i.getStock()));

					TextField serieField = new TextField(i.getSerie());
					
					ComboBox<String> sectorBox = new ComboBox<>();
					if (emp.getPhone().equals("Both")) {
						sectorBox.getItems().addAll("HomeApliances","IT" );
			}
					
					else {
						sectorBox.getItems().addAll(emp.getPhone());
					}
					sectorBox.setValue(i.getSpefics());
					Button saveBtn = new Button("Save");

					saveBtn.setOnAction(ev -> {
						controller.editItem(i, nameField.getText(), brandField.getText(), priceField.getText(),
								stockField.getText(), serieField.getText(),bpriceField.getText(), sectorBox.getValue()
								

						);
						 table.refresh();
						popup.close();
					});

					GridPane grid1 = new GridPane();
					grid1.setPadding(new Insets(15));
					grid1.setHgap(10);
					grid1.setVgap(10);

					grid1.add(new Label("Name"), 0, 0);
					grid1.add(nameField, 1, 0);

					grid1.add(new Label("Brand"), 0, 1);
					grid1.add(brandField, 1, 1);

					grid1.add(new Label("Price"), 0, 2);
					grid1.add(priceField, 1, 2);
					
					grid1.add(new Label("B.Price"), 0, 3);
					grid1.add(bpriceField, 1, 3);
					

					grid1.add(new Label("Stock"), 0, 4);
					grid1.add(stockField, 1, 4);

					grid1.add(new Label("Serie"), 0, 5);
					grid1.add(serieField, 1, 5);
					
					grid1.add(new Label("Sector"), 0, 6);
					grid1.add(sectorBox, 1, 6);
					
					
					grid1.add(saveBtn, 1, 7);
					popup.setScene(new Scene(grid1));
					popup.show();
				});
			}

			public void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {

					setGraphic(btn);

				}
			}
		});

		table.getColumns().add(nameCol);
		table.getColumns().add(brandCol);
		table.getColumns().add(priceCol);
		table.getColumns().add(bpriceCol);
	
		table.getColumns().add(stockCol);
		table.getColumns().add(serieCol);
		table.getColumns().add(removeCol);
		table.getColumns().add(modifyCol);
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

		stage.setScene(new Scene(p, 1000, 600));
		stage.setTitle("Controll");
		stage.show();
		stage.sizeToScene();

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public void cashiers(Stage stage) {

	    GridPane grid = new GridPane();
	    grid.setAlignment(Pos.TOP_LEFT);
	    grid.setPadding(new Insets(20));
	    grid.setHgap(10);
	    grid.setVgap(10);
 Employee m=controller.getEmplo();
	    TextField username = new TextField();
	    PasswordField password = new PasswordField();
	    TextField name = new TextField();
	    TextField salary = new TextField();
	    DatePicker birthday = new DatePicker();
	    
	    TextField email = new TextField();
	    ComboBox<String> comboBox = new ComboBox<>();
		if (m.getPhone().equals("Both")) {
			comboBox.getItems().addAll("HomeApliances","IT","Both" );
}
		
		else {
			comboBox.getItems().addAll(m.getPhone());
		}

	    grid.add(new Label("Username:"), 0, 0);
	    grid.add(username, 1, 0);
	    grid.add(new Label("Password:"), 0, 1);
	    grid.add(password, 1, 1);
	    grid.add(new Label("Name:"), 0, 2);
	    grid.add(name, 1, 2);
	    grid.add(new Label("Status:"), 0, 3);
	    grid.add(new Label("CASHIER"), 1, 3);
	    grid.add(new Label("Salary:"), 0, 4);
	    grid.add(salary, 1, 4);
	    grid.add(new Label("Birthday:"), 0, 5);
	    grid.add(birthday, 1, 5);
	    grid.add(new Label("Sector:"), 0, 6);
	    grid.add(comboBox, 1, 6);
	    grid.add(new Label("Email:"), 0, 7);
	    grid.add(email, 1, 7);

	    Button addBtn = new Button("Create Cashier");
	    addBtn.setOnAction(e -> {
	        try {
	            LocalDate localDate = birthday.getValue();
	            if (localDate == null) return;

	            Employee emp = new Cashier(
	                    username.getText(),
	                    password.getText(),
	                    name.getText(),
	                    "CASHIER",
	                    Double.parseDouble(salary.getText()),
	                    Date.valueOf(localDate),
	                    comboBox.getValue(),
	                    email.getText()
	            );

	            controller.addEmployee(emp);

	        } catch (Exception ex) {
	            System.out.println("Invalid input");
	        }
	    });
	    grid.add(addBtn, 1, 8);

	    Button b1 = new Button("Back");
	    b1.setOnAction(e -> show());

	    ArrayList<Employee> e = controller.getListEmployee();
	    ArrayList<Employee> cashiers = controller.sortEmployees(m.getPhone(),controller.getCashiers(e));

	    // ------------------ Bills panel ------------------
	    GridPane q = new GridPane();
	    q.setPadding(new Insets(20));

	    Label totalLabel = new Label("Total: $0.00");
	    totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

	    VBox billsBox = new VBox(10);
	    billsBox.setPadding(new Insets(10));

	    q.add(totalLabel, 0, 0);

	    ScrollPane scroll = new ScrollPane(billsBox);
	    scroll.setFitToWidth(true);

	    q.add(scroll, 0, 1);

	    // ------------------ Table ------------------
	    TableView<Employee> table = new TableView<>();
	    table.setItems(FXCollections.observableArrayList(cashiers));

	    TableColumn<Employee, String> nameCol =
	            new TableColumn<>("Name");
	    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

	    TableColumn<Employee, String> userCol =
	            new TableColumn<>("Username");
	    userCol.setCellValueFactory(new PropertyValueFactory<>("username"));

	    TableColumn<Employee, String> statusCol =
	            new TableColumn<>("Status");
	    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

	    TableColumn<Employee, Void> billsCol =
	            new TableColumn<>("Bills");

	    billsCol.setCellFactory(col -> new TableCell<>() {
	        private final Button btn = new Button("View");

	        {
	            btn.setOnAction(e -> {
	                Employee cashier =
	                        getTableView().getItems().get(getIndex());

	                billsBox.getChildren().clear();
	                HBox dates=new HBox();
	                   
	               
	                DatePicker birthday1 = new DatePicker();
	                DatePicker birthday2 = new DatePicker();
	               
	                Button p=new Button("generate");
	                Label d=new Label("to:");
	                Label d1=new Label("from");
	                dates.getChildren().addAll(d1,birthday2,d,birthday1,p);
	                billsBox.getChildren().addAll(dates,p);
	                ArrayList<Bill> bills=new ArrayList<>();
	                p.setOnAction(m->{
	                	
	                	LocalDate localDate1 = birthday1.getValue();
	                    LocalDate localDate2 = birthday2.getValue();

	                    if (localDate1 == null || localDate2 == null) {
	                        System.out.println("Please select both dates");
	                        return;
	                    }
	                	bills.clear();
	                    bills.addAll(
	                        controller.billsBetweenDates(
	                            cashier.getUsername(),
	                            Date.valueOf(localDate1),
	                            Date.valueOf(localDate2)
	                        )
	                    );
	                    
	                    
		                double total = 0;
		                for (Bill bill : bills) {
		                    total += bill.getTotal();
	                        
		                    VBox billBox = new VBox(2);
		                    billBox.getChildren().add(new Label("---------------------------------"));
		                    billBox.getChildren().add(new Label("---------------------------------"));
		                    billBox.getChildren().add(new Label("Bill ID: " + bill.getBillId()));
		                    billBox.getChildren().add(new Label("Date: " + bill.getdate()));
		                    billBox.getChildren().add(new Label("Cashier: " + bill.getcashier()));

		                    for (BillItems item : bill.getItems()) {
		                        billBox.getChildren().add(
		                                new Label("• " + item.getName()
		                                        + " x" + item.getQuantity()
		                                        + " $" + item.getPrice())
		                        );
		                    }

		                    billBox.getChildren().add(new Label("Total: $" + bill.getTotal()));
		                    billBox.getChildren().add(new Label("---------------------------------"));

		                    billsBox.getChildren().addAll(billBox);
		                }

		                totalLabel.setText(
		                        "Total: $" + String.format("%.2f", total)
		                );
	                    
	                });
	               
	            });
	        }

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);
	            setGraphic(empty ? null : btn);
	        }
	    });

	    table.getColumns().addAll(nameCol, userCol, statusCol, billsCol);
	    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	    GridPane t = new GridPane();
	    t.setPadding(new Insets(20));
	    t.add(table, 0, 0);

	    GridPane p = new GridPane();
	    p.setPadding(new Insets(20));
	    p.setHgap(10);
	    p.setVgap(8);

	    p.add(grid, 0, 0);
	    p.add(t, 1, 0);
	    p.add(q, 2, 0);
	    p.add(b1, 3, 0);

	    Scene scene = new Scene(p, 1000, 600);
	    stage.setScene(scene);
	    stage.show();
	}
}
