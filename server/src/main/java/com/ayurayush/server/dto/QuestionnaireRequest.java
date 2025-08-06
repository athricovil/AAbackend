package com.ayurayush.server.dto;

import jakarta.validation.constraints.NotBlank;

public class QuestionnaireRequest {
    @NotBlank(message = "Age group is required")
    private String ageGroup;

    @NotBlank(message = "Health concerns are required")
    private String healthConcerns;

    private String currentMedications;
    private String allergies;
    private String dietaryRestrictions;
    private String lifestyleFactors;
    private String stressLevel;
    private String sleepQuality;
    private String digestiveHealth;
    private String skinConcerns;

    // Getters and Setters
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
} 