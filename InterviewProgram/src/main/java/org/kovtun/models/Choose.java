package org.kovtun.models;

import java.util.ArrayList;

import lombok.Data;

/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
@Data
public class Choose {
	/**This class is used as a ModelAttribute to get selected topic types*/
	private ArrayList<String> options;

}
