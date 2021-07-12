package test.two;

import java.io.Serializable;


public class SomeOne implements Serializable {

    private String a;

    private String b;

    private String c;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public SomeOne(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    //重写clone()方法
    @Override
    protected Object clone() throws CloneNotSupportedException {
        SomeOne someOne = null;
        someOne = (SomeOne) super.clone();
        return someOne;
    }
}
