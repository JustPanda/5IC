package server;

import java.util.function.BiFunction;

class Node implements Type
{
	String key, type, question;
	String[] answers;

	Node(String key, String type, String question, String[] answers)
	{
		this.key=key;
		this.type=type;
		this.question=question;
		this.answers=answers;
	}

	boolean exec(Product product, Receipt receipt)
	{
		BiFunction<Product, Receipt, Boolean> function=Operations.exec.get(type);
		return function!=null?function.apply(product, receipt):true;
	}
}
