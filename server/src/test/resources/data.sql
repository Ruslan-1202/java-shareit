insert into users (name, email)
values ('John', 'Doe@'),('Test 2', 'Mail 2');

insert into items (name, description, is_available, owner_id, request_id)
values ('Item name', 'Item descr', true, 1, null),
       ('Item name 2', 'Item descr 2', false, 1, null);

insert into bookings (start_date, end_date, item_id, booker_id, status)
values ('2012-01-01', '2022-03-23', 1, 1, 1), ('2012-01-01', '2022-03-23', 1, 1, 0);

insert into requests (description, requestor_id, created)
values ('Req Descr', 1, '2024-03-22');