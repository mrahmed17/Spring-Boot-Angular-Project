package com.mrahmed.HRandPayrollManagementSystem.repository;

import com.mrahmed.HRandPayrollManagementSystem.entity.Bonus;
import com.mrahmed.HRandPayrollManagementSystem.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    @Query("SELECT b.bonusAmount FROM Bonus b WHERE b.user.id = :userId AND b.year = :year")
    BigDecimal getBonusForUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    @Query("SELECT SUM(b.bonusAmount) FROM Bonus b WHERE b.user.id = :userId AND b.year = :year")
    BigDecimal getTotalBonusForUserAndYear(@Param("userId") Long userId, @Param("year") int year);

    @Query("SELECT b FROM Bonus b WHERE b.bonusMonth = :bonusMonth AND b.year = :year")
    List<Bonus> getBonusesByMonthAndYear(@Param("bonusMonth") Month bonusMonth, @Param("year") int year);

    @Query("SELECT b FROM Bonus b WHERE b.user.id = :userId AND b.bonusMonth = :bonusMonth AND b.year = :year")
    Bonus getBonusForUserByMonthAndYear(@Param("userId") Long userId, @Param("bonusMonth") Month bonusMonth, @Param("year") int year);

    @Query("SELECT SUM(b.bonusAmount) FROM Bonus b WHERE b.year = :year")
    BigDecimal getTotalBonusPaidInYear(@Param("year") int year);

    @Query("SELECT b FROM Bonus b WHERE b.bonusDate BETWEEN :startDate AND :endDate")
    List<Bonus> getBonusesBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT DISTINCT b.user.id FROM Bonus b WHERE b.year = :year")
    List<Long> getUsersWhoReceivedBonusInYear(@Param("year") int year);

    @Query("SELECT b FROM Bonus b WHERE b.user.id = :userId ORDER BY b.bonusDate DESC LIMIT 1")
    Bonus getLatestBonusForUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(b) FROM Bonus b WHERE b.user.id = :userId AND b.year = :year")
    int countBonusesForUserInYear(@Param("userId") Long userId, @Param("year") int year);

    @Query("SELECT SUM(b.bonusAmount) FROM Bonus b WHERE b.bonusMonth = :bonusMonth AND b.year = :year")
    BigDecimal getTotalBonusForMonthAndYear(@Param("bonusMonth") Month bonusMonth, @Param("year") int year);


}
