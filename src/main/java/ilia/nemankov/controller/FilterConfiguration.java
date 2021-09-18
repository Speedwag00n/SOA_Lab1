package ilia.nemankov.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterConfiguration {
    private Integer count;
    private Integer page;
    private String[] order;
    private String[] filter;
}
