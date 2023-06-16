package lk.ijse.Roosalu.Entity;


import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    private String employee_id;
    private String employee_nic;
    private String employee_name;
    private String employee_address;
    private String employee_contact_no;
    private double employee_salary_per_hour;
}
