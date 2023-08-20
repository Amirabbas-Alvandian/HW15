package example.entity;
public enum Courses {
    MATH(3),
    CHEMISTRY(2),
    PHYSICS(2),
    PROGRAMMING(3),
    LITERATURE(1),
    GORAZ_SHENASI(1);



    private final int unit;

    private Courses (int unit){
        this.unit = unit;
    }

    public int getUnit() {
        return this.unit;
    }
}
