package com.ayurayush.server.service;

import com.ayurayush.server.dto.QuestionnaireRequest;
import com.ayurayush.server.entity.Questionnaire;
import com.ayurayush.server.entity.Product;
import com.ayurayush.server.entity.User;
import com.ayurayush.server.repository.QuestionnaireRepository;
import com.ayurayush.server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {
    
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    
    @Autowired
    private ProductRepository productRepository;

    public Questionnaire submitQuestionnaire(User user, QuestionnaireRequest request) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setUser(user);
        questionnaire.setAgeGroup(request.getAgeGroup());
        questionnaire.setHealthConcerns(request.getHealthConcerns());
        questionnaire.setCurrentMedications(request.getCurrentMedications());
        questionnaire.setAllergies(request.getAllergies());
        questionnaire.setDietaryRestrictions(request.getDietaryRestrictions());
        questionnaire.setLifestyleFactors(request.getLifestyleFactors());
        questionnaire.setStressLevel(request.getStressLevel());
        questionnaire.setSleepQuality(request.getSleepQuality());
        questionnaire.setDigestiveHealth(request.getDigestiveHealth());
        questionnaire.setSkinConcerns(request.getSkinConcerns());
        
        // Generate product recommendations based on health concerns
        String recommendedProducts = generateProductRecommendations(request);
        questionnaire.setRecommendedProducts(recommendedProducts);
        
        questionnaire.setUpdatedAt(LocalDateTime.now());
        
        return questionnaireRepository.save(questionnaire);
    }

    public List<Questionnaire> getUserQuestionnaires(User user) {
        return questionnaireRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Questionnaire getLatestQuestionnaire(User user) {
        return questionnaireRepository.findFirstByUserOrderByCreatedAtDesc(user).orElse(null);
    }

    private String generateProductRecommendations(QuestionnaireRequest request) {
        List<Product> allProducts = productRepository.findAll();
        List<Product> recommendedProducts = allProducts.stream()
            .filter(product -> matchesHealthConcerns(product, request))
            .limit(5) // Limit to top 5 recommendations
            .collect(Collectors.toList());
        
        return recommendedProducts.stream()
            .map(Product::getName)
            .collect(Collectors.joining(","));
    }

    private boolean matchesHealthConcerns(Product product, QuestionnaireRequest request) {
        String healthConcerns = request.getHealthConcerns().toLowerCase();
        String productName = product.getName().toLowerCase();
        String productDescription = product.getDescription().toLowerCase();
        
        // Simple matching logic - can be enhanced with AI/ML
        if (healthConcerns.contains("digestive") || healthConcerns.contains("stomach")) {
            return productName.contains("digestive") || productName.contains("stomach") || 
                   productDescription.contains("digestive") || productDescription.contains("stomach");
        }
        
        if (healthConcerns.contains("skin") || healthConcerns.contains("acne")) {
            return productName.contains("skin") || productName.contains("acne") || 
                   productDescription.contains("skin") || productDescription.contains("acne");
        }
        
        if (healthConcerns.contains("stress") || healthConcerns.contains("anxiety")) {
            return productName.contains("stress") || productName.contains("anxiety") || 
                   productDescription.contains("stress") || productDescription.contains("anxiety");
        }
        
        if (healthConcerns.contains("sleep") || healthConcerns.contains("insomnia")) {
            return productName.contains("sleep") || productName.contains("insomnia") || 
                   productDescription.contains("sleep") || productDescription.contains("insomnia");
        }
        
        // Default recommendation for general wellness
        return productName.contains("immunity") || productName.contains("wellness") || 
               productDescription.contains("immunity") || productDescription.contains("wellness");
    }
} 