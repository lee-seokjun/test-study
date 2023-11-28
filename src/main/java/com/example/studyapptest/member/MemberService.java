package com.example.studyapptest.member;

import java.util.Optional;

import com.example.studyapptest.domain.Member;
import com.example.studyapptest.domain.Study;

public interface MemberService
{
    Optional<Member> findById(Long memberId);
    void notify(Study memberId);
    void validate(Long memberId);
    void notify (Member member);
}
