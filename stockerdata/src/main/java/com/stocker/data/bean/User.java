package com.stocker.data.bean;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

/**
 * Database representation for telegram user object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString
public class User {

    @Id
    private String id;
    private String name;

    private String firstName;
    private String lastName;

    private String telegramId;

    List<String> watchList = Collections.emptyList();
}
