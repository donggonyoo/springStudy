package springSecurity2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springSecurity2.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    UserEntity findByUsername(@Param("username")String username);
}
