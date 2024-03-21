package com.amazon.buspassmanagement.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import com.amazon.buspassmanagement.BusPassSession;
import com.amazon.buspassmanagement.db.BusPassDAO;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.controller.RepeatRouteException;


public class BusPassService {

	
	BusPassDAO passDAO = new BusPassDAO();
	
	// Create it as a Singleton 
	private static BusPassService passService = new BusPassService();
	Scanner scanner = new Scanner(System.in);
	
	public static BusPassService getInstance() {
		return passService;
	}
	
	private BusPassService() {
	
	}
	
	
	
	public void requestPass() throws RepeatRouteException {
		BusPassDAO dao = new BusPassDAO(); 
		BusPass pass = new BusPass();
		pass.getDetails(false);
		
		
		pass.uid = BusPassSession.user.id;
		
		pass.status = 1; 
		
		String sql = "SELECT * from BusPass where uid = "+pass.uid+" and routeId = "+pass.routeId+" and status <> 4";
		List<BusPass> objects = dao.retrieve(sql);
		
		int result1 = 0;
		for(BusPass object : objects) {
			result1++;
		}
		
		if (result1 != 0) {
			throw new RepeatRouteException ("A buss pass for this route already exists");
		}
    	
		
		int result = dao.insert(pass);
		String message = (result > 0) ? "Pass Requested Successfully" : "Request for Pass Failed. Try Again.."; 
		System.out.println(message);
	}
	
	public void deletePass() {
		BusPass pass = new BusPass();
		System.out.println("Enter Pass ID to be deleted: ");
		pass.id = scanner.nextInt();
		int result = passDAO.delete(pass);
		String message = (result > 0) ? "Pass Deleted Successfully" : "Deleting Pass Failed. Try Again.."; 
		System.out.println(message);
	}
	 public void expiredpass() {

		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");  
		    Date date = new Date();  
		    System.out.println(formatter.format(date));
			
			List<BusPass> objects = null;
//			Date current = new Date();
			
				String sql = "SELECT * from BusPass where validTill < '"+formatter.format(date)+"'";
				objects = passDAO.retrieve(sql);
		
			
			for(BusPass object : objects) {
				object.prettyPrint();
			}
	 }
	  public void daterange() {
//			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");  
//			    Date date = new Date();  
			  
			    System.out.println("Enter start date (yyyy-mm-dd): ");
				String requestedon = scanner.nextLine();
				System.out.println("Enter end date (yyyy-mm-dd): ");
				String validtill = scanner.nextLine();
				List<BusPass> objects = null;
				
				String sql = "SELECT * from BusPass where requestedOn >=  '"+requestedon+" 00:00:00' and validTill <= '"+validtill+" 00:00:00'";
				objects = passDAO.retrieve(sql);
				
				for(BusPass object : objects) {
					object.prettyPrint();
				}
				
	  }
	  
	  
     public void requestSuspension(int uid) {
    	 BusPass obj = new BusPass();
    	 BusPassDAO dao = new BusPassDAO(); 
    	 
    	 System.out.println("Enter route id: ");
    	 int routeid = scanner.nextInt();
    	 
    	obj.uid = uid;
    	obj.routeId = routeid;
    	obj.status = 4;
    	
    	String sql = "SELECT * from BusPass where uid = "+uid+" and routeId = "+routeid;
		List<BusPass> objects = dao.retrieve(sql);
		
		for(BusPass object : objects) {
			obj.approvedRejectedOn = object.approvedRejectedOn;
			obj.validTill = object.validTill;
		}
    	
    	int result = dao.update(obj);
 		String message = (result > 0) ? "Pass suspended Successfully" : "suspending Pass Request Failed. Try Again.."; 
 		System.out.println(message);
     }
	
	public void approveRejectPassRequest() {
		
		BusPass pass = new BusPass();

		System.out.println("Enter Pass ID: ");
		pass.id = scanner.nextInt();
		
		System.out.println("2: Approve");
		System.out.println("3: Cancel");
		System.out.println("Enter Approval Choice: ");
		pass.status = scanner.nextInt();

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		pass.approvedRejectedOn = dateFormat.format(date1);
		
		if(pass.status == 2) {
			calendar.add(Calendar.YEAR, 1);
			Date date2 = calendar.getTime();
			pass.validTill = dateFormat.format(date2);
		}else {
			pass.validTill = pass.approvedRejectedOn;
		}
		
		int result = passDAO.update(pass);
		String message = (result > 0) ? "Pass Request Updated Successfully" : "Updating Pass Request Failed. Try Again.."; 
		System.out.println(message);
	}
	
	public void viewPassRequests() {
		
		System.out.println("Enter Route ID to get All the Pass Reqeuests for a Route");
		System.out.println("Or 0 for All Bus Pass Requests");
		System.out.println("Enter Route ID: ");
		
		int routeId = scanner.nextInt();
		
		List<BusPass> objects = null;
		
		if(routeId == 0) {
			objects = passDAO.retrieve();
		}else {
			String sql = "SELECT * from BusPass where routeId = "+routeId;
			objects = passDAO.retrieve(sql);
		}
		
		for(BusPass object : objects) {
			object.prettyPrint();
		}
	}
	
	public void viewPassRequestsByUser(int uid) {
		
		String sql = "SELECT * from BusPass where uid = "+uid;
		List<BusPass> objects = passDAO.retrieve(sql);
		
		for(BusPass object : objects) {
			object.prettyPrint();
		}
	}
	
}
