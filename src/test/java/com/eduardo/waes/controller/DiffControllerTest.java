package com.eduardo.waes.controller;

import com.eduardo.waes.domain.DirectionEnum;
import com.eduardo.waes.exception.DirectionAlreadyLoadedException;
import com.eduardo.waes.service.DiffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DiffController.class, secure = false)
public class DiffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiffService diffService;

    @Captor
    private ArgumentCaptor<DirectionEnum> directionCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<String> valueCaptor;

    @Test
    public void saveLeft_whenValueIsNotBase64_shouldRespondException() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/123/left")
                .content("test not Base 64")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Not a Base64 encoded parameter", result.getResponse().getErrorMessage());
    }

    @Test
    public void saveLeft_whenDirectionAlreadyHaveValueForThatId_shouldRespondException() throws Exception {
        doThrow(new DirectionAlreadyLoadedException()).when(diffService).save(anyLong(), anyString(), any());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/123/left")
                .content(Base64.getEncoder().encodeToString("test".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Value already loaded to the informed direction for this id", result.getResponse().getErrorMessage());
    }

    @Test
    public void saveLeft_whenHappyPath_shouldCallServiceLayerWithCorrectValues() throws Exception {
        doNothing().when(diffService).save(idCaptor.capture(), valueCaptor.capture(), directionCaptor.capture());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/123/left")
                .content(Base64.getEncoder().encodeToString("test".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(204));

        assertEquals(Long.valueOf(123L), idCaptor.getValue());
        assertEquals("test", new String(Base64.getDecoder().decode(valueCaptor.getValue())));
        assertEquals(DirectionEnum.left, directionCaptor.getValue());
    }

    @Test
    public void saveRight_whenValueIsNotBase64_shouldRespondException() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/123/right")
                .content("test not Base 64")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Not a Base64 encoded parameter", result.getResponse().getErrorMessage());
    }

    @Test
    public void saveRight_whenDirectionAlreadyHaveValueForThatId_shouldRespondException() throws Exception {
        doThrow(new DirectionAlreadyLoadedException()).when(diffService).save(anyLong(), anyString(), any());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/123/right")
                .content(Base64.getEncoder().encodeToString("test".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Value already loaded to the informed direction for this id", result.getResponse().getErrorMessage());
    }

    @Test
    public void saveRight_whenHappyPath_shouldCallServiceLayerWithCorrectValues() throws Exception {
        doNothing().when(diffService).save(idCaptor.capture(), valueCaptor.capture(), directionCaptor.capture());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/123/right")
                .content(Base64.getEncoder().encodeToString("test".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(204));

        assertEquals(Long.valueOf(123L), idCaptor.getValue());
        assertEquals("test", new String(Base64.getDecoder().decode(valueCaptor.getValue())));
        assertEquals(DirectionEnum.right, directionCaptor.getValue());
    }

}