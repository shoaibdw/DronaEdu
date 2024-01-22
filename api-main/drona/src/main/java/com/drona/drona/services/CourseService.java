package com.drona.drona.services;

import com.drona.drona.models.Course;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;



/**
 * Service class for performing course-related operations.
 */
@Service
public class CourseService {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter converter;


    /**
     * Find courses by a search field, minimum and maximum rating, sort order, and limit.
     *
     * @param field The search field.
     * @param minRating The minimum rating.
     * @param maxRating The maximum rating.
     * @param sort The sort order.
     * @param limit The maximum number of results to return.
     * @return A list of courses matching the criteria.
     */
    public List<Course> findByFieldAndRatingRange(String field, double minRating, double maxRating, String sort, int limit) {

        // Create a list to store the resulting courses.
        final List<Course> courses = new ArrayList<>();

        // Obtain a reference to the MongoDB database and the "courses" collection.
        MongoDatabase database = mongoClient.getDatabase("DronaDB");
        MongoCollection<Document> collection = database.getCollection("courses");

        // Create the regex pattern to perform partial match search
        Pattern regexPattern = Pattern.compile(".*" + field + ".*", Pattern.CASE_INSENSITIVE);

        // Perform an aggregation query to filter, sort, and limit the courses based on criteria.
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("$and", Arrays.asList(
                                new Document("$or", Arrays.asList(
                                        new Document("title", regexPattern),
                                        new Document("description", regexPattern),
                                        new Document("price", regexPattern),
                                        new Document("lessons", regexPattern),
                                        new Document("duration", regexPattern),
                                        new Document("language", regexPattern),
                                        new Document("category", regexPattern)
                                )),
                                new Document("rating", new Document("$gte", minRating).append("$lte", maxRating))
                        ))
                ),
                new Document("$sort", new Document(sort, 1L)),
                new Document("$limit", limit)
        ));

        // Convert the MongoDB documents to Course objects and add them to the result list.
        result.forEach(doc -> courses.add(converter.read(Course.class, doc)));

        return courses;
    }





//    public List<Course> findByField(String field, String sort, int limit) {
//        final List<Course> courses = new ArrayList<>();
//        MongoDatabase database = mongoClient.getDatabase("DronaDB");
//        MongoCollection<Document> collection = database.getCollection("courses");
//
//        // Create the regex pattern to perform partial match search
//        Pattern regexPattern = Pattern.compile(".*" + field + ".*", Pattern.CASE_INSENSITIVE);
//
//        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
//                new Document("$match",
//                        new Document("$or", Arrays.asList(
//                                new Document("title", regexPattern),
//                                new Document("description", regexPattern),
//                                new Document("price", regexPattern),
//                                new Document("lessons", regexPattern),
//                                new Document("duration", regexPattern),
//                                new Document("rating", regexPattern),
//                                new Document("language", regexPattern),
//                                new Document("category", regexPattern)
//                        ))
//                ),
//                new Document("$sort", new Document(sort, 1L)),
//                new Document("$limit", limit)
//        ));
//
//        result.forEach(doc -> courses.add(converter.read(Course.class, doc)));
//
//        return courses;
//    }

//    public List<Course> findByRating(double minRating, double maxRating, int limit) {
//
//        final List<Course> courses = new ArrayList<>();
//        MongoDatabase database = mongoClient.getDatabase("DronaDB");
//        MongoCollection<Document> collection = database.getCollection("courses");
//
//        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$match",
//                        new Document("rating", new Document("$gte", minRating)).append("rating", new Document("$lte", maxRating))),
//                new Document("$sort",
//                        new Document("rating", -1L)),
//                new Document("$limit", limit)));
//
//        result.forEach(doc -> courses.add(converter.read(Course.class, doc)));
//
//        return courses;
//    }




//    public List<Course> findByField(String field,String sort,int limit)
//    {
//
//        final List<Course> courses=new ArrayList<>();
//        MongoDatabase database = mongoClient.getDatabase("DronaDB");
//        MongoCollection<Document> collection = database.getCollection("courses");
//
//        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
//                        new Document("index", "Default")
//                                .append("text",
//                                        new Document("query", new Document("$regex", "^java"))
//                                                .append("path", Arrays.asList("title","description", "price", "lessons", "duration", "rating", "language","category")))),
//                new Document("$sort",
//                        new Document(sort, 1L)),
//                new Document("$limit", limit)));
//
//        result.forEach(doc ->courses.add(converter.read(Course.class,doc)));
//
//        return courses;
//    }





}
