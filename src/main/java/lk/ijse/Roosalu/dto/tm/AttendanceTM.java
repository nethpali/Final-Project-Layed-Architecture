package lk.ijse.Roosalu.dto.tm;

import lombok.*;

@ToString
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Getter
public class AttendanceTM {
    private String employee_id;
    private String attendance_id;
    private  String date;
    private String time;
    private String absent_present;
}
