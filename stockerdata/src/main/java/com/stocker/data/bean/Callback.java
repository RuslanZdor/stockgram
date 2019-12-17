package com.stocker.data.bean;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Database representation for telegram callback object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString
public class Callback {

    @Id
    private String id;
    private String telegramId;
    private String symbol;
}
