package models;
import java.sql.*;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.GetConnection;
 
public class CheckUser {

	private String userid,idkey;
	private String pswd;
		
	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public void getIdkey1()
	{
	    try
	    {
	    	Connection con; 
	    	CallableStatement cst;
	         GetConnection obj=new  GetConnection();
	        con=obj.getConnection();
	        cst=con.prepareCall("{call getIdkey1(?)}");
	        cst.setString(1,userid);
	        cst.execute();
	         ResultSet rs=cst.getResultSet();
	                     
	        while(rs.next())
	        { 
	        	
	        	System.out.println("true");
	          
	            idkey=rs.getString("idkey1");
	            
	            System.out.println("true"+idkey);
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
	
public String getIdkey() {
		return idkey;
	}
public void getIdkey_Private()
{
    try
    {
    	Connection con; 
    	CallableStatement cst;
         GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        cst=con.prepareCall("{call getIdkey(?)}");
        cst.setString(1,userid);
        cst.execute();
         ResultSet rs=cst.getResultSet();
                     
        while(rs.next())
        { 
        	
        	System.out.println("true");
          
            idkey=rs.getString("idkey1");
            
            System.out.println("true"+idkey);
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
	public String checkUser(HttpServletRequest request) {
		
		Connection con;
		ResultSet rs;
		String typ,st="";
		GetConnection gc = new GetConnection();
		
		
		try {
			
			
			con=gc.getConnection();
			PreparedStatement pst;
			pst=con.prepareStatement("select * from users where userid=? and pswd=? and userstatus='active' ");
			pst.setString(1,userid );
			pst.setString(2, pswd);
				
			rs=pst.executeQuery();
			
			if(rs.next()) {
								
				
				HttpSession sess=request.getSession(true);
				sess.setAttribute("userid",userid);
				sess.setAttribute("usertype", rs.getString("usertype"));
				 
				typ=rs.getString("usertype");
				sess.setAttribute("photo", getPhoto(userid, typ));
				getIdkey_Private();
				sess.setAttribute("pidkey",idkey);
				getIdkey1();
				sess.setAttribute("pubidkey",idkey);
				System.out.println("type="+typ+" "+idkey);
				//LoginTracker lt=new LoginTracker();
				//lt.recordLogin(userid, typ);
				if(typ.equals("officer"))
					st="officer";
				else if(typ.equals("user"))
				{
					JavaFuns jf=new JavaFuns();
					 Vector v=jf.getValue("select emailid from userdetails where userid='"+userid+"'", 1);
					 sess.setAttribute("emailid", v.elementAt(0).toString().trim());
					st="user";
				}
				else if(typ.equals("admin"))
					st="admin";
				 
			}
			else
				st="LoginFailure.jsp";
			try
			{
				con.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return(st);	
	}
public String getPhoto(String userid,String utype) {
		String photo="common.png";
		Connection con;
		ResultSet rs;
		String typ,st="";
		GetConnection gc = new GetConnection();
		
		
		try {
			
			
			con=gc.getConnection();
			PreparedStatement pst;
			String qr="";
			if(utype.equals("user"))
			{
				qr="select photo from userdetails where userid='"+userid+"'";
			}
			 
				 

			pst=con.prepareStatement(qr);
			 
			rs=pst.executeQuery();
			
			if(rs.next()) { 
				photo=rs.getString("photo");
				 
			}
			try
			{
				con.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return(photo);	
	}
}
