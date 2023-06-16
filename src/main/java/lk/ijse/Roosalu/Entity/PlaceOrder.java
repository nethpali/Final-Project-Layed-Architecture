package lk.ijse.Roosalu.Entity;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PlaceOrder {
    private String order_id;
    private String date;
    private String agent_id;
    private String product_id;
    private Double unit_price;
    private int quantity;
    private Double total;
}
