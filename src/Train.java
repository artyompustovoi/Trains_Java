import java.time.LocalDateTime;

record Train(long number, String from, String to, LocalDateTime departure, LocalDateTime arrival) {
    public LocalDateTime getDeparture() {
        return departure;
    }
    public LocalDateTime getArrival() {
        return arrival;
    }
    public String GetDepartureTown() {
        return from;
    }

    @Override
    public String toString() {
        return "Train " + number + " {from: " + from + "; to: " + to + "; depart: " + departure + "; arrive: " + arrival + "}";
    }
}