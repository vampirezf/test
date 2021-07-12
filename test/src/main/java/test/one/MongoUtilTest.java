package test.one;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MongoUtilTest {


    //需要密码认证方式连接
    public static MongoDatabase getConnect() throws Exception {
        MongoClient mongoClient = null;
        try {
            List<ServerAddress> adds = new ArrayList<>();
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress("localhost", 27777);
            adds.add(serverAddress);

            List<MongoCredential> credentials = new ArrayList<>();
            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("ex9", "ex9_1", "xylx1.t!@#".toCharArray());
            credentials.add(mongoCredential);

            //通过连接认证获取MongoDB连接
            mongoClient = new MongoClient(adds, credentials);
        } catch (Exception e) {
            throw new Exception("数据源获取错误！");
        }

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("ex9_1");

        //返回连接数据库对象
        return mongoDatabase;
    }

    @Test
    public void t1() throws Exception {


        List documentList = new ArrayList<>();

        MongoDatabase connect = getConnect();
        MongoCollection<Document> collection=null;
        try {
            collection = connect.getCollection("contentMongoEO");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BasicDBObject object = new BasicDBObject("_id", "79408603");
        FindIterable<Document> findIterable = collection.find(object);
        MongoCursor<Document> cursor = findIterable.iterator();

        while (cursor.hasNext()){

            documentList.add(cursor.next());

        }
        System.out.println(documentList.size());

    }


}
