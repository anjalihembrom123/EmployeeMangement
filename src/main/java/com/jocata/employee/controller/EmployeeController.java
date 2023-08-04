package com.jocata.employee.controller;

import com.jocata.employee.entity.Employee;
import com.jocata.employee.entity.Organisation;
import com.jocata.employee.helper.UploadExcelHelper;
import com.jocata.employee.message.ResponseMessage;
import com.jocata.employee.service.EmployeeService;
import com.jocata.employee.service.ExcelService;
import com.jocata.employee.service.OrgService;
import com.jocata.employee.vo.EmpRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    OrgService orgService;
    @Autowired
    ExcelService fileService;

    @PostMapping("/saveEmployee")
    public ResponseEntity<?> saveEmployee(@RequestBody EmpRequestResponse request) {
        EmpRequestResponse response = new EmpRequestResponse();
        Employee employee = new Employee();
        Organisation organisation;
        organisation = orgService.getEntityById(request.getOrgid());
        employee.setEmpName(request.getEmpname());
        employee.setSalary(request.getSalary());
        employee.setOrganisation(organisation);
        int id = (employeeService.save(employee));
        if (id > 0) {
            employee.getEmpId();
            response.setEmpid(employee.getEmpId());
            response.setEmpname(employee.getEmpName());
            response.setSalary(employee.getSalary());
            response.setOrgid(employee.getOrganisation().getOrgId());
            return new ResponseEntity<EmpRequestResponse>(response, HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Failed to POST Details of Employee", HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody EmpRequestResponse request) {
        EmpRequestResponse response = new EmpRequestResponse();
        Employee employee = new Employee();
        Organisation organisation;
        employee.setEmpId(request.getEmpid());
        employee.setEmpName(request.getEmpname());
        employee.setSalary(request.getSalary());
        organisation = orgService.getEntityById(request.getOrgid());
        employee.setOrganisation(organisation);
        int check_id = request.getEmpid();
        if(check_id > 0) {
            employeeService.update(employee);
            response.setEmpid(employee.getEmpId());
            response.setEmpname(employee.getEmpName());
            response.setOrgid(employee.getOrganisation().getOrgId());
            response.setSalary(employee.getSalary());
            return new ResponseEntity<EmpRequestResponse>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to update Details of Employee", HttpStatus.BAD_REQUEST);
        }

    }
//    @GetMapping("/getOrgById")
//    public ResponseEntity<?> getOrgById(@RequestBody EmpRequestResponse request) {
//        EmpRequestResponse response = new EmpRequestResponse();
//        Organisation organisation;
//
//        organisation = orgService.getEntityById(request.getOrgid());
//        if (organisation != null) {
//            response.setEmpid(organisation.getOrgid());
//            response.setEmpname(organisation.getOrgname());
//            return new ResponseEntity<EmpRequestResponse>(response, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Failed!!", HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable ("id") Integer id) {
        EmpRequestResponse response = new EmpRequestResponse();
        Employee employee = new Employee();
        Employee employee1 = employeeService.getEntityById(id);
        if (employee1 != null) {
            response.setEmpid(employee1.getEmpId());
            response.setEmpname(employee1.getEmpName());
            response.setSalary(employee1.getSalary());
            response.setOrgid(employee1.getOrganisation().getOrgId());
            return new ResponseEntity<EmpRequestResponse>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed!!", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllEmployee() {
        EmpRequestResponse response = new EmpRequestResponse();
        Employee employee = new Employee();
        List<Employee> employee1 = employeeService.loadEntityByHql();
        if (employee1 != null) {
            return new ResponseEntity<List<Employee>>(employee1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed!!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getByOrgId/{id}")
    public ResponseEntity<?> getByOrgId(@PathVariable ("id") Integer id) {
        EmpRequestResponse response;
        Employee employee;
        Organisation organisation;
        organisation = orgService.getEntityById(id);
        if (organisation != null) {
            List<Employee> employeeList = employeeService.loadEntityByHql(id);
            if (employeeList != null) {
                return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Failed!!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>("No organisation exists with this id in the database", HttpStatus.BAD_REQUEST);
        }
    }
    // using getbyId in hibernate utils
    @GetMapping("/getEmployeeByOrgId/{id}")
    public ResponseEntity<?> getEmployeeByOrgId(@PathVariable ("id") int id) {
        EmpRequestResponse response;
        Employee employee;
        Organisation organisation;
        organisation = orgService.getEntityById(id);
        if (organisation != null) {
            List<Employee> employeeList = organisation.getEmployee();
            System.out.println(employeeList);
            if (employeeList != null) {
                return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Failed!!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>("No organisation exists with this id in the database", HttpStatus.BAD_REQUEST);
        }
    }
    // using create query in hibernate utils
//    @GetMapping("/getEmployeeByOrg/{id}")
//    public ResponseEntity<?> getEmployeeByOrg(@PathVariable ("id") Integer id) {
//        EmpRequestResponse response;
//        Employee employee;
//        List<Organisation> organisation;
//        organisation = orgService.getOrg();
//        if (organisation != null) {
//            // for getting employee list from organisation
//            System.out.println("getting organisation size");
//            System.out.println(organisation.size());
//            System.out.println("organisation with org id: using get id: running query to get employee: ");
//            List<Employee> employeeList = organisation.get(id-1).getEmployee();
//            System.out.println("employeeList");
//            System.out.println(employeeList.size());
//            if (employeeList != null) {
//                return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<String>("Failed!!", HttpStatus.BAD_REQUEST);
//            }
//        } else {
//            return new ResponseEntity<String>("No organisation exists with this id in the database", HttpStatus.BAD_REQUEST);
//        }
//    }

    //--------------------------------------------------Upload/download file -----------------------------------------//

   @PostMapping("/upload")
   public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
       String message = "";

       if (UploadExcelHelper.hasExcelFormat(file)) {
           try {
               fileService.save(file);

               message = "Uploaded the file successfully: " + file.getOriginalFilename();
               return new ResponseEntity<String>(message, HttpStatus.OK);
           } catch (Exception e) {
               System.out.println(e.getMessage());
               message = "Could not upload the file: " + file.getOriginalFilename() + "!";
               return new ResponseEntity<String>(message,HttpStatus.EXPECTATION_FAILED);
           }
       }

       message = "Please upload an excel file!";
       return status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
   }

   @GetMapping("/download")
   public ResponseEntity<Resource> download() throws IOException{
        String filname = "Employee.xlsx";

       ByteArrayInputStream actualData = fileService.getAllData();
       InputStreamResource file = new InputStreamResource(actualData);

       ResponseEntity<Resource> body = ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+filname)
               .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(file);
       return body;
    }
    @GetMapping("/downloadBySalary/{salary}")
    public ResponseEntity<Resource> downloadbyId(@PathVariable ("salary") float salary) throws IOException{
        String filname = "salary.xlsx";

        ByteArrayInputStream actualData = fileService.getAllDataBySalary(salary);
        InputStreamResource file = new InputStreamResource(actualData);

        ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+filname)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(file);

         return body;
    }

    @GetMapping("/downloadByOrgId/{OrgId}")
    public ResponseEntity<Resource> downloadbyId(@PathVariable ("OrgId") int OrgId) throws IOException{
        String filname = "organisation.xlsx";

        ByteArrayInputStream actualData = fileService.getAllDataByOrg(OrgId);
        InputStreamResource file = new InputStreamResource(actualData);

        ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+filname)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(file);

        return body;
    }

}

