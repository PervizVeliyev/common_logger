package com.example.common_logger.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum FilterPattern {

  NAME_SURNAME("[A-Z]+[ ]+[A-Z]+[ ]?+[A-Z]?", "**** *******"),
  PIN("\\b([A-Z]+\\d[A-Z\\d]+)|(\\d+[A-Z][A-Z\\S]+)\\b", "*******"),
  EMAIL("\\b([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\\b", "*@*.*"),
  AMOUNT_AND_IP("\\b[0-9]+\\.[0-9]+\\b", "*.*"),
  CARD_NUMBER("\\b\\d{16}\\b", "****************"),
  ACCOUNT_NUMBER("\\b\\d{20}\\b", "********************");

  String regexp;
  String mask;

}
