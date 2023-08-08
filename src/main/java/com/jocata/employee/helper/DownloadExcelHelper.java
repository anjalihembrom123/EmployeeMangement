package com.jocata.employee.helper;

import com.jocata.employee.entity.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class DownloadExcelHelper {

    static String[] HEADER = { "empid", "empname", "salary", "orgid" };
    public static String Sheet = "Employee";

    public static ByteArrayInputStream dataToExcel(List<Employee> list) throws IOException {
        //create workbook
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            //create Sheet
            Sheet sheet = workbook.createSheet("Employee_data");

            //create row
            Row row = sheet.createRow(0);

            for(int i = 0; i < HEADER.length;i++){
                row.createCell(i);
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADER[i]);
            }
            // value rows
            int rowIndex = 1;
            for(Employee employee:list){
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(employee.getEmpId());
                row1.createCell(1).setCellValue(employee.getEmpName());
                row1.createCell(2).setCellValue(employee.getSalary());
                row1.createCell(3).setCellValue(employee.getOrganisation().getOrgId());
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        }catch(IOException e){
           e.printStackTrace();
            System.out.println("fail to import data in excel");
            return null;
        }
        finally {
            workbook.close();
            out.close();
        }
    }
}
