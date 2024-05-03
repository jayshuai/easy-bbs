package org.example.entity.enums;

public enum PageSizeEnum {
    PAGE_SIZE_10(15), PAGE_SIZE_15(15), PAGE_SIZE_20(20);
    final int size;

    PageSizeEnum(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
