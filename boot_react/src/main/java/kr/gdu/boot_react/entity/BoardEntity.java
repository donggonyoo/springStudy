package kr.gdu.boot_react.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    private String name;
    private String pass;
    private String subject;
    private String content;
    private String file1;
    
    @Temporal(TemporalType.TIMESTAMP) 
    //날짜,시간 모두포함해 DB에저장
    private Date regdate; 
    
    private int readcnt;
    private String boardid;

    @PrePersist //가장먼저실행
    public void prePersist() {
        this.regdate = new Date();
    }
}
