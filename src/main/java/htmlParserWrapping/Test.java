package htmlParserWrapping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) {
		String htmlUrl = "https://angular-ui.github.io/bootstrap/";
		// TODO Auto-generated method stub
		Document doc;
		HashMap<String, String> properties = new HashMap<String, String>();
		List<HashMap<String, String>> excelDataList = new ArrayList<HashMap<String, String>>();
//		Properties propertiesBean = new Properties();
		List<Properties> excelDataList2 = new ArrayList<Properties>();

		String COMMA_DELIMITER  = "|";
//		String COMMA_DELIMITER  = ",";
		String FILE_HEADER = "code,em,text";
		String NEW_LINE_SEPARATOR = "\n";
		String file_name="bootstrap.csv";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file_name);
			doc = Jsoup.connect(htmlUrl).get();
			String title = doc.title();
			System.out.println(title);

			Elements maps = doc.select("ul > li > p ");
			Elements codes = doc.select("ul > li > p > code");
			Elements ems = doc.select("ul > li > p > em");

			for (Element code : codes) {
				String codeText = code.text();
				//				  System.out.println("codeText="+codeText);
			}
			for (Element map : maps) {
				String mapHtml = map.html();
				String mapHtml2 = map.html();

				doc = Jsoup.parse(mapHtml);
				Element code = doc.select("code").first();
				Element em = doc.select("em").first();

				String emText = "";
				String emHtml = "";
				String code2Text = "";
				if (em != null) {
					String regex = "[(|)]";
					String replacement = "";
					emText = em.ownText().replaceAll(regex, replacement);
					emHtml= em.html();
					doc = Jsoup.parse(emHtml);
					Element code2 = doc.select("code").first();
					if (code2 != null) {

						code2Text = code2.ownText();
					}
				}



				String text = map.ownText().replaceAll("(- )|(: )", "");
				String codeText = code.ownText();
				System.out.println("=============="  );
				System.out.println("mapHtml=" + mapHtml);
				System.out.println("text=" + text);
				System.out.println("code=" + codeText);
				System.out.println("em=" + emText +code2Text);
//				System.out.println("code2=" + code2Text);

				properties.put("code", codeText);
				properties.put("em", emText+code2Text);
//				properties.put("code2Text", code2Text);
				properties.put("text", text);

				Properties propertiesBean = new Properties();
				propertiesBean.setCode(codeText);
				propertiesBean.setEm(emText+code2Text);
				propertiesBean.setText(text);

				excelDataList.add(properties);
				excelDataList2.add(propertiesBean);

			}

			System.out.println("excelDataList="+excelDataList);
			System.out.println("excelDataList2="+excelDataList2);

			//			File input = new File("/tmp/input.html");
			//			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(FILE_HEADER.toString());

			for (Properties properties2 : excelDataList2) {
				fileWriter.append(properties2.getCode());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(properties2.getEm());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(properties2.getText());
				fileWriter.append(NEW_LINE_SEPARATOR);

			}

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
