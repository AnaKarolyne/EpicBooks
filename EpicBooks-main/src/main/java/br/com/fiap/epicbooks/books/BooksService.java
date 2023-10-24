package br.com.fiap.epicbooks.books;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.epicbooks.user.User;
import br.com.fiap.epicbooks.user.UserService;

@Service
public class BooksService {

    @Autowired
    BooksRepository repository;

    @Autowired
    UserService userService;

    public List<Books> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var books = repository.findById(id);

        if(books.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Books books) {
        repository.save(books);
    }

    public void decrement(Long id) {
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Livro não encontrada");

        var books = optional.get();

        if (books.getStatus() == null || books.getStatus() <= 0)
            throw new RuntimeException("Livro não pode ter status negativo");

        books.setStatus(books.getStatus() - 10);

        // salvar
        repository.save(books);
    }

    public void increment(Long id) {
        // buscar a tarefa no bd
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Livro não encontrada");

        var books = optional.get();

        if (books.getStatus() == null)
            books.setStatus(0);

        if (books.getStatus() == 100) {
            throw new RuntimeException("Livro não pode ter status maior que 100%");
        }

        books.setStatus(books.getStatus() + 10);

        if (books.getStatus() == 100){
            var user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.addScore(User.convert(user) , books.getScore());
        }

        // salvar
        repository.save(books);
    }

    public void cacthBooks(Long id, OAuth2User user) {
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Livro não encontrada");

        var books = optional.get();

        if (books.getUser() != null)
            throw new RuntimeException("Livro já atribuída");

        books.setUser(User.convert(user));

        repository.save(books);

    }

    public void dropBooks(Long id, OAuth2User user) {
        var optional = repository.findById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Livro não encontrada");

        var books = optional.get();

        if (books.getUser() == null)
            throw new RuntimeException("Livro não atribuída");
            
        if (!books.getUser().equals(User.convert(user)))
            throw new RuntimeException("não pode largar o livro de outra pessoa");

        books.setUser(null);

        repository.save(books);
    }

}
