package com.stocker.yahoo.data.callback;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddToWatchListCallback extends AbstractCallback {

    private String symbol;
    private String telegramId;

}
