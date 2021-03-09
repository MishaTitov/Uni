package filesprocessing.mysection.exceptions;

public class NoFilterException extends sectionException {

    public NoFilterException(){}

    public String getMessage(){return "FILTER sub-section is missing";}
}