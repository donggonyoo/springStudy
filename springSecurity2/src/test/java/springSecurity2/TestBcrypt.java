package springSecurity2;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class TestBcrypt {
    public static void main(String[] args) {
        System.out.println("new BCryptPasswordEncoder().encode('123');");
        String encode = new BCryptPasswordEncoder().encode("123");
        String encode2 = new BCryptPasswordEncoder().encode("123");
        System.out.println("123 -> : "+encode);
        System.out.println("123 -> : "+encode2);

        System.out.println("\n검증은 BCryptPasswordEncoder().matches(암호화x,암호화O)");
        boolean matches1 = new BCryptPasswordEncoder().matches("123", encode);
        boolean matches2 = new BCryptPasswordEncoder().matches("15523", encode);
        System.out.println("123? : "+matches1);
        System.out.println("15523? : "+matches2);


    }

}
