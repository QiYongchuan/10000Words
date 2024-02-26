package com.example.demo.controller;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import com.example.demo.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {

    private final WordRepository wordRepository;
    private TranslationService translationService;
    @Autowired
    public WordController(WordRepository wordRepository, TranslationService translationService) {
        this.wordRepository = wordRepository;
        this.translationService = translationService;
    }

    // 获取所有单词
    @GetMapping
    public List getAllWords() {
        return wordRepository.findAll();
    }

    // POST endpoint to add a new word
    // 添加一个新单词
    @PostMapping
    public Word createWord(@RequestBody Word word) {
        String translation = translationService.translateWord(word.getWord());
        word.setTranslation(translation);
        return (Word) wordRepository.save(word);
    }

}
