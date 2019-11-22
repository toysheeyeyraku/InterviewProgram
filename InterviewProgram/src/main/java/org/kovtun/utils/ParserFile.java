package org.kovtun.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

public class ParserFile {
	public HashMap<String, ArrayList<String>> typeQuestions = new HashMap<String, ArrayList<String>>();

	public ParserFile(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		System.out.println("Retrieving Sheets using for-each loop");
		DataFormatter dataFormatter = new DataFormatter();
		Sheet startsheet = workbook.getSheetAt(0);
		Row row = startsheet.getRow(0);
		int k = 0;
		for (Cell cell : row) {
			String questionType = dataFormatter.formatCellValue(cell);
			k++;
			typeQuestions.put(questionType, new ArrayList<>());
			Sheet typesheet = workbook.getSheetAt(k);
			for (Row r : typesheet) {
				String question = dataFormatter.formatCellValue(r.getCell(0));
				typeQuestions.get(questionType).add(question);
			}
		}
	}
}
