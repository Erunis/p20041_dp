package com.osu.dp.database;

import javax.persistence.*;

@Entity
public class Dictionary {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String pattern;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean longWord;

    public Dictionary(String word) {
        this.pattern = word;
    }

    public Dictionary() {

    }

    public Long getId() {
        return id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean isLongWord() {
        return longWord;
    }

    public void setLongWord(boolean longWord) {
        this.longWord = longWord;
    }
}
