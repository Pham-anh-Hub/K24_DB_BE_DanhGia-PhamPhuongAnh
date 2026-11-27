package vn.rikkei.cinemabox.customer.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//{ "id": 9, "fullName": "Tr n Th Thu", "email": "thu.tran@example.com", ầ ị
//n Th ầ ị
//"phone": "0912345678", "memberLevel": "STANDARD",
//"createdAt": "2026-12-01T07:40:12" }
@Getter
@Setter
public class CustomerRequest {
    @NotNull(message = "Vui lòng không để trống tên")
    String fullName;
    @NotNull(message = "Vui lòng không để trống email")
    String email;
    String phone;
    @Pattern(regexp = "^(STANDARD|GOLD)$", message = "memberLevel chỉ nhận STANDARD hoặc GOLD")
    String memberLevel;

}
