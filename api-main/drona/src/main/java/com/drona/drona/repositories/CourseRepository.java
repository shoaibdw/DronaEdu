package com.drona.drona.repositories;

import com.drona.drona.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


/**
 * Repository for interacting with the "courses" collection in the MongoDB database.
 */
@Repository
public interface CourseRepository extends MongoRepository<Course,Integer> {


    /**
     * Find a course by its title.
     *
     * @param title The title of the course to search for.
     * @return An optional containing the course if found, or empty if not found.
     */
    Optional<Course> findCourseByTitle(String title);

    /**
     * Find a list of courses by their category.
     *
     * @param category The category of courses to search for.
     * @return A list of courses with the specified category.
     */
    List<Course> findCoursesByCategory(String category);

    /**
     * Find a list of courses with ratings within a specified range.
     *
     * @param min The minimum rating value.
     * @param max The maximum rating value.
     * @return A list of courses with ratings within the specified range.
     */
    List<Course> findCoursesByRatingBetween(double min, double max);

    /**
     * Find a course by its course ID.
     *
     * @param courseId The course ID to search for.
     * @return An optional containing the course if found, or empty if not found.
     */
    Optional<Course> findCourseByCourseId(int courseId);

    /**
     * Find a list of courses by their level.
     *
     * @param level The level of courses to search for.
     * @return A list of courses with the specified level.
     */
    List<Course> findCoursesByLevel(String level);



}
