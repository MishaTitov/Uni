package filesprocessing.mysection.exceptions;

public class NoOrderException extends sectionException{

    public  NoOrderException(){}

    public String getMessage(){return "ORDER sub-section is missing";}
}
