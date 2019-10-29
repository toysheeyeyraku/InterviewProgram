package services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import com.mkyong.ParserFile;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class Session {
		private HashMap<String,HashSet<Integer>> answered =new HashMap<String,HashSet<Integer>>();
		private String userName;
		private ParserFile file ;
		public Session(ParserFile file,String userName) {
			this.file=file;
			this.userName=userName;
		}
	
		public String getQuestion(String type) {
			if (file==null) {
				return "File not loaded";
			}
			if (!file.typeQuestions.containsKey(type)) {
				return "No such type";
			}
			if (!answered.containsKey(type)) {
				answered.put(type, new HashSet<Integer>());
			}
			
			if ( file.typeQuestions.get(type).size()==answered.get(type).size()){
				return null;
			}
			Random rand =new Random();
			int ans=-1;
			while (true) {
				int randomNum = rand.nextInt((file.typeQuestions.get(type).size()-1 ) + 1) ;
				if (!answered.get(type).contains(randomNum)) {
					ans=randomNum;
					answered.get(type).add(ans);
					break;
				}
			}
		/*	StringBuilder b=new StringBuilder();
			java.util.Collections.sort( file.typeQuestions.get(type));
			for (String s: file.typeQuestions.get(type)) {
				b.append(s+" ");
			}*/
			return file.typeQuestions.get(type).get(ans);
			//return b.toString();
		}
}
