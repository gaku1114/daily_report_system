package com.techacademy.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;

import lombok.Data;

@Data
@Entity
@Table(name = "Employee")
@Where(clause = "delete_flag = 0")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    @NotEmpty
    @Length(max=20)
    private String name;

    @Column(name = "delete_flag", nullable = false)
    private Integer deleteFlag;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy="employee", cascade = CascadeType.ALL)
    private Authentication authentication;

    @PreRemove
    @Transactional
    private void preRemove() {
        if (authentication!=null) {
            authentication.setEmployee(null);
        }
    }
}
