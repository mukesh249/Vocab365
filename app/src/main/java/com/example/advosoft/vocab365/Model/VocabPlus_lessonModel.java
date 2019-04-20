package com.example.advosoft.vocab365.Model;

import java.io.Serializable;

public class VocabPlus_lessonModel implements Serializable {
    String lesson_id,lesson_name,lesson_pdf,lesson_video,lesson_video_link;

    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public String getLesson_pdf() {
        return lesson_pdf;
    }

    public void setLesson_pdf(String lesson_pdf) {
        this.lesson_pdf = lesson_pdf;
    }

    public String getLesson_video() {
        return lesson_video;
    }

    public void setLesson_video(String lesson_video) {
        this.lesson_video = lesson_video;
    }

    public String getLesson_video_link() {
        return lesson_video_link;
    }

    public void setLesson_video_link(String lesson_video_link) {
        this.lesson_video_link = lesson_video_link;
    }
}
