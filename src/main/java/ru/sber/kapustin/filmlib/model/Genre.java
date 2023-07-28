package ru.sber.kapustin.filmlib.model;

public enum Genre {
    DRAMA("Драма"),
    COMEDY("Комедия"),
    THRILLER("Триллер"),
    HORROR("Ужасы");

    private final String genreText;

    Genre(String text) {
        this.genreText = text;
    }

    public String getGenreText() {
        return this.genreText;
    }
}
