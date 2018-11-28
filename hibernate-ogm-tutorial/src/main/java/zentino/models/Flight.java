package zentino.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Flight")
public class Flight implements Serializable {
    @Id
    private Long F_ID;
    @Column
    private String F_AIRPLANE_TYPE;
    @Column
    private Date F_DEPARTURE;
    @Column
    private Date F_ARRIVAL;
    @Column
    private Integer F_EC_SEATS;
    @Column
    private Double F_EC_PRICE;
    @Column
    private Integer F_FC_SEATS;
    @Column
    private Double F_FC_PRICE;

    @OneToMany(mappedBy = "flight")
    private List<Flightsegment> flightsegments = new ArrayList<>();

    @ManyToMany(mappedBy = "bookedFlights")
    private List<MyCustomer> customers = new ArrayList<>();


    public Flight() {}

    public String toString(){
        return "Flight{" +
                "F_ID=" + F_ID +
                ", F_AIRPLANE_TYPE='" + F_AIRPLANE_TYPE + '\'' +
                ", F_DEPARTURE='" + F_DEPARTURE + '\'' +
                ", F_ARRIVAL='" + F_ARRIVAL + '\'' +
                ", F_EC_SEATS='" + F_EC_SEATS + "\'" +
                ", F_EC_PRICE='" + F_EC_PRICE + '\'' +
                ", F_FC_SEATS='" + F_FC_SEATS + "\'" +
                ", F_FC_PRICE='" + F_FC_PRICE + '\'' +
                '}';
    }

    public Long getF_ID() {
        return F_ID;
    }

    public void setF_ID(Long f_ID) {
        F_ID = f_ID;
    }

    public String getF_AIRPLANE_TYPE() {
        return F_AIRPLANE_TYPE;
    }

    public void setF_AIRPLANE_TYPE(String f_AIRPLANE_TYPE) {
        F_AIRPLANE_TYPE = f_AIRPLANE_TYPE;
    }

    public Date getF_DEPARTURE() {
        return F_DEPARTURE;
    }

    public void setF_DEPARTURE(Date f_DEPARTURE) {
        F_DEPARTURE = f_DEPARTURE;
    }

    public Date getF_ARRIVAL() {
        return F_ARRIVAL;
    }

    public void setF_ARRIVAL(Date f_ARRIVAL) {
        F_ARRIVAL = f_ARRIVAL;
    }

    public Integer getF_EC_SEATS() {
        return F_EC_SEATS;
    }

    public void setF_EC_SEATS(Integer f_EC_SEATS) {
        F_EC_SEATS = f_EC_SEATS;
    }

    public Double getF_EC_PRICE() {
        return F_EC_PRICE;
    }

    public void setF_EC_PRICE(Double f_EC_PRICE) {
        F_EC_PRICE = f_EC_PRICE;
    }

    public Integer getF_FC_SEATS() {
        return F_FC_SEATS;
    }

    public void setF_FC_SEATS(Integer f_FC_SEATS) {
        F_FC_SEATS = f_FC_SEATS;
    }

    public Double getF_FC_PRICE() {
        return F_FC_PRICE;
    }

    public void setF_FC_PRICE(Double f_FC_PRICE) {
        F_FC_PRICE = f_FC_PRICE;
    }

    public List<Flightsegment> getFlightsegments() {
        return flightsegments;
    }

    public void setFlightsegments(List<Flightsegment> flightsegments) {
        this.flightsegments = flightsegments;
    }

    public List<MyCustomer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<MyCustomer> customers) {
        this.customers = customers;
    }

}
