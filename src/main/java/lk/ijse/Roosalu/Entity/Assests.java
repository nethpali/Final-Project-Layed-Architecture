package lk.ijse.Roosalu.Entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Assests {
    private String id;
    private String name;
    private String description;
    private int quantity;
    private double value;
}
