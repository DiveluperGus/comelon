package com.gustavo.comelon.model;

public class Meal {

    private String nameMeal;
    private String nameStew;
    private String description;
    private int numNecessaryPersons;
    private int numRealPersons;
    private float mealCost;
    private String deadline;
    private String timeLimit;
    private String status;

    public Meal(String nameMeal, String nameStew, String description, int numNecessaryPersons, int numRealPersons, float mealCost, String deadline, String timeLimit, String status) {
        this.nameMeal = nameMeal;
        this.nameStew = nameStew;
        this.description = description;
        this.numNecessaryPersons = numNecessaryPersons;
        this.numRealPersons = numRealPersons;
        this.mealCost = mealCost;
        this.deadline = deadline;
        this.timeLimit = timeLimit;
        this.status = status;
    }

    public String getNameMeal() {
        return nameMeal;
    }

    public String getNameStew() {
        return nameStew;
    }

    public String getDescription() {
        return description;
    }

    public int getNumNecessaryPersons() {
        return numNecessaryPersons;
    }

    public float getMealCost() {
        return mealCost;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public String getStatus() {
        return status;
    }
}
