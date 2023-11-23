package com.example.studyapptest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

/*
* 테스트 메서드마다 새로운 인스턴스를 만드는게 기본 전략임.
* 테스트마다 의존성을 없애기 위함
* 순서도 항상 정해져있지 않음..
* @TestInstance 를 활용하여 Lifecycle을 지정해 줄 수 있음.
* 만약 class단위로 지정한다면 before, after 도 class단위로 설정되어 클레스당 한번만 실행됨
* 순서 지정을 위해 TestMethodOrder 사용.
* */
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestInstanceStudy
{
    int value = 1;

    @BeforeAll
    void before() {
        System.out.println("before");
    }
    @AfterAll
    void after() {
        System.out.println("after");
    }
    @Test
    @RepeatedTest(10)
    @Order(10)
    void valueTest() {
        System.out.println("one " +value++);
    }
    @Test
    @Order(1)
    void valueTest2() {
        System.out.println("two " +value++);
    }
}
