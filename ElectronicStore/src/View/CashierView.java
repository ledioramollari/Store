package View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Label;
import Controll.StoreControll;
import Model.Bill;
import Model.BillItems;
import Model.Employee;
import Model.Items;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class CashierView {
	private final StoreControll controller;
	private final Stage stage;

	public CashierView(Stage stage, StoreControll controller) {
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

		Button cashier = new Button("Bills of Today");
		cashier.setPrefWidth(200);
		cashier.setPrefHeight(50);
		cashier.setOnAction(e -> {
			show2(stage);
		});
		Button controll = new Button("Generate Bills");
		controll.setPrefWidth(200);
		controll.setPrefHeight(50);
		controll.setOnAction(e -> {
			RegisterBills(stage);
		});

		VBox vbox1 = new VBox(10);
		vbox1.getChildren().addAll(controll, cashier);
		p.add(vbox1, 3, 0);
		p.add(b, 5, 8);
		Scene scene2 = new Scene(p, 900, 600);
		stage.setScene(scene2);
		stage.sizeToScene();

	}

	public void show2(Stage stage) {

		GridPane p = new GridPane();
		p.setPadding(new Insets(20));

		VBox billsBox = new VBox();
		billsBox.setSpacing(10);
		billsBox.setPadding(new Insets(10));

		ArrayList<Bill> bills = controller.BillsToday();

		for (Bill bill : bills) {

			VBox billBox = new VBox();
			billBox.setSpacing(2);

			billBox.getChildren().add(new Label("---------------------------------"));
			billBox.getChildren().add(new Label("Bill ID: " + bill.getBillId()));
			billBox.getChildren().add(new Label("Date: " + bill.getdate()));
			billBox.getChildren().add(new Label("Cashier: " + bill.getcashier()));
			billBox.getChildren().add(new Label("Items:"));

			for (BillItems item : bill.getItems()) {
				String line = "  • " + item.getName() + "   x" + item.getQuantity() + "   $" + item.getPrice();

				billBox.getChildren().add(new Label(line));
			}

			billBox.getChildren().add(new Label("Total: $" + bill.getTotal()));
			billBox.getChildren().add(new Label("---------------------------------"));

			billsBox.getChildren().add(billBox);
		}

		ScrollPane scroll = new ScrollPane();
		scroll.setContent(billsBox);
		Button b1 = new Button("Back");
		b1.setOnAction(e -> {
			show();
		});
		p.add(scroll, 0, 0);
		p.add(b1, 1, 0);

		Scene scene = new Scene(p, 900, 600);
		 stage.setTitle("Todays bills");
		stage.setScene(scene);
		stage.sizeToScene();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public void RegisterBills(Stage stage) {
		GridPane p = new GridPane();

		p.setAlignment(Pos.TOP_LEFT);
		p.setPadding(new Insets(20, 10, 10, 10));
		p.setHgap(50);
		p.setVgap(10);
		Employee emp = controller.getEmplo();
		ArrayList<Items> items = controller.sortSectors(controller.getListItems(),emp.getPhone());
		System.out.println(emp.getPhone());
		 

		Label totLabel = new Label("Total: 0.0");
		
		ObservableList<BillItems> billItems = FXCollections.observableArrayList();

		VBox itemsBox = new VBox(15);
		itemsBox.setPadding(new Insets(10));
		itemsBox.setPrefWidth(260);

		TableView<BillItems> table = new TableView<>(billItems);
		table.setPrefSize(450, 400);
		Button b1 = new Button("Back");
		for (Items i : items) {

			Button itemBtn = new Button(i.getName() + "\n$" + i.getPrice());
			if (i.getStock() <= 0) {
				itemBtn.setDisable(true);
			}
			itemBtn.setPrefSize(240, 80);
			itemBtn.setStyle("-fx-font-size: 16px;");

			itemBtn.setOnAction(e -> {
				b1.setDisable(true);
				for (BillItems bi : billItems) {
					if(i.getStock()<=0) {
				    	itemBtn.setDisable(true);
				    	itemBtn.setText("Out of Stock");
				    	break;
				    }
					if (bi.getSerie().equals(i.getSerie())) {

						bi.setQuantity(bi.getQuantity() + 1);
						i.setStock(i.getStock() - 1);
						System.out.print(i.getStock());
						totLabel.setText("Total: " + BillItems.calculateTotal(billItems));
						table.refresh();
						return;
					}
				}
				
				if(i.getStock()<=0) {
			    	itemBtn.setDisable(true);
			    }

				else{
					billItems.add(new BillItems(i.getName(), i.getPrice(), 1, i.getSerie()));

				i.setStock(i.getStock() - 1);
				totLabel.setText("Total: " + BillItems.calculateTotal(billItems));
				System.out.print(i.getStock());
				}
			});

			itemsBox.getChildren().add(itemBtn);
		}

		TableColumn<BillItems, String> nameCol = new TableColumn<>("Item");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setPrefWidth(100);

		TableColumn<BillItems, Integer> qtyCol = new TableColumn<>("Qty");
		qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		qtyCol.setPrefWidth(60);

		TableColumn<BillItems, Double> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceCol.setPrefWidth(80);

		TableColumn<BillItems, String> serieCol = new TableColumn<>("Serie");
		serieCol.setCellValueFactory(new PropertyValueFactory<>("serie"));
		serieCol.setPrefWidth(80);

		
		
		
		TableColumn<BillItems, Void> removeCol = new TableColumn<>("Remove");
		removeCol.setPrefWidth(100);

		removeCol.setCellFactory(col -> new TableCell<>() {
			 Button btn = new Button("Remove");

			{
				btn.setOnAction(e -> {
					

					BillItems bi = getTableView().getItems().get(getIndex());
					for (Items i : items) {
						if (i.getSerie().equals(bi.getSerie())) {
							i.setStock(i.getStock() + 1);

							
							System.out.print(i.getStock());
						}
					}
					bi.setQuantity(bi.getQuantity() - 1);
					totLabel.setText("Total: " + BillItems.calculateTotal(billItems));
					if (bi.getQuantity() == 0) {
						billItems.remove(bi);
						
					}
					if (billItems.isEmpty()) {
						b1.setDisable(false);
						RegisterBills(stage);
						return;
					}
					
					table.refresh();
				});
			}

			
			public void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(empty ? null : btn);
			}
		});

		table.getColumns().addAll(nameCol, qtyCol, priceCol, serieCol, removeCol);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		
		//---------------------make the bill--------------------------
		Button makeBill = new Button("Generate Bill");
		makeBill.setPrefSize(200, 50);
		;

		makeBill.setOnAction(e -> {
			

			LocalDate today = LocalDate.now();
			java.sql.Date date = java.sql.Date.valueOf(today);

			double total = BillItems.calculateTotal(billItems);
			
			Random random = new Random();
		    int number = random.nextInt(10000); // 0 to 9999
		   String ID= String.format("%04d", number);
			Bill bill = new Bill(ID, date, emp.getName(), new ArrayList<>(billItems), total);

			controller.addtheBill(bill);
			billItems.clear();
			table.refresh();
			b1.setDisable(false);

			System.out.println("bill made");
		});

	


		HBox root = new HBox(20);
		root.setPadding(new Insets(20));

		b1.setOnAction(e -> {
			show();
		});

		VBox rightSide = new VBox(15, table, makeBill, totLabel);

		HBox root1 = new HBox(20, itemsBox, rightSide, b1);
		root.setPadding(new Insets(20));

		stage.setScene(new Scene(root1, 900, 600));
		stage.setTitle("Billing System");
		stage.sizeToScene();
		stage.show();
	}
	
	public void disableButton(Button button) {
		button.setDisable(true);
	}
}
