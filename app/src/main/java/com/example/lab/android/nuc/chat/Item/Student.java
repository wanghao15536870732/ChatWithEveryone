package com.example.lab.android.nuc.chat.Item;

import com.example.lab.android.nuc.chat.SetTeacher;

public class Student {
    private SetTeacher setTeacher;

    public Student(SetTeacher setTeacher) {
        this.setTeacher = setTeacher;
    }

    public SetTeacher getSetTeacher() {
        return setTeacher;
    }

    public void setSetTeacher(SetTeacher setTeacher) {
        this.setTeacher = setTeacher;
    }
}