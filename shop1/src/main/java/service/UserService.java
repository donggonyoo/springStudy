package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDao;
import logic.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao dao;

	public void userInsert(User user) {
		dao.insert(user);
	}

	/*public boolean selectUser(User user, HttpSession session) {
		String login = dao.selectUser(user);
		//로그인정보가없다면
		if(login==null) {
			return false;
		}
		else {
			session.setAttribute("loginUser", login);
			return true;
		}
		
	}*/
	
	public User selectUser(String userid) {
		return dao.selectOne(userid);
	}

}
