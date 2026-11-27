-- F3. Khách chi tiêu cao (xem đề). Viết MỘT câu SELECT bên dưới, kết thúc bằng dấu ;

# Cột kết quả:
# full_name
# , e
# email
# , s
# so_lan_dat
# (số vé CONFIRMED của khách),
# tong_chi_tieu

select full_name, email, count(b.id) as so_lan_dat, sum(b.total_amount) as tong_chi_tieu from
customers c join bookings b on b.customer_id = c.id and b.status = 'CONFIRMED'
where tong_chi_tieu >= 500000
group by c.id, c.full_name, c.email
order by tong_chi_tieu desc , email asc

