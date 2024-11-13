CREATE TABLE member (
                        member_id BIGINT AUTO_INCREMENT,
                        email VARCHAR(255),
                        member_profile_url VARCHAR(255),
                        nickname VARCHAR(255),
                        login_type VARCHAR(20),
                        member_role VARCHAR(20),
                        created_at datetime,
                        last_modified_at datetime,
                        PRIMARY KEY (member_id)
);

INSERT INTO member VALUES (1,"test1@email.com", "memberProfileUrl1", "testNickname1", "GOOGLE", "USER", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO member VALUES (2,"test2@email.com", "memberProfileUrl2", "testNickname2", "GOOGLE", "USER", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO member VALUES (3,"test3@email.com", "memberProfileUrl3", "testNickname3", "GOOGLE", "USER", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO member VALUES (4,"test4@email.com", "memberProfileUrl4", "testNickname4", "GOOGLE", "CREATOR", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

CREATE TABLE member_jwt_refresh_token (
                                          member_jwt_refresh_token_id BIGINT AUTO_INCREMENT,
                                          member_id BIGINT NOT NULL,
                                          jwt_refresh_token VARCHAR(350),
                                          created_at datetime,
                                          last_modified_at datetime,
                                          PRIMARY KEY (member_jwt_refresh_token_id)
);

CREATE TABLE team (
                      team_id BIGINT AUTO_INCREMENT,
                      name VARCHAR(255),
                      team_profile_url VARCHAR(255),
                      created_at datetime,
                      last_modified_at datetime,
                      PRIMARY KEY (team_id)
);

INSERT INTO team VALUES (1, "team1", "teamProfileUrl", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779" );

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


CREATE TABLE follow (
                        follow_id BIGINT AUTO_INCREMENT,
                        follower_id BIGINT,
                        following_id BIGINT,
                        created_at datetime,
                        last_modified_at datetime,
                        PRIMARY KEY (follow_id)
);


CREATE TABLE icon_group (
                            icon_group_id BIGINT AUTO_INCREMENT,
                            member_id BIGINT,
                            icon_type VARCHAR(20),
                            icon_builtin VARCHAR(20),
                            name VARCHAR(255) NOT NULL,
                            price BIGINT NOT NULL,
                            icon_state VARCHAR(20),
                            created_at datetime,
                            last_modified_at datetime,
                            PRIMARY KEY (icon_group_id)
);

INSERT INTO icon_group VALUES (1,4,"TOAST","BUILTIN","iconGroup1","0","REGISTERED",  "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon_group VALUES (2,4,"NOT_OPEN","BUILTIN","iconGroup2","0","REGISTERED",  "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

CREATE TABLE icon (
                      icon_id BIGINT AUTO_INCREMENT,
                      icon_group_id BIGINT,
                      icon_image_url VARCHAR(255),
                      created_at datetime,
                      last_modified_at datetime,
                      PRIMARY KEY (icon_id)
);

INSERT INTO icon VALUES (1,1,"iconImageUrl1", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon VALUES (2,1,"iconImageUrl2", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon VALUES (3,1,"iconImageUrl3", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");
INSERT INTO icon VALUES (4,2,"notOpenUrl", "2024-11-03 22:34:32.431779", "2024-11-03 22:34:32.431779");

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

CREATE TABLE gift_toast (
                            gift_toast_id BIGINT AUTO_INCREMENT,
                            icon_id BIGINT,
                            group_id BIGINT,
                            memorized_date DATE,
                            opened_date DATE,
                            is_opened BOOLEAN,
                            title VARCHAR(255),
                            gift_toast_type VARCHAR(20),
                            created_at datetime,
                            last_modified_at datetime,
                            PRIMARY KEY (gift_toast_id)
);

CREATE TABLE gift_toast_owner (
                                  gift_toast_owner_id BIGINT AUTO_INCREMENT,
                                  member_id BIGINT,
                                  gift_toast_id BIGINT,
                                  created_at datetime,
                                  last_modified_at datetime,
                                  PRIMARY KEY (gift_toast_owner_id)
);

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

CREATE TABLE toast_piece_image (
                                   toast_piece_image_id BIGINT AUTO_INCREMENT,
                                   toast_piece_id BIGINT,
                                   image_url VARCHAR(255),
                                   created_at datetime,
                                   last_modified_at datetime,
                                   PRIMARY KEY (toast_piece_image_id)
);

CREATE TABLE showcase (
                          showcase_id BIGINT AUTO_INCREMENT,
                          member_id BIGINT,
                          event_toast_id BIGINT,
                          created_at DATETIME,
                          last_modified_at DATETIME,
                          PRIMARY KEY (showcase_id)
);

