package ssm.util;

/**
 * 生成排序字符串
 */
public class SequenceUtil {

    /**
     * 按照当前时间生成排序字段
     * <p>
     * 单位1s，默认精度100ms
     *
     * @return 保留到小数点后1位
     */
    public static double next() {
        long now = (System.currentTimeMillis() / 100) * 100; // 精确到100ms
        return (double) now / 1000;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 12; i++) {
            System.out.println(next());
            Thread.sleep(1000);
        }
    }
}
