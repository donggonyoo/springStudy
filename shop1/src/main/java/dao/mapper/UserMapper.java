package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import logic.User;

public interface UserMapper {

	@Insert("insert into useraccount "
			
			+ " (userid,password,username,phoneno,postcode,email,birthday,address)"
			+ " values(#{userid},#{password},#{username},#{phoneno},#{postcode},"
			+ " #{email},#{birthday},#{address})")
	void insert(User user);

	@Select("select userid from useraccount where password=#{password} and userid=#{userid}")
	String selectUser(User user);
	
	@Select({"<script>",
			"select * from useraccount ",
			"<if test='userid != null'> where userid=#{userid}</if>",
			"</script>"})
	List<User> select(Map<String,Object> param);
	

}
