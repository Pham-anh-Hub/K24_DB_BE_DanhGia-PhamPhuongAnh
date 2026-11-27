package vn.rikkei.cinemabox.showtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.rikkei.cinemabox.common.ResourceNotFoundException;
import vn.rikkei.cinemabox.showtime.dto.ShowtimeResponse;

@RequiredArgsConstructor
@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    public ShowtimeResponse getShowtimeById(Long id){
        Showtime target = showtimeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Suất chiếu không tồn tại, vui lòng kiểm tra lại"));
        return ShowtimeResponse.builder()
                .id(id)
                .roomName(target.getRoom().getName())
                .price(target.getPrice())
                .availableSeats(target.getAvailableSeats())
                .movieTitle(target.getMovie().getTitle())
                .startTime(target.getStartTime())
                .build();
    }
}
