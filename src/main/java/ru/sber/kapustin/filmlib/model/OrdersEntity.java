package ru.sber.kapustin.filmlib.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders", schema = "public", catalog = "filmlib")
public class OrdersEntity extends GenericModel {
    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "FK_ORDER_USER"))
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "film_id",
            foreignKey = @ForeignKey(name = "FK_ORDER_FILM"))
    private FilmsEntity film;

    @Basic
    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Basic
    @Column(name = "rent_period", nullable = false)
    private int rentPeriod;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "returned")
    private Boolean returned;
}
