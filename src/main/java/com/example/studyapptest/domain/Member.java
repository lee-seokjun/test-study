package com.example.studyapptest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member
{
    @Id
    @GeneratedValue
    private long id;
    private String email;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
