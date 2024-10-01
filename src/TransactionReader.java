import org.json.JSONArray;
import org.json.JSONObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TransactionReader {

    public List<Transaction> readCSV(String filePath) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                transactions.add(new Transaction(values[0], Double.parseDouble(values[1]), values[2]));
            }
        }
        return transactions;
    }

    public List<Transaction> readJSON(String filePath) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONArray jsonArray = new JSONArray(content);
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            transactions.add(new Transaction(jsonObject.getString("date"),
                    jsonObject.getDouble("amount"),
                    jsonObject.getString("description")));
        }
        return transactions;
    }

    public List<Transaction> readXML(String filePath) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(filePath);
        NodeList nodeList = doc.getElementsByTagName("transaction");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String date = element.getElementsByTagName("date").item(0).getTextContent();
            double amount = Double.parseDouble(element.getElementsByTagName("amount").item(0).getTextContent());
            String description = element.getElementsByTagName("description").item(0).getTextContent();
            transactions.add(new Transaction(date, amount, description));
        }
        return transactions;
    }
}


