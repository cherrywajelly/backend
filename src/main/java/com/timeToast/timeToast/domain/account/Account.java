package com.timeToast.timeToast.domain.account;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long id;

    private long memberId;

    private String bank;

    private String accountNumber;

    @Builder
    public Account(final long memberId, final String bank, final String accountNumber) {
        this.memberId = memberId;
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public void updateAccount(final String bank, final String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }
}

