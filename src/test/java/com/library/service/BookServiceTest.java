package com.library.service;

import com.library.dto.BookDTO;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private BookDTO testBookDTO;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("1234567890");
        testBook.setPublicationDate(LocalDate.of(2023, 1, 1));
        testBook.setGenre("Fiction");
        testBook.setAvailable(true);
        testBook.setDescription("Test Description");

        testBookDTO = new BookDTO();
        testBookDTO.setId(1L);
        testBookDTO.setTitle("Test Book");
        testBookDTO.setAuthor("Test Author");
        testBookDTO.setIsbn("1234567890");
        testBookDTO.setPublicationDate(LocalDate.of(2023, 1, 1));
        testBookDTO.setGenre("Fiction");
        testBookDTO.setAvailable(true);
        testBookDTO.setDescription("Test Description");
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(Arrays.asList(testBook));

        // Act
        List<BookDTO> result = bookService.getAllBooks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testBook.getTitle(), result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // Act
        BookDTO result = bookService.getBookById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testBook.getTitle(), result.getTitle());
        assertEquals(testBook.getAuthor(), result.getAuthor());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_WhenBookDoesNotExist_ShouldThrowException() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookByIsbn_WhenBookExists_ShouldReturnBook() {
        // Arrange
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(testBook));

        // Act
        BookDTO result = bookService.getBookByIsbn("1234567890");

        // Assert
        assertNotNull(result);
        assertEquals(testBook.getIsbn(), result.getIsbn());
        verify(bookRepository, times(1)).findByIsbn("1234567890");
    }

    @Test
    void createBook_WhenIsbnDoesNotExist_ShouldCreateBook() {
        // Arrange
        when(bookRepository.existsByIsbn(testBookDTO.getIsbn())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // Act
        BookDTO result = bookService.createBook(testBookDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testBookDTO.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).existsByIsbn(testBookDTO.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_WhenIsbnExists_ShouldThrowException() {
        // Arrange
        when(bookRepository.existsByIsbn(testBookDTO.getIsbn())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> bookService.createBook(testBookDTO));
        verify(bookRepository, times(1)).existsByIsbn(testBookDTO.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void updateBook_WhenBookExists_ShouldUpdateBook() {
        // Arrange
        BookDTO updatedDTO = new BookDTO();
        updatedDTO.setTitle("Updated Title");
        updatedDTO.setAuthor("Updated Author");
        updatedDTO.setIsbn("1234567890");
        updatedDTO.setPublicationDate(LocalDate.of(2023, 1, 1));
        updatedDTO.setGenre("Non-Fiction");
        updatedDTO.setAvailable(false);
        updatedDTO.setDescription("Updated Description");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // Act
        BookDTO result = bookService.updateBook(1L, updatedDTO);

        // Assert
        assertNotNull(result);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_WhenBookDoesNotExist_ShouldThrowException() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, testBookDTO));
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_WhenBookExists_ShouldDeleteBook() {
        // Arrange
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_WhenBookDoesNotExist_ShouldThrowException() {
        // Arrange
        when(bookRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).deleteById(1L);
    }

    @Test
    void updateBookAvailability_ShouldUpdateAvailability() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // Act
        BookDTO result = bookService.updateBookAvailability(1L, false);

        // Assert
        assertNotNull(result);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void getBooksByAuthor_ShouldReturnMatchingBooks() {
        // Arrange
        when(bookRepository.findByAuthorContainingIgnoreCase("Test")).thenReturn(Arrays.asList(testBook));

        // Act
        List<BookDTO> result = bookService.getBooksByAuthor("Test");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("Test");
    }

    @Test
    void getAvailableBooks_ShouldReturnOnlyAvailableBooks() {
        // Arrange
        when(bookRepository.findByAvailable(true)).thenReturn(Arrays.asList(testBook));

        // Act
        List<BookDTO> result = bookService.getAvailableBooks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getAvailable());
        verify(bookRepository, times(1)).findByAvailable(true);
    }
}
