package com.postech.fiap.fase1.webapi.utils;

import com.postech.fiap.fase1.core.dto.product.FileDTO;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputOutputFilesUtilsTest {

    private InputOutputFilesUtils utils;

    @BeforeEach
    void setUp() {
        utils = new InputOutputFilesUtils();
    }

    @Test
    void getFileDTO_shouldReturnFileDTO_whenFileIsValid() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("arquivo.txt");
        when(file.getBytes()).thenReturn("conteudo".getBytes());

        FileDTO dto = utils.getFileDTO(file);
        assertEquals("arquivo.txt", dto.getFileName());
        assertArrayEquals("conteudo".getBytes(), dto.getFile());
    }

    @Test
    void getFileDTO_shouldThrowApplicationException_whenFileThrowsException() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("arquivo.txt");
        when(file.getBytes()).thenThrow(new RuntimeException("erro"));

        ApplicationException ex = assertThrows(ApplicationException.class, () -> utils.getFileDTO(file));
        assertEquals("Falha ao ler o arquivo", ex.getMessage());
    }
}