package demo.two;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class Example2 {
	public static void main(String args[]) {
		
		/* first, we init the runtime engine. Defaults are fine. */
		Velocity.init();

		/* lets make a Context and put data into it */
		VelocityContext context = new VelocityContext();

		context.put("name", "Velocity");
		context.put("project", "Jakarta");

		/* lets render a template */
		StringWriter w = new StringWriter();
		
		// 加入語系參數
		Velocity.mergeTemplate("testtemplate.vm","UTF-8", context, w);
		System.out.println(" template : " + w);

		/* lets make our own string to render */
//		String s = "We are using $project $name to render this.";
//		w = new StringWriter();
//		Velocity.evaluate(context, w, "mystring", s);
//		System.out.println(" string : " + w);		
		
		// generate code
		geneFile(w.toString());

	}

	private static void geneFile(String w) {
		String separator = File.separator;
		String file = "geneFile.java";
		String directory = "dirA" + separator + "dirB";

		File f = new File(directory, file);
		if (f.exists()) {
			System.out.println("文件名：" + f.getAbsolutePath());
			System.out.println("檔案長度：" + f.length());
			try {
				writeToFile(w, f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
				
				writeToFile(w, f);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeToFile(String w, File f) throws IOException {
		
		StringReader sr = new StringReader(w);				
		BufferedReader brdFile = new BufferedReader(sr);

		FileWriter fw = new FileWriter(f);
		BufferedWriter bwrFile = new BufferedWriter(fw);
		String strLine;
		while ((strLine = brdFile.readLine()) != null) {
			bwrFile.write(strLine);
			bwrFile.newLine();
		}
		brdFile.close();
		bwrFile.close();
	}

}
