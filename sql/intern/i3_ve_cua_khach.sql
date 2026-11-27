-- I3. Vé đã xác nhận của khách an.nguyen@example.com (xem đề). Viết MỘT câu SELECT bên dưới, kết thúc bằng dấu ;
# Liệt kê các vé CONFIRMED của khách có email an.nguyen@example.com.
# Lọc theo email, không dùng id của khách.•
# Cột kết quả: booking_id, title  (tên phim), s start_time  (giờ chiếu), quantity, total_amount.•
# Sắp xếp: theo s start_time  tăng dần.

select booking_id, title, start_time, quantity, total_amount
from bookings b join customers c on b.customer_id = c.id
join showtimes s on b.showtime_id = s.id
join movies m  on s.movie_id = m.id
where b.status = 'CONFIRMED' and c.email = 'an.nguyen@example.com'
order by s.start_time asc ;