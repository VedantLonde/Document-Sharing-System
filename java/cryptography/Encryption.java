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
 
public class Encryption {
    private String skey;
    private List lstseq;
    private List<TempBytes> lstb;
    private int n,len;
    private double size,senctime,encsize1,endtime,enctime,etime;
    private int encsize;
    private double  encsizeAES,endtimeAES,enctimeAES,etimeAES,senctimeAES;
    public int getLen() {
        return len;
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

    public double getEncsize1() {
        return encsize1;
    }

    public void setEncsize1(double encsize1) {
        this.encsize1 = encsize1;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public List<TempBytes> getLstb() {
        return lstb;
    }

    public void setLstb(List<TempBytes> lstb) {
        this.lstb = lstb;
    }

   
    public double getEtime() {
        return etime;
    }

    public void setEtime(double etime) {
        this.etime = etime;
    }

    public double getSenctime() {
        return senctime;
    }

    public void setSenctime(double senctime) {
        this.senctime = senctime;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getEncsize() {
        return encsize;
    }

    public void setEncsize(int encsize) {
        this.encsize = encsize;
    }

    public List getLstseq() {
        return lstseq;
    }

    public void setLstseq(List lstseq) {
        this.lstseq = lstseq;
    }

    
    

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
    
    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }
    
    public String getSeckey() 
    {
      String passwordToHash = skey;
      String generatedPassword = null;

      try 
      {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // Add password bytes to digest
        md.update(passwordToHash.getBytes());

        // Get the hash's bytes
        byte[] bytes = md.digest();

        // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
          sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // Get complete hashed password in hex format
        generatedPassword = sb.toString();
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
      System.out.println(generatedPassword);
      skey=generatedPassword;
      return generatedPassword;
    }
        public void encryptAES(String inFile,int id,String outpath)
    {
        
        try{
            skey="";
            lstb=new ArrayList<TempBytes>();
            System.out.println("inputfile="+inFile);
          String[] ss=inFile.split("\\.");
          String ext=ss[ss.length-1];
              byte[] b= Files.readAllBytes(Paths.get(inFile));
              
              
              byte[] benc=transformationAES(b,(getSeckey()));
                
               // File fout=new File(outpath);
              //fout.mkdir();
              FileOutputStream Fout=new FileOutputStream(outpath);
              Fout.write(benc);
              Fout.flush();
              Fout.close();
              encsizeAES=Double.parseDouble(String.valueOf(benc.length))/1024.0;
        }
        catch(Exception ex)
        {
           System.out.println("err="+ex.getMessage());
        }
         
    }
        public void encryptAES1(String inFile,int id,String outpath)
        {
            
            try{
                 
                lstb=new ArrayList<TempBytes>();
                System.out.println("inputfile="+inFile);
              String[] ss=inFile.split("\\.");
              String ext=ss[ss.length-1];
                  byte[] b= Files.readAllBytes(Paths.get(inFile));
                  
                  
                  byte[] benc=transformationAES(b,(skey));
                    
                   // File fout=new File(outpath);
                  //fout.mkdir();
                  FileOutputStream Fout=new FileOutputStream(outpath);
                  Fout.write(benc);
                  Fout.flush();
                  Fout.close();
                  encsizeAES=Double.parseDouble(String.valueOf(benc.length))/1024.0;
            }
            catch(Exception ex)
            {
               System.out.println("err="+ex.getMessage());
            }
             
        }
    public byte[] transformation(byte[] msg,String key)
{
    byte[] out=null;
    try{
        int cnt=0;
        byte[] keyb=key.getBytes();
        AESencrp.setKeyValue(keyb);
      //  etime=0;
         senctime=System.nanoTime();
        out=AESencrp.encryptB(msg);
         endtime=System.nanoTime();
         enctime=enctime+(endtime-senctime);
         //enctime+=etime;
    }
    catch(Exception ex)
    {
        System.out.println("error in transformation="+ex.getMessage());
    }
    return out;
}
     public byte[] transformationAES(byte[] msg,String key)
{
    byte[] out=null;
    try{
        int cnt=0;
        byte[] keyb=key.getBytes();
        AESencrp.setKeyValue(keyb);
         senctimeAES=System.nanoTime();
        out=AESencrp.encryptB(msg);
         endtimeAES=System.nanoTime();
         etimeAES=endtimeAES-senctimeAES;
    }
    catch(Exception ex)
    {
        System.out.println("error in transformation="+ex.getMessage());
    }
    return out;
}
    public List generateKeys(int n)
    {
        List keys=new ArrayList();
        try{
            while(keys.size()<n)
            {
            Random rnd=new Random();
            int n1=rnd.nextInt(9999)+1111;
            if(!keys.contains(n1))
            {
                if(String.valueOf(n1).length()>4)
                    continue;
                else
                keys.add(n1);
            }
            }
        }
        catch(Exception eX)
        {
            System.out.println("keys err="+eX.getMessage());
        }
        return keys;
    }
  
    
}
