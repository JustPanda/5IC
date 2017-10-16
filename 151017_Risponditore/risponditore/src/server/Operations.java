package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

class Operations implements Type
{
	private PrintWriter pw=null;
	private HashMap<String, BiFunction<Product, Receipt, Void>> exec=new HashMap<>();
	private HashMap<String, Function<Object[], String>> printToClient=new HashMap<>();

	Operations(String path)
	{
		try{
			this.pw=new PrintWriter(new FileOutputStream(new File(path), true));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		this.exec.put(ITEM_TYPE, (Product product, Receipt receipt) -> {
			receipt.addProduct(product);
			return null;
		});
		this.exec.put(INSERT_TYPE, (Product product, Receipt receipt) -> {
			receipt.removeProduct(product);
			return null;
		});
		this.printToClient.put(CHOOSE_TYPE, (object) -> {
			PrintWriter out=(PrintWriter) object[1];
			Node actual=(Node) object[2];
			HashMap<String, Product> products=(HashMap<String, Product>) object[3];
			String[] prices=new String[actual.getAnswers().length], texts=((String[] )object[5]);
			for(int i=0;i<prices.length;i++)
			{
				Product product=products.get(actual.getAnswers()[i]);
				if(product!=null)
				{
					prices[i]=String.format( "%.2f", product.getPrice());
				}
			}
			out.println(String.format(actual.getQuestion(), texts[0], texts[1]));
			out.println(arrayToString(actual.getAnswers()));
			out.println(arrayToString(prices));
			return actual.getType();
		});
		this.printToClient.put(ITEM_TYPE, (object) -> {
			PrintWriter out=(PrintWriter) object[1];
			Node actual=(Node) object[2];
			HashMap<String, Product> products=(HashMap<String, Product>) object[3];
			out.println(String.format(actual.getQuestion(), products.get(actual.getKey()).getPrice()));
			return actual.getKey();
		});
		this.printToClient.put(INSERT_TYPE, (object) -> {
			String keyProduct=null;
			BufferedReader in=(BufferedReader) object[0];
			PrintWriter out=(PrintWriter) object[1];
			Node actual=(Node) object[2];
			HashMap<String, Product> products=(HashMap<String, Product>) object[3];
			out.println(actual.getQuestion());
			try{
				while(products.get((keyProduct=in.readLine()))==null)
				{
					out.println(false);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			out.println(true);
			return keyProduct;
		});
		this.printToClient.put(SHOW_TYPE, (object) -> {
			PrintWriter out=(PrintWriter) object[1];
			Node actual=(Node) object[2];
			Receipt receipt=(Receipt) object[4];
			out.println(String.format(actual.getQuestion(), receipt.getPrice()));
			out.println(receipt.printElements(","));
			return actual.getType();
		});
		this.printToClient.put(EXIT_TYPE, (object) -> {
			PrintWriter out=(PrintWriter) object[1];
			Node actual=(Node) object[2];
			out.println(String.format(actual.getQuestion(), ((String[] )object[5])[0]));
			return actual.getType();
		});
	}

	private String arrayToString(String[] elements)
	{
		StringBuilder stringElements=new StringBuilder("");
		for(String element: elements) stringElements.append(element+",");
		return stringElements.toString();
	}

	synchronized void writeOrder(String order)
	{
		pw.write(order+"\n\n");
		pw.flush();
	}

	BiFunction<Product, Receipt, Void> getExec(String type)
	{
		return exec.get(type);
	}

	Function<Object[], String> getPrintToClient(String type)
	{
		return printToClient.get(type);
	}

	String getDistanceAndTime(String origin, String destitation)
	{
		String apiKey="AIzaSyDAV7Mtlrm06HhBlFmMHS5BSU-nGU1Bgl8";
		String toReturn=null;
		try{
			URL url=new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+origin+"&destinations="+destitation+"&key="+apiKey);
			HttpURLConnection con= (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			int statuscode=con.getResponseCode();
			if(statuscode==HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}
				String json = sb.toString();
				JSONParser parser = new JSONParser();
				JSONObject root = (JSONObject) parser.parse(json);
				JSONArray array_rows = (JSONArray) root.get("rows");
				JSONObject object_rows = (JSONObject) array_rows.get(0);
				JSONArray array_elements = (JSONArray) object_rows.get("elements");
				JSONObject object_elements = (JSONObject) array_elements.get(0);
				if(object_elements.get("status").equals("OK"))
				{
					JSONObject object_duration = (JSONObject) object_elements.get("duration");
					toReturn=object_duration.get("text").toString();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}catch(ParseException e){
			e.printStackTrace();
		}
		return toReturn;
	}
}
