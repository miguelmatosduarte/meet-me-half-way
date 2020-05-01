package meetmehalfway.utils;

public enum ErrorType {
    NO_QUOTES("E001", "No quotes were obtained from SkyScanner for these dates for at least one of the passengers."),
    NO_COMMON_DESTINATION("E002", "No common destination was found for the passengers."),
    SKYSCANNER_VALIDATION_ERROR("E003", "Skyscanner API validation error.")
    ;

    private final String code;
    private final String description;

    ErrorType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
