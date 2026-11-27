-- F2. Doanh thu theo phim (xem đề). Viết MỘT câu SELECT bên dưới, kết thúc bằng dấu ;
# : title, s so_ve  (tổng quantity), doanh_thu  (tổng total_amount).
# Phim chưa có vé CONFIRMED thì so_ve  và d doanh_thu  bằng 0, không để NULL


select full_name, email, count(b.id) as so_lan_dat, sum(b.total_amount) as tong_chi_tieu from
customers c join bookings b on b.customer_id = c.id and b.status = 'CONFIRMED'
group by c.id, c.email, c.full_name
having sum(b.total_amount) >= 500000
order by tong_chi_tieu desc , email asc;


