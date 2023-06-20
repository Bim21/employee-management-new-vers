package com.ncc.controller;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.Employee;
import com.ncc.projection.EmployeeWithoutCheckInOutProjection;
import com.ncc.service.impl.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetCheckInOuts() throws Exception {
        int employeeId = 1;
        List<CheckInOutDTO> checkInOuts = new ArrayList<>();
        // Thiết lập dữ liệu đầu vào khác cho kiểm thử

        // Định nghĩa hành vi của phương thức employeeService.getCheckInOutsByEmployeeId()
        when(employeeService.getCheckInOutsByEmployeeId(employeeId)).thenReturn(checkInOuts);

        // Thực hiện yêu cầu và kiểm tra phản hồi
        mockMvc.perform(get("/{employeeId}/checkInOuts", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(checkInOuts.size())))
                // Thực hiện các kiểm tra khác trên phản hồi nếu cần
                .andReturn();
    }

    @Test
    public void testGetEmployeesWithCheckInOuts() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        Pageable pageable = PageRequest.of(0, 10);
        Page<EmployeeResponseDTO> employeePage = new PageImpl<>(new ArrayList<>());
        // Thiết lập dữ liệu đầu vào khác cho kiểm thử

        // Định nghĩa hành vi của phương thức employeeService.getEmployeesWithCheckInOuts()
        when(employeeService.getEmployeesWithCheckInOuts(startDate, endDate, pageable)).thenReturn(employeePage);

        // Thực hiện yêu cầu và kiểm tra phản hồi
        mockMvc.perform(get("/checkInOuts")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                // Thực hiện các kiểm tra khác trên phản hồi nếu cần
                .andReturn();
    }

    @Test
    public void testGetEmployeesWithoutCheckInOut() throws Exception {
        List<EmployeeWithoutCheckInOutProjection> employees = new ArrayList<>();
        // Thiết lập dữ liệu đầu vào khác cho kiểm thử

        // Định nghĩa hành vi của phương thức employeeService.getEmployeesWithoutCheckInOut()
        when(employeeService.getEmployeesWithoutCheckInOut()).thenReturn(employees);

        // Thực hiện yêu cầu và kiểm tra phản hồi
        mockMvc.perform(get("/without-checkinout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(employees.size())))
                // Thực hiện các kiểm tra khác trên phản hồi nếu cần
                .andReturn();
    }

    @Test
    public void testGetEmployeesCheckInOutError() throws Exception {
        List<Employee> employees = new ArrayList<>();
        // Thiết lập dữ liệu đầu vào khác cho kiểm thử

        // Định nghĩa hành vi của phương thức employeeService.getEmployeesWithoutError()
        when(employeeService.getEmployeesWithoutError()).thenReturn(employees);

        // Thực hiện yêu cầu và kiểm tra phản hồi
        mockMvc.perform(get("/checkin-errors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(employees.size())))
                // Thực hiện các kiểm tra khác trên phản hồi nếu cần
                .andReturn();
    }
}
