package com.library.config;

import com.library.model.Book;
import com.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        Book book1 = new Book();
        book1.setTitle("Clean Code");
        book1.setAuthor("Robert C. Martin");
        book1.setIsbn("9780132350884");
        book1.setPublicationDate(LocalDate.of(2008, 8, 1));
        book1.setGenre("Programming");
        book1.setAvailable(true);
        book1.setDescription("A Handbook of Agile Software Craftsmanship");

        Book book2 = new Book();
        book2.setTitle("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setIsbn("9780134685991");
        book2.setPublicationDate(LocalDate.of(2017, 12, 27));
        book2.setGenre("Programming");
        book2.setAvailable(true);
        book2.setDescription("Best practices for the Java platform");

        Book book3 = new Book();
        book3.setTitle("Design Patterns");
        book3.setAuthor("Erich Gamma");
        book3.setIsbn("9780201633610");
        book3.setPublicationDate(LocalDate.of(1994, 10, 31));
        book3.setGenre("Software Engineering");
        book3.setAvailable(false);
        book3.setDescription("Elements of Reusable Object-Oriented Software");

        Book book4 = new Book();
        book4.setTitle("Spring in Action");
        book4.setAuthor("Craig Walls");
        book4.setIsbn("9781617294945");
        book4.setPublicationDate(LocalDate.of(2018, 10, 1));
        book4.setGenre("Programming");
        book4.setAvailable(true);
        book4.setDescription("Covers Spring 5");

        Book book5 = new Book();
        book5.setTitle("The Pragmatic Programmer");
        book5.setAuthor("Andrew Hunt");
        book5.setIsbn("9780135957059");
        book5.setPublicationDate(LocalDate.of(2019, 9, 13));
        book5.setGenre("Software Engineering");
        book5.setAvailable(true);
        book5.setDescription("Your Journey to Mastery");

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);

        System.out.println("Sample data initialized: " + bookRepository.count() + " books added.");
    }
}
