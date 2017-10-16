package risponditore;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author tommasoquinto
 */
public class Decisioni {

    String file;
    Node<String> root;
    Scanner input;
    ArrayList<String> pietanze = new ArrayList<>();
    ArrayList<Double> prezzi = new ArrayList<>();
    static int tot = 0;

    public Decisioni(String file) {
        this.file = file;
        try {
            input = new Scanner(new FileReader(file + ".txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("non è stato trovato il file");
        }
    }

    public void add() {

        root = new Node("root");
        root.children.add(new Node("1"));
        root.children.add(new Node("2"));
        root.children.add(new Node("3"));
        String help = "2";

        while (!help.equals("") && input.hasNextLine()) {
            help = input.nextLine();
            root.children.get(0).children.add(new Node(help));
        }
        help = "2";
        while (!help.equals("") && input.hasNextLine()) {
            help = input.nextLine();
            root.children.get(1).children.add(new Node(help));
        }
        help = "2";
        while (!help.equals("") && input.hasNextLine()) {
            help = input.nextLine();
            root.children.get(2).children.add(new Node(help));
        }

    }

    public String print(String n) {
        String s = "";
        int conv = Integer.parseInt(n) - 1;
        for (int i = 0; i < root.children.get(conv).children.size() - 1; i++) {
            s += (root.children.get(conv).children.get(i).element) + "€, ";
        }
        return s;
    }

}
