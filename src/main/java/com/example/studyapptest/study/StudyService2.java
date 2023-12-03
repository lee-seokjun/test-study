package com.example.studyapptest.study;

import com.example.studyapptest.member.MemberService;
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
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException(" member id does not exists"))
            .getId());
        repository.save(study);
        memberService.notify(study);
        memberService.notify(member.get());
        return study;
    }
    public Optional<Study> findById (Long id) {
        return repository.findById(id);
    }
}
