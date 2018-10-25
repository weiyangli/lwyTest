package ssm.handle;

import static java.lang.Thread.sleep;

public class Run implements Runnable{
    private int num = 20;
    static Object sy = 1;
    private String name = null;
    public Run(String name){
        this.name = name;
    }
    @Override
    public void run() {
        // 当前使用线程同步，可能出现同一位谷歌购买同一张票
        synchronized (sy) {
            System.out.println("顾客"+this.name+"买走了第"+num+"张票");
            num--;
            try{
                if (num <=0) {
                    // 票不足等待下一次放票
                    wait();
                } else {
                    // 等上一个买完
                    sleep(1000);
                }
//                if (num > 0) {
//                    notify();
//                }
            }catch (Exception e) {
                System.out.println("线程休眠出错!");
            }
        }
    }

}

