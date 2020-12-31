package ca.ulaval.glo4002.reservation.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.google.common.truth.Truth.assertThat;

public class RestaurantFinanceTest {

    private static final BigDecimal A_INCOME = BigDecimal.valueOf(1000);
    private static final BigDecimal A_CHEF_COST = BigDecimal.valueOf(600);
    private static final BigDecimal A_INGREDIENT_COST = BigDecimal.valueOf(200);
    private static final BigDecimal MATERIAL_COST = BigDecimal.valueOf(100);

    private RestaurantFinance restaurantFinance;

    @BeforeEach
    public void  setUpRestaurantFinance() {
        restaurantFinance = new RestaurantFinance(A_INCOME, A_CHEF_COST, A_INGREDIENT_COST, MATERIAL_COST);
    }

    @Test
    public void givenAProfitAndAnIncome_whenGetProfit_thenProfitShouldBeIncomeLessProfits() {
        // when
        BigDecimal actualProfits = restaurantFinance.getProfits();

        // then
        BigDecimal expectedProfit = BigDecimal.valueOf(100);
        assertThat(actualProfits).isEqualTo(expectedProfit);
    }

    @Test
    public void givenAChefCostAndIngredientCostAndAMaterialCost_whenGetExpense_thenExpenseShouldBeTheRightAmount() {
        // when
        BigDecimal actualExpense = restaurantFinance.getExpense();

        // then
        BigDecimal expectedExpense = BigDecimal.valueOf(900);
        assertThat(actualExpense).isEqualTo(expectedExpense);
    }
}
