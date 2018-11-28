package zentino.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Airports")
public class Airport {

    @Id
    private Long A_ID;
    @Column
    private String A_NAME;

    @Column
    private String A_ADDR;

    @OneToMany(mappedBy = "FS_GOAL_AIRPORT")
    private List<Flightsegment> goal = new ArrayList<>();

    @OneToMany(mappedBy = "FS_START_AIRPORT")
    private List<Flightsegment> start = new ArrayList<>();

    public Long getA_ID() {
        return A_ID;
    }
    public void setA_ID(Long a_ID) {
        A_ID = a_ID;
    }
    public String getA_NAME() {
        return A_NAME;
    }
    public void setA_NAME(String a_NAME) {
        A_NAME = a_NAME;
    }
    public List<Flightsegment> getFlightsegments() {
        return goal;
    }

    public void setFlightsegments(List<Flightsegment> flightsegments) {
        this.start = flightsegments;
    }
    public String getA_ADDR() {
        return A_ADDR;
    }

    public void setA_ADDR(String a_ADDR) {
        A_ADDR = a_ADDR;
    }
}
