package com.alphaindelhi.question_answer_app.model;

public class Question {

    private String answer;
    private Boolean answerTrue;

    public Question ()
    {

    }

    public Question(String answer, Boolean answerTrue)
    {
        this.answer = answer;
        this.answerTrue = answerTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerTrue(Boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
