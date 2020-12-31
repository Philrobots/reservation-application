package ca.ulaval.glo4002.reservation.domain;

import java.math.BigDecimal;

public final class RestaurantFinance {
    private final BigDecimal income;
    private final BigDecimal expense;
    private final BigDecimal profits;

    public RestaurantFinance(BigDecimal restaurantIncome, BigDecimal totalChefFees, BigDecimal ingredientCost, BigDecimal materialCost) {
        this.income = restaurantIncome;
        this.expense = calculateExpense(totalChefFees, ingredientCost, materialCost);
        this.profits = calculateProfits();
    }

    private BigDecimal calculateExpense(BigDecimal totalChefFees, BigDecimal ingredientCost, BigDecimal materialCost) {
        return totalChefFees.add(ingredientCost).add(materialCost);
    }

    private BigDecimal calculateProfits() {
        return income.subtract(expense);
    }

    public BigDecimal getIncome() { return income; }

    public BigDecimal getExpense() {
        return expense;
    }

    public BigDecimal getProfits() {
        return profits;
    }
}
