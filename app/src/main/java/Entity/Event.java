package Entity;

import java.util.Date;

public class Event {
    private Date date;
    private String description;
    private String title;

    public Event(Date date, String title, String description) {
        this.date = date;
        this.description = description;
        this.title = title;
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getDescription() { return description; }


    public void setDescription(String description) { this.description = description; }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

