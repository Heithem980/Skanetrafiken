package API;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GetBusDetails {
	
	
	
	public String URL = "http://www.labs.skanetrafiken.se/v2.2/stationresults.asp?selPointFrKey=80000";
	
	private String busNum;
	private String busTime;
	


	
	public String getAPI(){
		
		
		
		try {
			
			URL line = new URL(URL);
			
			HttpURLConnection linec = (HttpURLConnection) line.openConnection();
			
			linec.setDoInput(true);
			linec.setDoOutput(true);
			
			linec.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader (new InputStreamReader(linec.getInputStream()));
			
			String inputLine;
			
			String APIresponse = "";
			
			
			while((inputLine = in.readLine()) != null) {
				APIresponse += inputLine;
			}
			in.close();
			
			Document doc = convertStringToXLMDocument(APIresponse);
			
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("Line");
			
			for(int i = 0 ; i < nList.getLength() ; i++) {
				Node node = nList.item(i);
				
				if (node.hasChildNodes()){
				
					NodeList childList = node.getChildNodes();	
				
				    for(int x = 0 ; x < childList.getLength() ; x++) {
					  Node childNode = childList.item(x);
					  
					  
					  
					  if(childNode.getNodeName() == "No") {
						  
						  
						  
						  
						  if(busNum.equals(childNode.getTextContent())) {
							  
							  busTime = childList.item(2).getTextContent();
							  
							  return childList.item(7).getTextContent();
							  
						  }
						  
						}  
					 }	  
					}   
					
				}	  
				     
				  
					
				
			  
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String empty = "This bus does not exist.";
		return  empty;
		
		
		
		
		
	}


	

	



	public String getBusTime() {
		return busTime;
	}








	private Document convertStringToXLMDocument(String aPIresponse) {
		// TODO Auto-generated method stub
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = null;
		
		try {
			builder = factory.newDocumentBuilder();
			
			Document doc = builder.parse(new InputSource(new StringReader(aPIresponse)));
			
			return doc;
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public String getBusNum() {
		return busNum;
	}



	public void setBusNum(String busNum) {
		this.busNum = busNum;
	}


	

}
