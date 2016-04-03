import java.sql.*;


public class SqlConnect {
	

	private final static String connectionUrl = "jdbc:sqlserver://localhost; user=sa; password=Index_1234; database=";
	//private final static String connectionUrl2 = "jdbc:sqlserver://sk5-410-db-edu1:1433;" +
	  //       "databaseName=Schekalov_FishOrg;integratedSecurity=true;";
	//private final static String connectionUrl3 = "jdbc:sqlserver://localhost; user=sa; password=Index_1234; database=FishAppDB_1";
	private Connection con;
	public Connection isConnect(String nameDataBase){
		try{
		   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e){
			return null;
		}	
		     
			try {
				 con = DriverManager.getConnection(connectionUrl+nameDataBase);
			} catch(SQLException e){
				
				System.out.println(e.getMessage());
				return null;
			}
			
		return con;
	}
	
				
		

}
