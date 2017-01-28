package ems.web;

import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JsonCreator {
	public static void main(String[] args){
		ResultSet courseList;
		ResultSet instanceList;
		try{
			Connection connection = DriverManager.getConnection("jdbc:sqlite:db/lifetracker.db");
			Statement courseListStatement = connection.createStatement();  
			Statement instanceStatement = connection.createStatement();
	        
			/*
			courseList = courseListStatement  
	                 .executeQuery("SELECT DISTINCT name FROM metrics WHERE name LIKE 'CS%'");
			
			JSONObject coursesObj = new JSONObject();
			while(courseList.next()){
				String course = courseList.getString("name");
				instanceList = instanceStatement
						.executeQuery("SELECT name, date, count FROM instances WHERE name LIKE '"+ course +"'");
				
				JSONArray instances = new JSONArray();  
		        while(instanceList.next()){
		        	JSONObject instance = new JSONObject();
		        	instance.put("date", instanceList.getString("date"));
		        	instance.put("hours", instanceList.getString("count"));
		        	instances.add(instance);
				}
		        coursesObj.put(course, instances);
			}
	        */
  			
			JSONObject coursesObj = new JSONObject();
			instanceList = instanceStatement
					.executeQuery("SELECT name, date, count FROM instances WHERE name LIKE 'CS%'");
			JSONArray instances = new JSONArray();  
	        while(instanceList.next()){
	        	JSONObject instance = new JSONObject();
	        	instance.put("name", instanceList.getString("name"));
	        	instance.put("date", instanceList.getString("date"));
	        	instance.put("hours", instanceList.getString("count"));
	        	instances.add(instance);
			}
			coursesObj.put("data", instances);

            // Writing to a file  
            File file=new File("files/omscs-info.json");  
            file.createNewFile();  
            FileWriter fileWriter = new FileWriter(file);  
            System.out.println("Writing JSON object to file");  
            System.out.println("-----------------------");  
            System.out.print(coursesObj);  

            fileWriter.write(coursesObj.toJSONString());  
            fileWriter.flush();  
            fileWriter.close();  
	        
		}catch(SQLException e){
			System.out.println("sql exception: "+ e);
		}catch(IOException e){
			System.out.println("io exception:"+ e);
		}
	}
}
