package dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by lzy on 2016/9/9.
 */
public class MongoDBJDBC {
//连接要做成单例的形式
    private static Logger logger = Logger.getLogger(MongoDBJDBC.class);
    private static MongoCollection<Document> collection;

    public static void main( String args[] ){
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase mongoDatabase = mongoClient.getDatabase("code_generator");

        // 记录info级别的信息
        logger.info("Connect to database successfully!");

        MongoCollection<Document> collection = mongoDatabase.getCollection("model");

/*        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());*/

/*        for (Document cur : collection.find()) {
            System.out.println(cur.toJson());
            循环提早结束，会造成游标泄漏
        }*/

/*
* BSON格式的过滤器
* 几种查询方式：1、选择（where，选行）  2、排序    3、投影（选列）  4、聚合查询（有比较大的发挥空间）
* */
/*
* ---->json
* <----read json
* */
        Document myDoc = collection.find(eq("i", 71)).first();
        System.out.println(myDoc.toJson());

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

    }

    class Model{

    }
    /*
    * 返回值应该斟酌一下
    * */

    void createModel(Model model){
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));

        collection.insertOne(doc);
    }

    void bulkCreateModel(List<Model> models){
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }
        collection.insertMany(documents);
    }
}

/*
*         // 记录debug级别的信息
        //logger.debug("This is debug message.");
        // 记录error级别的信息
        //logger.error("This is error message.");
* */

