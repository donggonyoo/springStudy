package gradleProject.shop3.service;

import gradleProject.shop3.domain.Mail;
import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.MailDto;
import gradleProject.shop3.dto.UserDto;
import gradleProject.shop3.repository.UserRepository;
import gradleProject.shop3.util.CipherUtil;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Service
public class UserService {

//	@Value("${resources.dir}")
//	private String RESOURCES_DIR;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("D:/dongGit/springStudy/shop3/src/main/resources/")
	private String RESOURCE_DIR;

	public void userInsert(User user) {
		userRepository.save(user);
	}

	public User selectUser(String userid) {
		//userid에 해당하는 데이터조회후반환
		Optional<User> byId = userRepository.findById(userid);
		User user = byId.get();
		return user;

		//@id로 설정된값에 해당하는 데이터조회
	}

	//
	public void userUpdate(User user) {
		userRepository.save(user);
	}

	//
	public void userDelete(String userid) {
		userRepository.deleteById(userid);

	}

	public void updatePassword(String userid, String chgpass) {
		userRepository.chgpass(userid, chgpass);
	}

	//
	public String getSearch(User user) throws Exception {

		if (user.getUserid() == null) { //id찾기겠지
			String email = user.getEmail();
			String phoneno = user.getPhoneno();

			List<User> users = userRepository.searchByUserid(phoneno);
			for (User u : users) {
				u = CipherUtil.emailDecrypt(u);
				if (u.getEmail().equalsIgnoreCase(email)) {
					return u.getUserid();
				}
			}
		} else {
			User user1 = CipherUtil.emailEncrypt(user);
			return userRepository.searchByPassword(user1.getUserid(), user1.getEmail(), user1.getPhoneno());
		}
		return null;

	}

	public List<User> getUserList(String[] idchks) {
		return userRepository.findByUseridIn(idchks);
	}


	public List<User> selectList() {
		return userRepository.findAll();
	}


	private final class MyAuthenticator extends Authenticator {
		private String id;
		private String pw;

		public MyAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}
	}


	public boolean mailSend(MailDto mail) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			// MimeMessageHelper: 복잡한 메일(첨부파일, HTML 등)을 쉽게 구성하도록 돕는 클래스
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			// 보내는 사람 이메일 설정 (application.properties의 username과 일치해야 함)
			helper.setFrom(mail.getGoogleid() + "@gmail.com");

			// 받는 사람 이메일 설정 (쉼표로 구분된 문자열을 배열로 변환)
			helper.setTo(mail.getRecipient().split(","));

			// 메일 제목 설정
			helper.setSubject(mail.getTitle());

			// 메일 내용 설정 (두 번째 인자 true: HTML 형식으로 보내겠다는 의미)
			helper.setText(mail.getContents(), true);

			// 첨부파일 처리
			if (mail.getFile1() != null && !mail.getFile1().isEmpty()) {
				for (MultipartFile mf : mail.getFile1()) {
					if (!mf.isEmpty()) {
						// 원본 파일 이름으로 첨부파일 추가
						helper.addAttachment(mf.getOriginalFilename(), new ByteArrayResource(mf.getBytes()));
					}
				}
			}

			// 최종 메일 발송
			javaMailSender.send(message);
			return true; // 성공 시 true 반환

		} catch (Exception e) {
			e.printStackTrace();
			return false; // 실패 시 false 반환
		}
	}

	//첨부파일을 처리해 MimeBodyPart로 변환.
	private BodyPart bodyPart(MultipartFile mf) {
		MimeBodyPart body = new MimeBodyPart();
		String orgFile = mf.getOriginalFilename();
		String path = RESOURCE_DIR + "mailupload/"; //업로드되는 폴더
		File f1 = new File(path);
		if (!f1.exists()) {
			f1.mkdir();
		}
		File f2 = new File(path + orgFile);
		try {
			mf.transferTo(f2);

			body.attachFile(f2);
			body.setFileName(orgFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}

	public void mailfileDelete(Mail mail) {
		String path = RESOURCE_DIR + "mailupload/"; //업로드되는 폴더
		ArrayList<String> fileNames = new ArrayList<>();
		for (MultipartFile mf : mail.getFile1()) {
			fileNames.add(mf.getOriginalFilename());
		}
		for (String f : fileNames) {
			File file = new File(path, f);
			System.out.println(file.toString() + "삭제 성공");
			file.delete();
		}
	}
}

