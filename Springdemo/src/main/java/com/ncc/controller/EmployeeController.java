package com.ncc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.dto.EmployeeDTO;
import com.ncc.dto.EmployeeRequestDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.Employee;
import com.ncc.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final IEmployeeService employeeService;

    @Autowired
    private MessageSource messageSource;

//    @Value("${hrm.api.url}")
//    private String hrmApiUrl;

//    @GetMapping(value = "/employee")
//    public EmployeeResponseDTO getAndSaveListUser() throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl = hrmApiUrl;
//        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
//        List<String> rs = Collections.singletonList(response.getBody());
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode rootNode = mapper.readTree(response.getBody());
//        JsonNode resultNode = rootNode.get("result");
//        List<Employee> employees = mapper.readValue(resultNode.toString(), new TypeReference<List<Employee>>() {
//        });
//        for (Employee employee : employees) {
//            System.out.println(employee);
//        }
//
//        employeeService.saveUser(employees);
//
//        return getAndSaveListUser();
//
//    }
    @PostMapping("/employee")
    public List<EmployeeResponseDTO> syncData(@RequestBody EmployeeRequestDTO employeeRequestDTO){
        return employeeService.syncData(employeeRequestDTO);
    }

    @PostMapping
    public EmployeeResponseDTO createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeService.createEmployee(employeeRequestDTO);
    }


    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        employeeRequestDTO.setId(id);
        return employeeService.updateEmployee(employeeRequestDTO);

    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/search")
    public List<EmployeeResponseDTO> searchEmployeesByName(@RequestParam("keyword") String keyword) {
        return employeeService.searchEmployeesByName(keyword);
    }
}
