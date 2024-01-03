1. Junit
  - @DisplayName(value = "fast test2")
  - assertEquals(expected , actual)
  - assertThrows(RuntimeException.class, Executable ) //  Executable : functional interface
  - assertThat(actual)  (.isBetween, isCloseTo, isZero ...)
  - assertAll
  - assertTimeout(java.time.Duration, Executable)
  - EnabledOnOs
  - BeforeAll BeforeEach
  - AfterAll AfterEach
2. for CI / CD
  - tag annoation을 이용하여 특정 tag의 test만 실행하도록 사용 가능.
  - 방법 1 : run configuration 
  - 방법 2: maven profile 구분
    <profile>
            <id>ci</id>
            <build>
                <plugins>
                    <plugin>
                        <artifactId>maven-surefire-plugin</artifactId>
                        <configuration><groups>fast | slow </groups></configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
3. extension : 테스트 동작에 필요한 전, 후 처리를 extension 으로 만들 수 있음.  
   - @ExtendWith 으로 주입받는 방법
   - @RegisterExtension
4. mock :구현체가 없는 경우 사용할 수 있음.
   - 구현체가 없을 때 사용할 수 있음.
   - interface만 정의되었을 때, 특정 method의 결과값을 테스트에서 정의하여 다른 테스트를 확인할 때 사용
   - 해당 interface의 method가 몇번 호출되었는지, 특정 시점 이후 호출되지 않았는지 등 체크가능.
   - BDD Behavior Driven design  given when then 
5. test container : postgresql, elasticsearch 와 같은 인프라 환경을 구성하기 위해 사용, test run 시 docker container의 stop , run을 자동으로 해줌
6. chaos monkey : service, repository 등 application에 부하가 있는 것처럼 할 수 있음. 
   - ex) postgresql에서 응답값이 느릴때 동작 확인.
7. archtest : 아키텍쳐 테스트 
   - 클래스가 적절한 패키지에 들어가 있는지 확인
   - 순환 참조 테스트
   


