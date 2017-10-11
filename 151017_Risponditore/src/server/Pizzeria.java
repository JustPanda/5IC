package server;

import java.util.HashMap;

class Pizzeria
{
	private HashMap<String, Node> allNode=new HashMap<>();
	private Node root, actual;

	Pizzeria(String[] qa)
	{
		Node actual=root;
		for(int i=0;i<qa.length;i++)
		{
			int firstLimits=qa[i].indexOf("->"), lastLimits=qa[i].lastIndexOf("->");
			String key=qa[i].substring(0, firstLimits), question=qa[i].substring(firstLimits+2, lastLimits);
			String[] answers=qa[i].substring(lastLimits+2).split(",");
			allNode.put(key, new Node(question, answers));
		}
	}

	boolean isAnswer(String key)
	{
		boolean notExist=true;
		for(int i=0;(notExist=i<actual.answers.length)&&!key.equals(actual.answers[i]);i++);
		return !notExist;
	}

	String getAnswers(String key, String name)
	{
		actual=allNode.get(key.replace(" ", "").toLowerCase());
		return actual.print(name);
	}
}