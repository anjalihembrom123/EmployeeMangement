package com.jocata.employee.helper;

import com.jocata.employee.entity.Employee;
import com.jocata.employee.entity.Organisation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
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
            Sheet sheet = workbook.getSheet("Employee_data");
            Iterator<Row> rows = sheet.iterator();

            List<Employee> employees = new ArrayList<Employee>();
                //enter row
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Employee employee = new Employee();
                Organisation organisation = new Organisation();

                //create index
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                                employee.setEmpId((int) currentCell.getNumericCellValue());

                            break;

                        case 1:
                                employee.setEmpName(currentCell.getStringCellValue());

                            break;

                        case 2:
                              employee.setSalary((float) currentCell.getNumericCellValue());

                            break;
                        case 3:
                            organisation.setOrgId((int)currentCell.getNumericCellValue());
                            employee.setOrganisation(organisation);
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                employees.add(employee);
            }

            workbook.close();

            return employees;
        } catch (IOException e) {

            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
