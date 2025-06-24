package ex02_aes;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
    private static byte[] randomKey;
    private final static byte[] iv = new byte[] {
            (byte)0x8E,0x12,0x39,(byte)0x90,
            0x07,0x72,0x6F,(byte)0x5A,
            (byte)0x8E,0x12,0x39,(byte)0x90,
            0x07,0x72,0x6F,(byte)0x5A,
    };

    static Cipher cipher;
    static {
        try {
            /*
            AES : 암호화알고리즘
             */
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] getRandomKey(String algo) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algo);
        keyGen.init(128);
        SecretKey key = keyGen.generateKey();
        return key.getEncoded();
    }

    public static String encrypt(String plain) {
        byte[] cipherMsg = new byte[102];
        try {
            randomKey = getRandomKey("AES");
            Key key = new SecretKeySpec(randomKey, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            cipherMsg = cipher.doFinal(plain.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteToHex(cipherMsg).trim();
    }

    public static String dencrypt(String cipherMsg) {
        byte[] plainMsg = new byte[1024];
        try {
            Key key = new SecretKeySpec(randomKey, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            plainMsg= cipher.doFinal(hexToByte(cipherMsg.trim()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(plainMsg).trim();
    }
    private static String byteToHex(byte[] cipherMsg) {
        if(cipherMsg == null) {
            return null;
        }
        String str = "";
        for(byte b : cipherMsg) {
            str += String.format("%02x", b);
        }
        return str;
    }

    private static byte[] hexToByte(String str) {
        if(str== null || str.length() <2) {
            return null;
        }
        int len = str.length() /2 ;
        byte[] buf = new byte[len];
        for(int i = 0; i <len; i++) {
            //16진수임을 명시
            //ex) subString의 결과가 A1 이라면 161로 변환할것임
            buf[i] = (byte)Integer.parseInt(str.substring(i *2, i *2 +2), 16);
        }
        return buf;
    }

}