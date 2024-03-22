package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Word;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository <Word, Long> {
//    List<Word> findByUser_Id(Long userId);

    List<Word> findByWordContaining(String word);

    Optional<Word> findByWord(String word);
}
