CREATE TABLE member (
                        member_id BIGINT AUTO_INCREMENT,
                        premium_id BIGINT,
                        email VARCHAR(255),
                        member_profile_url VARCHAR(255),
                        nickname VARCHAR(255),
                        login_type VARCHAR(20),
                        member_role VARCHAR(20),
                        created_at datetime,
                        last_modified_at datetime,
                        PRIMARY KEY (member_id)
);



CREATE TABLE premium (
                         premium_id BIGINT AUTO_INCREMENT,
                         premium_type VARCHAR(20),
                         price INT,
                         count INT,
                         description VARCHAR(255),
                         created_at DATETIME,
                         last_modified_at DATETIME,
                         PRIMARY KEY (premium_id)
);

CREATE TABLE member_token (
                              member_token_id BIGINT AUTO_INCREMENT,
                              member_id BIGINT,
                              jwt_refresh_token VARCHAR(350),
                              fcm_token VARCHAR(350),
                              created_at datetime,
                              last_modified_at datetime,
                              PRIMARY KEY (member_token_id)
);


INSERT INTO premium VALUES (1,"BASIC",0, 3, "베이직", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO premium VALUES (2,"PREMIUM",5500, 10, "프리미엄", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");


INSERT INTO member VALUES (1,1,"test1@email.com", "memberProfileUrl1", "user1", "GOOGLE", "USER", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO member VALUES (2,1,"test2@email.com", "memberProfileUrl2", "testNickname2", "GOOGLE", "USER", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO member VALUES (3,1,"test3@email.com", "memberProfileUrl3", "testNickname3", "GOOGLE", "USER", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO member VALUES (4,1,"test4@email.com", "memberProfileUrl4", "TimeToast", "GOOGLE", "CREATOR", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO member VALUES (5,1,"withDrawal@email.com", "memberProfileUrl5", "withDrawalUser", "GOOGLE", "USER", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");




CREATE TABLE team (
                      team_id BIGINT AUTO_INCREMENT,
                      name VARCHAR(255),
                      team_profile_url VARCHAR(255),
                      created_at datetime,
                      last_modified_at datetime,
                      PRIMARY KEY (team_id)
);

INSERT INTO team VALUES (1, "team1", "teamProfileUrl", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779" );
INSERT INTO team VALUES (2, "team2", "teamProfileUrl2", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779" );

CREATE TABLE team_member (
                             team_member_id BIGINT AUTO_INCREMENT,
                             member_id BIGINT,
                             team_id BIGINT,
                             created_at datetime,
                             last_modified_at datetime,
                             PRIMARY KEY (team_member_id)
);

INSERT INTO team_member VALUES (1,1, 1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO team_member VALUES (2,2, 1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO team_member VALUES (3,3, 1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO team_member VALUES (4,5, 1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO team_member VALUES (5,1, 2, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO team_member VALUES (6,2, 2, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO team_member VALUES (7,3, 2, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

CREATE TABLE follow (
                        follow_id BIGINT AUTO_INCREMENT,
                        follower_id BIGINT,
                        following_id BIGINT,
                        created_at datetime,
                        last_modified_at datetime,
                        PRIMARY KEY (follow_id)
);

INSERT INTO follow VALUES (1,1, 2, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO follow VALUES (2,1, 3, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO follow VALUES (3,2, 1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO follow VALUES (4,3, 1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO follow VALUES (5,5, 1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO follow VALUES (6,2, 5, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");



CREATE TABLE icon_group (
                            icon_group_id BIGINT AUTO_INCREMENT,
                            member_id BIGINT,
                            icon_type VARCHAR(20),
                            icon_builtin VARCHAR(20),
                            name VARCHAR(255) NOT NULL,
                            price int NOT NULL,
                            icon_state VARCHAR(20),
                            description VARCHAR(200),
                            thumbnail_image_url VARCHAR(255),
                            created_at datetime,
                            last_modified_at datetime,
                            PRIMARY KEY (icon_group_id)
);

INSERT INTO icon_group VALUES (1,4,"TOAST","BUILTIN","보통맛","0","REGISTERED",  "타임토스트 제공 기본 토스트 아이콘","thumbnailImageUrl","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_group VALUES (2,4,"JAM","BUILTIN","보통맛","0","REGISTERED", "타임토스트 제공 기본 잼 아이콘","thumbnailImageUrl", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

CREATE TABLE icon (
                      icon_id BIGINT AUTO_INCREMENT,
                      icon_group_id BIGINT,
                      icon_image_url VARCHAR(255),
                      created_at datetime,
                      last_modified_at datetime,
                      PRIMARY KEY (icon_id)
);

INSERT INTO icon VALUES (1,1,"toastImageUrl1", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon VALUES (2,1,"toastImageUrl2", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon VALUES (3,1,"toastImageUrl3", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon VALUES (4,2,"jamImageUrl1", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon VALUES (5,2,"jamImageUrl2", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon VALUES (6,2,"jamImageUrl3", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

CREATE TABLE icon_member (
                             icon_member_id BIGINT AUTO_INCREMENT,
                             member_id BIGINT,
                             icon_group_id BIGINT,
                             created_at datetime,
                             last_modified_at datetime,
                             PRIMARY KEY (icon_member_id)
);

INSERT INTO icon_member VALUES (1,1,1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_member VALUES (2,1,2,"2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_member VALUES (3,2,1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_member VALUES (4,2,2,"2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_member VALUES (5,3,1, "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_member VALUES (6,3,2,"2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_member VALUES (7,5,1,"2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_member VALUES (8,5,2,"2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

CREATE TABLE gift_toast (
                            gift_toast_id BIGINT AUTO_INCREMENT,
                            icon_id BIGINT,
                            team_id BIGINT,
                            memorized_date DATE,
                            opened_date DATE,
                            is_opened BOOLEAN,
                            title VARCHAR(255),
                            gift_toast_type VARCHAR(20),
                            description VARCHAR(255),
                            created_at datetime,
                            last_modified_at datetime,
                            PRIMARY KEY (gift_toast_id)
);

INSERT INTO gift_toast VALUES (1,1,1, "2024-12-03","2024-12-03", true, "title", "MINE","description","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

CREATE TABLE gift_toast_owner (
                                  gift_toast_owner_id BIGINT AUTO_INCREMENT,
                                  member_id BIGINT,
                                  gift_toast_id BIGINT,
                                  created_at datetime,
                                  last_modified_at datetime,
                                  PRIMARY KEY (gift_toast_owner_id)
);

INSERT INTO gift_toast_owner VALUES (1,5,1,"2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

CREATE TABLE toast_piece (
                             toast_piece_id BIGINT AUTO_INCREMENT,
                             member_id BIGINT,
                             gift_toast_id BIGINT,
                             icon_id BIGINT,
                             title VARCHAR(255),
                             contents_url VARCHAR(255),
                             created_at datetime,
                             last_modified_at datetime,
                             PRIMARY KEY (toast_piece_id)
);

INSERT INTO toast_piece VALUES (1,5,1,1,"title","contents_url","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO toast_piece VALUES (2,5,1,1,"title","contents_url","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");


CREATE TABLE toast_piece_image (
                                   toast_piece_image_id BIGINT AUTO_INCREMENT,
                                   toast_piece_id BIGINT,
                                   image_url VARCHAR(255),
                                   created_at datetime,
                                   last_modified_at datetime,
                                   PRIMARY KEY (toast_piece_image_id)
);

INSERT INTO toast_piece_image VALUES (1,1,"image_url1","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO toast_piece_image VALUES (2,2,"image_url2","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");


CREATE TABLE event_toast (
                             event_toast_id BIGINT AUTO_INCREMENT,
                             member_id BIGINT,
                             icon_id BIGINT,
                             title VARCHAR(255),
                             opened_date DATE,
                             is_opened BOOLEAN,
                             description VARCHAR(255),
                             created_at DATETIME,
                             last_modified_at DATETIME,
                             PRIMARY KEY (event_toast_id)
);

INSERT INTO event_toast VALUES (1,5,1,"title1","2024-12-04",false,"description1","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");



CREATE TABLE jam (
                     jam_id BIGINT AUTO_INCREMENT,
                     member_id BIGINT,
                     event_toast_id BIGINT,
                     icon_id BIGINT,
                     title VARCHAR(255),
                     contents_url VARCHAR(255),
                     image_url VARCHAR(255),
                     created_at DATETIME,
                     last_modified_at DATETIME,
                     PRIMARY KEY (jam_id)
);
INSERT INTO jam VALUES (1,5,1,1,"title","contents_url1","image_url","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");



CREATE TABLE showcase (
                          showcase_id BIGINT AUTO_INCREMENT,
                          member_id BIGINT,
                          event_toast_id BIGINT,
                          created_at DATETIME,
                          last_modified_at DATETIME,
                          PRIMARY KEY (showcase_id)
);

INSERT INTO showcase VALUES (1,5,1,"2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");


CREATE TABLE fcm (
                     fcm_id BIGINT AUTO_INCREMENT,
                     member_id BIGINT,
                     fcm_constant VARCHAR(50),
                     sender_id BIGINT,
                     toast_name VARCHAR(255),
                     is_opened BOOLEAN,
                     param BIGINT,
                     image_url VARCHAR(255),
                     created_at DATETIME,
                     last_modified_at DATETIME,
                     PRIMARY KEY (fcm_id)
);

INSERT INTO fcm VALUES (1,5,"FOLLOW",4,"toast_name",false,2,"image_url","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");



CREATE TABLE template (
                     template_id BIGINT AUTO_INCREMENT,
                     member_id BIGINT,
                     event_toast_id BIGINT,
                     template_text VARCHAR(200),
                     created_at DATETIME,
                     last_modified_at DATETIME,
                     PRIMARY KEY (template_id)
);

CREATE TABLE inquiry (
                     inquiry_id BIGINT AUTO_INCREMENT,
                     title VARCHAR(100),
                     contentsUrl VARCHAR(255),
                     email VARCHAR(100),
                     inquiry_state VARCHAR(40),
                     created_at DATETIME,
                     last_modified_at DATETIME,
                     PRIMARY KEY (inquiry_id)
);

CREATE TABLE creator_account (
    creator_account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    bank VARCHAR(20) NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    created_at DATETIME,
    last_modified_at DATETIME
);


CREATE TABLE payment (
                         payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         member_id BIGINT NOT NULL,
                         order_id VARCHAR(255) UNIQUE,
                         item_type VARCHAR(255),
                         payment_state VARCHAR(255),
                         amount INT,
                         item_id BIGINT,
                         expired_date DATE,
                         created_at DATETIME,
                         last_modified_at DATETIME

);

INSERT INTO payment VALUES (1,5,"orderId1","PREMIUM","SUCCESS",5500,2,"2025-03-02","2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");


CREATE TABLE settlement (
                            settlement_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            member_id BIGINT NOT NULL,
                            icon_group_id BIGINT NOT NULL,
                            years_month DATE NOT NULL,
                            sales_count INT NOT NULL,
                            revenue BIGINT,
                            settlements BIGINT,
                            settlement_state VARCHAR(255),
                            settlement_date DATE,
                            created_at DATETIME,
                            last_modified_at DATETIME
);