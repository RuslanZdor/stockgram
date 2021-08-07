package com.stocker.data.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Database representation for telegram callback object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Callback {

    private String id;
    private String telegramId;
    private String symbol;
}
