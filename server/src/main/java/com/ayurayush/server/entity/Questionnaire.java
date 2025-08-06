package com.ayurayush.server.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "questionnaires")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "health_concerns")
    private String healthConcerns;

    @Column(name = "current_medications")
    private String currentMedications;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "dietary_restrictions")
    private String dietaryRestrictions;

    @Column(name = "lifestyle_factors")
    private String lifestyleFactors;

    @Column(name = "stress_level")
    private String stressLevel;

    @Column(name = "sleep_quality")
    private String sleepQuality;

    @Column(name = "digestive_health")
    private String digestiveHealth;

    @Column(name = "skin_concerns")
    private String skinConcerns;

    @Column(name = "recommended_products")
    private String recommendedProducts;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Questionnaire() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }

    public String getHealthConcerns() { return healthConcerns; }
    public void setHealthConcerns(String healthConcerns) { this.healthConcerns = healthConcerns; }

    public String getCurrentMedications() { return currentMedications; }
    public void setCurrentMedications(String currentMedications) { this.currentMedications = currentMedications; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getDietaryRestrictions() { return dietaryRestrictions; }
    public void setDietaryRestrictions(String dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }

    public String getLifestyleFactors() { return lifestyleFactors; }
    public void setLifestyleFactors(String lifestyleFactors) { this.lifestyleFactors = lifestyleFactors; }

    public String getStressLevel() { return stressLevel; }
    public void setStressLevel(String stressLevel) { this.stressLevel = stressLevel; }

    public String getSleepQuality() { return sleepQuality; }
    public void setSleepQuality(String sleepQuality) { this.sleepQuality = sleepQuality; }

    public String getDigestiveHealth() { return digestiveHealth; }
    public void setDigestiveHealth(String digestiveHealth) { this.digestiveHealth = digestiveHealth; }

    public String getSkinConcerns() { return skinConcerns; }
    public void setSkinConcerns(String skinConcerns) { this.skinConcerns = skinConcerns; }

    public String getRecommendedProducts() { return recommendedProducts; }
    public void setRecommendedProducts(String recommendedProducts) { this.recommendedProducts = recommendedProducts; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 