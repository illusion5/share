package top.yangyl.mqtt.test;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author yangyanglei
 */
public class MqttTest {

    private static Logger logger= LoggerFactory.getLogger(MqttTest.class);

    private String url="tcp://127.0.0.1:1883";

    private MqttClient mqttClient;

    public void init() throws Exception {
        MqttConnectOptions options=new MqttConnectOptions();

        mqttClient=new MqttClient(url, UUID.randomUUID().toString());
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                logger.error("断开连接",cause);
                while (!mqttClient.isConnected()){
                    try {
                        Thread.sleep(2000);
                        mqttClient.connect(options);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                logger.info("topic为:{},message为:{}",topic,message!=null?message.toString():"");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {
                    logger.debug("token:{}",token!=null?token.getMessage():null);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        mqttClient.connect(options);
        int retryCount=10;
        for (int i=0;i<retryCount;i++){
            if(mqttClient.isConnected()){
                break;
            }
            mqttClient.connect(options);
        }
        if(!mqttClient.isConnected()){
            logger.error("连接未成功");
            throw new RuntimeException("连接未成功");
        }
    }

    public boolean isConnected(){
        return mqttClient.isConnected();
    }

    public void sendMessage(String topic,String message) throws MqttException {
        MqttMessage mqttMessage=new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttMessage.setQos(0);
        mqttMessage.setRetained(false);
        mqttClient.publish(topic,mqttMessage);
    }

    public static void main(String[] args) throws Exception {
        MqttTest mqttTest=new MqttTest();
        mqttTest.init();
        mqttTest.sendMessage("/wss","fhfeuhf");
        while (true){
            Thread.sleep(2000);
            if(!mqttTest.isConnected()){
                logger.error("连接断开");
            }else{
                mqttTest.sendMessage("/wss","fhfeuhf");
            }
        }

    }


}
