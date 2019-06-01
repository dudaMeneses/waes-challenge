package com.eduardo.waes.integration;

import com.eduardo.waes.DiffApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiffApplication.class)
public class DiffDifferentSizeIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenSendingBase64ValueToLeft_shouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/2/left")
                .content(Base64.getEncoder().encodeToString("test".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void whenSendingBase64ValueToRight_shouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/2/right")
                .content(Base64.getEncoder().encodeToString("test1".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void whenDiffEquals_shouldReturnOkAndEquals() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/diff/2")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        assertEquals("{\"result\":\"DIFFERENT_SIZE\"}", result.getResponse().getContentAsString());
    }
}
