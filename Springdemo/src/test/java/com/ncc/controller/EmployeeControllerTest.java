package com.ncc.controller;

import com.ncc.dto.EmployeeDTO;

import com.ncc.entity.Employee;
import com.ncc.service.impl.EmployeeService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;


import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testGetAllEmployees() throws Exception {
        // Create mock data
        List<EmployeeDTO> mockEmployees = new ArrayList<>();
        mockEmployees.add(new EmployeeDTO(1, "John", "Doe", "chiennguye2@gmail.com", null));
        mockEmployees.add(new EmployeeDTO(2, "Lan", "Nguyen", "honglannguyen@gmail.com", null));

        // Configure the mock behavior
        Mockito.when(employeeService.getAllEmployee()).thenReturn(mockEmployees);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Lan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Nguyen"));
    }
}




