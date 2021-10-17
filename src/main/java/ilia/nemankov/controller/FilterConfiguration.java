package ilia.nemankov.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterConfiguration {
    private Integer count;
    private Integer page;
    private List<String> order;
    private List<String> filter;
}
