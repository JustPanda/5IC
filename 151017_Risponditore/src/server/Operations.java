package server;

import java.util.HashMap;
import java.util.function.BiFunction;

public class Operations implements Type
{
	static HashMap<String, BiFunction<Product, Receipt, Boolean>> exec=new HashMap<>();
	{
		exec.put(ITEM_TYPE, (Product product, Receipt receipt) -> {
				receipt.addProduct(product);
				return true;
		});
		exec.put(INSERT_TYPE, (Product product, Receipt receipt) -> {
			return receipt.removeProduct(product);
		});
	}
}
