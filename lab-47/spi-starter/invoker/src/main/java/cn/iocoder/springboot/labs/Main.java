package cn.iocoder.springboot.labs;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<Printer> printerLoader = ServiceLoader.load(Printer.class);
        for (Printer printer : printerLoader) {
            printer.print();
        }
    }
}