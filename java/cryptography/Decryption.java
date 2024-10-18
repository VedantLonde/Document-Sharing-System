/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptography;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.*;
import org.apache.tomcat.jni.Directory;
 
public class Decryption {
    private byte[] out;
    private String skey;
    private List<TempBytes> lstbytes;
    private int n;
    private double size,senctime,encsize1,endtime,enctime,etime;
    private int encsize;
    private double  encsizeAES,endtimeAES,enctimeAES,etimeAES,senctimeAES;
   
   
    
    public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getSenctime() {
		return senctime;
	}

	public void setSenctime(double senctime) {
		this.senctime = senctime;
	}

	public double getEncsize1() {
		return encsize1;
	}

	public void setEncsize1(double encsize1) {
		this.encsize1 = encsize1;
	}

	public double getEndtime() {
		return endtime;
	}

	public void setEndtime(double endtime) {
		this.endtime = endtime;
	}

	public double getEnctime() {
		return enctime;
	}

	public void setEnctime(double enctime) {
		this.enctime = enctime;
	}

	public double getEtime() {
		return etime;
	}

	public void setEtime(double etime) {
		this.etime = etime;
	}

	public int getEncsize() {
		return encsize;
	}

	public void setEncsize(int encsize) {
		this.encsize = encsize;
	}

	public double getEncsizeAES() {
		return encsizeAES;
	}

	public void setEncsizeAES(double encsizeAES) {
		this.encsizeAES = encsizeAES;
	}

	public double getEndtimeAES() {
		return endtimeAES;
	}

	public void setEndtimeAES(double endtimeAES) {
		this.endtimeAES = endtimeAES;
	}

	public double getEnctimeAES() {
		return enctimeAES;
	}

	public void setEnctimeAES(double enctimeAES) {
		this.enctimeAES = enctimeAES;
	}

	public double getEtimeAES() {
		return etimeAES;
	}

	public void setEtimeAES(double etimeAES) {
		this.etimeAES = etimeAES;
	}

	public double getSenctimeAES() {
		return senctimeAES;
	}

	public void setSenctimeAES(double senctimeAES) {
		this.senctimeAES = senctimeAES;
	}

	public byte[] getOut() {
        return out;
    }

    public void setOut(byte[] out) {
        this.out = out;
    }

    public List<TempBytes> getLstbytes() {
        return lstbytes;
    }

    public void setLstbytes(List<TempBytes> lstbytes) {
        this.lstbytes = lstbytes;
    }
    
    
     public void decrypt(String inFile, String outpath)
    {
        int K=0,outsize=0; 
        try{
          System.out.println("path for aes="+inFile +" k="+skey);
             byte[] b= Files.readAllBytes(Paths.get(inFile));
           
                    
               byte[] benc=transformationAES(b,skey);    
            
               
              System.out.println("outputpath="+outpath);
             
              FileOutputStream Fout=new FileOutputStream(outpath);
              Fout.write(benc);
              Fout.flush();
              Fout.close();
        }
        catch(Exception ex)
        {
           System.out.println("err="+ex.getMessage());
        }
         
    } 
      public byte[] transformationAES(byte[] msg,String key)
{
    byte[] out=null;
    try{
        int cnt=0;
        byte[] keyb=key.getBytes();
        AESencrp.setKeyValue(keyb);
        senctimeAES=System.nanoTime();
        out=AESencrp.decryptB(msg);
        endtimeAES=System.nanoTime();
        etimeAES=endtimeAES-senctimeAES;
    }
    catch(Exception ex)
    {
        System.out.println("error in transformation33="+ex.getMessage());
    }
    return out;
}
     
}
