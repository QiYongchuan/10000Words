package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

/**
 * 实体类：Word
 */
@Entity
public class Word {
    /**
     * ID字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 单词字段
     */
    private String word;
    /**
     * 翻译字段
     */
    private String translation;

    // Standard getters and setters

    /**
     * 获取ID
     *
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取单词
     *
     * @return 单词
     */
    public String getWord() {
        return word;
    }

    /**
     * 设置单词
     *
     * @param word 单词
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * 获取翻译
     *
     * @return 翻译
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * 设置翻译
     *
     * @param translation 翻译
     */
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    // Default constructor for JPA

    /**
     * 默认构造函数，用于JPA
     */
    public Word() {
    }

    // Constructor for convenience

    /**
     * 方便使用的构造函数
     *
     * @param word   单词
     * @param translation 翻译
     */
    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }
}
