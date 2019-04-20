package com.example.advosoft.vocab365.wrapper;

import java.io.Serializable;
import java.util.List;

public class testModal implements Serializable {
    String attemp;
    List<question> question_list;
    String right_mark;
    String rowsqsmar;
    int status;
    int total_question;
    String total_time;

    public static class question implements Serializable {
        String answer;
        String corret_mark;
        String direction;
        String explanation;
        String id;
        String no_of_attemp;
        String noofoption;
        String opt_1;
        String opt_2;
        String opt_3;
        String opt_4;
        String opt_5 = "";
        String opt_hi_1;
        String opt_hi_2;
        String opt_hi_3;
        String opt_hi_4;
        String opt_hi_5;
        String question;
        String questionhi;
        String time;
        String topic_name = "";
        String ttl_ques;
        private String uniq;
        String wrong_mark;

        public void setQuestion(String question) {
            this.question = question;
        }

        public void setOpt_1(String opt_1) {
            this.opt_1 = opt_1;
        }

        public void setOpt_2(String opt_2) {
            this.opt_2 = opt_2;
        }

        public void setOpt_3(String opt_3) {
            this.opt_3 = opt_3;
        }

        public void setOpt_4(String opt_4) {
            this.opt_4 = opt_4;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public void setNoofoption(String noofoption) {
            this.noofoption = noofoption;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniq() {
            return this.uniq;
        }

        public void setUniq(String uniq) {
            this.uniq = uniq;
        }

        public String getOpt_5() {
            return this.opt_5;
        }

        public String getId() {
            return this.id;
        }

        public String getTopic_name() {
            return this.topic_name;
        }

        public String getTtl_ques() {
            return this.ttl_ques;
        }

        public void setCorret_mark(String corret_mark) {
            this.corret_mark = corret_mark;
        }

        public void setWrong_mark(String wrong_mark) {
            this.wrong_mark = wrong_mark;
        }

        public void setOpt_5(String opt_5) {
            this.opt_5 = opt_5;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public String getCorret_mark() {
            return this.corret_mark;
        }

        public String getWrong_mark() {
            return this.wrong_mark;
        }

        public void setNo_of_attemp(String no_of_attemp) {
            this.no_of_attemp = no_of_attemp;
        }

        public void setTtl_ques(String ttl_ques) {
            this.ttl_ques = ttl_ques;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getQuestion() {
            return this.question;
        }

        public String getOpt_1() {
            return this.opt_1;
        }

        public String getOpt_2() {
            return this.opt_2;
        }

        public String getOpt_3() {
            return this.opt_3;
        }

        public String getOpt_4() {
            return this.opt_4;
        }

        public String getOpt_hi_1() {
            return this.opt_hi_1;
        }

        public String getOpt_hi_2() {
            return this.opt_hi_2;
        }

        public String getOpt_hi_3() {
            return this.opt_hi_3;
        }

        public String getOpt_hi_4() {
            return this.opt_hi_4;
        }

        public String getOpt_hi_5() {
            return this.opt_hi_5;
        }

        public String getAnswer() {
            return this.answer;
        }

        public String getExplanation() {
            return this.explanation;
        }

        public String getDirection() {
            return this.direction;
        }

        public String getQuestionhi() {
            return this.questionhi;
        }

        public String getNoofoption() {
            return this.noofoption;
        }
    }

    public int getStatus() {
        return this.status;
    }

    public int getTotal_question() {
        return this.total_question;
    }

    public String getAttemp() {
        return this.attemp;
    }

    public String getTotal_time() {
        return this.total_time;
    }

    public String getRowsqsmar() {
        return this.rowsqsmar;
    }

    public String getRight_mark() {
        return this.right_mark;
    }

    public List<question> getQuestion_list() {
        return this.question_list;
    }
}
