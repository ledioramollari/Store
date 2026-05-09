package Model;



import javafx.collections.ObservableList;

public class BillItems {
	private String name;
    private int quantity;
    private double Price;
    private String Serie;
  
    
    public BillItems(String name, double Price, int quantity, String Serie )
    {
    this.name = name;
    this.Price = Price;
    this.Serie = Serie;
    this.quantity = quantity;
    
    }
   
    public String getName() {
    	return name;
    	}

    	
    	
    	public double getPrice() {

    		return Price;

    	}

    	
    	
    	
    	public int getQuantity() {

    		return quantity;

    	}

    	public void setQuantity(int quantity) {

    		this.quantity = quantity;

    	}
    	
    	public String getSerie() {

    		return Serie;

    	}

    	
    	
    	
    	
    	public String toText() {
    	    return name + "," + quantity + "," + Price + "," + Serie + ";";
    	}
    	
    	public static double calculateTotal(ObservableList<BillItems> billItems) {
         double total = 0;

    		for (int i = 0; i < billItems.size(); i++) {
    		           
    		total=total+ billItems.get(i).getPrice()*billItems.get(i).getQuantity();

    		}
    		return total;

    		}
    	
    	



    		

    
    }
    
   

