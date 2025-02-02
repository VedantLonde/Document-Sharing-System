package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.web.multipart.MultipartFile;

import beans.GetConnection;
import beans.IDKeyGeneration;
import beans.Mail;

public class Users {
	private String userid,pubidkey,privateIdkey;
	private String city;
	private String state;
	private String usernm;
	private String pswd;
	private String usertype;
	private String userstatus;
	private String emailid;
	private String mobileno;
	private String gender;
	private String addr;
	private String dob;
	private String pincode;
	private MultipartFile file;
	private String path;
	 
	
	
 
	public String getPubidkey() {
		return pubidkey;
	}
	public void setPubidkey(String pubidkey) {
		this.pubidkey = pubidkey;
	}
	public String getPrivateIdkey() {
		return privateIdkey;
	}
	public void setPrivateIdkey(String privateIdkey) {
		this.privateIdkey = privateIdkey;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getGender() {
		return gender;
	}
	 
	public void setGender(String gender) {
		this.gender = gender;
	}
	 
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getUserid() {
		return userid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
public List<Users> getUserReport(String userid1){
		
		GetConnection gc = new GetConnection();
		Connection con;
		List<Users> lst = new ArrayList<Users>();
		System.out.println("userid="+userid1);
		ResultSet rs;
		
		try {
		
		con= gc.getConnection();
		Statement st=con.createStatement();
		rs=st.executeQuery("select * from userdetails where userid='"+userid1+"'");
		Users vs;
		 
		while(rs.next()) {
			
			vs= new Users();
			vs.setUserid(rs.getString("userid"));
			vs.setUsernm(rs.getString("usernm"));
			vs.setEmailid(rs.getString("emailid"));
			vs.setMobileno(rs.getString("mobileno"));
			vs.setGender(rs.getString("gender"));
			vs.setPincode(rs.getString("pincode"));
			vs.setDob(rs.getString("dob"));
			vs.setAddr(rs.getString("addr"));
			vs.setUsertype(rs.getString("usertype"));
			vs.setState(rs.getString("state"));
			vs.setCity(rs.getString("city"));
			lst.add(vs);
		}
		try
		{
			con.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return(lst);
	}
public List<Users> getUserReport1(String userid1,String docid){
	
	GetConnection gc = new GetConnection();
	Connection con;
	List<Users> lst = new ArrayList<Users>();
	System.out.println("userid="+userid1);
	ResultSet rs;
	
	try {
	
	con= gc.getConnection();
	Statement st=con.createStatement();
	rs=st.executeQuery("select * from userdetails where userid<>'"+userid1+"' and userid not in (select userid from accessperdocgroup where docId="+docid+")");
	Users vs;
	 
	while(rs.next()) {
		
		vs= new Users();
		vs.setUserid(rs.getString("userid"));
		vs.setUsernm(rs.getString("usernm"));
		vs.setEmailid(rs.getString("emailid"));
		vs.setMobileno(rs.getString("mobileno"));
		vs.setGender(rs.getString("gender"));
		vs.setPincode(rs.getString("pincode"));
		vs.setDob(rs.getString("dob"));
		vs.setAddr(rs.getString("addr"));
		vs.setUsertype(rs.getString("usertype"));
		vs.setState(rs.getString("state"));
		vs.setCity(rs.getString("city"));
		lst.add(vs);
	}
	try
	{
		con.close();
	}
	catch (Exception e) {
		// TODO: handle exception
	}
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return(lst);
}

public List<Users> getStudentReport(){
	
	GetConnection gc = new GetConnection();
	Connection con;
	List<Users> lst = new ArrayList<Users>(); 
	ResultSet rs;
	
	try {
	
	con= gc.getConnection();
	Statement st=con.createStatement();
	rs=st.executeQuery("select * from userdetails");
	Users vs;
	 
	while(rs.next()) {
		
		vs= new Users();
		vs.setUserid(rs.getString("userid"));
		vs.setUsernm(rs.getString("usernm"));
		vs.setEmailid(rs.getString("emailid"));
		vs.setMobileno(rs.getString("mobileno"));
		vs.setGender(rs.getString("gender"));
		vs.setPincode(rs.getString("pincode"));
		vs.setDob(rs.getString("dob"));
		vs.setAddr(rs.getString("addr"));
		vs.setUsertype(rs.getString("usertype"));
		vs.setState(rs.getString("state"));
		vs.setCity(rs.getString("city"));
		lst.add(vs);
	}
	try
	{
		con.close();
	}
	catch (Exception e) {
		// TODO: handle exception
	}
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return(lst);
}

	public String addNewUser()
	{
		GetConnection gc = new GetConnection();
		int y=0;
		
		Connection con;
		String st="";
		try {
		con=gc.getConnection();
		PreparedStatement pst;
		
		pst=con.prepareStatement("insert into users values(?,?,?,?,?,?,?);");

		pst.setString(1,userid);
		pst.setString(2,usernm);
		pst.setString(3,pswd);
		pst.setString(4,usertype);
		pst.setString(5,"active");
		
		pst.setString(6,pubidkey);
		pst.setString(7,privateIdkey);

		int x=pst.executeUpdate();
		
		if(x>0) {
			
			pst=con.prepareStatement("insert into userdetails values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			pst.setString(1,userid);
			pst.setString(2,usernm);
			pst.setString(3,usertype);
			pst.setString(4, addr);
			pst.setString(5, pincode);
			pst.setString(6,mobileno);
			pst.setString(7,emailid);
			pst.setString(8, dob);
			pst.setString(9, gender);
			pst.setString(10, "active");
			pst.setString(11, path);
			pst.setString(12, city);
			pst.setString(13, state);
			y=pst.executeUpdate();
		}
		else
			st="failure";
		
		if(y>0)
			st="success";
		else
			st="failure";
		try
		{
			con.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		IDKeyGeneration obj=new IDKeyGeneration();
		obj.setUserid(userid);
		pubidkey=obj.generateIdKey();
		obj.updateIdkey();
		privateIdkey=obj.generateIdKey();
		obj.updateIdkey1();
		try {
			Mail mail=new Mail();
			String msg="Dear user, your identity key is "+pubidkey;
			if(mail.sendMail(msg, emailid, "Identity Key")) {}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return st;
		
		
	}
	public String updateUser(String userid1)
	{
		GetConnection gc = new GetConnection();
		int y=0;
		
		Connection con;
		String st="";
		try {
		con=gc.getConnection();
		PreparedStatement pst;
		
		 if(!path.equals("NA"))
		 {
			pst=con.prepareStatement("update userdetails set mobileno=?,addr=?,city=?,state=?,emailid=?,photo=?,dob=?,pincode=? where userid=?");
			 
			pst.setString(1,mobileno);
			pst.setString(2,addr);
			pst.setString(3,city);
			pst.setString(4,state);
			pst.setString(5, emailid);
			pst.setString(6, path);
			pst.setString(7, dob);
			pst.setString(8, pincode);
			pst.setString(9, userid1);
		 }
		else
		{
			pst=con.prepareStatement("update userdetails set mobileno=?,addr=?,city=?,state=?,emailid=?,dob=?,pincode=?  where userid=?");
			 
			pst.setString(1,mobileno);
			pst.setString(2,addr);
			pst.setString(3,city);
			pst.setString(4,state);
			pst.setString(5, emailid); 
			pst.setString(6, dob);
			pst.setString(7, pincode);
			pst.setString(8, userid1);
		}
			y=pst.executeUpdate();
		 
		
		if(y>0)
			st="success";
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

}
