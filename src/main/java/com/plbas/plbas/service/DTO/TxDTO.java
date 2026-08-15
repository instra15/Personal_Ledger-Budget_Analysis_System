package com.plbas.plbas.service.DTO;

import com.plbas.plbas.enums.EntryDirection;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxDTO {

    @NotBlank
    private String txNo;

    @NotNull
    private Date date;

    private String remark;

    @NotEmpty
    private List<EntryItem> entries;

    @Data
    public static class EntryItem
    {
        @NotNull
        private Long account_id;

        @NotNull
        private Long category_id;

        @NotNull
        @DecimalMin("0.00")
        private BigDecimal amount;

        @NotNull
        private EntryDirection direction;
    }


}
