package com.amazon.buspassmanagement;

import com.amazon.buspassmanagement.db.DB;

public class App {
	
    public static void main( String[] args ){
       
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println( "Welcome to Bus Pass Management Application" );
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	
    	Menu menu = new Menu();
//        Menu menu = null;
        
//        //if(args.length > 0) {s
//        DB.FILEPATH = args[0];
//        //}
        
        menu.showMainMenu();
		
    }
    
}
