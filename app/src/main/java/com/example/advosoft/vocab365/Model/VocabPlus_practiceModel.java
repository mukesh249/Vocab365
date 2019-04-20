package com.example.advosoft.vocab365.Model;

import java.io.Serializable;

public class VocabPlus_practiceModel implements Serializable {
    String practice_id,practice_name;

    public String getPractice_id() {
        return practice_id;
    }

    public void setPractice_id(String practice_id) {
        this.practice_id = practice_id;
    }

    public String getPractice_name() {
        return practice_name;
    }

    public void setPractice_name(String practice_name) {
        this.practice_name = practice_name;
    }
}
