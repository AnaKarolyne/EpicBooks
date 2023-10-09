package br.com.fiap.epicbooks.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BooksService {

    @Autowired
    BooksRepository repository;

    public List<Books> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var books = repository.findById(id);

        if(books.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }
    
}
