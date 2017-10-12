package server;

import java.util.HashMap;

class Pizzeria
{
	private HashMap<String, Node> allNode=new HashMap<>();
	private Node actual, before;

	Pizzeria(String[] qa)
	{
		for(int i=0;i<qa.length;i++)
		{
			int firstLimit=qa[i].indexOf("->"), lastLimit=qa[i].lastIndexOf("->");
			String key=qa[i].substring(0, firstLimit), question=qa[i].substring(firstLimit+2, lastLimit);
			String[] answers=qa[i].substring(lastLimit+2).split(",");
			allNode.put(key, new Node(question, answers));
		}
	}

	private void changePosition(String key)
	{
		before=actual;
		actual=allNode.get(key.replace(" ", "").toLowerCase());
	}

	private String getAnswers(String name)
	{
		String answer=actual.print(name);
		if(actual.isTerminal)
		{
			changePosition(actual.answers[0]);
			answer+=" "+actual.print(name);
		}
		return answer;
	}

	String getAnswers(String key, String name)
	{
		changePosition(key);
		return getAnswers(name);
	}
}