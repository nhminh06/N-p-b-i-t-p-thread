package com.app;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XMLHandler {
    private static final String FILE_PATH = "data.xml";

    @FXML private TextField txtTagName;
    @FXML private TextArea txtContent;
    @FXML private TextArea txtDisplay;

    @FXML
    public void handleAdd() {
        try {
            String tagName = txtTagName.getText().trim();
            String content = txtContent.getText().trim();

            if (tagName.isEmpty() || content.isEmpty()) {
                txtDisplay.setText(" Vui lòng nhập đầy đủ tên thẻ và nội dung!");
                return;
            }

            addXMLElement(tagName, content);
            txtDisplay.setText(" Đã thêm thẻ: <" + tagName + ">" + content + "</" + tagName + ">");
        } catch (Exception e) {
            txtDisplay.setText(" Lỗi khi thêm thẻ XML!");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEdit() {
        try {
            String tagName = txtTagName.getText().trim();
            String newContent = txtContent.getText().trim();

            if (tagName.isEmpty() || newContent.isEmpty()) {
                txtDisplay.setText(" Vui lòng nhập đầy đủ tên thẻ và nội dung mới!");
                return;
            }

            updateXMLElement(tagName, newContent);
            txtDisplay.setText(" Đã cập nhật nội dung thẻ: <" + tagName + "> thành \"" + newContent + "\"");
        } catch (Exception e) {
            txtDisplay.setText("Lỗi khi sửa thẻ XML!");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        try {
            String tagName = txtTagName.getText().trim();

            if (tagName.isEmpty()) {
                txtDisplay.setText(" Vui lòng nhập tên thẻ cần xóa!");
                return;
            }

            deleteXMLElement(tagName);
            txtDisplay.setText(" Đã xóa thẻ: <" + tagName + ">");
        } catch (Exception e) {
            txtDisplay.setText(" Lỗi khi xóa thẻ XML!");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMerge() {
        try {
            List<String> fileList = new ArrayList<>();
            fileList.add("file1.xml");
            fileList.add("file2.xml");

            mergeXMLFiles(fileList, false);
            txtDisplay.setText(" Đã ghép các file XML thành `merged.xml`!");
        } catch (Exception e) {
            txtDisplay.setText(" Lỗi khi ghép file XML!");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDisplay() {
        try {
            String content = readXMLFile(FILE_PATH);
            txtDisplay.setText(content.isEmpty() ? " (Không có nội dung XML để hiển thị)" : content);
        } catch (Exception e) {
            txtDisplay.setText(" Lỗi khi đọc file XML!");
            e.printStackTrace();
        }
    }

    public static void addXMLElement(String tagName, String content) throws Exception {
        Document doc = getOrCreateDocument();
        Element root = doc.getDocumentElement();

        Element newElement = doc.createElement(tagName);
        newElement.setTextContent(content);
        root.appendChild(newElement);

        saveDocument(doc);
    }

    public static void updateXMLElement(String tagName, String newContent) throws Exception {
        Document doc = getOrCreateDocument();
        NodeList elements = doc.getElementsByTagName(tagName);

        if (elements.getLength() > 0) {
            elements.item(0).setTextContent(newContent);
            saveDocument(doc);
        }
    }

    public static void deleteXMLElement(String tagName) throws Exception {
        Document doc = getOrCreateDocument();
        NodeList elements = doc.getElementsByTagName(tagName);

        if (elements.getLength() > 0) {
            Node node = elements.item(0);
            node.getParentNode().removeChild(node);
            saveDocument(doc);
        }
    }

    public static void mergeXMLFiles(List<String> filePaths, boolean nested) throws Exception {
        Document mainDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = mainDoc.createElement("MergedXML");
        mainDoc.appendChild(root);

        for (String filePath : filePaths) {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
            doc.getDocumentElement().normalize();

            if (nested) {
                Element nestedElement = mainDoc.createElement("File");
                nestedElement.setAttribute("source", filePath);
                nestedElement.appendChild(mainDoc.importNode(doc.getDocumentElement(), true));
                root.appendChild(nestedElement);
            } else {
                Node importedNode = mainDoc.importNode(doc.getDocumentElement(), true);
                root.appendChild(importedNode);
            }
        }

        saveDocument(mainDoc, "merged.xml");
    }

    public static String readXMLFile(String filePath) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
        doc.getDocumentElement().normalize();

        StringBuilder result = new StringBuilder();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                result.append("<").append(node.getNodeName()).append(">");
                result.append(node.getTextContent());
                result.append("</").append(node.getNodeName()).append(">\n");
            }
        }

        return result.toString();
    }

    private static Document getOrCreateDocument() throws Exception {
        File file = new File(FILE_PATH);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        if (!file.exists()) {
            Document doc = builder.newDocument();
            Element root = doc.createElement("Root");
            doc.appendChild(root);
            saveDocument(doc);
            return doc;
        }
        return builder.parse(file);
    }

    private static void saveDocument(Document doc) throws Exception {
        saveDocument(doc, FILE_PATH);
    }

    private static void saveDocument(Document doc, String filePath) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}
