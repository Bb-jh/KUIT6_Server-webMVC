package jwp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Question {
    private final Long questionId;
    private final String writer;
    private final String title;
    private final String contents;
    private final Timestamp createdDate;
    private final int countOfAnswer;

    public Question(Long questionId, String writer, String title, String contents, Timestamp createdDate, int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(String writer, String title, String contents) {
        this(null, writer, title, contents, new Timestamp(System.currentTimeMillis()), 0);
    }

    public Long getQuestionId() { return questionId; }
    public String getWriter() { return writer; }
    public String getTitle() { return title; }
    public String getContents() { return contents; }
    public Timestamp getCreatedDate() { return createdDate; }
    public int getCountOfAnswer() { return countOfAnswer; }

}
