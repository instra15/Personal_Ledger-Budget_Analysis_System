package com.plbas.plbas.service.DTO;

import com.plbas.plbas.enums.EntryDirection;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull
    private Date date;

    private String remark;

    private List<EntryItem> entries;

    @Data
    public static class EntryItem
    {
        @NotNull
        private Long account_id;

        @NotNull
        private Long category_id;

        @NotNull
        @Positive
        private BigDecimal amount;

        @NotNull
        private EntryDirection direction;
    }


}
