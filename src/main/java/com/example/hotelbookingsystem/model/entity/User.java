package com.example.hotelbookingsystem.model.entity;

import com.example.hotelbookingsystem.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String userName;
    private String password;
    private String userNationality;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedInfoAt;

    public User(String userName, String password, String userNationality, String phoneNumber, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.userNationality = userNationality;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }
}
