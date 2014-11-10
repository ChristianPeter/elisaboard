/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.neptune.elisaboard.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.examples.html.ToHtml;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;

/**
 *
 * @author murdoc
 */
@Stateless
public class ExcelProcessor implements Serializable {

    private static final Logger LOG = Logger.getLogger(ExcelProcessor.class.getName());

    public String processExcel(InputStream is) throws IOException, ParserConfigurationException {
        LOG.info("process Excel...");
        StringBuilder output = new StringBuilder();
        final ToHtml temp = ToHtml.create(is, output);
        temp.setCompleteHTML(true);
        temp.printPage();
        return output.toString();
        /*
         HSSFWorkbook wb = new HSSFWorkbook(is);

         final HSSFSheet sheet = wb.getSheetAt(0);
        
         final Iterator<Row> rowIterator = sheet.rowIterator();
        
         while (rowIterator.hasNext()){
         final Row row = rowIterator.next();
         final Cell cell = row.getCell(0);
         LOG.info(" cell "  + cell);
         }

         ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(
         DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
         excelToHtmlConverter.processWorkbook(wb);
        
         final Document document = excelToHtmlConverter.getDocument();
         DOMSource domSource = new DOMSource( document );
        
         System.out.println(domSource.getNode());

       
         */
    }
}
