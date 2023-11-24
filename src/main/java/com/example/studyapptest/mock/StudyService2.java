package com.example.studyapptest.mock;

import java.util.Optional;

import com.example.studyapptest.domain.Member;
import com.example.studyapptest.domain.Study;

public class StudyService2 extends StudyService
{

    public StudyService2(MemberService memberService, StudyRepository repository)
    {
        super(memberService, repository);
    }

    public Study createNewStudy (Long memberId, Study study) {
        Optional<Member> member = super.memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException(" member id does not exists")));
        repository.save(study);
        memberService.notify(study);
        memberService.notify(member.get());
        return study;
    }
}
