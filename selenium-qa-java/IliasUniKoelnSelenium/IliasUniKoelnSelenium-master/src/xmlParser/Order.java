package xmlParser;

/**
 * Created by EPC-Nils on 03.05.2016.
 */
public class Order {
    int orderPosition;
    String orderName;

    public int getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Order(int orderPosition, String orderName) {
        this.orderPosition = orderPosition;
        this.orderName = orderName;
    }
}
