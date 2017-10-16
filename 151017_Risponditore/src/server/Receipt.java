package server;

import java.util.HashMap;
import java.util.TreeMap;

class Receipt
{
	private TreeMap<String, Integer> elements=new TreeMap<>();
	private double price=0;

	double getPrice()
	{
		return price;
	}

	void addProduct(Product product)
	{
		String name=product.getName();
		Integer number=elements.get(name);
		if(number==null){
			number=0;
		}
		elements.put(name, ++number);
		price+=product.getPrice();
	}

	boolean removeProduct(Product product)
	{
		boolean toReturn;
		String name=product.getName();
		Integer number=elements.get(name);
		if(toReturn=(number!=null))
		{
			if(number==1)
			{
				elements.remove(name);
			}else{
				elements.put(name, --number);
			}
			price-=product.getPrice();
		}
		return toReturn;
	}

	String printElements(String separator)
	{
		final StringBuilder ALL_ELEMENTS=new StringBuilder("");
		elements.forEach((k, v) -> {ALL_ELEMENTS.append(k+" - "+v+separator);});
		return ALL_ELEMENTS.toString();
	}
}
