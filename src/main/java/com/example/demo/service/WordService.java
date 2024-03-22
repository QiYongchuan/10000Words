package com.example.demo.service;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author QiYongChuan
 * @Version 1.0
 * 2024/3/15
 */
//简单地说，`wordRepository`用于直接与数据库进行通信，执行基本的数据操作。
// 而`wordService`则是一个中间层，用于定义和执行业务逻辑，
// 它通常会使用`wordRepository`来完成数据的实际操作。


// 对实体Word的各种操作：
// 1.增删改查，但是是通过调用wordRepository 的基础方法来实现的
// 2.最主要的是，这里来实现各种的复杂业务逻辑：如在添加单词之前进行翻译，
@Service
public class WordService {

    private static WordRepository wordRepository ;


    @Autowired
    private TranslationService translationService;

    public WordService(WordRepository wordRepository, TranslationService translationService) {
        this.wordRepository = wordRepository;
        this.translationService = translationService;
    }

    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word saveWord(Word word) {
        return wordRepository.save(word);
    }

    public static List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    public Word getWordById(Long id) {
        return wordRepository.findById(id).orElse(null);
    }

    public void deleteWord(Long id) {
        wordRepository.deleteById(id);
    }

    // 上面是基础的增删改查，下面开始实现一些复杂业务逻辑

    public static Word addWord(Word word) {
        // 首先检查单词是否已存在
        Optional<Word> existingWord = wordRepository.findByWord(word.getWord());
        if (existingWord.isPresent()) {
            // 如果单词已存在，直接返回数据库中的翻译
            return existingWord.get();
        } else {
            // 否则，调用翻译服务进行翻译
            String translation = TranslationService.translateWord(word.getWord());
            word.setTranslation(translation);
            // 保存新单词及其翻译到数据库
            return wordRepository.save(word);
        }
    }

    public Word updateWord(Word word) {  // 更新单词，但是方法没有研究
        Word oldWord = wordRepository.findById(word.getId()).orElse(null);
        if (oldWord != null) {
            oldWord.setWord(word.getWord());
            oldWord.setTranslation(word.getTranslation());
            return wordRepository.save(oldWord);
        }
        return null;
    }
    public List<Word> searchWord(String word) {
        return wordRepository.findByWordContaining(word);
    }

//    public List<Word> getWordsForUser(Long userId) {
//        // 可能涉及复杂的业务逻辑，如用户的单词学习进度，权限验证等
//        return wordRepository.findByUser_Id(userId);
//    }
}
