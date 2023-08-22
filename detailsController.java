package com.example.demo.detail;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.mongoc.mongoconnection;
import com.example.demo.repo.detailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;


@RestController
@RequestMapping(path = "/")
public class detailsController {

	 
	
       

     
	
	private final detailsService dservice;
	static mongoconnection conn = new mongoconnection();
	static MongoCollection<Document> collection = conn.getconn("SH");
	
	private static Map<String, Integer> hallCapacities = new HashMap<>();
//	 hallCapacities.put("Hall-A", 50);
//     hallCapacities.put("Hall-B", 80);
//     hallCapacities.put("Hall-C", 100);
//     hallCapacities.put("Hall-D", 60);
//     hallCapacities.put("Hall-E", 120);
//     hallCapacities.put("Hall-F", 150);
	 
	@Autowired
	detailsRepository repo;
	
	

	public detailsController(detailsService dservice) {

		this.dservice = dservice;
	}
	
	@GetMapping("/details")
	public List<details> getdetails(){
		
		return repo.findAll();
	}
	
	
	@PostMapping("/postd")
	public details adddetails(@RequestBody details detail ) {
		
		System.out.print(detail.getStartDate());
//		return detail;
		return repo.save(detail);
		
		
	}
	
	
	
//	@GetMapping("/read/")
	 public static Sdetails[] readjson() {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Sdetails[] detaillist = null ;
	        try {
	            File jsonFile = new File("C:\\Users\\akadi\\OneDrive\\Documents\\sts_workspace\\semi_rest\\src\\main\\resources\\details.json");
	             detaillist = objectMapper.readValue(jsonFile, Sdetails [].class);
//	            List<details> people = objectMapper.readValue(jsonFile, new TypeReference<List<details>>() {});

//	            System.out.println("start date is : " + detail.length);
//	            System.out.println("Age: " + person.getAge());
//	            System.out.println("Email: " + person.getEmail());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return detaillist;
     }  
  
	
	
	@GetMapping("/getinr/{startDate}/{endDate}")
	public String bookedHallOnDat(@PathVariable String startDate,@PathVariable String endDate) {
		// TODO Auto-generated method stub
		 
		MongoCollection<Document> collection = conn.getconn("SH");
		 LocalDateTime sd = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
	     LocalDateTime ed = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
		Bson filt = Filters.and(Filters.gte("startDate",sd ), Filters.lte("endDate", ed));
		System.out.println("start date is "+sd);
		System.out.println("end date is "+ed);
		MongoCursor<Document> cursor = collection.find(filt).iterator();
//		List<String> al = new ArrayList<Document>();
		JSONArray al = new JSONArray();
//		Gson gso = new Gson();
		 while(cursor.hasNext()) {
//			 String ab = cursor.next().getString("hallName");
			 String ne = cursor.next().toJson();
			 System.out.println("ne is "+ne);
			 
//			    JSONObject temp = 
			
//		        System.out.println("ne is "+ne);
//			 String jsonString = gso.toJson(cursor.next());
		        al.put(ne);
		        
		    
		    }
//		 System.out.println(al.toString());
//		 System.out.println(al.toString());
//		 JSONArray jA = new JSONArray(al);
//	        JSONObject jo = al.getJSONObject(0);
//	        String capacit = jo.getString("capacity");
//	        System.out.println(capacit);
		 System.out.println(al);
		 
		 return al.toString();
//		 return startDate;
		 
//

//		
	}
	
//	@GetMapping("/viewvacant/{startDate}/{endDate}/{capacity}")
//	private static String viewVacantOnDatee(@PathVariable String startDate,@PathVariable String endDate,@PathVariable String capacity) {
//		// TODO Auto-generated method stub
//		
//		
//		
//		Map<String, Boolean> status = new HashMap<>();
//		Map<String, String>  res = new HashMap<>();
//		
//		for(String hall : hallCapacities.keySet()) {
//		status.put(hall,false);
//		}
//		MongoCollection<Document> collection = conn.getconn("SH");
//		 LocalDateTime sd = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));;
//	     LocalDateTime ed = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));;
//		
//		
//		ArrayList<String> hallList = new ArrayList<String>();
//	
//		
//		Bson filt = Filters.and(Filters.eq("startDate",sd ), Filters.eq("endDate", ed));
//		System.out.println("start date is "+sd);
//		System.out.println("end date is "+ed);
//		MongoCursor<Document> cursor = collection.find(filt).iterator();
//		
//		 while(cursor.hasNext()) {
//			    String j = (String) cursor.next().get("hallName");
//			    if(Integer.parseInt(capacity) <= hallCapacities.get(j)) {
//
//			    status.replace(j, true);
//			    
//			    }
//		        
//		    }
//		 
//		 JSONArray hl = new JSONArray();
//
//		 for(String hall : hallCapacities.keySet()) {
//			 if(status.get(hall) == false) {
//				hallList.add(hall);
//				hl.put(hall);}
//				}
//	
//		 JSONObject obj = new JSONObject();
//		 obj.put("startDate ",startDate.toString());
//		 obj.put("endDate", endDate.toString());
//		 obj.put("capacity : ", capacity);
////		 System.out.println(obj);
//		 obj.put("available halls :", hallList.toString());
////		 System.out.println(obj);
//		 
////		 GSON a = new GSON();
//		 Gson gson = new Gson();
//		 String jsonArray = gson.toJson(hallList);
////		 details deserializedUser = gson.fromJson(obj, details.class);
//		 
////		 obj.put("json one",jsonArray);
//		 
//		 JSONArray ress = new JSONArray();
//		 ress.put(obj);
//	
//		 
//		 JSONObject ob = new JSONObject();
//		 
//		 String s = "slash";
//		 ob.put(s,jsonArray.toString());
////		 System.out.println(obj);
////		 System.out.println(hl);
////		 System.out.println(ress);
//		 return ress.toString();
//
//		
//		
//	}
//
	
	@PostMapping("/sav/{startDate}/{endDate}/{capacity}/{hallName}")
	private static BsonValue bookHall(@PathVariable String startDate, @PathVariable String endDate, @PathVariable String capacity, @PathVariable String hallName) {
		// TODO Auto-generated method stub
		
		    hallCapacities.put("Hall-A", 50);
	        hallCapacities.put("Hall-B", 80);
	        hallCapacities.put("Hall-C", 100);
	        hallCapacities.put("Hall-D", 60);
	        hallCapacities.put("Hall-E", 120);
	        hallCapacities.put("Hall-F", 150);
	        
		
		int capacityy = Integer.parseInt(capacity);  
		
		
		System.out.println("startDate is : "+startDate);
		System.out.println("hallName2: " );
		LocalDateTime sd = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
	     LocalDateTime ed = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
		Bson filter = Filters.and(Filters.and(Filters.gte("startDate",sd ), Filters.lte("endDate",ed)),Filters.eq("hallName",hallName));
		MongoCursor<Document> cursor = collection.find(filter).iterator();
		BsonValue id = null;
		
//		while(cursor.hasNext()) {
//	        System.out.println(cursor.next().toJson());
//	    }
		if(cursor.hasNext() ) {
			
			System.out.println("Try again with differnt input, the hall is already booked on the demanded date" );
//			System.out.println(cursor.next().toJson());
		}
		
		else{
//			System.out.println("Try again with differnt input" );
			if(capacityy <= hallCapacities.get(hallName)  )
			{
			Document doc = new Document().append("startDate",startDate).append("endDate",endDate).append("capacity",capacity).append("hallName",hallName);
			InsertOneResult result = collection.insertOne(doc);
					
			id = result.getInsertedId();
//			return id;
//			System.out.println("id created: " +id);
			}
			else {
				System.out.println("Required hall capacity is less than you demanded");
			}
			
		}
		return id;
		
	}
	
	@GetMapping("/getbdinr/{startDate}/{endDate}")
	private static String bookedHallOnDate(@PathVariable String startDate, @PathVariable String endDate) {
		// TODO Auto-generated method stub
		
		Bson filt = Filters.and(Filters.gte("startDate",startDate ), Filters.lte("endDate", endDate));
		System.out.println("start date is "+startDate);
		System.out.println("end date is "+endDate);
		MongoCursor<Document> cursor = collection.find(filt).iterator();
//		List<String> al = new ArrayList<Document>();
		JSONArray al = new JSONArray();
//		Gson gso = new Gson();
		 while(cursor.hasNext()) {
//			 String ab = cursor.next().getString("hallName");
			  Document c = cursor.next();
			 JSONObject a = new JSONObject();
			 String sd = c.getString("startDate");
			 String ed = c.getString("endDate");
			 String cap =  c.getString("capacity");
			 String h =  c.getString("hallName");
			 
			 a.put("startDate ",sd);
			 a.put("endDate", ed);
			 a.put("capacity : ", cap);
//			 System.out.println(obj);
			 a.put("hallName ", h);
			 
			 
//			    JSONObject temp = 
			
//		        System.out.println("ne is "+ne);
//			 String jsonString = gso.toJson(cursor.next());
//			 l.add(null);
		        al.put(a);  
		    
		    }
//		 System.out.println(al.toString());
//		 System.out.println(al.toString());
//		 JSONArray jA = new JSONArray(al);
//	        JSONObject jo = al.getJSONObject(0);
//	        String capacit = jo.getString("capacity");
//	        System.out.println(capacit);
		 System.out.println(al);
		 return al.toString();
//

//		
	}
	
	@GetMapping("/viewv/{startDate}/{endDate}/{capacity}")
	private static String viewVacantOnDatee(@PathVariable String startDate, @PathVariable String endDate, @PathVariable String capacity) {
		// TODO Auto-generated method stub
		hallCapacities.put("Hall-A", 50);
        hallCapacities.put("Hall-B", 80);
        hallCapacities.put("Hall-C", 100);
        hallCapacities.put("Hall-D", 60);
        hallCapacities.put("Hall-E", 120);
        hallCapacities.put("Hall-F", 150);
		
		
		Map<String, Boolean> status = new HashMap<>();
		Map<String, String>  res = new HashMap<>();
		
		for(String hall : hallCapacities.keySet()) {
		status.put(hall,false);
		}
		
		
		
		ArrayList<String> hallList = new ArrayList<String>();
	
		
		Bson filt = Filters.and(Filters.eq("startDate",startDate ), Filters.eq("endDate", endDate));
		System.out.println("start date is "+startDate);
		System.out.println("end date is "+endDate);
		MongoCursor<Document> cursor = collection.find(filt).iterator();
		
		 while(cursor.hasNext()) {
			    String j = (String) cursor.next().get("hallName");
			    if(Integer.parseInt(capacity) <= hallCapacities.get(j)) {

			    status.replace(j, true);
			    
			    }
		        
		    }
		 
		 JSONArray hl = new JSONArray();

		 for(String hall : hallCapacities.keySet()) {
			 if(status.get(hall) == false) {
				hallList.add(hall);
				hl.put(hall);}
				}
	
		 JSONObject obj = new JSONObject();
		 obj.put("startDate ",startDate.toString());
		 obj.put("endDate", endDate.toString());
		 obj.put("capacity : ", capacity);
//		 System.out.println(obj);
		 obj.put("available halls :", hallList.toString());
//		 System.out.println(obj);
		 
//		 GSON a = new GSON();
		 Gson gson = new Gson();
		 String jsonArray = gson.toJson(hallList);
//		 details deserializedUser = gson.fromJson(obj, details.class);
		 
//		 obj.put("json one",jsonArray);
		 
		 JSONArray ress = new JSONArray();
		 ress.put(obj);
	
		 
		 JSONObject ob = new JSONObject();
		 
		 String s = "slash";
		 ob.put(s,jsonArray.toString());
//		 System.out.println(obj);
//		 System.out.println(hl);
//		 System.out.println(ress);
		 return ress.toString();

		
		
	}

	//without path variable
	 void  bookHallwopv( String startDate, String endDate, String capacity, String hallName) {
		// TODO Auto-generated method stub
		
		    hallCapacities.put("Hall-A", 50);
	        hallCapacities.put("Hall-B", 80);
	        hallCapacities.put("Hall-C", 100);
	        hallCapacities.put("Hall-D", 60);
	        hallCapacities.put("Hall-E", 120);
	        hallCapacities.put("Hall-F", 150);
	        
		
		int capacityy = Integer.parseInt(capacity);  
		
		
		System.out.println("startDate is : "+startDate);
		System.out.println("hallName2: " );
		LocalDateTime sd = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
	     LocalDateTime ed = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
		Bson filter = Filters.and(Filters.and(Filters.gte("startDate",sd ), Filters.lte("endDate",ed)),Filters.eq("hallName",hallName));
		MongoCursor<Document> cursor = collection.find(filter).iterator();
		BsonValue id = null;
		
//		while(cursor.hasNext()) {
//	        System.out.println(cursor.next().toJson());
//	    }
		if(cursor.hasNext() ) {
			
			System.out.println("Try again with differnt input, the hall is already booked on the demanded date" );
//			System.out.println(cursor.next().toJson());
		}
		
		else{
//			System.out.println("Try again with differnt input" );
			if(capacityy <= hallCapacities.get(hallName)  )
			{
			Document doc = new Document().append("startDate",startDate).append("endDate",endDate).append("capacity",capacity).append("hallName",hallName);
			InsertOneResult result = collection.insertOne(doc);
					
			id = result.getInsertedId();
//			return id;
//			System.out.println("id created: " +id);
			}
			else {
				System.out.println("Required hall capacity is less than you demanded");
			}
			
		}
		 System.out.println("saved object id is:  "+id);
		
	}

	
	@PostMapping("/bookmultiple/")
	private static void bookmultiple() throws ParseException, FileNotFoundException {
		 

		     Sdetails[] detaillist = readjson();
		
	        int jarray_length = detaillist.length;
	 		System.out.println("length of json array : "+jarray_length);
	 		Sdetails det[] = new Sdetails[jarray_length];
	 		for(int i = 0; i < jarray_length;  i++ ) {
	 			det[i] = new Sdetails();
	 			
//	 			jsonObject
	 			
	 			String a = detaillist[i].getStartDate();
	 	        String b = detaillist[i].getEndDate();
	 	        det[i].capacity = detaillist[i].getCapacity();
	 	        det[i].hallName = detaillist[i].getHallName();
	 	        
//	 	       LocalDateTime sd = LocalDateTime.parse(a, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
//	 		   LocalDateTime ed = LocalDateTime.parse(b, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
	 	        

	 	        det[i].startDate = a;
	 	        det[i].endDate = b;
	 	        }
	 	        
	 		for(int i = 0; i < jarray_length; i = i+2 ) {
	 			System.out.println("index i is"+i);
	 			
	 			//Odd length
	 		if(jarray_length/2 ==1) {
	 			
	 			if(jarray_length-i != 1)
	 			{
	 				System.out.println("Hey even index exist");
	 				System.out.println("i is    "+i);
	 			multi_hall ob1 = new multi_hall(det[i], collection);
	 			multi_hall ob2 = new multi_hall(det[i+1], collection);
	 			
	 			Thread t1 = new Thread(ob1);
	 			Thread t2 = new Thread(ob2);
	 			
	 			t1.start();
	 			try {
	 				Thread.sleep(10);
	 			} catch (InterruptedException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	 			t2.start();
	 			}
	 			
	 			else {
	 				
	 				
	 				System.out.println("Hey odd index exist");
	 				multi_hall ob1 = new multi_hall(det[i], collection);
//	 				multi_hall ob2 = new multi_hall(det[i+1], collection);
	 				
	 				Thread t1 = new Thread(ob1);
	 				t1.start();
	 				
	 			}
	 			
	 			}
	 		
	 		else 
	 		{
	 			multi_hall ob1 = new multi_hall(det[i], collection);
	 			multi_hall ob2 = new multi_hall(det[i+1], collection);
	 			
	 			Thread t1 = new Thread(ob1);
	 			Thread t2 = new Thread(ob2);
	 			
	 			t1.start();
	 			try {
	 				Thread.sleep(10);
	 			} catch (InterruptedException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	 			t2.start();	
	 		}
	 		

	         
		
		
	}
	
	}

}
