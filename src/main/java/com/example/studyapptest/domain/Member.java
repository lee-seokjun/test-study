package com.example.studyapptest.domain;

public class Member
{
    private long Id;
    private String email;

    public long getId()
    {
        return Id;
    }

    public void setId(long id)
    {
        Id = id;
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
