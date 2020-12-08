package models;

import java.security.InvalidParameterException;
import java.util.List;

/*
    Creating an interface just in case we need to add more methods
    Remove if it proves to be unnecessary
 */
public interface WhaleStore{
    List<Whale> getAllWhales();
    void addWhale(Whale w, long obsId) throws InvalidParameterException;
}