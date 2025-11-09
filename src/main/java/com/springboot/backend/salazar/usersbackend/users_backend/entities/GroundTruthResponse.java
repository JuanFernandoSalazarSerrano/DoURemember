package com.springboot.backend.salazar.usersbackend.users_backend.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "ground_truth_response")
public class GroundTruthResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "airesponse", columnDefinition = "LONGTEXT")
    private String aiResponse;

    @Column(name = "rememberscore")
    private Integer rememberScore;

    @Column(name = "presententities", columnDefinition = "MEDIUMTEXT")
    private String presentEntities;

    @Column(name = "missingentities", columnDefinition = "MEDIUMTEXT")
    private String missingEntities;

    @Column(name = "incorrectdetails", columnDefinition = "MEDIUMTEXT")
    private String incorrectDetails;

    @Column(name = "confabulateddetails", columnDefinition = "MEDIUMTEXT")
    private String confabulatedDetails;

    @Column(name = "presence")
    private Double presence;

    @Column(name = "accuracy")
    private Double accuracy;

    @Column(name = "omission")
    private Double omission;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "explanation", columnDefinition = "MEDIUMTEXT")
    private String explanation;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User usergroundTruthResponse;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public Integer getRememberScore() {
        return rememberScore;
    }

    public void setRememberScore(Integer rememberScore) {
        this.rememberScore = rememberScore;
    }

    public String getPresentEntities() {
        return presentEntities;
    }

    public void setPresentEntities(String presentEntities) {
        this.presentEntities = presentEntities;
    }

    public String getMissingEntities() {
        return missingEntities;
    }

    public void setMissingEntities(String missingEntities) {
        this.missingEntities = missingEntities;
    }

    public String getIncorrectDetails() {
        return incorrectDetails;
    }

    public void setIncorrectDetails(String incorrectDetails) {
        this.incorrectDetails = incorrectDetails;
    }

    public String getConfabulatedDetails() {
        return confabulatedDetails;
    }

    public void setConfabulatedDetails(String confabulatedDetails) {
        this.confabulatedDetails = confabulatedDetails;
    }

    public Double getPresence() {
        return presence;
    }

    public void setPresence(Double presence) {
        this.presence = presence;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getOmission() {
        return omission;
    }

    public void setOmission(Double omission) {
        this.omission = omission;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}


