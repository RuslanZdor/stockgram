package com.stocker.telegram.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.SortedSet;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Company {

    private String id;
    private String name;
    private String symbol;
    private String industry;

    private SortedSet<Day> days = new TreeSet<>();

}
