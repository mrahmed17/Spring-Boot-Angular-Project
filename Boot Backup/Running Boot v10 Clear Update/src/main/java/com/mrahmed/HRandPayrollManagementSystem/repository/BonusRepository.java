package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Bonus;
import com.mrahmed.HRandPayrollManagementSystem.entity.LeaveType;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    @Query("SELECT b FROM Bonus b WHERE b.user.email = :email")
    List<Bonus> findBonusesByEmail(@Param("email") String email);

    @Query("SELECT b FROM Bonus b WHERE LOWER(b.user.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Bonus> findBonusesByName(@Param("name") String name);


    @Query("SELECT SUM(b.bonusAmount) FROM Bonus b WHERE b.user.id = :userId AND LOWER(b.user.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    double getTotalBonusByName(@Param("userId") Long userId);

    @Query("SELECT b FROM Bonus b WHERE b.user.id = :userId")
    List<Bonus> findBonusesByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(b.bonusAmount) FROM Bonus b WHERE b.user.id = :userId")
    double getTotalBonusByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Bonus b WHERE b.bonusMonth = :month")
    List<Bonus> findBonusesByMonth(@Param("month") Month month);


    @Query("SELECT SUM(b.bonusAmount) FROM Bonus b WHERE b.bonusMonth = :month")
    double getTotalBonusByMonth(@Param("month") Month month);

    @Query("SELECT b FROM Bonus b WHERE b.year = :year")
    List<Bonus> findBonusesByYear(@Param("year") int year);

    @Query("SELECT SUM(b.bonusAmount) FROM Bonus b WHERE b.year = :year")
    double getTotalBonusByYear(@Param("year") int year);

    @Query("SELECT b FROM Bonus b WHERE b.bonusDate BETWEEN :startDate AND :endDate")
    List<Bonus> findBonusesByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Bonus b WHERE b.user.id = :userId ORDER BY b.bonusDate DESC")
    List<Bonus> findLatestBonusByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT b.user.id FROM Bonus b WHERE b.year = :year")
    List<Long> getUsersWhoReceivedBonusInYear(@Param("year") int year);

    @Query("SELECT COUNT(b) FROM Bonus b WHERE b.user.id = :userId AND b.year = :year")
    int countBonusesForUserInYear(@Param("userId") Long userId, @Param("year") int year);


}
