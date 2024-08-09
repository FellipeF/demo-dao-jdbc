package db;
//Checks if data to be deleted isn't referenced anywhere else on the DB.
public class DbIntegrityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DbIntegrityException(String msg) {
        super(msg);
    }
}
