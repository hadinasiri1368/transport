package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SubsidiaryLedgerDto {
    private Long id;
    private int number;
    private String name;
    private Long generalLedgerId;
}
