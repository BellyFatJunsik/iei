package kr.co.iei.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    private int totalPatients;
    private int totalTherapists;
    private int pendingTherapists;
    private int totalPrograms;
    private int activePrograms;
    private int totalReviews;
    private int totalReservations;
    private int completedReservations;
    private int cancelledReservations;
    private double averageRating;
    private int monthlyNewUsers;
    private int monthlyRevenue;
}
