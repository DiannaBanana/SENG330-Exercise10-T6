package models;

import java.util.List;

/*
    Creating an interface just in case we need to add more methods
 */
public interface WhaleStore{
    List<Whale> getAllWhales();
}