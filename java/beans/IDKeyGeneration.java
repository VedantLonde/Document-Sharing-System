package beans;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 

public class IDKeyGeneration {
	Connection con;
    CallableStatement csmt;
    ResultSet rs;
    private List<String> lstalgo;
    private String userid,idkey,email;
    public IDKeyGeneration()
    {
    	idkey="";
    }
    
    
    public List<String> getLstalgo() {
		return lstalgo;
	} 
	public void setLstalgo(List<String> lstalgo) {
		this.lstalgo = lstalgo;
	} 
	public String getUserid() {
		return userid;
	} 
	public void setUserid(String userid) {
		this.userid = userid;
	}
 
	public String getIdkey() {
		return idkey;
	} 
	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}
 
	public String getEmail() {
		return email;
	}
 
	public void setEmail(String email) {
		this.email = email;
	}
	public String generateIdKey()
    {
		String  temp="",index="";
        try
        { 
        	 getAlgo();
             Collections.shuffle(lstalgo);
             String algo=lstalgo.get(0).trim();
             String[] subAlgo=algo.split("\\+");
        	System.out.println("userid in details="+userid);
	        GetConnection obj=new  GetConnection();
	       con=obj.getConnection();
	       csmt=con.prepareCall("{call getUser(?)}"); 
	       csmt.setString(1, userid);
	        csmt.execute();
	        rs=csmt.getResultSet(); 
	        System.out.println("rs");
        	System.out.println("userid="+userid);
        	//getEmpDetails(userid);
            
             while(rs.next())
             { System.out.println("idkey="+idkey);
             for(int i=0;i<subAlgo.length;i++)
             {
            	 String[] singleDef=subAlgo[i].split("\\|");
            	 temp=rs.getString(singleDef[0]).trim();
            	 System.out.println("temp="+singleDef[0]);
            	 if(singleDef[0].equals("userid"))
            	 {
            		 idkey+="@"+(rs.getString("userid"));
            		 System.out.println((rs.getString("userid")));
            	 }
            	 else
            	 {
	            	 index=singleDef[1].trim();
	            	 
	            	 for(int j=0;j<index.length();j++)
	            	 {
	            		 try {
	            		 int ind=Integer.parseInt(String.valueOf(index.charAt(j)));
	            		 idkey+=temp.charAt(ind);
	            		 }
	            		 catch (Exception e) {
							// TODO: handle exception
	            			 idkey+=RandomString.getAlphaNumericString(2);
						}
	            	 }
            	 }
             }
             }
             System.out.println("idkey="+idkey);
            // updateIdkey();
        } 
        catch(Exception ex)
        {
            System.out.println("err in idkey="+ex.getMessage());
             
        }
        return idkey;
    }
	public void getUserDetails(String userid1)
	{
	    try
	    {
	    	System.out.println("userid in empdetails="+userid1);
	         GetConnection obj=new  GetConnection();
	        con=obj.getConnection();
	        csmt=con.prepareCall("{call getUser(?)}"); 
	        csmt.setString(1, userid1);
	         csmt.execute();
	         rs=csmt.getResultSet(); 
	         System.out.println("rs");
	         try
	 		{
	 			con.close();
	 		}
	 		catch (Exception e) {
	 			// TODO: handle exception
	 		}
	    } 
	    catch(Exception ex)
	    {
	        System.out.println("err="+ex.getMessage());
	         
	    }
	    
	}
	public boolean checkIDkey()
	{
		boolean flag=false;
	    try
	    {
	    	System.out.println("userid in empdetails="+userid);
	         GetConnection  obj=new  GetConnection();
	        con=obj.getConnection();
	        csmt=con.prepareCall("{call checkIdkey(?,?)}"); 
	        csmt.setString(1, userid);
	        csmt.setString(2, idkey);
	         csmt.execute();
	         rs=csmt.getResultSet(); 
	         while(rs.next())
	         {
	        	flag=true; 
	         }
	    } 
	    catch(Exception ex)
	    {
	        System.out.println("err="+ex.getMessage());
	         
	    }
	    return flag;
	    
	}public void updateIdkey1()
	{
	    try
	    {
	    	System.out.println("userid in empdetails="+userid);
	         GetConnection obj=new  GetConnection();
	        con=obj.getConnection();
	        csmt=con.prepareCall("{call updateIdkey1(?,?)}"); 
	        csmt.setString(1, userid);
	        csmt.setString(2, idkey);
	         csmt.execute();
	         try
	    		{
	    			con.close();
	    		}
	    		catch (Exception e) {
	    			// TODO: handle exception
	    		} 
	    } 
	    catch(Exception ex)
	    {
	        System.out.println("err="+ex.getMessage());
	         
	    }
	    
	}
	public void updateIdkey()
	{
	    try
	    {
	    	System.out.println("userid in empdetails="+userid);
	         GetConnection obj=new  GetConnection();
	        con=obj.getConnection();
	        csmt=con.prepareCall("{call updateIdkey(?,?)}"); 
	        csmt.setString(1, userid);
	        csmt.setString(2, idkey);
	         csmt.execute();
	         try
	    		{
	    			con.close();
	    		}
	    		catch (Exception e) {
	    			// TODO: handle exception
	    		} 
	    } 
	    catch(Exception ex)
	    {
	        System.out.println("err="+ex.getMessage());
	         
	    }
	    
	}
	public void getAlgo()
    {
        try
        {
             GetConnection obj=new  GetConnection();
            con=obj.getConnection();
            csmt=con.prepareCall("{call getIdKeyAlgo()}");
           
             csmt.execute();
             rs=csmt.getResultSet();
             lstalgo=new ArrayList<String>();            
            while(rs.next())
            { System.out.println("true");
            lstalgo.add(rs.getString("algo1").trim());
                  
            }
            try
    		{
    			con.close();
    		}
    		catch (Exception e) {
    			// TODO: handle exception
    		}
        }
           
         
        catch(Exception ex)
        {
            System.out.println("err="+ex.getMessage());
             
        }
    }
}
