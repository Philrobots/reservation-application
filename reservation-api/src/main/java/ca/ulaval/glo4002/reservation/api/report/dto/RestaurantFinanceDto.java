package ca.ulaval.glo4002.reservation.api.report.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonPropertyOrder({ "expense", "income", "profits" })
public final class RestaurantFinanceDto {

    private final BigDecimal expense;
    private final BigDecimal income;
    private final BigDecimal profits;

    public RestaurantFinanceDto(BigDecimal income, BigDecimal expense, BigDecimal profits) {
        this.expense = expense;
        this.income = income;
        this.profits = profits;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public BigDecimal getProfits() {
        return profits;
    }
}
