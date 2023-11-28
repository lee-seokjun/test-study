package com.example.studyapptest.study;

import com.example.studyapptest.domain.Study;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
public class RepeatedTestStudy
{

    @RepeatedTest(10)// 반복을 위함
    void create_study() {
        System.out.println("test");
    }
    @DisplayName("스터디 만들기 반복")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition} / {totalRepetitions}")// 반복을 위함
    void create_study_info(RepetitionInfo repetitionInfo) {
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/"
        +   repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가","많이","추워지고","있네요"})
    void parameterizedTest(String message) {
        System.out.println(message);
    }
    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
//    @EmptySource
//    @NullSource
    @NullAndEmptySource // 위 두개 합친거

    void parameterizedTest2(String message) {
        System.out.println(message);
    }
    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0} , v = {1}")
    @ValueSource(ints= {10,20,30})
    void parameterizedTest3(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getStatus());
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException
        {
            assertEquals(Study.class, aClass,"can only convert to Study");
            return new Study(Integer.parseInt(o.toString()));
        }
    }
    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0} , v = {1}")
    @CsvSource({"java, 10", "java, 7"})
    void parameterizedTestCvs(String message, int v) {
        System.out.println(message + v);
    }
    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0} , v = {1}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void parameterizedTestCvs2(Integer limit, String name) {
        System.out.println(new Study(limit, name));
    }
    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0} , v = {1}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void parameterizedTestCvs3(ArgumentsAccessor argumentsAccessor) {
        Study study =new Study(argumentsAccessor.getInteger(0),argumentsAccessor.getString(1));
        System.out.println(study);
    }
    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0} , v = {1}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void parameterizedTestCvs4(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }
    //반드시 static , public 클라스 형태로 만들어 줘야함.
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException
        {
            return new Study(argumentsAccessor.getInteger(0),argumentsAccessor.getString(1));
        }
    }
}
