package springSecurity2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springSecurity2.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
