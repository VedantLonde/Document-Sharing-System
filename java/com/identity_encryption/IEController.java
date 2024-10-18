package com.identity_encryption;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.Document;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import beans.Base64Encoder;
import beans.Mail;
import beans.RandomString;
import cryptography.Decryption;
import cryptography.Encryption;
import models.CheckUser;
import models.Documents; 
import models.Pass;
import models.PasswordRecovery;
import models.ShareDoc;
import models.Users; 
@Controller
public class IEController implements ErrorController{
	@RequestMapping("/home")
	public String index()
	{
		return "index.jsp";
	}
	@RequestMapping("/Cities")
	public String cities()
	{
		return "Cities.jsp";
	}

	 
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
        session.invalidate();
		return "Logout.jsp";
	}
	@RequestMapping("/regOffice")
	public String registration()
	{
		return "Register.jsp";
	}
	 
	@RequestMapping("/user")
	public String student()
	{
		return "User.jsp";
	}
	@RequestMapping("/dealer")
	public String staff()
	{
		return "Dealer.jsp";
	}
	@RequestMapping("/pubIdkeyAuth")
	public ModelAndView pubIdkeyAuth(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("PublicIdKeyAuth.jsp");
		mv.addObject("path",request.getParameter("path").toString().trim());
		mv.addObject("docId",request.getParameter("docId").toString().trim());
		mv.addObject("seckey",request.getParameter("seckey").toString().trim());
		return mv;
	}
	@RequestMapping("/OTPAuth")
	public ModelAndView OTPAuth(HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		if(request.getParameter("otp").toString().trim().equals(request.getParameter("uotp").toString().trim()))
		{
			mv.setViewName("Success.jsp");
			Mail mail=new Mail();
			String idkey="";
			CheckUser ck=new CheckUser();
			ck.setUserid(ses.getAttribute("userid").toString().trim());
			ck.getIdkey1();
			idkey=ck.getIdkey();
			String msg="Dear user, your public id key is "+idkey;
			try
			{
				if(mail.sendMail(msg,ses.getAttribute("emailid").toString().trim(), "Identity key")){}
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("err in mail ="+e.getMessage());
			}
			mv.addObject("activity","idkeysent");
		}
		else
		{
			mv.setViewName("Failure.jsp");
			 
			mv.addObject("activity","idkeysent");
		}
	    return mv;
	}
	@RequestMapping("/sendOTP")
	public ModelAndView sendOTP(HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("OTPVerification.jsp");
		RandomString rnd=new RandomString();
		String otp=rnd.getAlphaNumericString(4);
		Mail mail=new Mail();
		String msg="Dear user, your one time password is "+otp;
		try
		{
			if(mail.sendMail(msg, ses.getAttribute("emailid").toString().trim(),"OTP")) {}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("otp",otp);
	    return mv;
	}
	@RequestMapping("/pubIdkeyAuth1")
	public ModelAndView pubIdkeyAuth1(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("PublicIdKeyAuth1.jsp");
		mv.addObject("path",request.getParameter("path").toString().trim());
		mv.addObject("docId",request.getParameter("docId").toString().trim());
		mv.addObject("seckey",request.getParameter("seckey").toString().trim());
		return mv;
	}
	@SessionScope
	@RequestMapping("/PubIDKeyVerification1")
	public ModelAndView PubIDKeyVerification1(HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		System.out.println("pubidkey1="+request.getParameter("uotp").toString().trim());
		System.out.println("pubidkey="+ses.getAttribute("pubidkey").toString().trim());
		System.out.println("pubidkey1="+request.getParameter("uotp").toString().trim());
		if(request.getParameter("uotp").toString().trim().equals(ses.getAttribute("pubidkey").toString().trim()))
		{
			Documents doc=new Documents();
			String filepath=request.getServletContext().getRealPath("/")+"/Documents/";
			String filepath1=request.getServletContext().getRealPath("/")+"/Documents/temp/";
			String id=request.getParameter("path").toString().trim().split("\\.")[0];
			doc.setDocid(Integer.parseInt(id.trim()));
			String data=doc.readFile(filepath+id+".txt",filepath1+id+".txt");
			System.out.println("ex data="+data);
			String[] str=data.split("\\|");
			System.out.println("private id="+ses.getAttribute("pidkey").toString().trim());
			for(int i=0;i<str.length;i++)
			{
				System.out.println("private id="+ses.getAttribute("pidkey").toString().trim()+" "+str[i]);
			if(str[i].trim().equals(ses.getAttribute("pidkey").toString().trim()))
			{
				Decryption dec=new Decryption();
			 
				//String filepath1=request.getServletContext().getRealPath("/")+"/Documents/temp/";
			
				String infile,outFile;
				infile=filepath+request.getParameter("path").toString().trim();
				outFile=filepath1+request.getParameter("path").toString().trim();
				 String seckey=request.getParameter("seckey").toString().trim();
			
				 System.out.println("seckey="+seckey);
			  
				 dec.setSkey(seckey);
			 
				dec.decrypt(infile, outFile);
				mv.setViewName("download.jsp");
				mv.addObject("path",request.getParameter("path").toString().trim());
			break;
			}
			else
			{
				mv.setViewName("Failure.jsp");
				mv.addObject("activity","idauth1");
			}
			
			}
		 }
		else
		{
			mv.setViewName("Failure.jsp");
			mv.addObject("activity","idauth");
		}
		return mv;
	}
	@SessionScope
	@RequestMapping("/PubIDKeyVerification")
	public ModelAndView PubIDKeyVerification(HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		System.out.println("pubidkey1="+request.getParameter("uotp").toString().trim());
		System.out.println("pubidkey="+ses.getAttribute("pubidkey").toString().trim());
		System.out.println("pubidkey1="+request.getParameter("uotp").toString().trim());
		if(request.getParameter("uotp").toString().trim().equals(ses.getAttribute("pubidkey").toString().trim()))
		{
			Decryption dec=new Decryption();
			String filepath=request.getServletContext().getRealPath("/")+"/Documents/";
			String filepath1=request.getServletContext().getRealPath("/")+"/Documents/temp/";
			
			String infile,outFile;
			infile=filepath+request.getParameter("path").toString().trim();
			outFile=filepath1+request.getParameter("path").toString().trim();
			 String seckey=request.getParameter("seckey").toString().trim();
			
			 System.out.println("seckey="+seckey);
			  
			 dec.setSkey(seckey);
			 
			dec.decrypt(infile, outFile);
			mv.setViewName("download.jsp");
			mv.addObject("path",request.getParameter("path").toString().trim());
		 }
		else
		{
			mv.setViewName("Failure.jsp");
			mv.addObject("activity","idauth");
		}
		return mv;
	}
	@RequestMapping("/uploaddoc")
	public String uploaddoc()
	{
		return "UploadDocs.jsp";
	}
	@RequestMapping("/admin")
	public String admin()
	{
		return "Admin.jsp";
	}
	 @RequestMapping("/error")
    public String handleError() {
        //do something like logging
		return "home";
    }
  
    public String getErrorPath() {
        return "/error";
    }
    @RequestMapping("/check")
	public String check(CheckUser cu,HttpServletRequest request) {
		
		String st=cu.checkUser(request);
		
		return st;
	}	  
     
     
	@RequestMapping("/viewUsers")
	@SessionScope
	public ModelAndView viewUsers() {
		
		List<Users> lst = new ArrayList<Users>();
		Users vs = new Users();
		lst=vs.getStudentReport();
		ModelAndView mv=new ModelAndView();
		mv.addObject("std",lst);
		mv.setViewName("ViewUsersReport.jsp");
		return mv;
	}
	 
	 @RequestMapping("/shareWithGroups")
		public ModelAndView shareWithGroups(HttpSession ses,HttpServletRequest request)
		{
			List<Users> lst = new ArrayList<Users>();
			Users obj=new Users();
			lst=obj.getUserReport1(ses.getAttribute("userid").toString().trim(),request.getParameter("docid").toString().trim());
			 
			ModelAndView mv = new ModelAndView();

			mv.setViewName("ShareDoc.jsp");
			mv.addObject("docid",request.getParameter("docid").trim());
			mv.addObject("lst", lst); 
			System.out.println("lst="+lst.size());
			return mv;
			 
		}
	@RequestMapping("/viewdocs")
	@SessionScope
	public ModelAndView viewMyDocs(HttpSession ses) {
		ModelAndView mv=new ModelAndView();
		try
		{
		List<models.Documents> lst = new ArrayList<Documents>();
		//CallMinnerAPI vs = new CallMinnerAPI();
		Documents obj=new Documents();
		 
		  obj.getDocs(ses.getAttribute("userid").toString().trim());
		
		mv.addObject("std",obj.getLst());
		mv.setViewName("ViewMyDocs.jsp");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("err="+e.getMessage());
		}
		return mv;
	}
	 
	
	@RequestMapping("/regDoc")
	public ModelAndView regDoc(Documents stu,ServletRequest request,HttpSession ses)
	{String fileName="NA";
		
	ModelAndView mv=new ModelAndView();
	try
		 {
			 stu.setUserid(ses.getAttribute("userid").toString().trim());
			 
		  
		 try {
			 MultipartFile file=stu.getFile();
			 String filepath=request.getServletContext().getRealPath("/")+"/Documents/";
			 String filepath1=request.getServletContext().getRealPath("/")+"/Documents/temp/";
			 
			 stu.getId();
			 System.out.println("path="+filepath);
			 File f=new File(filepath);
			 f.mkdir();
			 f=new File(filepath1);
			 f.mkdir();
			  fileName=stu.getDocid()+"."+ file.getOriginalFilename().split("\\.")[1];
			 file.transferTo(new File(filepath1+"/"+fileName));
			 Encryption enc=new Encryption();
			 String seckey=ses.getAttribute("pidkey").toString().trim();
			 if(seckey.length()>16)
			 {
				 seckey=seckey.substring(0,15);
				 System.out.println("idkey1="+seckey);
			 }
			 enc.setSkey(seckey);
			
			 System.out.println("idkey="+seckey);
			 enc.encryptAES(filepath1+fileName, stu.getDocid(), filepath+fileName);
			 stu.setSeckey(enc.getSkey());
			f=new File(filepath1+fileName);
			f.delete();
		 }
		 catch (Exception e) {
			 System.out.println("ex="+e.getMessage());
		}
		  
		 stu.setFilePath(fileName);
		 
		 String st=stu.insert();
		 if(st.equals("success"))
				mv.setViewName("Success.jsp");
			else
				mv.setViewName("Failure.jsp");
		 }
		 catch (Exception e) {
			 System.out.println("in update="+e.getMessage());
				// TODO: handle exception
			 mv.setViewName("Failure.jsp");
			}
		 mv.addObject("activity","upload");
		 return mv;
	}
	@RequestMapping("/viewSharedDocs")
	@SessionScope
	public ModelAndView viewSharedDocs(HttpSession ses) {
		ModelAndView mv=new ModelAndView();
		try
		{
		List<models.Documents> lst = new ArrayList<Documents>();
		//CallMinnerAPI vs = new CallMinnerAPI();
		Documents obj=new Documents();
		 
		  obj.getDocs1(ses.getAttribute("userid").toString().trim());
		
		mv.addObject("std",obj.getLst());
		mv.setViewName("ViewSharedDocs.jsp");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("err="+e.getMessage());
		}
		return mv;
	}
	
	@RequestMapping("/ShareImgWithGroups")
	public ModelAndView SubmitReqDocs(HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		String metaFile,data="";
		 metaFile=request.getServletContext().getRealPath("/")+"/Documents/";
		 String metaFile1=request.getServletContext().getRealPath("/")+"/Documents/temp/";
			
		//System.out.println("docs="+request.getParameter("chkdoc").toString().trim());
		try {
		    Enumeration  e=request.getParameterNames();
		    String groupName="",docid="";
		    Vector v=new Vector();
		    Vector v1=new Vector();
		    Documents doc=new Documents();
		    while(e.hasMoreElements())
		    {
		    String Chknm=(String)e.nextElement();
		      if(Chknm.trim().equals("docid"))
		      {
		    	  docid=request.getParameter("docid").toString().trim();
		    	  System.out.println("docid="+docid);
		      }
		      else
		      {
		    	  v.addElement(Chknm.trim());
		    	  v1.addElement(request.getParameter(Chknm.trim()));
		    	  System.out.println("docid="+Chknm);
		      }
		    }
		      for(int i=0;i<v.size();i++)
		      {
		    	ShareDoc obj=new ShareDoc();
		    	obj.setGroupName(v.elementAt(i).toString().trim());
		    	obj.setUserid(v1.elementAt(i).toString().trim());
		    	obj.setMsgid(docid);
		    	obj.shareDocsWithGroups();
		    	CheckUser chk=new CheckUser();
		    	chk.setUserid(v1.elementAt(i).toString().trim());
		    	chk.getIdkey_Private();
		    	if(i==0)
		    	data=chk.getIdkey();
		    	else
		    	{
		    		data+="|"+ chk.getIdkey();
		    	}
		    	doc.setDocid(Integer.parseInt(docid.trim()));
		    	
		      }
		      doc.manageAccessPer(metaFile+docid+".txt",metaFile1+docid+".txt", data);
		    
		        
		    mv.setViewName("Success.jsp?type=ShareDoc");
		    mv.addObject("activity","ShareDoc");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("err="+e.getMessage());
		}
		return mv;
	}
	@RequestMapping("/updateuser")
	public ModelAndView updateuser(Users stu,ServletRequest request,HttpSession ses)
	{String fileName="NA";
		
	ModelAndView mv=new ModelAndView();
	try
		 {
			 stu.setUserid(ses.getAttribute("userid").toString().trim());
			 
		  
		 try {
			 MultipartFile file=stu.getFile();
			 String filepath=request.getServletContext().getRealPath("/")+"/Uploads/";
			 
			 
			 System.out.println("path="+filepath);
			 File f=new File(filepath);
			 f.mkdir();
			  fileName=stu.getUserid()+"."+ file.getOriginalFilename().split("\\.")[1];
			 file.transferTo(new File(filepath+"/"+fileName));
			 
		 }
		 catch (Exception e) {
			// TODO: handle exception
			// return "UserRegFailure.jsp";
		}
		 if(!fileName.equals("NA"))
		 {
			 ses.setAttribute("photo", fileName);
		 }
		 stu.setPath(fileName);
		 String st=stu.updateUser(stu.getUserid());
		 if(st.equals("success"))
				mv.setViewName("Success.jsp");
			else
				mv.setViewName("Failure.jsp");
		 }
		 catch (Exception e) {
			 System.out.println("in update="+e.getMessage());
				// TODO: handle exception
			 mv.setViewName("Failure.jsp");
			}
		 mv.addObject("activity","StudProfile");
		 return mv;
	}
	@RequestMapping("/registeruser")
	public ModelAndView registeruser(Users stu,ServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		 try
		 {MultipartFile file=stu.getFile();
		 String filepath=request.getServletContext().getRealPath("/")+"/Uploads/";
		 
		 
		 System.out.println("path="+filepath);
		 File f=new File(filepath);
		 f.mkdir();
		  
		 try {
			  
			 String fileName=stu.getUserid()+"."+ file.getOriginalFilename().split("\\.")[1];
			 file.transferTo(new File(filepath+"/"+fileName));
			 stu.setPath(fileName);
			 String st=stu.addNewUser();
				if(st.equals("success"))
					mv.setViewName("Success.jsp");
				else
					mv.setViewName("Failure.jsp");
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 mv.setViewName("Failure.jsp");
		}}
		 catch (Exception e) {
				// TODO: handle exception
			 mv.setViewName("Failure.jsp");
			}
		 mv.addObject("activity","UserReg");
		 return mv;
		
	}
	@RequestMapping("/forgetpassword")
	public String forgetpassword() {
		
		return("ForgetPassword.jsp");
	}
	@RequestMapping("/recoverpassword")
	public String recoverpassword(PasswordRecovery pr) {
		
		String sts=pr.getNewPassword();
		
		return(sts);
	}
	@RequestMapping("/ChangePass")
	public String ChangePass()
	{
		return "ChangePass.jsp";
	}
	@RequestMapping("/ChangePassService")
	public ModelAndView ChangePassService(Pass eobj,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		 try
		 {
			 
			 eobj.setUserid(ses.getAttribute("userid").toString().trim());
			 if(eobj.changePassword())
			 { 
				 mv.setViewName("Success.jsp");
			 }
			 else
			 { 
				 mv.setViewName("Failure.jsp");
			 }
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println("err="+e.getMessage());
			 mv.setViewName("Failure.jsp");
		}
		 mv.addObject("activity","changePass");
		 return mv;
		 
	}

	 

}
 
