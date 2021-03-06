package transferToSDB;
import java.io.*;
import java.util.*;

public class T2SDB {
	
	
    public void translate_training(int window_size, String path, HashMap<Integer, String> class_table, String output) {
       try {
           //System.out.print("=============Transfer to SDB(Training)=============\n");
           ArrayList<ArrayList<String>> records = readCSV(path);
           
           int training_data = (int)((records.size() - 1)*0.8);
                      
           //output
           File fout = new File(output);
	       FileOutputStream fos = new FileOutputStream(fout);
	       OutputStreamWriter osw = new OutputStreamWriter(fos);           
           for (int i = 1; i <= training_data-window_size+1; i++) { 
               //The calss_table's index
               int class_index = 0;   
               for (int j = 0; j < window_size; j++) {
                   int index = i + j;                     
                   if (index <= training_data) {
                       class_index = index;     
                       for (int k = 1; k < records.get(i).size()-1; k++) {
                    	   osw.write(records.get(index).get(k) + " ");        	                     	   
                       }                       
                       osw.write(-1 + " ");
                   }                       
               }
               
               class_index = class_index + 1;
               if (class_index <= records.size()) {
                   osw.write(class_table.get(class_index) + " "+ -1 + " ");
                   //Debug
                   //osw.write(class_table.get(class_index) + "(" + class_index + ")" + " "+ -1 + " ");
               }               
               osw.write(""+-2);
               osw.write("\r\n");               		
           }
           osw.close(); 
         
           //System.out.println("Training Data's window number: " + training_data );
           //System.out.println("===================================================\n");              
       } catch (FileNotFoundException e) {
	       System.out.println("[ERROR] File Not Found Exception.");
	    e.printStackTrace();
	   } catch (IOException e) {
           System.out.println("[ERROR] I/O Exception.");
           e.printStackTrace();
       }  
       
   }
    
	/*
	public void translate_training_weka(int window_size, String path, HashMap<Integer, String> class_table, String output) {
	       try {	         
	           ArrayList<ArrayList<String>> records = readCSV(path);
	           
	           int training_data = (int)((records.size() - 1)*0.8);
	                      
	           //output
	           File fout = new File(output);
		       FileOutputStream fos = new FileOutputStream(fout);
		       OutputStreamWriter osw = new OutputStreamWriter(fos);           
	           
	           for (int i = 1; i <= training_data-window_size+1; i++) { 
	               //The calss_table's index
	               int class_index = 0;             
	               for (int j = 0; j < window_size;j++) {
	                   int index = i + j;                     
	                   if (index <= training_data) {
	                       class_index = index;   
	                       for (int k = 1;k < records.get(i).size()-1; k++) {
	                    	   osw.write(records.get(index).get(k)  + "," + " ");        	                     	   
	                       }                   	                                          
	                   }                       
	               }
	               
	               class_index = class_index + 1;
	               if (class_index <= training_data) {
	                   osw.write(class_table.get(class_index));	                   
	               }               
	               
	               osw.write("\r\n");               		
	           }
	           osw.close(); 	         	           
	       } catch (FileNotFoundException e) {
		       System.out.println("[ERROR] File Not Found Exception.");
		    e.printStackTrace();
		   } catch (IOException e) {
	           System.out.println("[ERROR] I/O Exception.");
	           e.printStackTrace();
	       }  
	       
   }*/
    public void translate_training_weka(int window_size, String path, HashMap<Integer, String> class_table, String output) {
        try {
            ArrayList<ArrayList<String>> records = readCSV(path);               
//           int training_data = (int)((records.size() - 1)*0.8);    
            int training_data = records.size();
            //output
            File fout = new File(output);
 	        FileOutputStream fos = new FileOutputStream(fout);
 	        OutputStreamWriter osw = new OutputStreamWriter(fos);  
 	        
 	        //write title 	        
 	        int size = window_size*(records.get(0).size()-2);
 	        for (int i = 1; i <= size; i++) {
 	        	osw.write("A" + i  + "," + " ");    	
 	        }
 	        osw.write("Target");    	
 	        osw.write("\r\n");      
// 	       for (int i = 1; i <= training_data-window_size+1; i++) { 
            for (int i = 1; i <= training_data-window_size-2; i++) { 
                //The calss_table's index
                int class_index = 0;   
                for (int j = 0; j < window_size; j++) {
                    int index = i + j;                     
                    if (index <= training_data) {
                        class_index = index;     
                        for (int k = 1; k < records.get(i).size()-1; k++) {
                     	   osw.write(records.get(index).get(k)  + "," + " ");        	                     	   
                        }                       
                       
                    }
                }
                
                class_index = class_index + 1;
                if (class_index <= records.size()) {
                    osw.write(class_table.get(class_index));
                    //Debug
                    //osw.write(class_table.get(class_index) + "(" + class_index + ")" + " "+ -1 + " ");
                }               
             
                osw.write("\r\n");               		
            }
            osw.close(); 
          
        } catch (FileNotFoundException e) {
 	       System.out.println("[ERROR] File Not Found Exception.");
 	    e.printStackTrace();
 	   } catch (IOException e) {
            System.out.println("[ERROR] I/O Exception.");
            e.printStackTrace();
        }  
        
    }
   
   public void translate_testing(int window_size, String path, String output) {
       try {
    	   //System.out.print("=============Transfer to SDB(Testing)=============\n");
           ArrayList<ArrayList<String>> records = readCSV(path);                          
           int training_data = (int)((records.size()-1)*0.8);       
           
           //output
           File fout = new File(output);
	       FileOutputStream fos = new FileOutputStream(fout);
	       OutputStreamWriter osw = new OutputStreamWriter(fos);           
           
           for (int i = training_data + 1; i < records.size()-window_size; i++) {      
               for (int j = 0; j < window_size;j++) {
                   int index = i + j;                     
                   if (index < records.size()) {                          
                	   for (int k = 1;k < records.get(i).size()-1; k++) {
                    	   osw.write(records.get(index).get(k) + " ");        	                     	   
                       }                       
                       osw.write(-1 + " ");                       
                       
                   }                    
               }        
               osw.write(""+-2);
               osw.write("\r\n");
             
           }
           osw.close(); 
           //System.out.println("Testing Data's window number: " + (records.size()- 1 - training_data) );
           //System.out.println("===================================================\n");   
       } catch (FileNotFoundException e) {
	       System.out.println("[ERROR] File Not Found Exception.");
	       e.printStackTrace();
	   } catch (IOException e) {
           System.out.println("[ERROR] I/O Exception.");
           e.printStackTrace();
       }  

     
   } 
   public void translate_testing_weka(int window_size, String path, String output) {
       try {
    	   //System.out.print("=============Transfer to SDB(Testing)=============\n");
           ArrayList<ArrayList<String>> records = readCSV(path);                          
           int training_data = (int)((records.size()-1)*0.8);       
           
             
           
           
           
           //output
           File fout = new File(output);
	       FileOutputStream fos = new FileOutputStream(fout);
	       OutputStreamWriter osw = new OutputStreamWriter(fos);       
	       
	       
	       //write title 	        
	        int size = window_size*(records.get(0).size()-2);
	        for (int i = 1; i <= size; i++) {
	        	osw.write("A" + i  + "," + " ");    	
	        }   	
	        osw.write("\r\n");    
           
           for (int i = training_data + 1; i < records.size()-window_size; i++) {      
               for (int j = 0; j < window_size;j++) {
                   int index = i + j;                     
                   if (index < records.size()) {                          
                	   for (int k = 1;k < records.get(i).size()-1; k++) {
                    	   osw.write(records.get(index).get(k)  + "," + " ");        	                     	   
                       }                       
                                             
                       
                   }                    
               }        
          
               osw.write("\r\n");
             
           }
           osw.close(); 
           //System.out.println("Testing Data's window number: " + (records.size()- 1 - training_data) );
           //System.out.println("===================================================\n");   
       } catch (FileNotFoundException e) {
	       System.out.println("[ERROR] File Not Found Exception.");
	       e.printStackTrace();
	   } catch (IOException e) {
           System.out.println("[ERROR] I/O Exception.");
           e.printStackTrace();
       }  

     
   } 
   
   static ArrayList<ArrayList<String>> readCSV(String fullpath) throws FileNotFoundException{
       ArrayList<ArrayList<String>> records = new ArrayList<>();
       File inputFile = new File(fullpath);
	   Scanner scl = new Scanner(inputFile);
	   while(scl.hasNextLine()){
		   ArrayList<String> newRecord = new ArrayList<>();
		   String[] tokens = scl.nextLine().split(",");
		   for(String token : tokens){
			   newRecord.add(token);
		   }
		   records.add(newRecord);
	   }
	   scl.close();
		
	   return records; 
   }
   
   
}
