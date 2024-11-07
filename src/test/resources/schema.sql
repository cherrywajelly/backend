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

CREATE TABLE team_member (
                             member_id BIGINT,
                             team_id BIGINT,
                             team_member_id BIGINT AUTO_INCREMENT,
                             created_at datetime,
                             last_modified_at datetime,
                             PRIMARY KEY (team_member_id)
);

CREATE TABLE follow (
                        follow_id BIGINT AUTO_INCREMENT,
                        follower_id BIGINT,
                        following_id BIGINT,
                        created_at datetime,
                        last_modified_at datetime,
                        PRIMARY KEY (follow_id)
);