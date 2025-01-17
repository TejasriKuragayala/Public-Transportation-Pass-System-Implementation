package com.amazon.buspassmanagement.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// To connect and to execute SQL Statements in Database MySQL or MSSQL :)

/*
 	
 	JDBC Procedure:
 		1. Load the Driver
 			1.1 Add the DataBase Library in your project
 			1.2 Use API -> Class.forname
 		
 		2. Connect to DataBase
 			URL
 			Username
 			Password
 			API -> DriverManager.getConnection(....)
 			API -> Connection
 	
 		3. Execute SQL Query
 			API -> Using the API Statment
 		
 		4. Close Connection
 */

public class DB {
	
//	public static String FILEPATH = "/Users/ishant/Downloads/AmazonAtlas22/buspassmanagement/src/main/java/dbconfig.txt";
//	public static String URL = "jdbc:mysql://localhost/buspassdb";
//	public static String USER = "root";
//	public static String PASSWORD = "";
	
	Connection connection;  // API from JDBC Package to store connection :)
	Statement statement;	// API from JDBC Package to execute SQL Statements :)

	private static DB db = new DB();
	
	public static DB getInstance() {
		return db;
	}
	
	private DB() {
		
		try {
			
			
			// MS-SQL 
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			System.out.println("[DB] Driver Loaded Successfully....");
			
			createConnection();
			
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
	
	}
	
	private void createConnection() {
		try {

			
			// MS-SQL
			String url = "jdbc:sqlserver://localhost:1433;databaseName=buspass;user=amazon;password=divyam@123;trustServerCertificate=true";
			connection = DriverManager.getConnection(url);
			System.out.println("Connection created");
			
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
	}
	
	public int executeSQL(String sql) {
		
		int result = 0;
		
		try {
			System.out.println("[DB] Executing SQL Query | "+sql);
			statement = connection.createStatement();
			result = statement.executeUpdate(sql); // executeUpdate -> is used to perform insert/update/delete in table
			System.out.println("[DB] SQL Query Executed...");
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
		
		return result;
	}
	
	
	public ResultSet executeQuery(String sql) {
		
		ResultSet set = null;
		
		try {
			System.out.println("[DB] Executing SQL Query | "+sql);
			statement = connection.createStatement();
			set = statement.executeQuery(sql); // which will retrieve data from the table into java application
			System.out.println("[DB] SQL Query Executed...");
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
		
		return set;
	}
	
	public void closeConnection() {
		try {
			connection.close();
			System.out.println("[DB] Connection Closed...");
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
	}
	
}
