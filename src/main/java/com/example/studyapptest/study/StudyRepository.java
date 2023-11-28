package com.example.studyapptest.study;

import com.example.studyapptest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, String>
{
}
