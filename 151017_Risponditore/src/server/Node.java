package server;

import java.util.LinkedList;

class Node
{
	String question;
	String[] answers;
//	LinkedList<Node<String>> edge=new LinkedList<>();

	Node(String question, String[] answers)
	{
		this.question=question;
		this.answers=answers;
	}

	String print(String name)
	{
		String tmp=question;
		for (int i=0;i<answers.length;i++)
		{
			tmp+="*"+(i+") "+answers[i]);
		}
		return String.format(tmp, name);
	}
}
