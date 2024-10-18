package models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.multipart.MultipartFile;

import beans.GetConnection;
import beans.IDKeyGeneration;
import beans.Mail;
import cryptography.Decryption;
import cryptography.Encryption;
 

public class Documents {
private String userid,dhash,username,seckey,otp,uotp,title,docdesc,dt,tm,filePath;
private List<Documents> lst;
private int docid;
Connection con;
CallableStatement csmt;
ResultSet rs;
private MultipartFile file;


public MultipartFile getFile() {
	return file;
}

public void setFile(MultipartFile file) {
	this.file = file;
}

public String getDhash() {
	return dhash;
}

public void setDhash(String dhash) {
	this.dhash = dhash;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public List<Documents> getLst() {
	return lst;
}

public void setLst(List<Documents> lst) {
	this.lst = lst;
}

public String getOtp() {
	return otp;
}

public void setOtp(String otp) {
	this.otp = otp;
}

public String getUotp() {
	return uotp;
}

public void setUotp(String uotp) {
	this.uotp = uotp;
}

public String getSeckey() {
	return seckey;
}

public void setSeckey(String seckey) {
	this.seckey = seckey;
}
 

public int getDocid() {
	return docid;
}

public void setDocid(int docid) {
	this.docid = docid;
}

public String getUserid() {
	return userid;
}

public void setUserid(String userid) {
	this.userid = userid;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getDocdesc() {
	return docdesc;
}

public void setDocdesc(String docdesc) {
	this.docdesc = docdesc;
}

public String getDt() {
	return dt;
}

public void setDt(String dt) {
	this.dt = dt;
}

public String getTm() {
	return tm;
}

public void setTm(String tm) {
	this.tm = tm;
}

public String getFilePath() {
	return filePath;
}

public void setFilePath(String filePath) {
	this.filePath = filePath;
}
public Documents()
{
	
}
public void manageAccessPer(String metaFile,String metaFile1,String data) {

    try {
        File f1 = new File(metaFile);
       if(!f1.exists()) {
          f1.createNewFile();
          
       }
       else
       {
    	   data=readFile(metaFile, metaFile1)+ "|"+data;
    	   System.out.println("data111="+data);
       }
       System.out.println("data="+data);
       FileWriter fileWritter = new FileWriter(metaFile,false);
       BufferedWriter bw = new BufferedWriter(fileWritter);
       bw.write(data);
       bw.close();
       Encryption enc=new Encryption();
       enc.setSkey(docid+"@11111111111");
        enc.encryptAES1(metaFile, docid, metaFile);
       System.out.println("Done");
    } catch(IOException e){
       e.printStackTrace();
    }
 }
public Documents(ResultSet rs)
{
	try
	{docid=rs.getInt("docid") ;
		title=rs.getString("title").trim();
		seckey=rs.getString("skey").trim();
   	 dt=rs.getString("dt").trim();
   	 tm=rs.getString("tm").trim();
   	 docdesc=rs.getString("docdesc").trim();
   	dhash=rs.getString("dhash").trim();
   	 filePath=rs.getString("filePath").trim();
 	userid=rs.getString("userid").trim();
   	username=rs.getString("usernm").trim();
   
	}
	catch (Exception e) {
		// TODO: handle exception
		System.out.println("err="+e.getMessage());
	}
}
public String readFile(String filepath,String outpath)
{
	String data="";
	try {
		   Decryption enc=new Decryption();
	       enc.setSkey(docid+"@11111111111");
	       enc.decrypt(filepath, outpath);
	BufferedReader buffReader = new BufferedReader( new InputStreamReader(new FileInputStream(outpath))); 
	String line = buffReader.readLine(); 
	while (line != null) 
	{ 
		System.out.println(line);
		data+=line;
		line = buffReader.readLine(); 
	}
	}
	catch (Exception e) {
		// TODO: handle exception
	}
	 return data;
}
public void getId()
{
    try
    {
    	Connection con; 
    	CallableStatement cst;
         GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        cst=con.prepareCall("{call getMaxDocId()}");
       
        cst.execute();
         ResultSet rs=cst.getResultSet();
                    
        boolean auth=false;
        while(rs.next())
        { System.out.println("true");
            auth=true;
            
            docid=rs.getInt("mxid");
            if(docid==0)
            	docid=1001;
             
              
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
public String insert()
{	String st="failure";
	try
	{
	GetConnection gc = new GetConnection();
	int y=0;
	
	Connection con; 
	CallableStatement cst;
     GetConnection obj=new  GetConnection();
    con=obj.getConnection();
    cst=con.prepareCall("{call insertDoc(?,?,?,?,?,?,?,?)}");
   cst.setInt(1, docid);
   cst.setString(2, userid);
   cst.setString(3, title);
   cst.setString(4, dt);
   cst.setString(5, tm);
   cst.setString(6, docdesc);
   cst.setString(7, seckey);
   cst.setString(8, filePath);
    int x= cst.executeUpdate();
	 
	if(x>0) {
		
		st="success";
	}
	else
		st="failure";
	
	 
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
	return st;
	
	
}
public void getDocs1(String userid)
{
	boolean flag=false;
	  lst=new ArrayList<Documents>();
    try
    {
         GetConnection  obj=new  GetConnection();
         
        con=obj.getConnection();
        csmt=con.prepareCall("{call getDocuments2(?)}");
         
        csmt.setString(1, userid);
        
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        {  
        	lst.add(new Documents(rs));
        }
    }
       
     
    catch(Exception ex)
    {
        System.out.println("err in api="+ex.getMessage());
         
    }
    
}
public void getDocs(String userid)
{
	boolean flag=false;
	  lst=new ArrayList<Documents>();
    try
    {
         GetConnection  obj=new  GetConnection();
         
        con=obj.getConnection();
        csmt=con.prepareCall("{call getDocuments1(?)}");
         
        csmt.setString(1, userid);
        
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        {  
        	lst.add(new Documents(rs));
        }
    }
       
     
    catch(Exception ex)
    {
        System.out.println("err in api="+ex.getMessage());
         
    }
    
}
public void getUserDocs(String docids)
{
	boolean flag=false;
  lst=new ArrayList<Documents>();
    try
    {
        GetConnection obj=new  GetConnection();
         
        con=obj.getConnection();
        Statement st=con.createStatement();
        System.out.println("qr="+"select * from getdocuments where  docid in("+docids+")");
		rs=st.executeQuery("select * from getdocuments where  docid in("+docids+")");
                     
        while(rs.next())
        {  
        	lst.add(new Documents(rs));
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err in api="+ex.getMessage());
         
    }
     
}
}
