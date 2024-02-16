package com.kelton.tooljavafx;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * @Author zhouzekun
 * @Date 2024/2/14 20:18
 */
public class SvgComponent extends StackPane {
    protected Region region;

    public SvgComponent(String svgString, int width, int height) {
        // 创建一个SVG对象
        SVGPath svg = new SVGPath();
        svg.setContent(svgString);
        // 根据给定的宽度和高度来调整SVG图像的大小
        svg.setScaleX(width / svg.getBoundsInLocal().getWidth());
        svg.setScaleY(height / svg.getBoundsInLocal().getHeight());

        // 创建一个Region，设置SVG为其形状
        region = new Region();
        region.setShape(svg);
        region.setMinSize(width, height);
        region.setPrefSize(width, height);
        region.setMaxSize(width, height);
        region.setStyle("-fx-background-color: black;");

        this.setMinSize(width+5, height+5);
        this.setPrefSize(width+5, height+5);
        this.setMaxSize(width+5, height+5);

        this.setStyle(this.getStyle() +"-fx-background-radius: 5;");

        // 将Region添加到StackPane中
        this.getChildren().add(region);
    }
}
