package gradleProject.shop3.domain;

import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentId implements Serializable {
    private int num;
    private int seq;
}
