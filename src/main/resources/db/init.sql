create table category
(
    id         bigint auto_increment
        primary key,
    name       varchar(50) not null,
    created_at datetime    not null,
    updated_at datetime    not null
)
    charset = utf8mb4;

create table user
(
    id            bigint auto_increment
        primary key,
    username      varchar(50)                 not null,
    password_hash varchar(100)                not null,
    phone         varchar(20)                 not null,
    avatar_url    varchar(255)                null,
    balance       decimal(10, 2) default 0.00 null,
    created_at    datetime                    not null,
    updated_at    datetime                    not null,
    constraint phone
        unique (phone)
)
    charset = utf8mb4;

create table message
(
    id          bigint auto_increment
        primary key,
    sender_id   bigint               not null,
    receiver_id bigint               not null,
    content     text                 not null,
    is_read     tinyint(1) default 0 null,
    created_at  datetime             not null,
    updated_at  datetime             not null,
    constraint message_ibfk_1
        foreign key (sender_id) references user (id),
    constraint message_ibfk_2
        foreign key (receiver_id) references user (id)
)
    charset = utf8mb4;

create index idx_conversation
    on message (sender_id, receiver_id);

create index idx_unread
    on message (receiver_id, is_read);

create table product
(
    id              bigint auto_increment
        primary key,
    title           varchar(100)                                      not null,
    description     text                                              null,
    category_id     bigint                                            not null,
    price           decimal(10, 2)                                    not null,
    condition_score int                                               not null,
    status          enum ('DRAFT', 'ON_SALE', 'SOLD') default 'DRAFT' null,
    user_id         bigint                                            not null,
    view_count      int                               default 0       null,
    created_at      datetime                                          not null,
    updated_at      datetime                                          not null,
    constraint product_ibfk_1
        foreign key (category_id) references category (id),
    constraint product_ibfk_2
        foreign key (user_id) references user (id)
)
    charset = utf8mb4;

create table `order`
(
    id            bigint auto_increment
        primary key,
    product_id    bigint                                                                                          not null,
    buyer_id      bigint                                                                                          not null,
    seller_id     bigint                                                                                          not null,
    price         decimal(10, 2)                                                                                  not null,
    status        enum ('PENDING_PAYMENT', 'PAID', 'SHIPPED', 'COMPLETED', 'CANCELLED') default 'PENDING_PAYMENT' null,
    address       json                                                                                            not null,
    contact_name  json                                                                                            not null,
    contact_phone json                                                                                            null,
    created_at    datetime                                                                                        not null,
    updated_at    datetime                                                                                        not null,
    constraint order_ibfk_1
        foreign key (product_id) references product (id),
    constraint order_ibfk_2
        foreign key (buyer_id) references user (id),
    constraint order_ibfk_3
        foreign key (seller_id) references user (id)
)
    charset = utf8mb4;

create index buyer_id
    on `order` (buyer_id);

create index idx_order_status
    on `order` (status);

create index product_id
    on `order` (product_id);

create index seller_id
    on `order` (seller_id);

create table payment
(
    id             bigint auto_increment
        primary key,
    order_id       bigint                                                  not null,
    amount         decimal(10, 2)                                          not null,
    status         enum ('PENDING', 'SUCCESS', 'FAILED') default 'PENDING' null,
    transaction_id varchar(100)                                            null,
    expire_time    datetime                                                not null,
    created_at     datetime                                                not null,
    updated_at     datetime                                                not null,
    constraint order_id
        unique (order_id),
    constraint payment_ibfk_1
        foreign key (order_id) references `order` (id)
)
    charset = utf8mb4;

create index category_id
    on product (category_id);

create index idx_price
    on product (price);

create index idx_status
    on product (status);

create index user_id
    on product (user_id);

create table product_image
(
    id         bigint auto_increment
        primary key,
    product_id bigint        not null,
    image_url  varchar(255)  not null,
    sort_order int default 0 null,
    created_at datetime      not null,
    constraint product_image_ibfk_1
        foreign key (product_id) references product (id)
            on delete cascade
)
    charset = utf8mb4;

create index product_id
    on product_image (product_id);

create table review
(
    id           bigint auto_increment
        primary key,
    order_id     bigint               not null,
    reviewer_id  bigint               not null,
    reviewed_id  bigint               not null,
    rating       int                  not null,
    comment      text                 null,
    is_anonymous tinyint(1) default 0 null,
    created_at   datetime             not null,
    constraint review_ibfk_1
        foreign key (order_id) references `order` (id),
    constraint review_ibfk_2
        foreign key (reviewer_id) references user (id),
    constraint review_ibfk_3
        foreign key (reviewed_id) references user (id),
    check ((`rating` >= 1) and (`rating` <= 5))
)
    charset = utf8mb4;

create index idx_rating
    on review (rating);

create index order_id
    on review (order_id);

create index reviewed_id
    on review (reviewed_id);

create index reviewer_id
    on review (reviewer_id);

create index idx_phone
    on user (phone);

