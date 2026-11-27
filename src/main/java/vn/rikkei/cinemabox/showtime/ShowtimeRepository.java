package vn.rikkei.cinemabox.showtime;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.rikkei.cinemabox.showtime.dto.ShowtimeResponse;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    ShowtimeResponse getShowtimesById(Long id);
}
