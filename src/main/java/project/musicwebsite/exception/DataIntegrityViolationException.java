package project.musicwebsite.exception;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(String message) {super(message);}

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
