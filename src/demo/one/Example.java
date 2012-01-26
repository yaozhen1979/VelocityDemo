package demo.one;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;
import java.util.ArrayList;

/**
 * This class is a simple demonstration of how the Velocity Template Engine can
 * be used in a standalone application.
 * 
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: Example.java 463298 2006-10-12 16:10:32Z henning $
 */

public class Example {
	public Example(String templateFile) {
		try {
			/*
			 * setup
			 */

			Velocity.init("velocity.properties");
			//Velocity.init();
			
			/*
			 * Make a context object and populate with the data. This is where
			 * the Velocity engine gets the data to resolve the references (ex.
			 * $list) in the template
			 */

			VelocityContext context = new VelocityContext();
			context.put("list", getNames());

			/*
			 * get the Template object. This is the parsed version of your
			 * template input file. Note that getTemplate() can throw
			 * ResourceNotFoundException : if it doesn't find the template
			 * ParseErrorException : if there is something wrong with the VTL
			 * Exception : if something else goes wrong (this is generally
			 * indicative of as serious problem...)
			 */

			Template template = null;

			try {
				template = Velocity.getTemplate(templateFile);
			} catch (ResourceNotFoundException rnfe) {
				System.out.println("Example : error : cannot find template "
						+ templateFile);
			} catch (ParseErrorException pee) {
				System.out.println("Example : Syntax error in template "
						+ templateFile + ":" + pee);
			}

			/*
			 * Now have the template engine process your template using the data
			 * placed into the context. Think of it as a 'merge' of the template
			 * and the data to produce the output stream.
			 */

			BufferedWriter writer = writer = new BufferedWriter(
					new OutputStreamWriter(System.out));

			if (template != null)
				template.merge(context, writer);

			/*
			 * flush and cleanup
			 */

			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList getNames() {
		ArrayList list = new ArrayList();

		list.add("ArrayList element 1");
		list.add("ArrayList element 2");
		list.add("ArrayList element 3");
		list.add("ArrayList element 4");

		return list;
	}

	public static void main(String[] args) {
		Example t = new Example("example.vm");
	}
}
