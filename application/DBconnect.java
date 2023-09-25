package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnect {
	
		public Connection conn;
		
		public Connection getConn() {
			 
			String driver = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "school";
			String pw = "school";
			
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, id, pw);
				System.out.println("DB접속 성공~ 20230623");
			} catch (Exception e) {
				System.out.println("DB접속 실패~ 20230623");
				e.printStackTrace();
			}
			
			
			return conn;
			
		}
}
