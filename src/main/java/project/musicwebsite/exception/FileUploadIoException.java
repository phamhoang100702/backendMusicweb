package project.musicwebsite.exception;

public class FileUploadIoException extends RuntimeException{
    public FileUploadIoException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
