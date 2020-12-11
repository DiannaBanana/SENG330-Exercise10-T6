package models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SearchStore {
    List<Observation> getSearchResult();
    void addSearchToStore(Observation toAdd);
    void clearSearch();


    interface IdGenerator{
        long generateId();
    }
}
