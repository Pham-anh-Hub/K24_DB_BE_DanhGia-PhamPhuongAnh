package vn.rikkei.cinemabox.showtime.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShowtimeResponse {
    Long id;
    String movieTitle;
    String roomName;
    LocalDateTime startTime;
    Long price;
    Integer availableSeats;
}
