package com.plbas.plbas.service.DTO;

import com.plbas.plbas.enums.EntryDirection;
import jakarta.validation.Valid;
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
    private Date date;

    private String remark;

    private List<EntryItem> entries;

    public static class EntryItem
    {
        private Long account_id;

        private Long category_id;

        private BigDecimal amount;

        private EntryDirection direction;
    }


}
