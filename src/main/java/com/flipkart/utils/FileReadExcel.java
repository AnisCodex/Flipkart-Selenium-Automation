package com.flipkart.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileReadExcel {

    public String path;
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;

    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    public FileReadExcel(String path) {

        this.path = path;

        try {

            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);

            fis.close();
            fis = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRowCount(String sheetName) {

        int index = workbook.getSheetIndex(sheetName);

        if (index == -1) {
            return 0;
        } else {

            sheet = workbook.getSheetAt(index);

            int number = sheet.getLastRowNum() + 1;

            return number;
        }
    }

    public String getCellData(
            String sheetName,
            String colName,
            int rowNum) {

        try {

            if (rowNum <= 0) {
                return "";
            }

            int index = workbook.getSheetIndex(sheetName);

            if (index == -1) {
                return "";
            }

            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(0);

            if (row == null) {
                return "";
            }

            int colNum = -1;

            for (int i = 0; i < row.getLastCellNum(); i++) {

                XSSFCell headerCell = row.getCell(i);

                if (headerCell != null
                        && headerCell.getCellType() == CellType.STRING
                        && headerCell.getStringCellValue()
                                .trim()
                                .equalsIgnoreCase(colName.trim())) {

                    colNum = i;
                    break;
                }
            }

            if (colNum == -1) {
                return "";
            }

            row = sheet.getRow(rowNum - 1);

            if (row == null) {
                return "";
            }

            cell = row.getCell(colNum);

            if (cell == null) {
                return "";
            }

            return getCellValue(cell);

        } catch (Exception e) {

            e.printStackTrace();

            return "row " + rowNum
                    + " or column "
                    + colName
                    + " does not exist in xls";
        }
    }

    public String getCellData(
            String sheetName,
            int colNum,
            int rowNum) {

        try {

            if (rowNum <= 0) {
                return "";
            }

            int index = workbook.getSheetIndex(sheetName);

            if (index == -1) {
                return "";
            }

            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(rowNum - 1);

            if (row == null) {
                return "";
            }

            cell = row.getCell(colNum);

            if (cell == null) {
                return "";
            }

            return getCellValue(cell);

        } catch (Exception e) {

            e.printStackTrace();

            return "row " + rowNum
                    + " or column "
                    + colNum
                    + " does not exist in xls";
        }
    }

    private String getCellValue(XSSFCell cell) {

        if (cell == null) {
            return "";
        }

        CellType cellType = cell.getCellType();

        switch (cellType) {

        case STRING:

            return cell.getStringCellValue();

        case NUMERIC:

            if (DateUtil.isCellDateFormatted(cell)) {

                double d = cell.getNumericCellValue();

                Calendar cal = Calendar.getInstance();

                cal.setTime(DateUtil.getJavaDate(d));

                return cal.get(Calendar.DAY_OF_MONTH)
                        + "/"
                        + (cal.get(Calendar.MONTH) + 1)
                        + "/"
                        + cal.get(Calendar.YEAR);
            }

            return String.valueOf(
                    cell.getNumericCellValue());

        case FORMULA:

            switch (cell.getCachedFormulaResultType()) {

            case STRING:

                return cell.getStringCellValue();

            case NUMERIC:

                if (DateUtil.isCellDateFormatted(cell)) {

                    double d =
                            cell.getNumericCellValue();

                    Calendar cal =
                            Calendar.getInstance();

                    cal.setTime(
                            DateUtil.getJavaDate(d));

                    return cal.get(Calendar.DAY_OF_MONTH)
                            + "/"
                            + (cal.get(Calendar.MONTH) + 1)
                            + "/"
                            + cal.get(Calendar.YEAR);
                }

                return String.valueOf(
                        cell.getNumericCellValue());

            case BOOLEAN:

                return String.valueOf(
                        cell.getBooleanCellValue());

            case BLANK:

                return "";

            default:

                return "";
            }

        case BOOLEAN:

            return String.valueOf(
                    cell.getBooleanCellValue());

        case BLANK:

            return "";

        case ERROR:

            return "";

        default:

            return "";
        }
    }

    public boolean setCellData(
            String sheetName,
            String colName,
            int rowNum,
            String data) {

        try {

            if (rowNum <= 0) {
                return false;
            }

            FileInputStream inputStream =
                    new FileInputStream(path);

            XSSFWorkbook workbookToWrite =
                    new XSSFWorkbook(inputStream);

            inputStream.close();

            int index =
                    workbookToWrite.getSheetIndex(sheetName);

            if (index == -1) {

                workbookToWrite.close();

                return false;
            }

            XSSFSheet writeSheet =
                    workbookToWrite.getSheetAt(index);

            XSSFRow headerRow =
                    writeSheet.getRow(0);

            if (headerRow == null) {

                workbookToWrite.close();

                return false;
            }

            int colNum = -1;

            for (int i = 0;
                    i < headerRow.getLastCellNum();
                    i++) {

                XSSFCell headerCell =
                        headerRow.getCell(i);

                if (headerCell != null
                        && headerCell.getCellType()
                                == CellType.STRING
                        && headerCell.getStringCellValue()
                                .trim()
                                .equalsIgnoreCase(
                                        colName.trim())) {

                    colNum = i;
                    break;
                }
            }

            if (colNum == -1) {

                workbookToWrite.close();

                return false;
            }

            writeSheet.autoSizeColumn(colNum);

            XSSFRow dataRow =
                    writeSheet.getRow(rowNum - 1);

            if (dataRow == null) {

                dataRow =
                        writeSheet.createRow(rowNum - 1);
            }

            XSSFCell dataCell =
                    dataRow.getCell(colNum);

            if (dataCell == null) {

                dataCell =
                        dataRow.createCell(colNum);
            }

            dataCell.setCellValue(data);

            FileOutputStream outputStream =
                    new FileOutputStream(path);

            workbookToWrite.write(outputStream);

            outputStream.close();

            workbookToWrite.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public boolean setCellData(
            String sheetName,
            String colName,
            int rowNum,
            String data,
            String url) {

        try {

            if (rowNum <= 0) {
                return false;
            }

            FileInputStream inputStream =
                    new FileInputStream(path);

            XSSFWorkbook workbookToWrite =
                    new XSSFWorkbook(inputStream);

            inputStream.close();

            int index =
                    workbookToWrite.getSheetIndex(sheetName);

            if (index == -1) {

                workbookToWrite.close();

                return false;
            }

            XSSFSheet writeSheet =
                    workbookToWrite.getSheetAt(index);

            XSSFRow headerRow =
                    writeSheet.getRow(0);

            if (headerRow == null) {

                workbookToWrite.close();

                return false;
            }

            int colNum = -1;

            for (int i = 0;
                    i < headerRow.getLastCellNum();
                    i++) {

                XSSFCell headerCell =
                        headerRow.getCell(i);

                if (headerCell != null
                        && headerCell.getCellType()
                                == CellType.STRING
                        && headerCell.getStringCellValue()
                                .trim()
                                .equalsIgnoreCase(
                                        colName.trim())) {

                    colNum = i;
                    break;
                }
            }

            if (colNum == -1) {

                workbookToWrite.close();

                return false;
            }

            writeSheet.autoSizeColumn(colNum);

            XSSFRow dataRow =
                    writeSheet.getRow(rowNum - 1);

            if (dataRow == null) {

                dataRow =
                        writeSheet.createRow(rowNum - 1);
            }

            XSSFCell dataCell =
                    dataRow.getCell(colNum);

            if (dataCell == null) {

                dataCell =
                        dataRow.createCell(colNum);
            }

            dataCell.setCellValue(data);

            XSSFCreationHelper createHelper =
                    workbookToWrite.getCreationHelper();

            CellStyle hyperlinkStyle =
                    workbookToWrite.createCellStyle();

            XSSFFont hyperlinkFont =
                    workbookToWrite.createFont();

            hyperlinkFont.setUnderline(
                    XSSFFont.U_SINGLE);

            hyperlinkFont.setColor(
                    IndexedColors.BLUE.getIndex());

            hyperlinkStyle.setFont(
                    hyperlinkFont);

            XSSFHyperlink link =
                    createHelper.createHyperlink(
                            HyperlinkType.URL);

            link.setAddress(url);

            dataCell.setHyperlink(link);

            dataCell.setCellStyle(
                    hyperlinkStyle);

            FileOutputStream outputStream =
                    new FileOutputStream(path);

            workbookToWrite.write(outputStream);

            outputStream.close();

            workbookToWrite.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public boolean addSheet(String sheetname) {

        try {

            workbook.createSheet(sheetname);

            FileOutputStream outputStream =
                    new FileOutputStream(path);

            workbook.write(outputStream);

            outputStream.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public boolean removeSheet(String sheetName) {

        int index =
                workbook.getSheetIndex(sheetName);

        if (index == -1) {
            return false;
        }

        try {

            workbook.removeSheetAt(index);

            FileOutputStream outputStream =
                    new FileOutputStream(path);

            workbook.write(outputStream);

            outputStream.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public boolean addColumn(
            String sheetName,
            String colName) {

        try {

            FileInputStream inputStream =
                    new FileInputStream(path);

            XSSFWorkbook workbookToWrite =
                    new XSSFWorkbook(inputStream);

            inputStream.close();

            int index =
                    workbookToWrite.getSheetIndex(
                            sheetName);

            if (index == -1) {

                workbookToWrite.close();

                return false;
            }

            XSSFCellStyle style =
                    workbookToWrite.createCellStyle();

            style.setFillPattern(
                    FillPatternType.SOLID_FOREGROUND);

            XSSFSheet writeSheet =
                    workbookToWrite.getSheetAt(index);

            XSSFRow headerRow =
                    writeSheet.getRow(0);

            if (headerRow == null) {

                headerRow =
                        writeSheet.createRow(0);
            }

            short lastCellNum =
                    headerRow.getLastCellNum();

            XSSFCell newCell;

            if (lastCellNum == -1) {

                newCell =
                        headerRow.createCell(0);

            } else {

                newCell =
                        headerRow.createCell(
                                lastCellNum);
            }

            newCell.setCellValue(colName);

            newCell.setCellStyle(style);

            FileOutputStream outputStream =
                    new FileOutputStream(path);

            workbookToWrite.write(outputStream);

            outputStream.close();

            workbookToWrite.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public boolean removeColumn(
            String sheetName,
            int colNum) {

        try {

            if (!isSheetExist(sheetName)) {
                return false;
            }

            FileInputStream inputStream =
                    new FileInputStream(path);

            XSSFWorkbook workbookToWrite =
                    new XSSFWorkbook(inputStream);

            inputStream.close();

            XSSFSheet writeSheet =
                    workbookToWrite.getSheet(sheetName);

            XSSFCellStyle style =
                    workbookToWrite.createCellStyle();

            style.setFillPattern(
                    FillPatternType.NO_FILL);

            int rowCount =
                    writeSheet.getLastRowNum() + 1;

            for (int i = 0;
                    i < rowCount;
                    i++) {

                XSSFRow currentRow =
                        writeSheet.getRow(i);

                if (currentRow != null) {

                    XSSFCell currentCell =
                            currentRow.getCell(colNum);

                    if (currentCell != null) {

                        currentCell.setCellStyle(
                                style);

                        currentRow.removeCell(
                                currentCell);
                    }
                }
            }

            FileOutputStream outputStream =
                    new FileOutputStream(path);

            workbookToWrite.write(outputStream);

            outputStream.close();

            workbookToWrite.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public boolean isSheetExist(
            String sheetName) {

        int index =
                workbook.getSheetIndex(sheetName);

        if (index == -1) {

            index =
                    workbook.getSheetIndex(
                            sheetName.toUpperCase());

            if (index == -1) {

                return false;

            } else {

                return true;
            }

        } else {

            return true;
        }
    }

    public int getColumnCount(
            String sheetName) {

        if (!isSheetExist(sheetName)) {
            return -1;
        }

        sheet =
                workbook.getSheet(sheetName);

        row =
                sheet.getRow(0);

        if (row == null) {
            return -1;
        }

        return row.getLastCellNum();
    }

    public boolean addHyperLink(
            String sheetName,
            String screenShotColName,
            String testCaseName,
            int index,
            String url,
            String message) {

        url =
                url.replace('\\', '/');

        if (!isSheetExist(sheetName)) {
            return false;
        }

        sheet =
                workbook.getSheet(sheetName);

        for (int i = 2;
                i <= getRowCount(sheetName);
                i++) {

            if (getCellData(
                    sheetName,
                    0,
                    i)
                    .equalsIgnoreCase(
                            testCaseName)) {

                setCellData(
                        sheetName,
                        screenShotColName,
                        i + index,
                        message,
                        url);

                break;
            }
        }

        return true;
    }

    public int getCellRowNum(
            String sheetName,
            String colName,
            String cellValue) {

        for (int i = 2;
                i <= getRowCount(sheetName);
                i++) {

            if (getCellData(
                    sheetName,
                    colName,
                    i)
                    .equalsIgnoreCase(
                            cellValue)) {

                return i;
            }
        }

        return -1;
    }

    public HashMap<String, String> getRowTestData(
            String worksheetName,
            String testName) {

        HashMap<String, String> testData =
                new HashMap<String, String>();

        for (int i = 2;
                i <= getRowCount(worksheetName);
                i++) {

            if (getCellData(
                    worksheetName,
                    0,
                    i)
                    .equals(testName)) {

                int columnCount =
                        getColumnCount(
                                worksheetName);

                for (int j = 0;
                        j < columnCount;
                        j++) {

                    String columnName =
                            getCellData(
                                    worksheetName,
                                    j,
                                    1);

                    String columnValue =
                            getCellData(
                                    worksheetName,
                                    j,
                                    i);

                    if (columnName != null
                            && !columnName
                                    .trim()
                                    .isEmpty()) {

                        testData.put(
                                columnName,
                                columnValue);
                    }
                }

                break;
            }
        }

        return testData;
    }

    public void close() {

        try {

            if (workbook != null) {

                workbook.close();

                workbook = null;
            }

            if (fis != null) {

                fis.close();

                fis = null;
            }

            if (fileOut != null) {

                fileOut.close();

                fileOut = null;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}