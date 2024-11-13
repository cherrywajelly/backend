package com.timeToast.timeToast.domain.premium;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "premium")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Premium extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premium_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PremiumType premiumType;

    private int price;

    private int count;

    private String description;

    @Builder
    public Premium(final PremiumType premiumType, final int price, final int count, final String description){
        this.premiumType = premiumType;
        this.price = price;
        this.count = count;
        this.description = description;
    }
}
