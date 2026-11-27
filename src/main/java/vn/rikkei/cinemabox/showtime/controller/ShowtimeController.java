package vn.rikkei.cinemabox.showtime.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.rikkei.cinemabox.showtime.ShowtimeService;
import vn.rikkei.cinemabox.showtime.dto.ShowtimeResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    public ResponseEntity<ShowtimeResponse> getShowtimeByIds(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(showtimeService.getShowtimeById(id));
    }

}
