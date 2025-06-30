package springSecurity2.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
//table명과 class명을 동일하게설정한다면 @Table을 굳이 정의하지않아도 됨
@Setter
@Getter
@ToString
public class UserEntity {
    @Id
    //새로운 UserEntity를 저장할 때 데이터베이스가 id에 다음 순차적 정수 값을 자동 할당합니다(예: 1, 2, 3, ...).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String password;
    private String role;
}
