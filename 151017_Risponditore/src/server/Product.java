package server;

class Product
{
	private double price;
	private String name;

	Product(String name, double price)
	{
		this.name=name;
		this.price=price;
	}

	double getPrice()
	{
		return this.price;
	}

	String getName()
	{
		return this.name;
	}
}
