package com.codemouse.salog.tags.diaryTags.entity;

import com.codemouse.salog.audit.Auditable;
import com.codemouse.salog.members.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DiaryTag extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryTagId;

    @Column(nullable = false)
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "diaryTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryTagLink> diaryTagLinks = new ArrayList<>();
}

