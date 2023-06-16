package lk.ijse.Roosalu.Entity;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class Product {
    private String production_id;
    private String date;
    private int quantity;
    private double unit_price;

}
