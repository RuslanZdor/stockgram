package com.stocker.telegram.spring.callback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToWatchListCallback extends AbstractCallback {

    private String symbol;
    private String telegramId;

}
