package kr.gdu.service;

import java.util.List;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gdu.dao.UserDao;
import kr.gdu.logic.User;



@Service
public class UserService {
	
	@Autowired
	private UserDao dao;

	public void userInsert(User  user) {
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

	public void userUpdate( User user) {
		 dao.update(user);
	}

	public void userDelete(String userid) {
		dao.delete(userid);
	}

	public void changePw(User loginUser) {
		dao.changepw(loginUser);
		
	}

	public String getSearch(User user) {
		return dao.search(user);
	}

	public List<User> selectList() {
		return dao.list();
	}

	

	
	


}
