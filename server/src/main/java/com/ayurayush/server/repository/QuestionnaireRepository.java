package com.ayurayush.server.repository;

import com.ayurayush.server.entity.Questionnaire;
import com.ayurayush.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    List<Questionnaire> findByUserOrderByCreatedAtDesc(User user);
    Optional<Questionnaire> findFirstByUserOrderByCreatedAtDesc(User user);
} 