package com.timeToast.timeToast.domain.creator_account;

import com.google.protobuf.Enum;
import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "creator_account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatorAccount extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_account_id")
    private long id;

    private long memberId;

    private Bank bank;

    private String accountNumber;

    @Builder
    public CreatorAccount(final long memberId, final Bank bank, final String accountNumber) {
        this.memberId = memberId;
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public void updateAccount(final Bank bank, final String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }
}

