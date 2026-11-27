-- F1. Lệnh CREATE TABLE reviews (xem đề). Chỉ tạo bảng reviews, không sửa bảng có sẵn.

create table reviews (
    id bigint auto_increment primary key,
    foreign key (customer_id) references customers(id),
    foreign key (movie_id) references movies(id),
    rating int check ( rating >=1 and rating <= 5 ) not null ,
    content varchar(255) ,
    created_at timestamp not null default current_timestamp
)
