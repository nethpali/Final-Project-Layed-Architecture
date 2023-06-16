package lk.ijse.Roosalu.Entity;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Agent {
    private String id;
    private String email;
    private String name;
    private String contact_no;
    private String company;
}
