package ssm.handle;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import ssm.bean.Student;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.io.Serializable;

public class JMSProducer {
    //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    //发送的消息数量
    private static final int SENDNUM = 1;


    public static void producerMessages(Student student) {
        //连接工厂
        ConnectionFactory connectionFactory;
        //连接
        Connection connection = null;
        //会话,接收或者发送消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKERURL);
        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个名称为Hello World!的消息队列
            destination = session.createQueue("student");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //发送消息
            sendMessage(session,messageProducer,student);

            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 发送消息
     * @param session
     * @param messageProducer 消息生产者
     * @throws Exception
     */
    public static void sendMessage(Session session,MessageProducer messageProducer, Student student) throws Exception{
        for (int i = 0; i < JMSProducer.SENDNUM; i++) {
            //创建一条文本消息
            ActiveMQObjectMessage msg = (ActiveMQObjectMessage) session.createObjectMessage();
            msg.setObject((Serializable) student);
            // TextMessage message = session.createTextMessage("activemq 发送消息:" + Text);
            System.err.println(student.getStudentName()+"发送消息:activemq 发送消息:" + i);
            //通过消息生产者发出消息
            messageProducer.send(msg);
        }
    }
}
