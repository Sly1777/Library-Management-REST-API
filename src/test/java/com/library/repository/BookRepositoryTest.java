package com.library.repository;

import com.library.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("1234567890");
        testBook.setPublicationDate(LocalDate.of(2023, 1, 1));
        testBook.setGenre("Fiction");
        testBook.setAvailable(true);
        testBook.setDescription("Test Description");
    }

    @Test
    void findByIsbn_WhenBookExists_ShouldReturnBook() {
        // Arrange
        entityManager.persist(testBook);
        entityManager.flush();

        // Act
        Optional<Book> found = bookRepository.findByIsbn("1234567890");

        // Assert
        assertTrue(found.isPresent());
        assertEquals(testBook.getTitle(), found.get().getTitle());
    }

    @Test
    void findByIsbn_WhenBookDoesNotExist_ShouldReturnEmpty() {
        // Act
        Optional<Book> found = bookRepository.findByIsbn("9999999999");

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    void findByAuthorContainingIgnoreCase_ShouldReturnMatchingBooks() {
        // Arrange
        entityManager.persist(testBook);
        entityManager.flush();

        // Act
        List<Book> found = bookRepository.findByAuthorContainingIgnoreCase("test");

        // Assert
        assertEquals(1, found.size());
        assertEquals(testBook.getAuthor(), found.get(0).getAuthor());
    }

    @Test
    void findByTitleContainingIgnoreCase_ShouldReturnMatchingBooks() {
        // Arrange
        entityManager.persist(testBook);
        entityManager.flush();

        // Act
        List<Book> found = bookRepository.findByTitleContainingIgnoreCase("book");

        // Assert
        assertEquals(1, found.size());
        assertEquals(testBook.getTitle(), found.get(0).getTitle());
    }

    @Test
    void findByGenreIgnoreCase_ShouldReturnMatchingBooks() {
        // Arrange
        entityManager.persist(testBook);
        entityManager.flush();

        // Act
        List<Book> found = bookRepository.findByGenreIgnoreCase("fiction");

        // Assert
        assertEquals(1, found.size());
        assertEquals(testBook.getGenre(), found.get(0).getGenre());
    }

    @Test
    void findByAvailable_ShouldReturnOnlyAvailableBooks() {
        // Arrange
        Book unavailableBook = new Book();
        unavailableBook.setTitle("Unavailable Book");
        unavailableBook.setAuthor("Another Author");
        unavailableBook.setIsbn("0987654321");
        unavailableBook.setPublicationDate(LocalDate.of(2023, 1, 1));
        unavailableBook.setGenre("Non-Fiction");
        unavailableBook.setAvailable(false);

        entityManager.persist(testBook);
        entityManager.persist(unavailableBook);
        entityManager.flush();

        // Act
        List<Book> availableBooks = bookRepository.findByAvailable(true);
        List<Book> unavailableBooks = bookRepository.findByAvailable(false);

        // Assert
        assertEquals(1, availableBooks.size());
        assertTrue(availableBooks.get(0).getAvailable());
        assertEquals(1, unavailableBooks.size());
        assertFalse(unavailableBooks.get(0).getAvailable());
    }

    @Test
    void existsByIsbn_WhenBookExists_ShouldReturnTrue() {
        // Arrange
        entityManager.persist(testBook);
        entityManager.flush();

        // Act
        boolean exists = bookRepository.existsByIsbn("1234567890");

        // Assert
        assertTrue(exists);
    }

    @Test
    void existsByIsbn_WhenBookDoesNotExist_ShouldReturnFalse() {
        // Act
        boolean exists = bookRepository.existsByIsbn("9999999999");

        // Assert
        assertFalse(exists);
    }
}
