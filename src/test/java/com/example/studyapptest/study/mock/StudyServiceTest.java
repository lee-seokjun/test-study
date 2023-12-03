package com.example.studyapptest.study.mock;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.studyapptest.member.MemberService;
import com.example.studyapptest.study.StudyRepository;
import com.example.studyapptest.study.StudyService;
import com.example.studyapptest.study.StudyService2;
import java.util.Optional;

import com.example.studyapptest.domain.Member;
import com.example.studyapptest.domain.Study;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest
{

    @Test
    void createStudyService(@Mock MemberService memberService, @Mock StudyRepository studyRepository){

        Member member = new Member();
        member.setId(1L);
        member.setEmail("iope100@naver.com");
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Optional<Member> getMember = memberService.findById(1L);
        assertEquals("iope100@naver.com",getMember.get().getEmail());
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        assertThrows(IllegalArgumentException.class, () ->  memberService.validate(1L));

    }
    @Test
    void createStudyService2(@Mock MemberService memberService, @Mock StudyRepository studyRepository){

        Member member = new Member();
        member.setId(1L);
        member.setEmail("iope100@naver.com");
        when(memberService.findById(any()))
            .thenReturn(Optional.of(member))
            .thenThrow(new RuntimeException())
            .thenReturn(Optional.empty());

        Optional<Member> getMember = memberService.findById(1L);
        assertEquals("iope100@naver.com",getMember.get().getEmail());
        assertThrows(RuntimeException.class, () -> memberService.findById(2L));

        assertEquals(Optional.empty(), memberService.findById(3L));

    }
    @Test
    void createStudyService3(@Mock MemberService memberService, @Mock StudyRepository studyRepository){

        StudyService2 studyService = new StudyService2(memberService, studyRepository);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("iope100@naver.com");
        when(memberService.findById(any()))
            .thenReturn(Optional.of(member));


        Study study = new Study(10, "java");
        studyService.createNewStudy(1L, study);
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

        InOrder inOrder = Mockito.inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
        verifyNoMoreInteractions(memberService); // 어떤 액션 이후 호출이 없어야 한다.

    }
}

