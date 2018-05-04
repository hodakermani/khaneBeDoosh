package khaneBeDoosh.domain;

public class Price {
    private int sellPrice;
    private int basePrice;
    private int rentPrice;

    public Price(){}

    public Price(int _sellPrice) {
        this.sellPrice = _sellPrice;
    }

    public Price(int _basePrice, int _rentPrice) {
        this.basePrice = _basePrice;
        this.rentPrice = _rentPrice;
    }

    public int getSellPrice() { return sellPrice; }

    public int getBasePrice() {
        return basePrice;
    }

    public int getRentPrice() {
        return rentPrice;
    }
}
