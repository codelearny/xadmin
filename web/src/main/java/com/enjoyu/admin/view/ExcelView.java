package com.enjoyu.admin.view;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ExcelView extends AbstractXlsxStreamingView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = (String) model.get("sheetName");
        Sheet sheet = workbook.createSheet(name);
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("xx");
        String file = adaptFileName(request);
        response.setHeader("Content-Disposition", "attachment; filename = \"" + file + "\"");
    }

    private String adaptFileName(HttpServletRequest request) throws UnsupportedEncodingException {
        String name = "文件";
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("Mozilla")) {
            return new String(name.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        }
        return URLEncoder.encode(name, "UTF8");
    }
}
