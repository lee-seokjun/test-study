package com.example.studyapptest.study.testcontainer;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.example.studyapptest.domain.Member;
import com.example.studyapptest.domain.Study;
import com.example.studyapptest.member.MemberService;
import com.example.studyapptest.study.StudyRepository;
import com.example.studyapptest.study.StudyService2;
import java.io.File;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
class DockerComposeTest
{

    @Mock MemberService memberService;
    @Container
    static DockerComposeContainer composeContainer =
        new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

    @Autowired StudyRepository studyRepository;
    @BeforeEach
    void BeforeEach() {
        studyRepository.deleteAll();
    }

    @DisplayName("BDD 스타일 API")
    @Test
    void createStudyService(){
        //Given
        StudyService2 studyService = new StudyService2(memberService, studyRepository);
        Assertions.assertNotNull(studyService);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("iope100@naver.com");
        Study study = new Study(10, "java");

        given(memberService.findById(1L)).willReturn(Optional.of(member));

        // When
        studyService.createNewStudy(1L, study);

        // Then
        Assertions.assertEquals(member.getId(), study.getOwnerId());


        then(memberService).should(times(1)).notify(study);
        then(memberService).should(times(1)).notify(member);
        then(memberService).shouldHaveNoMoreInteractions();

        verifyNoMoreInteractions(memberService); // 어떤 액션 이후 호출이 없어야 한다.
        Study findStudy = studyService.findById(study.getId()).orElseThrow(() -> new RuntimeException("study를 찾을 수 없음"));
        Assertions.assertEquals(findStudy.getOwnerId(), study.getOwnerId());
    }
    @DisplayName("beforeEach 동작 확인.")
    @Test
    void sizeCheck(){
        Assertions.assertEquals(studyRepository.findAll().size(), 0);

    }


}

