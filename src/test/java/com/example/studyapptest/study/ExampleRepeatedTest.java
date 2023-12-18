package com.example.studyapptest.study;

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
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleRepeatedTest
{


    @DisplayName("테스트 반복")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition} / {totalRepetitions}")// displayname 반복을 위함
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
    @ParameterizedTest(name = "{index} {displayName} message={0} , v = {1}")
    @CsvSource({"first, seok", "second, jun"})
    void parameterizedTest3(@AggregateWith(UserAggregator.class) User user) {
        System.out.println(user.id);
        System.out.println(user.name);
    }
    static class User {
        public String id;
        public String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    static class UserAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException
        {
            return new User(argumentsAccessor.getString(0),argumentsAccessor.getString(1));
        }
    }
    @DisplayName("파라미터 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0} , v = {1}")
    @ValueSource(strings= {"first, seok", "second, jun"})
    void ConverterTest(@ConvertWith(ExampleRepeatedTest.UserConverter.class) User user) {
        System.out.println(user.id);
        System.out.println(user.name);
    }

    static class UserConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException
        {
            assertEquals(User.class, aClass,"can only convert to Study");
            String str = o.toString();
            String[] strArr = str.split(",");

            return new User(strArr[0],strArr[1]);
        }
    }

}
