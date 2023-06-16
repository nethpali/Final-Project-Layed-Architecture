package lk.ijse.Roosalu.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AgentDto{
    private String id;
    private String email;
    private String name;
    private String contact_no;
    private String company;
}
