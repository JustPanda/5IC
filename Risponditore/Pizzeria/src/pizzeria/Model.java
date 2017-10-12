package pizzeria;

import java.util.ArrayList;
import java.util.Scanner;

class Nodo
{
    String info;
    Nodo parent;
    ArrayList<Nodo> children;
    Nodo left;
    Nodo right;
    Nodo root;
    String id;
    
    public Nodo(){}
    
    public Nodo(String id, String info)
    {
        this.id = id;
        this.info = info;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.children = null;
        this.root = null;
    }
}

class Layer
{
    ArrayList<Nodo> nodes;
    
    public Layer(){}
    
    public Layer(int NodesNumber)
    {
        nodes = new ArrayList<>();
        for(int i=0; i<NodesNumber; i++)
        {
            nodes.add(new Nodo());
        }
    }
}

public class Model
{            
    static ArrayList<String> questions;
    static ArrayList<String> Id;
    ArrayList<Layer> layers;
    
    public Model()
    {
        questions = new ArrayList<>();
        layers = new ArrayList<>();
        Id = new ArrayList<>();
    }
    public void Setup()
    {
        questions.add("Vuole ordinare qualcosa?(Si|No)");
        questions.add("Cosa desidera ordinare?(Organi vitali|Organi secondari)");
        questions.add("Arrivederci");
        questions.add("Che Organo vitale vuole?(Cuore|Cervello)");
        questions.add("Che Organi secondari vuole?(Rene|Polmoni)");
        questions.add("Vuole altro?(Si|No)");
        Id.add("root");
        Id.add("Si");
        Id.add("No");
        Id.add("Organi vitali");
        Id.add("Organi secondari");
        Id.add("Other");
    }
    
    public void AddLayer(int NumNodes)
    {
        this.layers.add(new Layer(NumNodes));
    }
    
    public void Build()
    {
        AddLayer(1);
        AddLayer(2);
        AddLayer(2);
        AddLayer(1);
        
        this.layers.get(0).nodes.get(0).id = Id.get(0);
        this.layers.get(0).nodes.get(0).info = questions.get(0);
        
        for(int i=0; i<this.layers.get(1).nodes.size(); i++)
        {
            this.layers.get(1).nodes.get(i).id = Id.get(i+1);
            this.layers.get(1).nodes.get(i).info = questions.get(i+1);
        }
        
        for(int i=0; i<this.layers.get(2).nodes.size(); i++)
        {
            this.layers.get(2).nodes.get(i).id = Id.get(i+3);
            this.layers.get(2).nodes.get(i).info = questions.get(i+3);
        }
        
        for(int i=0; i<this.layers.get(3).nodes.size(); i++)
        {
            this.layers.get(3).nodes.get(i).id = Id.get(i+5);
            this.layers.get(3).nodes.get(i).info = questions.get(i+5);
        }        
    }
    
    public void Assign()
    {
        for(int i=1; i<this.layers.size(); i++)
        {
            if(this.layers.get(i).nodes.size() == 2)
            {
                for(int j = 0; j<this.layers.get(i-1).nodes.size(); j++)
                {
                    this.layers.get(i-1).nodes.get(j).right = this.layers.get(i).nodes.get(0);
                    this.layers.get(i-1).nodes.get(j).left = this.layers.get(i).nodes.get(1); 
                }
            }
            else
            {
                if(this.layers.get(i).nodes.size() > 2)
                {
                    for(int k=0; k<layers.get(i).nodes.size(); k++)
                    {
                        for(int m=0; m<layers.get(i-1).nodes.size(); m++)
                        {
                            this.layers.get(i-1).nodes.get(m).children.add(layers.get(i).nodes.get(k));
                        }
                    }
                }
                else
                {
                    if(this.layers.get(i).nodes.size() == 1)
                    {
                        for(int n = 0; n<this.layers.get(i-1).nodes.size(); n++)
                        {
                            this.layers.get(i-1).nodes.get(n).right = this.layers.get(i).nodes.get(0);
                            this.layers.get(i-1).nodes.get(n).left = this.layers.get(i).nodes.get(0); 
                        }
                    }
                }
            }
        }
    }
    
    public String GetRightQuestion(Nodo unit)
    {
        return unit.right.info;
    }
    
    public String GetLeftQuestion(Nodo unit)
    {
        return unit.left.info;
    }
	
	public String GetQuestion(Nodo unit, int index)
	{
		return unit.children.get(index).info;
	}
    
    public String Intro()
    {
        return this.layers.get(0).nodes.get(0).info;
    }
    
    public String Decide(String question, String answer)
    {
        Nodo help;
        if(question.toLowerCase().equals(this.Intro().toLowerCase()) && answer.toLowerCase().equals("si"))
        {
            help = this.Search(question);
            return this.GetRightQuestion(help);
        }
        else
        {
            if(question.toLowerCase().equals(this.Intro().toLowerCase()) && answer.toLowerCase().equals("no"))
            {
                help = this.Search(question);
                return this.GetLeftQuestion(help);
            }
        }
        
        if(question.toLowerCase().equals("Cosa desidera ordinare?(Organi vitali|Organi secondari)".toLowerCase()) && answer.toLowerCase().equals("organi vitali"))
        {
            help = this.Search(question);
            return this.GetRightQuestion(help);
        }
        else
        {
            if(question.toLowerCase().equals("Cosa desidera ordinare?(Organi vitali|Organi secondari)".toLowerCase()) && answer.toLowerCase().equals("organi secondari"))
            {
                help = this.Search(question);
                return this.GetLeftQuestion(help);
            }
        }
        
        if(question.toLowerCase().equals("Che Organo vitale vuole?(Cuore|Cervello)".toLowerCase()) && answer.toLowerCase().equals("cuore"))
        {
            help = this.Search(question);
            return this.GetRightQuestion(help);
        }
        else
        {
            if(question.toLowerCase().equals("Che Organo vitale vuole?(Cuore|Cervello)".toLowerCase()) && answer.toLowerCase().equals("cervello"))
            {
                help = this.Search(question);
                return this.GetRightQuestion(help);
            }
        }
        
        if(question.toLowerCase().equals("Vuole altro?(Si|No)".toLowerCase()) && answer.toLowerCase().equals("si"))
        {
            help = this.Search(this.Intro());
            return this.GetRightQuestion(help);
        }
        else
        {
            if(question.toLowerCase().equals("Vuole altro?(Si|No)".toLowerCase()) && answer.toLowerCase().equals("no"))
            {
                help = this.Search(this.Intro());
                return this.GetLeftQuestion(help);
            }
        }
        return null;
    }
    
    public Nodo Search(String info)
    {
        for(int i=0; i<this.layers.size(); i++)
        {
            for(int j=0; j<this.layers.get(i).nodes.size(); j++)
            {
                if(this.layers.get(i).nodes.get(j).info.toLowerCase().equals(info.toLowerCase()))
                {
                    return this.layers.get(i).nodes.get(j);
                }
            }
        }
        return null;
    }
}
