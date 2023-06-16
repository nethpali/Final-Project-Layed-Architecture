package lk.ijse.Roosalu.Entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attendance {
    private String employee_id;
    private String attendance_id;
    private  String date;
    private String time;
    private String absent_present;
}
