package gradleProject.shop3.exception;

import lombok.Getter;

@Getter
public class LogoutException extends RuntimeException {

    private String url;

    public LogoutException(String msg, String url) {
        super(msg);
        this.url = url;
    }
}
