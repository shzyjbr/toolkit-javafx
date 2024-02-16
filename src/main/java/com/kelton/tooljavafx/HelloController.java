package com.kelton.tooljavafx;

import com.kelton.tooljavafx.util.StringUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private TextField urlField;

    @FXML
    private ComboBox<String> httpMethodSelector;

    @FXML
    private ComboBox<String> cookieSelector;

    @FXML
    private VBox paramContainer;
    @FXML
    private VBox headersContainer;

    @FXML
    private HBox bodyContainer;

    @FXML
    private HBox paramPane;

    @FXML
    private TextArea bodyArea;

    @FXML
    private VBox cookiePair;

    @FXML
    private HBox cookiePairContainer;

    @FXML
    private TextArea cookieArea;

    @FXML
    private HBox cookieAreaContainer;


    @FXML
    private Button genBtn;

    @FXML
    private Button copyBtn;

    @FXML
    private TextArea retArea;


    @FXML
    void initialize() {
        ObservableList<String> items = httpMethodSelector.getItems();
        items.add("get");
        items.add("post");
        items.add("put");
        items.add("delete");
        httpMethodSelector.setValue("get");

//        ObservableList<String> contentTypeSelectorItems = contentTypeSelector.getItems();
//        contentTypeSelectorItems.add("application/json; charset=UTF-8");
//        contentTypeSelectorItems.add("text/html; charset=utf-8");
//        contentTypeSelectorItems.add("multipart/form-data");
//        contentTypeSelectorItems.add("text/plain");


        ObservableList<String> cookieSelectorItems = cookieSelector.getItems();
        cookieSelectorItems.add("key-value");
        cookieSelectorItems.add("text");
        cookieSelectorItems.add("file");
        cookieSelector.setValue("key-value"); // 将“选项1”设置为默认选项


// 获取comboBox的值
//        String selectedItem = httpMethodSelector.getSelectionModel().getSelectedItem();

        ObservableList<Node> paramItems = paramContainer.getChildren();
        for (int i = 0; i < 2; i++) {
            paramItems.add(new KeyValueItem(i, paramContainer));
        }

        ObservableList<Node> headersItems = headersContainer.getChildren();
        for (int i = 0; i < 2; i++) {
            headersItems.add(new KeyValueItem(i, headersContainer));
        }
        ObservableList<Node> cookiesItems = cookiePair.getChildren();
        for (int i = 0; i < 1; i++) {
            cookiesItems.add(new KeyValueItem(i, cookiePair));
        }

        httpMethodSelector.setOnAction(event -> {
            String selectedChoice = httpMethodSelector.getValue();
            System.out.println("Selected Choice: " + selectedChoice);
            showHttpMethodArea(selectedChoice);
        });

        cookieSelector.setOnAction(e -> {
            String selectedChoice = cookieSelector.getValue();
            System.out.println("cookieSelector Choice: " + selectedChoice);
            showCookieArea(selectedChoice);
        });


        genBtn.setOnMouseClicked(e -> {
            System.out.println("gen！");

            StringBuilder ret = new StringBuilder("curl");
            concateHttpMethod(ret, httpMethodSelector.getValue());
            concateHttpParam(ret, httpMethodSelector.getValue());
            concatHeader(ret);

            concateUrl(ret, urlField.getText());
            retArea.setText(ret.toString());

        });

        copyBtn.setOnMouseClicked( e-> {
            String retAreaText = retArea.getText();
            Clipboard systemClipboard = Clipboard.getSystemClipboard();
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(retAreaText);
            systemClipboard.setContent(clipboardContent);
        });


    }

    private void concatHeader(StringBuilder ret) {
        ObservableList<Node> children = headersContainer.getChildren();
        List<KeyValueItem.KeyValue> keyValues = new ArrayList<>();
        children.forEach( e-> {
            KeyValueItem keyValueItem = (KeyValueItem) e;
            String key = keyValueItem.getKeyString();
            String value = keyValueItem.getValueString();
            if (!StringUtil.empty(key) && !StringUtil.empty(value)) {
                keyValues.add(new KeyValueItem.KeyValue(key, value));
            }
        });
        for (KeyValueItem.KeyValue kv : keyValues) {
            ret.append(" -H ");
            ret.append(String.format("\"%s: %s\"", kv.getKey(), kv.getValue()));
        }
    }

    private void concateUrl(StringBuilder ret, String text) {
        ret.append(" " + "\"").append(text).append("\"");
    }

    private void concateHttpParam(StringBuilder ret, String value) {
        switch (value) {
            case "get" -> {
                ret.append(" -G");
                ObservableList<Node> children = paramContainer.getChildren();
                // 处理每一个节点
                children.forEach(n -> {
                    KeyValueItem keyValueItem = (KeyValueItem) n;
                    String keyString = keyValueItem.getKeyString();
                    String valueString = keyValueItem.getValueString();
                    if (!StringUtil.empty(keyString) && !StringUtil.empty(valueString)) {
                        ret.append(" -d \"" + keyString + "=" + valueString + "\"");
                    }
                });
            }
            case "post" -> {
                ret.append(" -d ");
                String text = bodyArea.getText();
                String replaced = text.replaceAll("[\\r\\n\\s]+", "");
                String s = replaced.replace("\"", "\\\"");
                ret.append(String.format("\"%s\"", s));
            }
        }
    }

    private void concateHttpMethod(StringBuilder ret, String value) {
        ret.append(" -X");
        switch (value) {
            case "get" -> ret.append(" get");
            case "post" -> ret.append(" post");
            case "put" -> ret.append(" put");
            case "delete" -> ret.append(" delete");
            default -> ret.append(" get");
        }
    }

    private void showHttpMethodArea(String selectedChoice) {

        switch (selectedChoice) {
            case "get":
                paramPane.setVisible(true);
                bodyContainer.setVisible(false);
                break;
            case "post":
                paramPane.setVisible(false);
                bodyContainer.setVisible(true);
                break;
            case "put":
                break;
            case "delete":
                break;
            default:
                break;
        }
    }

    private void showCookieArea(String selectedChoice) {
        switch (selectedChoice) {
            case "key-value":
            case "file":
                cookiePairContainer.setVisible(true);
                cookieAreaContainer.setVisible(false);
                break;
            case "text":
                cookiePairContainer.setVisible(false);
                cookieAreaContainer.setVisible(true);
                break;
            default:
                cookiePairContainer.setVisible(true);
                cookieAreaContainer.setVisible(false);
                break;
        }
    }


}