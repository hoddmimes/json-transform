package test;


import generated.DateTimeMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateDateTimeMessage
{

    public static DateTimeMessage createMessage() {
        DateTimeMessage msg = new DateTimeMessage();
        msg.setDate( LocalDate.now());
        msg.setDateTime(LocalDateTime.now());
        msg.setValue(42);
        return msg;
    }
}
