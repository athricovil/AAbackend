package com.ayurayush.server.controller;

import com.ayurayush.server.dto.QuestionnaireRequest;
import com.ayurayush.server.entity.Questionnaire;
import com.ayurayush.server.entity.User;
import com.ayurayush.server.repository.UserRepository;
import com.ayurayush.server.service.QuestionnaireService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {
    
    @Autowired
    private QuestionnaireService questionnaireService;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/submit")
    public ResponseEntity<?> submitQuestionnaire(@Valid @RequestBody QuestionnaireRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            Questionnaire questionnaire = questionnaireService.submitQuestionnaire(user, request);
            
            return ResponseEntity.ok(Map.of(
                "message", "Questionnaire submitted successfully",
                "questionnaireId", questionnaire.getId(),
                "recommendedProducts", questionnaire.getRecommendedProducts()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getUserQuestionnaires() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            List<Questionnaire> questionnaires = questionnaireService.getUserQuestionnaires(user);
            return ResponseEntity.ok(questionnaires);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestQuestionnaire() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            Questionnaire questionnaire = questionnaireService.getLatestQuestionnaire(user);
            if (questionnaire == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(questionnaire);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/check-required")
    public ResponseEntity<?> checkQuestionnaireRequired() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            Questionnaire latestQuestionnaire = questionnaireService.getLatestQuestionnaire(user);
            boolean questionnaireRequired = latestQuestionnaire == null;
            
            return ResponseEntity.ok(Map.of(
                "questionnaireRequired", questionnaireRequired,
                "latestQuestionnaire", latestQuestionnaire
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 