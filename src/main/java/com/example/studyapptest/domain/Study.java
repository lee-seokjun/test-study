package com.example.studyapptest.domain;

import com.example.studyapptest.StudyStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    public static final String LIMIT_MESSAGE = "limit은 0보다 커야한다.";
    private StudyStatus status = StudyStatus.DRAFT;
    @Column(name = "c_limit")
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

    public Long getId() {
        return id;
    }
}
