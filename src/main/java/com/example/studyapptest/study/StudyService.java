package com.example.studyapptest.study;

import com.example.studyapptest.member.MemberService;
import java.util.Optional;

import com.example.studyapptest.domain.Study;
import com.example.studyapptest.domain.Member;

public class StudyService
{
    protected final MemberService memberService;
    protected final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository)
    {
        assert memberService != null;
        assert  repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy (Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member
            .orElseThrow(() -> new IllegalArgumentException(" member id does not exists"))
            .getId()
        );
        return repository.save(study);
    }
}
