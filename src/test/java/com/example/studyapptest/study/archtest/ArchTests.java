package com.example.studyapptest.study.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.example.studyapptest.App;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

@AnalyzeClasses(packagesOf = App.class)
public class ArchTests {


  @ArchTest
  ArchRule domainPackageRule = classes().that().resideInAPackage("..domain..")
      .should().onlyBeAccessed().byClassesThat()
      .resideInAnyPackage("..study..", "..member..", "..domain..");
  @ArchTest
  ArchRule memberPackageRule = noClasses().that().resideInAnyPackage("..domain")
      .should().accessClassesThat().resideInAPackage("..member..");
  @ArchTest
  ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..")
      .should().accessClassesThat().resideInAnyPackage("..study.");
  @ArchTest
  ArchRule freeOfCycles = slices().matching("studyapptest.(*)..")
      .should().beFreeOfCycles();
  @Test
  void packageDependencyTests() {
    JavaClasses classes = new ClassFileImporter().importPackages("com.example.studyapptest");

    /**
     * .. domain.. 패키지에 있는 클래스는 ..study.., .member.. , ..domain에서 참조 가능
     *  ..member.. 패키지에 있는 클래스는 ..study.. 와 ..member.. 에서만 참조 가능.
     *   ..domain.. 패키지는 ..member.. 패키지를 참조하지 못한다.
     *   ..study.. 패키지에 있는 클래스는 ..study.. 에서만 참조 가능.
     *   순환 참조 없어야 함
     */


    domainPackageRule.check(classes);
    memberPackageRule.check(classes);
    studyPackageRule.check(classes);

    freeOfCycles.check(classes);
  }
}
