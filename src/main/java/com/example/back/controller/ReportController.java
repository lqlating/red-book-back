package com.example.back.controller;

import com.example.back.pojo.Report;
import com.example.back.pojo.Result;
import com.example.back.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Operation(summary = "Get reports by article type", description = "Returns a list of reports with report_type = 'article'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Report.class)) }),
            @ApiResponse(responseCode = "404", description = "Reports not found",
                    content = @Content)
    })
    @GetMapping("/getReportsByArticleType")
    public Result getReportsByArticleType() {
        List<Report> reports = reportService.getReportsByArticleType();
        if (!reports.isEmpty()) {
            return Result.success(reports);
        } else {
            return Result.error("Reports not found");
        }
    }

    @Operation(summary = "Get reports by book type", description = "Returns a list of reports with report_type = 'book'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Report.class)) }),
            @ApiResponse(responseCode = "404", description = "Reports not found",
                    content = @Content)
    })
    @GetMapping("/getReportsByBookType")
    public Result getReportsByBookType() {
        List<Report> reports = reportService.getReportsByBookType();
        if (!reports.isEmpty()) {
            return Result.success(reports);
        } else {
            return Result.error("Reports not found");
        }
    }

    @Operation(summary = "Get reports by comment type", description = "Returns a list of reports with report_type = 'comment'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Report.class)) }),
            @ApiResponse(responseCode = "404", description = "Reports not found",
                    content = @Content)
    })
    @GetMapping("/getReportsByCommentType")
    public Result getReportsByCommentType() {
        List<Report> reports = reportService.getReportsByCommentType();
        if (!reports.isEmpty()) {
            return Result.success(reports);
        } else {
            return Result.error("Reports not found");
        }
    }

    @Operation(summary = "Get reports by user type", description = "Returns a list of reports with report_type = 'user'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Report.class)) }),
            @ApiResponse(responseCode = "404", description = "Reports not found",
                    content = @Content)
    })
    @GetMapping("/getReportsByUserType")
    public Result getReportsByUserType() {
        List<Report> reports = reportService.getReportsByUserType();
        if (!reports.isEmpty()) {
            return Result.success(reports);
        } else {
            return Result.error("Reports not found");
        }
    }

    @Operation(summary = "Get reports by content type", description = "Returns a list of reports with specified content type and associated data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Report.class)) }),
            @ApiResponse(responseCode = "404", description = "Reports not found",
                    content = @Content)
    })
    @GetMapping("/getReportsByContentType")
    public Result getReportsByContentType(@RequestParam String contentType) {
        List<Report> reports = reportService.getReportsByContentType(contentType);
        if (!reports.isEmpty()) {
            return Result.success(reports);
        } else {
            return Result.error("Reports not found");
        }
    }
}