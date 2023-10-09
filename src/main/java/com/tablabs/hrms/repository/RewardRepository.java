package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Reward findByEmployeeId(String emplId);

    List<Reward> findAllByEmployeeId(String emplId);

    boolean existsByEmployeeId(String emplId);
//    @Query(value = "SELECT MAX(rating) FROM reward", nativeQuery = true)
//    List<Reward> findTopMaxDoubleValue();
//    List<Reward> findMaxRating();

    //    Page<Reward> findByOrderByRatingDesc(Pageable pageable);
//    Page<Reward> findByOnWhichDateStartingWithOrderByRatingDesc(String yyyyMonth,Pageable pageable);
    Page<Reward> findByOnWhichDateStartingWithOrderByOnWhichDateDesc(String yyyyMonth, Pageable pageable);

    //    List<Reward> findByOnWhichDateMonthAndOnWhichDateYear(String month,String year);
    List<Reward> findByOnWhichDateStartingWith(String yyyyMonth);
    List<Reward> findByEmployeeIdAndOnWhichDateStartingWith(String employeeId,String yyyyMonth);

    int countByEmployeeId(String emplId);

//    Reward findTopByOrderByEmployeeIdDesc();

    @Query("SELECT y.employeeId, COUNT(y) FROM Reward y GROUP BY y.employeeId")
    List<Object[]> findCountOfEachYourField();


    default Object[] findHighestCountField() {
        List<Object[]> fieldCounts = findCountOfEachYourField();

        if(fieldCounts.isEmpty()){
            return null;
        }

        Object[] highestCountField = null;
        long highestCount = 0;

        for (Object[] fieldCount : fieldCounts) {
            long count = (long) fieldCount[1];
            if (count > highestCount) {
                highestCount = count;
                highestCountField = fieldCount;
            }
        }

        return highestCountField;
    }
}
