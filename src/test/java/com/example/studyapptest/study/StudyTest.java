package com.example.studyapptest.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import com.example.studyapptest.StudyStatus;
import java.time.Duration;
import java.util.function.Supplier;

import com.example.studyapptest.domain.Study;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest
{
    @Test
    void create_new_study() {
        IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> new Study(-1));
        assertEquals(Study.LIMIT_MESSAGE, exception.getMessage());
        Study st  = new Study(1);
        assertThat(st.getLimit()).isGreaterThan(0);
    }

    @Test
    void test_all() {
        Study study = new Study(-10);
        assertAll(
            () ->         Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(),
                "스터디 생성시 상태값이 DRAFT 이어야 한다."),
            () -> assertNotNull(study),
            () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                () ->"스터디 생성시 상태값이 "+ StudyStatus.DRAFT + " 이어야 한다."),
            () ->         assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier() {

                @Override
                public Object get()
                {
                    return "스터디 생성시 상태값이 DRAFT 이어야 한다.";
                }
            }),
            () -> assertTrue(study.getLimit() > 0 , "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @Test
    void timeout()
    {
        assertTimeout(Duration.ofMillis(10), () -> {
            new Study(10);
            Thread.sleep(100);
        });
    }

    @Test
    @EnabledOnOs(value = OS.MAC) // os 확인하여 테스트 할지
    @EnabledOnJre({org.junit.jupiter.api.condition.JRE.JAVA_8}) // java version 확인하여 테스트할지 결정
    @EnabledIfEnvironmentVariable(named ="TEST_ENV", matches = "LOCAL")
    //@DisabledOnJre()
    //@DisabledOnOs()
    void assumeTrueTest() {
        String test_env = System.getenv("TEST_ENV");
        //환경변수에 따라 테스트 가능환경인지 체크
        System.out.println(test_env);
//        assumeTrue("LOCAL".equalsIgnoreCase(test_env));
        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(1);

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> assertThat(study.getLimit()).isGreaterThan(1));
        assumingThat("PROD".equalsIgnoreCase(test_env), () -> assertThat(study.getLimit()).isGreaterThan(1));
    }

    @Test
    void timeoutPreemptively()
    {

        //thread
        assertTimeoutPreemptively(Duration.ofMillis(10), () -> {
            new Study(10);
            Thread.sleep(100);
        });
    }


    @BeforeAll
    static void before() {
        System.out.println("before");
    }
    @AfterAll
    static void after() {
        System.out.println("afterAll");
    }
    @BeforeEach
     void beforeEach() {
        System.out.println("before each");
    }
    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
}