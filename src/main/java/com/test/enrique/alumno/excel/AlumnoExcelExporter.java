package com.test.enrique.alumno.excel;

import com.test.enrique.alumno.entity.Alumno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Alumno> listAlumnos;

    public AlumnoExcelExporter(List<Alumno> listAlumnos) {
        this.listAlumnos=listAlumnos;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell=row.createCell(columnCount);
        if(value instanceof Long) {
            cell.setCellValue((Long) value);
        }else if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine() {
        sheet=workbook.createSheet("Alumnos");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row,0,"Informacion de los alumnos",style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
        font.setFontHeightInPoints((short)(10));
        row=sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Id Alumno", style);
        createCell(row, 1, "Cedula Alumno", style);
        createCell(row, 2, "Nombre Alumno", style);
        createCell(row, 3, "Apellido Alumno", style);
        createCell(row, 4, "Grado Alumno", style);
        createCell(row, 5, "Fecha Nacimiento", style);
        createCell(row, 6, "Fecha Registro", style);
    }

    private void writeDataLines() {
        int rowCount=2;
        CellStyle style=workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        for(Alumno alu:listAlumnos) {
            Row row=sheet.createRow(rowCount++);
            int columnCount=0;
            createCell(row, columnCount++, alu.getId(), style);
            createCell(row, columnCount++, alu.getAlumnoCedula(), style);
            createCell(row, columnCount++, alu.getAlumnoNombre(), style);
            createCell(row, columnCount++, alu.getAlumnoApellido(), style);
            createCell(row, columnCount++, alu.getAlumnoGrado(), style);
            createCell(row, columnCount++, alu.getAlumnoFecNaci().toString(), style);
            createCell(row, columnCount++, alu.getAlumnoFecReg().toString(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream=response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
