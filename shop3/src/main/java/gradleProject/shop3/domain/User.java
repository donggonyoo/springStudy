package gradleProject.shop3.domain;


import gradleProject.shop3.dto.UserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity(name="Usercipher")
@Table(name = "usercipher")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
	@Id
	private String userid;
	private String channel;
	private String password;
	private String username;
	private String phoneno;
	private String postcode;
	private String address;
	private String email;
	private Date birthday;

	public User (UserDto dto){
		this.userid = dto.getUserid();
		this.channel = dto.getChannel();
		this.password = dto.getPassword();
		this.username = dto.getUsername();
		this.phoneno = dto.getPhoneno();
		this.postcode = dto.getPostcode();
		this.address = dto.getAddress();
		this.email = dto.getEmail();
		this.birthday = dto.getBirthday();
	}

}
