package gradleProject.shop3.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemId implements Serializable {
    private int saleid;
    private int seq;
}
