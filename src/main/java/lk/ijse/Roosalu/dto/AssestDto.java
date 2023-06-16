package lk.ijse.Roosalu.dto;


import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AssestDto {
    private String id;
    private String name;
    private String description;
    private int quantity;
    private double value;
}
