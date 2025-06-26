package gradleProject.shop3.controller;

import gradleProject.shop3.domain.Sale;
import gradleProject.shop3.domain.SaleItem;
import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.UserDto;
import gradleProject.shop3.exception.ShopException;
import gradleProject.shop3.mapper.UserMapper;
import gradleProject.shop3.service.ShopService;
import gradleProject.shop3.service.UserService;
import gradleProject.shop3.util.CipherUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper userMapper;
	@Autowired
	private ShopService shopService;

    // BoardController 의존성 제거 (필요 없는 경우)
    /*
    private final BoardController boardController;
    UserController(BoardController boardController) {
        this.boardController = boardController;
    }
    */

    @GetMapping("*") // GET 방식 모든 요청시 호출 (회원가입/로그인 폼 등)
    public String form(Model model, HttpSession session, HttpServletRequest request) {
        User loginUser = (User) session.getAttribute("loginUser");

        // 로그인 상태인 경우 마이페이지로 리다이렉트
        if (loginUser != null && StringUtils.hasText(loginUser.getUserid())) {
            return "redirect:/user/mypage?userid=" + loginUser.getUserid();
        }

        // 현재 요청 경로에 따라 적절한 뷰 설정 (더 명확하게 분리하는 것이 좋음)
        // 예를 들어, /user/join 요청은 user/join 뷰, /user/login 요청은 user/login 뷰
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/user/join")) {
            model.addAttribute("user", new User()); // 폼 바인딩용 User 객체
            model.addAttribute("title", "회원가입");
            return "user/join";
        } else if (requestURI.endsWith("/user/login")) {
            model.addAttribute("user", new User()); // 폼 바인딩용 User 객체
            model.addAttribute("title", "로그인");
            return "user/login";
        } else {
            // 그 외의 * 요청에 대한 기본 처리
            model.addAttribute("user", new User());
            model.addAttribute("title", "회원가입"); // 기본적으로 회원가입 폼을 보여주는 것으로 가정
            return "user/join";
        }
    }

    @PostMapping("join")
    public String userAdd(@Valid UserDto userDto, BindingResult bresult, Model model) {
        model.addAttribute("title", "회원가입"); // 페이지 제목 설정

        if (bresult.hasErrors()) {
            bresult.reject("error.input.user", "입력 값을 확인해 주세요."); // 전반적인 입력 오류
            return "user/join"; // 오류 발생 시 다시 user/join 뷰로
        }

        try {
            // 1. 비밀번호 해쉬함수로 해쉬값으로 변경
            // 2. 이메일을 암호화 처리
            // userid의 해쉬값
            // email 암호화
            String cipherPass = CipherUtil.makehash(userDto.getPassword());
            userDto.setPassword(cipherPass);
            String cipherUserid = CipherUtil.makehash(userDto.getUserid());
            String cipherEmail = CipherUtil.encrypt(userDto.getEmail(), cipherUserid);
            userDto.setEmail(cipherEmail);

            User user = userMapper.toEntity(userDto);

            service.userInsert(user);
        } catch (DataIntegrityViolationException e) { // 키값 중복된 경우 (아이디 중복)
            e.printStackTrace();
            bresult.reject("error.duplicate.user", "이미 존재하는 아이디입니다."); // 아이디 중복 오류
            return "user/join";
        } catch (Exception e) { // 기타 예외
            e.printStackTrace();
            bresult.reject("error.join.failed", "회원가입 중 알 수 없는 오류가 발생했습니다."); // 일반적인 회원가입 실패 메시지
            return "user/join";
        }
        return "redirect:/user/login"; // 성공 시 로그인 페이지로 리다이렉트 (절대 경로)
    }

    @GetMapping("login")
    public String callLogin(HttpSession session,Model model) {
        String clientId = "네이버의 client ID";
        clientId = "RzGew7S74e2kohOsbFV7";
        String redirectURL = null;
        try {
            redirectURL = URLEncoder.encode("http://localhost:8083/user/naverlogin","UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130,random).toString();
        String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        apiURL += "&client_id="+clientId;
        apiURL += "&redirect_uri="+redirectURL;
        apiURL += "&state=" + state;
        model.addAttribute(new User());
        model.addAttribute("apiURL",apiURL);
        session.getServletContext().setAttribute("state", state);
        session.getServletContext().setAttribute("session", session);
        System.out.println("1.session.id="+session.getId());
        return "user/login";
    }

    @RequestMapping("naverlogin")
    public String naverLogin(String code,String state,HttpSession session) {
        System.out.println("2.session.id = "+session.getId());
        String clientId = "클라이언트ID값"; //애플리케이션 클라이언트ID값
        clientId = "RzGew7S74e2kohOsbFV7";
        String clientSecret = "클라이언트 시크릿값";//애플리케이션 seceret값
        clientSecret = "IeoYH5E8AW";
        String redirectURL = null;
        try {
            redirectURL = URLEncoder.encode("YOUR_CALLBACK_URL","UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + clientId;
        apiURL += "&client_secret=" + clientSecret;
        apiURL += "&redirect_uri=" +redirectURL;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;
        System.out.println("redirectURL : "+redirectURL);
        System.out.println("code="+code+",state="+state);
        //String access_token="";
        //String refresh_token = "";
        StringBuffer res = new StringBuffer();

        System.out.println("apiURL : "+apiURL);
        try {
            URL url = new URL(apiURL);
            //apiURL연결 성공
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            System.out.println("responseCode="+responseCode);
            if(responseCode==200) {
                //정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            } else {
                // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            System.out.println("responseCode :: "+responseCode);
            if(responseCode==200) {
                System.out.println("성공");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //JSON형태의 문자열데이터 -> JSON객체로변경위함
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject)parser.parse(res.toString());
            System.out.println("json : "+json.toString());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String token = (String)json.get("access_token");
        System.out.println("token : "+token);
        String header = "Bearer "+token;
        try {
            apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            res = new StringBuffer();
            if(responseCode ==200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            while((inputLine=br.readLine())!=null) {
                res.append(inputLine);
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //res : 로그인한 사용자의 정보 전달(JSON형식)
        try {
            json = (JSONObject)parser.parse(res.toString());
            System.out.println("json : "+json.toString());

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ShopException("네이버 오류발생", "login");
        }

        JSONObject jsondetail = (JSONObject)json.get("response");
        System.out.println("@ jsonDetail : @"+jsondetail);

        String email = jsondetail.get("email").toString();
        String userid = email.substring(0,email.indexOf("@"));
        User user = service.selectUser(userid);
        if(user==null) {
            user = new User();
            //user.setUserid(userid);
            user.setUsername(jsondetail.get("name").toString());
            String phone = jsondetail.get("mobile").toString();
            user.setUserid(email.substring(0,email.indexOf("@")));
            user.setEmail(email);
            user.setPhoneno(phone);
            user.setChannel("naver");
            service.userInsert(user);
        }
        session.setAttribute("loginUser", user);
        return "redirect:mypage?userid="+user.getUserid();
    }

    @PostMapping("login")
    public String login(User user, BindingResult bresult, Model model, HttpSession session) throws Exception {
        model.addAttribute("title", "로그인"); // 페이지 제목 설정

        // 사용자 ID 및 비밀번호 길이 검증
        if (user.getUserid().trim().length() < 3 || user.getUserid().trim().length() > 10) {
            bresult.rejectValue("userid", "error.required.userid", "아이디는 3~10자리여야 합니다.");
        }
        if (user.getPassword().trim().length() < 3 || user.getPassword().trim().length() > 10) {
            bresult.rejectValue("password", "error.required.password", "비밀번호는 3~10자리여야 합니다.");
        }

        if (bresult.hasErrors()) { // 등록된 오류 존재?
            bresult.reject("error.input.check", "입력 값을 확인해 주세요."); // 전반적인 입력 확인 메시지
            return "user/login"; // 오류 발생 시 다시 user/login 뷰로
        }

		User dbUser = service.selectUser(user.getUserid());
		if(dbUser == null) { // 아이디 없음
			bresult.reject("error.login.id", "존재하지 않는 아이디입니다.");
			return "user/login";
		}

		if(CipherUtil.makehash(user.getPassword()).equalsIgnoreCase(dbUser.getPassword())) { // 비밀번호 일치
            user = emailDecrypt(dbUser);
            session.setAttribute("loginUser", user);
			return "redirect:/user/mypage?userid=" + user.getUserid(); // 마이페이지로 리다이렉트 (절대 경로)
		}
        else { // 비밀번호 불일치
			bresult.reject("error.login.password", "비밀번호가 일치하지 않습니다.");
			return "user/login";
		}
	}

	@RequestMapping("mypage")
	public String idCheckMypage( Model model,@RequestParam("userid") String userid,HttpSession session) throws Exception {
		model.addAttribute("title", "내 정보"); // 페이지 제목 설정

        System.out.println("userid : "+userid);
		User user = service.selectUser(userid);
        User user1 = emailDecrypt(user);

        List<Sale> salelist = shopService.saleList(userid);

		model.addAttribute("user", user1);
		model.addAttribute("salelist", salelist);

		return "user/mypage"; // user/mypage 뷰 반환
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 무효화
		return "redirect:/user/login"; // 로그인 페이지로 리다이렉트 (절대 경로)
	}
    private User emailDecrypt(User user) throws Exception {
        String key = CipherUtil.makehash(user.getUserid());
        String plainEmail = CipherUtil.decrypt(user.getEmail(),key);
        System.out.println(plainEmail);
        user.setEmail(plainEmail);
        return user;
    }

    private User emailEncrypt(User user) throws Exception {
        String email = user.getEmail();
        String key = CipherUtil.makehash(user.getUserid());
        String hashEmail = CipherUtil.encrypt(user.getEmail(),key);
        user.setEmail(hashEmail);
        return user;
    }
//
//	// 로그인 상태, 본인정보만 조회 검증 => AOP 클래스 (UserLoginAspect.userIdCheck)
//	// /user/update, /user/delete 요청을 각각 별도의 GetMapping으로 분리하는 것이 더 명확합니다.
//	// 기존의 * 방식 처리에서 특정 뷰를 반환하기 위해 HttpServletRequest를 사용하는 예시
	@GetMapping({"update", "delete"})
	public String idCheckUser(@RequestParam("userid") String userid, Model model, HttpServletRequest request) throws Exception {
		User user = service.selectUser(userid);
        UserDto userDto = new UserDto(user);
        model.addAttribute("userDto", userDto);
		// 요청 경로에 따라 뷰 이름 설정
		String requestURI = request.getRequestURI(); // 현재 요청의 URI 가져오기
		if (requestURI.endsWith("/user/update")) {
			model.addAttribute("title", "정보 수정");
			return "user/update";
		} else if (requestURI.endsWith("/user/delete")) {
			model.addAttribute("title", "회원 탈퇴");
			return "user/delete";
		} else {
			// 예외 처리 또는 기본 에러 페이지로
			model.addAttribute("title", "페이지 오류");
			return "error/error";
		}
	}
//
	@PostMapping("update")
	public String idCheckUpdate(@Valid UserDto dto, BindingResult bresult, Model model, HttpSession session) throws Exception {
		model.addAttribute("title", "정보 수정"); // 페이지 제목 설정
        System.out.println("dto ::  "+dto);

		if(bresult.hasErrors()) {
			bresult.reject("error.update.user", "회원 정보 수정 중 오류가 발생했습니다."); // 전반적인 수정 오류
			return "user/update"; // 오류 발생 시 다시 user/update 뷰로
		}

		// 비밀번호 검증
		User loginUser = (User) session.getAttribute("loginUser");
		if(!CipherUtil.makehash(dto.getPassword()).equalsIgnoreCase(loginUser.getPassword())) {
			bresult.reject("error.login.password", "비밀번호가 일치하지 않습니다.");
			return "user/update";
		}

		try {
            User user = new User(dto);
            User user1 = emailEncrypt(user);
            user1.setPassword(CipherUtil.makehash(dto.getPassword()));
            service.userUpdate(user1);
			if(loginUser.getUserid().equals(user.getUserid())) {
				session.setAttribute("loginUser", user1); // 수정된 정보로 세션 업데이트
			}
			return "redirect:/user/mypage?userid=" + user1.getUserid(); // 마이페이지로 리다이렉트 (절대 경로)
		} catch (Exception e) {
			e.printStackTrace();
			throw new ShopException("고객 정보 수정 실패", "/user/update?userid=" + dto.getUserid()); // 오류 발생 시 예외 처리
		}
	}
//
	@PostMapping("delete")
	public String idCheckDelete(@RequestParam("password") String password,
								@RequestParam("userid") String userid,
								Model model, HttpSession session) throws Exception {
		model.addAttribute("title", "회원 탈퇴"); // 페이지 제목 설정

		User loginUser = (User) session.getAttribute("loginUser");

		// 관리자 탈퇴 불가
		if(userid.equals("admin")) {
			throw new ShopException("관리자는 탈퇴할 수 없습니다.", "/user/mypage?userid="+userid);
		}

		// 비밀번호 불일치 (탈퇴하려는 사용자 본인의 비밀번호와 비교)
		User userToDelete = service.selectUser(userid); // 탈퇴하려는 사용자 정보 조회
		if(userToDelete == null || !CipherUtil.makehash(password).equals(userToDelete.getPassword())) {
			throw new ShopException("비밀번호를 확인하세요.", "/user/delete?userid="+userid);
		}

		try {
			service.userDelete(userid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ShopException("회원 탈퇴 중 오류 발생", "/user/delete?userid="+userid);
		}

		String redirectUrl;
		if(loginUser != null && loginUser.getUserid().equals("admin")) {
			redirectUrl = "redirect:/admin/list"; // 관리자라면 관리자 목록 페이지로 (절대 경로)
		} else {
			session.invalidate(); // 본인 탈퇴 시 세션 무효화
			redirectUrl = "redirect:/user/login"; // 로그인 페이지로 리다이렉트 (절대 경로)
		}
		return redirectUrl;
	}
//
	@GetMapping("password")
	public String loginCheckform(Model model) {
		model.addAttribute("title", "비밀번호 변경"); // 페이지 제목 설정
		return "user/password"; // user/password 뷰 반환
	}
//
	@PostMapping("password")
	public String loginCheckPassword(@RequestParam("password") String password, // 현재 비밀번호
									 @RequestParam("chgpass") String chgpass, // 변경할 비밀번호
									 Model model, HttpSession session) throws Exception {
		model.addAttribute("title", "비밀번호 변경"); // 페이지 제목 설정

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			throw new ShopException("로그인 후 이용해 주세요.", "/user/login");
		}

		// 현재 입력받은 비밀번호와 로그인된 사용자의 실제 비밀번호 비교
		if(!CipherUtil.makehash(password).equals(loginUser.getPassword())) {
			throw new ShopException("현재 비밀번호가 일치하지 않습니다. 다시 확인해주세요.", "/user/password");
		}

		try {
            String makehash = CipherUtil.makehash(chgpass);
            service.updatePassword(loginUser.getUserid(), makehash); // 서비스 호출
			loginUser.setPassword(makehash); // 세션에 저장된 사용자 정보 업데이트
		} catch (Exception e) {
			e.printStackTrace();
			throw new ShopException("비밀번호 변경 중 오류가 발생했습니다.", "/user/password");
		}

		return "redirect:/user/mypage?userid=" + loginUser.getUserid(); // 마이페이지로 리다이렉트 (절대 경로)
	}
    @GetMapping("idSearch")
    public String idSearchForm(Model model) {
        model.addAttribute("userDto", new UserDto()); // 폼 바인딩을 위한 빈 User 객체
        model.addAttribute("title", "아이디 찾기");
        return "user/idSearch"; // templates/user/idSearch.html 파일을 반환
    }

    // 2. 비밀번호 찾기 페이지 (GET)
    @GetMapping("pwSearch")
    public String pwSearchForm(Model model) {
        model.addAttribute("userDto", new UserDto()); // 폼 바인딩을 위한 빈 User 객체
        model.addAttribute("title", "비밀번호 찾기");
        return "user/pwSearch"; // templates/user/pwSearch.html 파일을 반환
    }

    // 3. 아이디 찾기 처리 (POST)
    @PostMapping("idSearch")
    public String idSearch( @Valid UserDto user, BindingResult bresult, Model model) throws Exception {
        System.out.println("user :: "+user);
        model.addAttribute("title", "아이디 찾기");

        if (!StringUtils.hasText(user.getEmail()) || !StringUtils.hasText(user.getPhoneno())) {
            bresult.reject("error.input.check", "모든 값을 입력해주세요.");
            return "user/idSearch";
        }
        User user1 = new User(user);
        String resultId = service.getSearch(user1);


        if (resultId == null) {
            model.addAttribute("result", "입력하신 정보와 일치하는 사용자가 없습니다.");
            bresult.reject("error.search", "User not found"); // Thymeleaf에서 에러 스타일링을 위해
        } else {
            model.addAttribute("result", "회원님의 아이디는 [ " + resultId + " ] 입니다.");
        }
        return "user/idSearch"; // 결과를 포함하여 다시 아이디 찾기 페이지를 보여줌
    }

    // 4. 비밀번호 찾기 처리 (POST)
    @PostMapping("pwSearch")
    public String pwSearch(@ModelAttribute UserDto user, BindingResult bresult, Model model) throws Exception {

        model.addAttribute("title", "비밀번호 찾기");

        if (!StringUtils.hasText(user.getUserid()) || !StringUtils.hasText(user.getEmail()) || !StringUtils.hasText(user.getPhoneno())) {
            bresult.reject("error.input.check", "모든 값을 입력해주세요.");
            return "user/pwSearch";
        }

        User user1 = new User(user);
        String resultId = service.getSearch(user1); // 먼저 해당 정보로 사용자가 있는지 확인

        if (resultId == null) {
            model.addAttribute("result", "입력하신 정보와 일치하는 사용자가 없습니다.");
            bresult.reject("error.search", "User not found"); // Thymeleaf에서 에러 스타일링을 위해
        } else {
            String newPassword = generateRandomPassword();
            // 중요: 새로 생성된 비밀번호도 반드시 해시 처리하여 저장해야 합니다.
            service.updatePassword(resultId,CipherUtil.makehash(newPassword));
            model.addAttribute("result", "임시 비밀번호가 발급되었습니다: [ " + newPassword + " ] 로그인 후 바로 변경해주세요.");
        }
        return "user/pwSearch"; // 결과를 포함하여 다시 비밀번호 찾기 페이지를 보여줌
    }



	// 사용자 입력 유효성 검사 (search 메서드에서 호출)
	private void validateUserInput(User user, BindingResult bresult, boolean isPasswordSearch) {
		if (isPasswordSearch && !StringUtils.hasText(user.getUserid())) {
			bresult.rejectValue("userid", "error.required.userid", "아이디를 입력해 주세요.");
		}
		if (!StringUtils.hasText(user.getEmail())) {
			bresult.rejectValue("email", "error.required.email", "이메일을 입력해 주세요.");
		}
		if (!StringUtils.hasText(user.getPhoneno())) {
			bresult.rejectValue("phoneno", "error.required.phoneno", "전화번호를 입력해 주세요.");
		}
	}


        private String generateRandomPassword () {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            return sb.toString();
        }

    }
