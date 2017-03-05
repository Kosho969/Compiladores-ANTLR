import java.nio.charset.Charset;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class antlrIDE
{
	public static void main(String[] args)
	{

		TabbedPanel panel = new TabbedPanel();
	
		// TODO: Remove this stuff
		try {
			panel.areaTest.setText(antlrIDE.readFile("tests/1-F3.txt"));
			panel.tabbedPane.setSelectedIndex(2);

			// If success, exit
			if (0 == panel.readToTree()) {
				//System.exit(0);
			}
		} catch (IOException e) {
		}
	}

  	static String readFile(String path) throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, StandardCharsets.UTF_8);
	}
}
