package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.reservation.builder.CustomerDtoBuilder;
import ca.ulaval.glo4002.reservation.api.reservation.builder.TableDtoBuilder;
import ca.ulaval.glo4002.reservation.api.reservation.dto.CustomerApiDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.TableApiDto;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;

@ExtendWith(MockitoExtension.class)
class TableAssemblerTest {

  @Mock
  private CustomerAssembler customerAssembler;

  private TableAssembler tableAssembler;

  @BeforeEach
  public void setUp() {
    tableAssembler = new TableAssembler(customerAssembler);
  }

  @Test
  public void whenAssembleFromTableDto_thenTableIsCreated() {
    // given
    CustomerApiDto customerApiDto = new CustomerDtoBuilder().build();
    TableApiDto tableApiDto = new TableDtoBuilder().withCustomer(customerApiDto).build();

    // when
    Table table = tableAssembler.assembleFromTableDto(tableApiDto);

    // then
    assertThat(table.getCustomers()).hasSize(1);
  }
}
