package ssm.handle;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;

/**
 * Zookeeper
 */
public class ZookeeperUtils{

    private	Logger logger = Logger.getLogger(this.getClass());
    //创建一个watcher对象,当我们对Zookeeper进行修改的时候都会触发该事件
    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            logger.debug("--------ZK Watcher---------");
        }
    };

    //初始化Zookeeper
    private ZooKeeper zooKeeper ;

    public void zookeeperInit() throws IOException {
        //多个ip使用，号分割
        zooKeeper = new ZooKeeper("192.168.10.158:2181", 20000 , watcher);
    }

    public void ZKOption() throws Exception, InterruptedException {
        logger.debug("-----ZK Option-----");
        zooKeeper.delete("/zoo1", -1);
        /*Properties prop = new Properties();
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        prop.load(ins);
        String driverName = prop.getProperty("database.driver");
        String dbURL = prop.getProperty("database.url");
        String userName = prop.getProperty("database.username");
        String userPwd = prop.getProperty("database.password");
        System.out.println(dbURL);
        ins.close();
        zooKeeper.create("/helloZookeeper/mybatis-config", dbURL.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.setData("/helloZookeeper/mybatis-config", dbURL.getBytes(),0);*/
        /*//添加watcher ，对zoo1进行监控变化-->获取数据 ,这个监控只会对下次操作节点起到作用
        zooKeeper.getData("/zoo1", this.watcher, null);
        //修改zoo1节点
        zooKeeper.setData("/zoo1", "hello".getBytes(), -1);
        //----------------------获取数据----------------------------
        logger.debug("获取第一次节点信息："+JSON.toJSONString(zooKeeper.getData("/zoo1", this.watcher, null)));
        System.out.println("获取第二次数据信息："+JSON.toJSONString(zooKeeper.getData("/zoo1", this.watcher, null)));
        //-----------------这次操作的节点不会进行监控-----------------------
        zooKeeper.setData("/zoo1", "world".getBytes(), -1);
        //----------------------获取数据----------------------------
        logger.debug("获取第二次数据信息："+zooKeeper.getData("/zoo1", this.watcher, null));
        System.out.println("获取第二次数据信息："+JSON.toJSONString(zooKeeper.getData("/zoo1", this.watcher, null)));
        //查看节点状态
        logger.debug("节点状态："+zooKeeper.getState());
        System.out.println("节点状态："+zooKeeper.getState());
        //删除节点
        zooKeeper.delete("/zoo1", -1);
        System.out.println(zooKeeper.exists("/zoo1", false));*/
    }

    //关闭Zookeeper
    public void closeZookeeper() throws Exception {
        zooKeeper.close();
    }


    public static void main(String[] args) throws Exception {
        ZookeeperUtils utils = new ZookeeperUtils();
        utils.zookeeperInit();
        utils.ZKOption();
        utils.closeZookeeper();
    }

}
