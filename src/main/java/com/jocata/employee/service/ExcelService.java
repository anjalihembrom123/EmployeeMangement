package com.jocata.employee.service;

import com.jocata.employee.dao.EmployeeDao;
import com.jocata.employee.dao.Repository;
import com.jocata.employee.entity.Employee;
import com.jocata.employee.helper.DownloadExcelHelper;
import com.jocata.employee.helper.UploadExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Service
public class ExcelService {
    @Autowired
    Repository repository;
    @Autowired
    EmployeeDao employeeDao;
    public void save(MultipartFile file){
        try {
            List<Employee> employees = UploadExcelHelper.excelToEmployeelist(file.getInputStream());
            repository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream getAllData() {
        try {
            List<Employee> employees = repository.findAll();
            ByteArrayInputStream byteArrayInputStream = DownloadExcelHelper.dataToExcel(employees);
            return byteArrayInputStream;
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream getAllDataBySalary(float salary) {
        try {
            List<Employee> employees = employeeDao.loadEntityByHql("from Employee where salary ="+ salary);
            ByteArrayInputStream byteArrayInputStream = DownloadExcelHelper.dataToExcel(employees);
            return byteArrayInputStream;
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream getAllDataByOrg(int OrgId) {
        try {
            List<Employee> employees = employeeDao.loadEntityByHql("select distinct e from Employee e join e.organisation o with o.orgid ="+ OrgId);
            ByteArrayInputStream byteArrayInputStream = DownloadExcelHelper.dataToExcel(employees);
            return byteArrayInputStream;
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
