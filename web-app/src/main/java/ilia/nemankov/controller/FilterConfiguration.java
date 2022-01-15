package ilia.nemankov.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class FilterConfiguration implements Serializable {
    private Integer count;
    private Integer page;
    private List<String> order;
    private List<String> filter;
}
