package com.example.weathercard;

public enum CellType {

    ToDay(1),
    Forecast(2),
    Footer(3);



    private int cellNumber;

    CellType(int cellNumber) {
        this.cellNumber = cellNumber;
    }

    public int getCellNumber() {
        return  this.cellNumber;
    }

//    CellType(String text) {
//        this.text = text;
//    }
//
//    public String getText() {
//        return this.text;
//    }
//
//    public static Blah fromString(String text) {
//        for (Blah b : Blah.values()) {
//            if (b.text.equalsIgnoreCase(text)) {
//                return b;
//            }
//        }
//        return null;
//    }
}
