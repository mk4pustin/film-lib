package ru.sber.kapustin.filmlib.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users", schema = "public", catalog = "filmlib")
public class UsersEntity extends GenericModel {
    @Basic
    @Column(name = "login", nullable = false, length = 100)
    private String login;

    @Basic
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Basic
    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Basic
    @Column(name = "middle_name", length = 60)
    private String middleName;

    @Basic
    @Column(name = "birth_date")
    private Date birthDate;

    @Basic
    @Column(name = "phone", length = 20)
    private String phone;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "email")
    private String email;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_USERS_ROLES"))
    private RoleEntity role;

    @Column(name = "change_password_token")
    private String changePasswordToken;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrdersEntity> orders;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
}
