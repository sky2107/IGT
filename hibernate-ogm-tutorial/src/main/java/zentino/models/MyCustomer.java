package zentino.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Mycustomer")
public class MyCustomer implements Serializable {
    @Id
    private Long C_ID;
    @Column
    private Integer C_ADDR_ID;
    @Column
    private String C_STATUS;
    @Column
    private Double C_FLOWN_MILES;
    @Column
    private Double C_CURRENT_FLOWN_MILES;
    @Column
    private String C_PHONE;



    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "booked_flights",
            joinColumns = { @JoinColumn(name = "C_ID") },
            inverseJoinColumns = { @JoinColumn(name = "F_ID") }
    )
    List<Flight> bookedFlights = new ArrayList<>();

    public MyCustomer() {}

    @Override
    public String toString() {
        return "Customer{" +
                "C_ID=" + C_ID +
                ", C_ADRESS='" + C_ADDR_ID + '\'' +
                ", C_PHONE='" + C_PHONE + '\'' +
                ", C_STATUS='" + C_STATUS + '\'' +
                ", C_FLOWN_MILES='" + C_FLOWN_MILES + "\'" +
                ", C_CURRENT_FLOWN_MILES='" + C_CURRENT_FLOWN_MILES + '\'' +
                '}';
    }

    public Long getC_ID() {
        return C_ID;
    }

    public void setC_ID(Long c_ID) {
        C_ID = c_ID;
    }

    public Integer getC_ADDR_ID() {
        return C_ADDR_ID;
    }

    public void setC_ADDR_ID(Integer c_ADDR_ID) {
        C_ADDR_ID = c_ADDR_ID;
    }

    public String getC_STATUS() {
        return C_STATUS;
    }

    public void setC_STATUS(String c_STATUS) {
        C_STATUS = c_STATUS;
    }

    public Double getC_FLOWN_MILES() {
        return C_FLOWN_MILES;
    }

    public void setC_FLOWN_MILES(Double c_FLOWN_MILES) {
        C_FLOWN_MILES = c_FLOWN_MILES;
    }

    public Double getC_CURRENT_FLOWN_MILES() {
        return C_CURRENT_FLOWN_MILES;
    }

    public void setC_CURRENT_FLOWN_MILES(Double c_CURRENT_FLOWN_MILES) {
        C_CURRENT_FLOWN_MILES = c_CURRENT_FLOWN_MILES;
    }

    public String getC_PHONE() {
        return C_PHONE;
    }

    public void setC_PHONE(String c_PHONE) {
        C_PHONE = c_PHONE;
    }

    public List<Flight> getBookedFlights() {
        return bookedFlights;
    }

    public void setBookedFlights(List<Flight> bookedFlights) {
        this.bookedFlights = bookedFlights;
    }

}
