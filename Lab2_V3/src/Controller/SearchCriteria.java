package Controller;

public enum SearchCriteria{
    GROUP_AND_SURNAME("По номеру группы или фамилии студента"),
    SURNAME_AND_TYPE_OF_SKIP("По фамилии студента или виду пропуска"),
    SURNAME_AND_AMOUNT_SKIP("По фамилии студента или количеству пропусков по видам");
    private final String value;

    SearchCriteria(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SearchCriteria getCriteriaByName(String value) {
        SearchCriteria res = null;
        for (SearchCriteria x : values()) {
            if (x.getValue().equals(value)) {
                res = x;
            }
        }
        return res;
    }
}
