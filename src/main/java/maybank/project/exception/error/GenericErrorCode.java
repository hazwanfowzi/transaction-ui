package maybank.project.exception.error;

public enum GenericErrorCode implements IErrorCode {

    GENERIC_ERROR(101),
    DATABASE_ERROR(102),
	SESSION_EXPIRED(103),
	ILLEGAL_PARAMETER(104);

    private final int number;

    private GenericErrorCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}
