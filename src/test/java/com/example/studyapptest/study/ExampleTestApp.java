package com.example.studyapptest.study;

import com.example.studyapptest.study.annotations.SlowTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

public class ExampleTestApp {

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


    @Test
    @DisplayName("테스트")
    @SlowTest
    @EnabledOnJre(JRE.JAVA_11)
    @EnabledOnOs(OS.WINDOWS)
    void exampleTestApp() {
        Assertions.assertEquals(0, 0);
        Assertions.assertTimeout(Duration.ofMillis(100), () -> Thread.sleep(10));
        Assertions.assertThrows(RuntimeException.class, this::runtimeException);
        SameName sameName = new SameName("name") ;
        SameName sameNameRef = sameName ;
        Assertions.assertSame(sameName, sameNameRef);
        Assertions.assertIterableEquals(Arrays.asList("1"), Arrays.asList("1"));

        Assertions.assertAll(
                () -> assertThat(this.runtimeException()).isEqualTo(1),
                () -> assertThat(1).isGreaterThan(getZero()),
                () -> assertThat(2).isIn(Arrays.asList(getZero(),1,2,3,4))

        );
    }
    int getZero() {
        System.out.println("called");
        return 0;
    }
    int runtimeException() {
        throw new RuntimeException("error ");
    }
    class SameName {
        private String name;

        public SameName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SameName sameName = (SameName) o;
            return Objects.equals(name, sameName.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }


}
