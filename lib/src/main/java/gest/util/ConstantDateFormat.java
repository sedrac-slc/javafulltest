package gest.util;

import java.time.format.DateTimeFormatter;

public final class ConstantDateFormat {
        public static final DateTimeFormatter FORMAT_LOCALCURRENT_LOCAL = DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss[.S][.SS]");

        public static final DateTimeFormatter FORMAT_LOCALCURRENT_MYSQL = DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss");

        public static final DateTimeFormatter FORMAT_PARSE_OBJECT = DateTimeFormatter
                        .ofPattern("yyyy-MM-dd'T'HH:mm:ss");
}
