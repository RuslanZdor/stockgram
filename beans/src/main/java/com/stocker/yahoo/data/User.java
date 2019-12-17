package com.stocker.yahoo.data;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;

    private String firstName;
    private String lastName;

    private String telegramId;

    List<String> watchList = Collections.emptyList();
}
