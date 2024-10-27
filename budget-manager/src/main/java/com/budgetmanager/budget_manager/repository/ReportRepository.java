package com.budgetmanager.budget_manager.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.budgetmanager.budget_manager.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByUserUserId(int userId);

    List<Report> findByType(String type);

    List<Report> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Report> findByUserUserIdAndType(int userId, String type);

    // Adding pagination
    Page<Report> findByUserUserId(int userId, Pageable pageable);

    // Adding pagination with type filtering
    Page<Report> findByType(String type, Pageable pageable);

    // Custom query (for complex filtering)
    // @Query("SELECT r FROM Report r WHERE r.user.id = :userId AND r.startDate >= :startDate")
    // List<Report> findCustomReports(@Param("userId") int userId, @Param("startDate") LocalDate startDate);
}