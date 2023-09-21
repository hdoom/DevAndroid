package com.example.devandroid;

public class Restaurant {
    private int id;
    private String nom;
    private String dateHeureRepas;
    private float noteDecoration;
    private float noteNourriture;
    private float noteService;
    private String descriptionCritique;

    public Restaurant(String nom, String dateHeure, float noteDecoration, float noteNourriture, float noteService, String description) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateHeureRepas() {
        return dateHeureRepas;
    }

    public void setDateHeureRepas(String dateHeureRepas) {
        this.dateHeureRepas = dateHeureRepas;
    }

    public float getNoteDecoration() {
        return noteDecoration;
    }

    public void setNoteDecoration(float noteDecoration) {
        this.noteDecoration = noteDecoration;
    }

    public float getNoteNourriture() {
        return noteNourriture;
    }

    public void setNoteNourriture(float noteNourriture) {
        this.noteNourriture = noteNourriture;
    }

    public float getNoteService() {
        return noteService;
    }

    public void setNoteService(float noteService) {
        this.noteService = noteService;
    }

    public String getDescriptionCritique() {
            String descriptionBrute = descriptionCritique;
            return descriptionCritique;
    }

    public void setDescriptionCritique(String descriptionCritique) {
        this.descriptionCritique = descriptionCritique;
    }

    @Override
    public String toString() {
        return nom + " - Date: " + dateHeureRepas;
    }
}
