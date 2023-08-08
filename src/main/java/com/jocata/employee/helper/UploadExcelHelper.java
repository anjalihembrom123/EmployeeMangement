package com.jocata.employee.helper;

import com.jocata.employee.entity.Employee;
import com.jocata.employee.entity.Organisation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UploadExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "empid", "empname", "salary", "orgid" };
    static String SHEET = "Employee";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Employee> excelToEmployeelist(InputStream is) {
        try {
            Workbook workbook  = new XSSFWorkbook(is);
            //fetch Sheet
            Sheet sheet = workbook.getSheet("Sheet1");
            Iterator<Row> rows = sheet.iterator();

            List<Employee> employees = new ArrayList<Employee>();
            //enter row
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Cell cell = currentRow.getCell(1);
                // skip header and blank in salary field
                if (rowNumber == 0 || (cell == null || cell.getCellType() == CellType.BLANK)) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Employee employee = new Employee();
                Organisation organisation = new Organisation();

                //create index
                int cellIdx = 0;
//
                    while (cellsInRow.hasNext() ) {
                        Cell currentCell = cellsInRow.next();
                        if (!ObjectUtils.isEmpty(currentCell)) {
                            switch (cellIdx) {
                                case 0:
                                    if (currentCell.getStringCellValue() != "") {
                                        employee.setEmpName(currentCell.getStringCellValue());
                                        break;
                                    }
                                    System.out.println("Employee name is null ");
                                    break;

                                case 1:
                                    if ((float) currentCell.getNumericCellValue() == 0) {
                                        System.out.println("Salary is null");
                                        break;
                                    }
                                    employee.setSalary((float) currentCell.getNumericCellValue());
                                    break;
                                case 2:
                                    if (currentCell.getNumericCellValue() == 0) {
                                        System.out.println("OrgId is null");
                                        break;
                                    }
                                    organisation.setOrgId((int) currentCell.getNumericCellValue());
                                    employee.setOrganisation(organisation);
                                    break;

                                default:
                                    break;
                            }

                            cellIdx++;
                        }
                        else {
                            continue;
                            }
                        employees.size();
                        employees.add(employee);

                    }
            }
            workbook.close();

            return employees;
        } catch (IOException e) {

            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
