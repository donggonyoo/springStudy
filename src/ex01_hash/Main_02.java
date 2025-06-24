package ex01_hash;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * usercipher테이블 생성 , useraccount와 같은 테이블로생성하기
 * Create Table usercipher select * from useracoount
 * 2.usercipher의 password크기를 300으로변경
 * 3.userid를 기본키로설정
 * 
 * useracoount테이블을 읽어서 usercipher테이블의 password를 
 * SHA-256 알고리즘이용 해쉬값저장
 */
public class Main_02 {
	public static void main(String[] args)  throws Exception{
		Class.forName("org.mariadb.jdbc.Driver");
		Connection conn = 
				DriverManager.getConnection("jdbc:mariadb://localhost:3306/gdjdb","gduser","1234");
		PreparedStatement pstmt = conn.prepareStatement("select userid,password from useraccount");
		ResultSet rs = pstmt.executeQuery();//쿼리실행 후 반환
		while(rs.next()) {
			String id = rs.getString("userid");
			String pass = rs.getString("password");
			if(pass==null)continue;
			MessageDigest md = MessageDigest.getInstance("SHA-256"); //SHA-256알고리즘사용
			String hashpass= "";
			byte[] plain = pass.getBytes();
			byte[] hash = md.digest(plain);
			for(byte b : hash) {
				hashpass += String.format("%02x", b);
			}
			pstmt.close();
			pstmt = conn.prepareStatement("update usercipher set password=? where userid=?");
			pstmt.setString(1, hashpass);
			pstmt.setString(2, id);
			pstmt.executeUpdate();


		}
		
		
		
		
	}

}
