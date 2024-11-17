package com.yushu.exercise36;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

class CalendarPane extends BorderPane {
    private String[] monthName = new String[]{"January", "Feburary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private Label lblHeader = new Label();
    private Label[] lblDay = new Label[49];
    private Calendar calendar;
    private int month;
    private int year;

    public CalendarPane() {
        for(int i = 0; i < 49; ++i) {
            this.lblDay[i] = new Label();
            this.lblDay[i].setTextAlignment(TextAlignment.RIGHT);
        }

        this.lblDay[0].setText("Sunday");
        this.lblDay[1].setText("Monday");
        this.lblDay[2].setText("Tuesday");
        this.lblDay[3].setText("Wednesday");
        this.lblDay[4].setText("Thursday");
        this.lblDay[5].setText("Friday");
        this.lblDay[6].setText("Saturday");
        GridPane dayPane = new GridPane();
        dayPane.setAlignment(Pos.CENTER);
        dayPane.setHgap(10.0);
        dayPane.setVgap(10.0);

        for(int i = 0; i < 49; ++i) {
            dayPane.add(this.lblDay[i], i % 7, i / 7);
        }

        this.setTop(this.lblHeader);
        BorderPane.setAlignment(this.lblHeader, Pos.CENTER);
        this.setCenter(dayPane);
        this.calendar = new GregorianCalendar();
        this.month = this.calendar.get(2);
        this.year = this.calendar.get(1);
        this.updateCalendar();
        this.showHeader();
        this.showDays();
    }

    public void showHeader() {
        this.lblHeader.setText(this.monthName[this.month] + ", " + this.year);
    }

    public void showDays() {
        int startingDayOfMonth = this.calendar.get(7);
        Calendar cloneCalendar = (Calendar)this.calendar.clone();
        cloneCalendar.add(5, -1);
        int daysInPrecedingMonth = cloneCalendar.getActualMaximum(5);

        int daysInCurrentMonth;
        for(daysInCurrentMonth = 0; daysInCurrentMonth < startingDayOfMonth - 1; ++daysInCurrentMonth) {
            this.lblDay[daysInCurrentMonth + 7].setTextFill(Color.LIGHTGRAY);
            this.lblDay[daysInCurrentMonth + 7].setText(String.valueOf(daysInPrecedingMonth - startingDayOfMonth + 2 + daysInCurrentMonth));
        }

        daysInCurrentMonth = this.calendar.getActualMaximum(5);

        int j;
        for(j = 1; j <= daysInCurrentMonth; ++j) {
            this.lblDay[j - 2 + startingDayOfMonth + 7].setTextFill(Color.BLACK);
            this.lblDay[j - 2 + startingDayOfMonth + 7].setText(String.valueOf(j));
        }

        j = 1;

        for(int i = daysInCurrentMonth - 1 + startingDayOfMonth + 7; i < 49; ++i) {
            this.lblDay[i].setTextFill(Color.LIGHTGRAY);
            this.lblDay[i].setText(String.valueOf(j++));
        }

    }

    public void updateCalendar() {
        this.calendar.set(1, this.year);
        this.calendar.set(2, this.month);
        this.calendar.set(5, 1);
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int newMonth) {
        this.month = newMonth;
        this.updateCalendar();
        this.showHeader();
        this.showDays();
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int newYear) {
        this.year = newYear;
        this.updateCalendar();
        this.showHeader();
        this.showDays();
    }
}
