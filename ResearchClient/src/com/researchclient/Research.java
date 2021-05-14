package com.researchclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Research extends DbConnection {


	//Read Research
	public String getResearch() {
		
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			// Prepare the HTML table to be displayed
			output = "<table  class='table table-dark table-striped'><tr>"
					+ "<th>Research ID</th>"
					+"<th>Researcher ID</th>"
					+ "<th>Research Name</th>"
					+ "<th>Description</th>"
					+ "<th>Category</th>"
					+ "<th>Expected Budget</th>"
					+ "<th>Update</th>"
					+ "<th>Delete</th></tr>";
			
			

			
			// sql query
			String query = " select * from research";

			// create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();
			
			//binding values
			JsonArray resultArray = new JsonArray();
			while(rs.next()) {
				
				int research_id = rs.getInt("research_id") ;
				int researcher_id = rs.getInt("researcher_id") ;
				String research_name= rs.getString("research_name") ;
				String description= rs.getString("description") ;
				String category= rs.getString("category") ;
				double expected_budget= rs.getDouble("expected_budget") ;
				
				
		
				// Add a row into the HTML table
				output += "<tr><td>" + research_id + "</td>";
				output += "<td>" + researcher_id + "</td>";
				output += "<td>" + research_name + "</td>"; 
				output += "<td>" + description + "</td>";
				output += "<td>" + category + "</td>";
				output += "<td>" + expected_budget + "</td>";
				

				// buttons
				output += "<td><input name='btnUpdate' id='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-researchid='" + research_id + "'></td>"
						+"<td><input name='btnDelete' id='btnDelete' type='button' value='Delete' class='btnDelete btn btn-danger' data-researchid='" + research_id + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the HTML table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the research.";
			System.err.println(e.getMessage());
		}
		return output;
	}


	//insert research
	public JsonObject insertResearch( int researcher_id , String name, String description,String category,double budget)
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error while connecting to the database for inserting.");
				return result;
			}


			// sql query
			String query = " insert into research"+
					"(`researcher_id`,`research_name`,`description`,`category`,`expected_budget`)"
					+ " values (?, ?, ?, ?, ?)";

			// create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query);


			// binding values
			preparedStmt.setInt(1, researcher_id);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, description);
			preparedStmt.setString(4, category);
			preparedStmt.setDouble(5,budget);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			result = new JsonObject();
			result.addProperty("status", "successfull");
			result.addProperty("data", getResearch());
		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("status", "exception");
			result.addProperty("data", "exception occured while inserting data.");
			System.err.println(e.getMessage());
		}
		return result;
	} 
	
	//update research
	public JsonObject updateResearch( int research_id, int researcher_id , String name, String description,String category,double budget)
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error while connecting to the database for updating.");
				return result;
			}


			//sql query
			String query = "UPDATE research SET researcher_id=?,research_name=?,description=?,category=?,expected_budget=? WHERE research_id=?";

			// create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query);


			// binding values
			preparedStmt.setInt(1, researcher_id);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, description);
			preparedStmt.setString(4, category);
			preparedStmt.setDouble(5,budget);
			preparedStmt.setInt(6, research_id);
			
			// execute the statement
			int status = preparedStmt.executeUpdate();
			con.close();
			
			result = new JsonObject();
			if(status > 0) {
				result.addProperty("status", "successfull");
				result.addProperty("data", getResearch());
				
			}else {
				result.addProperty("status", "failed");
				result.addProperty("data", getResearch());
			}
				
		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("status", "exception");
			result.addProperty("data", "exception occured while updating research.");
			System.err.println(e.getMessage());
		}
		return result;
	} 
	
	
	
	//delete research
	public JsonObject deleteResearch( int research_id )
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error while connecting to the database for deleting.");
				return result;
			}


			// sql query
			String query = "delete from research where research_id=?";
					 
			// create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query);


			// binding values
			preparedStmt.setInt(1, research_id);

			
			// execute the statement
			int status = preparedStmt.executeUpdate();
			con.close();
			
			result = new JsonObject();
			if(status > 0) {
				result.addProperty("status", "successfull");
				result.addProperty("data", getResearch());
				
			}else {
				result.addProperty("status", "failed");
				result.addProperty("data", getResearch());
			}
		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("status", "exception");
			result.addProperty("data", "exception occured while deleting research.");
			System.err.println(e.getMessage());
		}
		return result;
	} 
	
	
	//get a single record
	public JsonObject getsingleResearch(int research_id) {
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("ERROR", "Error while connecting to the database for inserting."); 
			}


			//sql query
			String query = " select * from research where research_id=?";

			// create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, research_id);
			ResultSet rs = preparedStmt.executeQuery();
			
			// binding values
			JsonArray resultArray = new JsonArray();
			while(rs.next()) {
				JsonObject jsonRow = new JsonObject();
				jsonRow.addProperty("research_id", rs.getInt("research_id") );
				jsonRow.addProperty("researcher_id", rs.getInt("researcher_id") );
				jsonRow.addProperty("research_name", rs.getString("research_name") );
				jsonRow.addProperty("description", rs.getString("description") );
				jsonRow.addProperty("category", rs.getString("category") );
				jsonRow.addProperty("expected_budget", rs.getDouble("expected_budget") );
				resultArray.add(jsonRow);
				
			}
			result = new JsonObject();
			result.add("research", resultArray);
			
			con.close();
		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("EXCEPTION", "Error occured while reading research");
			System.err.println(e.getMessage());
		}
		return result;
	}
	
	


}
