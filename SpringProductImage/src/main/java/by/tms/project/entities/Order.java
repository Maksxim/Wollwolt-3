package by.tms.project.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private LocalDateTime dateTime;
    private List<Item> items = new ArrayList<>();
    private boolean finished = false;

    public Order(int id, LocalDateTime dateTime, List<Item> items, boolean finished) {
        this.id = id;
        this.dateTime = dateTime;
        this.items = items;
        this.finished = finished;
    }

    public Order() {
        this.dateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return " Order{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", items=" + items +
                ", finished=" + finished +
                '}';
    }
}
