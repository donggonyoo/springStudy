package gradleProject.shop3.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Mail {

	private String googleid;
	private String googlepw;
	private String recipient;
	private String title;
	private String mtype;
	private List<MultipartFile> file1;
	private String contents;
}
