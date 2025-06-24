package ex01_hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main_01 {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		byte[] plain = null;
		byte[] hash = null;
		Set<String> algorithms = Security.getAlgorithms("MessageDigest");
		System.out.println(algorithms);
		String[] algo = {"MD5","SHA-1","SHA-256","SHA-512"};
		/*
		 * MD5 : 128bit , SHA-1 : 160bit 
		 * SHA-256 : 256bit , SHA-512 : 512bit
		 */
		System.out.println("해쉬값을 구할 문자열 입력");
		Scanner scan = new Scanner(System.in);
		String str = scan.nextLine();
		plain = str.getBytes();
		
		for (String a1 : algorithms) {
			MessageDigest md = MessageDigest.getInstance(a1);
			hash = md.digest(plain);
			System.out.println(a1+"해쉬값 크기 : "+(hash.length*8)+"bits");
			System.out.print(" 해쉬값 : ");
			
		
			for (byte b : hash) {
				System.out.printf("%02X",b);
				//%02X는 형식 지정자로, 바이트 값을 16진수(hexadecimal)로 출력.				
			}
			System.out.println();
			
		}
		
		
	}

}
