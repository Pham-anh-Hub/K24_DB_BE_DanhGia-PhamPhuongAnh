package vn.rikkei.cinemabox.customer.dto;

import lombok.*;

import java.time.LocalDateTime;

//{ "id": 9, "fullName": "Tr n Th Thu", "email": "thu.tran@example.com", ầ ị
//n Th ầ ị
//"phone": "0912345678", "memberLevel": "STANDARD",
//"createdAt": "2026-12-01T07:40:12" }
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {
    Long id;
    String fullName;
    String email;
    String phone;
    String memberLevel;
    LocalDateTime createdAt;
}
