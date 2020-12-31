package ca.ulaval.glo4002.reservation.api.report.assembler;

import ca.ulaval.glo4002.reservation.api.report.dto.RestaurantFinanceDto;
import ca.ulaval.glo4002.reservation.domain.RestaurantFinance;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RestaurantFinanceDtoAssembler {
    public RestaurantFinanceDto assembleRestaurantFinanceDto(RestaurantFinance restaurantFinance) {
        BigDecimal income = numberWithMaxTwoDecimal(restaurantFinance.getIncome());
        BigDecimal expense = numberWithMaxTwoDecimal(restaurantFinance.getExpense());
        BigDecimal profits = numberWithMaxTwoDecimal(restaurantFinance.getProfits());
        return new RestaurantFinanceDto(income, expense, profits);
    }

    private BigDecimal numberWithMaxTwoDecimal(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_UP);
    }
}
