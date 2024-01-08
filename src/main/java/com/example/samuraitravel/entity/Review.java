package com.example.samuraitravel.entity;

 import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
 
 @Entity
 @Table(name = "reviews")
 @Data
public class Review {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private Integer id;
 
     @Column(name = "name")
     private String name;
 
     @Column(name = "commenttext")
     private String commentText;
 
     @Column(name = "value")
     private Integer value;
 
     @Column(name = "updated_at", insertable = false, updatable = false)
     private Timestamp updatedAt;
}