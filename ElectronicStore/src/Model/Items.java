package Model;

import java.io.Serializable;

public  class Items implements Serializable {


private static final long serialVersionUID = 1L;
private String name;
private String brand;
private double price;
private int stock;
private double BPrice;
private String serie;
private String sector;




public Items(String name, String brand, double price, int stock,
		double BPrice, String serie,String sector)
{
this.name = name;
this.brand = brand;
this.price = price;
this.stock = stock;
this.BPrice = BPrice;
this.serie = serie;
this.sector = sector;
}


public String getName() {
return name;
}

public void setName(String name) {

	this.name = name;

}

public String getBrand() {

	return brand;

}

public void setBrand(String brand) {

	this.brand = brand;

}

public double getPrice() {

	return price;

}

public void setPrice(double price) {

	this.price = price;

}

public int getStock() {

	return stock;

}

public void setStock(int stock) {

	this.stock = stock;

}

public double getBprice() {

	return BPrice;

}

public void setBprice(double BPrice) {

	this.BPrice = BPrice;

}

public String getSerie() {

	return serie;

}

public void setSerie(String serie) {

	this.serie = serie;

}

public String getSpefics() {

	return sector;

}

public void setSpesifics(String sector) {

	this.sector = sector;

}


public String toText() {
	return name+" "+  brand+" "+price+" "+ stock+" "+ BPrice+" "+ serie+" "+ sector;
}






}
