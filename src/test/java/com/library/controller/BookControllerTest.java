package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.dto.BookDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private BookDTO testBookDTO;

    @BeforeEach
    void setUp() {
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
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(testBookDTO));

        // Act & Assert
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].author").value("Test Author"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() throws Exception {
        // Arrange
        when(bookService.getBookById(1L)).thenReturn(testBookDTO);

        // Act & Assert
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookById_WhenBookDoesNotExist_ShouldReturn404() throws Exception {
        // Arrange
        when(bookService.getBookById(1L)).thenThrow(new ResourceNotFoundException("Book not found"));

        // Act & Assert
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void createBook_WithValidData_ShouldCreateBook() throws Exception {
        // Arrange
        when(bookService.createBook(any(BookDTO.class))).thenReturn(testBookDTO);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));

        verify(bookService, times(1)).createBook(any(BookDTO.class));
    }

    @Test
    void createBook_WithInvalidData_ShouldReturn400() throws Exception {
        // Arrange
        BookDTO invalidBook = new BookDTO();
        invalidBook.setTitle(""); // Invalid: empty title

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBook)))
                .andExpect(status().isBadRequest());

        verify(bookService, never()).createBook(any(BookDTO.class));
    }

    @Test
    void updateBook_WithValidData_ShouldUpdateBook() throws Exception {
        // Arrange
        when(bookService.updateBook(eq(1L), any(BookDTO.class))).thenReturn(testBookDTO);

        // Act & Assert
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).updateBook(eq(1L), any(BookDTO.class));
    }

    @Test
    void deleteBook_WhenBookExists_ShouldReturn204() throws Exception {
        // Arrange
        doNothing().when(bookService).deleteBook(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void updateBookAvailability_ShouldUpdateAvailability() throws Exception {
        // Arrange
        testBookDTO.setAvailable(false);
        when(bookService.updateBookAvailability(1L, false)).thenReturn(testBookDTO);

        Map<String, Boolean> availabilityMap = new HashMap<>();
        availabilityMap.put("available", false);

        // Act & Assert
        mockMvc.perform(patch("/api/books/1/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(availabilityMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));

        verify(bookService, times(1)).updateBookAvailability(1L, false);
    }

    @Test
    void getBooksByAuthor_ShouldReturnMatchingBooks() throws Exception {
        // Arrange
        when(bookService.getBooksByAuthor("Test")).thenReturn(Arrays.asList(testBookDTO));

        // Act & Assert
        mockMvc.perform(get("/api/books/search/author")
                        .param("author", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Test Author"));

        verify(bookService, times(1)).getBooksByAuthor("Test");
    }

    @Test
    void getAvailableBooks_ShouldReturnOnlyAvailableBooks() throws Exception {
        // Arrange
        when(bookService.getAvailableBooks()).thenReturn(Arrays.asList(testBookDTO));

        // Act & Assert
        mockMvc.perform(get("/api/books/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].available").value(true));

        verify(bookService, times(1)).getAvailableBooks();
    }
}
