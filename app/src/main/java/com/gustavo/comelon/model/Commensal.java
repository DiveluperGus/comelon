package com.gustavo.comelon.model;

public class Commensal {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private boolean selected;
    private String rol;

    public Commensal(String name, String surname, String email, String phone, boolean selected) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.selected = selected;
    }

    public Commensal(String name, String surname, String email, String phone, boolean selected, String rol) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.selected = selected;
        this.rol = rol;
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
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getRol() {
        return rol;
    }
}
