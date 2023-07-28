package ru.sber.kapustin.filmlib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "films", schema = "public", catalog = "filmlib")
public class FilmsEntity extends GenericModel {
    @Basic
    @Column(name = "title", nullable = false, length = 60)
    private String title;

    @Basic
    @Column(name = "premier_year", nullable = false)
    private int premierYear;

    @Basic
    @Column(name = "country", nullable = false, length = 20)
    private String country;

    @Basic
    @Column(name = "genre", length = 20)
    private String genre;

    @Basic
    @Column(name = "amount", nullable = false)
    private Integer amount;


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "films_directors",
            joinColumns = @JoinColumn(name = "film_id"), foreignKey = @ForeignKey(name = "FK_FILMS_DIRECTORS"),
            inverseJoinColumns = @JoinColumn(name = "director_id"), inverseForeignKey = @ForeignKey(name = "FK_DIRECTORS_FILMS"))
    private List<DirectorsEntity> directors;
}
