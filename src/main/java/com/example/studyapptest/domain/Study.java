package com.example.studyapptest.domain;

import com.example.studyapptest.StudyStatus;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;

@Entity
public class Study {
    @Id
    @GeneratedValue
    private String id;
    public static final String LIMIT_MESSAGE = "limit은 0보다 커야한다.";
    private StudyStatus status = StudyStatus.DRAFT;
    private long limit;
    private String name;
    private Long ownerId;
    public Study( long limit, String name)
    {
        this.limit = limit;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Study{" +
            "status=" + status +
            ", limit=" + limit +
            ", name='" + name + '\'' +
            '}';
    }

    public Study()
    {
    }

    public Study(long limit)
    {
        if(limit < 0) {
            throw new IllegalArgumentException(LIMIT_MESSAGE);
        }
        this.limit = limit;
    }

    public long getLimit()
    {
        return limit;
    }

    public StudyStatus getStatus()
    {
        return status;
    }

    public Long getOwnerId()
    {
        return ownerId;
    }

    public void setOwner(Long ownerId)
    {
        this.ownerId = ownerId;
    }
}
