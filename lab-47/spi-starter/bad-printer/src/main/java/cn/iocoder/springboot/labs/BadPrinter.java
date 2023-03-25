package cn.iocoder.springboot.labs;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author Soxhwhat
 * @date 2023/3/25 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class BadPrinter implements Printer{

        @Override
        public void print() {
            System.out.println("Bad Printer");
        }
}
