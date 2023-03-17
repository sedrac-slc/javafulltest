package gest.enums;

public enum DBCrendecial {

    URL("jdbc:mysql://localhost:3306/java"),
    USER("root"),
    PASSWORD("");

    private final String code;

    private DBCrendecial(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

}
