package com.gene.IM.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author will
 * @email zq2599@gmail.com
 * @date 2021/10/17 5:10 下午
 * @description 存数据的bean
 */
@Data
@AllArgsConstructor
public class ObjectDetectionResult {
    // 类别索引
    int classId;
    // 类别名称
    String className;
    // 置信度
    float confidence;
    // 物体在照片中的横坐标
    int x;
    // 物体在照片中的纵坐标
    int y;
    // 物体宽度
    int width;
    // 物体高度
    int height;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
