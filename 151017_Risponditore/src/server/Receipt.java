package server;

import java.util.HashMap;

class Receipt
{
	private HashMap<String, Integer> elements=new HashMap<>();
	private double price=0;

	void addProduct(Product product)
	{
		Integer number=elements.get(product.getName());
		if(number==null)
		{
			elements.put(product.getName(), 1);
		}else{
			elements.put(product.getName(), ++number);
		}
		price+=product.getPrice();
		System.out.println(price);
	}

	double getPrice()
	{
		return price;
	}

	String printElements()
	{
		final StringBuilder ALL_ELEMENTS=new StringBuilder("");
		elements.forEach((k, v) -> {ALL_ELEMENTS.append("|"+k+" - "+v);});
		return ALL_ELEMENTS.toString();
	}
}
