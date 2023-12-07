package commons.result;

public class InvalidMoveResult implements MoveResult{
    String errorMessage;
    public InvalidMoveResult(String message){
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
