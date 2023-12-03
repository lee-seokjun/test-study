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
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(initializers = ContainerTestForSpring.ContainerPropertyInitializer.class)
class ContainerTestForSpring
{
    static Logger logger = LoggerFactory.getLogger(ContainerTestForSpring.class);
    @Mock MemberService memberService;
    @Container
    static GenericContainer genericContainer = new GenericContainer("postgres")
        .withExposedPorts(5432) //어떤 포트로 exporse 해줄 것인가?
        .withEnv("POSTGRES_DB", "studytest")
        .withEnv("POSTGRES_PASSWORD","1234");
    //        .waitingFor(Wait.forListeningPort()); // 가용한가 ? 확인
//        .waitingFor(Wait.forHttp());
    @Autowired StudyRepository studyRepository;
    @Autowired Environment environment;
    @Value("${container.port}") Long value;

    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger);
        genericContainer.followOutput(logConsumer); // container 로그가 쌓일 때 마다 로그 추가
    }
    @BeforeEach
    void BeforeEach() {
        System.out.println(environment.getProperty("container.port"));
        System.out.println(value);
//        System.out.println(genericContainer.getMappedPort(5432)); // expose 된 bounded port 확인에 사용.
        System.out.println(genericContainer.getLogs()); // log를 찍자.
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

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            //spring property에 container에 대한 정보 추가.
            genericContainer.start();
            TestPropertyValues
                .of("container.port=" + genericContainer.getMappedPort(5432))
                .applyTo(context.getEnvironment());
        }
    }
}

