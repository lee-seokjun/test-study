package com.example.studyapptest.study.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.example.studyapptest.App;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import javax.persistence.Entity;

@AnalyzeClasses(packagesOf = App.class)
public class ArchClassTest {
  @ArchTest
  ArchRule controllerClassRule = classes().that().haveSimpleNameEndingWith("Controller")
      .should().accessClassesThat().haveSimpleNameEndingWith("Service")
      .orShould().haveSimpleNameEndingWith("Repository");

  @ArchTest
  ArchRule repositoryClassRule = classes().that().haveSimpleNameEndingWith("..Repository..")
      .should().accessClassesThat().haveSimpleNameEndingWith("Service");

  @ArchTest
  ArchRule studyClassRule = classes().that().haveSimpleNameEndingWith("Study")
      .and().areNotEnums()
      .and().areNotAnnotatedWith(Entity.class)
      .should().resideInAnyPackage("..study");
}
