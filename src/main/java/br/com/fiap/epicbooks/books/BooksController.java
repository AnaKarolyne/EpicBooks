package br.com.fiap.epicbooks.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    BooksService service;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        return "books/index";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", messageSource.getMessage("book.delete.success", null, LocaleContextHolder.getLocale()));
        }else{
            redirect.addFlashAttribute("error", "Livro não encontrada");
        }
        return "redirect:/books";
    }

    @GetMapping("new")
    public String form(Books books, Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        return "books/form";
    }

    @PostMapping
    public String create(@Valid Books books, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) return "books/form";
        
        service.save(books);
        redirect.addFlashAttribute("success", "Livro cadastrada com sucesso");
        return "redirect:/books";
    }

    @GetMapping("/dec/{id}")
    public String decrement(@PathVariable Long id){
        service.decrement(id);
        return "redirect:/books";
    }

    @GetMapping("/inc/{id}")
    public String increment(@PathVariable Long id){
        service.increment(id);
        return "redirect:/books";
    }

    @GetMapping("/catch/{id}")
    public String cacthTask(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.cacthTask(id, user);
        return "redirect:/books";
    }

    @GetMapping("/drop/{id}")
    public String dropTask(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.dropTask(id, user);
        return "redirect:/books";
    }

    
}
