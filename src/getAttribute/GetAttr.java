package getAttribute;

import java.io.*;
import java.util.*;

public class GetAttr {
	private static HashMap<Integer, Double> temp_sl = new HashMap<>();
	private static HashMap<Integer, Double> temp_ll = new HashMap<>();
	
	public static HashMap<Integer, String> feature(int att_index, ArrayList<ArrayList<String>> records) {
		 HashMap<Integer, String> result = new HashMap<>(); 	    
	     int col = att_index; 
	     for (int i = 1; i < records.size(); i++ ) {       
	            if (i == 1) {
	                result.put(i, "D" );     
	                continue;
	            }
	            
	            if (Double.parseDouble(records.get(i).get(col))- Double.parseDouble(records.get(i-1).get(col)) >= 0 ) {
	    	    	result.put(i, "R");     
	    	    } else {
	    	    	result.put(i, "D");  
	    	    }	
        }       	        	
	    return result;		
	}
	public static HashMap<Integer, Double> feature2_weka(int att_index, ArrayList<ArrayList<String>> records) {
		 HashMap<Integer, Double> result = new HashMap<>(); 	    
	     int col = att_index; 
	     for (int i = 1; i < records.size(); i++ ) {       
	            if (i == 1) {
	            	
	            } else {	            
	                double min = Double.parseDouble(records.get(i).get(col))- Double.parseDouble(records.get(i-1).get(col));
	                result.put(i, min);
	            }
         }    
	     double average =  0.0;
	    	for (int i = 1; i < result.size(); i++) {
	    		if (result.get(i) != null) {
	    			average += result.get(i);
	    		}
	    	}
	    	
	    	average /= (double) result.size();
	    	for (int i = 1; i < result.size(); i++) {
	    		if (result.get(i) == null) {
	    			result.put(i, average);
	    		}
	    	}
	     return result;		
	}
	
	public static HashMap<Integer, String> feature2(int att_index, ArrayList<ArrayList<String>> records) {
		 HashMap<Integer, String> result = new HashMap<>(); 	    
	     int col = att_index; 
	     for (int i = 1; i < records.size(); i++ ) {       
	            if (i == 1) {
	            	result.put(i, records.get(0).get(col) + "_R");      
	                continue;
	            }
	            
	            if (Double.parseDouble(records.get(i).get(col))- Double.parseDouble(records.get(i-1).get(col)) >= 0 ) {
	    	    	result.put(i,  records.get(0).get(col) + "_R");     
	    	    } else {
	    	    	result.put(i,  records.get(0).get(col) + "_D");  
	    	    }	
       }       	        	
	    return result;		
	}
	
	
	public static HashMap<Integer, String> match_source_target(HashMap<Integer, String> s, HashMap<Integer, String> t, int sou, int tar) {
		HashMap<Integer, String> result = new HashMap<>(); 
	    for (int i = 1;i < t.size(); i++) {
	        if (s.get(i) == t.get(i)) {
	        	result.put(i, "Same" + "_" + sou + "_" + tar);
	        } else {
	        	result.put(i, "Diff" + "_" + sou + "_" + tar);
	        }
	    }	    
	    return result;
	}
	
	public static HashMap<Integer, String> Move_Average_same(int length1, int length2, String att, int att_index, ArrayList<ArrayList<String>> records) {
		HashMap<Integer, String> result = new HashMap<>();
		int col = att_index;   
		for (int i = 1; i < records.size(); i++ ) {       
	           if (i <= length1) {
	               result.put(i, "MAa"+ att.charAt(0) + length1 + "_1");     
	               continue;
	           }
	           double sum_t1 = 0;
	           double sum_t2= 0;
	           if (i - length1 + 1 >= 1) { 
	        	   for (int p_1 = i; p_1 >= i-length1+1; p_1--) {                
	                    sum_t1 = sum_t1+ Double.parseDouble(records.get(p_1).get(col));
	        	   }
	           }
	           if (i - length2 + 1 >= 1) { 
	        	   for (int p_1 = i; p_1 >= i-length2+1; p_1--) {                
	                    sum_t2 = sum_t2 + Double.parseDouble(records.get(p_1).get(col));
	        	   }
	           }
	           double MA = sum_t1/length1 - sum_t2/length2;
	           if (MA >= 0) {	                
	                result.put(i, "MAa" + att.charAt(0) + length1 + "_1");    
	           } else {	                
	                result.put(i, "MAa" + att.charAt(0) + length1 + "_0"); 
	           }       	           
		}
		return result;
	}
	public static HashMap<Integer, Double> Move_Average_weka(int length, String att, int att_index, ArrayList<ArrayList<String>> records) {
        HashMap<Integer, Double> result = new HashMap<>();    
        //The column of Target
        int col = att_index;                                                                                                                            
        for (int i = 1; i < records.size(); i++ ) {       
            if (i <= length) {
              
            }
            
            double sum_t = 0;
            double sum_t_1 = 0;
            if (i - length + 1 >= 1) {         
                for (int p_1 = i; p_1 >= i-length+1; p_1--) {                
                    sum_t = sum_t + Double.parseDouble(records.get(p_1).get(col));
                } 
                     
                int j = i - 1;
                if (j - length + 1 >=1) {
                    
                    for (int p_2 = j; p_2 >= j-length+1; p_2--) {
                       
                        sum_t_1 = sum_t_1 + Double.parseDouble(records.get(p_2).get(col));
                    }
                }
            }          
            
            double MA = sum_t/length - sum_t_1/length;     
            result.put(i, MA);
        }  
        
        double average =  0.0;
    	for (int i = 1; i < result.size(); i++) {
    		if (result.get(i) != null) {
    			average += result.get(i);
    		}
    	}
    	
    	average /= (double) result.size();
    	for (int i = 1; i < result.size(); i++) {
    		if (result.get(i) == null) {
    			result.put(i, average);
    		}
    	}
        return result;
    }    
	
    public static HashMap<Integer, String> Move_Average(int length, String att, int att_index, ArrayList<ArrayList<String>> records) {
        HashMap<Integer, String> result = new HashMap<>();    
        //The column of Target
        int col = att_index;                                                                                                                            
        for (int i = 1; i < records.size(); i++ ) {       
            if (i <= length) {
                result.put(i, "MA"+ att.charAt(0) + length + "_0");     
                continue;
            }
            
            double sum_t = 0;
            double sum_t_1 = 0;
            if (i - length + 1 >= 1) {         
                for (int p_1 = i; p_1 >= i-length+1; p_1--) {                
                    sum_t = sum_t + Double.parseDouble(records.get(p_1).get(col));
                } 
                     
                int j = i - 1;
                if (j - length + 1 >=1) {
                    
                    for (int p_2 = j; p_2 >= j-length+1; p_2--) {
                       
                        sum_t_1 = sum_t_1 + Double.parseDouble(records.get(p_2).get(col));
                    }
                }
            }          
            
            //Rise or Down
            double MA = sum_t/length - sum_t_1/length;     
            if (MA >= 0) {
                //System.out.println("i: " + i + " " + MA);
                result.put(i, "MA" + att.charAt(0) + length + "_1");    
            } else {
                //System.out.println("i: " + i + " " + MA);
                result.put(i, "MA" + att.charAt(0) + length + "_0"); 
            }              
        }            
        return result;
    }    
    
	 /*
	 //for Weka
	 public static HashMap<Integer, Double> Move_Average(int length, String att, int att_index, ArrayList<ArrayList<String>> records) {	        
	        HashMap<Integer, Double> result = new HashMap<>(); 
	        int training_data = (int)((records.size()-1)*0.8);  	        
	        
	        //The column of Target
	        int col = att_index;                                                                                                                            
	        for (int i = 1; i < records.size(); i++ ) {       
	            if (i < length) {	                  
	                continue;
	            }	            
	            double sum_t = 0;	           
	            if (i - length + 1 >= 1) {         
	                for (int p_1 = i; p_1 >= i-length+1; p_1--) {                
	                    sum_t = sum_t + Double.parseDouble(records.get(p_1).get(col));
	                } 	
	                result.put(i, sum_t/(double)length);  
	            }          	            	                    
	        }       
	           
	        return result;
	    }*/
	 
    public static HashMap<Integer, String> BIAS(int length, int att_index, double threshold, ArrayList<ArrayList<String>> records) {
    	HashMap<Integer, String> result = new HashMap<>();
    	int col = att_index;   
    	for (int i = 1; i < records.size(); i++) {
    		double bias;
    	    if (i <= length-1) {
    	    	result.put(i, "BIAS_" + records.get(0).get(att_index).charAt(0) + "_" + length + "_" + threshold + "_0");
    	    } else {
    	    	double sum_t = 0;
    	    	if (i - length + 1 >= 1) {
    	    		for (int j = i; j >= i-length+1; j--) {                
                        sum_t = sum_t + Double.parseDouble(records.get(j).get(col));
                    } 	    	    		
    	    	}
    	    	sum_t = sum_t / (double)length;
    	    	bias = (Double.parseDouble(records.get(i).get(att_index)) - sum_t)/(double) sum_t;
    	    	if (bias > threshold) {
    	    		result.put(i, "BIAS_" + records.get(0).get(att_index).charAt(0) + "_" + length + "_" + threshold + "_1");	
    	    	} else {
    	    		result.put(i, "BIAS_" + records.get(0).get(att_index).charAt(0) + "_" + length + "_" + threshold + "_0");	
    	    	}    	    	
    	    }
    		
    	}
    	
    	return result;
    }
    
    public static HashMap<Integer, Double> BIAS_weka(int length, int att_index, double threshold, ArrayList<ArrayList<String>> records) {
    	HashMap<Integer, Double> result = new HashMap<>();
    	int col = att_index;   
    	for (int i = 1; i < records.size(); i++) {
    		double bias;
    	    if (i <= length-1) {
    	    	
    	    } else {
    	    	double sum_t = 0;
    	    	if (i - length + 1 >= 1) {
    	    		for (int j = i; j >= i-length+1; j--) {                
                        sum_t = sum_t + Double.parseDouble(records.get(j).get(col));
                    } 	    	    		
    	    	}
    	    	sum_t = sum_t / (double)length;
    	    	bias = (Double.parseDouble(records.get(i).get(att_index)) - sum_t)/(double) sum_t;
    	    	result.put(i, bias);  	    	
    	    }
    		
    	}
    	
    	double average =  0.0;
    	for (int i = 1; i < result.size(); i++) {
    		if (result.get(i) != null) {
    			average += result.get(i);
    		}
    	}
    	
    	average /= (double) result.size();
    	for (int i = 1; i < result.size(); i++) {
    		if (result.get(i) == null) {
    			result.put(i, average);
    		}
    	}
    	return result;
    }
    
    
	public static void featureExtraction(String output_filename, ArrayList<ArrayList<String>> records) {				
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
	    HashMap<Integer, String> F_oil = feature2(2, records);	    
		HashMap<Integer, String> F_but = feature2(4, records);
		
		HashMap<Integer, String> FS_oil = feature(2, records);		
		HashMap<Integer, String> FS_cru = feature(1, records);
		HashMap<Integer, String> FT_but = feature(4, records);	
		
		HashMap<Integer, String> Match_of_oil_but = match_source_target(FS_oil, FT_but, 2, 4);
		HashMap<Integer, String> Match_of_cru_but = match_source_target(FS_cru, FT_but, 1, 4);
		
		HashMap<Integer, String> MACD_oil_1_2_3 = MACD(1, 2, 3,records.get(0).get(2), records);
		//HashMap<Integer, String> MACD_rate_1_2_3 = MACD(1, 2, 3,records.get(0).get(3), records);
		HashMap<Integer, String> MACD_T_1_2_3 = MACD(1, 2, 3,records.get(0).get(4), records);
		HashMap<Integer, String> MACD_T_2_3_4 = MACD(2, 3, 4,records.get(0).get(4), records);
		
		//HashMap<Integer, String> BIAS_rate_2_03 = BIAS(2, 3, 0.0003, records);
		HashMap<Integer, String> BIAS_T_2_03 = BIAS(2, 4, 0.0003, records);
		
		for (int i = 0; i < records.size(); i++) {		
			ArrayList<String> temp = new ArrayList<>();
			//Add Date
			temp.add(records.get(i).get(0));
			if(i == 0) {			 			     
			       temp.add("F_oil");			 
			       temp.add("F_but");
			       temp.add("Match_of_oil_but");	
			       temp.add("Match_of_cru_but");	
			       temp.add("MACD_oil_1_2_3");
			       //temp.add("MACD_rate_1_2_3");
			       temp.add("MACD_T_1_2_3");
			       temp.add("MACD_T_2_3_4");
			       //temp.add("BIAS_rate_2_03");
			       temp.add("BIAS_T_2_03");			
			       
			} else {
				//All the conditional att need to add. eg. x -> x x_3 x_4		       		        
		           temp.add(F_oil.get(i));		           
		           temp.add(F_but.get(i));
		           temp.add(Match_of_oil_but.get(i));
		           temp.add(Match_of_cru_but.get(i));
		           temp.add(MACD_oil_1_2_3.get(i));
		           //temp.add(MACD_rate_1_2_3.get(i));
		           temp.add(MACD_T_1_2_3.get(i));
		           temp.add(MACD_T_2_3_4.get(i));
		           //temp.add(BIAS_rate_2_03.get(i));
		           temp.add(BIAS_T_2_03.get(i));
			}
			//Add the last one of every line
			temp.add(records.get(i).get(records.get(i).size()-1));
			
			result.add(temp);
		}		
		try {
		writeCSV("", output_filename,result);
		} catch (IOException e) {
			System.out.println("[ERROR] I/O Exception.");
			e.printStackTrace();
		}
	}
	
public static void featureExtraction2(String output_filename, ArrayList<ArrayList<String>> records, HashMap<Integer, String> target, int window_size) {				
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		HashMap<Integer, String> F_oil = feature2(2, records);	    
	    HashMap<Integer, String> F_but = feature2(4, records);
			
		HashMap<Integer, String> FS_oil = feature(2, records);		
		HashMap<Integer, String> FS_cru = feature(1, records);
		HashMap<Integer, String> FT_but = feature(4, records);	
			
		HashMap<Integer, String> Match_of_oil_but = match_source_target(FS_oil, FT_but, 2, 4);
		HashMap<Integer, String> Match_of_cru_but = match_source_target(FS_cru, FT_but, 1, 4);
			
		HashMap<Integer, String> MACD_oil_1_2_3 = MACD(1, 2, 3,records.get(0).get(2), records);
		//HashMap<Integer, String> MACD_rate_1_2_3 = MACD(1, 2, 3,records.get(0).get(3), records);
		HashMap<Integer, String> MACD_T_1_2_3 = MACD(1, 2, 3,records.get(0).get(4), records);
		HashMap<Integer, String> MACD_T_2_3_4 = MACD(2, 3, 4,records.get(0).get(4), records);
			
		//HashMap<Integer, String> BIAS_rate_2_03 = BIAS(2, 3, 0.0003, records);
		HashMap<Integer, String> BIAS_T_2_03 = BIAS(2, 4, 0.0003, records);
		
		for (int i = 0; i <= records.size()*0.8; i++) {		
			ArrayList<String> temp = new ArrayList<>();
			//Add Date
			temp.add(records.get(i).get(0));
			if(i == 0) {			 			       
			    temp.add("F_oil");			 
			    temp.add("F_but");
			    temp.add("Match_of_oil_but");	
			    temp.add("Match_of_cru_but");	
			    temp.add("MACD_oil_1_2_3");
			    //temp.add("MACD_rate_1_2_3");
			    temp.add("MACD_T_1_2_3");
			    temp.add("MACD_T_2_3_4");
			    //temp.add("BIAS_rate_2_03");
			    temp.add("BIAS_T_2_03");			
			    temp.add(records.get(i).get(records.get(i).size()-1));	  
			} else {
				//All the conditional att need to add. eg. x -> x x_3 x_4		       		        
		        temp.add(F_oil.get(i));		           
		        temp.add(F_but.get(i));
		        temp.add(Match_of_oil_but.get(i));
		        temp.add(Match_of_cru_but.get(i));
		        temp.add(MACD_oil_1_2_3.get(i));
		        //temp.add(MACD_rate_1_2_3.get(i));
		        temp.add(MACD_T_1_2_3.get(i));
		        temp.add(MACD_T_2_3_4.get(i));
		        //temp.add(BIAS_rate_2_03.get(i));
		        temp.add(BIAS_T_2_03.get(i));		       
		        temp.add(target.get(i));			 
			}	
			result.add(temp);
		}		
		try {
		writeCSV("", output_filename,result);
		} catch (IOException e) {
			System.out.println("[ERROR] I/O Exception.");
			e.printStackTrace();
		}
	}
	 /*
	 public static void featureExtraction_one(String output_filename, ArrayList<ArrayList<String>> records) {				
			
			ArrayList<ArrayList<String>> result = new ArrayList<>();
						
			for (int i = 0; i < records.size(); i++) {		
				ArrayList<String> temp = new ArrayList<>();
				//Add Date
				temp.add(records.get(i).get(0));
				if(i == 0) {			 
				       //temp.add(records.get(i).get(1));
				       temp.add("Feature_S");				      			    
				} else {
					   if (Double.parseDouble(records.get(i).get(1)) <= 77.59) {
					       temp.add("RR");   
					   } else {
						   temp.add("DD"); 
					   }			       
			        	//temp.add(records.get(i).get(1));			          			        	     		    
				}
				//Add the last one of every line
				temp.add(records.get(i).get(records.get(i).size()-1));			
				result.add(temp);
			}		
			try {
			writeCSV("", output_filename,result);
			} catch (IOException e) {
				System.out.println("[ERROR] I/O Exception.");
				e.printStackTrace();
			}
		} 
	 */	
    //weka
    public static void featureExtraction_weka(String output_filename, ArrayList<ArrayList<String>> records) {		      		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		/*HashMap<Integer, String> F_oil = feature2(2, records);	    
		HashMap<Integer, String> F_but = feature2(4, records);
		
		HashMap<Integer, String> FS_oil = feature(2, records);		
		HashMap<Integer, String> FS_cru = feature(1, records);
		HashMap<Integer, String> FT_but = feature(4, records);	*/
		HashMap<Integer, Double> FS_oil = feature2_weka(2, records);
		
		/*
		HashMap<Integer, String> Match_of_oil_but = match_source_target(FS_oil, FT_but, 2, 4);
		HashMap<Integer, String> Match_of_cru_but = match_source_target(FS_cru, FT_but, 1, 4);*/
		
		/*HashMap<Integer, String> MACD_oil_1_2_3 = MACD(1, 2, 3,records.get(0).get(2), records);*/
		HashMap<Integer, Double> MACD_oil_1_2_3 = MACD_weka(1, 2, 3,records.get(0).get(2), records);
		//HashMap<Integer, String> MACD_rate_1_2_3 = MACD(1, 2, 3,records.get(0).get(3), records);
		/*HashMap<Integer, String> MACD_T_1_2_3 = MACD(1, 2, 3,records.get(0).get(4), records);*/
		HashMap<Integer, Double> MACD_T_1_2_3 = MACD_weka(1, 2, 3,records.get(0).get(4), records);
		/*HashMap<Integer, String> MACD_T_2_3_4 = MACD(2, 3, 4,records.get(0).get(4), records);*/
		HashMap<Integer, Double> MACD_T_2_3_4 = MACD_weka(2, 3, 4,records.get(0).get(4), records);
		
		//HashMap<Integer, String> BIAS_rate_2_03 = BIAS(2, 3, 0.0003, records);
		/*HashMap<Integer, String> BIAS_T_2_03 = BIAS(2, 4, 0.0003, records);*/
		HashMap<Integer, Double> BIAS_T_2_03 = BIAS_weka(2, 4, 0.0003, records);
				
		for (int i = 0; i < records.size(); i++) {		
			ArrayList<String> temp = new ArrayList<>();
			//Add time
			temp.add(records.get(i).get(0));
			if(i == 0) {
				/*
				   temp.add("F_oil");			 
			       temp.add("F_but");
			       temp.add("Match_of_oil_but");	
			       temp.add("Match_of_cru_but");	
			       temp.add("MACD_oil_1_2_3");
			       //temp.add("MACD_rate_1_2_3");
			       temp.add("MACD_T_1_2_3");
			       temp.add("MACD_T_2_3_4");
			       //temp.add("BIAS_rate_2_03");
			       temp.add("BIAS_T_2_03");		   	*/
				   temp.add("FS_oil");
				   temp.add("MACD_oil_1_2_3");
				   temp.add("MACD_T_1_2_3");
				   temp.add("MACD_T_2_3_4");
				   temp.add("BIAS_T_2_03");
				   
			} else {
				/*
				   temp.add(F_oil.get(i));		           
		           temp.add(F_but.get(i));
		           temp.add(Match_of_oil_but.get(i));
		           temp.add(Match_of_cru_but.get(i));
		           temp.add(MACD_oil_1_2_3.get(i));
		           //temp.add(MACD_rate_1_2_3.get(i));
		           temp.add(MACD_T_1_2_3.get(i));
		           temp.add(MACD_T_2_3_4.get(i));
		           //temp.add(BIAS_rate_2_03.get(i));
		           temp.add(BIAS_T_2_03.get(i));*/
				
				   temp.add(String.valueOf(FS_oil.get(i)));
				   temp.add(String.valueOf(MACD_oil_1_2_3.get(i)));
				   temp.add(String.valueOf(MACD_T_1_2_3.get(i)));
				   temp.add(String.valueOf(MACD_T_2_3_4.get(i)));
				   temp.add(String.valueOf(BIAS_T_2_03.get(i)));
			}	
			temp.add(records.get(i).get(records.get(i).size()-1));	
			result.add(temp);
		}		
		try {
		writeCSV("", output_filename,result);
		} catch (IOException e) {
			System.out.println("[ERROR] I/O Exception.");
			e.printStackTrace();
		}
	}
	
    public static HashMap<Integer, String> featureExtraction_target(ArrayList<ArrayList<String>> records) {
    	HashMap<Integer, String> result = new HashMap<>();
    	int index_of_target_att = records.get(0).size()-1;
    	for (int i = 1; i < records.size(); i++) {
    	    if (i==1) {
    	    	result.put(i, "Rise"); 
    	    	continue;
    	    }
    	    if (Double.parseDouble(records.get(i).get(index_of_target_att))- Double.parseDouble(records.get(i-1).get(index_of_target_att)) >= 0 ) {
    	    	result.put(i, "Rise");     
    	    } else {
    	    	result.put(i, "Down");  
    	    }	
    	}    	  
    	return result;  
    	
    }
           
    public static HashMap<Integer, String> MACD(int tl, int sl, int ll, String att, ArrayList<ArrayList<String>> records) {
    	HashMap<Integer, String> result = new HashMap<>(); 
    	for (int i = 1; i < records.size(); i++) {
    	    double MACD = DIF(i, sl, ll, records) - DEM(i, sl, ll, tl, records);        	
    		if (MACD < 0) {
    			result.put(i, "MACD0_" + att.charAt(0) + sl + ll);
    		} else {
    			result.put(i, "MACD1_" + att.charAt(0) + sl + ll);			
    		}
    	}
    	return result;
    } 
    
    public static HashMap<Integer, Double> MACD_weka(int tl, int sl, int ll, String att, ArrayList<ArrayList<String>> records) {
    	HashMap<Integer, Double> result = new HashMap<>(); 
    	for (int i = 1; i < records.size(); i++) {
    	    double MACD = DIF(i, sl, ll, records) - DEM(i, sl, ll, tl, records);        	
    		result.put(i, MACD);
    	}
    	return result;
    } 
     
    public static double EMA(int t, int l, ArrayList<ArrayList<String>> records, String s) {
    	if (t == 0) {  
    		return 0.0;
    	}
    	int col = 2;
    	double alpha = 2/(double)(l+1);
    	double p = Double.parseDouble(records.get(t).get(col));
        if (s.equals("sl")) {
        	temp_sl.put(0, 0.0);
            temp_sl.put(t, temp_sl.get(t-1) + alpha*(p - temp_sl.get(t-1)));
            return temp_sl.get(t);
        } else {  	
        	temp_ll.put(0, 0.0); 
            temp_ll.put(t, temp_ll.get(t-1) + alpha*(p - temp_ll.get(t-1)));
            return temp_ll.get(t);
        }  
    	
    }
    
    public static double DIF(int t, int sl, int ll, ArrayList<ArrayList<String>> records) {
        return EMA(t, sl, records, "sl") - EMA(t, ll, records, "ll"); 	
    }
    
    public static double DEM(int t, int sl, int ll, int tl, ArrayList<ArrayList<String>> records) {
        return 	(DIF(t, sl, ll, records) + DIF(t-1, sl, ll, records))/(double) tl;
    }
        
	static void writeCSV(String path, String filename, ArrayList<ArrayList<String>> records) throws IOException{
		FileWriter outputFW = new FileWriter(path + filename);
		for(int i=0;i<records.size();i++){
			ArrayList<String> record = records.get(i);
			StringBuilder recordSB = new StringBuilder();
			for(String col : record) recordSB.append(col).append(',');
			recordSB.deleteCharAt(recordSB.length()-1);
			outputFW.write(recordSB.toString());
			if(i < records.size()-1) outputFW.write("\r\n");
		}
		outputFW.close();
	}	
}
