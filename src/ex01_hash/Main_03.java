package ex01_hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

public class Main_03 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/gdjdb","gduser","1234");
        PreparedStatement pstmt = conn.prepareStatement("select password,username from usercipher where userid=?");
        Scanner scan=new Scanner(System.in);
        System.out.println("아이디를 입력하세요");
        String id = scan.nextLine();
        System.out.println("비밀번호를 입력하세요");
        String pw = scan.next();
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String hashpass="";
            byte[] plain= pw.getBytes();
            byte[] hash= md.digest(plain);
            for(byte b : hash) {
                hashpass += String.format("%02x", b);
            }
            System.out.println(hashpass);
            System.out.println(rs.getString("password"));
            if(rs.getString("password").equals(hashpass)) {
                System.out.println("반가워요"+rs.getString("username"));
            }
            else{
                System.out.println("비번오류");
            }
        }
        else{
            System.out.println("아이디없음");
        }


    }

}
