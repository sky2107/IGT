package zentino.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Flightsegment")
public class Flightsegment implements Serializable {
    @Id
    private String FS_NAME_ID;
    @ManyToOne
    @JoinColumn(name="A_ID", nullable=false)
    private Airport FS_START_AIRPORT;
    @ManyToOne
    @JoinColumn(name="A_ID", nullable=false)
    private Airport FS_GOAL_AIRPORT;
    @Column
    private Double FS_DISTANCE;
    @ManyToOne
    @JoinColumn(name="FS_ID", nullable=false)
    private Flight flight;


    public Flightsegment() {}

    public String toString(){
        return "Flightsegment{" +
                "FS_NAME_ID=" + FS_NAME_ID +
                ", FS_START_AIRPORT='" + FS_START_AIRPORT + '\'' +
                ", FS_GOAL_AIRPORT='" + FS_GOAL_AIRPORT + '\'' +
                ", FS_DISTANCE='" + FS_DISTANCE + '\'' +
                '}';
    }

    public String getFS_NAME_ID() {
        return FS_NAME_ID;
    }

    public void setFS_NAME_ID(String FS_NAME_ID) {
        this.FS_NAME_ID = FS_NAME_ID;
    }

    public Airport getFS_START_AIRPORT() {
        return FS_START_AIRPORT;
    }

    public void setFS_START_AIRPORT(Airport FS_START_AIRPORT) {
        this.FS_START_AIRPORT = FS_START_AIRPORT;
    }

    public Airport getFS_GOAL_AIRPORT() {
        return FS_GOAL_AIRPORT;
    }

    public void setFS_GOAL_AIRPORT(Airport FS_GOAL_AIRPORT) {
        this.FS_GOAL_AIRPORT = FS_GOAL_AIRPORT;
    }

    public Double getFS_DISTANCE() {
        return FS_DISTANCE;
    }

    public void setFS_DISTANCE(Double FS_DISTANCE) {
        this.FS_DISTANCE = FS_DISTANCE;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}

