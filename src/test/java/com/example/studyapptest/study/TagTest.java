package com.example.studyapptest.study;


import com.example.studyapptest.study.annotations.FastTest;
import com.example.studyapptest.study.annotations.SlowTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TagTest
{
    @DisplayName("fast test2")
    @Tag("fast")
    @Test
    void fastTest() {
        System.out.println("fast");
    }
    @DisplayName("slow test")
    @Tag("slow")
    @Test
    void slowTest() {
        System.out.println("slow");
    }
    @DisplayName("fast test2")
    @Test
    @FastTest
    void fastTest2() {
        System.out.println("fast");
    }
    @DisplayName("slow test")
    @Test
    @SlowTest
    void slowTest2() {
        System.out.println("slow");
    }
}