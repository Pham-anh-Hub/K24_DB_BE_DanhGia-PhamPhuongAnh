-- I2. Suất chiếu ngày 05/12/2026 còn từ 20 ghế (xem đề). Viết MỘT câu SELECT bên dưới, kết thúc bằng dấu ;
select title, room_name, start_time, available_seats from movie m join showtimes s
on s.movie_id = m.id
where s.start_time >= '2026-12-05 00:00:00'
  and s.start_time < '2026-12-06 00:00:00'
  and s.available_seats >= 20
order by s.start_time asc , room_name asc;