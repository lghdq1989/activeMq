

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.jms.MessageProducer;
    /**
     * Created by asus on 2018/5/29.
     */
    public class Main {
        private static String USERNAME = ActiveMQConnection.DEFAULT_USER;
        private static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
        private static String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;
        private static Integer SENDNUM = 10;

        public static void main(String[] args)throws Exception {
            ConnectionFactory connectionFactory;
            Connection connection;
            Session session;
            Destination destination;
            MessageProducer messageProducer=null;
            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.142.2:61616");
            //1建立起工厂
            // 第一个参数是是否是事务型消息，设置为true,第二个参数无效
            //第二个参数是
            //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
            //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
            //时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
            //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
            connection = connectionFactory.createConnection();
            connection.start();
            //开2启  connection连接方式
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            //3创建session 是否启动事务，后面是自动模式
            destination = session.createQueue("MyQueue1");// Create message
            // 4queue
            messageProducer = session.createProducer(destination);// Create
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//5是否存储

            for(int i=0;i<5;i++) {
                TextMessage message = session.createTextMessage();
                message.setText("MQ");
                messageProducer.send(message);
            }
            if(connection!=null){
                connection.close();
            }
        }


    }
