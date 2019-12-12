package com.gustavo.comelon.model;

public class Commensal {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private int rol;
    private boolean selectedDraw;
    private int status;
    private int remeaningMeals;
    private boolean suscribedMeal;

    public Commensal(String name, String surname, String email, String phone, int rol, boolean selectedDraw, int status, int remeaningMeals, boolean suscribedMeal) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.rol = rol;
        this.selectedDraw = selectedDraw;
        this.status = status;
        this.remeaningMeals = remeaningMeals;
        this.suscribedMeal = suscribedMeal;
    }

    public Commensal(String name, String surname, String email, String phone, int rol, boolean selectedDraw) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.selectedDraw = selectedDraw;
    }

    public Commensal() {
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isSelected() {
        return selectedDraw;
    }

    public void setSelected(boolean selected) {
        this.selectedDraw = selected;
    }

    public int getRol() {
        return rol;
    }

    public boolean isSelectedDraw() {
        return selectedDraw;
    }

    public int getStatus() {
        return status;
    }

    public int getRemeaningMeals() {
        return remeaningMeals;
    }

    public boolean isSuscribedMeal() {
        return suscribedMeal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public void setSelectedDraw(boolean selectedDraw) {
        this.selectedDraw = selectedDraw;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRemeaningMeals(int remeaningMeals) {
        this.remeaningMeals = remeaningMeals;
    }

    public void setSuscribedMeal(boolean suscribedMeal) {
        this.suscribedMeal = suscribedMeal;
    }
}
