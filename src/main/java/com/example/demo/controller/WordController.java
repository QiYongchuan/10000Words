package com.example.demo.controller;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import com.example.demo.service.ImgCreateService;
import com.example.demo.service.TranslationService;
import com.example.demo.service.WordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/words")
public class WordController {



    @Autowired
    private ImgCreateService imgCreateService;
    private final WordRepository wordRepository;
    private TranslationService translationService;
    @Autowired
    public WordController(ImgCreateService imgCreateService, WordRepository wordRepository, TranslationService translationService) {
        this.imgCreateService = imgCreateService;
        this.wordRepository = wordRepository;
        this.translationService = translationService;
    }

    // 获取所有单词


    @GetMapping()
    public ResponseEntity<List<Word>> getAllWords() {
        // 查询数据库中所有单词
        List<Word> words = WordService.getAllWords();
        return ResponseEntity.ok(words);
    }

    // POST endpoint to add a new word
    // 添加一个新单词

    @PostMapping
    public Word createWord(@RequestBody Word word)  {


        return   WordService.addWord(word);
    }

    @PostMapping("/img")
    public ResponseEntity<String> createWordWithImage(@RequestBody Word word) {
        // 假设Word类中有一个getString方法来获取文本内容
        String imageResult = imgCreateService.generateImageFromText(word.getWord());
        return ResponseEntity.ok(imageResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        WordService.deleteWord(id);
        return ResponseEntity.noContent().build();
    }





}
