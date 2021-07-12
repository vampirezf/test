package mongo;


import com.alibaba.fastjson.JSONArray;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

public class MongoUtil {

    public static MongoTemplate mongoTemplate;


    public static void main(String[] args) {
//        try {
//           new MongoUtil().getLJXContent();
//        } catch (Exception ex) {
//            System.out.print(ex);
//        }

        String res = new MongoUtil().getLJXContent1(3141911L);
        System.out.println(res);
    }

    public void reload() {
        try {
            if (mongoTemplate == null) {
                String url = "mongodb://192.168.1.138:27017/ex9_1";
                MongoClientOptions.Builder mongoBuilder = new MongoClientOptions.Builder();
                mongoBuilder.maxWaitTime(1000 * 60 * 3);
                mongoBuilder.connectTimeout(60 * 1000 * 3); //与数据库建立连接的timeout设置为1分钟
                mongoBuilder.minConnectionsPerHost(1);
                MongoClientURI mongoClientURI = new MongoClientURI(url,
                        MongoClientOptions.builder().cursorFinalizerEnabled(false));
// MongoClientURI mongoClientURI = new MongoClientURI(url, mongoBuilder);
//1.使用 mongoDbFactory 方式连接
                SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClientURI);
                mongoTemplate = new MongoTemplate(mongoDbFactory);
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }

    }

    public String getLJXContent1(Long id) {
        if (mongoTemplate == null) {
            reload();
        }
        MongoCollection collection = mongoTemplate.getDb().getCollection("contentMongoEO");
        BasicDBObject condition = new BasicDBObject();
        //condition.put("_id", id);
        List<Long> values = new ArrayList<>();
        values.add(3141911L);
        values.add(3141921L);
        condition.put("_id",new BasicDBObject("$in",values));
//        Query query = new Query();
//        query.addCriteria(Criteria.where("_id").in("3141911"));
        //MongoCursor<Document> cursor = collection.find(condition).sort(Sorts.orderBy(Sorts.descending("times"))).skip(0).limit(10).iterator();
        MongoCursor<org.bson.Document> cursor = collection.find(condition).iterator();
        while (cursor.hasNext()) {
            org.bson.Document document = cursor.next();
            return document.get("content").toString();
        }
        return "";
    }


    public   String getLJXContent2() {
        if (mongoTemplate == null) {
            reload();
        }
        MongoCollection collection = mongoTemplate.getDb().getCollection("contentMongoEO");
        BasicDBObject condition = new BasicDBObject();
//        condition.put("_id", id);
// Query query = new Query();
// query.addCriteria(Criteria.where("_id").in("5605351"));
//数据
// MongoCursor<Document> cursor = collection.find(condition).sort(Sorts.orderBy(Sorts.descending("times"))).skip(0).limit(10).iterator();//
        MongoCursor<org.bson.Document> cursor = collection.find(condition).iterator();//
        int index = 0;
        List<String> ids = new ArrayList<>();
        while (cursor.hasNext()) {
            index++;
            org.bson.Document document = cursor.next();
            String content = document.get("content").toString();
            if (content.indexOf("ahta.com.cn") > -1) {
                ids.add(document.get("_id").toString());
                System.out.println("---finde--" + index + "----");
            }
            System.out.println("-----" + index + "----");
        }
        System.out.println("---------------------------------"+ JSONArray.toJSONString(ids));
        return "";
    }


}