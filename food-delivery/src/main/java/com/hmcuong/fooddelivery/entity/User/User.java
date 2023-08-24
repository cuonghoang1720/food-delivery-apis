package com.hmcuong.fooddelivery.entity.User;

import com.hmcuong.fooddelivery.entity.Token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "verificationcode")
    private String verificationCode;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public boolean isEnable(){
        return this.enable;
    }
}
