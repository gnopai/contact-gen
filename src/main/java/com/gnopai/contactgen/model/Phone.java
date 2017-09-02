package com.gnopai.contactgen.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Phone {
    String areaCode;
    String exchange;
    String line;
}
