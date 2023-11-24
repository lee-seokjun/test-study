package com.example.studyapptest.mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Optional;

import com.example.studyapptest.domain.Member;
import com.example.studyapptest.domain.Study;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BddStudyServiceTest
{

    @DisplayName("BDD 스타일 API")
    @Test
    void createStudyService(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        //Given
        StudyService2 studyService = new StudyService2(memberService, studyRepository);
        Assertions.assertNotNull(studyService);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("iope100@naver.com");
        Study study = new Study(10, "java");

        //bdd에 따르면 given으로
//        when(memberService.findById(any())).thenReturn(Optional.of(member));
//        when(studyRepository.save(study)).thenReturn(study);

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.createNewStudy(1L, study);

        // Then
        Assertions.assertEquals(member, study.getOwner());

//        InOrder inOrder = Mockito.inOrder(memberService);
//        inOrder.verify(memberService).notify(study);
//        inOrder.verify(memberService).notify(member);

        then(memberService).should(times(1)).notify(study);
        then(memberService).should(times(1)).notify(member);
        then(memberService).shouldHaveNoMoreInteractions();

        verifyNoMoreInteractions(memberService); // 어떤 액션 이후 호출이 없어야 한다.

    }
}

