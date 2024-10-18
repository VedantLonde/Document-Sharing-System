package beans;

import java.sql.*;

public class GetConnection {

	private Connection dbconnection;
    public GetConnection()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
          //  dbconnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/dataIntegrityDB?user=root&password=crosspolo&useSSL=false&allowPublicKeyRetrieval=true");
              dbconnection=DriverManager.getConnection("jdbc:mysql://u0w5xlqdqh6sb0vb:3uXtZgSEhluAmVhvbaNV@bgktfbxonqfztpis5x8p-mysql.services.clever-cloud.com:3306/bgktfbxonqfztpis5x8p");
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
    }
    
    public Connection getConnection()
    {
        return(dbconnection);
    }
	
}
