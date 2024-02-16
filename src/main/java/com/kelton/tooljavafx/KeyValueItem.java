package com.kelton.tooljavafx;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @Author zhouzekun
 * @Date 2024/2/14 17:16
 */
public class KeyValueItem extends HBox {

    private TextField keyField;
    private TextField valueField;

    private Label label;

    private SvgComponent delBtn;

    private SvgComponent addBtn;

    private int index;
    private VBox parentNode;


    public KeyValueItem(int index, VBox parentNode) {
        this.keyField = new TextField();
        this.valueField = new TextField();
        this.label = new Label("=");
        this.label.setMinWidth(30);
        this.label.setAlignment(Pos.CENTER);

        String addSvgPath = "M514.048 62.464q93.184 0 175.616 35.328t143.872 96.768 96.768 143.872 35.328 175.616q0 94.208-35.328 176.128t-96.768 143.36-143.872 96.768-175.616 35.328q-94.208 0-176.64-35.328t-143.872-96.768-96.768-143.36-35.328-176.128q0-93.184 35.328-175.616t96.768-143.872 143.872-96.768 176.64-35.328zM772.096 576.512q26.624 0 45.056-18.944t18.432-45.568-18.432-45.056-45.056-18.432l-192.512 0 0-192.512q0-26.624-18.944-45.568t-45.568-18.944-45.056 18.944-18.432 45.568l0 192.512-192.512 0q-26.624 0-45.056 18.432t-18.432 45.056 18.432 45.568 45.056 18.944l192.512 0 0 191.488q0 26.624 18.432 45.568t45.056 18.944 45.568-18.944 18.944-45.568l0-191.488 192.512 0z";
        String delSvgPath = "M512 85.333333c235.640936 0 426.666667 191.025731 426.666667 426.666667S747.640936 938.666667 512 938.666667 85.333333 747.640936 85.333333 512 276.359064 85.333333 512 85.333333z m0 85.333334c-188.513201 0-341.333333 152.820132-341.333333 341.333333s152.820132 341.333333 341.333333 341.333333 341.333333-152.820132 341.333333-341.333333-152.820132-341.333333-341.333333-341.333333z m256 298.666666c23.564433 0 42.666667 19.102234 42.666667 42.666667s-19.102234 42.666667-42.666667 42.666667h-512c-23.564433 0-42.666667-19.102234-42.666667-42.666667s19.102234-42.666667 42.666667-42.666667h512z";

        int btnSize = 15;
        this.addBtn = new SvgComponent(addSvgPath,btnSize,btnSize);

        this.delBtn = new SvgComponent(delSvgPath, btnSize, btnSize);

        this.index = index;
        this.parentNode = parentNode;

        keyField.setPromptText("key");
        valueField.setPromptText("value");

        addBtn.setOnMouseClicked(event -> {
            addParamItem(parentNode);
        });



        delBtn.setOnMouseClicked(event -> {
            delParamItem(parentNode, this);
        });

        // 添加鼠标悬停效果
        addBtn.setOnMouseEntered(e -> {
            addBtn.setStyle(addBtn.getStyle() +"-fx-background-color: #d4d1d1;");
            addBtn.region.setStyle("-fx-background-color: blue;");  // 鼠标悬停时，填充颜色变为蓝色
        });

        addBtn.setOnMouseExited(e -> {
            addBtn.setStyle(addBtn.getStyle() +"-fx-background-color: white;");
            addBtn.region.setStyle("-fx-background-color: black;"); // 鼠标移出时，填充颜色变回黑色
        });

        // 添加鼠标悬停效果
        delBtn.setOnMouseEntered(e -> {
            delBtn.setStyle(delBtn.getStyle() + "-fx-background-color: #d4d1d1;");
            delBtn.region.setStyle("-fx-background-color: red;");  // 鼠标悬停时，填充颜色变为蓝色
        });

        delBtn.setOnMouseExited(e -> {
            delBtn.setStyle(delBtn.getStyle() +"-fx-background-color: white;");
            delBtn.region.setStyle("-fx-background-color: black;"); // 鼠标移出时，填充颜色变回黑色
        });



        this.setSpacing(10.0);
        this.getChildren().addAll(keyField, label, valueField, delBtn, addBtn);


    }

    public void addParamItem(VBox parentNode) {

        ObservableList<Node> children = parentNode.getChildren();
        if (children.size() < 15) {
            children.add(new KeyValueItem(children.size(), parentNode));
        }
    }

    public void delParamItem(VBox parentNode, KeyValueItem cur) {
        ObservableList<Node> children = parentNode.getChildren();

        if (children.size() > 1) {

            children.remove(cur);
        }
    }


    public String getKeyString() {
        return keyField.getText();
    }

    public String getValueString() {
        return valueField.getText();
    }


    public static class KeyValue {
        private String key;
        private String value;

        public KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public KeyValue(){}

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
