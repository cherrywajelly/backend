package com.timeToast.timeToast.domain.follow;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    //팔로우 대상
    private Long followingId;

    //팔로우 하는 사람
    private Long followerId;

    @Builder
    public Follow(final long followingId, final long followerId){
        this.followingId = followingId;
        this.followerId = followerId;
    }

}
