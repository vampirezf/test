package designPatterns.factoryMethod;


public class FactoryTest {
    public static void main(String[] args) {
        SendFactory sendFactory = new SendFactory();
        Sender produce = sendFactory.produce("mail");
        produce.send();
    }
}
