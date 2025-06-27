package gradleProject.shop3.repository;


import gradleProject.shop3.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    @Transactional
    @Modifying
    @Query
    ("update Usercipher u set u.password=:chgpass where u.userid = :userid")
    void chgpass(@Param("userid") String userid ,
                 @Param("chgpass") String chgpass);

    @Query("select u from Usercipher u  where u.phoneno = :phoneno")
            List<User> searchByUserid(@Param("phoneno") String phoneno);

    @Query("select u.password from Usercipher u  where u.userid = :userid  and u.email = :email and u.phoneno = :phoneno")
    String searchByPassword(@Param("userid")String userid,@Param("email")String email , @Param("phoneno") String phoneno);

    //list안의 userid를 꺼내 모든 컬럼조회
    List<User> findByUseridIn(String[] idchks);
}
