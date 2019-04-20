package com.example.advosoft.vocab365.Model;

import java.io.Serializable;

public class DateItem implements Serializable {
    int VocabCount;
    private String date;
    private String id;
    private String img_url;
    private String title;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVocabCount(int vocabCount) {
        this.VocabCount = vocabCount;
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DateItem other = (DateItem) obj;
        if (this.img_url.equals(other.img_url) && this.title.equals(other.title)) {
            return true;
        }
        return false;
    }
}
