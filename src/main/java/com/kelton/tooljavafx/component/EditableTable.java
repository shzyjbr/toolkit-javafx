package com.kelton.tooljavafx.component;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @Author kelton
 * @Date 2024/2/27 21:51
 * @Version 1.0
 */
public class EditableTable extends VBox {

    /** 初始行数 */
    public int rows;

    public EditableTable() {

        this(1);

    }

    public EditableTable(int rows) {
        super();
        this.rows = rows;
        ObservableList<Node> children = this.getChildren();
        for (int i = 0; i < rows; i++) {
            children.add(new KeyValueItem(i, this));
        }

        this.setMinHeight(50);
    }

}
