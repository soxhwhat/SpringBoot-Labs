package cn.iocoder.springboot.labs;

public interface Printer {
    void print();

    /**
     * 这里需要重点说明，每一个SPI接口都需要在自己项目的静态资源目录中声明一个services文件，文件名为实现规范接口的类名全路径，在此例中便是moe.cnkirito.spi.api.Printer，在文件中，则写上一行具体实现类的全路径，在此例中便是moe.cnkirito.spi.api.GoodPrinter。
     */
}
