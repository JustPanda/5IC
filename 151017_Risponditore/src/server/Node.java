package server;

class Node implements Type
{
	boolean isTerminal;
	String question, type;
	String[] answers, answersKey;
	Product product;

	Node(String question, String type)
	{
		this.question=question;
		this.type=type;
		setTerminal(type);
	}

	Node(String question, String[] answers, String type)
	{
		this.question=question;
		this.type=type;
		this.answers=/*new String[answers.length]*/answers;
//		this.answersKey=new String[answers.length];
//		for(int i=0;i<answers.length;i++)
//		{
//			String[] tmp=answers[i].split(":");
//			this.answers[i]=tmp[0];
//			if(type.equals(CHOOSE_TYPE))
//			{
//				this.answersKey[i]=tmp[1];
//			}
//		}
		setTerminal(type);
	}

	Node(String question, String[] answers, String key, String type)
	{
		String[] tmp=question.split("\\|");
		this.question=tmp[0];
		this.type=type;
		this.answersKey=answers;
		this.product=new Product(key, Double.parseDouble(tmp[1]));
		setTerminal(type);
	}

	private void setTerminal(String type)
	{
		this.isTerminal=type.equals(INSERT_TYPE)||type.equals(ITEM_TYPE);
	}

	void exec(Receipt receipt)
	{
		switch(type)
		{
			case ITEM_TYPE:
					receipt.addProduct(product);
				break;
			case INSERT_TYPE:
				break;
		}
	}

	String print(Receipt receipt)
	{
		return String.format(question, receipt.getPrice(), receipt.printElements());
	}
}
