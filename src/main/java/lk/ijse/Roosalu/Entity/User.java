package lk.ijse.Roosalu.Entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String user_id;
    private String password;
    private String re_enter_password;
    private String user_name;
    private String user_email;
}
