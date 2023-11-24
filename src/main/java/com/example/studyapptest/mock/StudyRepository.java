package com.example.studyapptest.mock;

import com.example.studyapptest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, String>
{
}
