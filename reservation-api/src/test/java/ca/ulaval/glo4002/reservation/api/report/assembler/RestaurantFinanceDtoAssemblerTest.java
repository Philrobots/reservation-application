package ca.ulaval.glo4002.reservation.api.report.assembler;

import ca.ulaval.glo4002.reservation.api.report.dto.RestaurantFinanceDto;
import ca.ulaval.glo4002.reservation.domain.RestaurantFinance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;

public class RestaurantFinanceDtoAssemblerTest {

    private static final BigDecimal AN_INCOME = BigDecimal.valueOf(1000.540);
    private static final BigDecimal A_CHEF_EXPENSE = BigDecimal.valueOf(324.120);
    private static final BigDecimal AN_INGREDIENT_EXPENSE = BigDecimal.valueOf(34.340);
    private static final BigDecimal AN_MATERIAL_EXPENSE = BigDecimal.valueOf(20.610);
    private static final int TWO = 2;

    private RestaurantFinance restaurantFinance;

    private RestaurantFinanceDtoAssembler restaurantFinanceDtoAssembler;

    @BeforeEach
    public void setUpRestaurantFinanceDtoAssembler() {
        restaurantFinance = givenARestaurantFinance();
        restaurantFinanceDtoAssembler = new RestaurantFinanceDtoAssembler();
    }

    @Test
    public void givenARestaurantFinance_whenAssembleRestaurantFinanceDto_thenIncomeThatShouldBeTheSame() {
        // when
        RestaurantFinanceDto restaurantFinanceDto = restaurantFinanceDtoAssembler.assembleRestaurantFinanceDto(restaurantFinance);

        // then
        assertThat(restaurantFinanceDto.getIncome()).isEqualTo(AN_INCOME);
    }

    @Test
    public void givenARestaurantFinance_whenAssembleRestaurantFinanceDto_thenNumberShouldHaveTwoDecimal() {
        // when
        RestaurantFinanceDto restaurantFinanceDto = restaurantFinanceDtoAssembler.assembleRestaurantFinanceDto(restaurantFinance);

        //then
        assertThat(restaurantFinanceDto.getIncome().scale()).isEqualTo(TWO);
        assertThat(restaurantFinanceDto.getExpense().scale()).isEqualTo(TWO);
        assertThat(restaurantFinanceDto.getProfits().scale()).isEqualTo(TWO);
    }

    private RestaurantFinance givenARestaurantFinance() {
        return new RestaurantFinance(AN_INCOME, A_CHEF_EXPENSE, AN_INGREDIENT_EXPENSE, AN_MATERIAL_EXPENSE);
    }

}
