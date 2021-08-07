package com.stocker.data.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * Database representation for telegram user object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private String id;
    private String name;

    private String firstName;
    private String lastName;

    private String telegramId;

    List<String> watchList = Collections.emptyList();
}
