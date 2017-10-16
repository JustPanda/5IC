package server;

import java.util.function.BiFunction;

class Node
{
	private String key, type, question;
	private String[] answers;

	Node(String key, String type, String question, String[] answers)
	{
		this.key=key;
		this.type=type;
		this.question=question;
		this.answers=answers;
	}

	String getType()
	{
		return this.type;
	}

	String getKey()
	{
		return this.key;
	}

	String getQuestion()
	{
		return this.question;
	}

	String[] getAnswers()
	{
		return this.answers;
	}

	void exec(Operations operations, Product product, Receipt receipt)
	{
		BiFunction<Product, Receipt, Void> function=operations.getExec(type);
		if(function!=null)
			function.apply(product, receipt);
	}

	String printToClient(Object[] objects, Operations operations)
	{
		return operations.getPrintToClient(type).apply(objects);
	}

}
