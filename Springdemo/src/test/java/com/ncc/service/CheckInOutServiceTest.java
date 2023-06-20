//package com.ncc.service;
//
//import com.ncc.entity.CheckInOut;
//import com.ncc.entity.Employee;
//import com.ncc.repository.ICheckInOutRepository;
//import com.ncc.service.impl.CheckInOutService;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CheckInOutServiceTest {
//
//    @Mock
//    private ICheckInOutRepository checkInOutRepository;
//
//    @InjectMocks
//    private CheckInOutService checkInOutService;
//
//    @Test
//    public void testCheckIn() {
//        // Mocking input data
//        Employee employee = new Employee();
//        // Set up other necessary data for the test
//
//        // Define the behavior of the checkInOutRepository.save() method
//        when(checkInOutRepository.save(any(CheckInOut.class))).thenReturn(new CheckInOut());
//
//        // Call the method under test
//        CheckInOut result = checkInOutService.checkIn(employee);
//
//        // Assert the result or verify the behavior
//        assertNotNull(result);
//        // Perform other assertions or verifications as needed
//    }
//
//    @Test
//    public void testCheckOut() {
//        // Mocking input data
//        Employee employee = new Employee();
//        // Set up other necessary data for the test
//
//        // Define the behavior of the checkInOutRepository.save() method
//        when(checkInOutRepository.save(any(CheckInOut.class))).thenReturn(new CheckInOut());
//
//        // Call the method under test
//        CheckInOut result = checkInOutService.checkOut(employee);
//
//        // Assert the result or verify the behavior
//        assertNotNull(result);
//        // Perform other assertions or verifications as needed
//    }
//
//    // Write more test methods for other scenarios
//
//}
