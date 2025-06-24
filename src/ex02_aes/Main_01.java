package ex02_aes;

public class Main_01 {
    public static void main(String[] args) {
        String plain1 = "안녕하세요홍길동입니다";
        String cipher1 = CipherUtil.encrypt(plain1);//암호

        System.out.println("암호문"+cipher1);
        String cipher2 = CipherUtil.dencrypt(cipher1);//복호
        System.out.println("복호문"+cipher2);
    }
}
