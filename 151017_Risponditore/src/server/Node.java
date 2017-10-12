package server;

class Node
{
	boolean isTerminal;
	String question;
	String[] answers;

	Node(String question, String[] answers)
	{
		this.question=question;
		this.answers=answers;
		this.isTerminal=(answers.length==1);
	}

	String print(String name)
	{
		String tmp=question;
		if(!isTerminal)
		{
			tmp+="->";
			for (int i=0;i<answers.length;i++)
			{
				tmp+=answers[i]+(i==(answers.length-1)?"":",");
			}
		}
		return String.format(tmp, name);
	}
}
