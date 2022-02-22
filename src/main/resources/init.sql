USE epam_airlines;

create table title
(
    id    int          not null auto_increment primary key,
    title varchar(124) not null
);


create table users
(
    id        int          not null auto_increment Primary key,
    email     varchar(124) not null,
    password  varchar(124) not null,
    firstName varchar(124) not null,
    lastName  varchar(124) not null,
    image     varchar(124) not null,
    title     int,
    birthday  DATE         not null,
    constraint FK_to_position foreign key (title) references title (id)
);

insert into title (title)
values ('ADMIN'),
       ('TRAFFIC_CONTROLLER'),
       ('CAPTAIN'),
       ('FIRST_OFFICER'),
       ('CHIEF_STEWARD'),
       ('STEWARD');

create table aircraft
(
    id   int          not null auto_increment primary key,
    name varchar(124) not null
);

create table airport
(
    id   int          not null auto_increment primary key,
    code varchar(3) not null
);

create table flight
(
    id              int          not null auto_increment Primary key,
    name            varchar(8) not null,
    date            DATETIME     not null,
    aircraft_id     int,
    to_airport_id   int,
    from_airport_id int,
    captain         int,
    first_officer   int,
    chief_steward   int,
    steward_1       int,
    steward_2       int,
    constraint FK_to_aircraft foreign key (aircraft_id) references aircraft (id),
    constraint FK_to_start_point foreign key (to_airport_id) references airport (id),
    constraint FK_to_destination_point foreign key (from_airport_id) references airport (id),
    constraint FK_to_crew_member_1 foreign key (captain) references users (id),
    constraint FK_to_crew_member_2 foreign key (first_officer) references users (id),
    constraint FK_to_crew_member_3 foreign key (chief_steward) references users (id),
    constraint FK_to_crew_member_4 foreign key (steward_1) references users (id),
    constraint FK_to_crew_member_5 foreign key (steward_2) references users (id)
);
