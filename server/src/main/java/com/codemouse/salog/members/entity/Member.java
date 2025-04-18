package com.codemouse.salog.members.entity;

import com.codemouse.salog.audit.Auditable;
import com.codemouse.salog.diary.entity.Diary;
import com.codemouse.salog.ledger.budget.entity.MonthlyBudget;
import com.codemouse.salog.ledger.fixedIncome.entity.FixedIncome;
import com.codemouse.salog.ledger.fixedOutgo.entity.FixedOutgo;
import com.codemouse.salog.ledger.income.entity.Income;
import com.codemouse.salog.ledger.outgo.entity.Outgo;
import com.codemouse.salog.tags.diaryTags.entity.DiaryTag;
import com.codemouse.salog.tags.ledgerTags.entity.LedgerTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true) // oauth
    private String password;

    @Column(nullable = false)
    private boolean emailAlarm;

    @Column(nullable = false)
    private boolean homeAlarm;

    // 소셜 회원 분류
    // 재사용 용이하게 Enum
    @Enumerated(value = EnumType.STRING)
    @Column
    private Social_type socialType = null;

//    @Enumerated(value = EnumType.STRING)
//    @Column(nullable = false)
//    private Status status = Status.MEMBER_ACTIVE;

    // JWT - 역할 부여
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    // cascade
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Income> incomes;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Outgo> outgos;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FixedIncome> fixedIncomes;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FixedOutgo> fixedOutgos;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Diary> diaries;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MonthlyBudget> monthlyBudgets;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<LedgerTag> ledgerTags;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<DiaryTag> diaryTags;


//    public enum Status {
//        MEMBER_ACTIVE("활동중"),
//        MEMBER_QUIT("탈퇴 상태");
//
//        @Getter
//        private final String status;
//
//        Status(String status){
//            this.status = status;
//        }
//    }

    public enum Social_type {
        GOOGLE("구글 회원"),
        NAVER("네이버 회원"),
        KAKAO("카카오 회원");

        @Getter
        private final String social_type;

        Social_type(String social_type) {
            this.social_type = social_type;
        }
    }
}
