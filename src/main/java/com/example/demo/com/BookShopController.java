
package com.example.demo.com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Book;
import com.example.demo.BookRepository;

@Controller
public class BookShopController {
    private final BookRepository bookRepository;

    @Autowired
    public BookShopController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        initializeBooks();
    }

    private void initializeBooks() {
        if (bookRepository.count() == 0) {
            List<Book> initialBooks = List.of(
                    new Book("Bille", 1992, "Vizma Belševica", 18.99f, "Proza", true, "https://imgur.com/jsIjazJ.png",
                            "Triloģija, kas ataino jaunas meitenes Billes dzīvi 20. gadsimta 30. gados Latvijā. Stāsts ir par Billes bērnību, viņas iekšējo pasauli un apkārtējās sabiedrības ietekmi.",
                            "https://imgur.com/MwPVqUT.jpg"),
                    new Book("Es esmu šeit", 2016, "Māra Zālīte", 15.50f, "Romāns", true, "https://imgur.com/v38MkoZ.png",
                            "Romāns, kas risina ģimenes attiecību un vēsturisko notikumu ietekmi uz cilvēka likteni. Stāsts ir par sievieti, kura meklē savu identitāti un vietu pasaulē.",
                            "-"),
                    new Book("Zemes sāls", 2014, "Nora Ikstena", 22.00f, "Biogrāfija", true, "https://imgur.com/lBjCFw4.png",
                            "Biogrāfisks romāns par rakstnieces Māras Svīres dzīvi. Grāmata ataino sarežģītu sievietes likteni, kurā savijas māksla, mīlestība un traģēdija.",
                            "-"),
                    new Book("Klūgu mūks", 2018, "Jānis Joņevs", 17.75f, "Romāns", true, "https://imgur.com/I32FF5B.png",
                            "Romāns par jauna cilvēka meklējumiem un pašizziņu mūsdienu Latvijā. Stāsts ir par Rīgas naktsdzīvi, jauniešu subkultūrām un personīgo krīzi.",
                            "-"),
                    new Book("Sunīši", 2000, "Inga Ābele", 16.20f, "Drāma", true, "https://imgur.com/vAqsj8Y.png",
                            "Drāma, kas risina ģimenes attiecību un emocionālo traumu tēmas. Stāsts ir par ģimeni, kuras dzīvi ietekmē pagātnes notikumi un neatrisināti konflikti.",
                            "-"),
                    new Book("Melnā vēža stāsti", 2019, "Osvalds Zebris", 20.50f, "Romāns", true, "https://imgur.com/AA54re9.png",
                            "Romāns, kurā savijas reālistiski un sirreāli elementi. Grāmata ataino mūsdienu sabiedrības problēmas un cilvēka iekšējo pasauli.",
                            "-"),
                    new Book("Svina garša", 2017, "Gundars Repše", 19.80f, "Detektīvs", true, "https://imgur.com/A5RJ0YU.png",
                            "Detektīvromāns ar psiholoģiska trillera elementiem. Stāsts ir par izmeklētāju, kurš risina sarežģītu noziegumu, atklājot tumšus noslēpumus.",
                            "-"),
                    new Book("Tarakāni manā galvā", 2020, "Zane Zusta", 14.99f, "Romāns", true, "https://imgur.com/Vzm2Fcv.png",
                            "Romāns par jaunas sievietes dzīves peripetijām un iekšējiem konfliktiem. Stāsts ir par pašizziņu, mīlestību un izaicinājumiem mūsdienu sabiedrībā.",
                            "-"),
                    new Book("Debesis pelnos", 2021, "Karīna Račko", 16.50f, "Romāns", true, "https://imgur.com/l18Egi2.png",
                            "Romāns, kas risina ģimenes noslēpumu un pagātnes ietekmes tēmas. Stāsts ir par sievieti, kura atklāj ģimenes pagātnes tumšos noslēpumus.",
                            "-"),
                    new Book("Saplēstās mežģīnes", 2022, "Karīna Račko", 17.00f, "Romāns", true, "https://imgur.com/l18Egi2.png",
                            "Romāns par sievietes likteni un izvēlēm sarežģītos apstākļos. Stāsts ir par mīlestību, nodevību un izdzīvošanu.",
                            "-")
            );
            bookRepository.saveAll(initialBooks);
        }
    }

    @GetMapping("/bookShop")
    public String startPage(Model model) {
        return "start";
    }

    @GetMapping("/bookShop/books")
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/bookShop/books/description/{id}")
    public String showBooksDescription(@PathVariable int id, Model model) {
        Book currentBook = bookRepository.findById(id).orElse(null);

        if (currentBook != null) {
            List<Book> allBooks = bookRepository.findAll();
            int currentIndex = allBooks.indexOf(currentBook);
            int totalBooks = allBooks.size();
            Integer previousBookId = null;
            Integer nextBookId = null;

            if (currentIndex > 0) {
                previousBookId = allBooks.get(currentIndex - 1).getId();
            }

            if (currentIndex < totalBooks - 1) {
                nextBookId = allBooks.get(currentIndex + 1).getId();
            }

            model.addAttribute("book", currentBook);
            model.addAttribute("previousBookId", previousBookId);
            model.addAttribute("nextBookId", nextBookId);
            model.addAttribute("totalBooks", totalBooks);
            return "moreInfo";
        } else {
            return "error";
        }
    }

    @GetMapping("/bookShop/books/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "add";
    }

    @PostMapping("/bookShop/books")
    public String addBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/bookShop/books";
    }

    @GetMapping("/bookShop/books/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            model.addAttribute("book", book);
            return "edit";
        } else {
            return "error";
        }
    }

    @PostMapping("/bookShop/books/edit/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute Book updatedBook) {
        bookRepository.findById(id).ifPresent(book -> {
        	
            updatedBook.setId(id);
            bookRepository.save(updatedBook);
        });
        return "redirect:/bookShop/books";
    }

    @GetMapping("/bookShop/books/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookRepository.deleteById(id);
        return "redirect:/bookShop/books";
    }
}