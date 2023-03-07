package de.qytera.qtaf.core.reflection.sample3;

public class Driver {
    private Automobile car;

    /**
     * Get car
     *
     * @return car
     */
    public Automobile getCar() {
        return car;
    }

    /**
     * Set car
     *
     * @param car Car
     * @return this
     */
    public Driver setCar(Automobile car) {
        this.car = car;
        return this;
    }

    public boolean drivesCar(FourWheels car) {
        return this.car.getClass().isInstance(car);
    }

    public boolean drivesCar(Automobile car) {
        return this.car.getClass().isInstance(car);
    }

    public boolean drivesCar(GermanCar car) {
        return this.car.getClass().isInstance(car);
    }

    public String sayCarName() {
        return "My car is a " + car;
    }
}
